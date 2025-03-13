package io.github.minesweeper.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cell {
    private Texture image;
    public Sprite sprite;
    public int number;
    public boolean isMine;
    public boolean isRevealed;
    private boolean isFlagged;


    public Cell(float x, float y){
        image = new Texture("cells/basic_cell.png");
        sprite = new Sprite(image);
        sprite.setSize(1, 1);
        sprite.setPosition(x, y);
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

    public void toggleFlag(){
        if (isRevealed){
            return;
        }

        if (!isFlagged){
            changeTexture("cells/flagged_cell.png");
            isFlagged = true;
        }
        else{
            changeTexture("cells/basic_cell.png");
            isFlagged = false;
        }
    }
}
