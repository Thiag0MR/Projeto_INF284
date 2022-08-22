package com.inf284.model.metaheuristica.GLS;

import com.inf284.model.Instancia;
import com.inf284.model.metaheuristica.componente.FuncaoDeAvaliacao;
import com.inf284.model.metaheuristica.componente.Solucao;

public class GLS {

    FuncaoDeAvaliacao funcaoDeAvaliacao;
    FuncaoDeAvaliacaoGLS funcaoDeAvaliacaoGLS;
    Instancia instancia;
    BuscaLocalGLS buscaLocalGLS;

    public GLS(Instancia instancia) {
        this.instancia = instancia;
        this.funcaoDeAvaliacao = new FuncaoDeAvaliacao(instancia);
        this.funcaoDeAvaliacaoGLS = new FuncaoDeAvaliacaoGLS(instancia);
        this.buscaLocalGLS = new BuscaLocalGLS(instancia);
    }

    public Solucao executar (Solucao solucao, int tMax) {

        long start = System.currentTimeMillis();

        // Inicializar as penalidades
        int qtdCidades = solucao.cidades.length;
        int[][] penalidadesArestas = new int[qtdCidades + 1][qtdCidades + 1];
        
        Solucao solucaoOtimoLocal = solucao;
        Solucao solucaoIncumbente = solucao;
        float valorSolucaoIncumbente = funcaoDeAvaliacao.avaliar(solucao);
        float valorOtimoLocal = valorSolucaoIncumbente;
        System.out.println(instancia.NAME + " : " + valorSolucaoIncumbente);

        float alfa = 0.3F;
        float lambda = alfa * valorOtimoLocal / qtdCidades;

        // Critério de parada
        while (System.currentTimeMillis() < (start + (tMax * 1_000 * 60))) {

            // Busca local
            solucaoOtimoLocal = buscaLocalGLS.bestImprovement_2_Opt(solucaoOtimoLocal, lambda, penalidadesArestas);
            // solucaoOtimoLocal = buscaLocalGLS.firstImprovement_2_Opt(solucaoOtimoLocal, lambda, penalidadesArestas);
            valorOtimoLocal = funcaoDeAvaliacao.avaliar(solucaoOtimoLocal);

            // Critério de aceitação
            if (valorOtimoLocal < valorSolucaoIncumbente) {
                solucaoIncumbente = solucaoOtimoLocal;
                valorSolucaoIncumbente = valorOtimoLocal;

                System.out.println(instancia.NAME + " : " + valorSolucaoIncumbente);

                // if (valorSolucaoIncumbente < 630) break;
            }

            int utilMax = calcularUtilidadeMaxima(solucaoOtimoLocal, penalidadesArestas);

            // Penaliza features com util = utilMax
            penalizarFeatures(solucaoOtimoLocal, penalidadesArestas, utilMax);

        }

        // long stop = System.currentTimeMillis() - start;
        // System.out.println("Tempo em milisegundos: " + stop);
        // System.out.printf("Tempo em segundos: %.5f %n", (float) stop/1000);

        return solucaoIncumbente;
    }

    private void penalizarFeatures(Solucao solucaoOtimoLocal, int[][] penalidadesArcos, int utilMax) {
        int util = 0;
        int c1 = 0;
        int c2 = 0;
        
        for (int i = 1; i <= solucaoOtimoLocal.cidades.length; i++) {
            c1 = solucaoOtimoLocal.cidades[i-1];
            c2 = solucaoOtimoLocal.cidades[i % solucaoOtimoLocal.cidades.length];

            util = instancia.MATRIZ_DISTANCIA[c1][c2]/(1 + penalidadesArcos[c1][c2]);
            
            if (util == utilMax) {
                penalidadesArcos[c1][c2]++;
            }
        }
    }

    private int calcularUtilidadeMaxima (Solucao solucao, int[][] penalidadesArcos) {
        int utilMax = 0;
        int util = 0;
        int c1 = 0;
        int c2 = 0;

        for (int i = 1; i <= solucao.cidades.length; i++) {
            c1 = solucao.cidades[i-1];
            c2 = solucao.cidades[i % solucao.cidades.length];
            
            util = instancia.MATRIZ_DISTANCIA[c1][c2]/(1 + penalidadesArcos[c1][c2]);

            if (utilMax == 0 || utilMax < util) {
                utilMax = util;
            } 
        }

        return utilMax;
    }
}

