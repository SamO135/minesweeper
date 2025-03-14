package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

        stage = new Stage(new FitViewport(0, 0));
        Gdx.input.setInputProcessor(stage);

        Texture titleText = new Texture(Gdx.files.internal("text/minesweeper_text.png"));
        Image titleTextImage = new Image(new TextureRegionDrawable(new TextureRegion(titleText)));

        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/start_button/start_button.atlas"));
        CustomButton startButton = new CustomButton(buttonAtlas, this::onStartClick);

        Table table = new Table();
        table.setFillParent(true);
        table.add(titleTextImage).expand().top().padTop(32);
        table.row();
        table.add(startButton).expand().bottom().padBottom(96);

        stage.addActor(table);
    }

    @Override
    public void show() {
        // set up camera and viewport
        int worldSize = 352;
        this.viewport = game.getGameViewport();
        this.viewport.setWorldSize(worldSize, worldSize);
        this.camera = game.getCamera();
        this.camera.setToOrtho(false,worldSize, worldSize);
        stage.getViewport().setWorldSize(worldSize, worldSize);
    }

    public void render(float delta) {
        // clear screen
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

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
        viewport.update(width, height, true);
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
