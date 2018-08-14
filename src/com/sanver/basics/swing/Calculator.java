package com.sanver.basics.swing;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {

	class DigitListener implements ActionListener {
		int digit;

		public DigitListener(int arg) {
			digit = arg;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			setCurrentValue(Integer.toString(digit));
		}
	}

	class OperatorListener implements ActionListener {
		String operator;

		public OperatorListener(String arg) {
			operator = arg;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (operator) {
			case "/":
			case "*":
			case "-":
			case "+":
				if (isLastOperationEqualOperation) {
					isLastOperationEqualOperation = false;
					setValue2(null);
				} else if (calculate())
					setResult(value1);

				setOperator(operator);
				setIsCurrentFirst(false);
				break;
			case ".":
				if (isCurrentDecimal)
					return;

				if (isSwitchedToSecond) {
					isSwitchedToSecond = false;
					setResult("0");
				}

				setResult(getResult() + ".");
				isCurrentDecimal = true;
				break;
			case "=":
				isLastOperationEqualOperation = true;
				calculate();
				setResult(value1);
				setIsCurrentFirst(true);
				break;
			case "C":
				clear();
				break;
			default:
				break;
			}
		}
	}

	private JFrame frmCalculator;
	private JPanel pnlCalculator;
	private BorderLayout borderLayout;
	private JTextField txtResult;
	private JPanel pnlKeys;
	private GridLayout grdLayout;
	private JButton[] btnDigits;
	private JButton[] btnOperators;
	private Font font = new Font(Font.DIALOG, Font.BOLD, 20);
	private Double value1;
	private Double value2;
	private String operator;
	private boolean isCurrentFirst;
	private boolean isCurrentDecimal;
	private boolean isSwitchedToSecond;
	private boolean isLastOperationEqualOperation;

	public Calculator() {
		int i, j;
		frmCalculator = new JFrame("Calculator 1.0");
		pnlCalculator = new JPanel();
		borderLayout = new BorderLayout();
		txtResult = new JTextField();
		txtResult.setFont(font);
		txtResult.setEditable(false);
		pnlKeys = new JPanel();
		grdLayout = new GridLayout(5, 4);
		pnlKeys.setLayout(grdLayout);
		pnlCalculator.setLayout(borderLayout);
		pnlCalculator.add(BorderLayout.NORTH, txtResult);
		pnlCalculator.add(BorderLayout.CENTER, pnlKeys);
		frmCalculator.setContentPane(pnlCalculator);
		btnDigits = new JButton[10];
		btnOperators = new JButton[7];
		JButton button;

		for (i = 0; i < 10; i++) {
			button = new JButton(Integer.toString(i));
			button.setFont(font);
			button.addActionListener(new DigitListener(i));
			btnDigits[i] = button;
		}

		btnOperators[0] = new JButton("C");
		btnOperators[1] = new JButton("/");
		btnOperators[2] = new JButton("*");
		btnOperators[3] = new JButton(".");
		btnOperators[4] = new JButton("-");
		btnOperators[5] = new JButton("+");
		btnOperators[6] = new JButton("=");

		for (JButton item : btnOperators) {
			item.setFont(font);
			item.addActionListener(new OperatorListener(item.getText()));
		}

		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				pnlKeys.add(btnDigits[9 - j - 3 * i]);
			}
			pnlKeys.add(btnOperators[i]);
		}

		pnlKeys.add(btnDigits[0]);

		for (j = 3; j < btnOperators.length; j++) {
			pnlKeys.add(btnOperators[j]);
		}

		clear();
	}

	public void setCurrentValue(String key) {
		String result = getResult();
		double resultValue;

		if (isSwitchedToSecond) {
			isSwitchedToSecond = false;
			result = "0";
		}

		if (result.equals("0"))
			result = key;
		else
			result += key;

		resultValue = Double.parseDouble(result);
		setResult(result);

		if (isCurrentFirst)
			value1 = resultValue;
		else
			value2 = resultValue;
	}

	public void setIsCurrentFirst(boolean value) {
		Double currentValue;

		if (value)
			currentValue = value1;
		else
			currentValue = value2;

		isCurrentFirst = value;
		isCurrentDecimal = currentValue != null && currentValue.intValue() != currentValue;
		isSwitchedToSecond = !value;
	}

	public void setValue1ToResult() {
		if (value1 == value1.intValue())
			setResult(Integer.toString(value1.intValue()));
		else
			setResult(value1.toString());
	}

	public Double getValue1() {
		return value1;
	}

	public void setValue1(Double value) {
		value1 = value;
	}

	public Double getValue2() {
		return value2;
	}

	public void setValue2(Double value) {
		value2 = value;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String value) {
		operator = value;
	}

	public String getResult() {
		return txtResult.getText();
	}

	public void setResult(Double value) {
		if (value == value.intValue())
			txtResult.setText(Integer.toString(value.intValue()));
		else
			txtResult.setText(value.toString());
	}

	public void setResult(String result) {
		txtResult.setText(result);
	}

	public boolean calculate() {
		if (operator == null || value2 == null)
			return false;

		switch (operator) {
		case "/":
			if (value2 != 0)
				value1 = value1 / value2;
			else {
				JOptionPane.showConfirmDialog(null, "Devision by zero is not defined.", "Warning",
						JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE);
			}
			break;
		case "*":
			value1 = value1 * value2;
			break;
		case "-":
			value1 = value1 - value2;
			break;
		case "+":
			value1 = value1 + value2;
			break;
		}

		return true;
	}

	public void show() {
		frmCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalculator.pack();
		frmCalculator.setVisible(true);
	}

	public void clear() {
		isCurrentFirst = true;
		isCurrentDecimal = false;
		isSwitchedToSecond = false;
		isLastOperationEqualOperation = false;
		value1 = 0d;
		value2 = null;
		operator = null;
		setResult("0");
	}

	public static void main(String[] args) {
		Calculator frmCalculator = new Calculator();
		frmCalculator.show();
	}
}
