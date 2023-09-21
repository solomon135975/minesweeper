package io.github.solomon135975;

/**
 * this is the Grid class which is the actual grid of row by col cells 
 * the grid holds cells in a 2D array, the cells may or may not be a mine
 * the class has methods which allow one to interact with the cells and the grid itself
 * also has methods to print the current grid display as well as the capability to print the revealed grid
 */
public class Grid {
    private int numRows;
    private int numColumns;

    private static final String mineVal = "X";   //the value of a mine cell

    private int totMines;  //total number of mines in the grid
    private int numFlags;  //total number of flags allowed to be used (= num of mines)

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

    /**
     * uses the removeMine() method in Cell class to clear all the mines in a grid
     * this is only used when generating the mines to ensure a clean slate grid
     */
    public void clearAllMines() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                grid[i][j].removeMine();
            }
        }
    }

    /**
     * returns a cell at the given row/column position
     * @param row
     * @param column
     * @return
     */
    public Cell getCellAt(int row, int column) {
        return grid[row][column];
    }

    /**
     * returns the number of columns for the grid
     */
    public int getNumColumns() {
        return numColumns;
    }

    /**
     * returns the number of rows for the grid
     * @return
     */
    public int getNumRows() {
        return numRows;
    }

    /** 
     * reveal cells 
     * if cell is already revealed or if cell is flagged nothing happens, otherwise cell is set to revealed
     * 
     * if the cell is a mine or the number of mines around the nonmine cell > 0, return 
     * 
     * if the number of mines around the cell is 0, then cascade reveal
     * recursively reveal all cells around the 0 cell
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
     * sets a cell in the grid as flaggeed as long as it isn't already revealed
     */
    public void flagCell(int row, int column) {
        if (!grid[row][column].alreadyRevealed()) {
            grid[row][column].setFlagged();
            if (grid[row][column].alreadyFlagged()) {
                numFlags--;
            }
            else {
                numFlags++;
            }
        }
    }

    /**
     * sets a cell as a mine and calls the incrNumNeighbourMines() method from Cell to increase the values of the cells around it
     * this is used in the mine generation method of MineGenerator class
     * @param row
     * @param column
     */
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
     * this returns the number of mines around a cell as long as as it isn't a mine
     */
    public int checkForMines(int row, int column) {
        if (!grid[row][column].getValue().equals(mineVal)) {
            return grid[row][column].getNumMines();
        }
        return -1;
    }

    /**
     * reset grid
     * resets the grid by initialising a new cell in each position of the grid
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
     * this actually processes the move as well as provides user feedback for what move was made
     * calls the flag or reveal function
     */
    public void makeMove(int row, int column, char action) {
        if (action == 'f') {
            flagCell(row, column);
            if (grid[row][column].getDisplay().equals("!")) {
                System.out.println("flagged " + (char) ('a' + row) + column + "\n"); 
            }
            else if (grid[row][column].getDisplay().equals("#")) {
                System.out.println("unflagged " + (char) ('a' + row) + column + "\n"); 
            }
            else if (grid[row][column].alreadyRevealed()) {
                System.out.println("can't flag a cell thats revealed :/");
            }
        }
        else {
            revealCell(row, column);
            System.out.println("revealed cell(s)!");
        }
    } 

    /**
     * print grid
     * prints the grid using the display value, this is what the user sees when playing
     */
    public void printGrid() {   
        System.out.print("  ");
        for (int i = 0; i<numColumns; i++) {
            if (i < 10) {
                System.out.print("  " + i + " ");
            }
            else {
                System.out.print(" " + i + " ");
            }
        }
        System.out.println("");
        for (int i = 0; i<numRows; i++) {
            char rowLabel = (char)('a' + i);
            System.out.print(rowLabel + " ");
            for (int j = 0; j<numColumns; j++) {
                System.out.print ("  " + grid[i][j].getDisplay() + " ");
            }
            System.out.println("");
        }
    }

    /**
     * this prints the grid will all values revealed for a game over scenario
     */
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
     * checks if a mine has been clicked
     */
    public boolean isMineClicked(int row, int column) {
        return grid[row][column].getValue().equals(mineVal) && grid[row][column].alreadyRevealed();
    }
   
    /**
     * all mines flagged
     * checks whether or not all mines are flagged
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
     * checks if all the safe cells have been revealed so that a gameWon situation can happen
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
     * gets the number of revealed cells 
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
     * get the number of mines that are flagged
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

    /**
     * returns the total number of mines in the grid
     * @return
     */
    public int getTotNumMines() {
        return totMines;
    }

    /**
     * returns the number of flags for the grid
     * @return
     */
    public int getNumFlags() {
        return numFlags;
    }
}
