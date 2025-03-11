package io.github.minesweeper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Grid {
    private Cell[][] grid;
    private static int gridWidth;
    private static int gridHeight;

    public Grid(int width, int height, int numMines){
        grid = new Cell[width][height];
        gridWidth = width;
        gridHeight = height;
        createGrid(width, height);
        placeMines(numMines);
        calculateNumbers();
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
                grid[randX][randY].isMine = true;
                numMines--;
            }
        }
    }

    public void calculateNumbers(){
    }

    public void render(SpriteBatch spriteBatch){
        for (Cell[] cellRow : grid){
            for (Cell cell : cellRow){
                cell.render(spriteBatch);
            }
        }
    }
}
