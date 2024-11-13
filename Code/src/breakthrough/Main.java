/**
 * The Main class serves as the entry point for the Breakthrough game.
 * It initializes the graphical user interface (GUI) for the game and
 * starts the application.
 * The commented-out section provides an alternative, text-based way to
 * play the game using the console. This is useful for debugging or as
 * a simple fallback for environments without GUI support.
 */
package breakthrough;

import javax.swing.*;

public class Main {
    /**
     * The main method launches the Breakthrough game by creating and
     * displaying the graphical user interface.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BreakthroughGameGUI gameGUI = new BreakthroughGameGUI(); 
            gameGUI.setVisible(true); 
        });    
    }
}

/*
 * The commented-out code provides a console-based version of the Breakthrough game.
 * It is left here for debugging purposes or for running the game in environments without a GUI.
 *-----Debugging ko b define krna ha for future easy use.
 * The console version allows players to input their moves manually by entering row and column values.
 * The game state is printed after each move, and the program determines the winner when the game ends.
 * Uncomment the code below if you want to play the game in text mode.
 */

//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Choose board size: 6, 8, or 10");
//        int size = scanner.nextInt();
//        Board board = new Board(size);
//
//        System.out.println("Initial Board:");
//        board.displayBoard();
//
//        boolean gameEnded = false;
//        boolean isPlayerOneTurn = true;
//
//        while (!gameEnded) {
//            System.out.println("Player " + (isPlayerOneTurn ? "1" : "2") + "'s turn");
//
//            System.out.print("Enter current row and column of the pawn to move: ");
//            int currentRow = scanner.nextInt();
//            int currentCol = scanner.nextInt();
//
//            Pawn selectedPawn = (Pawn) board.board[currentRow][currentCol];
//
//            // Check if the selected position is valid and if it is the correct player's turn
//            if ((selectedPawn == null) || (selectedPawn.isPlayerOne != isPlayerOneTurn)) {
//                System.out.println("Invalid selection. Please choose a valid pawn.");
//                continue; // Skip to the next iteration
//            }
//
//            System.out.print("Enter new row and column to move the pawn: ");
//            int newRow = scanner.nextInt();
//            int newCol = scanner.nextInt();
//            Position newPosition = new Position(newRow, newCol);
//
//            // Attempt to move the pawn
//            if (board.movePawn(selectedPawn, newPosition)) {
//                // Print the board state after a successful move
//                System.out.println("Board after Player " + (isPlayerOneTurn ? "1" : "2") + "'s move:");
//                board.displayBoard();
//
//                // Check for win conditions
//                if (board.checkWin(selectedPawn)) {
//                    gameEnded = true;
//                    System.out.println("Player " + (isPlayerOneTurn ? "1" : "2") + " wins!");
//                } else {
//                    // Switch turns if no one has won yet
//                    isPlayerOneTurn = !isPlayerOneTurn;
//                }
//            } else {
//                System.out.println("Invalid move. Try again.");
//            }
//        }
//
//        scanner.close();
