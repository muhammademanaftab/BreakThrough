/**
 * The Position class represents a specific location on the game board
 * using row and column coordinates.
 * 
 * This class helps to track where a pawn or any other object is located
 * on the board and provides methods to retrieve and compare positions.
 */
package breakthrough;

public class Position {
    private int row; // The row number of the position.
    private int column; // The column number of the position.

    /**
     * Creates a new Position with the specified row and column.
     * 
     * @param row The row number of the position (e.g., 0 for the first row).
     * @param column The column number of the position (e.g., 0 for the first column).
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Gets the row number of this position.
     * 
     * @return The row number.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column number of this position.
     * 
     * @return The column number.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Checks if this position is equal to another position.
     * Two positions are considered equal if they have the same row and column.
     * 
     * @param other The position to compare with this position.
     * @return True if the positions have the same row and column, false otherwise.
     */
    public boolean equals(Position other) {
        return this.row == other.row && this.column == other.column;
    }
}
