package io.github.minesweeper.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.minesweeper.screens.MainMenuScreen;
import io.github.minesweeper.settings.Difficulty;

public class MinesweeperGame extends Game {
    SpriteBatch spriteBatch;
    public Grid grid;
    public Difficulty difficulty;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();

        // set default difficulty
        difficulty = Difficulty.EASY;

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
