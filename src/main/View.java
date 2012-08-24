package main;

import javax.swing.JFrame;

import main.scene.TitleScene;

public class View extends JFrame
{
	private Game game;
	
	// Each scene of the game is a class that extends Scene, which itself extends JPanel. The scenes 
	// know what objects they contain and how to draw them, so all the View (a JFrame) needs to know 
	// is WHICH Scene is currently being drawn on the screen.
	private Scene scene;
	
	public View (Game game)
	{
		this.game = game;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		
		// The game will go to a small title screen first. All it has is a button that says "PLAY" which
		// brings you to the "Game Scene." If you want to skip the title scene, just put new GameScene(this)
		// in place of it in the next line.
		scene = new TitleScene(this);
		setContentPane(scene);
	}
	public void update(double delta)
	{
		scene.repaint();
	}
	public void setScene(Scene scene)
	{
		System.out.println("Did you remove all KeyListeners?");
		// This has caused me HUGE problems in the past, so I'm just leaving this as a reminder for anyone
		// who might forget to do this. Whenever you switch scenes, you need to make sure the old scene is
		// removed from all of the View's listener lists, or you could have big, big problems.
		
		this.scene = scene;
		setContentPane(scene);
		validate();
	}
}
