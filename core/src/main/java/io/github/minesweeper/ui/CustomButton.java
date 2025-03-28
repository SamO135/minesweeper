package io.github.minesweeper.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.minesweeper.game.MinesweeperGame;

public class CustomButton extends Button {

    public CustomButton(TextureAtlas buttonAtlas, ButtonClickHandler buttonClickHandler, MinesweeperGame game) {
        super(createButtonStyle(buttonAtlas));
        Sound buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sfx/button_click.mp3"));

        this.addListener(new ClickListener() {
            @Override
                public void clicked(InputEvent event, float x, float y) {
                if (!game.prefs.getBoolean("muteSound", false)){
                    buttonClickSound.play();
                }
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
