
/**
 * O Tabuleiro do jogo. 
 * Respons�vel por armazenar as 64 casas.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Tabuleiro {

    private Casa[][] casas;

    public Tabuleiro()
    {
		casas = new Casa[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Casa casa = new Casa(x, y);
                casas[x][y] = casa;
            }
        }
    }
	
	
    /**
     * @param x linha
     * @param y coluna
     * @return Casa na posicao (x,y)
     */
    public Casa getCasa(int x, int y)
    {
        if((x <= 7) && (x >= 0) && (y <= 7) && (y >= 0))
        {
            return casas[x][y];
        }
        else
        {
            return null;
        }
    }
    
    public Casa getCasa(Casa casaAtual, int i, int j)
    {
        int x = casaAtual.getX();
        int y = casaAtual.getY();
        
        return casas[x + i][y + j];
    }
    
    /**
     * @param x linha
     * @param y coluna
     * @return true se existe casa na coordenada (x, y), caso contrario false.
     */
    public boolean existeCasa(int x, int y){
        if (x >= 0  && x <= 7 && y >=0 && y <= 7) {
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * @param x linha
     * @param y coluna
     * @return true se existe casa na coordenada (x, y), caso contrario false.
     */
    public boolean existeCasa(Casa casaAtual, int i, int j){
        int x = casaAtual.getX() + i;
        int y = casaAtual.getY() + j;
        
        if (x >= 0  && x <= 7 && y >=0 && y <= 7) {
            return true;
        }else{
            return false;
        }
    }
}
