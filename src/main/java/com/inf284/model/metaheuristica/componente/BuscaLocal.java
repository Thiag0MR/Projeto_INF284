package com.inf284.model.metaheuristica.componente;

import com.inf284.model.Instancia;

public class BuscaLocal {

    private FuncaoDeAvaliacao funcaoDeAvaliacao;

    public BuscaLocal(Instancia instancia) {
        this.funcaoDeAvaliacao = new FuncaoDeAvaliacao(instancia);
    }

    public Solucao bestImprovement_2_Opt (Solucao solucao) {
        
        int valorSolucao = funcaoDeAvaliacao.avaliar(solucao);

        while (true) {
            Solucao vizinho = melhorVizinho(solucao);
            int valorVizinho = funcaoDeAvaliacao.avaliar(vizinho);

            if (valorVizinho < valorSolucao) {
                solucao = vizinho;
                valorSolucao = valorVizinho;
            } else {
                break;
            }
        } 

        return solucao;
    }

    private Solucao melhorVizinho(Solucao solucao) {

        Solucao melhorVizinho = solucao;
        int valorMelhorVizinho = funcaoDeAvaliacao.avaliar(melhorVizinho);

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

                    int valorVizinho = funcaoDeAvaliacao.avaliar(vizinho);
                    if (valorVizinho < valorMelhorVizinho) {
                        valorMelhorVizinho = valorVizinho;
                        melhorVizinho = vizinho;
                    }
                }
            }
        }

        return melhorVizinho;
    }
}
