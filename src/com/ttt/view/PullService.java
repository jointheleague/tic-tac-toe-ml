package com.ttt.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import com.ttt.TicTacToe;
import com.ttt.ai.PlayerConfiguration;
import com.ttt.pull.LocalPullService;
import com.ttt.pull.NetworkPullService;

public class PullService extends JPanel {
	private static final long serialVersionUID = -8280783537191203540L;

	public PullService(JDialog frame) {
		super(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0);

		JTextField url = new JTextField(20);
		JButton pull = new JButton("Pull");
		JButton cancel = new JButton("Cancel");

		pull.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PlayerConfiguration config = NetworkPullService.pullConfiguration(new URL(url.getText()));
					MenuService.registerConfiguration(config);
					TicTacToe.refreshMenu();
					frame.dispose();
				} catch (IOException | ReflectiveOperationException | InterruptedException e1) {
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

		JButton fromSystem = new JButton("From File");
		fromSystem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser files = new JFileChooser();
				files.setFileFilter(new FileFilter() {
					@Override
					public String getDescription() {
						return "Tic-Tac-Toe ZIP Archives (*.zip)";
					}

					@Override
					public boolean accept(File f) {
						return f.getName().endsWith(".zip");
					}
				});
				if (files.showOpenDialog(TicTacToe.FRAME) == JFileChooser.APPROVE_OPTION) {
					try {
						PlayerConfiguration config = LocalPullService.pullConfiguration(files.getSelectedFile());
						MenuService.registerConfiguration(config);
						TicTacToe.refreshMenu();
						frame.dispose();
					} catch (IOException | ReflectiveOperationException | InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		add(new JLabel("From URL:"), c);
		c.gridx++;
		add(url, c);
		c.gridx++;
		add(pull, c);
		c.gridx = 0;
		c.gridy++;
		add(fromSystem, c);
		c.gridx++;
		add(cancel, c);
	}
}
