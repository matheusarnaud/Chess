package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RunServer {
	public static void main(String args[]) {
		try { 
			ServerSocket s = new ServerSocket(4444);
			Socket ns = s.accept();
			Socket ns2 = s.accept();
			Server a1 = new Server(ns, ns2);
			Server a2 = new Server(ns2, ns);
			Thread t1 = new Thread(a1); 
			Thread t2 = new Thread(a2);
			t1.start();
			t2.start();
		}catch(IOException e) {}
	}
}
