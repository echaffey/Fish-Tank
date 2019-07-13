import java.awt.Color;
import java.awt.Graphics;


public class Fish implements Constants
{
	private final String FISH = "Fish/";

	private int x, y;
	private int width, height;
	private int frameCounter = 1;
	private int health = 1000;
	private String ref;
	private String movement;
	private String direction = "R";
	private String name;
	private String[] fishNames = {"Angel", "Goldfish", "Puffer"};
	private Sprite sprite;


	//path constants
	public static final String GOLDFISH = "Fish/GOLDFISH/GOLDFISH_SWIM_R_1.bmp";
	public static final String PUFFER = "Fish/PUFFER/PUFFER_SWIM_R_1.bmp";
	public static final String ANGEL = "Fish/ANGEL/ANGEL_SWIM_R_1.bmp";

	/**
	 * creates a new random type of fish at a random x and y position somewhere
	 * inside the tank
	 */
	public Fish()
	{		
		this((int)(Math.random()*(TANK_WIDTH-50)), (int)(Math.random()*(TANK_HEIGHT-50)));
	}

	/**
	 * Creates a random fish at a set location
	 * @param newX the X position of the new fish
	 * @param newY the Y position of the new fish
	 */
	public Fish(int newX, int newY)
	{
		x = newX;
		y = newY;
		randomizeFish();
		direction = "R";
		movement = "SWIM";
		ref = buildFilePath();
		sprite = SpriteManager.get().getSprite(ref);
		width = sprite.getWidth();
		height = sprite.getHeight();
	}

	/**
	 * Creates a specified type of fish at random coordinates
	 * @param path Path of the image file
	 * @param type Name of fish type
	 */
	public Fish(String path, String type)
	{
		x = (int)(Math.random()*(TANK_WIDTH-50));
		y = (int)(Math.random()*(TANK_HEIGHT-50));
		name = type;
		direction = "R";
		movement = "SWIM";
		ref = path;
		sprite = SpriteManager.get().getSprite(ref);
		width = sprite.getWidth();
		height = sprite.getHeight();
	}



	/**
	 * Creates a specific fish at specified coordinates
	 * @param path Path of the image file
	 * @param type Name of fish type
	 * @param newX the X position of the new fish
	 * @param newY the Y position of the new fish
	 */
	public Fish(String path, String type, int newX, int newY)
	{
		x = newX;
		y = newY;
		name = type;
		direction = "R";
		movement = "SWIM";
		ref = path;
		sprite = SpriteManager.get().getSprite(ref);
		width = sprite.getWidth();
		height = sprite.getHeight();

	}

	/**
	 * sets the X position
	 * @param newX new X
	 */
	public void setX(int newX)
	{
		x = newX;
	}

	/**
	 * gets the X position
	 * @return x position
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * sets the Y position
	 * @param newX new Y
	 */
	public void setY(int newY)
	{
		y = newY;
	}

	/**
	 * gets the Y position
	 * @return Y position
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * sets the width
	 * @param newWidth new width for the fish
	 */
	public void setWidth(int newWidth)
	{
		width = newWidth;
	}

	/**
	 * gets the width
	 * @return width
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * sets the new fish height
	 * @param newHeight new height for the fish
	 */
	public void setHeight(int newHeight)
	{
		height = newHeight;
	}

	/**
	 * gets the height 
	 * @return height
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * sets the health
	 * @param newHealth new value for health
	 */
	public void setHealth(int newHealth)
	{
		health = newHealth;
	}

	/**
	 * gets the health value
	 * @return health
	 */
	public int getHealth()
	{
		return (int)(health/10);
	}

	/**
	 * sets the movement of the fish
	 * @param newMovement new movement
	 */
	public void setMovement(String newMovement)
	{
		movement = newMovement;
	}

	/**
	 * gets the fish movement
	 * @return movement type
	 */
	public String getMovement()
	{
		return movement;
	}

	/**
	 * sets the name of the fish
	 * @param newName new name for the fish
	 */
	public void setName(String newName)
	{
		name = newName;
	}

	/**
	 * gets the name of the fish
	 * @return name of the fish
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * randomizes a fish name to load into the tank
	 */
	private void randomizeFish()
	{
		int n = (int)(Math.random()*fishNames.length);
		name = fishNames[n];
	}

	/**
	 * increases the animation frame so that the fish appears to be
	 * swimming across the screen
	 */
	public void incrementFrame()
	{

		if(frameCounter < MAX_FRAME)
			frameCounter++;
		else
		{
			frameCounter = 1;
			if(movement == "TURN")
				movement = "SWIM";
		}
	}

	/**
	 * sets the sprite image of the fish
	 * @param newSprite new sprite image
	 */
	public void setSprite(Sprite newSprite)
	{
		sprite = newSprite;
		width = newSprite.getWidth();
		height = newSprite.getHeight();
	}

	/**
	 * gets the sprite image
	 * @return sprite image
	 */
	public Sprite getSprite()
	{
		return sprite;
	}

	/**
	 * updates the position of the fish by moving it left or right
	 * depending on which way it is facing
	 */
	public void move()
	{
		if(direction.equals("L"))
		{
			if((x - 5) < 0)
			{
				movement = "TURN";
				direction = "R";
				frameCounter = 1;
			}
			else
				x-=4;
		}	
		else
		{
			if((x + width) > (TANK_WIDTH - 5))
			{
				movement = "TURN";
				direction = "L";
				frameCounter = 1;
			}
			else
				x+=4;
		}

		if(health > 0)
		{
			health -= 1;
		}

	}

	/**
	 * checks to see if a mouse click is inside the fish
	 * @param x X to check
	 * @param y Y to check
	 * @return true if inside
	 */
	public boolean isInside(int x, int y)
	{
		return (x > getX() && x < (getX() + getWidth())
				&& y > getY() && y < (getY() + getHeight()));
	}

	/**
	 * increases the fish's overall health by 20
	 */
	public void feed()
	{
		if(health + 200 > 1000)
			health = 1000;
		else
			health += 200;
	}

	/** 
	 * builds a file path of the image depeding on the name, movement and facing variables
	 * @return file path
	 */
	public String buildFilePath()
	{
		return FISH + name + "/" + name + "_" + movement + "_" + direction + "_" + frameCounter + ".bmp";
	}

	/**
	 * draws the fish
	 * @param g graphics context to draw to
	 */
	public void draw(Graphics g)
	{
		getSprite().draw(g, x, y, sprite.getWidth(), sprite.getHeight());
	}

	/**
	 * draws the fish with a selected color set to transparent
	 * @param g graphics context to draw to
	 * @param c color to set transparent
	 */
	public void draw(Graphics g, Color c)
	{
		getSprite().drawTransparent(g, x, y, c);
	}

}
