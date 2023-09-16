package io.github.solomon135975;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Point;
import java.util.List;

/* idea: 
 * planning rdfs for mine generation:
    - Starting point:
        player chooses cell x,y. this is the starting point for the dfs
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
    List <Point> potentialMines = new ArrayList<>();
    Random rand = new Random();

    public Grid generateMines(Grid grid, int startRow, int startCol) {
        int numColumns = grid.getNumColumns();
        int numRows = grid.getNumRows();
        int totMines = grid.getTotNumMines();

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j ++) {
                potentialMines.add(new Point(i,j));
            }
        }
        for (int i = startRow -1; i < startRow + 2; i++) {
            for (int j = startCol -1; j<startCol+2; j++) {
                Point hasToBeSafeCell = new Point(i,j);
                potentialMines.remove(hasToBeSafeCell);
            }
        }


        
        return grid;
    }
    
    public void randomiseCellList (int lengthOfList) {
        
    }
}
