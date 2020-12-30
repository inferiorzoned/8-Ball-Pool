package server;

import sample.NetworkUtil;

public class NewGame {
    Thread thread;
    static int gameCount=0;
    NetworkUtil player1;
    NetworkUtil player2;
    boolean firstPlayerMove;//which player's move

    public NewGame(NetworkUtil player1, NetworkUtil player2) {
        this.player1 = player1 ;
        this.player2 = player2 ;
        System.out.println((gameCount+1)+" th game start.");
        gameCount++;
        startGame();
    }
    public void startGame(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                firstPlayerMove=true;
                while (true){
                    String moveData=receiveMoveData();
                    if(moveData==null){
                        System.out.println("Exiting game");
                        break;
                    }
                    sentMoveData(moveData);
                    if(firstPlayerMove)
                        firstPlayerMove=false;
                    else
                        firstPlayerMove=true;
                }
            }
        };
        thread=new Thread(runnable);
        thread.start();
    }

    public String receiveMoveData(){
        String s;

        if(firstPlayerMove){
            s= (String)player1.read();
        }
        else {
            s=  (String) player2.read();
        }
        if(s==null || s.equals("EXIT")){
            closeGame();
            return null;
        }
        return s;

    }
    public void sentMoveData(String data){
        if(!firstPlayerMove){
            if(!player1.write(data)){
                player2.write("EXIT");
            }
        }
        else {
            if(!player2.write(data))
                player1.write("EXIT");
        }
    }
    
    public void closeGame(){
        System.out.println("Player disconnected");

        /*
         * write your code here
         * Here show Player disconnected/game disconnected in GUI
         */

        try {
            thread.wait();
        } catch (Exception e) {
            System.out.println("error in waiting");
        }
    }
    


}
