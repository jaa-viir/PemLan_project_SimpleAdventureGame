package com.jaaviir.rpgtextgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MinimapPanel extends JPanel {
    private int[][] map;
    private int playerX, playerY;

    public MinimapPanel(int[][] map) {
        this.map = map;
        this.playerX = 2; 
        this.playerY = 0;
        this.setPreferredSize(new Dimension(150, 150)); // Set size of minimap
    }

    public void setPlayerPosition(int x, int y) {
        this.playerX = x;
        this.playerY = y;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = Math.min(getWidth() / map[0].length, getHeight() / map.length);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == playerY && j == playerX) {  // Note: Transposed indices
                    g.setColor(Color.RED);
                } else {
                    g.setColor(map[i][j] != 0 ? Color.GRAY : Color.BLACK); // Different colors for different areas
                }
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.WHITE);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }
}