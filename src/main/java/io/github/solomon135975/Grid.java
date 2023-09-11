package io.github.solomon135975;

public class Grid {
    private int numRows;
    private int numColumns;

    private int totMines;
    private int numFlags;
    private int numSafeCells;

    private Cell[][] grid;

    public Grid (int numRows, int numColumns, int totMines) {
        this.numRows = numRows;
        this.numColumns = numColumns; 
        this.totMines = totMines;
        this.numFlags = totMines;
        this.numSafeCells = numRows*numColumns - totMines;

        grid = new Cell[numColumns][numRows];

        for (int y = 0; y < numColumns; y++) {
            for (int x = 0; x < numRows; x++) {
                grid[y][x] = new Cell();
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
                for (int y = column -1; y < column + 2; y++) {
                    for (int x = row -1; x<row+2; x++) {
                        if ((y>-1 && y<numColumns) && (x>-1 && x<numRows) && !grid[y][x].alreadyRevealed()) {
                            revealCell(y,x);
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
        for (int y = 0; y < numColumns; y++) {
            for (int x = 0; x < numRows; x++) {
                grid[y][x] = new Cell();
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
        for (int y = 0; y<numColumns; y++) {
            
            char rowLabel = (char)('a' + y);
            System.out.print(rowLabel + " ");
            for (int x = 0; x<numRows; x++) {
                System.out.print (" " + grid[y][x].getDisplay() + " ");
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
            for (int y = 0; y < numColumns; y++) {
                for (int x = 0; x < numRows; x++) {
                    if ((grid[y][x].getValue().equals("X") && !grid[y][x].alreadyFlagged()) 
                        || (!grid[y][x].getValue().equals("X") && grid[y][x].alreadyFlagged())) {
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
        for (int y = 0; y < numColumns; y++) {
            for (int x = 0; x < numRows; x++) {
                if((grid[y][x].getValue().equals("X") && grid[y][x].alreadyRevealed()) 
                    || (!grid[y][x].getValue().equals("X") && !grid[y][x].alreadyRevealed())) {
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
        for (int y = 0; y < numColumns; y++) {
            for (int x = 0; x < numRows; x++) {
                if (grid[y][x].alreadyRevealed()) {
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
        for (int y = 0; y < numColumns; y++) {
            for (int x = 0; x < numRows; x++) {
                if (grid[y][x].getValue().equals("X") && grid[y][x].alreadyFlagged()) {
                     numMinesFlagged++;
                }
            }
        }
        return numMinesFlagged;
    }
}
