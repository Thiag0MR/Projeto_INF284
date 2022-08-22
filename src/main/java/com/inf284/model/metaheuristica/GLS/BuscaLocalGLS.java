package com.inf284.model.metaheuristica.GLS;

import java.util.Random;
import java.util.stream.IntStream;

import com.inf284.model.Instancia;
import com.inf284.model.metaheuristica.componente.Solucao;

public class BuscaLocalGLS {
    private FuncaoDeAvaliacaoGLS funcaoDeAvaliacaoGLS;

    public BuscaLocalGLS(Instancia instancia) {
        this.funcaoDeAvaliacaoGLS = new FuncaoDeAvaliacaoGLS(instancia);
    }

    public Solucao bestImprovement_2_Opt (Solucao solucao, float lambda, int[][] penalidadesArestas) {
        
        float valorSolucao = funcaoDeAvaliacaoGLS.avaliar(solucao, lambda, penalidadesArestas);

        while (true) {
            Solucao vizinho = melhorVizinho(solucao, lambda, penalidadesArestas);
            float valorVizinho = funcaoDeAvaliacaoGLS.avaliar(vizinho, lambda, penalidadesArestas);

            if (valorVizinho < valorSolucao) {
                solucao = vizinho;
                valorSolucao = valorVizinho;
            } else {
                break;
            }
        } 

        return solucao;
    }

    private Solucao melhorVizinho(Solucao solucao, float lambda, int[][] penalidadesArestas) {

        Solucao melhorVizinho = solucao;
        float valorMelhorVizinho = funcaoDeAvaliacaoGLS.avaliar(melhorVizinho, lambda, penalidadesArestas);

        for (int i = 0; i < solucao.cidades.length - 1; i++) {
            for (int j = i + 2; j < solucao.cidades.length; j++) {

                if (!(i == 0 && j == solucao.cidades.length - 1)) {
                    Solucao vizinho = new Solucao(solucao.cidades.length);
                
                    int l = 0;
                    for (int k = 0; k <= i; k++) {
                        vizinho.cidades[l] = solucao.cidades[k];
                        l++;
                    }

                    for (int k = j; k > i; k--) {
                        vizinho.cidades[l] = solucao.cidades[k];
                        l++;
                    }

                    for (int k = j + 1; k < solucao.cidades.length; k++) {
                        vizinho.cidades[l] = solucao.cidades[k];
                        l++;
                    }

                    float valorVizinho = funcaoDeAvaliacaoGLS.avaliar(vizinho, lambda, penalidadesArestas);
                    if (valorVizinho < valorMelhorVizinho) {
                        valorMelhorVizinho = valorVizinho;
                        melhorVizinho = vizinho;
                    }
                }
            }
        }

        return melhorVizinho;
    }

    public Solucao firstImprovement_2_Opt (Solucao solucao, float lambda, int[][] penalidadesArestas) {
        
        int[] vertices1 = IntStream.rangeClosed(0, solucao.cidades.length - 2).toArray();
        int[] vertices2 = IntStream.rangeClosed(2, solucao.cidades.length - 1).toArray();
        
        while (true) {
            // shuffle(vertices1, vertices1.length);
            shuffle(vertices2, vertices2.length);

            float valorSolucaoAtual = funcaoDeAvaliacaoGLS.avaliar(solucao, lambda, penalidadesArestas);

            Solucao vizinho = obterPrimeiroVizinho(solucao, valorSolucaoAtual, lambda, penalidadesArestas, vertices1, vertices2);
            
            // Quando um vizinho não puder ser obtido é porque toda vizinhança foi explorada
            if (vizinho == null) {
                break;
            } else {
                float valorVizinho = funcaoDeAvaliacaoGLS.avaliar(vizinho, lambda, penalidadesArestas);
                solucao = vizinho;
                valorSolucaoAtual = valorVizinho;
            }
        } 

        return solucao;
    }

    public Solucao obterPrimeiroVizinho(Solucao solucao, float valorSolucaoAtual, float lambda, int[][] penalidadesArestas,
        int[] vertices1, int[] vertices2) {
        
        for (int i : vertices1) {
            for (int j : vertices2) {
                if (j > (i + 1) && !(i == 0 && j == solucao.cidades.length - 1)) {
                    Solucao vizinho = new Solucao(solucao.cidades.length);
                
                    int l = 0;
                    for (int k = 0; k <= i; k++) {
                        vizinho.cidades[l] = solucao.cidades[k];
                        l++;
                    }

                    for (int k = j; k > i; k--) {
                        vizinho.cidades[l] = solucao.cidades[k];
                        l++;
                    }

                    for (int k = j + 1; k < solucao.cidades.length; k++) {
                        vizinho.cidades[l] = solucao.cidades[k];
                        l++;
                    }

                    float valorVizinho = funcaoDeAvaliacaoGLS.avaliar(vizinho, lambda, penalidadesArestas);
                    if (valorVizinho < valorSolucaoAtual) {
                        return vizinho;
                    }
                }
            }
        }
        return null;
    }

    private void shuffle( int array[], int a){
        // Creating object for Random class
        Random rd = new Random(System.currentTimeMillis());
         
        // Starting from the last element and swapping one by one.
        for (int i = a-1; i > 0; i--) {
             
            // Pick a random index from 0 to i
            int j = rd.nextInt(i+1);
             
            // Swap array[i] with the element at random index
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
