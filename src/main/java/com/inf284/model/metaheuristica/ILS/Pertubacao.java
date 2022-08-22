package com.inf284.model.metaheuristica.ILS;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.inf284.model.metaheuristica.componente.Solucao;
import com.inf284.utils.MersenneTwisterFast;

public class Pertubacao {

    MersenneTwisterFast rand;

    public Pertubacao() {
        this.rand = new MersenneTwisterFast();
    }

    public Solucao doisOpt (Solucao solucao, int max) {

        Solucao solucaoPertubada = new Solucao(solucao.cidades.length);

        while (max > 0) {
            int i;
            int j;
            do {
                i = rand.nextInt(solucao.cidades.length - 2);
                j = rand.nextInt(solucao.cidades.length);
            } while(i >= (j-1) && !(i == 0 && j == solucao.cidades.length - 1));
            
            int l = 0;
            for (int k = 0; k <= i; k++) {
                solucaoPertubada.cidades[l] = solucao.cidades[k];
                l++;
            }

            for (int k = j; k > i; k--) {
                solucaoPertubada.cidades[l] = solucao.cidades[k];
                l++;
            }

            for (int k = j + 1; k < solucao.cidades.length; k++) {
                solucaoPertubada.cidades[l] = solucao.cidades[k];
                l++;
            }
            
            max--;
        }
        return solucaoPertubada;
    }

    public Solucao doubleBridge(Solucao solucao) {

        // Escolher 4 arestas aleatórias. Essas arestas não podem ser adjacentes.
        // Arestas não adjacentes que serão removidas: (i, i+1), (j, j+1), (k, k+1), (l, l+1)
        // Novas arestas: (i, k+1), (j, l+1), (k, i+1), (l, j+1)
        // Nova rota: i, k+1, ..., l, j+1, ..., k, i+1, ..., j, l+1, ..., i

        Solucao solucaoPertubada = new Solucao(solucao.cidades.length);
       
        int[] temp = new int[4];
        int qtdCidades = solucao.cidades.length;

        if (qtdCidades > 8) {
            temp = new int[4];
            int qtd = 0;
            Set<Integer> set = new HashSet<>();
            while (qtd < 4) {
                int num = rand.nextInt(qtdCidades);
                if (!set.contains(num)) {
                    temp[qtd] = num;
                    qtd++;
                    set.add(num);
                    set.add((num + 1) % qtdCidades);
                    if (num == 0) {
                        set.add(qtdCidades - 1);
                    } else {
                        set.add(num - 1);
                    }
                }
            }
        } else {
            temp = new int[] {0,2,4,6};
        }

        Arrays.sort(temp);

        int i = temp[0];
        int j = temp[1];
        int k = temp[2];
        int l = temp[3];

        int aux = 0;
        solucaoPertubada.cidades[aux++] = solucao.cidades[i];
        for (int p = k+1; p <= l; p++) {
            solucaoPertubada.cidades[aux++] = solucao.cidades[p];
        }
        for (int p = j+1; p <= k; p++) {
            solucaoPertubada.cidades[aux++] = solucao.cidades[p];
        }
        for (int p = i+1; p <= j; p++) {
            solucaoPertubada.cidades[aux++] = solucao.cidades[p];
        }
        for (int p = l+1; p < qtdCidades; p++) {
            solucaoPertubada.cidades[aux++] = solucao.cidades[p];
        }
        for (int p = 0; p < i; p++) {
            solucaoPertubada.cidades[aux++] = solucao.cidades[p];
        }
        
        return solucaoPertubada;
    }
}
