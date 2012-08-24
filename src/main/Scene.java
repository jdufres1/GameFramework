package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Scene extends JPanel
{
	protected View owner;
	protected BufferedImage background;
	protected ArrayList<GameObject> objects = new ArrayList<GameObject>();
	public Scene(View owner)
	{
		this.owner = owner;
		setSize(owner.getContentPane().getWidth(), owner.getContentPane().getHeight());
	}
	public void update(double delta)
	{
		for (GameObject object : objects)
		{
			object.update(delta);
		}
		repaint();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this);
		// Call the draw method of each GameObject. GameObjects should know how to draw themselves.
		for (GameObject object : objects)
		{
			object.draw(g);
		}
	}
	public void destroy()
	{
		// If the Scene is registered as a listener with the View, it should be removed here.
		// EX: owner.removeKeyListener(this); etc.
	}
	public void addGameObject(GameObject object)
	{
		// As gameObjects are added, sort them so that the lower an object's Z-value is, the higher its
		// index will be. That way, objects with a lower z-value will be drawn in front of objects with 
		// a lower z-value;
		int index = 0;
		while(index < objects.size() && object.getZ() <= objects.get(index).getZ())
		{
			index++;
		}
		objects.add(index, object);
	}
	public void removeGameObject(GameObject object)
	{
		objects.remove(object);
	}
	public ArrayList<GameObject> getGameObjects()
	{
		return objects;
	}
}
