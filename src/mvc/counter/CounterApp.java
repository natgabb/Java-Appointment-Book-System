package mvc.counter;



public class CounterApp {	
	
	public static void main (String[] args)
	{
		Counter counter = new Counter();
		new TextView(counter);
		new GraphicView(counter);
		new Controller(counter).activate();
	}

}
