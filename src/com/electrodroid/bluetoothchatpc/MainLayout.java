package com.electrodroid.bluetoothchatpc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainLayout extends JFrame {
	private static MainLayout ml;
	
	public static MainLayout getMl() // a getter method but sumthing lyk factory method to obtain the frame object to change the fields
	{
		return ml;
	}

	private JLabel title;
	private JLabel lChatHere;
	private JTextArea taChatHere;
	private JButton bTurnOn;
	private JButton bTurnOff;
	private JLabel lToSend;
	private JTextField tfToSend;
	
	
	private JButton bSend;
	private JPanel panel;

	// constructor
	MainLayout() {
		setTitle("Bluetooth Chat App");
		setSize(700, 400); // this.setSize on current object
		
		initializeComponents();
		setPosition();
		this.setVisible(true); // main frame is visible now
		this.setLocation(100, 100);
		addListenerToButtons();

	}

	

	private void addListenerToButtons() {
		ButtonsActionListener bal = new ButtonsActionListener();
		bTurnOn.addActionListener(bal);
		bTurnOff.addActionListener(bal);
		bSend.addActionListener(bal);
	}

	private void initializeComponents() {
		title = new JLabel("Bluetooth Chat-SERVER", JLabel.CENTER);
		lChatHere = new JLabel("Chat Here: ");
		taChatHere = new JTextArea("Here will be the chat");
		//taChatHere.setEditable(false); // we can only see this chat cant edit
		taChatHere.setAutoscrolls(true);
		taChatHere.setWrapStyleWord(true);
		bTurnOn = new JButton("Turn On");
		bTurnOff = new JButton("Turn OFF");
		lToSend = new JLabel("To Send");
		tfToSend = new JTextField();
		tfToSend.setToolTipText("What to Send?");
		bSend = new JButton("Send");

		// adding the main panel in the frame
		panel = new JPanel();
		this.add(panel); // default is added at BorderLayout.Center

	}

	private void setPosition() {
		panel.setLayout(new GridBagLayout());
        //panel.setBackground(new Color(240,240,240));
		// adding main title in frame
		title.setFont(new Font("TimesNewRoman", Font.BOLD, 28));
		this.add(title, BorderLayout.NORTH);

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(8, 8, 8, 8); // TLBR

		// anchor all component west
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(lChatHere, c);

		c.gridx = 1;
		c.weightx = 1.0; // all space on x axes it will use
		c.weighty = 1.0;
		c.gridwidth = 3;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		panel.add(taChatHere, c);

		c.gridx = 4;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 0;
		c.weighty = 0;
		panel.add(bTurnOn, c);

		c.gridy = 1;
		c.gridx = 4;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		// bTurnOff.setBackground(new Color(50,60,100));
		panel.add(bTurnOff, c);

		c.gridx = 0;
		c.gridy = 2;
		panel.add(lToSend, c);

		c.gridx = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		panel.add(tfToSend, c);

		c.gridx = 4;
		c.gridwidth = 1;
		c.weightx = 0;
		panel.add(bSend, c);

	}

	public static void main(String args[]) {
		ml = new MainLayout();
		ml.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	//get the text to b sent
	public String getTextTfToSend() {
		return tfToSend.getText();
	}


// send the string to set as a argu from other class
	public void setTextTaChatHere(String s) {
		taChatHere.setText(s);
	}

	public String getTextTaChatHere(){
		return taChatHere.getText();
	}

}
