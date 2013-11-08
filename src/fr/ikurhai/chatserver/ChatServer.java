package fr.ikurhai.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/** 
 * Classe serveur du chat
 * 
 * @author ikurhai
 * @version 0.2
 */
public class ChatServer {

	private ServerSocket serverSocket;
	private int port;
	private String name;
	private String motd;
	private Scanner s;	


	/**
	 * Constructeur par défault
	 */
	public ChatServer() {

		// Paramétrage des différents attributs du serveur
		
		this.s = new Scanner(System.in);

		System.out.println("- ChatServer -");

		System.out.print("Enter server name: ");		
		this.name = s.nextLine();

		System.out.print("Enter port: ");		
		this.port = s.nextInt();
		s.nextLine();

		System.out.print("Enter MOTD: ");		
		this.motd = s.nextLine();

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Lance le processus du server
	 */
	public void run() {

		Socket clientSocket; 
		BufferedReader in;
		PrintWriter out; 

		try {

			System.out.println("Listening on " + port + "...");			

			// Boucle d'écoute du serveur
			while (true) {

				// On attend une connexion d'un client
				clientSocket = serverSocket.accept();	

				// Une fois le client connecté, on crée le buffer de lecture des données envoyées par le nouveau client
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				// Et on crée le flux d'envoi de données vers ce client
				out = new PrintWriter(clientSocket.getOutputStream());

				// Création du processus de réception des messages d'un client
				// (in.readLine() récupère le nom que le client a envoyé)
				new ReceiverThread(in.readLine(), clientSocket, in).start();

				// Envoi du nom et du MOTD du serveur au nouveau client
				out.println(name);
				out.flush();
				out.println(motd);
				out.flush();
				
				// Fin de la boucle, on attend la connexion d'un nouveau client
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
