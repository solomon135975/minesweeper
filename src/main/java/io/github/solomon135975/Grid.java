package io.github.solomon135975;

public class Grid {
    private int numRows;
    private int numColumns;

    private static final String mineVal = "X";

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

    public void clearAllMines() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                grid[i][j].removeMine();
            }
        }
    }

    public Cell getCellAt(int row, int column) {
        return grid[row][column];
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

            if (grid[row][column].getValue().equals((mineVal)) || grid[row][column].getNumMines() > 0) {
                return;
            }
            else if (grid[row][column].getNumMines() == 0) {
                for (int i = row -1; i < row + 2; i++) {
                    for (int j = column -1; j<column+2; j++) {
                        if ((i>=0 && i<numRows) && (j>=0 && j<numColumns) && !grid[i][j].alreadyRevealed()) {
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
        else {
            System.out.println("can't flag a cell thats revealed :/");
        }
    }

    public void setMineAtCell(int row, int column) {
        if (row >= 0 && row < numRows && column >= 0 && column < numColumns && !grid[row][column].getValue().equals(mineVal)) {
            grid[row][column].setMine();
            for (int i = row -1; i < row + 2; i++) {
                for (int j = column -1; j<column+2; j++) {
                    if ((i>=0 && i<numRows) && (j>=0 && j<numColumns) && !grid[i][j].isAMine()) {
                        grid[i][j].incrNumNeighbourMines();
                    }
                }
            }
        }
    }

    /**
     * check for mines around a cell
     */
    public int checkForMines(int row, int column) {
        if (!grid[row][column].getValue().equals(mineVal)) {
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
     * make move action
     */
    public void makeMove(int row, int column, char action) {
        if (action == 'f') {
            flagCell(row, column);
            if (grid[row][column].getDisplay().equals("!")) {
                System.out.println("flagged " + (char) ('a' + row) + column + "\n"); 
            }
            else {
                System.out.println("unflagged " + (char) ('a' + row) + column + "\n"); 
            }
        }
        else {
            revealCell(row, column);
            System.out.println("revealed cell(s)!");
        }
    } 

    /**
     * print grid
     */
    public void printGrid() {   
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

    public void printRevealedGrid() {
        System.out.print("  ");
        for (int i = 0; i<numColumns; i++) {
            System.out.print(" " + i + " ");
        }
        System.out.println("");
        for (int i = 0; i<numRows; i++) {
            char rowLabel = (char)('a' + i);
            System.out.print(rowLabel + " ");
            for (int j = 0; j<numColumns; j++) {
                System.out.print (" " + grid[i][j].getValue() + " ");
            }
            System.out.println("");
        }
    }
    
    /**
     * mine clicked 
     */
    public boolean isMineClicked(int row, int column) {
        return grid[row][column].getValue().equals(mineVal) && grid[row][column].alreadyRevealed();
    }
   
    /**
     * all mines flagged
     */
    public boolean areAllMinesFlagged() {
        if (numFlags == 0) {
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numColumns; j++) {
                    if ((grid[i][j].getValue().equals(mineVal) && !grid[i][j].alreadyFlagged()) 
                        || (!grid[i][j].getValue().equals(mineVal) && grid[i][j].alreadyFlagged())) {
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
                if((grid[i][j].getValue().equals(mineVal) && grid[i][j].alreadyRevealed()) 
                    || (!grid[i][j].getValue().equals(mineVal) && !grid[i][j].alreadyRevealed())) {
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
                if (grid[i][j].getValue().equals(mineVal) && grid[i][j].alreadyFlagged()) {
                     numMinesFlagged++;
                }
            }
        }
        return numMinesFlagged;
    }

    public int getTotNumMines() {
        return totMines;
    }

    public int getNumFlags() {
        return numFlags;
    }
}
