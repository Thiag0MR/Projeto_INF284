package com.inf284.model.metaheuristica.componente;

import java.util.Arrays;

import com.inf284.model.Instancia;
import com.inf284.utils.LerArquivo;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class FuncaoDeAvaliacaoTest {

    String[] instancias = {
        "gr17",
        "gr21",
        "gr48",
        "gr120",
        "brazil58",
        "berlin52",
        "fri26",
        "eil51",
        "eil76",
        "eil101",
        "a280"
    };

    String[][] instancias2 = {
        {"gr17", "3", "80"},
        {"gr48", "6", "80"},
        {"gr48", "10", "80"},
        {"brazil58", "5", "300"},
        {"brazil58", "8", "100"},
        {"brazil58", "8", "250"},
        {"berlin52", "3", "90"},
        {"eil101", "10", "5"},
        {"a280", "10", "10"},
    };


    @Test
    public void testarMetodoAvaliar() {

        Instancia instancia = LerArquivo.obterInstancia("gr17.tsp", 0, 0);

        // Solucao solucao = SolucaoInicial.construirSolucaoInicialSequencial(instancia);
        // Solucao solucao = SolucaoInicial.construirSolucaoInicialVizinhoMaisProximoPrimeiraVersao(instancia);
        Solucao solucao = SolucaoInicial.construirSolucaoInicialVizinhoMaisProximoSegundaVersao(instancia);

        FuncaoDeAvaliacao funcaoDeAvaliacao = new FuncaoDeAvaliacao(instancia);

        System.out.println(instancia.NAME + " : " + funcaoDeAvaliacao.avaliar(solucao));
    }


    @Test
    public void testarMetodoAvaliarParaTodasAsInstancias() {

        Arrays.stream(instancias).forEach((String s) -> {
            int k = 0;
            int v = 0;

            Instancia instancia = LerArquivo.obterInstancia(s + ".tsp", k, v);

            // Solucao solucao = SolucaoInicial.construirSolucaoInicialSequencial(instancia);
            //  Solucao solucao = SolucaoInicial.construirSolucaoInicialVizinhoMaisProximoPrimeiraVersao(instancia);
            Solucao solucao = SolucaoInicial.construirSolucaoInicialVizinhoMaisProximoSegundaVersao(instancia);

            FuncaoDeAvaliacao funcaoDeAvaliacao = new FuncaoDeAvaliacao(instancia);

            System.out.println(instancia.NAME + " : " + funcaoDeAvaliacao.avaliar(solucao));
        });
    }

    @Test
    public void testarMetodoAvaliarParaTodasAsInstanciasComEntregas() {

        Arrays.stream(instancias2).forEach((String[] s) -> {
            int k = Integer.parseInt(s[1]);
            int v = Integer.parseInt(s[2]);

            Instancia instancia = LerArquivo.obterInstancia(s[0] + ".tsp", k, v);

            // Solucao solucao = SolucaoInicial.construirSolucaoInicialSequencial(instancia);
            // Solucao solucao = SolucaoInicial.construirSolucaoInicialVizinhoMaisProximoPrimeiraVersao(instancia);
            Solucao solucao = SolucaoInicial.construirSolucaoInicialVizinhoMaisProximoSegundaVersao(instancia);

            FuncaoDeAvaliacao funcaoDeAvaliacao = new FuncaoDeAvaliacao(instancia);

            System.out.println(instancia.NAME + ", " + k + ", " + v + " : " + funcaoDeAvaliacao.avaliar(solucao));
        });
    }
}
