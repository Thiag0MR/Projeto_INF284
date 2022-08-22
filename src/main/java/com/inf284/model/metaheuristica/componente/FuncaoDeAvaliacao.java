package com.inf284.model.metaheuristica.componente;

import com.inf284.model.Instancia;

public class FuncaoDeAvaliacao {

    public final Instancia instancia;

    public FuncaoDeAvaliacao(Instancia instancia) {
        this.instancia = instancia;
    }

    public int avaliar(Solucao solucao) {

        int soma = 0;
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

        return soma;
    }

}
