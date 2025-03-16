package io.github.minesweeper.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.minesweeper.screens.MainMenuScreen;
import io.github.minesweeper.settings.GameSettings;

public class MinesweeperGame extends Game {
    SpriteBatch spriteBatch;
    public Grid grid;
    public GameSettings settings;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();

        // load settings
        settings = GameSettings.getInstance();
        settings = settings.loadSettings();

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    public void exitGame() {
        settings.saveSettings();
        Gdx.app.exit();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        settings.saveSettings();
    }
}
