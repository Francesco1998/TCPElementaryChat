package nb_serverchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerClient extends Thread {

    static boolean done = false;
    static int porta = 3333;
    String nome;

    private Scanner scanner;
    private myChannel channelToClient;

    public ServerClient() {
        scanner = new Scanner(System.in);
        channelToClient = new myChannel();
        nome = "";
    }

    public void myOpen(Socket clientSocket) throws IOException {
        channelToClient.myOpen(clientSocket);
    }

    public void run() {
        String clientLine = "";
        String userLine = null;
        nome = channelToClient.myReadData();
        System.out.println(nome);
        while (!done) {
            if (!clientLine.equalsIgnoreCase("bye")) {
                userLine = scanner.nextLine();
                channelToClient.myWriteData(userLine);

                clientLine = channelToClient.myReadData();
                System.out.println(nome + " ha scritto: " + clientLine + "\n");
            } else {
                done = true;
                channelToClient.myClose();
            }
        }
    }
}
