package io.github.minesweeper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;

public class Cell {
    private Texture image;
    public Sprite sprite;
    public int number;
    public boolean isMine;
    private boolean isRevealed;


    public Cell(float x, float y){
        image = new Texture("cells/basic_cell.png");
        sprite = new Sprite(image);
        sprite.setPosition(x * sprite.getWidth(), y * sprite.getHeight());
    }

    public void render(SpriteBatch spriteBatch){
        sprite.draw(spriteBatch);
    }

    public void changeTexture(String newTexturePath){
        image.dispose();
        image = new Texture(newTexturePath);
        sprite.setTexture(image);
    }

    public void reveal(){
        if (isRevealed){
            return;
        }

        if (isMine){
            changeTexture("cells/mine_cell.png");
        }
        else{
            changeTexture("cells/cells" + number + ".png");
        }
        isRevealed = true;
    }

    public void flag(){
        if (isRevealed){
            return;
        }

        changeTexture("cells/flagged_cell.png");
    }
}
