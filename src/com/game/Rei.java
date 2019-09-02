package com.game;

/**
 * Write a description of class Rei here.
 * @author (your name)
 * @version (a version number or a date)
 */
public class Rei extends Peca
{
    /**
     * Constructor for objects of class Rei
     */
    public Rei(Casa casa, int tipo) 
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
       
            
            if (absX == absY && absX == 1) {
                irPara(destino);
                this.jaSeMoveu = true;
            }else if ((xf == xi && absY == 1) || (yf == yi && absX == 1)) {
                irPara(destino);
                this.jaSeMoveu = true;
            }
            
            if (yf == yi) {
                //se for um movimento legal de roque, realiza o roque.
                fazerRoque(destino, jogo);
                
            }
        
    }
    
    private void fazerRoque (Casa destino, Jogo jogo) {
        int xf = destino.getX();
        int yf = destino.getY();
        int xi = casa.getX();
        int yi = casa.getY();
        Tabuleiro tabuleiro = jogo.getTabuleiro();
        
        
        int x = 0;
        //'i' varia de (-2) a (+2) e 'j' varia de (0) a (7)
        for (int i = -2; i < 3; i = i+4) {
            int tipo1 = REI_BRANCO;
            int tipo2 = TORRE_BRANCA;
            for (int j = 0; j < 8; j = j+7) {
                if (xf == xi + i && this.tipo == tipo1 && this.jaSeMoveu == false) {
                    if (tabuleiro.getCasa(x, j).possuiPeca()) {
                        Peca that = tabuleiro.getCasa(x, j).getPeca();
                        if (that.tipo ==  tipo2 && that.jaSeMoveu == false) {
                            if (!temPecaPerto(this.casa, destino, tabuleiro)) {
                                this.irPara(destino);
                                that.irPara(tabuleiro.getCasa(destino, -i/2, 0));
                                this.jaSeMoveu = true;
                                that.jaSeMoveu = true;
                            }
                        }
                    }
                }
                tipo1 = REI_PRETO;
                tipo2 = TORRE_PRETA;
            }
            x = 7;
        }
    }
    
}