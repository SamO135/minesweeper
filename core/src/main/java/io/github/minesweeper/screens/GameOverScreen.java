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

public class GameOverScreen implements Screen {
    private final MinesweeperGame game;
    private final SpriteBatch spriteBatch;
    private final Texture gameOverTexture;
    OrthographicCamera camera;
    FitViewport viewport;

    private final Stage stage;

    public GameOverScreen(MinesweeperGame game) {
        this.game = game;
        spriteBatch = new SpriteBatch();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        gameOverTexture = new Texture(Gdx.files.internal("text/game_over_text.png"));
        Image gameOverImage = new Image(new TextureRegionDrawable(new TextureRegion(gameOverTexture)));

        // create play again button
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/play_again_button/play_again_text.atlas"));
        CustomButton playAgainButton = new CustomButton(buttonAtlas, this::onPlayAgainClick);

        // Create UI Table
        Table table = new Table();
        table.add(gameOverImage).width(8).height(4).padBottom(2);
        table.row();
        table.add(playAgainButton).width(5).height(2);
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void show() {
        // set up camera and viewport
        this.camera = game.getCamera();
        this.camera.setToOrtho(false, game.grid.getWidth(), game.grid.getHeight());
        this.viewport = game.getViewport();
        this.viewport.setWorldSize(game.grid.getWidth(), game.grid.getHeight());

        // set up UI viewport
        stage.getViewport().setWorldSize(game.grid.getWidth(), game.grid.getHeight());
    }

    @Override
    public void render(float delta) {
        // clear screen
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // update camera and viewport
        viewport.apply();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        // render game grid in the background
        spriteBatch.begin();
        game.grid.render(spriteBatch);
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
        gameOverTexture.dispose();
        stage.dispose();
    }

    private void onPlayAgainClick() {
        game.setScreen(new GameScreen(game));
    }

}
