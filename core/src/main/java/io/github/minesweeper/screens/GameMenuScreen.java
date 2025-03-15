package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.minesweeper.game.MinesweeperGame;

abstract class GameMenuScreen extends MenuScreen {
    protected FitViewport gameViewport;
    protected Texture dimTexture;
    protected float borderWidth, taskbarHeight, gameWidth, gameHeight;

    public GameMenuScreen(MinesweeperGame game) {
        super(game);

        int gridWidth = game.difficulty.width;
        int gridHeight = game.difficulty.height;

        // set up border size as a pecentage of grid size
        borderWidth = game.difficulty.width * .05f;
        taskbarHeight = game.difficulty.height * 0.25f;
        gameWidth = gridWidth + borderWidth*2;
        gameHeight = gridHeight + borderWidth + taskbarHeight;

        // set up camera and game viewport
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, gameWidth, gameHeight);
        this.gameViewport = new FitViewport(gameWidth, gameHeight);

        // set up ui viewport with same aspect ratio as game viewport
        float gameAspectRatio = gameHeight / gameWidth;
        FitViewport uiViewport = new FitViewport(100, 100 * gameAspectRatio);
        stage = new Stage(uiViewport);

        uiWidth = uiViewport.getWorldWidth();
        uiHeight = uiViewport.getWorldHeight();

        // create dimming texture
        dimTexture = new Texture((Gdx.files.internal("vfx/dimmer.png")));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // clear screen
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // update camera and viewport
        gameViewport.apply();
        stage.getViewport().apply();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        // render game grid in the background
        spriteBatch.begin();
        game.grid.render(spriteBatch);

        // dim background
        spriteBatch.setColor(0, 0, 0, 0.5f);
        spriteBatch.draw(dimTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        spriteBatch.end();

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
    public void dispose() {
        super.dispose();
        dimTexture.dispose();
    }

}
