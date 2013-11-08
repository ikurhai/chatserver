package fr.ikurhai.chatserver;

import java.io.PrintWriter;
import java.util.HashMap;


public class SendingThread extends Thread {

	private HashMap<String, PrintWriter> outs;


	public SendingThread() {
		super();
		this.outs = new HashMap<String, PrintWriter>();
	}


	public void addClient(String name, PrintWriter out) {
		outs.put(name, out);
	}

	public void sendMessage(String sender, String message) {
		for(String client : outs.keySet()) {
			if(!client.equals(sender)) {
			}
		}
	}

}
