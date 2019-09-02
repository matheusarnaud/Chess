package com.game;

/**
 * Write a description of class Peao here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Peao extends Peca
{
    private boolean enPassant;
    /**
     * Constructor for objects of class Peao
     */
    public Peao(Casa casa, int tipo) 
    {
        super(casa, tipo);
        enPassant = false;
    }

    public void mover(Casa destino, Jogo jogo)
    {
        int xf = destino.getX();
        int yf = destino.getY();
        int xi = casa.getX();
        int yi = casa.getY();
        Tabuleiro tabuleiro = jogo.getTabuleiro();
        
        //movimento normal
        if (xf == xi) {
            if (!destino.possuiPeca() && !temPecaPerto(casa, destino, tabuleiro)) {
                //Movimento do peao branco.
                if (tipo == PEAO_BRANCO && yf == yi + 1) {
                    irPara(destino);
                    enPassant = false;
                }
                //Movimento do peao preto.
                else if (tipo == PEAO_PRETO && yf == yi - 1) {
                    irPara(destino);
                    enPassant = false;
                }
                //Andar 2 casas com o peao branco quando ele está na sua casa inicial.
                else if (tipo == PEAO_BRANCO && yi == 1 && yf == yi + 2) {
                    irPara(destino);
                    enPassant = true;
                }
                //Andar 2 casas com o peao branco quando ele está na sua casa inicial.
                else if (tipo == PEAO_PRETO && yi == 6 && yf == yi - 2) {
                    irPara(destino);
                    enPassant = true;
                }
            }
        }
        //movimento de captura
        if (Math.abs(xf - xi) == 1) {
            capturar(destino, jogo);
        }
    }
    
    public void capturar(Casa destino, Jogo jogo) 
    {
        int xf = destino.getX();
        int yf = destino.getY();
        int xi = casa.getX();
        int yi = casa.getY();
        Tabuleiro tabuleiro = jogo.getTabuleiro();
        
        //captura normal
        if (destino.possuiPeca()) {
            //Captura com peao branco.
            if (tipo == PEAO_BRANCO && yf == yi + 1) {
                irPara(destino);
                enPassant = false;
            }
            //Captura com peao preto.
            else if (tipo == PEAO_PRETO && yf == yi - 1) {
                irPara(destino);
                enPassant = false;
            }
        } else { //captura en-passant
            Peao peaoAnterior = null;
            if (jogo.pecaAnterior.tipo == PEAO_BRANCO || jogo.pecaAnterior.tipo == PEAO_PRETO) {
                peaoAnterior = (Peao) jogo.pecaAnterior;
            }
            //checa se o movimento é uma das 4 opções de captura en-passant válidas
            for (int i = 1; i > -2; i = i-2) {
                int tipoAtual = PEAO_BRANCO;
                for (int j = 1; j > -2; j = j-2) {
                    //checa a casa à direita (+1, 0) e à esquerda (-1, 0) do peao clicado
                    if (peaoAnterior != null && tabuleiro.existeCasa(this.casa, +i, 0)) {
                        if (peaoAnterior.enPassant == true && tabuleiro.getCasa(this.casa, +i, 0).possuiPeca()) {
                            Peca that = tabuleiro.getCasa(this.casa, +i, 0).getPeca();
                            if (that == peaoAnterior && this.tipo == tipoAtual && this.coresDiferentes(that)) {
                                if (xf == xi + i && yf == yi + j) {
                                    this.irPara(destino);
                                    this.enPassant = false;
                                    that.casa.removerPeca();
                                }
                            }
                        }
                    }
                    tipoAtual = PEAO_PRETO;
                }
            }
        }
    }
}
