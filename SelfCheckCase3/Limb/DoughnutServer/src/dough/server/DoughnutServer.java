package dough.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.quickconnect.json.JSONException;
import org.quickconnect.json.JSONInputStream;
import org.quickconnect.json.JSONOutputStream;

public class DoughnutServer {
	String host;
	int port;
	ServerSocket aServerSocket;
	Socket aSocket;
	JSONOutputStream outStream;
	JSONInputStream inStream;
	ArrayList<String> doughnutList;
	
	public String getDoughnut(int dayOfWeek){
		return doughnutList.get(dayOfWeek - 1);
	}
	
	public void run(){
		doughnutList = new ArrayList<String>();
		doughnutList.add("Keep the Sabboth day holy!");
		doughnutList.add("Strawberry Shortcake");
		doughnutList.add("Irish Cream");
		doughnutList.add("Maple Bars");
		doughnutList.add("Jelly filled");
		doughnutList.add("Bear Claw");
		doughnutList.add("Apple Fritter");		
		
		try {
				aServerSocket = new ServerSocket(port);
			while(true){
				System.out.println("Listening...");
					aSocket = aServerSocket.accept();
				inStream = new JSONInputStream(aSocket.getInputStream());
				outStream = new JSONOutputStream(aSocket.getOutputStream());
				try{
					int dayOfWeek = Integer.parseInt((String) inStream.readObject());
					String doughnut = getDoughnut(dayOfWeek);
					outStream.writeObject(doughnut);
				}catch(NumberFormatException e){
					e.printStackTrace();
					outStream.writeObject("Invalid day!");
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public DoughnutServer(String pHost, int pPort){
		this.host = pHost;
		this.port = pPort;
	}
	
	public DoughnutServer(){
		host = "localhost";
		port = 98765;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DoughnutServer("localhost",8080).run();

	}

}
