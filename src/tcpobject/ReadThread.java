package tcpobject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import jfx.Speed;
import jfx.Test;
import util.NetworkUtil;

import java.util.Scanner;
import java.util.Stack;

public class ReadThread implements Runnable {
	private Thread thr;
	private NetworkUtil nc;
	private Player player;
	public Stage primaryStage;

	public ReadThread(NetworkUtil nc,Stage primaryStage){
		this.nc = nc;
		this.primaryStage = primaryStage;
		this.thr = new Thread(this);
		thr.start();
	}

	public void run() {
		try {
			Object o;
			double vel;
			Speed obj = null;
			Scanner input = new Scanner(System.in);
			while (true){
				o = nc.read();
				if (o instanceof Player) {
					player = (Player) o;
					System.out.println("inside y/n");
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								new Play(player,nc,primaryStage);
							}
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					//break;
				}
			}
			/*
			while(true) {
					System.out.println("is not null");
					if (o instanceof String) {
						s = (String) o;
						System.out.println("khelchi "+s+" er shathe");
						if(s.equals("no")) {
						*/
				//System.out.println("in RT "+player.isMyturn());
				/*while (player.isMyturn()){
					System.out.println("enter velocity : ");
					vel = input.nextDouble();
					System.out.println("enter angle : ");
					double ang = input.nextDouble();
					Speed v = new Speed(vel, ang);
					nc.refresh();
					nc.write(v);
					if(v.getVelocity() == 0){
						player.setMyturn(false);
					}
				}
				while (!player.isMyturn()){
					o = nc.read();
					if(o instanceof Speed) {
						System.out.println("is a speed!!");
						obj = (Speed) o;
						System.out.println(obj.getVelocity() + " " + obj.getAngle());
						if(obj.getVelocity()==0){
							player.setMyturn(true);
						}
					}

				}
			}*/
		} catch(Exception e) {
			System.out.println (e);
		}
		nc.closeConnection();
	}
}



