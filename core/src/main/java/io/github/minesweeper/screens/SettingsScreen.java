package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.minesweeper.game.MinesweeperGame;
import io.github.minesweeper.ui.Checkbox;
import io.github.minesweeper.ui.CustomButton;

public class SettingsScreen extends MenuScreen {
    private MenuScreen menuScreen;

    public SettingsScreen(MinesweeperGame game, MenuScreen menuScreen) {
        super(game);
        this.menuScreen = menuScreen;

        Texture menuTexture = new Texture(Gdx.files.internal("menu.png"));
        Image menuImage = new Image(new TextureRegionDrawable(new TextureRegion(menuTexture)));
        menuImage.setSize(uiWidth, uiHeight);
        menuImage.setPosition((uiViewport.getWorldWidth() / 2) - (menuImage.getWidth() / 2), (uiViewport.getWorldHeight() / 2) - (menuImage.getHeight() / 2));

        TextureAtlas settingsAtlas = new TextureAtlas(Gdx.files.internal("buttons/settings/settings_text.atlas"));
        Image settingsTextImage = new Image(new TextureRegionDrawable(settingsAtlas.findRegion("button_up")));

        Texture soundTexture = new Texture(Gdx.files.internal("text/sound_text.png"));
        Image soundTextImage = new Image(new TextureRegionDrawable(new TextureRegion(soundTexture)));

        Checkbox soundCheckbox = createCheckbox("buttons/checkbox/checkbox.atlas", this::onSoundCheckboxClick);
        CustomButton backButton = createCustomButton("buttons/back/back_text.atlas", this::onBackClick);

        float textSize = .05f;
        float spacing = .1f;
        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.top();
        buttonTable.add(settingsTextImage).width(uiWidth * .03f * 8).height(uiHeight * .03f);
        buttonTable.getCell(settingsTextImage).padTop(uiHeight * .2f);
        buttonTable.row();

        // sound option
        Table soundOption = new Table();
        soundOption.add(soundTextImage).width(uiWidth * textSize * 5).height(uiHeight * textSize);
        soundOption.add(soundCheckbox).width(uiWidth * textSize * 1).height(uiHeight * textSize);
        soundOption.getCell(soundCheckbox).padLeft(uiWidth * .2f);
        buttonTable.add(soundOption).padTop(uiHeight * .1f).padRight(uiWidth * .1f);
        buttonTable.row();

        // back button
        buttonTable.add(backButton).width(uiWidth * textSize * 4).height(uiHeight * textSize);
        buttonTable.getCell(backButton).padTop(uiHeight * .3f);

        stage.addActor(menuImage);
        stage.addActor(buttonTable);

        // match ui with the game settings
        soundCheckbox.setChecked(!game.prefs.getBoolean("muteSound", false));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void onSoundCheckboxClick() {
        boolean muteSound = game.prefs.getBoolean("muteSound", false);
        game.prefs.putBoolean("muteSound", !muteSound);
    }

    private void onBackClick() {
        game.setScreen(menuScreen);
    }
}
