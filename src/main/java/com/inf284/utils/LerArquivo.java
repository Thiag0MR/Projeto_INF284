package com.inf284.utils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;

import com.inf284.model.Instancia;

public class LerArquivo {
    private static final String FILEPATH;
    // private static final String FILEPATH = "./Instancias/";
    // private static final String FILEPATH = "src/main/resources/Instancias/";

    static {
        if (System.getProperty("pathInstancias") != null) {
            FILEPATH = System.getProperty("pathInstancias");
        } else {
            FILEPATH = "./Instancias/";
        }
    }

    public static Instancia obterInstancia (String FILENAME, int k, int v) {

        Instancia instancia = new Instancia();

        try (Scanner in = new Scanner(Path.of(FILEPATH + FILENAME), StandardCharsets.UTF_8)) {

            in.useLocale(Locale.ENGLISH);
            
            while(in.hasNextLine()) {
                
                String line = in.nextLine();
                String[] words = line.split(":");

                switch (words[0].trim()) {
                    case "NAME": 
                        instancia.NAME = words[1].trim(); 
                        break;
                    case "DIMENSION": 
                        instancia.DIMENSION = Integer.parseInt(words[1].trim()); 
                        instancia.MATRIZ_DISTANCIA = new int[instancia.DIMENSION+1][instancia.DIMENSION+1];
                        break;
                    case "EDGE_WEIGHT_TYPE": 
                        instancia.EDGE_WEIGHT_TYPE = words[1].trim(); 
                        break;
                    case "NODE_COORD_SECTION":

                        float[][] coordenadas_2d = new float[instancia.DIMENSION+1][2];

                        for (int i = 1; i <= instancia.DIMENSION; i++) {
                            in.nextInt();
                            coordenadas_2d[i][0] = in.nextFloat();
                            coordenadas_2d[i][1] = in.nextFloat();
                        }

                        for (int i = 1; i <= instancia.DIMENSION; i++) {
                            for (int j = i; j <= instancia.DIMENSION; j++) {
                                if (i != j) {
                                    float xd = Math.abs(coordenadas_2d[i][0] - coordenadas_2d[j][0]);
                                    float yd = Math.abs(coordenadas_2d[i][1] - coordenadas_2d[j][1]);
                                    double dij = Math.sqrt(xd*xd + yd*yd);
                                    instancia.MATRIZ_DISTANCIA[i][j] = (int) Math.round(dij);
                                    instancia.MATRIZ_DISTANCIA[j][i] = (int) Math.round(dij);
                                }
                            }
                        }

                        break;
                    case "EDGE_WEIGHT_FORMAT": 
                        instancia.EDGE_WEIGHT_FORMAT = words[1].trim(); 
                        break;
                    case "EDGE_WEIGHT_SECTION":
                        if (instancia.EDGE_WEIGHT_FORMAT.equals("UPPER_ROW")) {
                            for (int i = 1; i < instancia.DIMENSION; i++) {
                                String[] nums = in.nextLine().split(" ");
                                int l = 1+i;
                                for (int j = 0; j < nums.length; j++) {
                                    instancia.MATRIZ_DISTANCIA[i][l] = Integer.parseInt(nums[j]);
                                    instancia.MATRIZ_DISTANCIA[l][i] = Integer.parseInt(nums[j]);
                                    l++;
                                }
                            }
                        } else if(instancia.EDGE_WEIGHT_FORMAT.equals("LOWER_DIAG_ROW")) {
                            int i = 1;
                            int j = 1;

                            while (in.hasNextInt()) {
                                int num = in.nextInt();
                                
                                instancia.MATRIZ_DISTANCIA[i][j] = num;
                                instancia.MATRIZ_DISTANCIA[j][i] = num;

                                if (i == j) {
                                    j = 1;
                                    i++;
                                } else {
                                    j++;
                                }
                            }
                        }
                        break;
                    default:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        instancia.ENTREGAS = new int[instancia.DIMENSION + 1];

        if (k != 0 && v != 0) {
            
            for (int i = 1; i <= k; i++) {
                instancia.ENTREGAS[2*i] = (2*i)+1;
            }

            instancia.VALOR_ENTREGA = v;
        }

        return instancia;
    }

}
