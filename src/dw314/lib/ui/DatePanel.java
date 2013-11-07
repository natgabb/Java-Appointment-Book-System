package dw314.lib.ui;

import static javax.swing.BorderFactory.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DatePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JComboBox monthNamesComboBox;
	private JTextField dayTextField,yearTextField;
			
	public DatePanel(Locale aLocale)
	{
		Calendar today;
		if (aLocale != null)
			today = Calendar.getInstance(aLocale);
		else
			today = Calendar.getInstance();
		setLayout(new BorderLayout());
		setBorder(createCompoundBorder
				(createTitledBorder("Enter Date"),
						createEmptyBorder(10,10,10,10)));
												
		JPanel monthPanel = new JPanel(new GridLayout(2,0,5,5));
		java.text.DateFormatSymbols dateFormatter = 
			java.text.DateFormatSymbols.getInstance(aLocale);
		String months [] = dateFormatter.getMonths();
		monthNamesComboBox = new JComboBox(months);
		monthNamesComboBox.removeItemAt(12);
		monthPanel.add(new JLabel("Month",JLabel.CENTER));
		monthPanel.add(monthNamesComboBox);
		
		JPanel dayYearPanel = new JPanel(new GridLayout(2,0,5,5));
		dayYearPanel.setBorder(createEmptyBorder(0,5,0,0));
		dayTextField = 	new JTextField();
		dayTextField.setHorizontalAlignment(JTextField.RIGHT);
		yearTextField = new JTextField();
		yearTextField.setHorizontalAlignment(JTextField.RIGHT);
		
		//set date values
		monthNamesComboBox.setSelectedIndex(today.get(Calendar.MONTH));
		dayTextField.setText("" + today.get(Calendar.DAY_OF_MONTH));
		yearTextField.setText("" + today.get(Calendar.YEAR));
		
		dayYearPanel.add(new JLabel("Day",JLabel.RIGHT));
		dayYearPanel.add(new JLabel("Year",JLabel.RIGHT));
		dayYearPanel.add(dayTextField);
		dayYearPanel.add(yearTextField);
		
		add(monthPanel,BorderLayout.WEST);
		add(dayYearPanel,BorderLayout.EAST);
		
	}
	
	 
	public Calendar getDate() throws NumberFormatException
	{
		if (yearTextField.getText().length() != 4)
		{
			throw new NumberFormatException
					("You must enter a 4-digit year number!");
		}
		
		//parseInt may throw a NumberFormatException (i.e. run time exception)
		int year = Integer.parseInt(yearTextField.getText());
		int day = Integer.parseInt(dayTextField.getText());
				
		int month = monthNamesComboBox.getSelectedIndex();
		Calendar requestedDate = Calendar.getInstance();
		requestedDate.set(year,month,1,0,0,0);
		requestedDate.set(Calendar.MILLISECOND,0);
		//validate date
		int max = requestedDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (day < 1 || day > max)
		{
			throw new NumberFormatException
				("Day must be between 1 and " + max +"!");
		}
		
		requestedDate.set(Calendar.DATE,day);
		return requestedDate;
	}
	
	public void setDate(Calendar date)
	{
		monthNamesComboBox.setSelectedIndex(date.get(Calendar.MONTH));
		dayTextField.setText("" + date.get(Calendar.DAY_OF_MONTH));
		yearTextField.setText("" + date.get(Calendar.YEAR));
		
	}

}

