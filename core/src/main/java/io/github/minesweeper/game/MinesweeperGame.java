package io.github.minesweeper.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.minesweeper.screens.MainMenuScreen;
import io.github.minesweeper.settings.Difficulty;

public class MinesweeperGame extends Game {
    SpriteBatch spriteBatch;
    public Grid grid;
    public Difficulty difficulty;
    public Preferences prefs;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();

        // load settings
        prefs = Gdx.app.getPreferences("GameSettings");

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    public void exitGame() {
        Gdx.app.exit();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
