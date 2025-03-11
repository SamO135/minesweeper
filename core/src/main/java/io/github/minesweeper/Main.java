package io.github.minesweeper;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    private Texture image;
    OrthographicCamera camera;
    FitViewport viewport;
    Grid grid;

    @Override
    public void create(){
        int gridWidth = 10;
        int gridHeight = 10;
        spriteBatch = new SpriteBatch();
        grid = new Grid(gridWidth, gridHeight, 1);

        camera = new OrthographicCamera();
        camera.setToOrtho(false,gridWidth*32, gridHeight*32);

        viewport = new FitViewport(gridWidth*32, gridHeight*32);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown (int x, int y, int pointer, int button){
                Vector3 worldCoordinates = viewport.unproject(new Vector3(x, y, 0));
                System.out.println(worldCoordinates.x + ", " + worldCoordinates.y);
                return true;
            }
        });
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
        image.dispose();
    }
}
