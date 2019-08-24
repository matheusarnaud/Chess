import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client{
	private int x;
	private int y;
	private Socket s;
	private Jogo jogo;
	private JanelaPrincipal j;
	
	public Client(Jogo jogo, JanelaPrincipal j){
		this.jogo = jogo;
		this.j = j;
		run();
	}
	
	//public static void main(String args[]){
		//JanelaPrincipal j = new JanelaPrincipal();
		//Jogo jogo = j.getJogo();
		//Client clientObj = new Client(jogo, j);
		//clientObj.run();
	//}
	
	public void run() {
		try{
			s = new Socket("127.0.0.1", 4444);
			DataInputStream in = new DataInputStream(s.getInputStream());
			ClientReceive r = new ClientReceive(in, jogo);
			Thread t = new Thread(r);
			t.start();
			atualiza a = new atualiza(j);
			Thread t2 = new Thread(a);
			t2.start();
			//while(true) {
				//enviaCasa(out);
			//}
		}catch(IOException e) {}
	}
	
	public void enviaCasa(int x0, int y0, int x1, int y1){
		try{	System.out.println("be");
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			System.out.println(" " +x0+" "+y0+" "+x1+" "+y1);
			out.writeInt(x0);
			out.writeInt(y0);
			out.writeInt(x1);
			out.writeInt(y1);
			System.out.println("here");
		}catch(Exception e){} 
	}
}