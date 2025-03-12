package io.github.minesweeper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Grid {
    private final Cell[][] grid;
    private final int gridWidth;
    private final int gridHeight;

    public Grid(int width, int height, int numMines){
        grid = new Cell[width][height];
        gridWidth = width;
        gridHeight = height;
        createGrid(width, height);
        placeMines(numMines);
    }

    public void createGrid(int width, int height){
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                grid[x][y] = new Cell(x, y);
            }
        }
    }

    public void placeMines(int numMines){
        if (numMines > gridWidth * gridHeight){
            numMines = gridWidth * gridHeight;
        }
        while (numMines > 0){
            int randX = (int)(Math.random() * gridWidth);
            int randY = (int)(Math.random() * gridHeight);
            if (!grid[randX][randY].isMine){
                Cell cell = grid[randX][randY];
                cell.isMine = true;
                incrementNeighbours(grid, randX, randY);
                numMines--;
            }
        }
    }

    private void incrementNeighbours(Cell[][] grid, int cellX, int cellY){
        for (int x : new int[]{-1, 0, 1}){
            for (int y : new int[]{-1, 0, 1}){
                int newX = cellX + x;
                int newY = cellY + y;
                if (newX >=0 && newX < gridWidth && newY >= 0 && newY < gridHeight) {
                    grid[cellX + x][cellY + y].number += 1;
                }
            }
        }
    }

    public void render(SpriteBatch spriteBatch){
        for (Cell[] cellRow : grid){
            for (Cell cell : cellRow){
                cell.render(spriteBatch);
            }
        }
    }

    public Cell getCell(int xCoord, int yCoord){
        int spriteWidth = (int)grid[0][0].sprite.getWidth();
        return grid[xCoord / spriteWidth][yCoord / spriteWidth];
    }
}
