package com.inf284.model.metaheuristica.componente;

import java.util.LinkedList;

import com.inf284.model.Instancia;

public class SolucaoInicial {

    public static Solucao construirSolucaoInicialSequencial(Instancia instancia) {
        
        Solucao solucao = new Solucao(instancia.DIMENSION);

        for (int i = 0; i < solucao.cidades.length; i++) {
            solucao.cidades[i] = i+1;
        }

        return solucao;
    }


    public static Solucao construirSolucaoInicialVizinhoMaisProximoPrimeiraVersao(Instancia instancia) {
        int qtdCidades = instancia.DIMENSION;
        Solucao solucao = new Solucao(qtdCidades);

        boolean[] cidadesVisitadas = new boolean[qtdCidades+1];
        
        // A origem é a cidade 1
        solucao.cidades[0] = 1;

        // Cidade 1 já foi visitada
        cidadesVisitadas[1] = true;

        for (int i = 1; i < solucao.cidades.length; i++) {
            int cidadeOrigem = solucao.cidades[i-1];
            int cidadeDestino = 0;
            int menorDistancia = 0;

            
            for (int j = 1; j <= qtdCidades; j++) {
                if (!cidadesVisitadas[j] && (menorDistancia == 0 || instancia.MATRIZ_DISTANCIA[cidadeOrigem][j] < menorDistancia)) {
                    menorDistancia = instancia.MATRIZ_DISTANCIA[cidadeOrigem][j];
                    cidadeDestino = j;
                } 
            }

            solucao.cidades[i] = cidadeDestino;
            cidadesVisitadas[cidadeDestino] = true;
        }

        return solucao;

    }

    public static Solucao construirSolucaoInicialVizinhoMaisProximoSegundaVersao(Instancia instancia) {
        int qtdCidades = instancia.DIMENSION;
        

        boolean[] cidadesVisitadas = new boolean[qtdCidades+1];
        int qtdCidadesVisitadas = 0;
        
        // Cidade 1 já foi visitada
        cidadesVisitadas[1] = true;
        qtdCidadesVisitadas++;

        LinkedList<Integer> rota = new LinkedList<>();
        rota.add(1);

        int origem = 1;
        int menorDistancia = 0;
        int destino = 0;

        for (int cidade = 1; cidade <= qtdCidades; cidade++) {
            if (!cidadesVisitadas[cidade] && (menorDistancia == 0 || instancia.MATRIZ_DISTANCIA[origem][cidade] < menorDistancia)) {
                menorDistancia = instancia.MATRIZ_DISTANCIA[origem][cidade];
                destino = cidade;
            }
        }

        cidadesVisitadas[destino] = true;
        qtdCidadesVisitadas++;
        rota.add(destino);

        while (qtdCidadesVisitadas < qtdCidades) {
           int primeiraCidade = rota.getFirst();
           int ultimaCidade = rota.getLast();

           int menorDistanciaPrimeiraCidade = 0;
           int menorDistanciaUltimaCidade = 0;

           int destinoPrimeiraCidade = 0;
           int destinoUltimaCidade = 0;

           for (int cidade = 1; cidade <= qtdCidades; cidade++) {
                if (!cidadesVisitadas[cidade]) {
                    if (menorDistanciaPrimeiraCidade == 0 || instancia.MATRIZ_DISTANCIA[primeiraCidade][cidade] < menorDistanciaPrimeiraCidade) {
                        menorDistanciaPrimeiraCidade = instancia.MATRIZ_DISTANCIA[primeiraCidade][cidade];
                        destinoPrimeiraCidade = cidade;
                    }

                    if (menorDistanciaUltimaCidade == 0 || instancia.MATRIZ_DISTANCIA[ultimaCidade][cidade] < menorDistanciaUltimaCidade) {
                        menorDistanciaUltimaCidade = instancia.MATRIZ_DISTANCIA[ultimaCidade][cidade];
                        destinoUltimaCidade = cidade;
                    }
                } 
            }
            if (menorDistanciaPrimeiraCidade < menorDistanciaUltimaCidade) {
                rota.addFirst(destinoPrimeiraCidade);
                cidadesVisitadas[destinoPrimeiraCidade] = true;
            } else {
                rota.addLast(destinoUltimaCidade);
                cidadesVisitadas[destinoUltimaCidade] = true;
            }
            qtdCidadesVisitadas++;
        }
        
        return new Solucao(rota.stream().mapToInt(o -> o).toArray());

    }

}
