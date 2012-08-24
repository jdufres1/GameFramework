package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

// The only purpose for this class is the static loadImage method. Makes things a tiny bit more streamlined.

public class Sprite
{
	public static BufferedImage loadImage(String img)
	{
		URL imageURL = Game.class.getResource(img);
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(imageURL);
		} catch(IOException e) 
		{
			e.printStackTrace();
			System.out.println("Image not found at: " + img);
			image = null;
		}
		return image;
	}
}