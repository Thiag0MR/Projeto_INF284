package com.inf284.model.metaheuristica.ILS;

import com.inf284.model.Instancia;
import com.inf284.model.metaheuristica.componente.BuscaLocal;
import com.inf284.model.metaheuristica.componente.FuncaoDeAvaliacao;
import com.inf284.model.metaheuristica.componente.Solucao;

public class ILS {

    FuncaoDeAvaliacao funcaoDeAvaliacao;
    BuscaLocal buscaLocal;
    Pertubacao pertubacao;
    Instancia instancia;

    public ILS(Instancia instancia) {
        this.instancia = instancia;
        this.funcaoDeAvaliacao = new FuncaoDeAvaliacao(instancia);
        this.buscaLocal = new BuscaLocal(instancia);
        this.pertubacao = new Pertubacao();
    }

    public Solucao executar (Solucao solucao, int tMax) {

        long start = System.currentTimeMillis();

        // Busca local
        Solucao solucaoOtimoLocal = buscaLocal.bestImprovement_2_Opt(solucao);
        int valorOtimoLocal = funcaoDeAvaliacao.avaliar(solucaoOtimoLocal);

        Solucao solucaoIncumbente = solucaoOtimoLocal;
        int valorSolucaoIncumbente = valorOtimoLocal;
        System.out.println(instancia.NAME + " : " + valorSolucaoIncumbente);

        // Critério de parada
        while (System.currentTimeMillis() < (start + (tMax * 1_000 * 60))) {

            // Pertubação
            // Solucao solucaoPertubada = pertubacao.doisOpt(solucaoOtimoLocal, 3);
            Solucao solucaoPertubada = pertubacao.doubleBridge(solucaoOtimoLocal);

            // Busca local
            Solucao solucaoNovoOtimoLocal = buscaLocal.bestImprovement_2_Opt(solucaoPertubada);
            int valorNovoOtimoLocal = funcaoDeAvaliacao.avaliar(solucaoNovoOtimoLocal);

            // Critério de aceitação
            if (valorNovoOtimoLocal < valorSolucaoIncumbente) {
                solucaoIncumbente = solucaoNovoOtimoLocal;
                valorSolucaoIncumbente = valorNovoOtimoLocal;

                solucaoOtimoLocal = solucaoIncumbente;
                valorOtimoLocal = valorSolucaoIncumbente;

                System.out.println(instancia.NAME + " : " + valorSolucaoIncumbente);
            }

            // if (valorSolucaoIncumbente == 629) break;
        }

        // long stop = System.currentTimeMillis() - start;
        // System.out.println("Tempo em milisegundos: " + stop);
        // System.out.printf("Tempo em segundos: %.5f %n", (float) stop/1000);

        return solucaoIncumbente;
    }
}
