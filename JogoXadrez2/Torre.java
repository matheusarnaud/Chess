import java.util.ArrayList;
/**
 * Write a description of class Torre here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Torre extends Peca
{
    /**
     * Constructor for objects of class Torre
     */
    public Torre(Casa casa, int tipo) 
    {
        super(casa, tipo);
    }

    public void mover(Casa destino, Jogo jogo) 
    {
        int xf = destino.getX();
        int yf = destino.getY();
        int xi = casa.getX();
        int yi = casa.getY();
        Tabuleiro tabuleiro = jogo.getTabuleiro();
        
        if (!temPecaPerto(casa, destino, tabuleiro)) {
            if (xf == xi || yf == yi) {
                irPara(destino);
                this.jaSeMoveu = true;
            }
        }
    }
}
