import java.util.ArrayList;
/**
 * Write a description of class Bispo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bispo extends Peca
{
    /**
     * Constructor for objects of class Bispo
     */
    public Bispo(Casa casa, int tipo) 
    {
        super(casa, tipo);
    }

    public void mover(Casa destino, Jogo jogo) 
    {
        int xf = destino.getX();
        int yf = destino.getY();
        int xi = casa.getX();
        int yi = casa.getY();
        int absX = Math.abs(xf - xi);
        int absY = Math.abs(yf - yi);
        Tabuleiro tabuleiro = jogo.getTabuleiro();
       
        if (absX == absY && !temPecaPerto(casa, destino, tabuleiro)) {
            irPara(destino);
        }
    }
}
