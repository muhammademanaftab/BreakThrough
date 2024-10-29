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
        int colDiff = Math.abs(newPosition.getColumn() - position.getColumn());

        if (isPlayerOne) {
            return (rowDiff == 1 && colDiff <= 1);
        } else {
            return (rowDiff == -1 && colDiff <= 1);
        }
    }

    @Override
    public boolean canCapture(Position opponentPosition) {
        if (isCaptured) return false;

        int rowDiff = opponentPosition.getRow() - position.getRow();
        int colDiff = Math.abs(opponentPosition.getColumn() - position.getColumn());

        if (isPlayerOne) {
            return (rowDiff == 1 && colDiff == 1);
        } else {
            return (rowDiff == -1 && colDiff == 1);
        }
    }
}
