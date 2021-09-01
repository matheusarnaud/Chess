
import java.util.ArrayList;

/**
 * Escreva a descrição da classe Jogador aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class Jogador
{
    public String cor;
    private Jogo jogo;
    private Tabuleiro tabuleiro;
    public ArrayList<Peca> pecas;
    public ArrayList<Casa> casas;
    public ArrayList<Casa> casasDoRei;
    public boolean xeque;
    public boolean xequeMate;
    

    /**
     * COnstrutor para objetos da classe Jogador
     */
    public Jogador(String cor, Jogo jogo)
    {
        this.cor = cor;
        this.jogo = jogo;
        this.tabuleiro = jogo.getTabuleiro();
        pecas = new ArrayList<Peca>();
        getPecas(cor);
        getCasas();
    }

    public ArrayList getPecas(String cor)
    {
        //Esse método pega todas as pecas da mesma cor do jogador.
        
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Casa casaPercorrida = tabuleiro.getCasa(x, y);
                if(casaPercorrida.possuiPeca())
                {
                    Peca peca = casaPercorrida.getPeca();
                    if(peca.corDaPeca().equals(cor))
                    {
                        pecas.add(peca);
                    }
                }
            }
        }
        
        return pecas;
    }
    
    public ArrayList getCasas()
    {
        //Retorna um ArrayList com todas as casas com a possibilidade de ameacar o rei.
        
        for(Peca peca : pecas)
        {
            if(peca.getClass() == Rei.class)
            {
                Casa casa = peca.getCasa();
                int x = casa.getX();
                int y = casa.getY();
                int i;
                int j;
        
        
                casas = new ArrayList<Casa>();
                for(i = 0; i <= 7; i++)
                {
                    Casa casa1 = tabuleiro.getCasa(i, y);
                    casas.add(casa1);
                }
        
                for(j = 0; j <= 7; j++)
                {
                    Casa casa1 = tabuleiro.getCasa(x, j);
                    casas.add(casa1);
                }
        
                for(i = 0; i <= 7; i++)
                {
                    Casa casa1 = tabuleiro.getCasa(x + i, y + i);
                    Casa casa2 = tabuleiro.getCasa(x - i, y + i);
                    Casa casa3 = tabuleiro.getCasa(x - i, y - i);
                    Casa casa4 = tabuleiro.getCasa(x + i, y - i);
                    casas.add(casa1);
                    casas.add(casa2);
                    casas.add(casa3);
                    casas.add(casa4);
                }
                
            }
        }
        
        return casas;
    }
    
    public ArrayList getCasasDestino(Casa destino)
    {
        //Esse método retorna as casas do rei no destino.
        
        for(Peca peca : pecas)
        {
            if(peca.getClass() == Rei.class)
            {
                
                int x = destino.getX();
                int y = destino.getY();
                int i;
                int j;
        
        
                casas = new ArrayList<Casa>();
                for(i = 0; i <= 7; i++)
                {
                    Casa casa1 = tabuleiro.getCasa(i, y);
                    casas.add(casa1);
                }
        
                for(j = 0; j <= 7; j++)
                {
                    Casa casa1 = tabuleiro.getCasa(x, j);
                    casas.add(casa1);
                }
        
                for(i = 0; i <= 7; i++)
                {
                    Casa casa1 = tabuleiro.getCasa(x + i, y + i);
                    Casa casa2 = tabuleiro.getCasa(x - i, y + i);
                    Casa casa3 = tabuleiro.getCasa(x - i, y - i);
                    Casa casa4 = tabuleiro.getCasa(x + i, y - i);
                    casas.add(casa1);
                    casas.add(casa2);
                    casas.add(casa3);
                    casas.add(casa4);
                }
                
            }
        }
        
        return casas;
    }
    
    public boolean estaEmXeque()
    {
        ArrayList<Peca> pecas = new ArrayList<Peca>();
        ArrayList<Casa> casas = getList();
        ArrayList<Casa> casas1 = getCasasDoRei();
        Peca rei = getRei();
        Casa casa1 = rei.getCasa();
        int x = casa1.getX();
        int y = casa1.getY();
        ArrayList<Peca> pecas1 = jogo.pecasPonta(casa1);
        //for each em todas as casas do arraylist para verificar as que possuem pecas.
        for(Casa casa : casas)
        {
            if(casa == null)
            {
                ;
            }
            else if(casa.possuiPeca())
            {
                Peca peca = casa.getPeca();
                pecas.add(peca);
            }
        }
        //for each em todas as pecas a procura de rainhas, bispos, torres e peoes que estejam ameacando o rei.
        for(Peca peca : pecas)
        {
            Casa casa2 = peca.getCasa();
            int x1 = casa2.getX();
            int y1 = casa2.getY();
            //Xeque da torre e rainha.
            if(((x == x1) || (y == y1)) && ((peca.getClass() == Torre.class) || (peca.getClass() == Rainha.class)) && (rei.coresDiferentes(peca)) && (!peca.temPecaPerto(casa2, casa1, tabuleiro)))
            {
                return xeque = true;
            }
            //Xeque do bispo e rainha
            if((Math.abs(x - x1) == Math.abs(y - y1)) && ((peca.getClass() == Bispo.class) || (peca.getClass() == Rainha.class)) && (rei.coresDiferentes(peca)) && (!peca.temPecaPerto(casa2, casa1, tabuleiro)))
            {
                return xeque = true;
            }
            
            if(((x == x1 + 1) || (x == x1 - 1)) && (peca.getClass() == Peao.class) && (peca.coresDiferentes(rei)))
            {
                if(peca.getTipo() == Peca.PEAO_PRETO)
                {
                    if(y == y1 - 1)
                    {
                        return xeque =  true;
                    }
                }
                
                if(peca.getTipo() == Peca.PEAO_BRANCO)
                {
                    if(y == y1 + 1)
                    {
                        return xeque =  true;
                    }
                }
            }
            
            
        }
        //Procura por cavalos que estejam ameacando o rei.
        for(Peca peca : pecas1)
        {
            
            if(peca.getClass() == Cavalo.class)
            {
                if(peca.coresDiferentes(rei))
                {
                    return xeque =  true;
                }
            }
            
        }
        //Procura por outros reis que estejam ameacando o rei.
        for(Casa casa : casas1)
        {
            if(casa.possuiPeca())
            {
                Peca peca = casa.getPeca();
                if(peca.getClass() == Rei.class)
                {
                    if(peca.coresDiferentes(rei))
                    {   
                        return xeque =  true;
                    }
                }
            }
        }
        
        
        return xeque =  false;
    }
    
    public ArrayList getCasasDoRei()
    {
        //Retorna todass as casas que o rei pode se movimentar.
        
        Peca rei = getRei();
        Casa casaDoRei = rei.getCasa();
        Casa origem2 = casaDoRei;
        casasDoRei = new ArrayList<Casa>();
        int x = casaDoRei.getX();
        int y = casaDoRei.getY();
        int i;
        int j;
        for(i = x - 1; i <= x + 1; i++)
        {
                Casa casa = tabuleiro.getCasa(i, y);
                if(casa != null)
                {
                    casasDoRei.add(casa);
                }
        }
        
        for(i = x - 1; i <= x + 1; i++)
        {
                Casa casa = tabuleiro.getCasa(i, y + 1);
                if(casa != null)
                {
                    casasDoRei.add(casa);
                }
        }
        
        for(i = x - 1; i <= x + 1; i++)
        {
                Casa casa = tabuleiro.getCasa(i, y - 1);
                if(casa != null)
                {
                    casasDoRei.add(casa);
                }
        }
        return casasDoRei;
    }

    public boolean getXeque()
    {
        return xeque;
    }
    
    public String getCor()
    {
        return cor;
    }
    
    public Peca getRei()
    {
        for(Peca peca : pecas)
        {
            if(peca.getClass() == Rei.class)
            {
                return peca;
            }
        }
        
        return null;
    }
    
    public ArrayList getList()
    {
        return casas;
    }
}
