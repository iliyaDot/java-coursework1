package dicegame;

public class Player {

    // Array of Die objects representing the player's dice (each player has 2 dice)
    private final Die[] dice;
    
    // Array to store the player's score for each column 
    private final int[] score;
    
    // Tracks the player's cumulative or total score across rounds
    private int totalScore;

    // Default constructor initializes dice and score array, setting up the player's starting state
    public Player() {
        dice = new Die[2];       // Each player has 2 dice
        score = new int[12];     // Score array for columns 2 to 12 (11 columns, 0-11 indexes)
        totalScore = 0;          // Initialize total score to zero
        for (int i = 0; i < dice.length; i++) {
            dice[i] = new Die(); // Instantiate each Die object with default settings (6 sides)
        }
    }

    // Returns the array of dice belonging to the player
    public Die[] getDice() {
        return dice;
    }

    // Returns a specific Die object based on the given index (0 or 1 since there are 2 dice)
    public Die getDie(int index) {
        return dice[index];
    }
    
    // Gets the face value of a specific die,  for retrieving the result of a roll
    public int getDieFace(int index) {
        return dice[index].getFace();
    }

    // Returns the score array for the player, which stores the score for each column
    public int[] getScore() {
        return score;
    }

    // Retrieves the player's total score across all rounds
    public int getTotalScore() {
        return totalScore;
    }
    
    // Sets the score for a specific column, where input is the score and index is the column (0-11)
    public void setScore(int input, int index) {
        score[index] = input;
    }
    
    // Sets the entire score array at once, useful if updating all column scores at once time
    public void setScore(int[] inputArray) {
        System.arraycopy(inputArray, 0, score, 0, inputArray.length);
    }

    // Updates the players total cumulative score
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    // Rolls both dice for the player, updating the face values
    public void rollDice() {
        for (Die die : dice) {
            die.roll(); // Roll each die, changing its face value
        }
    }
    
    // Rolls both dice and returns the sum of their face values
    public int rollDiceReturn() {
        int total = 0;
        for (Die die : dice) {
            total += die.rollReturn(); // Rolls each die and adds the result to total
        }
        return total; // Return the sum of both dice
    }

}
