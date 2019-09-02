package com.server;

import com.game.*;

public class Atualizador implements Runnable{
	private JanelaPrincipal j;
	
	public Atualizador(JanelaPrincipal j){
		this.j = j;
	}
	
	public void run(){
		while(true){
			j.atualizar();
		}
	}
}