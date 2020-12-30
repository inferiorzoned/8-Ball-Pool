package server;


import client.Speed1;
import jfx.Speed;
import sample.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public static void main(String[] args) {
        new ServerClass();
    }
}


class ServerClass {

    int clientCount=0;

    ServerSocket serverSocket;
    public ServerClass(){
        try {
            serverSocket = new ServerSocket(55555);
            while (true){
                serveGame();
            }
        } catch (IOException e) {
            /*
            * show GUI
            * Server not open
             */
            System.out.println("Exception in opening server socket");
            //e.printStackTrace();
        }

    }

    public void serveGame() {
        Socket firstPlayerSocket =  null;
        Socket secondPlayerSocket = null;

        NetworkUtil networkUtil1=null;
        NetworkUtil networkUtil2=null;

        while (true){
            try {
                if(firstPlayerSocket==null){

                    firstPlayerSocket = serverSocket.accept();
                    networkUtil1=new NetworkUtil(firstPlayerSocket);
                    Object s = networkUtil1.read();
                    if(s instanceof Speed1){
                        System.out.println("hello i am speed");
                    }
                    System.out.println((clientCount+1)+" th player connected");
                    clientCount++;
                }
            } catch (Exception e) {
                System.out.println("Client not connected");
                //e.printStackTrace();
                continue;
            }

            try {
                if(networkUtil2==null){
                    System.out.println("In second ");
                    secondPlayerSocket = serverSocket.accept();
                    networkUtil2=new NetworkUtil(secondPlayerSocket);
                    networkUtil1=new NetworkUtil(firstPlayerSocket);
                    Object s = networkUtil1.read();
                    if(s instanceof Speed1){
                        System.out.println("hello i am speed");
                    }
                    System.out.println((clientCount+1)+" th player connected");
                    clientCount++;
                }
            }catch (Exception e){

                System.out.println("Client not connected");
                continue;
                //e.printStackTrace();
            }


            if(networkUtil1!=null){
                if(!networkUtil1.write("start+firstPlayer")){
                    System.out.println((clientCount-1)+" Disconnected");
                    clientCount--;
                    networkUtil1=networkUtil2;
                    networkUtil2=null;
                    firstPlayerSocket=secondPlayerSocket;
                    secondPlayerSocket=null;
                    continue;
                }
            }

            if(networkUtil2!=null){
                if(!networkUtil2.write("start+secondPlayer")){
                    System.out.println((clientCount-1)+" Disconnected");
                    clientCount--;
                    networkUtil2=null;
                    secondPlayerSocket=null;
                    continue;
                }
            }

            System.out.println("Start Game");

            new NewGame(networkUtil1,networkUtil2);
            networkUtil1=null;
            networkUtil2=null;
            firstPlayerSocket =  null;
            secondPlayerSocket = null;
        }
    }
}

