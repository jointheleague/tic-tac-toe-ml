package com.ttt.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ttt.ai.PlayerConfiguration;
import com.ttt.net.NetworkService;

public class PullService extends JPanel {
	private static final long serialVersionUID = -8280783537191203540L;

	public PullService(JDialog frame) {
		JTextField url = new JTextField(20);
		JButton pull = new JButton("Pull");
		JButton cancel = new JButton("Cancel");

		pull.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PlayerConfiguration config = NetworkService.pullConfiguration(new URL(url.getText()));
					System.out.println(config);
				} catch (IOException | ReflectiveOperationException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		add(url);
		add(pull);
		add(cancel);
	}
}
