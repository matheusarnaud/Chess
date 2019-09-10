
import java.io.DataInputStream;
import java.io.IOException;

public class ClientReceive implements Runnable{
	private DataInputStream in;
	private Jogo jogo;
	private JanelaPrincipal j;
	private Tabuleiro t;
	public String cor;
	
	public ClientReceive(DataInputStream in, JanelaPrincipal j) {
		this.in = in;
		this.jogo = j.getJogo();
		this.j = j;
		t = jogo.getTabuleiro();
		
	}
	
	public void run() {
		while(true) {
			recebeCasa();
		}
	}
	
	public void recebeCasa(){
		try{ 
			int origemX = in.readInt();
			int origemY = in.readInt();
			int destinoX = in.readInt();
			int destinoY = in.readInt();
			int p = in.readInt();
			if(p!=900){
				Casa c = t.getCasa(origemX, origemY);
				if(c.getPeca().getClass() == Peao.class){
					jogo.promover2(p, c.getPeca(), t.getCasa(origemX, origemY));
				}
			}
			jogo.moverPeca(origemX, origemY, destinoX, destinoY);
			j.atualizar();
		}catch(IOException e){} catch(NullPointerException n){}
	}
}