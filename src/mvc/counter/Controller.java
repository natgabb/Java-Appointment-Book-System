package mvc.counter;

import java.util.Scanner;

public class Controller {
	
	private enum Command{INCREMENT, DECREMENT, RESET, STOP}
	
	private Counter counter;
	
	public Controller(Counter counter)
	{
		this.counter = counter;
	}
	
	public void activate()
	{
		Scanner keyboard = new Scanner(System.in);
		Command[] commands = Command.values();
		Command choice;
		//you should validate the input.
		do{
			displayChoices();
			System.out.print("\nEnter your choice: ");
			choice = commands[keyboard.nextInt() - 1];
			switch (choice)
			{
				case INCREMENT:
					counter.increment();
					break;
					
				case DECREMENT:
					counter.decrement();
					break;
					
				case RESET:
					counter.reset();	
			}
		} while (choice != Command.STOP);
	}
	
	private void displayChoices()
	{
		System.out.println("\nInteract with the counter by selecting a choice from the menu:");
		Command[] commands = Command.values();
		int numChoices = commands.length;
		for (int i = 0; i < numChoices; i++ )
			System.out.println("\t" + (i + 1) + " - " + commands[i]);
	}

}
