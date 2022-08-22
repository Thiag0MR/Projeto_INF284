package com.inf284.model.metaheuristica.GA;

import com.inf284.model.Instancia;
import com.inf284.model.metaheuristica.componente.BuscaLocal;
import com.inf284.model.metaheuristica.componente.FuncaoDeAvaliacao;
import com.inf284.model.metaheuristica.componente.Solucao;
import com.inf284.utils.MersenneTwisterFast;

public class GA {

    Instancia instancia;
    Mutacao mutacao;
    Cruzamento cruzamento;
    Selecao selecao;
    FuncaoDeAptidao funcaoDeAptidao;
    FuncaoDeAvaliacao funcaoDeAvaliacao;
    MersenneTwisterFast rand;
    BuscaLocal buscaLocal;

    public GA(Instancia instancia) {
        this.instancia = instancia;
        this.mutacao = new Mutacao();
        this.cruzamento = new Cruzamento(instancia);
        this.selecao = new Selecao();
        this.funcaoDeAptidao = new FuncaoDeAptidao(instancia);
        this.funcaoDeAvaliacao = new FuncaoDeAvaliacao(instancia);
        this.rand = new MersenneTwisterFast();
        this.buscaLocal = new BuscaLocal(instancia);
    }

    public Solucao executar (int tMax) {
        
        // Parâmetros
        int tamanhoDaPopulacao = 150;
        float probabilidadeDeCruzamento = 0.85F;
        float probabilidadeDeMutacao = 0.02F;
        // int numeroDeGeracoes = 1000;
        int numeroDeGeracoesSemMelhora = 500_000;

        long start = System.currentTimeMillis();
        int t = 0;

        // Gera a população inicial
        Populacao populacao = new Populacao().gerarPopulacaoInicial(instancia, tamanhoDaPopulacao);

        funcaoDeAptidao.calcularAptidaoPopulacao(populacao);

        Individuo melhor = populacao.obterIndividuoComMelhorValorFuncaoObjetivo().geraCopia();   

        // Critério de parada
        while (t < numeroDeGeracoesSemMelhora && System.currentTimeMillis() < (start + (tMax * 1_000 * 60))) {
            Populacao populacaoIntermediaria = new Populacao(tamanhoDaPopulacao);

            // Recombinação (Cruzamento)
            for (int i = 0; i < Math.incrementExact((int) (tamanhoDaPopulacao * probabilidadeDeCruzamento)); i++) {
                Individuo pai1 = selecao.metodoDaRoleta(populacao);
                Individuo pai2 = selecao.metodoDaRoleta(populacao);

                // Individuo filho = cruzamento.operadorPMX(pai1, pai2);
                // Individuo filho = cruzamento.operadorCX(pai1, pai2);
                Individuo filho = cruzamento.operadorOX(pai1, pai2);
                // Individuo filho = cruzamento.operadorERX(pai1, pai2);

                // Mutação
                int l = rand.nextBoolean() ? 1 : 2;
                // int l = (int) Math.ceil(probabilidadeDeMutacao * filho.solucao.cidades.length);
                for (int j = 1; j <= l; j++) {
                    mutacao.operadorSIM(filho);  // Melhor 3900
                    // mutacao.operadorISM(filho); // 4532 
                    // mutacao.operadorEM(filho);  // 5660
                    // mutacao.operadorDM(filho); // 6200
                    // mutacao.operadorIVM(filho);  // 7908
                    // mutacao.operadorSM(filho); // 8746
                    // aux = true;
                }

                filho.valorDaFuncaoObjetivo = funcaoDeAvaliacao.avaliar(filho.solucao);
                
                populacaoIntermediaria.adicionarIndividuo(filho);
            }

            // Mutação (A mutação acima é melhor)
            // for (Individuo individuo : populacaoIntermediaria.individuos) {
            //     if (rand.nextFloat() < probabilidadeDeMutacao) {
            //         mutacao.operadorSIM(individuo);  
            //         // mutacao.operadorISM(individuo); 
            //         // mutacao.operadorDM(individuo);   
            //         // mutacao.operadorIVM(individuo);  
            //         // mutacao.operadorEM(individuo);   
            //         // mutacao.operadorSM(individuo);   
            //         // individuo.solucao = buscaLocal.bestImprovement_2_Opt(individuo.solucao);
            //     }
            // }

            // Reinserção por aptidão: os indivíduos são ordenados e os t = tamanhoDaPopulação são escolhidos para a próxima geração
            populacao.individuos.addAll(populacaoIntermediaria.individuos);
            while (populacao.obterTamanhoDaPopulacao() > tamanhoDaPopulacao) {
                populacao.individuos.pollLast();
            }

            funcaoDeAptidao.calcularAptidaoPopulacao(populacao);

            Individuo melhorDaPopulacao = populacao.obterIndividuoComMelhorValorFuncaoObjetivo();

            if (melhor.valorDaFuncaoObjetivo > melhorDaPopulacao.valorDaFuncaoObjetivo) {
                melhor = melhorDaPopulacao.geraCopia();
                System.out.println("Função objetivo: " + melhor.valorDaFuncaoObjetivo + " Aptidão: " + melhor.valorDeAptidao);
                t = 0;
            }else {
                t++;
            }
        }

        return melhor.solucao;
    }
}
