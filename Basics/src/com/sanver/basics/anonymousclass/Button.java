package com.sanver.basics.anonymousclass;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Button {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Anonymous Class Sample");
		JButton button = new JButton("Click me..");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showConfirmDialog(null, "Pressed","Information",JOptionPane.DEFAULT_OPTION);
			}
		});
		frame.add(button);
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
