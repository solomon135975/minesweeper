package io.github.solomon135975;

public class Grid {
    private int numRows;
    private int numColumns;

    private int totMines;
    private int numFlags;

    private Cell[][] grid;

    public Grid (int numRows, int numColumns, int totMines) {
        this.numRows = numRows;
        this.numColumns = numColumns; 
        this.totMines = totMines;
        this.numFlags = totMines;

        grid = new Cell[numRows][numColumns];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getNumRows() {
        return numRows;
    }

    /** 
     * reveal cells 
     */
    public void revealCell(int row, int column) {
        if (!grid[row][column].alreadyRevealed() && !grid[row][column].alreadyFlagged()) {
            grid[row][column].setRevealed();

            if (grid[row][column].getValue().equals(("X")) || grid[row][column].getNumMines() > 0) {
                return;
            }
            else if (grid[row][column].getNumMines() == 0) {
                for (int i = row -1; i < row + 2; i++) {
                    for (int j = column -1; j<column+2; j++) {
                        if ((i>-1 && i<numRows) && (j>-1 && j<numColumns) && !grid[i][j].alreadyRevealed()) {
                            revealCell(i,j);
                        }
                    }
                }
            }
        }
    }

    /**
     * flag cell
     */
    public void flagCell(int row, int column) {
        if (!grid[row][column].alreadyRevealed()) {
            grid[row][column].setFlagged();
            numFlags--;
        }
    }

    /**
     * check for mines around a cell
     */
    public int checkForMines(int row, int column) {
        if (!grid[row][column].getValue().equals("X")) {
            return grid[row][column].getNumMines();
        }
        return -1;
    }

    /**
     * reset grid
     */
    public void resetGrid() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    /**
     * check is move valid (boolean)
     */
    public boolean checkMove(int row, int column, char action) {
        return (column >= 0) && (column < numColumns) && 
            (row >= 0) && (row < numRows) && 
            (action == 'f' || action == '\0');
    }

    /**
     * make move action
     */
    public void makeMove(int row, int column, char action) {
        if (checkMove(row, column, action)) {
            if (action == 'f') {
            flagCell(row, column);
            }
        else {
            revealCell(row, column);
            }
        }
    } 

    /**
     * print grid
     */
    public void printGrid() {   //need to check alignment
        System.out.print("  ");
        for (int i = 0; i<numColumns; i++) {
            System.out.print(" " + i + " ");
        }
        System.out.println("");
        for (int i = 0; i<numRows; i++) {
            char rowLabel = (char)('a' + i);
            System.out.print(rowLabel + " ");
            for (int j = 0; j<numColumns; j++) {
                System.out.print (" " + grid[i][j].getDisplay() + " ");
            }
            System.out.println("");
        }
    }

    /**
     * mine clicked 
     */
    public boolean isMineClicked(int row, int column) {
        if (grid[row][column].getValue().equals("X") && grid[row][column].alreadyRevealed()) {
            return true;
        }
        return false;
    }

    /**
     * all mines flagged
     */
    public boolean allMinesFlagged() {
        if (numFlags == 0) {
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numColumns; j++) {
                    if ((grid[i][j].getValue().equals("X") && !grid[i][j].alreadyFlagged()) 
                        || (!grid[i][j].getValue().equals("X") && grid[i][j].alreadyFlagged())) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * all safe cells revealed
     */
    public boolean allSafeCellsRevealed() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if((grid[i][j].getValue().equals("X") && grid[i][j].alreadyRevealed()) 
                    || (!grid[i][j].getValue().equals("X") && !grid[i][j].alreadyRevealed())) {
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * num revealed cells
     */
    public int getNumRevealedCells() {
        int numCellsRevealed = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (grid[i][j].alreadyRevealed()) {
                    numCellsRevealed++;
                }
            }
        }
        return numCellsRevealed;
    }

    /**
     * num mines flagged
     */
    public int numMinesFlagged() {
        int numMinesFlagged = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (grid[i][j].getValue().equals("X") && grid[i][j].alreadyFlagged()) {
                     numMinesFlagged++;
                }
            }
        }
        return numMinesFlagged;
    }

    public int getTotNumMines() {
        return totMines;
    }

    public int getTotNumFlags() {
        return numFlags;
    }
}
