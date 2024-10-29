/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Muhammad Eman Aftab
 */
package breakthrough;

public abstract class Doll {
    protected Position position;
    protected boolean isCaptured;

    public Doll(Position position) {
        this.position = position;
        this.isCaptured = false;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }   

    public boolean isCaptured() {
        return isCaptured;
    }

    protected abstract boolean canCapture(Position opponentPosition);
    protected abstract boolean canMove(Position newPosition);
}
