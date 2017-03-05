/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nb_serverchat;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gio
 */
class myChannel {

    private Socket clientSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public myChannel() {
        clientSocket = null;
        dataInputStream = null;
        dataOutputStream = null;
    }

    public void myOpen(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
    }

    public void myClose() {
        try {
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
        } catch (IOException ex) {
            System.out.println("myClose :> " + ex);
            Logger.getLogger(myChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String myReadData() {

        String line = null;
        try {
            if (dataInputStream != null) {
                line = dataInputStream.readUTF();
            }
        } catch (IOException ex) {
            System.out.println("myReadData :> " + ex);
            Logger.getLogger(myChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
    }

    public boolean myWriteData(String line) {
        try {
            if (dataOutputStream != null) {
                dataOutputStream.writeUTF(line);
                dataOutputStream.flush();
                return true;
            }
        } catch (IOException ex) {
            System.out.println("myWriteData :> " + ex);            
            Logger.getLogger(myChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
