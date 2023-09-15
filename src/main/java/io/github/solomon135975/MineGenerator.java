package io.github.solomon135975;

import java.util.Random; 
import java.util.Arrays;


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

    public Grid generateMines(Grid grid, int startCol, int startRow) {
        int totMines = grid.getTotNumMines();
        

        return grid;
    }
}
