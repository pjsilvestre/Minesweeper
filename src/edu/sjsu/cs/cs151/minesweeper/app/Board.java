package edu.sjsu.cs.cs151.minesweeper.app;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents the MineSweeper board, is responsible tile management.
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public final class Board
{	
	//-------------------------Public Interface-----------------------
	
	public  final static int NUM_ROWS = 9;
	public final static int NUM_COLS = 9;
	public final static int NUM_MINES = 9;
	
	/**
	 * Constructs a new Board instance, with randomized mine placement.
	 * @param usePresetSeed determines whether mine placement is predetermined or randomized
	 */
	public Board(boolean usePresetSeed)
	{
		tiles = new Tile[NUM_ROWS][NUM_COLS];
		initalizeTiles(usePresetSeed);
	}
	
	/**
	* Reveals the tile specified, and all.surrounding tiles without mines, recursively.
	* @param row the row of the tile specified
	* @param col the column of the tile specified
	*/
	public void revealTile(int row, int col)
	{
		if(!tiles[col][row].isRevealed() && !tiles[row][col].isFlagged()) //Mine is neither revealed nor flagged
		{
			tiles[col][row].reveal();
			
			for(int i = row + 1; i >= row - 1; i--) //Begins with the row directly above the clicked tile and moves down
			{
				for(int j = col -1; j <= col - 1; j++) //Begins from the column directly left of the clicked tile and moves right
				{
					if(i >= 0 && j >= 0 && i < tiles.length && j < tiles[0].length) //Only true if i and j are valid indices 
					{
						revealTile(i, j); 
					}
				}
			}
		}
	}
	
	/**
	* Gets the number of mines adjacent to the tile specified.
	* @param row the row of the tile specified
	* @param col the column of the tile specified
	*/
	public int adjacentMines(int row, int col)
	{
		int mines = 0;
		
		for(int i = row + 1; i >= row - 1; i--) //Begins with the row directly above the clicked tile
		{
			for(int j = col -1; j <= col - 1; j++) //Begins from the column directly left of the clicked tile
			{
				if(i >= 0 && j >= 0 && i < tiles.length && j < tiles[0].length) //Only true if i and j are valid indices 
				{
					if(tiles[i][j].isMine()) 
						mines++;
				}
			}
		}
		
		return mines; 
	}

	/**
	* gets the tile at the specified location.
	* @return Tile at the specified location
	*/
	public Tile getTileAt(int row, int col)
	{
		return tiles[row][col];
	}
	
	//-------------------------Private Fields/Methods------------------
	private Tile[][] tiles;
	
	/**
	 * Initializes all tiles, randomly choosing which have underlying mines.
	 * @postcondition This board will now have a new set of tiles, some of which are mines.
	 */
	private void initalizeTiles(boolean usePresetSeed)
	{
		ArrayList<Integer> mines = new ArrayList<Integer>();
		
		for(int i = 0; i < NUM_ROWS * NUM_COLS; i++)
			mines.add(i);
		
		if(usePresetSeed)
			Collections.shuffle(mines, new Random(0));
		else
			Collections.shuffle(mines);
		
        mines = new ArrayList<Integer>(mines.subList(0, NUM_MINES));
        
		int tileCounter = 0;
		for(int i = 0; i < NUM_ROWS; i++)
		{
			for(int j = 0; j < NUM_COLS; j++)
			{
				if(mines.contains(tileCounter))
					tiles[i][j] = new Tile(true);
				else
					tiles[i][j] = new Tile(false);
				
				tileCounter++;
			}
		}
	}
}

