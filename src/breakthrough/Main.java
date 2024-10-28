/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package breakthrough;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose board size: 6, 8, or 10");
        int size = scanner.nextInt();
        Board board = new Board(size);

        System.out.println("Initial Board:");
        board.displayBoard();

        boolean gameEnded = false;
        boolean isPlayerOneTurn = true;

        while (!gameEnded) {
            System.out.println("Player " + (isPlayerOneTurn ? "1" : "2") + "'s turn");

            System.out.print("Enter current row and column of the pawn to move: ");
            int currentRow = scanner.nextInt();
            int currentCol = scanner.nextInt();

            Pawn selectedPawn = (Pawn) board.board[currentRow][currentCol];

            // Checking if there is pawn on that position or not or if its playerone turn or playertwo 
//            turn if its player two turn the isplayerones is also false and turn is also false
            if ((selectedPawn == null) || (selectedPawn.isPlayerOne != isPlayerOneTurn)) {
                System.out.println("Invalid selection. Please choose a valid pawn.");
                continue; //if its true we move on to next loop iteration
            }

            System.out.print("Enter new row and column to move the pawn: ");
            int newRow = scanner.nextInt();
            int newCol = scanner.nextInt();
            Position newPosition = new Position(newRow, newCol);

            // now checking to move the pawn
            if (board.movePawn(selectedPawn, newPosition)) {
                // Printing board state after each move
                System.out.println("Board after Player " + (isPlayerOneTurn ? "1" : "2") + "'s move:");
                board.displayBoard();

                // Checking win conditionvif the move result in a win
                if (board.checkWin(selectedPawn)) {
                    gameEnded = true;
                    System.out.println("Player " + (isPlayerOneTurn ? "1" : "2") + " wins!");
                } else {
                    // if no one won and one player succeefully suceeded in his turn then giving turn to other
                    isPlayerOneTurn = !isPlayerOneTurn;
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        scanner.close();
    }
}
