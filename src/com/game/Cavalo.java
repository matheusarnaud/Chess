package com.game;

/**
 * @author:
 * @version:
 */

public class Cavalo extends Peca
{
    public Cavalo(Casa casa, int tipo) 
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
        
        if (absY == 2 && absX == 1) {
            irPara(destino);
        } else if (absY == 1 && absX == 2) {
            irPara(destino);
        }
    }
}
