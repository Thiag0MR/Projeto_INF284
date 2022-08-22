package com.inf284.model.metaheuristica.componente;

import com.inf284.model.Instancia;
import com.inf284.utils.LerArquivo;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SolucaoInicialTest {
    
    Instancia instancia = LerArquivo.obterInstancia("gr17.tsp", 0, 0);

    @Test
    public void testarMetodoConstruirSolucaoInicialVizinhoMaisProximoPrimeiraVersao() {
        Solucao solucao = SolucaoInicial.construirSolucaoInicialVizinhoMaisProximoPrimeiraVersao(instancia);
    }

    @Test
    public void testarMetodoConstruirSolucaoInicialVizinhoMaisProximoSegundaVersao() {
        Solucao solucao = SolucaoInicial.construirSolucaoInicialVizinhoMaisProximoSegundaVersao(instancia);
    }
}
