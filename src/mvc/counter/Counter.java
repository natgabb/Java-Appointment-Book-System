package mvc.counter;

import java.util.Observable;

public class Counter extends Observable {
	private int value;
	
	public int getValue()
	{
		return value;
	}
	
	public void decrement()
	{
		if (value > 0)
		{
			value--;
			setChanged();
			notifyObservers();
		}
	}
	
	public void increment()
	{
		value++;
		setChanged();
		notifyObservers();
	}
	
	public void reset()
	{
		value = 0;
		setChanged();
		notifyObservers();
	}
}
