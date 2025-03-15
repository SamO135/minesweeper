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

public class MainMenuScreen extends MenuScreen {

    public MainMenuScreen(MinesweeperGame game) {
        super(game);

        Texture titleText = new Texture(Gdx.files.internal("text/minesweeper_text.png"));
        Image titleTextImage = new Image(new TextureRegionDrawable(new TextureRegion(titleText)));

        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/start_button/start_button.atlas"));
        CustomButton startButton = new CustomButton(buttonAtlas, this::onStartClick);

        Table table = new Table();
        table.setFillParent(true);
        table.add(titleTextImage).width(uiHeight * .9f).height(uiHeight * .082f);
        table.getCell(titleTextImage).expand().top().padTop(uiHeight * .1f);
        table.row();
        table.add(startButton).width(uiWidth * .3f).height(uiHeight * .06f);
        table.getCell(startButton).expand().bottom().padBottom(uiHeight * .25f);

        stage.addActor(table);
    }

    private void onStartClick() {
        game.setScreen(new GameScreen(game));
    }
}
