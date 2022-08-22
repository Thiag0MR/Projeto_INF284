package com.inf284.model.metaheuristica.GA;

import java.util.Arrays;

import com.inf284.model.metaheuristica.componente.Solucao;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class MutacaoTest {

    Mutacao mutacao = new Mutacao();
    Individuo i = new Individuo(new Solucao(new int[]{1,2,3,4,5,6,7,8,9,10}), 0, 0);

    @Test
    public void testarOperadorEM () {
        mutacao.operadorEM(i);
        System.out.println(Arrays.toString(i.solucao.cidades));
    }

    @Test
    public void testarOperadorSIM () {
        mutacao.operadorSIM(i);
        System.out.println(Arrays.toString(i.solucao.cidades));
    }

    @Test
    public void testarOperadorISM () {
        mutacao.operadorISM(i);
        System.out.println(Arrays.toString(i.solucao.cidades));
    }

    @Test
    public void testarOperadorDM () {
        mutacao.operadorDM(i);
        System.out.println(Arrays.toString(i.solucao.cidades));
    }

    @Test
    public void testarOperadorIVM () {
        mutacao.operadorIVM(i);
        System.out.println(Arrays.toString(i.solucao.cidades));
    }

    @Test
    public void testarOperadorSM () {
        mutacao.operadorSM(i);
        System.out.println(Arrays.toString(i.solucao.cidades));
    }
}
