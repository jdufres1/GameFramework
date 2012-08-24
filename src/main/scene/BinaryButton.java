package main.scene;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import main.Scene;

public class BinaryButton extends GameButton
{
	// You don't have to define unique actions that occur when the button is unselected, but the 
	// potential is there.
	protected Action unselectAction;
	protected String unselectActionCommand;
	public BinaryButton(Scene owner) 
	{
		super(owner);
	}
	
	public void click()
	{
		selected = !selected;
		if (selected)
		{
			super.click();
		}
		else 
		{
			if (visible)
			{
				try
				{
					unselectAction.actionPerformed(new ActionEvent(this, id, unselectActionCommand));
				} catch (NullPointerException e)
				{
					System.out.println("No unselect action defined for this button.");
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
