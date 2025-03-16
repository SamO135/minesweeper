package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.minesweeper.game.Cell;
import io.github.minesweeper.game.MinesweeperGame;
import io.github.minesweeper.input.GameplayInputProcessor;
import io.github.minesweeper.game.Grid;
import io.github.minesweeper.ui.CustomButton;

public class GameScreen extends GameMenuScreen {
    private final Grid grid;

    public GameScreen(MinesweeperGame game) {
        super(game);
        gameBatch = new SpriteBatch();

        grid = new Grid(game.settings.difficulty.width, game.settings.difficulty.height, game.settings.difficulty.mines, borderWidth);
        game.grid = grid;

        CustomButton pauseButton = createCustomButton("buttons/pause/pause_button.atlas", this::onPauseClick);
        CustomButton resetButton = createCustomButton("buttons/reset/reset_button.atlas", this::onResetClick);

        float taskbarYOffset = .035f;
        float taskbarXOffset = .085f;
        Table table = new Table();
        table.setFillParent(true);
        table.add(pauseButton).width(pauseButton.getWidth() * .3f).height(pauseButton.getHeight() * .3f);
        table.getCell(pauseButton);
        table.add(resetButton).width(resetButton.getWidth() * .3f).height(resetButton.getHeight() * .3f);
        table.getCell(resetButton).expandX().right().padRight(uiViewport.getWorldWidth() * taskbarXOffset);
        table.top().left();
        table.padLeft(uiViewport.getWorldWidth() * taskbarXOffset).padTop(uiViewport.getWorldWidth() * taskbarYOffset);
        stage.addActor(table);
    }

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new GameplayInputProcessor(gameViewport, grid, borderWidth));
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    protected void renderGameLogic() {
        if (grid.clickedMine) {
            gameOver();
        }

        if (grid.getCellsRevealed() >= (grid.getWidth() * grid.getHeight()) - grid.getNumMines()){
            gameWon();
        }

        // render game grid in the background
        gameBatch.begin();
        grid.render(gameBatch);
        gameBatch.end();
    }

    private void gameOver(){
        for (Cell[] cellRow : grid.getGrid()){
            for (Cell cell : cellRow){
                if (cell.isMine){
                    cell.reveal();
                }
            }
        }
        Gdx.app.postRunnable(() -> game.setScreen(new GameOverScreen(game)));
    }

    private void gameWon() {
        Gdx.app.postRunnable(() -> game.setScreen(new GameWonScreen(game)));
    }

    private void onPauseClick() {
        game.setScreen(new PauseScreen(game, this));
    }

    private void onResetClick() {
        game.setScreen(new GameScreen(game));
    }
}
