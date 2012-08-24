package main;

public class Game 
{
	private boolean paused;
	private boolean debug;
	private View view;
	private Controller ctl;
	
	public Game ()
	{
		view = new View(this);
		ctl = new Controller(this);
	}
	public static void main (String[] args)
	{
		new Game();
	}
	
	public void update(double delta)
	{
		// This method is called by the Controller object, hopefully 30 times per second.
		view.update(delta);
	}
	public void setPaused(boolean pause) 
	{
		this.paused = pause;
	}
	public boolean isPaused()
	{
		return paused;
	}
	public boolean isDebug()
	{
		return debug;
	}
}
