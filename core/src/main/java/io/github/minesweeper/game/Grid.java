package io.github.minesweeper.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Grid {
    private final Cell[][] grid;
    private final int width;
    private final int height;
    private final int numMines;
    private int cellsRevealed;
    public boolean clickedMine;

    public Grid(int width, int height, int numMines) {
        grid = new Cell[width][height];
        this.width = width;
        this.height = height;
        this.numMines = numMines;
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
        if (numMines > width * height) {
            numMines = width * height;
        }
        while (numMines > 0) {
            int randX = (int) (Math.random() * width);
            int randY = (int) (Math.random() * height);
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
        int xIndex = (int)cell.sprite.getX();
        int yIndex = (int)cell.sprite.getY();

        ArrayList<Cell> neighbours = new ArrayList<Cell>();
        for (int x : new int[]{-1, 0, 1}) {
            for (int y : new int[]{-1, 0, 1}) {
                if (x == 0 && y == 0) {
                    continue;
                }
                int newX = xIndex + x;
                int newY = yIndex + y;
                if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                    neighbours.add(grid[newX][newY]);
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
        return grid[xCoord][yCoord];
    }

    /* Breadth First Search to reveal all connected cells that are not neighbouring a mine */
    public void reveal(Cell clickedCell) {
        if (clickedCell.isMine){
            clickedCell.reveal();
            clickedMine = true;
            return;
        }

        Cell nextCell;
        Queue<Cell> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[width][height];
        queue.add(clickedCell);
        visited[(int)clickedCell.sprite.getX()][(int)clickedCell.sprite.getY()] = true;

        while (!queue.isEmpty()) {
            nextCell = queue.poll();
            nextCell.reveal();
            cellsRevealed += 1;

            if (nextCell.number == 0 && !nextCell.isMine) {
                ArrayList<Cell> neighbours = getNeighbours(grid, nextCell);
                for (Cell neighbour : neighbours){
                    int neighbourX = (int)neighbour.sprite.getX();
                    int neighbourY = (int)neighbour.sprite.getY();
                    if (!visited[neighbourX][neighbourY] && !neighbour.isRevealed) {
                        queue.add(neighbour);
                        visited[neighbourX][neighbourY] = true;
                    }
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public int getCellsRevealed() {
        return cellsRevealed;
    }

    public int getNumMines() {
        return numMines;
    }
}
