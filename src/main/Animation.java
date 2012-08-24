package main;

public class Animation {
	private int counter = 0;
	private boolean skippable;
	private String name;
	public Animation (String name)
	{
		this.name = name;
		skippable = true;
	}
	public Animation (String name, boolean skippable)
	{
		this.name = name;
		this.skippable = skippable;
	}
	public String getName() {
		return name;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public void incrementCounter()
	{
		counter++;
	}
	public boolean isSkippable()
	{
		return skippable;
	}
	public void end()
	{
		counter = 0;
	}
}
