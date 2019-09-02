package com.game;

import java.util.ArrayList;

/**
 * Armazena o tabuleiro e responsavel por posicionar as pecas.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Jogo 
{
    private Tabuleiro tabuleiro;
    private boolean turnoDaBranca;
    public Peca pecaAnterior;
    private Peca pecaClicada;
    private Casa destino;
    private Casa origem;
	private Casa origem2;
    private boolean reiBranco;
    private boolean reiPreto;
    private boolean cavalo;
    private boolean bispo;
    private boolean temPeca;
    private boolean xeque;
    public boolean empate;
    public boolean afogado;
    public boolean brancas;
    public boolean pretas;
	public boolean v = false;
    public Jogador jogador1;
    public Jogador jogador2;
    public Jogador inimigo;
    public Jogador jogador;
    private ArrayList<Casa> casasDoRei;
	

    public Jogo() 
    {
        tabuleiro = new Tabuleiro();
        turnoDaBranca = true;
        pecaAnterior = null;
        pecaClicada = null;
        destino = null;
        criarPecas();
        jogador1 = new Jogador("branca", this);
        jogador2 = new Jogador("preta", this);
        jogador = jogador1;
        inimigo = jogador2;
	}

    /**
     * Posiciona pe�as no tabuleiro.
     * Utilizado na inicializa�ao do jogo.
     */
    private void criarPecas() 
    {
        //criar peoes
        for (int x = 0; x < 8; x++) {
            Peca peca1 = new Peao(tabuleiro.getCasa(x, 1), Peca.PEAO_BRANCO);
            Peca peca2 = new Peao(tabuleiro.getCasa(x, 6), Peca.PEAO_PRETO);
        }
        //criar outras pecas
        Peca peca01 = new Torre(tabuleiro.getCasa(0, 0), Peca.TORRE_BRANCA);
        Peca peca02 = new Torre(tabuleiro.getCasa(7, 0), Peca.TORRE_BRANCA);
        Peca peca03 = new Torre(tabuleiro.getCasa(0, 7), Peca.TORRE_PRETA);
        Peca peca04 = new Torre(tabuleiro.getCasa(7, 7), Peca.TORRE_PRETA);
        Peca peca05 = new Cavalo(tabuleiro.getCasa(1, 0), Peca.CAVALO_BRANCO);
        Peca peca06 = new Cavalo(tabuleiro.getCasa(6, 0), Peca.CAVALO_BRANCO);
        Peca peca07 = new Cavalo(tabuleiro.getCasa(1, 7), Peca.CAVALO_PRETO);
        Peca peca08 = new Cavalo(tabuleiro.getCasa(6, 7), Peca.CAVALO_PRETO);
        Peca peca09 = new Bispo(tabuleiro.getCasa(2, 0), Peca.BISPO_BRANCO);
        Peca peca10 = new Bispo(tabuleiro.getCasa(5, 0), Peca.BISPO_BRANCO);
        Peca peca11 = new Bispo(tabuleiro.getCasa(2, 7), Peca.BISPO_PRETO);
        Peca peca12 = new Bispo(tabuleiro.getCasa(5, 7), Peca.BISPO_PRETO);
        Peca peca13 = new Rainha(tabuleiro.getCasa(3, 0), Peca.RAINHA_BRANCA);
        Peca peca14 = new Rainha(tabuleiro.getCasa(3, 7), Peca.RAINHA_PRETA);
        Peca peca15 = new Rei(tabuleiro.getCasa(4, 0), Peca.REI_BRANCO);
        Peca peca16 = new Rei(tabuleiro.getCasa(4, 7), Peca.REI_PRETO);
    }

    /**
     * Comanda uma Pe�a na posicao (origemX, origemY) fazer um movimento 
     * para (destinoX, destinoY).
     * 
     * @param origemX linha da Casa de origem.
     * @param origemY coluna da Casa de origem.
     * @param destinoX linha da Casa de destino.
     * @param destinoY coluna da Casa de destino.
     */
    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) throws NullPointerException{
        origem = tabuleiro.getCasa(origemX, origemY);
        origem2 = origem;
        destino = tabuleiro.getCasa(destinoX, destinoY);
        Peca peca = destino.getPeca();
        pecaClicada = origem.getPeca();
        
        //Verifica se o jogador está em xeque.
       
		if(!jogador.getXeque()){
            //Verifica se o jogo está empatado.
            if(!empate){
                //Verifica o turno.
                if(verificaTurno(pecaClicada)  && pecaClicada.podeMover(destino)) {
                    //Move a peca para o destino sem move-lá definitivamente.
                    pecaClicada.colocar(destino);
                    jogador.getCasas();
                    jogador.estaEmXeque();
                    //Verifica se o jogador está em xeque depois de mover a peca.
                    //Se não estiver move a peca definitivamente para o destino
                    if(!jogador.getXeque())
                    { 
                        pecaClicada.colocar(origem2);
                        //Se no destino tinha uma peca recria ela.
                        if(peca != null)
                        {
                            novaPeca(destino, peca);
                        }
                        pecaClicada.mover(destino, this);
                        jogador.getCasas();
                        pecaAnterior = pecaClicada;
                        jogador.estaEmXeque();

                        //Verifica se empatou.
                        empate();
                        //Verifica se o rei está afogado.
                        afogamento();
                    }
                    //Se o jogador estiver em xeque deespois de mover coloca a peca devolta na origem e cria uma nova peca se no destino tinha uma.
                    if(jogador.getXeque())
                    {
                        pecaClicada.colocar(origem2);
                        novaPeca(destino, peca);
                        jogador.xeque = false;
                        return;
                    }
                    //Muda o turno se a casa clicada for igual ao destino.
                    if(pecaClicada.casa == destino)
                    {
                        mudaTurno(); 
                    }
                    return;    
                }
            }
            else{
                //Não faz nada.
            }
        }
        //Verifica se o jogador está em xeque.
        if(jogador.getXeque())
        {
            //Verifica se a peca clicada for da classe rei.
            if(pecaClicada.getClass() == Rei.class)
            {
                pecaClicada.colocar(destino);
                jogador.getCasas();
                jogador.estaEmXeque();
                //Mesma lógica de cima.
                if(!jogador.getXeque())
                {
                    pecaClicada.colocar(origem2);

                    if(peca != null)
                    {
                        novaPeca(destino, peca);
                    }
                    pecaClicada.mover(destino, this);
                    if (pecaClicada.casa == destino && pecaClicada.podeMover(destino)) {
                        jogador.getCasas();
                        pecaAnterior = pecaClicada;
                        jogador.estaEmXeque();

                        empate();
                        afogamento();
                    }
                }

                if(jogador.getXeque())
                {
                    pecaClicada.colocar(origem2);
                    novaPeca(destino, peca);

                    jogador.xeque = false;
                    return;
                }

                if(pecaClicada.casa == destino)
                {
                    mudaTurno(); 
                } 
                return; 
            }
            //Verifica se a peca clicada for de classe diferente de rei.
            if(pecaClicada.getClass() != Rei.class)
            {
                pecaClicada.colocar(destino);
                jogador.getCasas();
                jogador.estaEmXeque();
                //Mesma lógica da de cima.
                if(!jogador.getXeque())
                {
                    pecaClicada.colocar(origem2);

                    if(peca != null)
                    {
                        novaPeca(destino, peca);
                    }
                    pecaClicada.mover(destino, this);
                    if (pecaClicada.casa == destino) {
                        jogador.getCasas();
                        pecaAnterior = pecaClicada;
                        jogador.estaEmXeque();

                        empate();
                        afogamento();

                    }
                }

                if(jogador.getXeque())
                {
                    pecaClicada.colocar(origem2);
                    novaPeca(destino, peca);
                    jogador.xeque = false;
                    return;
                }

                if(pecaClicada.casa == destino)
                {
                    mudaTurno(); 
                }
                return;            
            }
        }
	}

    /**
     * @param peca é a Peca que foi clicada.
     * @return true se é a vez da peca, caso contrario false.
     */
    private boolean verificaTurno(Peca peca) 
    {
        if (peca.corDaPeca().equals("branca") && turnoDaBranca) {
            return true;
        }else if (peca.corDaPeca().equals("preta") && !turnoDaBranca) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * Inverte o turno atual.
     */
    private void mudaTurno() 
    {
        if (turnoDaBranca) {
            turnoDaBranca = false;
            jogador = jogador2;
            inimigo = jogador1;
        }else{
            turnoDaBranca = true;
            jogador = jogador1;
            inimigo = jogador2;
        }
    }
    
    public ArrayList<Peca> pecasPonta(Casa casaAtual) 
    {
        int x = casaAtual.getX();
        int y = casaAtual.getY();
        ArrayList<Peca> pecas = new ArrayList<Peca>();

        //checa em todas as pontas (os 8 destinos em L do cavalo)
        for (int i = 1; i > -2; i = i-2) {
            for (int j = 2; j > -3; j = j-4) {
                if (tabuleiro.existeCasa(casaAtual, i, j)) {
                    Casa casa1 = tabuleiro.getCasa(casaAtual, i, j);
                    if (casa1.possuiPeca()) {
                        pecas.add(casa1.getPeca());
                    }
                }
                if (tabuleiro.existeCasa(casaAtual, j, i)) {
                    Casa casa2 = tabuleiro.getCasa(casaAtual, j, i);
                    if (casa2.possuiPeca()) {
                        pecas.add(casa2.getPeca());
                    }
                }
            }
        }
        return pecas;
    }

    public ArrayList<Peca> pecasNoTabuleiro()
    { 
        //Percorre todo o tabuleiro e adiciona a um ArrayList as pecas.
        ArrayList<Peca> pecas = new ArrayList<Peca>();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Casa casaPercorrida = tabuleiro.getCasa(x, y);
                if(casaPercorrida.possuiPeca())
                {
                    pecas.add(casaPercorrida.getPeca());
                }
            }
        }
        return pecas;
    }
	
	public void promover(int nome)
    {
        //Promove o peão branco para rainha branca.
        if((pecaClicada.getTipo() == Peca.PEAO_BRANCO) && (nome == 0))
        {
            destino.removerPeca();
            Peca pecaPromovida = new Rainha(destino, Peca.RAINHA_BRANCA); 
        }
        //Promove o peão preto para rainha preta.
        if((pecaClicada.getTipo() == Peca.PEAO_PRETO)  && (nome == 0))
        {
            destino.removerPeca();
            Peca pecaPromovida = new Rainha(destino, Peca.RAINHA_PRETA); 
        }
        //Promove o peão branco para cavalo branco.
        if((pecaClicada.getTipo() == Peca.PEAO_BRANCO) && (nome == 1))
        {
            destino.removerPeca();
            Peca pecaPromovida = new Cavalo(destino, Peca.CAVALO_BRANCO); 
        }
        //Promove o peão preto para cavalo preto.
        if((pecaClicada.getTipo() == Peca.PEAO_PRETO)  && (nome == 1))
        {
            destino.removerPeca();
            Peca pecaPromovida = new Cavalo(destino, Peca.CAVALO_PRETO); 
        }
        //Promove o peão branco para torre branca.
        if((pecaClicada.getTipo() == Peca.PEAO_BRANCO) && (nome == 2))
        {
            destino.removerPeca();
            Peca pecaPromovida = new Torre(destino, Peca.TORRE_BRANCA); 
        }
        //Promove o peão preto para torre preta.
        if((pecaClicada.getTipo() == Peca.PEAO_PRETO)  && (nome == 2))
        {
            destino.removerPeca();
            Peca pecaPromovida = new Torre(destino, Peca.TORRE_PRETA); 
        }
        //Promove o peão branco para bispo branco.
        if((pecaClicada.getTipo() == Peca.PEAO_BRANCO) && (nome == 3))
        {
            destino.removerPeca();
            Peca pecaPromovida = new Bispo(destino, Peca.BISPO_BRANCO); 
        }
        //Promove o peão preto para bispo preto.
        if((pecaClicada.getTipo() == Peca.PEAO_PRETO)  && (nome == 3))
        {
            destino.removerPeca();
            Peca pecaPromovida = new Bispo(destino, Peca.BISPO_PRETO); 
        }
    }

    public boolean podePromover(int yi)
    {
        //Metodo que verifica se um peão pode promover
        if(pecaClicada.getClass() == Peao.class)
        {
            int yf = destino.getY();
            //Verifica se a casa final do peão for igual ao destino.
            if(pecaClicada.getCasa() == destino)
            {
                if ((yf == 7) && (yi == 6) && (pecaClicada.getTipo() == Peca.PEAO_BRANCO)) {
                    return true;
                }
                else if ((yf == 0) && (yi == 1) && (pecaClicada.getTipo() == Peca.PEAO_PRETO)) {
                    return true;
                }
            }
        }

        else
        {
            return false;
        }
        return false;
    }

    /**
     * @return o Tabuleiro em jogo.
     */
    public Tabuleiro getTabuleiro() 
    {
        return tabuleiro;
    }

    public boolean empate()
    {
        ArrayList<Peca> pecas = pecasNoTabuleiro();

        for(Peca peca : pecas)
        {
            //Verifica o tamanho do ArrayList.
            if(pecas.size() == 2)
            {

                if(peca.getTipo() == Peca.REI_BRANCO)
                {
                    reiBranco = true;
                }

                if(peca.getTipo() == Peca.REI_PRETO)
                {
                    reiPreto = true;
                }
                //Se as únicas pecas no tabuleiro forem o rei branco e o rei preto o jogo empata.
                if((reiBranco) && (reiPreto))
                {
                    return empate = true;
                }
            }
            //Verifica o tamanho do ArrayList.
            if(pecas.size() == 3)
            {
                if(peca.getTipo() == Peca.REI_BRANCO)
                {
                    reiBranco = true;
                }

                if(peca.getTipo() == Peca.REI_PRETO)
                {
                    reiPreto = true;
                }

                if((peca.getTipo() == Peca.CAVALO_BRANCO) || (peca.getTipo() == Peca.CAVALO_PRETO))
                {
                    cavalo = true;
                }

                if((peca.getTipo() == Peca.BISPO_BRANCO) || (peca.getTipo() == Peca.BISPO_PRETO))
                {
                    bispo = true;
                }
                //Se as únicas pecas no tabuleiro forem um cavalo e dois reis o jogo empata.
                if((((reiBranco) && (cavalo)) && (reiPreto)) || (((reiPreto) && (cavalo)) && (reiBranco)))
                {
                    return empate = true;
                }
                //Se as únicas pecas no tabuleiro forem um bispo e dois reis o jogo empata.
                if((((reiBranco) && (bispo)) && (reiPreto)) || (((reiPreto) && (bispo)) && (reiBranco)))
                {
                    return empate = true;
                }
            }
        }

        return false;
    }

    private boolean afogamento()
    {
        ArrayList<Peca> pecas = pecasNoTabuleiro();
        //Verifica se o inimigo não está em xeque
        if(!inimigo.estaEmXeque())
        {
            //Se nenhuma das condicoes acima não for realizada e não tiver nenhum movimento possivel no tabuleiro e o rei inimigo não pode se mover afogamento retorna true;
            if(!movimentoPossivel())
            {
                empate = true;
                return afogado = true;
            }
        }

        return false;
    }

    public boolean movimentoPossivel()
    {
        ArrayList<Peca> pecas = pecasNoTabuleiro();
        ArrayList<Peca> pecas1 = new ArrayList<Peca>();
        //Esse método vai percorrer todas as casas do tabuleiro pegar todas as pecas e colocar em um ArrayList, depois vai colocar as pecas que tenham a mesma cor que o inimigo em outro ArrayList
        //e vai ver todos os movimentos possíveis das pecas se em algum dos movimentos testados o rei não ficar em xeque o método retornará true.
        for(Peca peca : pecas)
        {
            if(peca.corDaPeca().equals(inimigo.cor))
            {
                pecas1.add(peca);
            }
        }

        for(Peca peca : pecas1)
        {
            Casa casa = peca.getCasa();
            int x = casa.getX();
            int y = casa.getY();

            if((peca.getClass() == Rainha.class) || (peca.getClass() == Torre.class))
            {
                Casa casa1 = tabuleiro.getCasa(x, y + 1);
                Casa casa2 = tabuleiro.getCasa(x, y - 1);
                Casa casa3 = tabuleiro.getCasa(x + 1, y);
                Casa casa4 = tabuleiro.getCasa(x - 1, y);

                if(casa1 != null)
                {
                    if(!casa1.possuiPeca())
                    {
                        peca.colocar(casa1);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            return true;
                        }
                        peca.colocar(casa);
                    }

                    if(casa1.possuiPeca() && peca.coresDiferentes(casa1.getPeca()))
                    {
                        Peca peca1 = casa1.getPeca();
                        peca.colocar(casa1);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            novaPeca(casa1, peca1);
                            return true;
                        }
                        peca.colocar(casa);
                    }
                }

                if(casa2 != null)
                {
                    if(!casa2.possuiPeca())
                    {
                        peca.colocar(casa2);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            return true;
                        }
                        peca.colocar(casa);
                    }

                    if(casa2.possuiPeca() && peca.coresDiferentes(casa2.getPeca()))
                    {
                        Peca peca1 = casa2.getPeca();
                        peca.colocar(casa2);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            novaPeca(casa2, peca1);
                            return true;
                        }
                        peca.colocar(casa);
                    }
                }

                if(casa3 != null)
                {
                    if(!casa3.possuiPeca())
                    {
                        peca.colocar(casa3);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            return true;
                        }
                        peca.colocar(casa);
                    }

                    if(casa3.possuiPeca() && peca.coresDiferentes(casa3.getPeca()))
                    {
                        Peca peca1 = casa3.getPeca();
                        peca.colocar(casa3);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            novaPeca(casa3, peca1);
                            return true;
                        }
                        peca.colocar(casa);
                    }
                }

                if(casa4 != null)
                {
                    if(!casa4.possuiPeca())
                    {
                        peca.colocar(casa4);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            return true;
                        }
                        peca.colocar(casa);
                    }

                    if(casa4.possuiPeca() && peca.coresDiferentes(casa4.getPeca()))
                    {
                        Peca peca1 = casa4.getPeca();
                        peca.colocar(casa4);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            novaPeca(casa4, peca1);
                            return true;
                        }
                        peca.colocar(casa);
                    }
                }
            }

            if((peca.getClass() == Rainha.class) || (peca.getClass() == Bispo.class))
            {
                Casa casa1 = tabuleiro.getCasa(x + 1, y + 1);
                Casa casa2 = tabuleiro.getCasa(x - 1, y + 1);
                Casa casa3 = tabuleiro.getCasa(x + 1, y - 1);
                Casa casa4 = tabuleiro.getCasa(x - 1, y - 1);

                if(casa1 != null)
                {
                    if(!casa1.possuiPeca())
                    {
                        peca.colocar(casa1);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            return true;
                        }
                        peca.colocar(casa);
                    }

                    if(casa1.possuiPeca() && peca.coresDiferentes(casa1.getPeca()))
                    {
                        Peca peca1 = casa1.getPeca();
                        peca.colocar(casa1);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            novaPeca(casa1, peca1);
                            return true;
                        }
                        peca.colocar(casa);
                    }
                }

                if(casa2 != null)
                {
                    if(!casa2.possuiPeca())
                    {
                        peca.colocar(casa2);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            return true;
                        }
                        peca.colocar(casa);
                    }

                    if(casa2.possuiPeca() && peca.coresDiferentes(casa2.getPeca()))
                    {
                        Peca peca1 = casa2.getPeca();
                        peca.colocar(casa2);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            novaPeca(casa2, peca1);
                            return true;
                        }
                        peca.colocar(casa);
                    }
                }

                if(casa3 != null)
                {
                    if(!casa3.possuiPeca())
                    {
                        peca.colocar(casa3);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            return true;
                        }
                        peca.colocar(casa);
                    }

                    if(casa3.possuiPeca() && peca.coresDiferentes(casa3.getPeca()))
                    {
                        Peca peca1 = casa3.getPeca();
                        peca.colocar(casa3);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            novaPeca(casa3, peca1);
                            return true;
                        }
                        peca.colocar(casa);
                    }
                }

                if(casa4 != null)
                {
                    if(!casa4.possuiPeca())
                    {
                        peca.colocar(casa4);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            return true;
                        }
                        peca.colocar(casa);
                    }

                    if(casa4.possuiPeca() && peca.coresDiferentes(casa4.getPeca()))
                    {
                        Peca peca1 = casa4.getPeca();
                        peca.colocar(casa4);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            novaPeca(casa4, peca1);
                            return true;
                        }
                        peca.colocar(casa);
                    }
                }
            }

            if(peca.getClass() == Cavalo.class)
            {
                ArrayList<Casa> casas1 = new ArrayList<Casa>();
                for (int i = 1; i > -2; i = i-2) {
                    for (int j = 2; j > -3; j = j-4) {
                        Casa casa1 = tabuleiro.getCasa(i, j);
                        if(casa1 != null)
                        {
                            casas1.add(casa1);
                        }
                    }
                }

                for(Casa casa1 : casas1)
                {
                    if((!casa.possuiPeca()) || ((casa.possuiPeca()) && (peca.coresDiferentes(casa.getPeca())))) 
                    {
                        peca.colocar(casa1);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            return true;
                        }
                        peca.colocar(casa);
                    }
                }
            }

            if(peca.getClass() == Peao.class)
            {
                if(peca.getTipo() == Peca.PEAO_BRANCO)
                {
                    Casa casa1 = tabuleiro.getCasa(x + 1, y + 1);
                    Casa casa2 = tabuleiro.getCasa(x - 1, y + 1);
                    Casa casa3 = tabuleiro.getCasa(x, y + 1);

                    if(casa1 != null)
                    {
                        if(casa1.possuiPeca())
                        {
                            Peca peca1 = casa1.getPeca();
                            peca.colocar(casa1);
                            if(!inimigo.estaEmXeque())
                            {
                                peca.colocar(casa);
                                novaPeca(casa1, peca1);
                                return true;
                            }
                            peca.colocar(casa);
                        }
                    }

                    if(casa2 != null)
                    {
                        if(casa2.possuiPeca())
                        {
                            Peca peca1 = casa2.getPeca();
                            peca.colocar(casa2);
                            if(!inimigo.estaEmXeque())
                            {
                                peca.colocar(casa);
                                novaPeca(casa2, peca1);
                                return true;
                            }
                            peca.colocar(casa);
                        }
                    }

                    if(casa3 != null)
                    {
                        if(!casa3.possuiPeca())
                        {
                            peca.colocar(casa3);
                            if(!inimigo.estaEmXeque())
                            {
                                peca.colocar(casa);
                                return true;
                            }
                            peca.colocar(casa);
                        }
                    }
                }

                if(peca.getTipo() == Peca.PEAO_PRETO)
                {
                    Casa casa1 = tabuleiro.getCasa(x + 1, y - 1);
                    Casa casa2 = tabuleiro.getCasa(x - 1, y - 1);
                    Casa casa3 = tabuleiro.getCasa(x, y - 1);

                    if(casa1 != null)
                    {
                        if(casa1.possuiPeca())
                        {
                            Peca peca1 = casa1.getPeca();
                            peca.colocar(casa1);
                            if(!inimigo.estaEmXeque())
                            {
                                peca.colocar(casa);
                                novaPeca(casa1, peca1);
                                return true;
                            }
                            peca.colocar(casa);
                        }
                    }

                    if(casa2 != null)
                    {
                        if(casa2.possuiPeca())
                        {
                            Peca peca1 = casa2.getPeca();
                            peca.colocar(casa2);
                            if(!inimigo.estaEmXeque())
                            {
                                peca.colocar(casa);
                                novaPeca(casa2, peca1);
                                return true;
                            }
                            peca.colocar(casa);
                        }
                    }

                    if(casa3 != null)
                    {
                        if(!casa3.possuiPeca())
                        {
                            peca.colocar(casa3);
                            if(!inimigo.estaEmXeque())
                            {
                                peca.colocar(casa);
                                return true;
                            }
                            peca.colocar(casa);
                        }   
                    }
                }
            }

            if(peca.getClass() == Rei.class)
            {
                if(reiInimigoPodeMover())
                {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean reiInimigoPodeMover()
    {
        ArrayList<Casa> casas2 = new ArrayList<Casa>();
        Peca peca = inimigo.getRei();
        Casa casa = peca.getCasa();
        casas2 = inimigo.getCasasDoRei();
        //Esse método verifica todas as casas que o rei pode se mover, se em alguma delas ele não ficar em xeque o metodo retorna true;

        for(Casa casa1 : casas2)
        {
            if(casa1 != casa)
            {
                if(!casa1.possuiPeca())
                {
                    peca.colocar(casa1);
                    inimigo.getCasasDestino(casa1);
                    if(!inimigo.estaEmXeque())
                    {
                        peca.colocar(casa);
                        return true;
                    }
                    peca.colocar(casa);
                }
                if(casa1.possuiPeca())
                {
                    Peca peca1 = casa1.getPeca();
                    if(peca.coresDiferentes(peca1))
                    {
                        peca.colocar(casa1);
                        inimigo.getCasasDestino(casa1);
                        if(!inimigo.estaEmXeque())
                        {
                            peca.colocar(casa);
                            return true;
                        }
                        peca.colocar(casa);
                    }
                }
            }
        }

        return false;
    }

    public void novaPeca(Casa destino, Peca peca)
    {
        //Esse método cria uma nova peca para quando esteja sendo feito um teste de xeque.
        if(peca != null)
        {
            if(peca.getClass() == Peao.class)
            {
                Peca peca1 = new Peao(destino, peca.getTipo());
            }

            if(peca.getClass() == Torre.class)
            {
                Peca peca1 = new Torre(destino, peca.getTipo());
            }

            if(peca.getClass() == Cavalo.class)
            {
                Peca peca1 = new Cavalo(destino, peca.getTipo());
            }

            if(peca.getClass() == Rainha.class)
            {
                Peca peca1 = new Rainha(destino, peca.getTipo());
            }

            if(peca.getClass() == Bispo.class)
            {
                Peca peca1 = new Bispo(destino, peca.getTipo());
            }

            if(peca.getClass() == Rei.class)
            {
                Peca peca1 = new Rei(destino, peca.getTipo());
            }
        }
    }

    private ArrayList getCasas(Peca peca)
    {
        ArrayList<Casa> casas = new ArrayList<Casa>();
        Casa casa = peca.getCasa();
        int x = casa.getX();
        int y = casa.getY();
        int i;

        //Esse método armazena em um ArrayList todas as casas que uma peca pode chegar.

        if((peca.getClass() == Rainha.class) || (peca.getClass() == Torre.class))
        {
            for(i = 0; i <= 7; i++)
            {
                Casa casa1 = tabuleiro.getCasa(i, y);
                casas.add(casa1);
            }

            for(i = 0; i <= 7; i++)
            {
                Casa casa1 = tabuleiro.getCasa(x, i);
                casas.add(casa1);
            }
        }

        if((peca.getClass() == Rainha.class) || (peca.getClass() == Bispo.class))
        {
            for(i = 0; i <= 7; i++)
            {
                Casa casa1 = tabuleiro.getCasa(x + i, y + i);
                Casa casa2 = tabuleiro.getCasa(x - i, y + i);
                Casa casa3 = tabuleiro.getCasa(x - i, y - i);
                Casa casa4 = tabuleiro.getCasa(x + i, y - i);
                if(casa1 != null)
                {
                    casas.add(casa1);
                }

                if(casa2 != null)
                {
                    casas.add(casa2);
                }

                if(casa3 != null)
                {
                    casas.add(casa3);
                }

                if(casa4 != null)
                {
                    casas.add(casa4);
                }
            }
        }

        if(peca.getClass() == Cavalo.class)
        {
            ArrayList<Peca> pecas = pecasPonta(casa);
            for(Peca peca1 : pecas)
            {
                Casa casa1 = peca1.getCasa();
                casas.add(casa1);
            }
        }

        if(peca.getTipo() == Peca.PEAO_BRANCO)
        {
            Casa casa1 = tabuleiro.getCasa(x + 1, y + 1);
            Casa casa2 = tabuleiro.getCasa(x - 1, y + 1);

            if(casa1 != null)
            {
                casas.add(casa1);
            }

            if(casa2 != null)
            {
                casas.add(casa2);
            }
        }

        if(peca.getTipo() == Peca.PEAO_PRETO)
        {
            Casa casa1 = tabuleiro.getCasa(x + 1, y - 1);
            Casa casa2 = tabuleiro.getCasa(x - 1, y - 1);

            if(casa1 != null)
            {
                casas.add(casa1);
            }

            if(casa2 != null)
            {
                casas.add(casa2);
            }
        }

        return casas;
    }
	
	public Casa getOrigem(){
		return origem2;
	}
	
	public Casa getDestino(){
		return destino;
	}
	
	public Casa ultimaCasa(){
		 return pecaClicada.getCasa();
	}
}

