package tcpsimple;

import jfx.Speed;
import util.NetworkUtil;

import javax.jws.Oneway;
import java.util.Scanner;

public class Client {
	public static void main(String args[]) {
		try {
			Object o ;
			Scanner input = new Scanner(System.in);
			String serverAddress = "127.0.0.1";
			int serverPort = 44444;
			NetworkUtil nc = new NetworkUtil(serverAddress,serverPort);
			System.out.println("enter your name : ");
			String s = input.nextLine();
			nc.write(s);
			Speed v1;
			while(true) {
				//game cholbe
				//jokhon button press korbo tokhon write korbe;
				System.out.println("enter velocity : ");
				double vel = input.nextDouble();
				System.out.println("enter angle : ");
				double ang = input.nextDouble();
				Speed v = new Speed(vel,ang);
				nc.refresh();
				nc.write(v);
				//write kora shesh
				//read korbe
				o = nc.read();
				if(o != null) {
					if (o instanceof String) {
						s = (String) o;
						System.out.println(s);
					}
					if (o instanceof Speed) {
						v1 = (Speed) o;
						System.out.println("it is speed " + v1.getVelocity() + " " + v1.getAngle());
					}
				}
			}
		} catch(Exception e) {
			System.out.println (e);
		}
	}
}

