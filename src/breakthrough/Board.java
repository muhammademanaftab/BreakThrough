package breakthrough;

/**
 * This class represents the game board for the Breakthrough game.
 * It holds the grid of cells and manages the pawns placed on it.
 * The board also handles the initialization, movements, and winning condition checks.
 */
public class Board {

    private int size; // The size of the board (number of rows and columns).
    private Doll[][] board; // A 2D array representing the board with pawns .

    /**
     * Gets the current state of the board as a 2D array of Dolls.
     *
     * @return the board grid containing all pawns.
     */
    public Doll[][] getBoard() {
        return this.board;
    }

    /**
     * Creates a new game board of the given size and initializes it.
     * Pawns are placed in their starting positions for both players.
     *
     * @param size the size of the board (e.g... 8 for an 8x8 board).
     */
    public Board(int size) {
        this.size = size;
        board = new Doll[size][size];
        initializeBoard();
    }

    /**
     * Places pawns for both players in their starting positions on the board.
     * Player 1 pawns are placed at the top, and Player 2 pawns at the bottom.
     */
    private void initializeBoard() {
        for (int col = 0; col < size; col++) {
            board[0][col] = new Pawn(new Position(0, col), true); // Player 1's first row.
            board[1][col] = new Pawn(new Position(1, col), true); // Player 1's second row.
        }

        for (int col = 0; col < size; col++) {
            board[size - 1][col] = new Pawn(new Position(size - 1, col), false); // Player 2's first row.
            board[size - 2][col] = new Pawn(new Position(size - 2, col), false); // Player 2's second row.
        }
    }

    /**
     * Checks if a pawn's move to a new position is valid.
     * It ensures the new position is within the bounds of board or not and checks if the pawns movement rules are followed.
     *
     * @param pawn        the pawn to be moved.
     * @param newPosition the position where the pawn is going to move.
     * @return true if the move is valid, false otherwise.
     */
    private boolean isMoveValid(Pawn pawn, Position newPosition) {
        return newPosition.getRow() >= 0 && newPosition.getRow() < size
                && newPosition.getColumn() >= 0 && newPosition.getColumn() < size
                && pawn.canMove(newPosition);
    }

    /**
     * Attempts to move a pawn to a new position.
     * The method checks if the move is valid and handles capturing opponent pawns if needed.
     *
     * @param pawn        the pawn to be moved.
     * @param newPosition the position where the pawn should move.
     * @return true if the move was successful, false if it was invalid.
     */
    public boolean movePawn(Pawn pawn, Position newPosition) {
        if (isMoveValid(pawn, newPosition)) {
            // If the target cell is empty, move the pawn.
            if (board[newPosition.getRow()][newPosition.getColumn()] == null) {
                executeMove(pawn, newPosition);
                return true;
            }
            // If the target cell has an opponent pawn, attempt to capture.
            else if (board[newPosition.getRow()][newPosition.getColumn()] instanceof Pawn) {
                Pawn targetPawn = (Pawn) board[newPosition.getRow()][newPosition.getColumn()];
                if (pawn.canCapture(newPosition) && targetPawn.isPlayerOne != pawn.isPlayerOne) {
                    executeMove(pawn, newPosition);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Executes the move of a pawn to a new position by updating its position
     * and updating the board grid.
     *
     * @param pawn        the pawn to be moved.
     * @param newPosition the position where the pawn should move.
     */
    private void executeMove(Pawn pawn, Position newPosition) {
        board[pawn.getPosition().getRow()][pawn.getPosition().getColumn()] = null; // Clear old position.
        pawn.setPosition(newPosition); // Update pawn's position.
        board[newPosition.getRow()][newPosition.getColumn()] = pawn; // Place pawn in new position.
    }

    /**
     * Displays the board in the console for debugging purposes.
     * Player 1's pawns are shown as "P1" and Player 2's pawns as "P2".
     * Empty cells are displayed as "__".
     * This is for debugging purposes, first trying to test in console then will develop its UI.
     */
    public void displayBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == null) {
                    System.out.print("__ ");
                } else if (((Pawn) board[row][col]).isPlayerOne) { // Player 1's pawn.
                    System.out.print("P1 ");
                } else { // Player 2's pawn.
                    System.out.print("P2 ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Checks if a pawn has reached the opposite side of the board, meeting the winning condition.
     *
     * @param pawn the pawn to check.
     * @return true if the pawn has won the game, false otherwise.
     */
    public boolean checkWin(Pawn pawn) {
        return (pawn.isPlayerOne && pawn.getPosition().getRow() == size - 1) // Player 1 reaches the bottom.
                || (!pawn.isPlayerOne && pawn.getPosition().getRow() == 0); // Player 2 reaches the top.
    }
}
