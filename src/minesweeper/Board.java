package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Board extends JPanel implements MouseListener {

    private Tile[][] board = new Tile[9][9];
    private int boardSize = 9;
    private final int mineNumber;

    public Board(int mineNumber) {
        this.mineNumber = mineNumber;

        JFrame frame = new JFrame("Minesweeper");
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(boardSize, boardSize));

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Tile t = new Tile(row, col);
                t.addMouseListener(this);
                frame.add(t);
                board[row][col] = t;
            }
        }
        placeMines();
        frame.setVisible(true);
    }
    public void placeMines() {
        int copy = mineNumber;
        Random random = new Random();
        while (copy > 0) {
            int a = random.nextInt(boardSize);
            int b = random.nextInt(boardSize);
            if (board[a][b].getIsMine()) {
                continue;
            }
            board[a][b].setMine(true);
            //board[a][b].setIcon(new ImageIcon("Mine.png"));
            updateNeighbors(a, b);
            copy--;
        }
    }
    public void updateNeighbors(int row, int col) {
        incrementCell(row, col + 1);
        incrementCell(row + 1, col);
        incrementCell(row, col - 1);
        incrementCell(row - 1, col);
        incrementCell(row + 1, col + 1);
        incrementCell(row - 1, col - 1);
        incrementCell(row + 1, col - 1);
        incrementCell(row - 1, col + 1);
    }
    public void incrementCell(int a, int b) {
        if (isOutOfBounds(a, b)) return;
        if (board[a][b].getIsMine()) return;
        if (board[a][b].getNumber() == 0) {
            board[a][b].setNumber(1);
        } else {
            board[a][b].setNumber(board[a][b].getNumber() + 1);
        }
    }
    public void revealTile(int row, int col) {
        if (isOutOfBounds(row, col)) {
            return;
        } else if (board[row][col].getNumber() != 0) {
            board[row][col].setText(board[row][col].getNumber() + "");
            board[row][col].setEnabled(false);
        } else {
            board[row][col].setEnabled(false);
            revealTile(row, col + 1);
            revealTile(row + 1, col);
            revealTile(row, col - 1);
            revealTile(row - 1, col);
            revealTile(row + 1, col + 1);
            revealTile(row - 1, col - 1);
            revealTile(row + 1, col - 1);
            revealTile(row - 1, col + 1);
        }
    }
    public boolean isOutOfBounds(int row, int col) {
        if (row < 0 || col < 0) {
            return true;
        }
        if (row >= boardSize || col >= boardSize) {
            return true;
        }
        if (!board[row][col].isEnabled()) {
            return true;
        }
        return false;
    }
    public void checkWin() {
        boolean exploredSafeTiles = true;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (!exploredSafeTiles) {
                    return;
                } else if (!board[i][j].getIsMine() && board[i][j].isEnabled()) {
                    exploredSafeTiles = false;
                }
            }
        }
        if (exploredSafeTiles) {
            gameOver();
            JOptionPane.showMessageDialog(null,"You won!", "Great job!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void youLost() {
        gameOver();
        JOptionPane.showMessageDialog(null,"You lost", "Better luck next time!", JOptionPane.INFORMATION_MESSAGE);
    }

    public void gameOver() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col].getIsMine()) {
                    board[row][col].setIcon(new ImageIcon("Mine.png"));
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton()==1) {
            Tile t = (Tile) e.getComponent();
            if(t.getIsMine()){
                youLost();
            } else {
                revealTile(t.x, t.y);
                checkWin();
            }
        } else if (e.getButton()==3) {
            Tile t = (Tile)(e.getComponent());
            t.toggleFlag();

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

