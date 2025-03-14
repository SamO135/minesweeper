package io.github.minesweeper.settings;

public enum Difficulty {
    EASY(10, 10, 5),
    MEDIUM(16, 16, 40),
    HARD(24, 24, 99);

    public final int width, height, mines;

    Difficulty(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
    }
}
