/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Muhammad Eman Aftab
 */

package breakthrough;
public class Pawn extends Doll {
    public boolean isPlayerOne; 

    public Pawn(Position position, boolean isPlayerOne) {
        super(position);
        this.isPlayerOne = isPlayerOne;
    }

    @Override
    public boolean canMove(Position newPosition) {
        if (isCaptured) return false; 

        int rowDiff = newPosition.getRow() - position.getRow();
//        new position is the position where pawn going and position.getrow is old posiiton of the pawn
//        player one moves from row one to two if row difference is equal one then it goes forward
        int colDiff = Math.abs(newPosition.getColumn() - position.getColumn());

        if (isPlayerOne) {
            return (rowDiff == 1 && colDiff <= 1); //col diff == 0 mean same col or 1 mean 1 step ahead
        } else {
            return (rowDiff == -1 && colDiff <= 1); //for player two rowdiff is -1 bcz array is moving form high index to low index
        }
    }

    public boolean canCapture(Position opponentPosition) {
        if (isCaptured) return false;

        int rowDiff = opponentPosition.getRow() - position.getRow();
        int colDiff = Math.abs(opponentPosition.getColumn() - position.getColumn());

        if (isPlayerOne) {
            return (rowDiff == 1 && colDiff == 1); // for playerone
        } else {
            return (rowDiff == -1 && colDiff == 1); // for player two
        }
    }
}
