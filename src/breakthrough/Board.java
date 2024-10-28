package breakthrough;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Muhammad Eman Aftab
 */
import java.util.ArrayList;

public class Board {

    private int size;
    public Doll[][] board;

    public Board(int size) {
        this.size = size;
        board = new Doll[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int col = 0; col < size; col++) {
            board[0][col] = new Pawn(new Position(0, col), true);
            board[1][col] = new Pawn(new Position(1, col), true);
        }

        for (int col = 0; col < size; col++) {
            board[size - 1][col] = new Pawn(new Position(size - 1, col), false);
            board[size - 2][col] = new Pawn(new Position(size - 2, col), false);
        }
    }

    public boolean isMoveValid(Pawn pawn, Position newPosition) {
        return newPosition.getRow() >= 0 && newPosition.getRow() < size
                && newPosition.getColumn() >= 0 && newPosition.getColumn() < size
                && pawn.canMove(newPosition);
    }

    // Checkign if the move is within bounds and valid for the direction like can move in diagnol 
//         and one step forward if there is no opponent pawn
    // Checking if there is opponent pawn diagnolly and we can capture it
    public boolean movePawn(Pawn pawn, Position newPosition) {
        if (isMoveValid(pawn, newPosition)) {
            if (board[newPosition.getRow()][newPosition.getColumn()] == null) {
                executeMove(pawn, newPosition);
                return true;
            } else if (board[newPosition.getRow()][newPosition.getColumn()] instanceof Pawn) {
                Pawn targetPawn = (Pawn) board[newPosition.getRow()][newPosition.getColumn()];
                if (pawn.canCapture(newPosition) && targetPawn.isPlayerOne != pawn.isPlayerOne) {
                    executeMove(pawn, newPosition);
                    return true;
                }
            }
        }
        return false;
    }

    private void executeMove(Pawn pawn, Position newPosition) {
        board[pawn.getPosition().getRow()][pawn.getPosition().getColumn()] = null;
        pawn.setPosition(newPosition);
        board[newPosition.getRow()][newPosition.getColumn()] = pawn;
    }

    // making board visual, this is for console for debugging issues
    public void displayBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == null) {
                    System.out.print("__ ");
                } else if (((Pawn) board[row][col]).isPlayerOne) { //type casting and changing it to pawn type
                    System.out.print("P1 ");
                } else {
                    System.out.print("P2 ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

//Wining condition checking 
    public boolean checkWin(Pawn pawn) {
        return (pawn.isPlayerOne && pawn.getPosition().getRow() == size - 1)
                || (!pawn.isPlayerOne && pawn.getPosition().getRow() == 0);
    }
}
