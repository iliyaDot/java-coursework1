package dicegame;

import java.util.HashSet;
import java.util.Scanner;

public class DiceGame {

    final Data gameData;
    final Player[] players;
    private final  HashSet<Integer> usedColumnsFirstRound; // Tracks first-round columns for uniquenes
    private final  Scanner inputScanner;

    // Sets up the game with initial player data and scores
    public DiceGame() {
        gameData = new Data();
        players = new Player[gameData.getTotalPlayers()];
        inputScanner = new Scanner(System.in);
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }
        usedColumnsFirstRound = new HashSet<>(); // Tracks columns chosen in the first round
    }

    // Simulates rolling a die with values from 1 to 6
    public int rollDie() {
        return (int) (Math.random() * 6) + 1;
    }

    // Displays the scoreboard with current scores, totals, and crossed-out entries
    public void showScoreboard() {
        System.out.println("Scoreboard:");
        System.out.print("Columns: ");
        for (int i = 2; i <= 12; i++) System.out.print(i + "\t");
        System.out.println("Total");

        for (int i = 0; i < gameData.getTotalPlayers(); i++) {
            System.out.print("Player " + (i + 1) + ": ");
            for (int j = 0; j < gameData.getColumnsRange(); j++) {
                if (gameData.getPlayerScores()[i][j] == -1) { // Display X if score was crossed out
                    System.out.print("X\t");
                } else {
                    System.out.print(gameData.getPlayerScores()[i][j] + "\t");
                }
            }
            System.out.println(gameData.getCumulativeScores()[i]);
        }
        System.out.println();
    }

    // Runs a player's turn, rolling the dice and selecting a column
    public void playTurn(int player, int round) {
        System.out.println("Press Enter to roll, Player " + (player + 1) + "...");
        inputScanner.nextLine(); // Wait for Enter

        int rollSum = players[player].rollDiceReturn();
        System.out.println("Player " + (player + 1) + " rolled a " + players[player].getDieFace(0) + " and " + players[player].getDieFace(1) + " (Sum: " + rollSum + ")");

        // Continue asking for a column until valid input is given
        while (true) {
            System.out.print("Choose a column (2-12) for this score: ");
            int chosenColumn = inputScanner.nextInt();
            inputScanner.nextLine(); // Clear newline
            int columnIdx = chosenColumn - 2;

            // Enforce rules on column selection, especially for unique choices in the first round
            if (chosenColumn < 2 || chosenColumn > 12) {
                System.out.println("Pick a column number between 2 and 12.");
            } else if (round == 1 && usedColumnsFirstRound.contains(chosenColumn)) {
                System.out.println("Column already chosen in this round. Try a different one.");
            } else if (gameData.getPlayerScores()[player][columnIdx] > 0) {
                System.out.println("Column taken by you. Choose another column.");
            } else {
                // Mark the column as chosen in the first round if applicable
                if (round == 1) {
                    usedColumnsFirstRound.add(chosenColumn);
                }
                gameData.getPlayerScores()[player][columnIdx] = rollSum;
                System.out.println("Player " + (player + 1) + " placed " + rollSum + " in column " + chosenColumn);
                break;
            }
        }
        showScoreboard();
    }

    // Checks each column to see if its fully filled awarding points where applicable
    public void checkColumns() {
        for (int i = 0; i < gameData.getColumnsRange(); i++) {
            if (gameData.getCompletedColumns()[i]) continue; // Skip if column already scored

            boolean columnFull = true;
            for (int j = 0; j < gameData.getTotalPlayers(); j++) {
                if (gameData.getPlayerScores()[j][i] == 0) { // Look for empty spots in column
                    columnFull = false;
                    break;
                }
            }
            if (columnFull) {
                handleColumnWinner(i);
                gameData.getCompletedColumns()[i] = true; // Mark column as scored
            }
        }
    }

    // Determines and marks the winner in a fully filled column, crossing out lower scores
    private void handleColumnWinner(int columnIdx) {
        int highestScore = 0, winningPlayer = -1;
        boolean tie = false;

        // First, find the highest score in the column, checking for ties
        for (int j = 0; j < gameData.getTotalPlayers(); j++) {
            if (gameData.getPlayerScores()[j][columnIdx] > highestScore) {
                highestScore = gameData.getPlayerScores()[j][columnIdx];
                winningPlayer = j;
                tie = false;
            } else if (gameData.getPlayerScores()[j][columnIdx] == highestScore) {
                tie = true;
            }
        }

        // Mark defeated scores as crossed out if no tie exists
        if (!tie) {
            for (int j = 0; j < gameData.getTotalPlayers(); j++) {
                if (j != winningPlayer && gameData.getPlayerScores()[j][columnIdx] < highestScore) {
                    gameData.getPlayerScores()[j][columnIdx] = -1; // Use -1 to indicate crossed-out score
                }
            }
            gameData.getCumulativeScores()[winningPlayer] += columnIdx + 2;
            System.out.println("Player " + (winningPlayer + 1) + " takes column " + (columnIdx + 2) + " with " + highestScore + " points.");
        } else {
            System.out.println("Column " + (columnIdx + 2) + " results in a tie; no points given.");
        }
    }

    public static void main(String[] args) {
        DiceGame game = new DiceGame();

        // Run the game for the specified rounds
        for (int round = 1; round <= game.gameData.getGameRounds(); round++) {
            System.out.println("Press Enter to start Round " + round);
            game.inputScanner.nextLine(); // Wait for Enter before starting round

            System.out.println("Starting Round " + round);
            for (int playerIndex = 0; playerIndex < game.gameData.getTotalPlayers(); playerIndex++) {
                game.playTurn(playerIndex, round);
            }
            game.checkColumns();
            System.out.println("End of Round " + round + "\n");
        }

        // Show final scores and announce the winner
        System.out.println("Game Over! Final Scores:");
        for (int i = 0; i < game.gameData.getTotalPlayers(); i++) {
            System.out.println("Player " + (i + 1) + ": " + game.gameData.getCumulativeScores()[i] + " points");
        }

        determineWinner(game.gameData.getCumulativeScores());
    }

    // Determines and announces the overall winner based on cumulative scores
    private static void determineWinner(int[] scores) {
        int maxScore = 0, bestPlayer = -1;
        boolean tieExists = false;

        for (int i = 0; i < scores.length; i++) {
            if (scores[i] > maxScore) {
                maxScore = scores[i];
                bestPlayer = i;
                tieExists = false;
            } else if (scores[i] == maxScore) {
                tieExists = true;
            }
        }

        if (tieExists) {
            System.out.println("The game ends in a tie!");
        } else {
            System.out.println("The winner is Player " + (bestPlayer + 1) + " with " + maxScore + " points!");
        }
    }
}
