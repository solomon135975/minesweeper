package io.github.solomon135975;

import java.util.Random;
import java.util.ArrayList;
import java.awt.Point;
import java.util.List;

/* idea: 
 * planning rdfs for mine generation:
    - Starting point:
        player chooses cell x,y. this is the starting point for mine gen.
        ensure that this cell and its immediate neighbours arent mines 
    - fisher-yates shuffle:
        create a list of all potential mine positions (all cells excluding the 3x3 beginner safe zone)
        shuffle this list to randomise the order
        place the mines in the first numMines cells of this shuffled list 
    - increment surrounding cells:
        when a mine is placed, increment nonmine cells around it
            or
        loop through all cells in grid, for each mine, increment the nonmine cells around it
 */

public class MineGenerator {
    private List <Point> potentialMines = new ArrayList<>();
    private Random rand = new Random();

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
    
    public void shufflePotentialMines () {
        int n = potentialMines.size();
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            Point temp = potentialMines.get(i);
            potentialMines.set(i, potentialMines.get(j));
            potentialMines.set(j, temp);
        }
    }
}
