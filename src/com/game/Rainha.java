package com.game;

/**
 * Write a description of class Rainha here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Rainha extends Peca
{
    
    /**
     * Constructor for objects of class Rainha
     */
    public Rainha(Casa casa, int tipo) 
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
        
        if (!temPecaPerto(casa, destino, tabuleiro)) {
            if (xf == xi || yf == yi || absX == absY) {
                irPara(destino);
            }
        }
    }
}
