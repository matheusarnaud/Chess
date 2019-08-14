import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class serverTCP implements Runnable{
	private Socket s;
	private ArrayList<Socket> clients;
	
	public serverTCP(Socket s, ArrayList<Socket> clients){
		this.s = s;
		this.clients = clients;
	}

	public void run(){
		try{
		DataInputStream in = new DataInputStream(s.getInputStream());
		while(true){
			
			while(true){
				enviarMSG(in);
			}
		}
		}catch(IOException e){}
	}

	public void enviarMSG(DataInputStream in){
		try{
		for(Socket l : clients){
			if(l != s){
				String m = in.readUTF();
				DataOutputStream out = new DataOutputStream(l.getOutputStream());
				out.writeUTF(m);
			}
		}
		}catch(IOException e){}catch(NullPointerException d){}
	}
}