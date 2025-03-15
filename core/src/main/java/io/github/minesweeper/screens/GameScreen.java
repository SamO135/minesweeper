package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
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

        grid = new Grid(game.difficulty.width, game.difficulty.height, game.difficulty.mines, borderWidth);
        game.grid = grid;

        CustomButton pauseButton = createCustomButton("buttons/pause/pause_button.atlas", this::onPauseClick);

        Table table = new Table();
        table.setFillParent(true);
        table.add(pauseButton).width(pauseButton.getWidth() * .5f).height(pauseButton.getHeight() * .5f);
        table.getCell(pauseButton).expand().top().left();
        table.getCell(pauseButton).padLeft(uiViewport.getWorldWidth() * .12f).padTop(uiViewport.getWorldWidth() * .05f);
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
}
