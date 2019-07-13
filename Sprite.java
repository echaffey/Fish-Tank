import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

/**
 * This class handles all the properties of each sprite
 * @author Evan Chaffey
 *
 */
public class Sprite 
{
	//Image of the sprite
	private BufferedImage oImage;
	private boolean oVisible = true;

	/**
	 * 
	 * @param img Image file to create sprite from
	 */
	public Sprite(BufferedImage img)
	{
		oImage = img;
	}

	/**
	 * 
	 * @return Width of the sprite
	 */
	public int getWidth()
	{
		return oImage.getWidth(null);
	}

	/**
	 * 
	 * @return Height of the sprite
	 */
	public int getHeight()
	{
		return oImage.getHeight(null);
	}

	/**
	 * 
	 * @return Visibility of sprite
	 */
	public boolean getVisible()
	{
		return oVisible;
	}

	public void setVisible(boolean newValue)
	{
		oVisible = newValue;
	}

	/**
	 * 
	 * @param g Graphics context to draw to
	 * @param destX X position to start drawing at
	 * @param destY Y position to start drawing at
	 */
	public void draw(Graphics g, int destX, int destY)
	{
		g.drawImage(oImage, destX, destY, null);
	}

	public void draw(Graphics g, int destX, int destY, int imgWidth, int imgHeight)
	{
		g.drawImage(oImage, destX, destY, imgWidth, imgHeight, null);
	}

	public void draw(Graphics g, int destX, int destY, int srcX, int srcY, int imgWidth, int imgHeight)
	{
		g.drawImage(oImage, destX, destY, destX + imgWidth, destY + imgHeight, srcX, srcY, srcX + imgWidth, srcY + imgHeight, null);
	}

	public void draw(Graphics g, int destX, int destY, int srcX, int imgWidth, int imgHeight)
	{
		g.drawImage(oImage,	destX, destY, destX + imgWidth, destY + imgHeight, srcX, 0, srcX + imgWidth, imgHeight, null);
	}

	public void drawTransparent(Graphics g, int x, int y, Color c1)
	{
		// Primitive test, just an example
		final int r1 = c1.getRed();
		final int g1 = c1.getGreen();
		final int b1 = c1.getBlue();
		ImageFilter filter = new RGBImageFilter()
	    {
	      public final int filterRGB(int x, int y, int rgb)
	      {
	        int r2 = (rgb & 0xFF0000) >> 16;
	        int g2 = (rgb & 0xFF00) >> 8;
	        int b2 = rgb & 0xFF;
	        if (r2 == r1 && g2 == g1 && b2 == b1)
	        {
	          // Set fully transparent but keep color
	          return rgb & 0xFFFFFF;
	        }
	        return rgb;
	      }
	    };
		ImageProducer ip = new FilteredImageSource(oImage.getSource(), filter);

		BufferedImage tempImg = toBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
		
		g.drawImage(tempImg, x, y, getWidth(), getHeight(), null);
	}

	private BufferedImage toBufferedImage(Image image)
	{
		BufferedImage dest = new BufferedImage(
				image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = dest.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return dest;

	}

}
