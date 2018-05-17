package com.sanver.basics.swing;

import javax.swing.JFrame;
import javax.swing.JButton;

public class JFrameSample extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3188408548830104991L;

	public static void main(String[] args) {
		JFrameSample window = new JFrameSample();
		JButton button = new JButton("Click me..");
		button.setBounds(10,10,180,40);
		window.setTitle("JFrame Sample");
		window.setLayout(null);
		window.setSize(210, 200);
		window.add(button);
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setVisible(true);
	}

}
