package com.electrodroid.bluetoothchatpc;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.bluetooth.*;
import javax.microedition.io.*;

/**
 * Class that implements an SPP Server which accepts single line of message from
 * an SPP client and sends a single line of response to the client.
 */
public class SimpleSPPServer {
	public static boolean receiveFlag = true;
	UUID uuid;
	StreamConnectionNotifier streamConnNotifier;
	StreamConnection connection;
	OutputStream outStream;
	PrintWriter pWriter;

	// start server
	void startServer() throws IOException {

		// Create a UUID for SPP
		uuid = new UUID("1101", true);
		// Create the servicve url
		String connectionString = "btspp://localhost:" + uuid
				+ ";name=Sample SPP Server";

		// open server url
		streamConnNotifier = (StreamConnectionNotifier) Connector
				.open(connectionString);

		// Wait for client connection
		System.out
				.println("\nServer Started. Waiting for clients to connect...");
		connection = streamConnNotifier.acceptAndOpen();

		RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
		System.out.println("Remote device address: "
				+ dev.getBluetoothAddress());
		System.out.println("Remote device name: " + dev.getFriendlyName(true));

		// initialize input and output streams
		outStream = connection.openOutputStream();
		pWriter = new PrintWriter(new OutputStreamWriter(outStream));

		ReceiveThread a = new ReceiveThread(connection);
		a.start();
		streamConnNotifier.close();

	}

	void write(String string) {
		// pWriter.write("This is Response String from SPP Server");
		pWriter.write(string + "\r\n");
		pWriter.flush();
	}

	protected void stop() {
		pWriter.close(); //closing the output stream
		receiveFlag = false; // stop receiving msgs
	}

	/*
	 * public static void main(String[] args) throws IOException {
	 * 
	 * //display local device address and name LocalDevice localDevice =
	 * LocalDevice.getLocalDevice();
	 * System.out.println("Address: "+localDevice.getBluetoothAddress());
	 * System.out.println("Name: "+localDevice.getFriendlyName());
	 * 
	 * SimpleSPPServer sampleSPPServer=new SimpleSPPServer();
	 * sampleSPPServer.startServer();
	 * 
	 * }
	 */
}

class ReceiveThread extends Thread {
	StreamConnection connection;
	transient String lineRead;
	BufferedReader bReader;
	InputStream inStream;
	final MainLayout ml1 = MainLayout.getMl();

	ReceiveThread(StreamConnection connection) {

		this.connection = connection;

		try {
			inStream = connection.openInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		bReader = new BufferedReader(new InputStreamReader(inStream));

	}

	public void run() {

		while (SimpleSPPServer.receiveFlag) {
			// read string from spp client

			try {
				while(!bReader.ready());
			} catch (IOException e1) {
				e1.printStackTrace();
			} // this block is for not letting program go ahead until bReader is ready.
				try {
					lineRead = bReader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			

			// if(lineRead!=null)
			System.out.println("andro: " + lineRead);
			ml1.setTextTaChatHere(ml1.getTextTaChatHere()+"\nandro: " + lineRead);
		}
		
		try {
			bReader.close();
		} catch (IOException e) {
			// gonna b called only when stop method is called.
		} 

	}
}