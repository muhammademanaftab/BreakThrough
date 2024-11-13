/**
 * The Doll class represents a generic game piece in the Breakthrough game.
 * This is an abstract class that provides common properties and methods
 * for all types of game pieces, such as position and capture status.
 * 
 * Specific types of game pieces, like pawns, will inherit from this class
 * and implement their own movement and capture logic.
 */
package breakthrough;

public abstract class Doll {
    protected Position position; // The current position of the game piece on the board. ---sabsi pehli condition doll kee
    protected boolean isCaptured; // Indicates whether this piece has been captured.

    /**
     * Constructs a Doll with an initial position.
     * The piece is not captured when it is first created.
     * 
     * @param position The initial position of the game piece on the board.
     */
    public Doll(Position position) {
        this.position = position;
        this.isCaptured = false; 
    }

    /**
     * Gets the current position of this game piece.
     * 
     * @return The position of the game piece.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Updates the position of this game piece to a new position.
     * 
     * @param newPosition The new position to move the game piece to new destinaiton.
     */
    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    /**
     * Checks if this game piece has been captured.
     * 
     * @return True if the piece is captured, or otherwise false .
     */
    public boolean isCaptured() {
        return isCaptured;
    }

    /**
     * Determines if this game piece can capture another piece
     * located at a specific position. This method must be implemented
     * by subclasses with specific capture logic.---yeh btayga ke kia pawn ko pakrha ja skta ha nai decision krge 
     * 
     * @param opponentPosition The position of the opponent's game piece.
     * @return True if the capture is allowed, false otherwise.
     */
    protected abstract boolean canCapture(Position opponentPosition);

    /**
     * Determines if this game piece can move to a specific position.
     * This method must be implemented by subclasses with specific movement logic.-- yeh btayga pawn move hoskta ha ya ya nai
     * 
     * @param newPosition The position to move the game piece to.
     * @return True if the move is allowed, false otherwise.
     */
    protected abstract boolean canMove(Position newPosition);
}
