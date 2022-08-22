package com.inf284.model.metaheuristica.GA;

import com.inf284.model.metaheuristica.componente.Solucao;
import com.inf284.utils.LerArquivo;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CruzamentoTest {

    Cruzamento cruzamento = new Cruzamento(LerArquivo.obterInstancia("gr17.tsp", 0, 0));

    @Test
    public void testarOperadorPMX() {
        Individuo pai1 = new Individuo(new Solucao(new int[]{1,2,3,4,5,6,7,8,9}), 0, 0);
        Individuo pai2 = new Individuo(new Solucao(new int[]{4,2,6,1,8,5,9,3,7}), 0, 0);
        // Cruzamento.operadorPMX(pai1, pai2);
    }

    @Test
    public void testarOperadorCX() {
        Individuo pai1 = new Individuo(new Solucao(new int[]{1,2,3,4,5,6,7,8,9}), 0, 0);
        Individuo pai2 = new Individuo(new Solucao(new int[]{4,1,2,8,7,6,9,3,5}), 0, 0);
        // Cruzamento.operadorCX(pai1, pai2);
    }

    @Test
    public void testarOperadorOX() {
        Individuo pai1 = new Individuo(new Solucao(new int[]{1,2,3,4,5,6,7,8}), 0, 0);
        Individuo pai2 = new Individuo(new Solucao(new int[]{2,4,6,8,7,5,3,1}), 0, 0);
        // Cruzamento.operadorOX(pai1, pai2);
    }

    @Test
    public void testarOperadorERX() {
        Individuo pai1 = new Individuo(new Solucao(new int[]{1,2,3,4,5,6}), 0, 0);
        Individuo pai2 = new Individuo(new Solucao(new int[]{3,4,1,6,2,5}), 0, 0);
        cruzamento.operadorERX(pai1, pai2);
    }
}
