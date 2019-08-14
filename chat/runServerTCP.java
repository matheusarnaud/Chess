import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class runServerTCP{
	public static void main(String args[]){
		try{
		ServerSocket s = new ServerSocket(4444);
		ArrayList<Socket> clients = new ArrayList<>();
		
		while(true){
			Socket ns = s.accept();
			clients.add(ns);
			serverTCP f = new serverTCP(ns, clients);
			Thread t = new Thread(f);
			t.start();
		}
		}catch(IOException e){}
	}
}