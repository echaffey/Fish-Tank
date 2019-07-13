import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Button 
{
	public static final int BUTTON_WIDTH = 30;
	public static final int BUTTON_HEIGHT = 20;
	
	private int oX, oY, oWidth, oHeight;
	private Color oUpColor = new Color(0.7f,0.5f,0.7f);
	private Color oDownColor = new Color(0.65f,0.45f,0.65f);
	private Color oButtonColor = oUpColor;
	private String oCaption = "";
	private boolean oPressed = true;
	private BufferedImage buttonImage;
	
	/**
	 * creates a new button with the default label and positon
	 */
	public Button()
	{
		this("button", 0, 0);
	}
	
	/**
	 * creates a new button with a specified caption
	 * @param newCaption button caption
	 */
	public Button(String newCaption)
	{
		this(newCaption, 0, 0);
	}
	
	/**
	 * creates a new button with the default size
	 * @param newCaption button caption
	 * @param newX X position
	 * @param newY Y position
	 */
	public Button(String newCaption, int newX, int newY)
	{
		this(newCaption, newX, newY, BUTTON_WIDTH, BUTTON_HEIGHT);
	}
	
	/**
	 * Creates a new button
	 * @param newCaption button caption
	 * @param newX X position
	 * @param newY Y position
	 * @param newWidth button width
	 * @param newHeight button height
	 */
	public Button(String newCaption, int newX, int newY, int newWidth, int newHeight)
	{
		oX = newX;
		oY = newY;
		oCaption = newCaption;
		oWidth = newWidth;
		oHeight = newHeight;
	}
	
	/**
	 * checks to see if the given coordinates are within the button
	 * @param objX 
	 * @param objY
	 * @return true if inside
	 */
	public boolean isInside(int objX, int objY)
	{
		return ((objX >= oX) && (objX <= oX + oWidth)
				&& (objY >= oY) && (objY <= oY + oHeight));
	}
	
	public void click()
	{
		oPressed = !oPressed;
		
		if(oPressed)
			oButtonColor = oUpColor;
		else
			oButtonColor = oDownColor;
	}
	
	/**
	 * draws the button
	 * @param g graphics context to draw to
	 */
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		buttonImage = new BufferedImage(oWidth, oHeight, BufferedImage.BITMASK);
		Graphics2D g2 = buttonImage.createGraphics();

		final int SPACER = 1;
		
		g2.setColor(oButtonColor);
		g2.fillRect(0, 0, oWidth - SPACER, oHeight - SPACER);
		
		g2.setColor(Color.black);
		g2.drawRect(0, 0, oWidth - SPACER, oHeight - SPACER);
		
		g2.setColor(Color.black);
		int captionWidth = g2.getFontMetrics().stringWidth(oCaption);
		int captionHeight = g2.getFontMetrics().getAscent();
		
		g2.drawString(oCaption, 
					 (oWidth - captionWidth)/2, 
					 (oHeight + captionHeight)/2);
		
		g2d.drawImage(buttonImage, null, oX, oY);
		
		//g2d.dispose();
	}
}
