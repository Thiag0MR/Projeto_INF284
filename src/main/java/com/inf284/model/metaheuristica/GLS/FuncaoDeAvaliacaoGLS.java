package com.inf284.model.metaheuristica.GLS;

import com.inf284.model.Instancia;
import com.inf284.model.metaheuristica.componente.Solucao;

public class FuncaoDeAvaliacaoGLS {

    Instancia instancia;

    public FuncaoDeAvaliacaoGLS(Instancia instancia) {
        this.instancia = instancia;
    }

    // A única diferença desse método para a função de avaliação normal é a adição da soma das penalidades
    // dos arcos e 
    public float avaliar(Solucao solucao, float lambda, int[][] penalidadesArestas) {

        int soma = 0;
        int somaPenalidadesArestas = 0;
        int c1 = 0;
        int c2 = 0;
        boolean[] cidadesVisitadas = new boolean [instancia.DIMENSION+1];
        int entregaSaindoDeC2 = 0;
        int qtdCidades = solucao.cidades.length;

        int i = 0;
        boolean aux = false;
        while (true) {
            if (solucao.cidades[i % qtdCidades] == 1) {
                if (aux) {
                    break;
                } else {
                    cidadesVisitadas[i] = true;
                    aux = true;
                }
            } else if (aux) {
                c1 = solucao.cidades[(i-1) % qtdCidades];
                c2 = solucao.cidades[i % qtdCidades];
                soma += instancia.MATRIZ_DISTANCIA[c1][c2];
                
                somaPenalidadesArestas += penalidadesArestas[c1][c2];

                cidadesVisitadas[c2] = true;

                entregaSaindoDeC2 = instancia.ENTREGAS[c2];

                // Verifica se existe uma entrega saindo de c2, caso exista, verifica se o destino
                // já foi visitado. Se o destino não apareceu antes, então a entrega é realizada e desconta-se
                // o valor da entrega.
                if (entregaSaindoDeC2 != 0 && !cidadesVisitadas[entregaSaindoDeC2]) {
                    soma -= instancia.VALOR_ENTREGA;   
                } 
            }
            i++;
        }

        c1 = solucao.cidades[(i-1) % qtdCidades];
        c2 = solucao.cidades[i % qtdCidades];
        soma += instancia.MATRIZ_DISTANCIA[c1][c2];

        somaPenalidadesArestas += penalidadesArestas[c1][c2];

        return soma + lambda * somaPenalidadesArestas;
    }
}
