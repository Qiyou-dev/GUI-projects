package minesweeper;

import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import java.util.Random;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.JFrame;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener
{
    private Tile[][] board;
    private int boardSize;
    private final int mineNumber;
    
    public Board(final int mineNumber) {
        this.board = new Tile[9][9];
        this.boardSize = 9;
        this.mineNumber = mineNumber;
        final JFrame frame = new JFrame("Minesweeper");
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(3);
        frame.setLayout(new GridLayout(this.boardSize, this.boardSize));
        for (int row = 0; row < this.boardSize; ++row) {
            for (int col = 0; col < this.boardSize; ++col) {
                final Tile t = new Tile(row, col);
                t.addMouseListener((MouseListener)this);
                frame.add((Component)t);
                this.board[row][col] = t;
            }
        }
        this.placeMines();
        frame.setVisible(true);
    }
    
    public void placeMines() {
        int copy = this.mineNumber;
        final Random random = new Random();
        while (copy > 0) {
            final int a = random.nextInt(this.boardSize);
            final int b = random.nextInt(this.boardSize);
            if (this.board[a][b].getIsMine()) {
                continue;
            }
            this.board[a][b].setMine(true);
            this.updateNeighbors(a, b);
            --copy;
        }
    }
    
    public void updateNeighbors(final int row, final int col) {
        this.incrementCell(row, col + 1);
        this.incrementCell(row + 1, col);
        this.incrementCell(row, col - 1);
        this.incrementCell(row - 1, col);
        this.incrementCell(row + 1, col + 1);
        this.incrementCell(row - 1, col - 1);
        this.incrementCell(row + 1, col - 1);
        this.incrementCell(row - 1, col + 1);
    }
    
    public void incrementCell(final int a, final int b) {
        if (this.isOutOfBounds(a, b)) {
            return;
        }
        if (this.board[a][b].getIsMine()) {
            return;
        }
        if (this.board[a][b].getNumber() == 0) {
            this.board[a][b].setNumber(1);
        }
        else {
            this.board[a][b].setNumber(this.board[a][b].getNumber() + 1);
        }
    }
    
    public void revealTile(final int row, final int col) {
        if (this.isOutOfBounds(row, col)) {
            return;
        }
        if (this.board[row][col].getNumber() != 0) {
            this.board[row][col].setText(invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, this.board[row][col].getNumber()));
            this.board[row][col].setIcon((Icon)null);
            this.board[row][col].setEnabled(false);
        }
        else {
            this.board[row][col].setIcon((Icon)null);
            this.board[row][col].setEnabled(false);
            this.revealTile(row, col + 1);
            this.revealTile(row + 1, col);
            this.revealTile(row, col - 1);
            this.revealTile(row - 1, col);
            this.revealTile(row + 1, col + 1);
            this.revealTile(row - 1, col - 1);
            this.revealTile(row + 1, col - 1);
            this.revealTile(row - 1, col + 1);
        }
    }
    
    public boolean isOutOfBounds(final int row, final int col) {
        return row < 0 || col < 0 || (row >= this.boardSize || col >= this.boardSize) || !this.board[row][col].isEnabled();
    }
    
    public void checkWin() {
        boolean exploredSafeTiles = true;
        for (int i = 0; i < this.boardSize; ++i) {
            for (int j = 0; j < this.boardSize; ++j) {
                if (!exploredSafeTiles) {
                    return;
                }
                if (!this.board[i][j].getIsMine() && this.board[i][j].isEnabled()) {
                    exploredSafeTiles = false;
                }
            }
        }
        if (exploredSafeTiles) {
            this.gameOver();
            JOptionPane.showMessageDialog(null, "You won!", "Great job!", 1);
        }
    }
    
    public void youLost() {
        this.gameOver();
        JOptionPane.showMessageDialog(null, "You lost", "Better luck next time!", 1);
    }
    
    public void gameOver() {
        for (int row = 0; row < this.boardSize; ++row) {
            for (int col = 0; col < this.boardSize; ++col) {
                if (this.board[row][col].getIsMine()) {
                    this.board[row][col].setIcon((Icon)new ImageIcon("Mine.png"));
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(final MouseEvent e) {
        if (e.getButton() == 1) {
            final Tile t = (Tile)e.getComponent();
            if (t.getIsMine()) {
                this.youLost();
            }
            else {
                this.revealTile(t.x, t.y);
                this.checkWin();
            }
        }
        else if (e.getButton() == 3) {
            final Tile t = (Tile)e.getComponent();
            t.toggleFlag();
        }
    }
    
    @Override
    public void mousePressed(final MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(final MouseEvent e) {
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
    }
}
