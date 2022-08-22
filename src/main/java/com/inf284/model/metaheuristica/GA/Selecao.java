package com.inf284.model.metaheuristica.GA;

import com.inf284.utils.MersenneTwisterFast;

public class Selecao {

    MersenneTwisterFast rand = new MersenneTwisterFast();

    public Individuo metodoDaRoleta(Populacao populacao) {
        float limite = rand.nextFloat() * populacao.somaDasAptidoes;
        float aux = 0;
        Individuo individuo = null;

        var it = populacao.individuos.iterator();

        while (it.hasNext()) {
            individuo = it.next();
            aux += individuo.valorDeAptidao;
            if (aux >= limite) break;
        }

        return individuo;
    }

}
