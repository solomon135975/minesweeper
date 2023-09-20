package io.github.solomon135975;

import java.util.Random;
import java.util.ArrayList;
import java.awt.Point;
import java.util.List;

/**
 * MineGenerator class to handle the mine generation logic
 * uses Fisher-Yates shuffle method
 * uses the Point class from the AWT package to store the position in the grid in an array which is shuffled
 */
public class MineGenerator {
    private List <Point> potentialMines = new ArrayList<>();
    private Random rand = new Random();

    /**
     * generates the mines based on the starting move's position in the grid
     * first clears the list of potentialMines to ensure that there are no positions listed already
     * clears the grid from mines to ensure that there are no mines on the grid already
     * 
     * each position in the grid is added to the potentialMines list
     * the 3x3 of adjacent cells around the starting position, including the starting position, are removed from the list
     * this ensures that the player starts with at least a 3x3 of safe cells and that the first move is not on a mine
     * 
     * the shuffle method is then called 
     * the mines are then placed in the first totMines positions of the list in the grid
     * @param grid
     * @param startRow
     * @param startCol
     */
    public void generateMines(Grid grid, int startRow, int startCol) {
        potentialMines.clear();
        grid.clearAllMines();

        int numColumns = grid.getNumColumns();
        int numRows = grid.getNumRows();
        int totMines = grid.getTotNumMines();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j ++) {
                potentialMines.add(new Point(i,j));
            }
        }

        for (int i = startRow -1; i < startRow + 2; i++) {
            for (int j = startCol -1; j < startCol + 2; j++) {
                if ((i>=0 && i<numRows) && (j>=0 && j<numColumns)) {
                    Point temp = new Point(i,j);
                    potentialMines.remove(temp);
                }
            }
        }

        shufflePotentialMines(); 
        placeMines(grid, totMines);
    }

    /**
     * this places the mines in the grid
     * it places the mines in the first totMines positions and decrements the minesToPlace variable to control and break the loop
     * @param grid
     * @param totMines
     */
    public void placeMines(Grid grid, int totMines) {
        int minesToPlace = totMines;

        for (Point potentialMine : potentialMines) {
            if (minesToPlace == 0) {
                break;
            }

            int row = (int) potentialMine.getX();
            int col = (int) potentialMine.getY();
            
            grid.setMineAtCell(row, col);
            minesToPlace--;

        }
    }
    
    /**
     * this shuffles the list of potentialMines by using the Fisher-Yates shuffle method
     * ensures a randomised placement of the mines
     */
    public void shufflePotentialMines () {
        int n = potentialMines.size();
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            //this swaps the elements in the list at positions i and j  
            Point temp = potentialMines.get(i);
            potentialMines.set(i, potentialMines.get(j));
            potentialMines.set(j, temp);
        }
    }
}
