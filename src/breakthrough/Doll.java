/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Muhammad Eman Aftab
 */

package breakthrough;

public class Doll {
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

    public void capture() {
        this.isCaptured = true;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public boolean canMove(Position newPosition) {
        return !isCaptured; 
    }
}
