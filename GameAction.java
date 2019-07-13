import java.awt.Graphics;
import java.awt.Graphics2D;

//////////////////////////////////////////////////////////////////////////////////////////////
////Methods that are implemented in the GameWorld class to combine all the methods////////////
//////////////////////////////////////////////////////////////////////////////////////////////

public interface GameAction 
{	
	void startGame();
	
	void stopGame();
	
	void run();
	
	void updateGame();
	
	void renderGame(Graphics g);
	
	void drawImages(Graphics g);
}
