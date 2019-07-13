import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameWorld extends Canvas implements Runnable, GameAction, Constants, MouseListener
{	//
	//Variables that control if the game is running or not.
	//
	private volatile boolean running = false;
	private volatile boolean gameOver = false;
	
	//
	//Thread object that does them main animation loop
	//
	private Thread animator = null;

	//
	//image sprite objects for all the visual components of the project
	//
	private Sprite bg, inventory, fishfood;
	
	//
	//fish on the inventory board
	//
	private Fish[] inventoryFish = new Fish[3];
	
	//
	//List containing all the fish objects
	//
	private LinkList fishList;
	
	//
	//Link List that holds all the information tiles
	//
	private TileList tileList;
	
	//
	//Buttons 
	//
	private Button goldfish, puffer, angel, feed;

	/**
	 * Creates a new Game world that has no title, is set at position (0,0) and is the
	 * same size as the back buffer
	 */
	public GameWorld()
	{
		this("", 0, 0, BUFFER_WIDTH, BUFFER_HEIGHT);
	}

	/**
	 * Creates a new GameWorld
	 * @param x X position of the window
	 * @param y Y position of the window
	 * @param width Width of the window
	 * @param height Height of the window
	 */
	public GameWorld(int x, int y, int width, int height)
	{
		this("", x, y, width, height);
	}

	/**
	 * Creates a new GameWorld
	 * @param name Title of the window frame
	 * @param x X position of the window
	 * @param y Y position of the window
	 * @param width Width of the window
	 * @param height Height of the window
	 */
	public GameWorld(String name, int x, int y, int width, int height)
	{	
		JFrame frame = new JFrame("Gamething");
		JPanel panel = (JPanel) frame.getContentPane();

		frame.setSize(BUFFER_WIDTH + 14 ,BUFFER_HEIGHT + 38);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(0, 0, BUFFER_WIDTH, BUFFER_HEIGHT);

		panel.setPreferredSize(new Dimension(getWidth(), getHeight()));
		panel.setLayout(null);
		panel.add(this);
		panel.setSize(BUFFER_WIDTH, BUFFER_HEIGHT);

		addMouseListener(this);
		//addKeyListener(keyHandler);
		setBackground(Color.black);

		frame.setIgnoreRepaint(true);
		frame.setLayout(null);
		frame.pack();
		//frame.setResizable(false);
		frame.setVisible(true);
		requestFocus();

		preLoadGraphics();

		startGame();
	}

	/**
	 * PreLoads all the images used in the project.
	 * This reduces the chance of delay when loading images during the program
	 * because they are already loaded into memory
	 */
	public void preLoadGraphics()
	{
		//////////////////////////LOADS THE UNCHANGING IMAGES////////////////////////////////////
		bg = SpriteManager.get().getSprite(BACKGROUND_FILE_PATH);
		inventory = SpriteManager.get().getSprite(INVENTORY_BOARD_PATH);
		fishfood = SpriteManager.get().getSprite(FOOD_PATH);
		/////////////////////////////////////////////////////////////////////////////////////////
		
		//creates two link lists to hold the fish data and the tile data
		fishList = new LinkList();
		tileList = new TileList(TANK_WIDTH, 0);
		
		//create all the fish on the inventory board
		inventoryFish[0] = new Fish(Fish.GOLDFISH, "Goldfish", ((inventory.getWidth() )/inventoryFish.length) - 40, TANK_HEIGHT + (inventory.getHeight()/2));
		inventoryFish[1] = new Fish(Fish.ANGEL, "Angel", inventoryFish[0].getX() + 60, TANK_HEIGHT + (inventory.getHeight()/2));
		inventoryFish[2] = new Fish(Fish.PUFFER, "Puffer", inventoryFish[1].getX() + 60, TANK_HEIGHT + (inventory.getHeight()/2));

		//create all the buttons
		goldfish = new Button("add", inventoryFish[0].getX(), inventoryFish[0].getY() + 30);
		angel = new Button("add", inventoryFish[1].getX(), inventoryFish[0].getY() + 30);
		puffer = new Button("add", inventoryFish[2].getX(), inventoryFish[0].getY() + 30);
		feed = new Button("feed", inventoryFish[2].getX() + 75, TANK_HEIGHT + 100);

		//creates the initial 3 fish in the tank
		for(int i= 0; i < 3; i++)
		{
			Fish newFish = new Fish();
			fishList.add(newFish);
			TileIcon newTile = new TileIcon(newFish);
			tileList.add(newTile);
		}

	}

	//initialize and start the thread
	public void startGame()
	{
		if(animator == null || !running)
		{
			animator = new Thread(this);
			animator.start();
		}
	}

	//stops the game from running
	public void stopGame()
	{
		running = false;
	}

	
	//main run mehtod
	public void run() 
	{
		running = true;
		while(running)
		{
			renderGame(getGraphics());	//render to a buffer
			updateGame();	//game state is updated
		}
		System.exit(0);
	}

	/**
	 * updates all objects and positions
	 */
	public void updateGame()
	{
		if(!gameOver)
		{
			try {
				
				/////////////////////////////////////////////////////////////////
				//////moves the fish and increases its animation frame to////////
				/////////////////////////////////////////////////////////////////
				
				while(fishList.hasNext())
				{					
					((Fish) fishList.getItem()).incrementFrame();
					((Fish) fishList.getItem()).move();

					Sprite tempSprite = SpriteManager.get().getSprite(((Fish) fishList.getItem()).buildFilePath());
					((Fish) fishList.getItem()).setSprite(tempSprite);
					
					fishList.next();
				}
				
				tileList.update();	//merges the values of the fish list with the values in the 
									//tile list so that they stay the same
				
				fishList.reset();	//resets the current node in the list to the head
				
				Thread.sleep(100);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * draws all images to the buffer
	 */
	public void drawImages(Graphics g)
	{
		//draws the background
		bg.draw(g, 0, 0);
		//draws the inventory board
		inventory.draw(g, 0, this.getHeight()-inventory.getHeight());
		//draws the fish food
		fishfood.drawTransparent(g, (inventory.getWidth()-fishfood.getWidth()) - 15,
				TANK_HEIGHT + (inventory.getHeight()/2)-(fishfood.getHeight()/2), new Color(255,0,255));

		//draws all the inventory fish
		for(int i = 0; i<inventoryFish.length; i++)
		{
			inventoryFish[i].getSprite().drawTransparent(g, inventoryFish[i].getX(), inventoryFish[i].getY(), new Color(255,0,255));
		}

		//draws all fish in the inventory
		while(fishList.hasNext())
		{
			((Fish) fishList.getItem()).draw(g, new Color(255,0,255));
			fishList.next();
		}
		fishList.reset();
		
		//draws all the tiles in the tileList
		tileList.draw(g);
		
		//draws buttons
		goldfish.paint(g);
		puffer.paint(g);
		angel.paint(g);
		feed.paint(g);
	}

	/**
	 * creates the buffer and draws all graphics to it
	 */
	public void renderGame(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;

		//Creates the back buffer image in memory
		BufferedImage buffer = new BufferedImage(BUFFER_WIDTH, BUFFER_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = buffer.createGraphics();

		drawImages(g2);  //draws images to the back buffer

		//Draws buffer to the screen
		g2D.drawImage(buffer, null, 0, 0);
		g2D.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		/////////////////////////////////////////////////////////////////////
		/////////////////////CHECK FOR BUTTON CLICKS/////////////////////////
		/////////////////////////////////////////////////////////////////////
		
		//check to see if goldfish add button was clicked
		if(goldfish.isInside(e.getX(), e.getY()))
		{
			Fish temp = new Fish(Fish.GOLDFISH, "Goldfish");	//adds new goldfish
			fishList.add(temp);									//adds new fish to the list
			tileList.add(new TileIcon(temp));					//adds a new fish tile to the tile list
		}
		
		//check to see if puffer fish button was clicked
		if(puffer.isInside(e.getX(), e.getY()))
		{
			Fish temp = new Fish(Fish.PUFFER, "Puffer");		//adds new puffer fish
			fishList.add(temp);									//adds new fish to the list
			tileList.add(new TileIcon(temp));					//adds a new fish tile to the tile list
		}
		
		//check to see if angel fish button was clicked
		if(angel.isInside(e.getX(), e.getY()))
		{
			Fish temp = new Fish(Fish.ANGEL, "Angel");			//adds new angel fish
			fishList.add(temp);									//adds new fish to the list
			tileList.add(new TileIcon(temp));					//adds a new fish tile to the tile list
		}
		
		//check to see if the feed button was clicked
		if(feed.isInside(e.getX(), e.getY()))	
		{
			while(fishList.hasNext())
			{
				((Fish)fishList.getItem()).feed();				//feeds the selected fish
				fishList.next();
			}
			
			fishList.reset();
		}
		/*
		//removes fish from tank when clicked on
		while(fishList.hasNext())
		{
			if(((Fish) fishList.getItem()).isInside(e.getX(), e.getY()))
			{
				fishList.remove((Fish) fishList.getItem());
				tileList.remove((Fish)fishList.getItem());
			}
				
			fishList.next();
		}
		fishList.reset();
	*/
	}
}