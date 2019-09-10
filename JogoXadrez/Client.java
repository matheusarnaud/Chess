
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client{
	public Socket s;
	public DataInputStream in;
	private Jogo jogo;
	private JanelaPrincipal j;
	
	public Client(JanelaPrincipal j){
		this.j = j;
		this.jogo = jogo;
		run();
	}
	
	//public static void main(String args[]){
		//Client clientObj = new Client();
		//clientObj.run();
	//}
	
	public void run() {
		try{
			s = new Socket("127.0.0.1", 4444);
			this.in = new DataInputStream(s.getInputStream());
			ClientReceive r = new ClientReceive(in, j);
			Thread t = new Thread(r);
			t.start();
		}catch(IOException e){}
	}
	
	public void enviaCasa(int x0, int y0, int x1, int y1){
		try{
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			out.writeInt(x0);
			out.writeInt(y0);
			out.writeInt(x1);
			out.writeInt(y1);
		}catch(Exception e){} 
	}
	
	public void enviaPromocao(int n){
		try{
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			out.writeInt(n);
		}catch(IOException e){}
	}
	
	public void e(int n){
		try{
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			out.writeInt(n);
		}catch(IOException e){}
	}
}