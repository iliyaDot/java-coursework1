package dicegame;

import java.util.Scanner;

public class Data {
     private final int[][] playerScores; // Stores each player's score for each column
     private final int totalPlayers = 3; // Total number of players in the game
     private final int columnsRange = 11; // Range of columns from 2 to 12
     private final int gameRounds = 11; // Total rounds to play
     private final int[] cumulativeScores; // Accumulated scores for each player
     private final boolean[] completedColumns; // Tracks if a column has been scored
     private final Scanner scanner; // Scanner for user input

    public Data() {
        playerScores = new int[totalPlayers][columnsRange];
        cumulativeScores = new int[totalPlayers];
        completedColumns = new boolean[columnsRange];
        scanner = new Scanner(System.in); // Initialize scanner
    }

    public int[][] getPlayerScores() {
        return playerScores;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public int getColumnsRange() {
        return columnsRange;
    }

    public int getGameRounds() {
        return gameRounds;
    }

    public int[] getCumulativeScores() {
        return cumulativeScores;
    }

    public boolean[] getCompletedColumns() {
        return completedColumns;
    }

    public Scanner getScanner() {
        return scanner;
    }
    
}
