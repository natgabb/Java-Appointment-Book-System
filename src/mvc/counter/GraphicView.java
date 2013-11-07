package mvc.counter;

import java.util.Observable;
import java.util.Observer;

public class GraphicView implements Observer {
	
	public GraphicView(Observable model)
	{
		model.addObserver(this);
		update(model,this);
	}
	
	public void update(Observable model, Object obj)
	{
		String graph = "Counter graph = ";
		int ctr = ((Counter)model).getValue();
		for (int i = 0; i < ctr; i ++)
			graph += "*";
		System.out.println("\n"+ graph);
	}

}
