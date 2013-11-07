package mvc.clock;


import java.util.Observer;
import java.util.Observable;


public class TextClock implements Observer {
	
	public TextClock(Observable model)
	{
		model.addObserver(this);
		update(model, null);
	}
	
	public void update(Observable model, Object obj)
	{
		System.out.println(((Clock)model).getDate());
		System.out.println("\t" + ((Clock)model).getTime() + "\n");
	}
}
