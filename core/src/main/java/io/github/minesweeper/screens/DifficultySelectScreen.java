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

public class DifficultySelectScreen extends MenuScreen{

    public DifficultySelectScreen(MinesweeperGame game) {
        super(game);

        Texture titleText = new Texture(Gdx.files.internal("text/minesweeper_text.png"));
        Image titleTextImage = new Image(new TextureRegionDrawable(new TextureRegion(titleText)));

        // create buttons
        CustomButton easyButton = createCustomButton("buttons/easy/easy_text.atlas", this::onEasyClick);
        CustomButton normalButton = createCustomButton("buttons/normal/normal_text.atlas", this::onNormalClick);
        CustomButton hardButton = createCustomButton("buttons/hard/hard_text.atlas", this::onHardClick);
        CustomButton backButton = createCustomButton("buttons/back/back_text.atlas", this::onBackClick);

        float textSize = .05f; // relative to viewport size
        float spacing = .075f; // relative to viewport size
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
        table.getCell(hardButton).padBottom(uiHeight * spacing);
        table.row();
        table.add(backButton).width(uiWidth * textSize * 4).height(uiHeight * textSize);
        table.getCell(backButton).padBottom(uiHeight * .08f);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    private void onEasyClick() {
        game.settings.difficulty = Difficulty.EASY;
        game.setScreen(new GameScreen(game));
    }

    private void onNormalClick() {
        game.settings.difficulty = Difficulty.MEDIUM;
        game.setScreen(new GameScreen(game));
    }

    private void onHardClick() {
        game.settings.difficulty = Difficulty.HARD;
        game.setScreen(new GameScreen(game));
    }

    private void onBackClick() {
        game.setScreen(new MainMenuScreen(game));
    }
}
