package dw314.lib.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PersonalDataPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField telephoneTextField;
	private JTextField reasonTextField;

	public PersonalDataPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Personal Data"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		add(getLabelsPanel(), BorderLayout.WEST);
		add(getTextFieldPanel(), BorderLayout.CENTER);
	}

	public String getFirstName() {
		return firstNameTextField.getText();
	}

	public String getLastName() {
		return lastNameTextField.getText();
	}

	public String getReason() {
		return reasonTextField.getText();
	}

	public String getTelephoneNumber() {
		if (isValidTelephone(telephoneTextField.getText()))
			return telephoneTextField.getText();
		else
			throw new RuntimeException("Invalid telephone number");
	}

	public void setFirstNameTextField(String firstName) {
		firstNameTextField.setText(firstName);
		firstNameTextField.setFocusable(true);
		firstNameTextField.selectAll();
	}

	public void setLastNameTextField(String lastName) {
		lastNameTextField.setText(lastName);
	}

	public void setTelephoneTextField(String telephone) {
		if (isValidTelephone(telephone))
			telephoneTextField.setText(telephone);
		else
			throw new RuntimeException("Invalid telephone number");
	}

	public void setReasonTextField(String reason) {
		reasonTextField.setText(reason);
	}

	// private methods

	private JPanel getLabelsPanel() {
		JPanel labelPanel = new JPanel(new GridLayout(4, 0, 5, 5));
		labelPanel.add(new JLabel("First Name"));
		labelPanel.add(new JLabel("Last Name"));
		labelPanel.add(new JLabel("Telephone # as (999)999-9999 "));
		labelPanel.add(new JLabel("Reason"));
		return labelPanel;
	}

	private JPanel getTextFieldPanel() {
		JPanel textFieldPanel = new JPanel(new GridLayout(4, 0, 5, 5));
		firstNameTextField = new JTextField();
		lastNameTextField = new JTextField();
		telephoneTextField = new JTextField();
		reasonTextField = new JTextField();
		textFieldPanel.add(firstNameTextField);
		textFieldPanel.add(lastNameTextField);
		textFieldPanel.add(telephoneTextField);
		textFieldPanel.add(reasonTextField);
		return textFieldPanel;
	}

	private boolean isValidTelephone(String phone) {
		boolean validPhone = true;
		if (!phone.matches("^\\((\\d{3})\\)(\\d{3})[-](\\d{4})$"))
			validPhone = false;
		return validPhone;
	}// end checkPhone method
}
