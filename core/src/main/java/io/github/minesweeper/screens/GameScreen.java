package io.github.minesweeper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.minesweeper.game.Cell;
import io.github.minesweeper.game.MinesweeperGame;
import io.github.minesweeper.input.GameplayInputProcessor;
import io.github.minesweeper.game.Grid;
import io.github.minesweeper.ui.CustomButton;

public class GameScreen extends MenuScreen {
    private final Grid grid;
    OrthographicCamera camera;
    public float borderWidth = 0.5f;
    public float taskbarHeight = 1.5f;

    public GameScreen(MinesweeperGame game) {
        super(game);

        grid = new Grid(game.difficulty.width, game.difficulty.height, game.difficulty.mines, borderWidth);
        game.grid = grid;

        float gameWidth = grid.getWidth() + borderWidth*2;
        float gameHeight = grid.getHeight() + borderWidth + taskbarHeight;

        // set up camera and game viewport
        this.camera = game.getCamera();
        this.camera.setToOrtho(false, gameWidth, gameHeight);
        this.gameViewport = game.getGameViewport();
        this.gameViewport.setWorldSize(gameWidth, gameHeight);

        CustomButton pauseButton = createCustomButton("buttons/pause/pause_button.atlas", this::gameWon);

        Table table = new Table();
        table.setFillParent(true);
        table.add(pauseButton).width(uiWidth * 0.1f).height(uiHeight * 0.1f);
        table.getCell(pauseButton).expand().top().left();
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
    public void render(float delta) {
        // clear screen
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // update camera and world viewport
        gameViewport.apply();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        if (grid.clickedMine) {
            gameOver();
        }

        if (grid.getCellsRevealed() >= (grid.getWidth() * grid.getHeight()) - grid.getNumMines()){
            gameWon();
        }

        // render game objects
        spriteBatch.begin();
        grid.render(spriteBatch);
        spriteBatch.end();

        stage.act(delta);
        stage.draw();
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

//    private void onPauseClick() {
//        game.setScreen(new PauseScreen(game));
//    }
}
