package edu.sku.hw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class SplashScreen extends JWindow {
	static Timer  msgTimer = null;
	static JLabel label;
	
	static int count = 1;
	static int TIMER_PAUSE = 25;
	static int TIMER_MAX   = 25;

	ActionListener myListener = new ActionListener() {
		@Override
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			if (TIMER_MAX == count) {
				msgTimer.stop();
				SplashScreen.this.setVisible(false);
			}
			count++;
		}
	};

	public SplashScreen() {
		Container container = getContentPane();

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0,0,0,0));
		panel.setBorder(new EtchedBorder());
		container.add(panel, BorderLayout.CENTER);

		label = new JLabel("                                              ");
		label.setFont(new Font("맑은고딕", Font.BOLD, 14));
		panel.add(label);

		pack();
		setLocationRelativeTo(null); //센터

		//setVisible(true);
		//msgShow("");
	}

	public void msgShow(String message) {
		label.setText(message);
		super.update(this.getGraphics());
		
		if (msgTimer == null) {
			msgTimer = new Timer(TIMER_PAUSE, myListener);
		}
		
		count = 1;
		msgTimer.start();
		SplashScreen.this.setVisible(true);
	}

	public void msgHide() {
		count = TIMER_MAX;
		msgTimer.stop();
		SplashScreen.this.setVisible(false);
	}
}
