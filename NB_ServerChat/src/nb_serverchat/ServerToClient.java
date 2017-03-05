package nb_serverchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerToClient extends Thread {

    private boolean done;
    private String nome;
    private Scanner scanner;
    private myChannel channelToClient;

    public ServerToClient() {
        scanner = new Scanner(System.in);
        channelToClient = new myChannel();
        nome = "";
        done = false;
    }

    public void myOpen(Socket clientSocket) throws IOException {
        channelToClient.myOpen(clientSocket);
    }

    public void run() {
        String clientLine = "";
        String userLine = null;
        nome = channelToClient.myReadData();
        System.out.println("iniziata chat con " + nome);
        while (!done) {
            if (!clientLine.equalsIgnoreCase("bye")) {
                System.out.print("scrivi qualcosa a " + nome + ": ");
                userLine = scanner.nextLine();
                channelToClient.myWriteData(userLine);

                clientLine = channelToClient.myReadData();
                if (clientLine != null) {
                    System.out.println(nome + " ha scritto: " + clientLine + "\n");
                } else {
                    done = true;
                    channelToClient.myClose();
                }
            } else {
                done = true;
                channelToClient.myClose();
            }
        }
    }
}
