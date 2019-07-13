import java.awt.Color;
import java.awt.Graphics;


public class TileIcon implements Constants
{
	private int x, y;				// X and Y position of where to draw the tile
	private String name;			//name of the fish to draw
	private int health;				//health of the fish to draw
	private Fish fish;				//fish object that the tile represents
	
	public static final int WIDTH = 200;
	public static final int HEIGHT = 50;
	
	/**
	 * creates a new TileIcon 
	 * @param someFish fish object to use to create the TileIcon
	 */
	public TileIcon(Fish someFish)
	{
		name = someFish.getName();
		health = someFish.getHealth();
		fish = someFish;
	}
	
	public void setX(int newX)
	{
		x = newX;
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setY(int newY)
	{
		y = newY;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setName(String newName)
	{
		name = newName;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Fish getFish()
	{
		return fish;
	}
	
	/**
	 * updates the health with the current health value of the fish
	 */
	public void update()
	{
		if(fish!=null)
			health = fish.getHealth();
	}
	
	/**
	 * checks to see if the given coordinates are within the tile
	 * @param someX 
	 * @param someY
	 * @return
	 */
	public boolean isInside(int someX, int someY)
	{
		return (someX > x && someX < (x + WIDTH)
				&& someY > y && someY < (y + HEIGHT));		
	}

	/**
	 * draws the tile
	 * @param g graphics context to draw to
	 */
	public void draw(Graphics g)
	{
		g.setColor(new Color(20, 90, 60));
		
		g.drawRoundRect(x, y, WIDTH, HEIGHT, 25, 25);
		
		g.setColor(new Color(200, 145, 15));
		
		int nameWidth = g.getFontMetrics().stringWidth(name);
		int nameHeight = g.getFontMetrics().getAscent();
		int nameX = x + (WIDTH - nameWidth)/2;
		int nameY = y + nameHeight + 5;
		g.drawString(name, nameX, nameY);
		
		Sprite healthBar = SpriteManager.get().getSprite(HEALTH_BAR_PATH + health + ".bmp");
		
		healthBar.drawTransparent(g, x + (WIDTH - healthBar.getWidth())/2, nameY + 5, new Color(255, 0, 255));
		
		g.setColor(Color.black);
		String healthStr = health + "/100";
		nameWidth = g.getFontMetrics().stringWidth(healthStr);
		nameHeight = g.getFontMetrics().getAscent();
		nameX = x + (WIDTH - nameWidth)/2;
		nameY += nameHeight + healthBar.getHeight() + 5;
		
		g.drawString(healthStr, nameX, nameY);
	}

}
