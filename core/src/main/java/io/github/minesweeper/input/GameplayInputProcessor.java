package io.github.minesweeper.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.minesweeper.game.Cell;
import io.github.minesweeper.game.Grid;
import io.github.minesweeper.game.MinesweeperGame;

public class GameplayInputProcessor extends InputAdapter {
    private final FitViewport viewport;
    private final Grid grid;
    private final float gridOffset;

    public GameplayInputProcessor(FitViewport viewport, Grid grid, float gridOffset) {
        this.viewport = viewport;
        this.grid = grid;
        this.gridOffset = gridOffset;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoordinates = viewport.unproject(new Vector3(screenX, screenY, 0));

        if ((worldCoordinates.x - gridOffset) < 0 || (worldCoordinates.x - gridOffset) > grid.getWidth() ||
            (worldCoordinates.y - gridOffset) < 0 || (worldCoordinates.y - gridOffset) > grid.getHeight()){
            return true;
        }
        System.out.println(worldCoordinates.x + ", " + worldCoordinates.y);
        int xCoord = (int) (worldCoordinates.x - gridOffset);
        int yCoord = (int) (worldCoordinates.y - gridOffset);
        Cell clickedCell = grid.getCell(xCoord, yCoord);

        switch (button) {
            case (Input.Buttons.LEFT):
                if (!clickedCell.isRevealed){
                    grid.reveal(clickedCell);
                }
            case (Input.Buttons.RIGHT):
                clickedCell.toggleFlag();
        }
        return true;
    }
}
