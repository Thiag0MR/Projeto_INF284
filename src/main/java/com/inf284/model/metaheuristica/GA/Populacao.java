package com.inf284.model.metaheuristica.GA;

import com.google.common.collect.MinMaxPriorityQueue;
import com.inf284.model.Instancia;
import com.inf284.model.metaheuristica.componente.FuncaoDeAvaliacao;
import com.inf284.model.metaheuristica.componente.Solucao;
import com.inf284.model.metaheuristica.componente.SolucaoInicial;
import com.inf284.utils.MersenneTwisterFast;

public class Populacao {

    // São ordenados pela função objetivo, o cabeça da fila é o melhor, que no caso é o menor
    // TreeSet<Individuo> individuos;
    MinMaxPriorityQueue<Individuo> individuos;

    int tamanhoMaxPopulacao;
    float somaDasAptidoes;

    public Populacao() {
        // this.individuos = new TreeSet<>();
    }

    public Populacao (int tamanhoMaxPopulacao) {
        // this.individuos = new TreeSet<>();
        this.individuos = MinMaxPriorityQueue.expectedSize(tamanhoMaxPopulacao).create();
        this.tamanhoMaxPopulacao = tamanhoMaxPopulacao;
    }

    public void adicionarIndividuo(Individuo i) {
        if (this.individuos.size() < this.tamanhoMaxPopulacao && i != null) {
            this.individuos.add(i);
        }
    }

    public int obterTamanhoDaPopulacao() {
        return this.individuos.size();
    }
    
    public Populacao gerarPopulacaoInicial(Instancia instancia, int tamanhoPopulacao) {
        
        MersenneTwisterFast rand = new MersenneTwisterFast();
        FuncaoDeAvaliacao funcaoDeAvaliacao = new FuncaoDeAvaliacao(instancia);

        Populacao populacao = new Populacao(tamanhoPopulacao);

        while (populacao.obterTamanhoDaPopulacao() < tamanhoPopulacao) {

            Solucao solucao = SolucaoInicial.construirSolucaoInicialSequencial(instancia);

            for (int j = 0; j < solucao.cidades.length; j++) {
                int index =  rand.nextInt(solucao.cidades.length);
                int temp = solucao.cidades[index];
                solucao.cidades[index] = solucao.cidades[j];
                solucao.cidades[j] = temp;
            }
            Individuo i = new Individuo(solucao, -1, funcaoDeAvaliacao.avaliar(solucao));

            populacao.adicionarIndividuo(i);

        }
        return populacao;
    }

    public Individuo obterIndividuoComMelhorValorFuncaoObjetivo() {
        return this.individuos.peekFirst();
    }

    public Individuo obterIndividuoComPiorValorFuncaoObjetivo() {
        return this.individuos.peekLast();
    }
}
