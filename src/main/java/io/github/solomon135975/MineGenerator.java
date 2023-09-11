package io.github.solomon135975;

import java.util.Random; 

/* idea: 
 * randomised depth first search:
 * 1. starting point: player chooses first cell
 * 2. ensure this cell is not a mine (it isn't because we haven't set any yet), mark as revealed
 * 3. start dfs from this first cell, mark immediate surrounding cells as revealed as well (they will not be bombs)
 * 4. from the neighbours, start the dfs and place mines in them or from them etc
 * 5. continue placing bombs until max num bombs reached
 * 
 * when a mine is placed, increment value of non-mine cells around it
 */

public class MineGenerator {
    
}
