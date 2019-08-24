import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class runServer{
	public static void main(String args[]) {
		try { 
			ServerSocket s = new ServerSocket(4444);
			Socket ns = s.accept();
			Socket ns2 = s.accept();
			server a1 = new server(ns, ns2);
			server a2 = new server(ns2, ns);
			Thread t1 = new Thread(a1); 
			Thread t2 = new Thread(a2);
			t1.start();
			t2.start();
		}catch(IOException e) {}
	}
}
