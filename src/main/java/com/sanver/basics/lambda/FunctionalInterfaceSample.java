package com.sanver.basics.lambda;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FunctionalInterfaceSample {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Anonymous Class Sample");
		JButton button = new JButton("Click me..");
		button.addActionListener(actionEvent -> JOptionPane.showConfirmDialog(null, "Pressed", "Information",
				JOptionPane.DEFAULT_OPTION));
		frame.add(button);
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
