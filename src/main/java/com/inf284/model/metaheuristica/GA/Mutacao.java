package com.inf284.model.metaheuristica.GA;

import java.util.Arrays;

import com.inf284.utils.MersenneTwisterFast;

// import com.inf284.model.metaheuristica.GA.Populacao.Individuo;

public class Mutacao {

    MersenneTwisterFast rand = new MersenneTwisterFast();

    // Exchange mutation (EM) - Troca duas cidades aleatórias de lugar
    public void operadorEM (Individuo individuo) {
        int p1 = 0;
        int p2 = 0;
        int n = individuo.solucao.cidades.length;

        do {
            p1 = rand.nextInt(n);
            p2 = rand.nextInt(n);
        } while (p1 == p2);

        int swap = individuo.solucao.cidades[p1];
        individuo.solucao.cidades[p1] = individuo.solucao.cidades[p2];
        individuo.solucao.cidades[p2] = swap;
    }

    // Simple inversion mutation (SIM)
    // Seleciona dois pontos de corte e inverte a sequencia entre os dois pontos
    public void operadorSIM (Individuo individuo) {
        int n = individuo.solucao.cidades.length;
        int pontoDeCorte1 = rand.nextInt(n);
        int pontoDeCorte2 = rand.nextInt(n - 1);

        if (pontoDeCorte1 == pontoDeCorte2) {
            pontoDeCorte2 = n - 1;
        } else if (pontoDeCorte1 > pontoDeCorte2) {
            int swap = pontoDeCorte1;
            pontoDeCorte1 = pontoDeCorte2;
            pontoDeCorte2 = swap;
        }
        int i = pontoDeCorte1;
        int j = pontoDeCorte2;
        while (i < j) {
            int temp = individuo.solucao.cidades[i];
            individuo.solucao.cidades[i] = individuo.solucao.cidades[j];
            individuo.solucao.cidades[j] = temp;
            i++;
            j--;
        }
    }

    // Displacement mutation (DM) - Mutação por Deslocamento
    // Escolhe uma subrota aleatoriamente e a insire em um local aleatório
    public void operadorDM (Individuo individuo) {
        int n = individuo.solucao.cidades.length;
        int s1;
        int s2;

        do {
            s1 = rand.nextInt(n);
            s2 = rand.nextInt(n - 1);
            if (s1 == s2) {
                s2 = n - 1;
            } else if (s1 > s2) {
                int swap = s1;
                s1 = s2;
                s2 = swap;
            }
        } while(s1 == 0 && s2 == n-1);
        
        int[] subrota = Arrays.copyOfRange(individuo.solucao.cidades, s1, s2+1);
        int[] subrotaRestante = new int[n - (s2 - s1) - 1];

        int j = 0;
        for (int i = 0; i < n; i++) {
            if (i < s1 || i > s2) {
                subrotaRestante[j++] = individuo.solucao.cidades[i];
            }
        }

        int local = rand.nextInt(subrotaRestante.length);

        int k = 0;
        for (int i = 0; i < subrotaRestante.length; i++) {
            individuo.solucao.cidades[k++] = subrotaRestante[i];
            if (i == local) {
                for (j = 0; j < subrota.length; j++) {
                    individuo.solucao.cidades[k++] = subrota[j];
                }
            }
        }
    }

    // Insertion mutation (ISM) - Mutação por Inserção
    // Escolhe uma cidade aleatoriamente e a insere em um local aleatório
    public void operadorISM(Individuo individuo) {
        int p1 = 0;
        int p2 = 0;
        int n = individuo.solucao.cidades.length;

        do {
            p1 = rand.nextInt(n);
            p2 = rand.nextInt(n);
        } while (p1 == p2);

        if (p1 > p2) {
            int swap = p1;
            p1 = p2;
            p2 = swap;
        }

        int cidade = individuo.solucao.cidades[p1];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (i == p1) {
                continue;
            }

            individuo.solucao.cidades[j++] = individuo.solucao.cidades[i];

            if (i == p2) {
                individuo.solucao.cidades[j++] = cidade;
            } 
        }
    }

    // Inversion mutation (IVM) - Mutação por Inversão
    // Semelhante ao operador de deslocamento (DM) porém a subrota é invertida
    public void operadorIVM(Individuo individuo) {
        int n = individuo.solucao.cidades.length;
        int s1;
        int s2;

        do {
            s1 = rand.nextInt(n);
            s2 = rand.nextInt(n - 1);
            if (s1 == s2) {
                s2 = n - 1;
            } else if (s1 > s2) {
                int swap = s1;
                s1 = s2;
                s2 = swap;
            }
        } while(s1 == 0 && s2 == n-1);

        int[] subrotaInvertida = new int[s2-s1+1];
        int j = 0;
        for (int i = s2; i >= s1; i--) {
            subrotaInvertida[j++] = individuo.solucao.cidades[i];
        }
        
        int[] subrotaRestante = new int[n - (s2 - s1) - 1];

        j = 0;
        for (int i = 0; i < n; i++) {
            if (i < s1 || i > s2) {
                subrotaRestante[j++] = individuo.solucao.cidades[i];
            }
        }

        int local = rand.nextInt(subrotaRestante.length);

        int k = 0;
        for (int i = 0; i < subrotaRestante.length; i++) {
            individuo.solucao.cidades[k++] = subrotaRestante[i];
            if (i == local) {
                for (j = 0; j < subrotaInvertida.length; j++) {
                    individuo.solucao.cidades[k++] = subrotaInvertida[j];
                }
            }
        }
    }
    
    // Scramble mutation (SM)
    // Semelhante ao operador de deslocamento (DM) porém a subrota é embaralhada
    public void operadorSM(Individuo individuo) {
        int n = individuo.solucao.cidades.length;
        int s1;
        int s2;

        do {
            s1 = rand.nextInt(n);
            s2 = rand.nextInt(n - 1);
            if (s1 == s2) {
                s2 = n - 1;
            } else if (s1 > s2) {
                int swap = s1;
                s1 = s2;
                s2 = swap;
            }
        } while(s1 == 0 && s2 == n-1);
        
        int[] subrota = Arrays.copyOfRange(individuo.solucao.cidades, s1, s2+1);

        // Embaralha a subrota
        for (int i = 0; i < subrota.length; i++) {
            int pos = rand.nextInt(subrota.length);
            int swap = subrota[i];
            subrota[i] = subrota[pos];
            subrota[pos] = swap;
        }

        int[] subrotaRestante = new int[n - (s2 - s1) - 1];

        int j = 0;
        for (int i = 0; i < n; i++) {
            if (i < s1 || i > s2) {
                subrotaRestante[j++] = individuo.solucao.cidades[i];
            }
        }

        int local = rand.nextInt(subrotaRestante.length);

        int k = 0;
        for (int i = 0; i < subrotaRestante.length; i++) {
            individuo.solucao.cidades[k++] = subrotaRestante[i];
            if (i == local) {
                for (j = 0; j < subrota.length; j++) {
                    individuo.solucao.cidades[k++] = subrota[j];
                }
            }
        }
    }
    
}
