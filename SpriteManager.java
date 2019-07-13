import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * This class handles all the loading and storing of the sprites
 * and the creating of the accelerated images
 * @author Evan Chaffey 
 *
 */
public class SpriteManager 
{
	// only want to have one instance of this class so we can call SpriteManager statically
	private static SpriteManager managerInstance = new SpriteManager();
	
	//stores all the references and images of the accelerated images that are created
	private HashMap<String, Sprite> allSprites = new HashMap<String, Sprite>();
	
	/**
	 * 
	 * @return Instance of SpriteManager
	 */
	public static SpriteManager get()
	{
		return managerInstance;
	}
	
	/**
	 * 
	 * @param ref Reference to the sprite image's location
	 * @return Sprite object created with the loaded image
	 */
	public Sprite getSprite(String ref, Color transColor)
	{
		/**
		 * Checks to see if the sprite is already loaded
		 */
		if (allSprites.get(ref) != null)
		{
			return (Sprite) allSprites.get(ref);
		}
		
		/**
		 * Object to hold the source image data
		 */
		BufferedImage srcImage = null;

		/**
		 * Attempt to load the image file
		 */
		try {
			
			URL url = this.getClass().getClassLoader().getResource(ref);

			if (url == null)
			{
				System.out.println("cant find resource:  " + ref);
				System.exit(0);
			}

			srcImage = ImageIO.read(url);

		} catch (IOException e) {
			System.out.println("failed to load:  " + ref);
		}
		
		/**
		 * Create an accelerated image that is the same size as our loaded image
		 * and is compatible with the screen graphics
		 */
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage img = gc.createCompatibleImage(srcImage.getWidth(), srcImage.getHeight(), Transparency.BITMASK);
		
		/**
		 * Draw the source image onto the created accelerated image
		 */
		img.getGraphics().drawImage(srcImage, 0, 0, null);
		
		/**
		 * Create a temporary sprite object to hold the sprite data
		 * Add the sprite and reference to the hash map
		 */
		Sprite tempSprite = new Sprite(img);
		allSprites.put(ref, tempSprite);
		
		return tempSprite;
	}
	
	/**
	 * Overload the getSprite method so that you can have an optional argument of transColor
	 * @param ref Reference to the sprite image's location
	 * @return Sprite object created with the loaded image and the default transparent color
	 */
	public Sprite getSprite(String ref)
	{
		return getSprite(ref, new Color(0,0,0,0));
	}
}
