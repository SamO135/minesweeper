package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.minesweeper.game.MinesweeperGame;

abstract class MenuScreen implements Screen {
    protected final MinesweeperGame game;
    protected final SpriteBatch spriteBatch;
    protected OrthographicCamera camera;
    protected FitViewport gameViewport;
    protected final Stage stage;
    protected float uiWidth;
    protected float uiHeight;

    public MenuScreen(MinesweeperGame game) {
        this.game = game;
        spriteBatch = new SpriteBatch();

        // set up ui viewport
        FitViewport uiViewport = game.getUIViewport();
        stage = new Stage(uiViewport);
        Gdx.input.setInputProcessor(stage);

        // set up camera and game viewport
        this.camera = game.getCamera();
        this.gameViewport = game.getGameViewport();

        uiWidth = uiViewport.getWorldWidth();
        uiHeight = uiViewport.getWorldHeight();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // clear screen
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // update camera and viewport
        gameViewport.apply();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        // render UI
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // resize the world viewport
        gameViewport.update(width, height, true);
        // resize the UI viewport
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
    }

}
