package com.inf284.model.metaheuristica.GA;

import com.inf284.model.metaheuristica.componente.Solucao;

public class Individuo implements Comparable<Individuo>{
    Solucao solucao;
    int valorDaFuncaoObjetivo;

    // Seu valor é inverso ao da função objetivo, ou seja, o indivíduo que possui o melhor (menor)
    // valor de função objetivo possui o maior valor de valorDaFuncaoObjetivoNormalizada.
    int valorDaFuncaoObjetivoNormalizada;

    float valorDeAptidao;

    public Individuo(Solucao solucao, float valorDeAptidao, int valorDaFuncaoObjetivo) {
        this.solucao = solucao;
        this.valorDeAptidao = valorDeAptidao;
        this.valorDaFuncaoObjetivo = valorDaFuncaoObjetivo;
    }

    public Individuo geraCopia() {
        return new Individuo(this.solucao.gerarCopia(), this.valorDeAptidao, this.valorDaFuncaoObjetivo);
    }

    
    @Override
    public int compareTo(Individuo o) {
        Individuo i = (Individuo) o;
        return this.valorDaFuncaoObjetivo - i.valorDaFuncaoObjetivo;
    }
}
