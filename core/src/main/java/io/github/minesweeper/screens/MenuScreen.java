package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.minesweeper.game.MinesweeperGame;
import io.github.minesweeper.ui.ButtonClickHandler;
import io.github.minesweeper.ui.CustomButton;

abstract class MenuScreen implements Screen {
    protected final MinesweeperGame game;
    protected final SpriteBatch spriteBatch;
    protected OrthographicCamera camera;
    protected Stage stage;
    protected float uiWidth;
    protected float uiHeight;

    public MenuScreen(MinesweeperGame game) {
        this.game = game;
        spriteBatch = new SpriteBatch();

        // set up camera and ui viewport
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 100, 100);
        FitViewport uiViewport = new FitViewport(100, 100);
        stage = new Stage(uiViewport);

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
        stage.getViewport().apply();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        // render UI
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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

    public CustomButton createCustomButton(String atlasPath, ButtonClickHandler buttonClickHandler) {
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        return new CustomButton(buttonAtlas, buttonClickHandler);
    }

}
