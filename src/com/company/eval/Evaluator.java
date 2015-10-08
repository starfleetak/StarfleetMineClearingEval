package com.company.eval;

import com.company.eval.Ship.Offset;
import com.company.eval.Ship.Pattern;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The main program the reads the input parameters, instantiates space and objects,
 * executes the script and computes the result.
 * 
 * Main methods:
 * Public: 
 * {@link #runScript() }
 * Private:
 * {@link #createCuboidSpaceFromFile(java.nio.file.Path) }
 * {@link #createScriptFromFile(java.nio.file.Path) }
 * 
 * @author Vijay
 */
public class Evaluator {

  /*
   * java -cp "StarfleetMineClearingEval.jar" com.company.eval.Evaluator "fieldEx1and3.txt" "scriptEx3.txt"

Step 1

gamma

.

pass(1)
   */
  public static void main (String[] args) throws IOException {
    Evaluator e = new Evaluator(args[0], args[1]);
    e.runScript();
  }
  
  private String result;
  private int points;
  private CuboidSpace space;
  
  public Evaluator(String fieldPath, String scriptPath) throws IOException {

    Path field = Paths.get(fieldPath);
    Path script = Paths.get(scriptPath);
    createCuboidSpaceFromFile(field);
    createScriptFromFile(script);
    
  }
  
  /**
   * Create the CuboidSpace and actors (mines and ship) in the space
   * 
   * @param fieldFilePath the {@link Path} to the field (cuboid space) file
   * 
   * @throws IOException if file not found
   * @throws InvalidObjectException if there is a parse error
   */
  private void createCuboidSpaceFromFile(Path fieldFilePath) throws IOException {
    List<Mine> mines = new ArrayList<Mine>();
    
    List<String> fieldStrings = Files.readAllLines(fieldFilePath, StandardCharsets.US_ASCII);
    int gridHeight = fieldStrings.size();
    
    if (gridHeight % 2 == 0) {
      throw new InvalidObjectException("A grid that has even number of rows can't place the ship at center!");
    }
    int heightOffset = (int) Math.floor(gridHeight/2);
    
    // We know at this point, there is atleast one line, otherwise, we would throw
    // exception above
    int gridWidth = fieldStrings.get(0).trim().toCharArray().length;
    int widthOffset = (int)Math.floor(gridWidth/2); // Assuming array index starting at 0
    int rowCount = 0;
    for (String fieldY : fieldStrings) {
      char [] xChars = fieldY.trim().toCharArray();
      if (gridWidth != xChars.length) {
        throw new InvalidObjectException("Found two rows, 0 and " + rowCount 
                + " with unequal characters!");
      }
      for (int colCount = 0; colCount < gridWidth; colCount++) {
        int depth = (int) xChars[colCount];
        if (depth >= 65 && depth <= 90) {
          mines.add(new Mine(colCount - widthOffset, 
                  heightOffset - rowCount, // This reversal is required because of traslation from 
                  // array indexing to Cartesian coordinate system
                  CuboidSpace.CHAR_OFFSET_UPPER - depth));
        } else if (depth >= 97 && depth <= 122) {
          mines.add(new Mine(colCount - widthOffset, 
                  heightOffset - rowCount, 
                  CuboidSpace.CHAR_OFFSET_LOWER - depth));
        }
      }
      rowCount++;
    }
    
    this.setSpace(new CuboidSpace(new Ship(0, 0, 0), mines));
  }
  
  /**
   * Build the script file with relevant assumptions, from the file
   * 
   * @param scriptFilePath the {@link Path} to the script file
   * 
   * @throws IOException if file is not found
   * @throws InvalidObjectException if there is a parse error
   */
  private void createScriptFromFile(Path scriptFilePath) throws IOException {
    List<String> scriptStrings = Files.readAllLines(scriptFilePath, StandardCharsets.US_ASCII);
    this.scriptTextStrings = scriptStrings;
    for (String scriptLine : scriptStrings) {
      // Upper case here to use Enum.valueOf()
      // trim to remove any possibility of split giving spurious words
      String [] words = scriptLine.trim().toUpperCase().split("\\s+");
      // Handle empty lines
      if (words.length == 0) {
        this.firePatterns.add(null);
        this.shipMoves.add(Offset.NO_OFFSET);
      } else if (words.length == 1) {
        // If there is only one word, try to get a pattern, if not,
        // try to get a ship move, if neither, throw parse error.
        try {
          Pattern p = Pattern.valueOf(words[0]);
          this.firePatterns.add(p);
          this.shipMoves.add(Offset.NO_OFFSET);
        } catch (IllegalArgumentException iae) {
          // Guess it wasn't a pattern, it must be a move
          try {
            Offset o = Offset.valueOf(words[0]);
            this.firePatterns.add(null);
            this.shipMoves.add(o);
          } catch (IllegalArgumentException iae2) {
            throw new InvalidObjectException("We got a word: " + words[0] 
                    + " that is neither a firing patter or a ship move.");
          }
        }
      } else if (words.length == 2) {
        // Find the word among the two words, that is a pattern
        // If neither is a pattern we have parse error
        int patternWordIndex;
        try {
          Pattern p = Pattern.valueOf(words[0]);
          patternWordIndex = 0;
          this.firePatterns.add(p);
        } catch (IllegalArgumentException iae) {
          try {
            Pattern p = Pattern.valueOf(words[1]);
            patternWordIndex = 1;
            this.firePatterns.add(p);
          } catch (IllegalArgumentException iae2)  {
            throw new InvalidObjectException("Neither words: " + Arrays.toString(words) + " encodes a firing pattern!");
          }
        }
        
        // The other word than above, should be the move, otherwise we have a
        // parse error
        String offsetWord = "empty"; // Just need to initialize for error message!
        try {
          if (patternWordIndex == 0) {
            offsetWord = words[1];
          } else {
            offsetWord = words[0];
          }
          Offset o = Offset.valueOf(offsetWord);
          this.shipMoves.add(o);
        } catch (IllegalArgumentException iae) {
          throw new InvalidObjectException("Could not find an ship move from: " + offsetWord);
        }
      } else {
        throw new InvalidObjectException("Too many words: " + Arrays.toString(words));
      }
    }
    
    // Sanity check - that number of lines in script is same as ship moves and fire pattern
    // including null fire patterns and depth only moves
    if (scriptStrings.size() != this.shipMoves.size() ||
            scriptStrings.size() != this.firePatterns.size()) {
      throw new InvalidObjectException("Mismatch in number of script lines and moves/firings: "
      + "\nscript length: " + scriptStrings.size()
              + "\nmoves: " + this.shipMoves.size() 
              + "\nfire patterns: " + this.firePatterns.size());
    }
  }
  
  public void runScript() {
    int mineCount = this.getSpace().getMines().size();
    this.setPoints(10 * mineCount);
    
    int numPointsForMoves = 0;
    int numPointsForFires = 0;
    for (int stepCount = 0; stepCount < this.shipMoves.size(); stepCount++) {
      System.out.println("Step " + (stepCount + 1) + "\n");
      Offset move = this.shipMoves.get(stepCount);
      Pattern pattern = this.firePatterns.get(stepCount);
      
      this.getSpace().executeMove(pattern, move, 
              this.scriptTextStrings.get(stepCount));
      
      if (pattern != null
              && numPointsForFires < 5 * mineCount) {
        numPointsForFires = Math.min(numPointsForFires + 5, 5 * mineCount);
      }
      
      if (move != Offset.NO_OFFSET
              && numPointsForMoves < 3 * mineCount) {
        numPointsForMoves = Math.min(numPointsForMoves + 2, 3 * mineCount);
      }
      
      if (this.getSpace().getMines().isEmpty()) {
        if (stepCount == this.shipMoves.size() - 1) { // if this is last step
          this.setPoints(this.getPoints() - numPointsForFires - numPointsForMoves);
          this.setResult("pass");
        } else { // steps remain!
          this.setPoints(1);
          this.setResult("pass");
        }
        break;
      }
      
      if (missedAMine()){
        this.setPoints(0);
        this.setResult("fail");
        break;
      }
    }
    
    if (!this.getSpace().getMines().isEmpty()) {
      this.setPoints(0);
      this.setResult("fail");
    }
    
    System.out.println(this.getResult() + "(" + this.getPoints() + ")");
  }
  
  
  
  /**
   * Stop the script if script misses a mine
   * 
   * @return true if we missed a mine 
   */
  private boolean missedAMine() {
    for (Mine m : this.getSpace().getMines()) {
      // The ship missed a mine!
      // NOTE: It seems from example, it is considered missed, if ship is at 
      // same level, i.e. torpedos only clear mines below, not at same level
      if (m.getzAxis() >= this.getSpace().getShip().getzAxis()) {
        return true;
      }
    }
    return false;
  }

  /*Internal state variables to implement behavior */
  
  private List<Pattern> firePatterns = new ArrayList<Pattern>();
  private List<Offset> shipMoves = new ArrayList<Offset>();
  private List<String> scriptTextStrings;
  
  /*Accessor methods for external (more interface level) state variables*/
  
  public String getResult() {
    return result;
  }

  private void setResult(String result) {
    this.result = result;
  }

  public int getPoints() {
    return points;
  }

  private void setPoints(int points) {
    this.points = points;
  }

  public CuboidSpace getSpace() {
    // Immutable - no defensive copy
    return space;
  }

  private void setSpace(CuboidSpace space) {
    this.space = space;
  }
  
  
}
