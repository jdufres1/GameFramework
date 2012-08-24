package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;

public class GameObject 
{
	/* Every object -- a character, an enemy, a projectile, a house, etc., should be in a class which 
	 	extends GameObject
	 	All classes extending GameObject should call initObject() in their constructor. initObject() calls the addObject() 
	 	method of the scene in which the object is drawn. 
	*/		   
	protected BufferedImage currentSprite;
	protected Scene owner;
	protected int x;
	protected int y;
	protected int z;						// The higher z-value an object has, the farther back 
											// it will be drawn.
	protected int width;
	protected int height;
	protected boolean fluid; 				// Does the object move when the screen moves?
											// A HUD, for example, would NOT be fluid.
	protected boolean solid;				// Can the object pass through other objects?
	protected boolean visible;				// Should we draw the object?
	// A HashMap containing all possible animations, so that they can be accessed by name.
	protected HashMap<String, Animation> animations = new HashMap<String, Animation>();
	// A queue of animations, so that if a character needs to go into another animation immediately
	// after the current one, that can be arranged.
	protected LinkedList<Animation> animationQueue = new LinkedList<Animation>();
	protected Animation currentAnimation;
	// Every single time I use this framework, I forget why changeAnimation is necessary, and then I
	// spend forever figuring it out again. It is definitely necessary, but everything it needs to 
	// be used for is taken care of in the framework, so you shouldn't need to worry about it.
	protected boolean changeAnimation = false;
	
	public GameObject(Scene owner)
	{
		this.owner = owner;
		// Put a blank animation called "Wait" into the list of animations. If you don't change it, 
		// the Wait animation literally does nothing. 
		animations.put("Wait", new Animation("Wait", true));
		currentAnimation = animations.get("Wait");
		visible = true;
	}
	public GameObject(Scene owner, boolean visible)
	{
		this(owner);
		this.visible = visible;
	}
	public void initObject()
	{
		owner.addGameObject(this);
	}
	public void destroy()
	{
		// This is the place where any references to an object should be made NULL or removed. 
		owner.removeGameObject(this);
	}
	public void draw(Graphics g)
	{
		// Draw the currentSprite at the current position
		g.drawImage(currentSprite, x, y, owner);
	}
	public void animate()
	{
		try {
			if (currentAnimation == null) 
			{
				throw new NullPointerException();
			}
			executeFrame(currentAnimation.getName(), currentAnimation.getCounter());
			if (!changeAnimation)
				currentAnimation.incrementCounter();
			else
				changeAnimation = false;
		} catch (NullPointerException e)
		{
			System.out.println("No animation specified for " + this);
		}
	}
	
	public void executeFrame(String name, int frame)
	{
		/*
		 *  I've constructed animations so that they can be organized in a pretty simple construct.
		 *  
		 *  For each animation, add an if (name == "WHATEVER") line.
		 *  Within the if construct, a switch statement picks out the current frame you're on.
		 *  You can define key frames within this switch statement so that key frames can both switch to
		 *  the next sprite AND do other things at the same time.
		 *  
		 *  I usually list all sprites as static variables at the top of a GameObject's class declaration. 
		 *  So a cowboy might have a list of sprites that looks like:
		  
		    Sprite wait1 = new Sprite("image/cowboy_wait1.png");
			Sprite wait2 = new Sprite("image/cowboy_wait2.png");
			Sprite shoot1 = new Sprite("image/cowboy_shoot1.png");
			Sprite shoot2 = new Sprite("image/cowboy_shoot2.png");
			
		 *  Then the cowboy's shoot animation in this method might look like this:
		 	
		 	if (name == "Shoot")
		 	{
		 		switch(frame)
		 		{
		 		case 0: currentSprite = shoot1;
		 				break;
		 		case 5: currentSprite = shoot2;
		 				createBullet(x, y);
		 				break;
		 		case 10: endAnimation();
		 		}
		 	}
		 	
		 * The last key frame of every animation should call endAnimation();
		 */ 
		if (name == "Wait")
		{
			switch(frame)
			{
			case 0: endAnimation();
					break;
			}
		}
	}
	
	public void endAnimation()
	{
		// if the animation queue is empty, then go back to the wait animation. 
		// else, go to the next animation in the queue.
		if (animationQueue.isEmpty())
		{
			resetAnimationCounter();
			setAnimation("Wait");
		}
		else
		{
			resetAnimationCounter();
			setAnimation(animationQueue.getFirst().getName());
			animationQueue.removeFirst();
		}
	}
	
	public void changeAnimation(String name)
	{
		// Only change the animaton IF the current one is skippable. 
		if (currentAnimation.isSkippable())
		{
			changeAnimation = true;
			resetAnimationCounter();
			setAnimation(name);
		}
	}
	
	public void queueAnimation(String name)
	{
		animationQueue.addLast(animations.get(name));
	}
	
	public void clearAnimationQueue()
	{
		animationQueue.clear();
	}
	public void moveX(int moveX)
	{
		x += moveX;
	}
	public void moveY(int moveY)
	{
		y += moveY;
	}
	public Animation getAnimation()
	{
		return currentAnimation;
	}
	protected void setAnimation(String name)
	{
		// Use this to change the animation even if the current one is not skippable.
		changeAnimation = true;
		currentAnimation = animations.get(name);
	}
	
	public void resetAnimationCounter()
	{
		currentAnimation.setCounter(0);
	}
	
	public int getAnimationCounter()
	{
		return currentAnimation.getCounter();
	}
	public BufferedImage getCurrentSprite() 
	{
		return currentSprite;
	}
	public void setCurrentSprite(BufferedImage currentSprite) 
	{
		this.currentSprite = currentSprite;
	}
	public Scene getOwner() 
	{
		return owner;
	}
	public void setOwner(Scene owner) 
	{
		this.owner = owner;
	}
	public int getX() 
	{
		return x;
	}
	public void setX(int x) 
	{
		this.x = x;
	}
	public int getY() 
	{
		return y;
	}
	public void setY(int y) 
	{
		this.y = y;
	}
	public int getZ()
	{
		return z;
	}
	public void setZ(int z)
	{
		this.z = z;
	}
	public int getWidth()
	{
		return width;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	public int getHeight()
	{
		return height;
	}
	public void setHeight(int height)
	{
		this.height = height;
	}
	public Rectangle getBounds()
	{
		// Return the object's bounding rectangle.
		Rectangle rect = null;
		try {
			rect = new Rectangle(x, y, width, height);
			if (width == 0 || height == 0)
				throw new Exception (this + ": Bounding rectangle has undefined dimensions.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rect;
	}
	public boolean isFluid() {
		return fluid;
	}
	public void setFluid(boolean fluid) {
		this.fluid = fluid;
	}
	public boolean isSolid() {
		return solid;
	}
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	public boolean isVisible() 
	{
		return visible;
	}
	public void setVisible(boolean visible) 
	{
		this.visible = visible;
	}
}
