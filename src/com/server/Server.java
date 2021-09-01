package com.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Server implements Runnable{
	private Socket s;
	private Socket s2;
	
	public Server(Socket s, Socket s2) {
		this.s = s;
		this.s2 = s2;
	}
	
	public void run(){
		try{
			while(true){
				DataInputStream in = new DataInputStream(s.getInputStream());
				enviaInt(in);
			}
		}catch(IOException e){}
	}
	
	public void enviaInt(DataInputStream in) {
		try{
			int x0 = in.readInt();
			int y0 = in.readInt();
			int x1 = in.readInt();
			int y1 = in.readInt();
			DataOutputStream out = new DataOutputStream(s2.getOutputStream()); 
			out.writeInt(x0); 
			out.writeInt(y0); 
			out.writeInt(x1);
			out.writeInt(y1);
		} catch(Exception e){} 
	}
}
