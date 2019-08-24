import java.net.*;
import java.io.*;
import java.awt.Color;

public class ClientReceive implements Runnable{
	private DataInputStream in;
	private Jogo jogo;
	
	public ClientReceive(DataInputStream in, Jogo jogo) {
		this.in = in;
		this.jogo = jogo;
	}
	
	public void run() {
		while(true) {
			recebeCasa();
		}
	}
	
	public void recebeCasa(){
		try{ System.out.println("AAA");
			int origemX = in.readInt();System.out.println("b");
			int origemY = in.readInt();System.out.println("c");
			int destinoX = in.readInt();System.out.println("d");
			int destinoY = in.readInt();
			jogo.moverPeca(origemX, origemY, destinoX, destinoY);
		}catch(IOException e){} catch(NullPointerException n){}
	}
}