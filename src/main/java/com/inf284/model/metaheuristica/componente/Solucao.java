package com.inf284.model.metaheuristica.componente;

import java.util.Arrays;

public class Solucao {
    public final int[] cidades;
    
    public Solucao(int n) {
        this.cidades = new int[n];
    }

    public Solucao(int[] cidades) {
        this.cidades = cidades;
    }

    public Solucao gerarCopia() {
        Solucao solucao = new Solucao(this.cidades.length);
        int j = 0;
        for (int i : this.cidades) {
            solucao.cidades[j++] = i;
        }
        return solucao;
    }

    public String toString() {
        return Arrays.toString(this.cidades);
    }
}
