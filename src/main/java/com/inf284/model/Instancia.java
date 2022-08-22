package com.inf284.model;

public class Instancia {
    public String NAME;
    public int DIMENSION;
    public String EDGE_WEIGHT_TYPE;
    public String EDGE_WEIGHT_FORMAT;

    public int[][] MATRIZ_DISTANCIA;

    // O índice representa a cidade de origem e o valor a cidade de destino. Se o valor
    // for zero significa que não existe entrega. 
    public int[] ENTREGAS;

    public int VALOR_ENTREGA;

}
