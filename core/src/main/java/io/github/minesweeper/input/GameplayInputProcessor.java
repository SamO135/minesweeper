package io.github.minesweeper.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.minesweeper.game.Cell;
import io.github.minesweeper.game.Grid;
import io.github.minesweeper.game.MinesweeperGame;

public class GameplayInputProcessor extends InputAdapter {
    private final FitViewport viewport;
    private final Grid grid;
    private final float gridOffset;
    private final Sound cellRevealSound, multicellRevealSound, insertFlagSound, removeFlagSound;
    private final MinesweeperGame game;

    public GameplayInputProcessor(FitViewport viewport, Grid grid, float gridOffset, MinesweeperGame game) {
        this.viewport = viewport;
        this.grid = grid;
        this.gridOffset = gridOffset;
        this.game = game;

        cellRevealSound = Gdx.audio.newSound(Gdx.files.internal("sfx/cell_reveal.mp3"));
        multicellRevealSound = Gdx.audio.newSound(Gdx.files.internal("sfx/multicell_reveal.mp3"));
        insertFlagSound = Gdx.audio.newSound(Gdx.files.internal("sfx/flag.mp3"));
        removeFlagSound = Gdx.audio.newSound(Gdx.files.internal("sfx/remove_flag.mp3"));
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoordinates = viewport.unproject(new Vector3(screenX, screenY, 0));

        if ((worldCoordinates.x - gridOffset) < 0 || (worldCoordinates.x - gridOffset) > grid.getWidth() ||
            (worldCoordinates.y - gridOffset) < 0 || (worldCoordinates.y - gridOffset) > grid.getHeight()){
            return true;
        }

        int xCoord = (int) (worldCoordinates.x - gridOffset);
        int yCoord = (int) (worldCoordinates.y - gridOffset);
        Cell clickedCell = grid.getCell(xCoord, yCoord);

        switch (button) {
            case (Input.Buttons.LEFT):
                if (!clickedCell.isRevealed){
                    int numCellsRevealed = grid.reveal(clickedCell);

                    if (!game.prefs.getBoolean("muteSound", false)){
                        if (numCellsRevealed == 1){
                            cellRevealSound.play();
                        }
                        else {
                            multicellRevealSound.play();
                        }
                    }

                }
            case (Input.Buttons.RIGHT):
                if (!clickedCell.isRevealed){
                    clickedCell.toggleFlag();

                    if (!game.prefs.getBoolean("muteSound", false)){
                        if (clickedCell.isFlagged){
                            insertFlagSound.play();
                        }
                        else {
                            removeFlagSound.play();
                        }
                    }
                }
        }
        return true;
    }
}
