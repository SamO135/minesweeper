package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.minesweeper.game.MinesweeperGame;
import io.github.minesweeper.ui.CustomButton;

public class MainMenuScreen implements Screen {
    private final MinesweeperGame game;
    private final SpriteBatch spriteBatch;
    private final Stage stage;
    OrthographicCamera camera;
    FitViewport viewport;

    public MainMenuScreen(MinesweeperGame game) {
        this.game = game;
        spriteBatch = new SpriteBatch();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);


        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/start_button/start_button.atlas"));
        CustomButton startButton = new CustomButton(buttonAtlas, this::onStartClick);

        Table table = new Table();
        table.setFillParent(true);
        table.add(startButton).width(200).height(60).pad(10);

        stage.addActor(table);
    }

    @Override
    public void show() {
        // set up camera and viewport
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        stage.getViewport().setWorldSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void render(float delta) {
        // clear screen
        ScreenUtils.clear(0, 0, 0, 1);

        // update camera and viewport
        viewport.apply();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        // render game obejcts
        spriteBatch.begin();
        spriteBatch.end();

        // render UI
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // resize the world viewport
        viewport.update(width, height);
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
    }

    private void onStartClick() {
        game.setScreen(new GameScreen(game));
    }
}
