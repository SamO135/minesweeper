package io.github.minesweeper;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    OrthographicCamera camera;
    FitViewport viewport;
    Grid grid;

    @Override
    public void create(){
        int gridWidth = 10;
        int gridHeight = 10;
        spriteBatch = new SpriteBatch();
        grid = new Grid(gridWidth, gridHeight, 10);
        camera = new OrthographicCamera();
        camera.setToOrtho(false,gridWidth*32, gridHeight*32);
        viewport = new FitViewport(gridWidth*32, gridHeight*32);
        Gdx.input.setInputProcessor(new MyInputProcessor(viewport, grid));
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true);
    }

    @Override
    public void render(){
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        viewport.apply();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        grid.render(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose(){
        spriteBatch.dispose();
    }
}
