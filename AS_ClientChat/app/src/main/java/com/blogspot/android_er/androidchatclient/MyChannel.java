package com.blogspot.android_er.androidchatclient;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Gio on 03/03/2017.
 */

public class MyChannel {

    private Socket clientSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public MyChannel() {
        clientSocket = null;
        dataInputStream = null;
        dataOutputStream = null;
    }

    public void myOpen(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        System.out.println("Client accettato: " + clientSocket);
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
    }

    public void myClose() throws IOException {
        if (clientSocket != null) {
            clientSocket.close();
            clientSocket = null;
        }
        if (dataInputStream != null) {
            dataInputStream.close();
            dataInputStream = null;
        }
        if (dataOutputStream != null) {
            dataOutputStream.close();
            dataOutputStream = null;
        }
    }

    public int dataAvailable() throws IOException {
        return dataInputStream.available();
    }
    public String myReadData() {

        String line = null;
        try {
            if (dataInputStream != null) {
                line = dataInputStream.readUTF();
            }
        } catch (IOException ex) {
            Logger.getLogger(MyChannel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return line;
    }

    public boolean myWriteDsta(String line) {
        try {
            if (dataOutputStream != null) {
                dataOutputStream.writeUTF(line);
                dataOutputStream.flush();
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(MyChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
