package main.scene;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.Action;

import main.GameObject;
import main.Scene;
import main.Sprite;

public class GameButton extends GameObject
{
	/*
	 I use this mostly to make buttons that can be animated like any other gameObject. 
	 The button's properties can be defined similarly to Swing buttons.
	 
	 EX:
	 
	 doneButton = new GameButton(owner);
	 doneButton.setVisible(true);
	 doneButton.setLocation(x, y);
	 doneButton.setAction(new AbstractAction() {
		public void actionPerformed(ActionEvent e) 
		{
			// do something
		}
	 }, actionCommand);
	 doneButton.setSelectedSprite(Sprite.loadImage("images/doneButton.png"), true);
	 
	 The GameButton class defines buttons that do something upon being clicked, but they are always
	 considered unselected. (In the future, I plan to make them selected after they have been clicked, 
	 while the mouse is still hovering over them.
	 The Binary Buttonclass acts like a check box. It can perform different actions upon being 
	 selected or unselected.
	 */
	protected boolean selected = false;
	protected BufferedImage selectedSprite;		
	protected BufferedImage unselectedSprite;
	protected Action action;
	protected String actionCommand;
	// ID's are here in case you want to keep track of which buttons do what 
	protected int id;
	private static int nextId = 0;
	
	public GameButton(Scene owner) 
	{
		super(owner);
		id = nextId;
		fluid = false;
		nextId++;
		actionCommand = Integer.toString(id);
	}
	
	public void draw(Graphics g)
	{
		if (selected)
		{
			try {
				g.drawImage(selectedSprite, x, y, owner);
			} catch (NullPointerException e) {
				System.out.println("No selected sprite defined for this button.");
				if (unselectedSprite != null)
					g.drawImage(unselectedSprite, x, y, owner);
				e.printStackTrace();
			}
		}
		else
		{
			try {
				g.drawImage(unselectedSprite, x, y, owner);
			} catch (NullPointerException e) {
				System.out.println("No unselected sprite defined for this button.");
				if (selectedSprite != null)
					g.drawImage(selectedSprite, x, y, owner);
				e.printStackTrace();
			}
		}
	}
	
	public void click()
	{
		if (visible)
		{
			try
			{
				action.actionPerformed(new ActionEvent(this, id, actionCommand));
			} catch (NullPointerException e)
			{
				System.out.println("No action defined for this button.");
				e.printStackTrace();
			}
		}
	}

	public boolean isSelected() 
	{
		return selected;
	}

	public void setSelected(boolean selected) 
	{
		this.selected = selected;
	}

	public BufferedImage getSelectedSprite() 
	{
		return selectedSprite;
	}

	public void setSelectedSprite(BufferedImage selectedSprite) 
	{
		this.selectedSprite = selectedSprite;
		width = selectedSprite.getWidth();
		height = selectedSprite.getHeight();
	}

	public void setSelectedSprite(BufferedImage selectedSprite, boolean useForUnselected) 
	{
		setSelectedSprite(selectedSprite);
		if (useForUnselected)
		{
			setUnselectedSprite(selectedSprite);
		}
	}
	
	public BufferedImage getUnselectedSprite() 
	{
		return unselectedSprite;
	}

	public void setUnselectedSprite(BufferedImage unselectedSprite) 
	{
		this.unselectedSprite = unselectedSprite;
	}

	public Action getAction() 
	{
		return action;
	}

	public void setAction(Action action) 
	{
		this.action = action;
	}
	
	public void setAction(Action action, String actionCommand) 
	{
		this.action = action;
		setActionCommand(actionCommand);
	}

	public String getActionCommand() 
	{
		return actionCommand;
	}

	public void setActionCommand(String actionCommand) 
	{
		this.actionCommand = actionCommand;
	}

	public int getID() 
	{
		return id;
	}

	public void setID(int id) 
	{
		this.id = id;
	}
}
