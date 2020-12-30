package tcpobject;

import java.net.ServerSocket;
import java.net.Socket;

import jfx.Speed;
import util.NetworkUtil;

public class Server {

	private String  player2Name;
	private String  player1Name;
	private Player player1;
	private Player player2;
	private ServerSocket ServSock;
	public NetworkUtil nc1;
	public NetworkUtil nc2;
	//public Hashtable<String,NetworkUtil> networktable;
	//public ArrayList<NetworkUtil> playertable;
	private int playerCount = 0;


	Server() {
		try {
		ServSock = new ServerSocket(44444);
		int count = 1;
		Speed s;
		while (true){
			Socket clientSock = ServSock.accept();
			if(playerCount == 0){
				System.out.println("hello "+playerCount);
			 	nc1 = new NetworkUtil(clientSock);
			    Object o = nc1.read();
			    if(o!= null ) {
				    System.out.println("null na");
					player1Name = (String)o;
				 	System.out.println(player1Name);
					if (o instanceof String) {
						System.out.println("string er instance :3");
						++playerCount;
						System.out.println(playerCount);
						//nc1.write(player1Name);
					//playertable.add(player1Name);
					//networktable.put(player1Name,nc);
						System.out.println(playerCount);
					//System.out.println((String) o);
				    }
				}
			}
			else if ((playerCount == 1)) {
				System.out.println("playerCount % 2");
				//networktable.get(player1Name).refresh();
				//networktable.get(player1Name).write(player2Name));
				//networktable.get(playerCount - 1).refresh();
				//networktable.get(playerCount - 1).write(playertable.get((playerCount - 1)));
				nc2 = new NetworkUtil(clientSock);
				Object o = nc2.read();
				if(o!= null ) {
					System.out.println("null na");
					player2Name = (String)o;
					System.out.println(player2Name);
					if (o instanceof String) {
						System.out.println("string er instance :3");
						++playerCount;
						player1 = new Player(player1Name,true);
						player2 = new Player(player2Name,false);
						nc1.write(player1);
						nc2.write(player2);
						Speed s1 = new Speed(0,10);
						s1.setBallX(250);
						s1.setBallY(325);
						nc2.write(s1);
						System.out.println(playerCount);
						//playertable.add(player1Name);
						//networktable.put(player1Name,nc);
						System.out.println(playerCount);
						//new ReadThreadServer(nc1,nc2,player1Name);
						new ReadThreadServer(nc1, nc2, "server");
						//new WriteThreadServer(nc1,nc2);
						/*while (true){
							System.out.println("yes");
						}*/
					}
				}
			}
			count++;
			System.out.println("true er sheshe");
		}
			//System.out.println(nc.read() + "," + count);
			//nc.write("Hello Client : " + count);

	} catch(Exception e) {
		System.out.println("Server starts:"+e);
	}
}

	public static void main(String args[]) {
		Server objServer = new Server();
	}
}
