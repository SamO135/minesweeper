package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.minesweeper.game.MinesweeperGame;
import io.github.minesweeper.ui.CustomButton;

public class GameWonScreen extends GameMenuScreen {

    public GameWonScreen(MinesweeperGame game) {
        super(game);

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

    private void onPlayAgainClick() {
        game.setScreen(new GameScreen(game));
    }
}
