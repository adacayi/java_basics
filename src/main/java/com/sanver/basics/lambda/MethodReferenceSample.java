package com.sanver.basics.lambda;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MethodReferenceSample {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Method Reference Sample");
		JButton button = new JButton("Click me..");
		button.addActionListener(new MethodReferenceSample()::showDialog);
		frame.add(button);
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void showDialog(ActionEvent event) {
		JOptionPane.showConfirmDialog(null, "Pressed", "Information", JOptionPane.DEFAULT_OPTION);
	}
}
