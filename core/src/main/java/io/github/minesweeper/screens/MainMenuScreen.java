package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

        // create buttons
        CustomButton settingsButton = createCustomButton("buttons/settings/settings_text.atlas", this::onSettingsClick);
        CustomButton exitButton = createCustomButton("buttons/exit/exit_text.atlas", game::exitGame);
        CustomButton startButton = createCustomButton("buttons/start/start_text.atlas", this::onStartClick);

        float textSize = .05f; // relative to viewport size
        float spacing = .075f; // relative to viewport size
        Table table = new Table();
        table.setFillParent(true);
        table.add(titleTextImage).width(uiWidth * .9f).height(uiHeight * .082f);
        table.getCell(titleTextImage).expand().top().padTop(uiHeight * .1f);
        table.row();
        table.add(startButton).width(uiWidth * textSize * 5).height(uiHeight * textSize);
        table.getCell(startButton).padBottom(uiHeight * spacing);
        table.row();
        table.add(settingsButton).width(uiWidth * textSize * 8).height(uiHeight * textSize);
        table.getCell(settingsButton).padBottom(uiHeight * spacing);
        table.row();
        table.add(exitButton).width(uiWidth * textSize * 4).height(uiHeight * textSize);
        table.getCell(exitButton).padBottom(uiHeight * .2f);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    private void onStartClick() {
        game.setScreen(new DifficultySelectScreen(game));
    }

    private void onSettingsClick() {
        game.setScreen(new SettingsScreen(game, this));
    }
}
