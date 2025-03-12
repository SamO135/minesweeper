package io.github.minesweeper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Grid {
    private final Cell[][] grid;
    private final int gridWidth;
    private final int gridHeight;

    public Grid(int width, int height, int numMines) {
        grid = new Cell[width][height];
        gridWidth = width;
        gridHeight = height;
        createGrid(width, height);
        placeMines(numMines);
    }

    public void createGrid(int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Cell(x, y);
            }
        }
    }

    public void placeMines(int numMines) {
        if (numMines > gridWidth * gridHeight) {
            numMines = gridWidth * gridHeight;
        }
        while (numMines > 0) {
            int randX = (int) (Math.random() * gridWidth);
            int randY = (int) (Math.random() * gridHeight);
            if (!grid[randX][randY].isMine) {
                Cell cell = grid[randX][randY];
                cell.isMine = true;
                incrementNeighbours(grid, cell);
                numMines--;
            }
        }
    }

    private void incrementNeighbours(Cell[][] grid, Cell cell) {
        ArrayList<Cell> neighbours = getNeighbours(grid, cell);
        for (Cell neighbour : neighbours) {
            neighbour.number += 1;
        }
    }

    private ArrayList<Cell> getNeighbours(Cell[][] grid, Cell cell) {
        int xIndex = worldCoordsToGridIndex(cell.sprite.getX());
        int yIndex = worldCoordsToGridIndex(cell.sprite.getY());

        ArrayList<Cell> neighbours = new ArrayList<Cell>();
        for (int x : new int[]{-1, 0, 1}) {
            for (int y : new int[]{-1, 0, 1}) {
                if (x == 0 && y == 0) {
                    continue;
                }
                int newX = xIndex + x;
                int newY = yIndex + y;
                if (newX >= 0 && newX < gridWidth && newY >= 0 && newY < gridHeight) {
                    neighbours.add(grid[xIndex + x][yIndex + y]);
                }
            }
        }
        return neighbours;
    }

    public void render(SpriteBatch spriteBatch) {
        for (Cell[] cellRow : grid) {
            for (Cell cell : cellRow) {
                cell.render(spriteBatch);
            }
        }
    }

    public Cell getCell(int xCoord, int yCoord) {
        int spriteWidth = (int) grid[0][0].sprite.getWidth();
        return grid[xCoord / spriteWidth][yCoord / spriteWidth];
    }

    /* Breadth First Search to reveal all connected cells that are not neighbouring a mine */
    public void reveal(Cell clickedCell) {
        Cell nextCell;
        Queue<Cell> queue = new ArrayDeque<>();
        queue.add(clickedCell);

        while (!queue.isEmpty()) {
            nextCell = queue.poll();
            nextCell.reveal();
            System.out.println(nextCell.number);

            if (nextCell.number == 0 && !nextCell.isMine) {
                ArrayList<Cell> neighbours = getNeighbours(grid, nextCell);
                for (Cell neighbour : neighbours){
                    if (!neighbour.isRevealed){
                        queue.add(neighbour);
                    }
                }
            }
        }
    }

    public int worldCoordsToGridIndex(float worldCoordinate) {
        return (int)(worldCoordinate / grid[0][0].sprite.getWidth());
    }
}
