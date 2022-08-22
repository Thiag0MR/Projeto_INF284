package com.inf284.model.metaheuristica.GA;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.inf284.model.Instancia;
import com.inf284.model.metaheuristica.componente.FuncaoDeAvaliacao;
import com.inf284.model.metaheuristica.componente.Solucao;
import com.inf284.utils.MersenneTwisterFast;

public class Cruzamento {

    MersenneTwisterFast rand;
    FuncaoDeAvaliacao funcaoDeAvaliacao;

    public Cruzamento(Instancia instancia) {
        this.rand = new MersenneTwisterFast();
        this.funcaoDeAvaliacao = new FuncaoDeAvaliacao(instancia);
    }

    // ERX (Edge Recombination)
    public Individuo operadorERX (Individuo pai1, Individuo pai2) {
        int n = pai1.solucao.cidades.length;
        boolean[] cidadesVisitadas = new boolean[n+1];

        Map<Integer, Set<Integer>> listaArestas = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int c1 = pai1.solucao.cidades[i];
            int c2 = pai2.solucao.cidades[i];
            if (!listaArestas.containsKey(c1)) {
                listaArestas.put(c1, new HashSet<>());
            }
            if (!listaArestas.containsKey(c2)) {
                listaArestas.put(c2, new HashSet<>());
            }

            if (i == 0) {
                listaArestas.get(c1).add(pai1.solucao.cidades[1]);
                listaArestas.get(c1).add(pai1.solucao.cidades[n-1]);
                listaArestas.get(c2).add(pai2.solucao.cidades[1]);
                listaArestas.get(c2).add(pai2.solucao.cidades[n-1]);
            } else if (i == n - 1) {
                listaArestas.get(c1).add(pai1.solucao.cidades[n-2]);
                listaArestas.get(c1).add(pai1.solucao.cidades[0]);
                listaArestas.get(c2).add(pai2.solucao.cidades[n-2]);
                listaArestas.get(c2).add(pai2.solucao.cidades[0]);
            } else {
                listaArestas.get(c1).add(pai1.solucao.cidades[i+1]);
                listaArestas.get(c1).add(pai1.solucao.cidades[i-1]);
                listaArestas.get(c2).add(pai2.solucao.cidades[i+1]);
                listaArestas.get(c2).add(pai2.solucao.cidades[i-1]);
            }
        }

        Solucao filho = new Solucao(n);

        if (listaArestas.get(pai1.solucao.cidades[0]).size() > listaArestas.get(pai2.solucao.cidades[0]).size()) {
            filho.cidades[0] = pai1.solucao.cidades[0];
        } else if (listaArestas.get(pai1.solucao.cidades[0]).size() < listaArestas.get(pai2.solucao.cidades[0]).size()) {
            filho.cidades[0] = pai2.solucao.cidades[0];
        } else {
            filho.cidades[0] = rand.nextBoolean() ? pai1.solucao.cidades[0] : pai2.solucao.cidades[0];
        }

        cidadesVisitadas[filho.cidades[0]] = true;

        for (int i = 1; i < n; i++) {
            int cidadeAnterior = filho.cidades[i - 1];
            int min = n + 1;
            int cidade = 0;
            for (int c : listaArestas.get(cidadeAnterior)) {
                if (min > listaArestas.get(c).size() && !cidadesVisitadas[c]) {
                    min = listaArestas.get(c).size();
                    cidade = c;
                }
            }
            if (cidade == 0) {
                for (int j = 1; j < cidadesVisitadas.length; j++) {
                    if (!cidadesVisitadas[j]) {
                        cidade = j;
                        break;
                    }
                }
            } 
            filho.cidades[i] = cidade;
            cidadesVisitadas[filho.cidades[i]] = true;
            
        }
        
        return new Individuo(filho, 0, funcaoDeAvaliacao.avaliar(filho));

    }

    //  OX (Order crossover)
    public Individuo operadorOX (Individuo pai1, Individuo pai2) {
        int n = pai1.solucao.cidades.length;

        int pontoDeCorte1 = rand.nextInt(n);
        int pontoDeCorte2 = rand.nextInt(n - 1);

        if (pontoDeCorte1 == pontoDeCorte2) {
            pontoDeCorte2 = n - 1;
        } else if (pontoDeCorte1 > pontoDeCorte2) {
            int swap = pontoDeCorte1;
            pontoDeCorte1 = pontoDeCorte2;
            pontoDeCorte2 = swap;
        }

        Solucao filho1 = new Solucao(n);
        Solucao filho2 = new Solucao(n);

        // Estruturas auxiliares
        boolean[] cidadesPresentesNoFilho1 = new boolean[n+1];
        boolean[] cidadesPresentesNoFilho2 = new boolean[n+1];

        for (int i = pontoDeCorte1; i <= pontoDeCorte2; i++) {
            filho1.cidades[i] = pai1.solucao.cidades[i];
            filho2.cidades[i] = pai2.solucao.cidades[i];

            cidadesPresentesNoFilho1[pai1.solucao.cidades[i]] = true;
            cidadesPresentesNoFilho2[pai2.solucao.cidades[i]] = true;
        }        

        int pos = (pontoDeCorte2 + 1) % n;
        int posLivreFilho1 = pos;
        int posLivreFilho2 = pos;
        do {
            int cidadePai2 = pai2.solucao.cidades[pos];
            if (!cidadesPresentesNoFilho1[cidadePai2]) {
                filho1.cidades[posLivreFilho1] = cidadePai2;
                // cidadesPresentesNoFilho1[cidadePai2] = true;
                posLivreFilho1 = (posLivreFilho1 + 1) % n;
            } 

            int cidadePai1 = pai1.solucao.cidades[pos];
            if (!cidadesPresentesNoFilho2[cidadePai1]) {
                filho2.cidades[posLivreFilho2] = cidadePai1;
                // cidadesPresentesNoFilho2[cidadePai1] = true;
                posLivreFilho2 = (posLivreFilho2 + 1) % n;
            } 

            pos = (pos + 1) % n;
        } while (pos != (pontoDeCorte2 + 1) % n);

        return verificarMelhorFilho(filho1, filho2);
    }

    // CX (Cycle Crossover)
    public Individuo operadorCX(Individuo pai1, Individuo pai2) {
        int n = pai1.solucao.cidades.length;

        Solucao filho1 = new Solucao(n);
        Solucao filho2 = new Solucao(n);

        // Estruturas auxiliares
        int[] mapeamentoCidadeIndicePai1 = new int[n+1];
        int[] mapeamentoCidadeIndicePai2 = new int[n+1];

        for (int i = 0; i < n; i++) {
            int c1 = pai1.solucao.cidades[i];
            mapeamentoCidadeIndicePai1[c1] = i;

            int c2 = pai2.solucao.cidades[i];
            mapeamentoCidadeIndicePai2[c2] = i;

            filho1.cidades[i] = pai2.solucao.cidades[i];
            filho2.cidades[i] = pai1.solucao.cidades[i];
        }

        int index = 0;
        do {
            filho1.cidades[index] = pai1.solucao.cidades[index];
            index = mapeamentoCidadeIndicePai1[pai2.solucao.cidades[index]];
        } while (index != 0);

        index = 0;
        do {
            filho2.cidades[index] = pai2.solucao.cidades[index];
            index = mapeamentoCidadeIndicePai2[pai1.solucao.cidades[index]];
        } while (index != 0);
 
        return verificarMelhorFilho(filho1, filho2);

    }

    // PMX (Partially Matched Crossover)
    public Individuo operadorPMX(Individuo pai1, Individuo pai2) {
        int n = pai1.solucao.cidades.length;

        int pontoDeCorte1 = rand.nextInt(n);
        int pontoDeCorte2 = rand.nextInt(n - 1);

        if (pontoDeCorte1 == pontoDeCorte2) {
            pontoDeCorte2 = n - 1;
        } else if (pontoDeCorte1 > pontoDeCorte2) {
            int swap = pontoDeCorte1;
            pontoDeCorte1 = pontoDeCorte2;
            pontoDeCorte2 = swap;
        }

        int[] map1 = new int[n+1];
        int[] map2 = new int[n+1];

        Solucao filho1 = new Solucao(n);
        Solucao filho2 = new Solucao(n);

        for (int i = pontoDeCorte1; i <= pontoDeCorte2; i++) {
            filho1.cidades[i] = pai2.solucao.cidades[i];
            filho2.cidades[i] = pai1.solucao.cidades[i];

            map1[pai2.solucao.cidades[i]] = pai1.solucao.cidades[i];
            map2[pai1.solucao.cidades[i]] = pai2.solucao.cidades[i];
        }

        for (int i = 0; i < n; i++) {
            if (i < pontoDeCorte1 || i > pontoDeCorte2) {
                int n1 = pai1.solucao.cidades[i];
                int m1 = map1[n1];

                int n2 = pai2.solucao.cidades[i];
                int m2 = map2[n2];

                while (m1 != 0) {
                    n1 = m1;
                    m1 = map1[m1];
                }

                while (m2 != 0) {
                    n2 = m2;
                    m2 = map2[m2];
                }

                filho1.cidades[i] = n1;
                filho2.cidades[i] = n2;
            }
        }

        return verificarMelhorFilho(filho1, filho2);
    }


    private Individuo verificarMelhorFilho (Solucao filho1, Solucao filho2) {
        Individuo escolhido;
        int valorDaFuncaoObjetivoFilho1 = funcaoDeAvaliacao.avaliar(filho1);
        int valorDaFuncaoObjetivoFilho2 = funcaoDeAvaliacao.avaliar(filho2);
        if (valorDaFuncaoObjetivoFilho1 < valorDaFuncaoObjetivoFilho2) {
            escolhido = new Individuo(filho1, 0, valorDaFuncaoObjetivoFilho1);
        } else {
            escolhido = new Individuo(filho2, 0, valorDaFuncaoObjetivoFilho2);
        }
        return escolhido;
    }
}
