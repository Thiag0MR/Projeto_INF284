package com.inf284.model.metaheuristica.GA;

import com.inf284.model.Instancia;
import com.inf284.utils.LerArquivo;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class FuncaoDeAptidaoTest {
    
    @Test
    public void testarMetodoAvaliarAptidaoPopulacao() {
        Instancia instancia = LerArquivo.obterInstancia("gr17.tsp", 0, 0);
        Populacao populacao = new Populacao().gerarPopulacaoInicial(instancia, 5);
        // FuncaoDeAptidao funcaoDeAptidao = new FuncaoDeAptidao(instancia);
        // funcaoDeAptidao.calcularAptidaoPopulacao(populacao);
        System.out.println("a");
    }
}
