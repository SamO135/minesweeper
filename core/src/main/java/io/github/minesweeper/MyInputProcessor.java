package io.github.minesweeper;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyInputProcessor extends InputAdapter {
    private final FitViewport viewport;
    private final Grid grid;

    public MyInputProcessor(FitViewport viewport, Grid grid) {
        this.viewport = viewport;
        this.grid = grid;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoordinates = viewport.unproject(new Vector3(screenX, screenY, 0));

        if (worldCoordinates.x < 0 || worldCoordinates.x > viewport.getWorldWidth()) {
            return true;
        }
        int xIndex = (int) worldCoordinates.x;
        int yIndex = (int) worldCoordinates.y;
        Cell clickedCell = grid.getCell(xIndex, yIndex);

        switch (button) {
            case (Input.Buttons.LEFT):
                clickedCell.reveal();
            case (Input.Buttons.RIGHT):
                clickedCell.flag();
        }
        return true;
    }
}
