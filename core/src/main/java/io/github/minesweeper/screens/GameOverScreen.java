package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.minesweeper.game.MinesweeperGame;
import io.github.minesweeper.ui.CustomButton;

public class GameOverScreen extends MenuScreen {
    private final Texture dimTexture;

    public GameOverScreen(MinesweeperGame game) {
        super(game);

        // create dimming texture
        dimTexture = new Texture((Gdx.files.internal("vfx/dimmer.png")));

        // create game over text
        Texture gameOverTexture = new Texture(Gdx.files.internal("text/game_over_text.png"));
        Image gameOverImage = new Image(new TextureRegionDrawable(new TextureRegion(gameOverTexture)));

        // create play again button
        CustomButton playAgainButton = createCustomButton("buttons/play_again_button/play_again_text.atlas", this::onPlayAgainClick);

        // Create UI Table
        Table table = new Table();
        table.add(gameOverImage).width(uiWidth * 0.6f).height(uiHeight * 0.3f);
        table.getCell(gameOverImage).expand().top().padTop(uiHeight * 0.1f);
        table.row();
        table.add(playAgainButton).width(uiWidth * 0.25f).height(uiHeight * 0.1f);
        table.getCell(playAgainButton).expand().bottom().padBottom(uiHeight * 0.2f);
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        // clear screen
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // update camera and viewport
        gameViewport.apply();
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

    public void dispose() {
        super.dispose();
        dimTexture.dispose();
    }

    private void onPlayAgainClick() {
        game.setScreen(new GameScreen(game));
    }

}
