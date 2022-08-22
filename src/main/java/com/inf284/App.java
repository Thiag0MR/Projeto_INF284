package com.inf284;

import java.util.Arrays;

import com.inf284.model.Instancia;
import com.inf284.model.metaheuristica.GA.GA;
import com.inf284.model.metaheuristica.GLS.GLS;
import com.inf284.model.metaheuristica.ILS.ILS;
import com.inf284.model.metaheuristica.componente.FuncaoDeAvaliacao;
import com.inf284.model.metaheuristica.componente.Solucao;
import com.inf284.model.metaheuristica.componente.SolucaoInicial;
import com.inf284.utils.LerArquivo;

public class App 
{
    static String[] instancias = {
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

    static String[][] instancias2 = {
        {"gr17", "3", "80"},
        {"gr48", "6", "80"},
        {"gr48", "10", "80"},
        {"brazil58", "5", "300"},
        {"brazil58", "8", "100"},
        {"brazil58", "8", "250"},
        {"berlin52", "3", "90"},
        {"eil101", "10", "5"},
        {"a280", "10", "10"},
        // {"brazil58", "16", "100"},
        // {"brazil58", "16", "250"},
        // {"eil101", "20", "5"},
        // {"eil101", "20", "10"},
        // {"eil101", "40", "5"},
        // {"eil101", "40", "10"},
        // {"a280", "10", "30"},
        // {"a280", "25", "10"},
        // {"a280", "25", "30"},
        // {"a280", "50", "10"},
        // {"a280", "50", "30"},
    };

    public static void main( String[] args)
    {
        executarInstanciaEspecifica("a280", 0, 0);
        // executarTodasInstancias();
        // executarTodasInstanciasComEntregas();
    }

    private static void executarInstanciaEspecifica(String string, int k, int v) {

        Instancia instancia = LerArquivo.obterInstancia(string + ".tsp", k, v);
        FuncaoDeAvaliacao funcaoDeAvaliacao = new FuncaoDeAvaliacao(instancia);

        // Solucao solucao = SolucaoInicial.construirSolucaoInicialSequencial(instancia);
        Solucao solucaov1 = SolucaoInicial.construirSolucaoInicialVizinhoMaisProximoPrimeiraVersao(instancia);
        Solucao solucaov2 = SolucaoInicial.construirSolucaoInicialVizinhoMaisProximoSegundaVersao(instancia);

        Solucao solucao = funcaoDeAvaliacao.avaliar(solucaov1) > funcaoDeAvaliacao.avaliar(solucaov2) ? solucaov2 : solucaov1;

        // ILS ils = new ILS(instancia);
        // solucao = ils.executar(solucao, 5 /* Minutos */);

        // GLS gls = new GLS(instancia);
        // solucao = gls.executar(solucao, 5 /* Minutos */);

        GA ga = new GA(instancia);
        solucao = ga.executar(5 /* Minutos */);
        
        System.out.println(instancia.NAME + ", " + k + ", " + v + " : " + funcaoDeAvaliacao.avaliar(solucao));
    }

    private static void executarTodasInstancias() {

        Arrays.stream(instancias).forEach((String s) -> {
            
            int k = 0;
            int v = 0;

            executarInstanciaEspecifica(s, k, v);
        });
    }

    private static void executarTodasInstanciasComEntregas() {

        Arrays.stream(instancias2).forEach((String[] s) -> {
            
            int k = Integer.parseInt(s[1]);
            int v = Integer.parseInt(s[2]);

            executarInstanciaEspecifica(s[0], k, v);
        });
    }
}
