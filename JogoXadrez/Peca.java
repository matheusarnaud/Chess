
/**
 * Representa uma Pe�a do jogo.
 * Possui uma casa e um tipo associado.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public abstract class Peca 
{

    protected static final int PEAO_BRANCO = 0;
    protected static final int CAVALO_BRANCO = 1;
    protected static final int BISPO_BRANCO = 2;
    protected static final int TORRE_BRANCA = 3;
    protected static final int RAINHA_BRANCA = 4;
    protected static final int REI_BRANCO = 5;
    protected static final int PEAO_PRETO = 6;
    protected static final int CAVALO_PRETO = 7;
    protected static final int BISPO_PRETO = 8;
    protected static final int TORRE_PRETA = 9;
    protected static final int RAINHA_PRETA = 10;
    protected static final int REI_PRETO = 11;
    
    protected Casa casa;
    protected int tipo;
    protected boolean jaSeMoveu;

    public Peca(Casa casa, int tipo) 
    {
        this.casa = casa;
        this.tipo = tipo;
        jaSeMoveu = false;
        casa.colocarPeca(this);
    }
    
    /**
     * Movimenta a peca para uma nova casa.
     * @param destino nova casa que ira conter esta peca.
     */
    public abstract void mover(Casa destino, Jogo jogo);
    
    protected void irPara(Casa destino) 
    {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
    }
    
    /**
     * Valor    Tipo
     *   0   Branca (Pedra)
     *   1   Branca (Dama)
     *   2   Vermelha (Pedra)
     *   3   Vermelha (Dama)
     * @return o tipo da peca.
     */
    public int getTipo() 
    {
        return tipo;
    }
    
    protected boolean coresDiferentes(Peca outraPeca) 
    {
        if (this.corDaPeca() != outraPeca.corDaPeca()) {
            return true;
        }else{
            return false;
        }
    }
    
    public String corDaPeca() 
    {
        if (tipo >= 0 && tipo < 6) {
            return "branca";
        }else{
            return "preta";
        }
    }
    
    public boolean podeMover(Casa destino) 
    {
        if (!destino.possuiPeca()) {
            return true;
        }else if (this.coresDiferentes(destino.getPeca())) { 
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * @param origem casa atual da peca.
     * @param destino nova casa que ira conter esta peca.
     * @param tabuleiro é o tabuleiro do jogo atual.
     * @return 
     */
    public boolean temPecaPerto(Casa origem, Casa destino, Tabuleiro tabuleiro) 
    {
        int xi = origem.getX();
        int yi = origem.getY();
        int xf = destino.getX();
        int yf = destino.getY();
        //distancia entre a origem e o destino (em modulo).
        int absX = Math.abs(xf-xi);
        int absY = Math.abs(yf-yi);
        int n = 0;
        
        //se estiver movendo na vertical
        if (xf - xi == 0) {
            n = absY;
        }
        //se estiver movendo na horizontal ou na diagonal
        else if (yf - yi == 0 || absX == absY) {
            n = absX;
        }
        
        if (n != 0) {
            int sx = (xf-xi)/n; // sinal de x: (+1) se o x cresce, (-1) se diminui
            int sy = (yf-yi)/n; // mesma logica pra y
            int a = sx; 
            int b = sy;
            // checa todas as casas entre a origem e o destino,
            for (int i = 0; i < n -1; i++) {
                if (tabuleiro.getCasa(xi+a, yi+b).possuiPeca()) {
                    return true;
                }
                a = a + sx;
                b = b + sy;
            }
        }
        return false;
    }
    
    public void colocar(Casa origem)
    {
        irPara(origem);
    }
    
    public Casa getCasa()
    {
        return casa;
    }
    
    public int getPosicaoX()
    {
        return casa.getX();
    }
    
    public int getPosicaoY()
    {
        return casa.getY();
    }
}
