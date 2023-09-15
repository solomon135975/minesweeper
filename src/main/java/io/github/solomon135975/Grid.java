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

        grid = new Cell[numColumns][numRows];

        for (int x = 0; x < numColumns; x++) {
            for (int y = 0; y < numRows; y++) {
                grid[x][y] = new Cell();
            }
        }
    }

    /** 
     * reveal cells 
     */
    public void revealCell(int column, int row) {
        if (!grid[column][row].alreadyRevealed() && !grid[column][row].alreadyFlagged()) {
            grid[column][row].setRevealed();

            if (grid[column][row].getValue().equals(("X")) || grid[column][row].getNumMines() > 0) {
                return;
            }
            else if (grid[column][row].getNumMines() == 0) {
                for (int x = column -1; x < column + 2; x++) {
                    for (int y = row -1; y<row+2; y++) {
                        if ((x>-1 && x<numColumns) && (y>-1 && y<numRows) && !grid[x][y].alreadyRevealed()) {
                            revealCell(x,y);
                        }
                    }
                }
            }
        }
    }

    /**
     * flag cell
     */
    public void flagCell(int column, int row) {
        if (!grid[column][row].alreadyRevealed()) {
            grid[column][row].setFlagged();
            numFlags--;
        }
    }

    /**
     * check for mines around a cell
     */
    public int checkForMines(int column, int row) {
        if (!grid[column][row].getValue().equals("X")) {
            return grid[column][row].getNumMines();
        }
        return -1;
    }

    /**
     * reset grid
     */
    public void resetGrid() {
        for (int x = 0; x < numColumns; x++) {
            for (int y = 0; y < numRows; y++) {
                grid[x][y] = new Cell();
            }
        }
    }

    /**
     * check is move valid (boolean)
     */
    public boolean checkMove(int column, int row, char action) {
        return (column >= 0) && (column < numColumns) && 
            (row >= 0) && (row < numRows) && 
            (action == 'f' || action == '\0');
    }

    /**
     * make move action
     */
    public void makeMove(int column, int row, char action) {
        if (checkMove(column, row, action)) {
            if (action == 'f') {
            flagCell(column, row);
            }
        else {
            revealCell(column, row);
            }
        }
    } 

    /**
     * print grid
     */
    public void printGrid() {   //need to check alignment
        System.out.print("  ");
        for (int i = 0; i<numRows; i++) {
            System.out.print(" " + i + " ");
        }
        System.out.println("");
        for (int x = 0; x<numColumns; x++) {
            
            char rowLabel = (char)('a' + x);
            System.out.print(rowLabel + " ");
            for (int y = 0; y<numRows; y++) {
                System.out.print (" " + grid[x][y].getDisplay() + " ");
            }
            System.out.println("");
        }
    }

    /**
     * mine clicked 
     */
    public boolean isMineClicked(int column, int row) {
        if (grid[column][row].getValue().equals("X") && grid[column][row].alreadyRevealed()) {
            return true;
        }
        return false;
    }

    /**
     * all mines flagged
     */
    public boolean allMinesFlagged() {
        if (numFlags == 0) {
            for (int x = 0; x < numColumns; x++) {
                for (int y = 0; y < numRows; y++) {
                    if ((grid[x][y].getValue().equals("X") && !grid[x][y].alreadyFlagged()) 
                        || (!grid[x][y].getValue().equals("X") && grid[x][y].alreadyFlagged())) {
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
        for (int x = 0; x < numColumns; x++) {
            for (int y = 0; y < numRows; y++) {
                if((grid[x][y].getValue().equals("X") && grid[x][y].alreadyRevealed()) 
                    || (!grid[x][y].getValue().equals("X") && !grid[x][y].alreadyRevealed())) {
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
        for (int x = 0; x < numColumns; x++) {
            for (int y = 0; y < numRows; y++) {
                if (grid[x][y].alreadyRevealed()) {
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
        for (int x = 0; x < numColumns; x++) {
            for (int y = 0; y < numRows; y++) {
                if (grid[x][y].getValue().equals("X") && grid[x][y].alreadyFlagged()) {
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
