import java.io.*;
import java.net.*;

public class clientReceive implements Runnable{
	private DataInputStream in;
	
	public clientReceive(DataInputStream in){
		this.in = in;
	}

	public void run(){
		try{
			while(true){
			String m = in.readUTF();
			System.out.println(m);}
		}catch(IOException e){}
	}	
}