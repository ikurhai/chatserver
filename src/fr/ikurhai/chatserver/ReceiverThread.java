package fr.ikurhai.chatserver;

import java.io.BufferedReader;
import java.net.Socket;


/**
 * Thread de r�ception des messages d'un client
 * 
 * @author mlelievre
 * @version 0.1
 */
public class ReceiverThread extends Thread {

	public String user;
	private Socket socket;
	private BufferedReader in;
	

	/**
	 * Constructeur surcharg�
 	 * @param name Nom du thread correspondant au nom du client
	 * @param socket Socket du client
	 * @param in Buffer de lecture des donn�es envoy�es par le nouveau client
	 */
	public ReceiverThread(String name, Socket socket, BufferedReader in){
		super(name);
		this.user = name;
		this.socket = socket;
		this.in = in;
	}


	/**
	 * Processus
	 */
	public void run(){

		String message;

		try {

			System.out.println("Connection detected: " + user + ".");

			// Tant que l'on ne recoit pas de /quit
			do {
				message = in.readLine();
				System.out.println(user + "> " + message);
			} while (!message.equals("/quit"));

			// Fermeture du socket
			socket.close();
 
			System.out.println("Connection closed: " + user + ".");

		} catch (Exception e) {
			System.out.println("Connection interrupted: " + user + ".");
		}

	}       

}
