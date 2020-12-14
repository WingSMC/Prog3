package com.kshsly.subnetcalc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.tree.TreePath;

public class SubnetCalcFrame extends JFrame {
	private static final long serialVersionUID = 376079434259498206L;
	private NetworkData networks;

	public SubnetCalcFrame() {
		super("Subnet Calculator");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(700, 200));
		this.setLayout(new BorderLayout());

		// Early declaration so I can set content in catch blocks
		JTextField messages = new JTextField();

		// Load data into the tree
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("networks.dat"))) {
			this.networks = new NetworkData((Network)ois.readObject());
		} catch(FileNotFoundException e) {
			try {
				this.networks = new NetworkData(new Network(new short[]{0, 0, 0, 0}, 0));
				this.writeDataToFile();
			} catch (Exception e1) {
				e1.printStackTrace();
				messages.setText(e.getMessage());
			}
		} catch(Exception e) {
			e.printStackTrace();
			messages.setText(e.getMessage());
		}


		JPanel treePanel = new JPanel(new BorderLayout());
			JPanel buttonPanel = new JPanel(new GridLayout(0,3));
				JButton removeButton = new JButton("Delete");
				buttonPanel.add(removeButton);
				JButton addByIPButton = new JButton("Add by IP");
				buttonPanel.add(addByIPButton);
				JButton addBySizeButton = new JButton("Add by size");
				buttonPanel.add(addBySizeButton);
			treePanel.add(buttonPanel, BorderLayout.NORTH);
			JTree networkTree = new JTree(this.networks);
			treePanel.add(new JScrollPane(networkTree), BorderLayout.CENTER);

			JPanel rightPanel = new JPanel(new GridLayout(2,0));
				JPanel addressPanel = new JPanel();
					JTextField octet1 = new JTextField(3);
					JTextField octet2 = new JTextField(3);
					JTextField octet3 = new JTextField(3);
					JTextField octet4 = new JTextField(3);
					JTextField mask = new JTextField(2);
					addressPanel.add(octet1);
					addressPanel.add(new JLabel("."));
					addressPanel.add(octet2);
					addressPanel.add(new JLabel("."));
					addressPanel.add(octet3);
					addressPanel.add(new JLabel("."));
					addressPanel.add(octet4);
					addressPanel.add(new JLabel("/"));
					addressPanel.add(mask);
				rightPanel.add(addressPanel);

				JPanel addBySizePanel = new JPanel();
					addBySizePanel.add(new JLabel("Number of hosts: "));
					JTextField size = new JTextField(10);
					addBySizePanel.add(size);
				rightPanel.add(addBySizePanel);

		JSplitPane mainPane = new JSplitPane(SwingConstants.VERTICAL, treePanel, rightPanel);
		this.add(mainPane, BorderLayout.CENTER);
			// messages textfield declared at the beginning of the ctor
			messages.setEditable(false);
		this.add(messages, BorderLayout.SOUTH);

		// Listeners
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				writeDataToFile();
			}
		});
		

		removeButton.addActionListener(ev -> {
			try {
				Network selected;
				TreePath currentPath = null;
				try {
					currentPath = networkTree.getSelectionPath();
					selected = (Network) currentPath.getLastPathComponent();
					networks.removeNodeFromParent(selected);
				} catch(NullPointerException e1) {
					throw new Exception("No network is selected.");
				}
			} catch (Exception e) {
				messages.setText(e.getMessage());
			}
		});

		// This doesn't validate based on parent network
		addByIPButton.addActionListener(ev -> {
			try {
				Network selected;
				TreePath currentPath = null;
				try {
					currentPath = networkTree.getSelectionPath();
					selected = (Network) currentPath.getLastPathComponent();
				} catch(NullPointerException e1) {
					selected = (Network) networks.getRoot();
				}
				networks.insertNodeInto(
					new Network(new short[]{
						Short.valueOf(octet1.getText()),
						Short.valueOf(octet2.getText()),
						Short.valueOf(octet3.getText()),
						Short.valueOf(octet4.getText()),
					}, Integer.valueOf(mask.getText())),
					selected, selected.getChildCount()
				);
				if(currentPath != null)
					networkTree.expandPath(currentPath);
			} catch (Exception e) {
				messages.setText(e.getMessage());
			}
		});
		
		addBySizeButton.addActionListener(ev -> {
			try {
				Network selected;
				TreePath currentPath = null;
				try {
					currentPath = networkTree.getSelectionPath();
					selected = (Network) currentPath.getLastPathComponent();
				} catch(NullPointerException e1) {
					selected = (Network) networks.getRoot();
				}
				networks.insertNodeInto(
					new Network(selected, Long.valueOf(size.getText())),
					selected, selected.getChildCount()
				);
				if(currentPath != null)
					networkTree.expandPath(currentPath);
			} catch (Exception e) {
				messages.setText(e.getMessage());
			}
		});
		this.setVisible(true);
	}

	private void writeDataToFile() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("networks.dat"))) {
			oos.writeObject(networks.getRoot());
			oos.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String... args) {
		new SubnetCalcFrame();
	}
}
