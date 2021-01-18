package minesweeper;

import javax.swing.*;

public class Tile extends JButton {
    final int x;
    final int y;
    private boolean isFlagged;
    private boolean isMine;
    private int number = 0;

    public Tile(int a, int b) {
        x = a;
        y = b;
        isFlagged = false;
        isMine = false;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void toggleFlag() {
        isFlagged = !isFlagged;
        if (isFlagged){
        this.setIcon(new ImageIcon("Flag.png"));
        } else {
            this.setIcon(null);
        }
    }

    public boolean getIsMine() {
        return isMine;
    }


    public void setMine(boolean mine) {
        isMine = mine;
    }
}
