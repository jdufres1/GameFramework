package main.scene;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Scene;
import main.View;

public class TitleScene extends Scene
{
	public TitleScene(final View owner)
	{
		super(owner);
		JButton playButton = new JButton("PLAY");
		playButton.setPreferredSize(new Dimension(100, 20));
		playButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent evt) 
			{
				owner.setScene(new GameScene(owner));
			}
			
		});
		add(playButton);
	}
}	
