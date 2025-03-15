package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.minesweeper.game.MinesweeperGame;
import io.github.minesweeper.ui.CustomButton;

public class GameOverScreen extends GameMenuScreen {

    public GameOverScreen(MinesweeperGame game) {
        super(game);

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

    private void onPlayAgainClick() {
        game.setScreen(new GameScreen(game));
    }

}
