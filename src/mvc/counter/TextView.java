package mvc.counter;

import java.util.Observable;
import java.util.Observer;


public class TextView implements Observer{
	public TextView(Observable model)
	{
		model.addObserver(this);
		update(model,this);
	}
	
	public void update(Observable model, Object obj)
	{
		System.out.println("Counter value = " + ((Counter)model).getValue());
	}
}
