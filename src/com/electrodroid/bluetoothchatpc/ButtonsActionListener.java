package com.electrodroid.bluetoothchatpc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;
import javax.swing.JButton;

public class ButtonsActionListener implements ActionListener {
	SimpleSPPServer sampleSPPServer;

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton jb = (JButton) event.getSource();
		String s = (String) jb.getLabel();
		// System.out.println(s);
		final MainLayout ml = MainLayout.getMl();

		if (s.equals("Turn On")) {

			Thread tostoplockhere = new Thread(new Runnable() {

				@Override
				public void run() {
					// display local device address and name
					LocalDevice localDevice = null;
					try {
						localDevice = LocalDevice.getLocalDevice();
					} catch (BluetoothStateException e) {
						e.printStackTrace();
					}
					System.out.println("Address: "
							+ localDevice.getBluetoothAddress());

					System.out.println("Name: " + localDevice.getFriendlyName());
					ml.setTextTaChatHere("Address: "
							+ localDevice.getBluetoothAddress() + " Name: "
							+ localDevice.getFriendlyName());
					System.out.println("turn on pressed");
					// ml.setTextTaChatHere("turn on pressed");

					// sampleSPPServer=new SimpleSPPServer();
					sampleSPPServer = new SimpleSPPServer();
					try {
						sampleSPPServer.startServer();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			});

			tostoplockhere.start();
		} else if (s.equals("Turn OFF")) {
			ml.setTextTaChatHere("turn off pressed");
			System.out.println("turn off pressd");
			sampleSPPServer.stop();
		} else if (s.equals("Send")) {

			sampleSPPServer.write(ml.getTextTfToSend());
			System.out.println("me: "+ml.getTextTfToSend());
			ml.setTextTaChatHere(ml.getTextTaChatHere()+"\nme: "+ml.getTextTfToSend());
		}

		return;

	}

}
