package com.inf284.model.metaheuristica.GA;

import com.inf284.model.Instancia;
import com.inf284.model.metaheuristica.componente.FuncaoDeAvaliacao;

public class FuncaoDeAptidao {

    FuncaoDeAvaliacao funcaoDeAvaliacao;

    public FuncaoDeAptidao(Instancia instancia) {
        this.funcaoDeAvaliacao = new FuncaoDeAvaliacao(instancia);
    }

    public void calcularAptidaoPopulacao(Populacao populacao) {
        metodoWindowing(populacao);
        metodoEscalonamentoLinear(populacao);
    }

    // Esse método determina um novo valor de função objetivo para o individuo, o qual será usado no método
    // de escalonamento linear e utilizado para determinada a aptidão do individuo
    private void metodoWindowing(Populacao populacao) {
        // Parâmetro
        int C = 1;

        // O pior valor é o maior valor
        int piorValorFuncaoObjetivo = populacao.obterIndividuoComPiorValorFuncaoObjetivo().valorDaFuncaoObjetivo;

        for (Individuo i : populacao.individuos) {
            i.valorDaFuncaoObjetivoNormalizada = C - (i.valorDaFuncaoObjetivo - piorValorFuncaoObjetivo);
        }
    }

    // Determina a aptidão dos indivíduos de uma população utilizando o método escalonamento linear
    private void metodoEscalonamentoLinear(Populacao populacao) {

        // Parâmetro, geralmente entre 1.2 e 2.0
        float C = 1.2F;

        // A ser definido
        float a = 0;
        float b = 0;

        // Cálculo para determinar a e b
        float soma = 0;
        int valorMinimoFuncaoObjetivoNormalizada = populacao.obterIndividuoComPiorValorFuncaoObjetivo().valorDaFuncaoObjetivoNormalizada;
        int valorMaximoFuncaoObjetivoNormalizada = populacao.obterIndividuoComMelhorValorFuncaoObjetivo().valorDaFuncaoObjetivoNormalizada;

        for (Individuo i : populacao.individuos) {
            int v = i.valorDaFuncaoObjetivoNormalizada;
            soma += v;
        }
        float valorMedioFuncaoObjetivoNormalizada = soma / populacao.obterTamanhoDaPopulacao();

        if (valorMinimoFuncaoObjetivoNormalizada > (C*valorMedioFuncaoObjetivoNormalizada - valorMaximoFuncaoObjetivoNormalizada)/(C - 1)) {
            float delta = valorMaximoFuncaoObjetivoNormalizada - valorMedioFuncaoObjetivoNormalizada;
            a = (C - 1)*valorMedioFuncaoObjetivoNormalizada/delta;
            b = valorMedioFuncaoObjetivoNormalizada*(valorMaximoFuncaoObjetivoNormalizada - C*valorMedioFuncaoObjetivoNormalizada)/delta;
        } else{
            float delta = valorMedioFuncaoObjetivoNormalizada - valorMinimoFuncaoObjetivoNormalizada;
            a = valorMedioFuncaoObjetivoNormalizada/delta;
            b = -1*valorMedioFuncaoObjetivoNormalizada*valorMinimoFuncaoObjetivoNormalizada/delta;
        }
        // fim

        float somaDasAptidoes = 0;

        // Após calcular a e b podemos determinar a aptidão do indivíduo
        for (Individuo i : populacao.individuos) {
            i.valorDeAptidao = a * i.valorDaFuncaoObjetivoNormalizada + b;
            somaDasAptidoes += i.valorDeAptidao;
        }

        // Utilizado no método da roleta
        populacao.somaDasAptidoes = somaDasAptidoes;
    }

}
