package com.ibm.lnja.sametimenotifications.message.notification;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class SametimeNotificationFrame extends JFrame {

	JPanel panel;
	Dimension dimension;
	JLabel titleLabel, textLabel;
	
	public SametimeNotificationFrame(String title, String text){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dimension = new Dimension(200,200);
		panel = new JPanel();
		panel.setPreferredSize(dimension);
		this.setPreferredSize(dimension);
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.WHITE);
		setTitle(title);
		setNotificationTitle(title);
		setNotificationText(text);
		this.setContentPane(panel);
		this.setUndecorated(true);
		
	}
	
	private void setNotificationTitle(String title) {
		
		titleLabel = new JLabel(title);
		panel.add(titleLabel, BorderLayout.NORTH);
	}
	
	private void setNotificationText(String text){
		textLabel = new JLabel(text);
		panel.add(textLabel, BorderLayout.CENTER);
	}
}
