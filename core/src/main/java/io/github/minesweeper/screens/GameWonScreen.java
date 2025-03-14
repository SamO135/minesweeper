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

public class GameWonScreen implements Screen {
    private final SpriteBatch spriteBatch;
    private final MinesweeperGame game;
    private final Stage stage;
    private final Texture dimTexture;
    private OrthographicCamera camera;
    private FitViewport gameViewport;

    public GameWonScreen(MinesweeperGame game) {
        this.game = game;
        spriteBatch = new SpriteBatch();

        FitViewport uiViewport = game.getUIViewport();
        stage = new Stage(uiViewport);
        Gdx.input.setInputProcessor(stage);

        // create dimming texture
        dimTexture = new Texture((Gdx.files.internal("vfx/dimmer.png")));

        // create win text
        Texture winTexture = new Texture(Gdx.files.internal("text/win_text.png"));
        Image winTextImage = new Image(new TextureRegionDrawable(new TextureRegion(winTexture)));

        // create play again button
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/play_again_button/play_again_text.atlas"));
        CustomButton playAgainButton = new CustomButton(buttonAtlas, this::onPlayAgainClick);

        // Create UI Table
        float uiWidth = uiViewport.getWorldWidth();
        float uiHeight = uiViewport.getWorldHeight();
        Table table = new Table();
        table.add(winTextImage).width(uiWidth * 0.45f).height(uiHeight * 0.15f);
        table.getCell(winTextImage).expand().top().padTop(uiHeight * 0.2f);
        table.row();
        table.add(playAgainButton).width(uiWidth * 0.25f).height(uiHeight * 0.1f);
        table.getCell(playAgainButton).expand().bottom().padBottom(uiHeight * 0.2f);
        table.setFillParent(true);
        stage.addActor(table);

    }

    @Override
    public void show() {
        // set up camera and world viewport
        this.camera = game.getCamera();
        this.camera.setToOrtho(false, game.grid.getWidth(), game.grid.getHeight());
        this.gameViewport = game.getGameViewport();
        this.gameViewport.setWorldSize(game.grid.getWidth(), game.grid.getHeight());

        // set up UI viewport
//        stage.getViewport().setWorldSize(game.grid.getWidth(), game.grid.getHeight());
    }

    @Override
    public void render(float delta) {
        // clear screen
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // update camera and viewport
        gameViewport.apply();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        // render grid
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

    private void onPlayAgainClick() {
        game.setScreen(new GameScreen(game));
    }
}
