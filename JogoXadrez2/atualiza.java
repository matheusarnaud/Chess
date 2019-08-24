
public class atualiza implements Runnable{
	private JanelaPrincipal j;
	
	public atualiza(JanelaPrincipal j){
		this.j = j;
	}
	
	public void run(){
		while(true){
			j.atualizar();
		}
	}
}