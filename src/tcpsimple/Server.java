package tcpsimple;

import jfx.Speed;
import util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class Server {

	private ServerSocket ServSock;
	public ArrayList<String> playertable;
	public ArrayList<NetworkUtil> networktable;
	private int playerCount = 0;
	
	Server() {
		try {
			ServSock = new ServerSocket(44444);
			int count = 1;
			Speed s;
			String name;
			 while (true){
				 System.out.println("hello "+playerCount);
				Socket clientSock = ServSock.accept();
				NetworkUtil nc = new NetworkUtil(clientSock);
				Object o = nc.read();
				if(o!= null) {
					System.out.println("null na");
					name = (String)o;
					System.out.println(name);
					if (o instanceof String) {
						System.out.println("string er instance :3");
						++playerCount;
						System.out.println(playerCount);
						playertable.add(name);
						networktable.add(nc);
						System.out.println(playerCount);
						//System.out.println((String) o);
						if ((playerCount % 2) == 0) {
							System.out.println("playerCount % 2");
							networktable.get(playerCount).refresh();
							networktable.get(playerCount).write(playertable.get(playerCount));
							networktable.get(playerCount - 1).refresh();
							networktable.get(playerCount - 1).write(playertable.get((playerCount - 1)));
						}
					}
					else if (o instanceof Speed) {
						s = (Speed) o;
						System.out.println("it is speed " + s.getVelocity() + " " + s.getAngle());
					}
				}
				//System.out.println(nc.read() + "," + count);
				//nc.write("Hello Client : " + count);
				count++;
				 System.out.println("true er sheshe");
			}
		} catch(Exception e) {
			System.out.println("Server starts:"+e);
		}
	}
	
	public static void main(String args[]) {
		Server objServer = new Server();
	}
}

