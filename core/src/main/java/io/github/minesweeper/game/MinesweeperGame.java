package io.github.minesweeper.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.minesweeper.screens.MainMenuScreen;
import io.github.minesweeper.settings.Difficulty;

public class MinesweeperGame extends Game {
    private OrthographicCamera camera;
    private FitViewport gameViewport;
    private FitViewport uiViewport;
    SpriteBatch spriteBatch;
    public Grid grid;
    public Difficulty difficulty;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();

        // set default difficulty
        difficulty = Difficulty.EASY;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Difficulty.EASY.width, Difficulty.EASY.height); // window size
        gameViewport = new FitViewport(Difficulty.EASY.width, Difficulty.EASY.height);  // world coordinates
        uiViewport = new FitViewport(100, 100);

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public FitViewport getGameViewport() {
        return gameViewport;
    }

    public FitViewport getUIViewport() {
        return uiViewport;
    }
}
