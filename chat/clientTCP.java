import java.io.*;
import java.net.*;
import java.util.Scanner;

public class clientTCP{
	public static void main(String args[]){
		try{
			Socket s = new Socket("127.0.0.1", 4444);
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			Scanner scan = new Scanner(System.in);
			clientReceive r = new clientReceive(in);
			System.out.println("DIGITE O SEU NOME:");
			String nome = scan.nextLine();
			Thread t = new Thread(r);
			t.start();
			while(true){
				String m = scan.nextLine();
				out.writeUTF(nome + ": " + m);
			}
		}catch(IOException e){}
	}
}
