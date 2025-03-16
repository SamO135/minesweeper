package io.github.minesweeper.settings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.File;

public final class GameSettings {
    private static GameSettings INSTANCE;
    private static final String SETTINGS_FILE_DIR = "config.json";

    public Difficulty difficulty;
    public boolean muteSound;

    private GameSettings() {
        difficulty = Difficulty.EASY;
        muteSound = false;
    }

    public static GameSettings getInstance() {
        if (INSTANCE == null){
            INSTANCE = new GameSettings();
        }
        return INSTANCE;
    }

    public void saveSettings() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);

        String settingsJson = json.toJson(this);

        Gdx.files.local(SETTINGS_FILE_DIR).writeString(settingsJson, false);
    }

    public GameSettings loadSettings() {
        File settingsFile = Gdx.files.local(SETTINGS_FILE_DIR).file();

        if (!settingsFile.exists()){
            return this;
        }

        Json json = new Json();
        String settingsJson = Gdx.files.local(SETTINGS_FILE_DIR).readString();
        return json.fromJson(GameSettings.class, settingsJson);
    }
}
