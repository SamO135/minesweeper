package io.github.minesweeper.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CustomButton extends Button {

    public CustomButton(TextureAtlas buttonAtlas, ButtonClickHandler buttonClickHandler) {
        super(createButtonStyle(buttonAtlas));

        this.addListener(new ClickListener() {
            @Override
                public void clicked(InputEvent event, float x, float y) {
                    buttonClickHandler.onClick();
            }
        });
    }

    // This method sets up the ButtonStyle and associates the texture for button states
    private static ButtonStyle createButtonStyle(TextureAtlas buttonAtlas) {
        ButtonStyle style = new ButtonStyle();

        // Create a Skin object to hold the button's textures
        Skin skin = new Skin(buttonAtlas);

        style.up = skin.getDrawable("button_up");
        style.down = skin.getDrawable("button_down");

        return style;
    }
}
