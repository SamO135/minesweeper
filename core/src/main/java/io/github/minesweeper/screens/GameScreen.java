package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.minesweeper.game.Cell;
import io.github.minesweeper.game.MinesweeperGame;
import io.github.minesweeper.input.GameplayInputProcessor;
import io.github.minesweeper.game.Grid;

public class GameScreen implements Screen {
    private final MinesweeperGame game;
    private final SpriteBatch spriteBatch;
    private final Grid grid;
    OrthographicCamera camera;
    FitViewport viewport;

    public GameScreen(MinesweeperGame game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        grid = new Grid(10, 10, 10);
        game.grid = grid;

        // set up camera and world viewport
        this.camera = game.getCamera();
        this.camera.setToOrtho(false, grid.getGridWidth(), grid.getGridHeight());
        this.viewport = game.getViewport();
        this.viewport.setWorldSize(grid.getGridWidth(), grid.getGridHeight());


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new GameplayInputProcessor(viewport, grid));
    }

    @Override
    public void render(float delta) {
        // clear screen
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // update camera and world viewport
        viewport.apply();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        if (grid.clickedMine) {
            gameOver();
        }

        // render game objects
        spriteBatch.begin();
        grid.render(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height){
        // resize the world viewport
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose(){
        spriteBatch.dispose();
    }

    private void gameOver(){
        for (Cell[] cellRow : grid.getGrid()){
            for (Cell cell : cellRow){
                if (cell.isMine){
                    cell.reveal();
                }
            }
        }
        game.setScreen(new GameOverScreen(game));
    }
}
