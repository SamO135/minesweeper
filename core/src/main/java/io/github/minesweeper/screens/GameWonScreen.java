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

public class GameWonScreen extends MenuScreen {
    private final Texture dimTexture;

    public GameWonScreen(MinesweeperGame game) {
        super(game);

        // create dimming texture
        dimTexture = new Texture((Gdx.files.internal("vfx/dimmer.png")));

        // create win text
        Texture winTexture = new Texture(Gdx.files.internal("text/win_text.png"));
        Image winTextImage = new Image(new TextureRegionDrawable(new TextureRegion(winTexture)));

        // create play again button
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/play_again_button/play_again_text.atlas"));
        CustomButton playAgainButton = new CustomButton(buttonAtlas, this::onPlayAgainClick);

        // Create UI Table
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

    public void dispose() {
        super.dispose();
        dimTexture.dispose();
    }

    private void onPlayAgainClick() {
        game.setScreen(new GameScreen(game));
    }
}
