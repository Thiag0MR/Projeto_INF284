package com.inf284.model.metaheuristica.componente;

import com.inf284.model.metaheuristica.ILS.Pertubacao;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class PertubacaoTest {

    @Test
    public void testarMetodoDoubleBridge() {
        Pertubacao pertubacao = new Pertubacao();
        // System.out.println(pertubacao.doubleBridge(new Solucao(new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16})));
        System.out.println(pertubacao.doubleBridge(new Solucao(new int[] {1,10,11,12,13,6,7,8,9,2,3,4,5,14,15,16})));
    }
}
