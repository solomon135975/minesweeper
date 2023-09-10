package io.github.solomon135975;
public class Cell {
    private boolean isMine;
    private boolean isRevealed;
    private boolean isFlagged;

    private int numMines;

    private String value;
    private String display;

    public Cell() {
        this.isMine = false;
        this.isRevealed = false;
        this.isFlagged = false;

        this.numMines = 0;

        this.value = Integer.toString(numMines);
        this.display = "#";
    }

    public void setMine() {
        this.isMine = true;
        this.value = "X";
    }

    public void setRevealed() {
        if (isRevealed) {
            return;
        }
        this.isRevealed = true;
        if (numMines == 0) {
            this.display = this.value;
        }
        else if (numMines > 0) {
            this.display = Integer.toString(numMines);
        }
    }

    public void setFlagged() {
        if (isRevealed) {
            return;
        }
        if (this.isFlagged) {
            this.isFlagged = false;
            this.display = "#";
        }
        else {
            this.isFlagged = true;
            this.display = "!";
        }
    }

    public void incrMines() {
        if (!this.value.equals("X")){
            this.numMines ++;
            this.value = Integer.toString(numMines);
        }
    }

    public String getDisplay() {
        return this.display;
    }

    public String getValue() {
        return this.value;
    }

    public int getNumMines() {
        return this.numMines;
    }

    public boolean alreadyRevealed() {
        return isRevealed;
    }

    public boolean alreadyFlagged() {
        return isFlagged;
    }
}
