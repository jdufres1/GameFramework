package main;

public class Controller implements Runnable 
{
	private Game game;
	private boolean running;
	
	public Controller(Game game)
	{
		this.game = game;
		new Thread(this).start();
	}
	public void run()
	{ 
		long lastLoopTime = System.nanoTime(); 
		// We want this to update 30 times per second, but you can try putting higher numbers
		// instead of 30 if you want it running faster;
		final long OPTIMAL_TIME = 1000000000 / 30; 
		long lastFpsTime = 0;
		long fps = 0;
		running = true;
		while (running)   
		{     
			if (!game.isPaused())
			{
				// Find out how long, in nanoseconds, it's been since the last update.     
				long now = System.nanoTime();   
				long updateLength = now - lastLoopTime; 
				lastLoopTime = now;  
				// delta SHOULD be somewhere around 1 (second), if the game is running at 
				// OPTIMAL_TIME.
				double delta = updateLength / ((double)OPTIMAL_TIME);  
				// update the frame counter  
				lastFpsTime += updateLength;   
				fps++;       
				if (lastFpsTime >= 1000000000)   
				{      
					// Every second, print the fps that we updated in for the previous second.
					System.out.println("(FPS: "+fps+")");   
					lastFpsTime = 0;     
					fps = 0;   
				}          
				// update the game    
				game.update(delta);
				if ((System.nanoTime() - lastLoopTime) > OPTIMAL_TIME)
				{
					// Do not sleep. We are already late.
				}
				else 
				{
					// If we reached the OPTIMAL_TIME, sleep for the rest of the frame.
					try {
						Thread.sleep( ( OPTIMAL_TIME - (System.nanoTime() - lastLoopTime) ) / 1000000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public boolean isRunning()
	{
		return running;
	}
	public void setRunning(boolean running)
	{
		this.running = running;
	}
}
