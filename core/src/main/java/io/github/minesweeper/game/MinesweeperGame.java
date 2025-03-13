package io.github.minesweeper.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.minesweeper.screens.MainMenuScreen;

public class MinesweeperGame extends Game {
    OrthographicCamera camera;
    FitViewport viewport;
    SpriteBatch spriteBatch;
    public Grid grid;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600); // window size
        viewport = new FitViewport(800, 600);  // world coordinates

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

    public FitViewport getViewport() {
        return viewport;
    }
}
