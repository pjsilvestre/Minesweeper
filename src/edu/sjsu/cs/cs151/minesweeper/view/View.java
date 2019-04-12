package edu.sjsu.cs.cs151.minesweeper.view;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * View class for Minesweeper using MVC pattern.
 *  
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */
public class View
{
	public static final int RIGHT_CLICK = 1;
	public static final int LEFT_CLICK = 2;

	/**
	 * Constructor for View
	 *
	 * @param rows the number of rows of tiles that the View will display
	 * @param cols the number of columns of tiles that the View will display
	 */
	public View(int rows, int cols)
	{
		this.rows = rows;
		this.columns = cols;
		messageQueue = new ArrayBlockingQueue<int[]>(10); //TODO: fix param
		initializeFrame();
	}

	/**
	 * Updates View subsequent to changes in Model.
	 */
	public void change()
	{
		//TODO
	}

	/**
	 * Reveals the tiles at the specified location
	 * @param row the row the tile
	 * @param col the column of the tile
	 */
	public void reveal(int row, int col)
	{
		buttons[row][col].reveal();
	}

	/**
	 * Explodes the tiles at the specified location
	 * @param row the row the tile
	 * @param col the column of the tile
	 */
	public void explode(int row, int col)
	{
		
		buttons[row][col].explode();
	}


	/**
	 * Flags the tiles at the specified location
	 * @param row the row the tile
	 * @param col the column of the tile
	 */
	public void flag(int row, int col, boolean flag)
	{
		buttons[row][col].flag(flag);
	}

	/**
	 * Gets the message queue (messages from user input).
	 * @return the message queue
	 */
	public Queue<int[]> getQueue()
	{
		return messageQueue;
	}

	//-------------------------Private Fields/Methods------------------
	JFrame frame;
	JPanel boardPanel;
	private int rows;
	private int columns;
	private BlockingQueue<int[]> messageQueue;
	private static TileButton[][] buttons;

	/**
	 * Creates frame, creates the boardPanel, and fills the boardPanel with buttons
	 * Created to keep the constructor relatively clear
	 */
	private void initializeFrame()
	{
		frame = new JFrame("Minesweeper");
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(rows, columns));
		buttons = new TileButton[rows][columns];

		// Fills the boardPanel with buttons
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				TileButton button = new TileButton(i, j, messageQueue, frame);
				buttons[i][j] = button;
				boardPanel.add(button);
			}
		}

		frame.add(boardPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(400, 400);
		frame.pack();
		frame.setVisible(true);
	}
}