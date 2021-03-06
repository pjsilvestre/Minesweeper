package edu.sjsu.cs.cs151.minesweeper.view;

import edu.sjsu.cs.cs151.minesweeper.message.DifficultyMessage;
import edu.sjsu.cs.cs151.minesweeper.message.Message;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * WelcomePanel represents a startup screen to welcome the player.
 *
 * @author JordanConragan
 * @author BrettDispoto
 * @author PatrickSilvestre
 */

public class WelcomePanel extends JPanel
{
	/**
	 * Constructor for WelcomePanel.
	 *
	 * @param messageQueue The message queue to collect user input.
	 */
	public WelcomePanel(BlockingQueue<Message> messageQueue)
	{
		setLayout(new BorderLayout());
		setUpNorth();
		setUpCenter(messageQueue);
		setUpSouth();
		setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	}

	/**
	 * Initiates the mine animation.
	 */
	public void animate()
	{
		animation.animate();
	}

	//-------------------------Private Fields/Methods------------------
	private MineAnimation animation;

	private static final int PREFERRED_WIDTH = 400;
	private static final int PREFERRED_HEIGHT = 200;

	/**
	 * Set up the north region of the WelcomePanel, which contains the textual logo of the game.
	 */
	private void setUpNorth()
	{
		JLabel logo;
		//avoid trying to create an ImageIcon with a null parameter
		try
		{
			BufferedImage img = ImageIO.read(new File("resources/logo.png"));
			logo = new JLabel(new ImageIcon(img));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			logo = new JLabel(new ImageIcon());
		}

		add(logo, BorderLayout.NORTH);
	}

	/**
	 * Set up the center region of the WelcomePanel, which contains difficulty selection functionality.
	 *
	 * @param messageQueue The message queue to collect user input.
	 */
	private void setUpCenter(BlockingQueue<Message> messageQueue)
	{
		JPanel difficultyPanel = new JPanel();
		difficultyPanel.setLayout(new GridLayout(2, 3, 10, 10));

		//using empty JLabels to fill grid cells
		difficultyPanel.add(new JLabel());

		JLabel difficultyLabel = new JLabel("Select a difficulty:");
		difficultyPanel.add(difficultyLabel);

		difficultyPanel.add(new JLabel());

		JButton easyButton = new JButton("Easy");
		easyButton.setBorder(BorderFactory.createLineBorder(Color.black));
		easyButton.setBackground(Color.YELLOW);
		easyButton.setOpaque(true);
		easyButton.addActionListener(e -> {
			try
			{
				messageQueue.put(new DifficultyMessage(View.Difficulty.EASY, true));
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		});
		difficultyPanel.add(easyButton);

		JButton mediumButton = new JButton("Medium");
		mediumButton.addActionListener(e -> {
			try
			{
				messageQueue.put(new DifficultyMessage(View.Difficulty.MEDIUM, true));
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		});
		mediumButton.setBorder(BorderFactory.createLineBorder(Color.black));
		mediumButton.setBackground(Color.cyan);
		mediumButton.setOpaque(true);
		difficultyPanel.add(mediumButton);

		JButton hardButton = new JButton("Hard");
		hardButton.addActionListener(e -> {
			try
			{
				messageQueue.put(new DifficultyMessage(View.Difficulty.HARD, true));
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		});
		hardButton.setBorder(BorderFactory.createLineBorder(Color.black));
		hardButton.setBackground(Color.RED);
		hardButton.setOpaque(true);
		difficultyPanel.add(hardButton);

		add(difficultyPanel, BorderLayout.CENTER);
		difficultyPanel.setBackground(Color.LIGHT_GRAY);
	}

	/**
	 * Set up the south region of the WelcomePanel, which contains the mine animation.
	 */
	private void setUpSouth()
	{
		BufferedImage mineIcon = null;

		try
		{
			mineIcon = ImageIO.read(new File("resources/mine.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		animation = new MineAnimation(mineIcon);
		add(animation, BorderLayout.SOUTH);
	}

	/**
	 * Animates a mine moving from left to right in a marquee-like fashion.
	 */
	private class MineAnimation extends JPanel
	{
		@Override
		public void paint(Graphics g)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(mineIcon, x, 0, MINE_WIDTH, MINE_HEIGHT, null, null);
		}

		@Override
		public Dimension getPreferredSize()
		{
			return new Dimension(400, 50);
		}

		/**
		 * Animates the mine by setting its position based on its location in the panel.
		 */
		public void animate()
		{
			if (movingRight)
			{
				x++;
			}
			else
			{
				x--;
			}
			if (x > WelcomePanel.this.getPreferredSize().getWidth() - MINE_WIDTH)
			{
				movingRight = false;
			}
			if (x < 0)
			{
				movingRight = true;
			}

		}

		private int x;

		private boolean movingRight;

		private BufferedImage mineIcon;

		private static final long serialVersionUID = 1L;
		private static final int MINE_WIDTH = 50;
		private static final int MINE_HEIGHT = 50;

		/**
		 * Constructs the MineAnimation.
		 *
		 * @param mineIcon The image used to represent the mine.
		 */
		private MineAnimation(BufferedImage mineIcon)
		{
			x = -MINE_WIDTH;
			this.mineIcon = mineIcon;
			movingRight = true;
		}
	}
}