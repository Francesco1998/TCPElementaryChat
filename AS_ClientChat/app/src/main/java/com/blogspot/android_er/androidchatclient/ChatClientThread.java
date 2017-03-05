package com.blogspot.android_er.androidchatclient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Gio on 03/03/2017.
 */

public class ChatClientThread extends Thread {

    public final static int MSG = 1;
    public final static int TOAST = 2;

    private String name, msgLog;
    private String dstAddress;
    private int dstPort;
    private Handler handler;

    private String msgToSend = "";
    private boolean fine;

    public ChatClientThread(String name, String address, int port) {
        this.name = name;
        dstAddress = address;
        dstPort = port;
        msgLog = "";
        fine = false;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        Socket socket = null;
        MyChannel myChannelToClient = new MyChannel();

        try {
            socket = new Socket(dstAddress, dstPort);
            myChannelToClient.myOpen(socket);
            myChannelToClient.myWriteDsta(name);

            while (!fine) {
                if (myChannelToClient.dataAvailable() > 0) {
                    msgLog = myChannelToClient.myReadData();
                    showInUI("from server: " + msgLog, MSG);
                }

                if (!msgToSend.equals("")) {
                    myChannelToClient.myWriteDsta(msgToSend);
                    msgToSend = "";
                }
                sleep(200);
            }

            myChannelToClient.myClose();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            final String eString = e.toString();
            showInUI(eString, TOAST);
        } catch (IOException e) {
            e.printStackTrace();
            final String eString = e.toString();
            showInUI(eString, TOAST);
        } catch (InterruptedException e) {
            e.printStackTrace();
            final String eString = e.toString();
            showInUI(eString, TOAST);
        }
    }

    private void showInUI(String msgLog, int type) {
        Message msg = new Message();
        Bundle bundle = new Bundle();

        bundle.putString("msg", msgLog);
        bundle.putInt("type", type);
        msg.setData(bundle);

        handler.sendMessage(msg);
    }

    public void sendMsg(String msg) {
        msgToSend = msg;
    }

    public void exit() {
        fine = true;
    }
}