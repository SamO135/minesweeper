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

public class PauseScreen extends GameMenuScreen{
    private final GameScreen gameScreen;

    public PauseScreen(MinesweeperGame game, GameScreen gameScreen) {
        super(game);

        this.gameScreen = gameScreen;

        CustomButton resumeButton = createCustomButton("buttons/resume/resume_text.atlas", this::onResumeClick);
        CustomButton mainMenuButton = createCustomButton("buttons/main_menu/main_menu_text.atlas", this::onMainMenuClick);
        CustomButton settingsButton = createCustomButton("buttons/settings/settings_text.atlas", this::onSettingsClick);

        float textSize = .05f; // relative to viewport size
        float spacing = .2f; // relative to viewport size
        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.add(resumeButton).width(uiWidth * textSize * 6).height(uiHeight * textSize);
        buttonTable.getCell(resumeButton).padBottom(uiHeight * spacing);
        buttonTable.row();
        buttonTable.add(settingsButton).width(uiWidth * textSize * 8).height(uiHeight * textSize);
        buttonTable.getCell(settingsButton).padBottom(uiHeight * spacing);
        buttonTable.row();
        buttonTable.add(mainMenuButton).width(uiWidth * textSize * 9).height(uiHeight * textSize);
        buttonTable.getCell(mainMenuButton).padBottom(uiHeight * spacing);
        buttonTable.setY(buttonTable.getY() - (uiHeight * spacing / 2));

//        stage.addActor(windowImage);
        stage.addActor(buttonTable);
    }

    public void onResumeClick() {
        game.setScreen(gameScreen);
    }
    public void onMainMenuClick() {
        game.setScreen(new MainMenuScreen(game));
    }
    public void onSettingsClick() {}
}
