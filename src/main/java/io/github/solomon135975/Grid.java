package io.github.solomon135975;

public class Grid {
    int numRows;
    int numColumns;

    int totMines;
    int numFlags;

    public Grid (int numRows, int numColumns, int totMines) {
        this.numRows = numRows;
        this.numColumns = numColumns; 
        this.totMines = totMines;
        this.numFlags = totMines;

        Cell[][] grid = new Cell[numColumns][numRows];
         
    }
}
