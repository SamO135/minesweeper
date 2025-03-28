package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
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

        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(gameWidth, gameHeight, uiCamera);

        grid = new Grid(game.difficulty.width, game.difficulty.height, game.difficulty.mines, borderWidth);
        game.grid = grid;

        CustomButton pauseButton = createCustomButton("buttons/pause/pause_button.atlas", this::onPauseClick);
        CustomButton resetButton = createCustomButton("buttons/reset/reset_button.atlas", this::onResetClick);

        stage = new Stage(uiViewport);
        float buttonSize = .05f;

        Table table = new Table();
        table.setFillParent(false);
        table.top().left();
        table.setSize(game.difficulty.width, taskbarHeight);
        table.setPosition(borderWidth, gameHeight - taskbarHeight-borderWidth);

        Table taskBar = new Table();
        taskBar.setFillParent(true);
        taskBar.add(pauseButton).width(gameWidth * buttonSize).height(gameHeight * buttonSize).expandX().left();
        taskBar.add(resetButton).width(gameWidth * buttonSize).height(gameHeight * buttonSize).expandX().right();

        table.add(taskBar);
        stage.addActor(table);
    }

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new GameplayInputProcessor(gameViewport, grid, borderWidth, game));
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    protected void renderGameLogic() {
        if (grid.clickedMine) {
            gameOver();
        }

        if (grid.getTotalCellsRevealed() >= (grid.getWidth() * grid.getHeight()) - grid.getNumMines()){
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
