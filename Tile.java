package minesweeper;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Tile extends JButton
{
    final int x;
    final int y;
    private boolean isFlagged;
    private boolean isMine;
    private int number;
    
    public Tile(final int a, final int b) {
        this.number = 0;
        this.x = a;
        this.y = b;
        this.isFlagged = false;
        this.isMine = false;
    }
    
    public int getNumber() {
        return this.number;
    }
    
    public void setNumber(final int number) {
        this.number = number;
    }
    
    public void toggleFlag() {
        this.isFlagged = !this.isFlagged;
        if (this.isFlagged) {
            this.setIcon(new ImageIcon("Flag.png"));
        }
        else {
            this.setIcon(null);
        }
    }
    
    public boolean getIsMine() {
        return this.isMine;
    }
    
    public void setMine(final boolean mine) {
        this.isMine = mine;
    }
}
