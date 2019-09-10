import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cor{
	public String cor;
	
	public Cor(){
		try{
			Socket s = new Socket("127.0.0.1", 4444);
			DataInputStream in = new DataInputStream(s.getInputStream());
			cor = in.readUTF();
		}catch(IOException e){}
	}
	
	public String cor(){
		return cor;
	}
}