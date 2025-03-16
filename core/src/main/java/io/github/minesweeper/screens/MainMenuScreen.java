package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.minesweeper.game.MinesweeperGame;
import io.github.minesweeper.settings.Difficulty;
import io.github.minesweeper.ui.CustomButton;

public class MainMenuScreen extends MenuScreen {

    public MainMenuScreen(MinesweeperGame game) {
        super(game);

        Texture titleText = new Texture(Gdx.files.internal("text/minesweeper_text.png"));
        Image titleTextImage = new Image(new TextureRegionDrawable(new TextureRegion(titleText)));

        // create buttons
        CustomButton easyButton = createCustomButton("buttons/easy/easy_text.atlas", this::onEasyClick);
        CustomButton normalButton = createCustomButton("buttons/normal/normal_text.atlas", this::onNormalClick);
        CustomButton hardButton = createCustomButton("buttons/hard/hard_text.atlas", this::onHardClick);

        float textSize = .05f; // relative to viewport size
        float spacing = .1f; // relative to viewport size
        Table table = new Table();
        table.setFillParent(true);
        table.add(titleTextImage).width(uiWidth * .9f).height(uiHeight * .082f);
        table.getCell(titleTextImage).expand().top().padTop(uiHeight * .1f);
        table.row();
        table.add(easyButton).width(uiWidth * textSize * 4).height(uiHeight * textSize);
        table.getCell(easyButton).padBottom(uiHeight * spacing);
        table.row();
        table.add(normalButton).width(uiWidth * textSize * 6).height(uiHeight * textSize);
        table.getCell(normalButton).padBottom(uiHeight * spacing);
        table.row();
        table.add(hardButton).width(uiWidth * textSize * 4).height(uiHeight * textSize);
        table.getCell(hardButton).padBottom(uiHeight * .2f);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void onEasyClick() {
        game.difficulty = Difficulty.EASY;
        game.setScreen(new GameScreen(game));
    }

    private void onNormalClick() {
        game.difficulty = Difficulty.MEDIUM;
        game.setScreen(new GameScreen(game));
    }

    private void onHardClick() {
        game.difficulty = Difficulty.HARD;
        game.setScreen(new GameScreen(game));
    }
}
