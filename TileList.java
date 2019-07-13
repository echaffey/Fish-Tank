import java.awt.Color;
import java.awt.Graphics;


public class TileList implements Constants
{
	private int x, y;						//X and Y position to draw the list at
	private LinkList list;					//list containing the TileIcon objects

	/**
	 * creates a new tile list
	 * @param listX X position
	 * @param listY Y position
	 */
	public TileList(int listX, int listY)
	{
		x = listX;
		y = listY;
		list = new LinkList();
	}

	/**
	 * sorts the list by name of the fish
	 */
	public void sortName()
	{
		list.sortName();
	}

	/**
	 * sorts the list by name of the fish
	 * @param list list to sort
	 */
	public void sortName(LinkList list)
	{
		LinkList templist = list;
		TileIcon high = ((TileIcon)templist.getItem());
		

		while(list.hasNext())
		{
			if(((TileIcon)templist.getItem()).getName().compareTo(high.getName())< 0)
			{
				high = ((TileIcon)templist.getItem());
			}

			templist.next();
		}

	}

	/**
	 * adds a tile to the list
	 * @param tile tile to add
	 */
	public void add(TileIcon tile)
	{
		list.add(tile);
		tile.setX(TANK_WIDTH);
		//tile.setY(nextTileY);

		//nextTileY += tile.HEIGHT;
	}

	/**
	 * removes a tile from the list
	 * @param fish fish contained in the tile to remove
	 * @return the removed tile
	 */
	public TileIcon remove(Fish fish)
	{
		if(fish != null)
		{
			while(list.hasNext())
			{
				if(((TileIcon)list.getItem()).getFish() == fish)
				{
					TileIcon temp =((TileIcon)list.getItem());
					list.remove(temp);
					temp = null;

					//temp.setY(temp.getY() - temp.HEIGHT);
				}
				list.next();
			}
			list.reset();
		}
		return null;
	}

	/**
	 * updates all the tileIcons in the list
	 */
	public void update()
	{
		while(list.hasNext())
		{
			((TileIcon)list.getItem()).update();
			list.next();
		}

		list.reset();
	}

	/**
	 * draws the list and all the tileIcons
	 * @param g graphics context to draw to
	 */
	public void draw(Graphics g)
	{
		int counter = 0;

		g.setColor(Color.black);
		g.fillRect(x, y, TileIcon.WIDTH, TileIcon.HEIGHT * (list.length() + 5));
		
		g.setColor(new Color(25, 135, 135));
		g.fillRoundRect(x, y, TileIcon.WIDTH, TileIcon.HEIGHT * (list.length()-1), 25, 25);

		while(list.hasNext())
		{
			if((TileIcon)list.getItem() != null)
			{
				((TileIcon)list.getItem()).setY(TileIcon.HEIGHT * counter);
				((TileIcon)list.getItem()).draw(g);
				counter++;
			}
			list.next();
		}		
		list.reset();
	}

}
