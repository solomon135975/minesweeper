package io.github.solomon135975;
public class Cell {

    private boolean isMine;
    private boolean isRevealed;
    private boolean isFlagged;

    private int numMines;

    private String value;  //value of the cell, determines if it's a mine or not
    private String display;  //what displays in the actual grid

    public Cell() {
        this.isMine = false;
        this.isRevealed = false;
        this.isFlagged = false;

        this.numMines = 0;

        this.value = Integer.toString(numMines);
        this.display = "#";
    }

    /**
     * sets a cell's value to a mine
     */
    public void setMine() {
        this.isMine = true;
        this.value = "X";
    }

    /**
     * sets a cell's status to revealed
     * does this by changing the display value to the actual value of the cell
     */
    public void setRevealed() {
        if (isRevealed) {
            return;
        }
        this.isRevealed = true;

        if (numMines == 0 || isMine) {
            this.display = this.value;
        }
        else if (numMines > 0) {
            this.display = Integer.toString(numMines);
        } 

    }

    /**
     * sets a cell to be flagged
     * display value = !
     * if cell is already flagged, the display becomes a # again, effectively unflagging it
     */
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

    /**
     * increments a non-mine cells value/number of mines around it
     */
    public void incrNumNeighbourMines() {
        if (!isMine){
            this.numMines ++;
            this.value = Integer.toString(numMines);
        }
    }

    /**
     * gets the display value of the cell
     * @return display value
     */
    public String getDisplay() {
        return this.display;
    }

    /**
     * returns value of cell
     * @return value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * returns number of mines are around the non-mine cell
     * @return
     */
    public int getNumMines() {
        return this.numMines;
    }

    /**
     * returns a boolean which states whether or not a cell is revealed
     * @return
     */
    public boolean alreadyRevealed() {
        return isRevealed;
    }

    /**
     * returns boolean of whether or not the cell is already flagged
     * @return
     */
    public boolean alreadyFlagged() {
        return isFlagged;
    }

    /**
     * returns boolean of whether cell is a mine or not
     * @return
     */
    public boolean isAMine() {
        return isMine;
    }

    /**
     * removes the mine value of a cell, turning it into a non-mine
     * essentially resets the cell
     * intended to only be used before mine generation to ensure that the grid is clean
     * if to be used within gameplay, need to implement a decrementing mine factor or something to redo mine-neighbour incrementing
     */
    public void removeMine() {
        if (this.isMine) {
            this.isMine = false;
            this.value = Integer.toString(numMines);
        }
    }
}
