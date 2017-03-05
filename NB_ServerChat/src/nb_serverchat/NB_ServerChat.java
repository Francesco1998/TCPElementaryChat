/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nb_serverchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gio
 */
public class NB_ServerChat {

    /**
     * @param args the command line arguments
     */
    private boolean done = false;
    private int porta;

    private static Scanner scanner;
    private ServerSocket server;
    private ServerToClient serverClient;
    
    private int clientNumber;

    public NB_ServerChat(int port) {
        server = null;
        serverClient = null;
        porta = port;
        clientNumber = 1;
        myMain();
    }

    public void myOpenPort(int port) throws IOException {
        //cerco di connettermi alla porta
        System.out.println("connessione alla porta " + port + ", attendi ...");
        server = new ServerSocket(port);
        System.out.println("Server started: " + server); //stampo che il server è partito
    }

    public void myAccept() throws IOException {
        System.out.println("Attendo client " + clientNumber + " ...");
        Socket clientSocket = server.accept();
        System.out.println("Client " + clientNumber + " accettato: " + clientSocket);
        clientNumber++;
        serverClient = new ServerToClient();
        serverClient.myOpen(clientSocket);
        serverClient.start();
    }

    private void myMain() {
        try {
            myOpenPort(porta);
            while (!done) {
                myAccept();
            }
        } catch (IOException ex) {
            Logger.getLogger(nb_serverchat.NB_ServerChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        scanner = new Scanner(System.in);
        System.out.println("Su quale porta ci si deve mettere in ascolto?");
        int port = scanner.nextInt();
        new NB_ServerChat(port);
    }

}
