import bin.*;

import java.io.IOException;

public class TesteUnitarios {
    public static void main(String[] args) throws IOException {
        testeCSVParaMatriz();
        testeMultiplicarMatrizes();
        testeDecomposicao();
        testeOrganizar();
        testeReduzirMatriz();
        testeTrasposta();
        testeCalculoDoEAM();
        testeLerCSVPeloFicheiro();
        testeNormalizar();
        testeMultiplicarPorEscalar();
        testeTrasformarVetor();
        testeCalVetorMedia();
        testeCentralizar();
        testeSomaDeMatrizes();
        testePegarVetor();
        testeCalcularU();
        testeCalcularPeso();
        testeReconstruirImagem();
        testeVetorParaMatriz();
        testeValorMaximo();
        testeDistanciaEuclidiana();
        testeDistanciasEuclidianasNovaParaImagens();
        testeIdentificarImagem();
        testeCriacaoDeImagens();
        testeNormalizarDados();
        testeValorMinimo();
    }

    public static void comparacao(int exemplo, double[][] outputEsperado, double[][] output, String teste) {
        for (int i = 0; i < outputEsperado.length; i++) {
            for (int j = 0; j < outputEsperado[0].length; j++) {
                if (output[i][j] != outputEsperado[i][j]) {
                    i = outputEsperado.length;
                    j = outputEsperado[0].length;
                    System.out.println("O teste de " + teste + " não passou no exemplo: " + exemplo);
                }
            }
        }
    }

    public static double[][] arredondarMatriz(double[][] matriz) {
        double[][] matrizArredondada = new double[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                matrizArredondada[i][j] = Math.round(matriz[i][j] * 100.0) / 100.0;
            }
        }
        return matrizArredondada;
    }

    public static void testeCSVParaMatriz() throws IOException {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            String entrada = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    entrada = "Testes/csvTeste1.csv";
                    outputEsperado = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                }
                case 1 -> {
                    entrada = "Testes/csvTeste2.csv";
                    outputEsperado = new double[][]{
                            {3, 1, 3},
                            {21, 20, 20},
                            {12, 1, 40}
                    };
                }
                case 2 -> {
                    entrada = "Testes/csvTeste3.csv";
                    outputEsperado = new double[][]{
                            {4, 1, 23},
                            {55, 24, 3},
                            {52, 23, 40}
                    };
                }
            }
            double[][] output = Funcionalidade1.csvParaMatriz(entrada);
            comparacao(exemplo, outputEsperado, output, "CSV para Matriz");
        }
    }

    public static void testeLerCSVPeloFicheiro() throws IOException {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            String entrada = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    entrada = "Testes/BaseTeste1";
                    outputEsperado = new double[][]{
                            {0, 3, 4},
                            {180, 21, 55},
                            {190, 12, 52},
                            {180, 1, 1},
                            {20, 20, 24},
                            {120, 1, 23},
                            {190, 3, 23},
                            {120, 20, 3},
                            {240, 40, 40}
                    };
                }
                case 1 -> {
                    entrada = "Testes/BaseTeste2";
                    outputEsperado = new double[][]{
                            {13, 17, 48},
                            {17, 77, 81},
                            {77, 44, 0},
                            {43, 62, 4},
                            {82, 67, 8},
                            {58, 10, 52},
                            {17, 79, 92},
                            {40, 13, 36},
                            {22, 76, 10}
                    };
                }
                case 2 -> {
                    entrada = "Testes/BaseTeste3";
                    outputEsperado = new double[][]{
                            {87, 72, 88},
                            {39, 4, 25},
                            {58, 78, 23},
                            {98, 70, 41},
                            {99, 59, 49},
                            {91, 37, 3},
                            {84, 76, 41},
                            {81, 27, 59},
                            {67, 57, 87}
                    };
                }
            }
            double[][] output = Funcionalidade2.lerCsvPeloFicheiro(entrada);
            comparacao(exemplo, outputEsperado, output, "Ler CSV pelo Ficheiro");
        }
    }

    public static void testeMultiplicarMatrizes() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] input2 = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    input2 = new double[][]{
                            {1, 0, 0},
                            {0, 1, 0},
                            {0, 0, 1}
                    };
                    outputEsperado = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    input2 = new double[][]{
                            {5, 6},
                            {7, 8}
                    };
                    outputEsperado = new double[][]{
                            {19, 22},
                            {43, 50}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    input2 = new double[][]{
                            {1, 0, 0, 0},
                            {0, 1, 0, 0},
                            {0, 0, 1, 0},
                            {0, 0, 0, 1}
                    };
                    outputEsperado = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                }
            }
            double[][] output = Funcionalidade1.multiplicadorDeMatrizes(input1, input2);
            comparacao(exemplo, outputEsperado, output, "Multiplicação de Matrizes");
        }
    }

    public static void testeDecomposicao() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] outputEsperado1 = null;
            double[][] outputEsperado2 = null;
            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    outputEsperado1 = new double[][]{
                            {-0.50, -0.36, 0.78},
                            {-0.43, -0.68, -0.59},
                            {-0.75, 0.64, -0.19}
                    };
                    outputEsperado2 = new double[][]{
                            {437.14, 0, 0},
                            {0, 3.67, 0},
                            {0, 0, -180.81}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    outputEsperado1 = new double[][]{
                            {-0.82, -0.42},
                            {0.57, -0.92}
                    };
                    outputEsperado2 = new double[][]{
                            {-0.37, 0},
                            {0, 5.37}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    outputEsperado1 = new double[][]{
                            {-0.15, 0.77, 0.50, -0.37},
                            {-0.35, 0.30, -0.83, 1.06},
                            {-0.55, -0.17, 0.16, -1.01},
                            {-0.75, -0.64, 0.17, 0.32}
                    };
                    outputEsperado2 = new double[][]{
                            {36.21, 0, 0, 0},
                            {0, -2.21, 0, 0},
                            {0, 0, 0.00, 0},
                            {0, 0, 0, 0.00}
                    };
                }
            }
            double[][] output1 = arredondarMatriz(Funcionalidade1.decomposicao(input1)[0]);
            double[][] output2 = arredondarMatriz(Funcionalidade1.decomposicao(input1)[1]);
            comparacao(exemplo, outputEsperado1, output1, "Decomposição para Autovetores");
            comparacao(exemplo, outputEsperado2, output2, "Decomposição do Autovalores");
        }
    }

    public static void testeOrganizar() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] input2 = null;
            double[][] outputEsperado1 = null;
            double[][] outputEsperado2 = null;
            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    input2 = new double[][]{
                            {10, 80, 90},
                            {10, 120, 20},
                            {1, 0, 230}
                    };
                    outputEsperado1 = new double[][]{
                            {80, 90, 10},
                            {120, 20, 10},
                            {0, 230, 1}
                    };
                    outputEsperado2 = new double[][]{
                            {20, 180, 190},
                            {180, 240, 120},
                            {190, 120, 0}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    input2 = new double[][]{
                            {12, 1},
                            {2, 5}
                    };
                    outputEsperado1 = new double[][]{
                            {1, 12},
                            {5, 2}
                    };
                    outputEsperado2 = new double[][]{
                            {4, 2},
                            {3, 1}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    input2 = new double[][]{
                            {12, 23, 3, 41},
                            {35, 26, 72, 18},
                            {19, 210, 31, 22},
                            {34, 12, 33, 12}
                    };
                    outputEsperado1 = new double[][]{
                            {23, 3, 41, 12},
                            {26, 72, 18, 35},
                            {210, 31, 22, 19},
                            {12, 33, 12, 34}
                    };
                    outputEsperado2 = new double[][]{
                            {6, 2, 3, 4},
                            {5, 11, 7, 8},
                            {9, 10, 16, 12},
                            {13, 14, 15, 1}
                    };
                }
            }
            double[][][] outputs = Funcionalidade1.organizar(input1,input2);
            double[][] output1 = outputs[0];
            double[][] output2 = outputs[1];
            comparacao(exemplo, outputEsperado1, output1, "Organização 1");
            comparacao(exemplo, outputEsperado2, output2, "Organização 2");
        }
    }

    public static void testeReduzirMatriz() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] input2 = null;
            int k = 9;
            double[][] outputEsperado1 = null;
            double[][] outputEsperado2 = null;
            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    input2 = new double[][]{
                            {10, 80, 90},
                            {10, 120, 20},
                            {1, 0, 230}
                    };
                    k = 1;
                    outputEsperado1 = new double[][]{
                            {0},
                            {180},
                            {190}
                    };
                    outputEsperado2 = new double[][]{
                            {10}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    input2 = new double[][]{
                            {12, 1},
                            {2, 5}
                    };
                    k = 2;
                    outputEsperado1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    outputEsperado2 = new double[][]{
                            {12, 0},
                            {0, 5}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    input2 = new double[][]{
                            {12, 23, 3, 41},
                            {35, 26, 72, 18},
                            {19, 210, 31, 22},
                            {34, 12, 33, 12}
                    };
                    k = 4;
                    outputEsperado1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    outputEsperado2 = new double[][]{
                            {12, 0, 0, 0},
                            {0, 26, 0, 0},
                            {0, 0, 31, 0},
                            {0, 0, 0, 12}
                    };
                }
            }
            double[][][] outputs = Funcionalidade1.reduzirMatriz(input1,input2,k);
            double[][] output1 = outputs[0];
            double[][] output2 = outputs[1];
            comparacao(exemplo, outputEsperado1, output1, "Redução 1");
            comparacao(exemplo, outputEsperado2, output2, "Redução 2");
        }
    }

    public static void testeTrasposta() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    outputEsperado = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    outputEsperado = new double[][]{
                            {1, 3},
                            {2, 4}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    outputEsperado = new double[][]{
                            {1, 5, 9, 13},
                            {2, 6, 10, 14},
                            {3, 7, 11, 15},
                            {4, 8, 12, 16}
                    };
                }
            }
            double[][] output = Funcionalidade1.transposta(input1);
            comparacao(exemplo, outputEsperado, output, "Transposta");
        }
    }

    public static void testeCalculoDoEAM() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] input2 = null;
            double outputEsperado = 0;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    input2 = new double[][]{
                            {1, 0, 0},
                            {0, 1, 0},
                            {0, 0, 1}
                    };
                    outputEsperado = 137.67;
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    input2 = new double[][]{
                            {5, 6},
                            {7, 8}
                    };
                    outputEsperado = 4.00;
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    input2 = new double[][]{
                            {1, 0, 0, 0},
                            {0, 1, 0, 0},
                            {0, 0, 1, 0},
                            {0, 0, 0, 1}
                    };
                    outputEsperado = 8.25;
                }
            }
            double output = Math.round(Funcionalidade1.calcularEAM(input1, input2) * 100.00) / 100.00;
            if (output != outputEsperado) {
                System.out.println("O teste de Calculo do EAM não passou no exemplo: " + exemplo);
            }
        }
    }

    public static void testeTrasformarVetor() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {170, 20, 120},
                            {180, 110, 140}
                    };
                    outputEsperado = new double[][]{
                            {0},
                            {170},
                            {180},
                            {180},
                            {20},
                            {110},
                            {190},
                            {120},
                            {140}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    outputEsperado = new double[][]{
                            {1},
                            {3},
                            {2},
                            {4}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    outputEsperado = new double[][]{
                            {1},
                            {5},
                            {9},
                            {13},
                            {2},
                            {6},
                            {10},
                            {14},
                            {3},
                            {7},
                            {11},
                            {15},
                            {4},
                            {8},
                            {12},
                            {16}
                    };
                }
            }
            double[][] output = Funcionalidade2.transformarEmVetor(input1);
            comparacao(exemplo, outputEsperado, output, "Trandformar em Vetor");
        }
    }

    public static void testeMultiplicarPorEscalar() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            int input2 = 0;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    input2 = 2;
                    outputEsperado = new double[][]{
                            {0, 360, 380},
                            {360, 40, 240},
                            {380, 240, 480}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    input2 = 4;
                    outputEsperado = new double[][]{
                            {4, 8},
                            {12, 16}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    input2 = 3;
                    outputEsperado = new double[][]{
                            {3, 6, 9, 12},
                            {15, 18, 21, 24},
                            {27, 30, 33, 36},
                            {39, 42, 45, 48}
                    };
                }
            }
            double[][] output = Funcionalidade2.multiplicadorDeMatrizComEscalar(input1, input2);
            comparacao(exemplo, outputEsperado, output, "Multiplicação por Escalar");
        }
    }

    public static void testeNormalizar() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    outputEsperado = new double[][]{
                            {0.00, 0.83, 0.58},
                            {0.69, 0.09, 0.36},
                            {0.73, 0.55, 0.73}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    outputEsperado = new double[][]{
                            {0.32, 0.45},
                            {0.95, 0.89}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    outputEsperado = new double[][]{
                            {0.06, 0.11, 0.15, 0.18},
                            {0.30, 0.33, 0.35, 0.37},
                            {0.54, 0.55, 0.55, 0.55},
                            {0.78, 0.76, 0.75, 0.73}
                    };
                }
            }
            double[][] output = arredondarMatriz(Funcionalidade2.normalizarVetores(input1));
            comparacao(exemplo, outputEsperado, output, "Normalização");
        }
    }

    public static void testeCalVetorMedia() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {170, 20, 120},
                            {180, 110, 140}
                    };
                    outputEsperado = new double[][]{
                            {123.33},
                            {103.33},
                            {143.33}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    outputEsperado = new double[][]{
                            {1.5},
                            {3.5}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    outputEsperado = new double[][]{
                            {2.5},
                            {6.5},
                            {10.5},
                            {14.5}
                    };
                }
            }
            double[][] output = arredondarMatriz(Funcionalidade2.calcVetorMedio(input1));
            comparacao(exemplo, outputEsperado, output, "Vetor Media");
        }
    }

    public static void testeCentralizar() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] input2 = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    input2 = new double[][]{
                            {123.33},
                            {103.33},
                            {143.33}
                    };

                    outputEsperado = new double[][]{
                            {-123.33, 56.67, 66.67},
                            {76.67, -83.33, 16.67},
                            {46.67, -23.33, 96.67}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    input2 = new double[][]{
                            {1.5},
                            {3.5}
                    };
                    outputEsperado = new double[][]{
                            {-0.5, 0.5},
                            {-0.5, 0.5}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    input2 = new double[][]{
                            {2.5},
                            {6.5},
                            {10.5},
                            {14.5}
                    };
                    outputEsperado = new double[][]{
                            {-1.5, -0.5, 0.5, 1.5},
                            {-1.5, -0.5, 0.5, 1.5},
                            {-1.5, -0.5, 0.5, 1.5},
                            {-1.5, -0.5, 0.5, 1.5}
                    };
                }
            }
            double[][] output = arredondarMatriz(Funcionalidade2.calculoMatrizA(input1, input2));
            comparacao(exemplo, outputEsperado, output, "Centralização");
        }
    }

    public static void testeSomaDeMatrizes() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] input2 = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    input2 = new double[][]{
                            {1, 30, 4},
                            {0, 1, 5},
                            {4, 22, 44}
                    };
                    outputEsperado = new double[][]{
                            {1, 210, 194},
                            {180, 21, 125},
                            {194, 142, 284}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    input2 = new double[][]{
                            {5, 6},
                            {7, 8}
                    };
                    outputEsperado = new double[][]{
                            {6, 8},
                            {10, 12}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    input2 = new double[][]{
                            {1, 34, 3, 32},
                            {23, 13, 3, 56},
                            {43, 2, 11, 22},
                            {34, 3, 33, 14}
                    };
                    outputEsperado = new double[][]{
                            {2, 36, 6, 36},
                            {28, 19, 10, 64},
                            {52, 12, 22, 34},
                            {47, 17, 48, 30}
                    };
                }
            }
            double[][] output = Funcionalidade2.somaDeMatrizes(input1, input2);
            comparacao(exemplo, outputEsperado, output, "Soma de Matrizes");
        }
    }

    public static void testePegarVetor() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            int input2 = 9;
            double[][] outputEsperado = null;
            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {170, 20, 120},
                            {180, 110, 140}
                    };
                    input2 = 0;
                    outputEsperado = new double[][]{
                            {0},
                            {170},
                            {180}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    input2 = 1;
                    outputEsperado = new double[][]{
                            {2},
                            {4}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    input2 = 2;
                    outputEsperado = new double[][]{
                            {3},
                            {7},
                            {11},
                            {15}
                    };
                }
            }
            double[][] output = Funcionalidade2.pegarVetor(input1, input2);
            comparacao(exemplo, outputEsperado, output, "Pegar Vetor");
        }
    }

    public static void testeCalcularU() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    outputEsperado = new double[][]{
                            {-0.50, -0.78, -0.36},
                            {-0.43, 0.59, -0.68},
                            {-0.75, 0.19, 0.64}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {34, 122, 193},
                            {155, 206, 133},
                            {148, 123, 243}
                    };
                    outputEsperado = new double[][]{
                            {-0.47, -0.52, 0.71},
                            {-0.59, 0.78, 0.19},
                            {-0.65, -0.33, -0.68}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {223, 130, 113},
                            {121, 23, 133},
                            {136, 45, 130}
                    };
                    outputEsperado = new double[][]{
                            {0.73, 0.67, 0.14},
                            {0.46, -0.63, 0.62},
                            {0.50, -0.39, -0.77}
                    };
                }
            }
            double[][] output = arredondarMatriz(Funcionalidade2.calcularU(input1));
            comparacao(exemplo, outputEsperado, output, "Calculo da Matriz U");
        }
    }

    public static void testeCalcularPeso() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] input2 = null;
            int k = 0;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    input2 = new double[][]{
                            {1, 0, 0},
                            {0, 1, 0},
                            {0, 0, 1}
                    };
                    k=3;
                    outputEsperado = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    input2 = new double[][]{
                            {5, 6},
                            {7, 8}
                    };
                    k = 1;
                    outputEsperado = new double[][]{
                            {26, 30}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    input2 = new double[][]{
                            {1, 0, 0, 0},
                            {0, 1, 0, 0},
                            {0, 0, 1, 0},
                            {0, 0, 0, 1}
                    };
                    k=2;
                    outputEsperado = new double[][]{
                            {1, 5, 9, 13},
                            {2, 6, 10, 14}
                    };
                }
            }
            double[][] output = Funcionalidade2.calcularMatrizPeso(input1, input2,k);
            comparacao(exemplo, outputEsperado, output, "Calculo da Matriz Peso");
        }
    }

    public static void testeReconstruirImagem() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] input2 = null;
            double[][] input3 = null;
            int k = 0;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0},
                            {180},
                            {190}
                    };
                    input2 = new double[][]{
                            {1, 0, 0},
                            {0, 1, 0},
                            {0, 0, 1}
                    };
                    k=3;
                    input3 = new double[][]{
                            {0, 10, 190},
                            {80, 120, 220},
                            {10, 20, 30}
                    };
                    outputEsperado = new double[][]{
                            {0, 10, 190},
                            {260, 300, 400},
                            {200, 210, 220}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1},
                            {3}
                    };
                    input2 = new double[][]{
                            {5, 6},
                            {7, 8}
                    };
                    k = 1;
                    input3 = new double[][]{
                            {26, 30},
                            {13, 45}
                    };
                    outputEsperado = new double[][]{
                            {131, 151},
                            {185, 213}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1},
                            {5},
                            {9},
                            {13}
                    };
                    input2 = new double[][]{
                            {1, 0, 0, 0},
                            {0, 1, 0, 0},
                            {0, 0, 1, 0},
                            {0, 0, 0, 1}
                    };
                    k=2;
                    input3 = new double[][]{
                            {14, 23, 23, 43},
                            {53, 63, 7, 82},
                            {91, 10, 121, 142},
                            {133, 144, 135, 216}
                    };
                    outputEsperado = new double[][]{
                            {15, 24, 24, 44},
                            {58, 68, 12, 87},
                            {9, 9, 9, 9},
                            {13, 13, 13, 13}
                    };
                }
            }
            double[][] output = Funcionalidade2.reconstruirImagem(input1, input2,k,input3);
            comparacao(exemplo, outputEsperado, output, "Reconstrução da Imagem");
        }
    }

    public static void testeVetorParaMatriz() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double[][] outputEsperado1 = null;
            double[][] outputEsperado2 = null;
            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {3, 11},
                            {23, 6},
                            {33, 24},
                            {87, 19}
                    };
                    outputEsperado1 = new double[][]{
                            {3, 11},
                            {23, 6}
                    };
                    outputEsperado2 = new double[][]{
                            {33, 24},
                            {87, 19}
                    };
                }
                case 1 -> {
                    input1 = new double[][]{
                            {13, 1},
                            {3, 34},
                            {13, 12},
                            {23, 4}
                    };
                    outputEsperado1 = new double[][]{
                            {13, 1},
                            {3, 34}
                    };
                    outputEsperado2 = new double[][]{
                            {13, 12},
                            {23, 4}
                    };
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {5, 6},
                            {9, 10},
                            {13, 14}
                    };
                    outputEsperado1 = new double[][]{
                            {1, 2},
                            {5, 6}
                    };
                    outputEsperado2 = new double[][]{
                            {9, 10},
                            {13, 14}
                    };
                }
            }
            double[][][] outputs = Funcionalidade2.vetorParaMatriz(input1);
            double[][] output1 = outputs[0];
            double[][] output2 = outputs[1];
            comparacao(exemplo, outputEsperado1, output1, "Vetor para Matriz 1");
            comparacao(exemplo, outputEsperado2, output2, "Vetor para Matriz 2");
        }
    }

    public static void testeValorMaximo() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] input1 = null;
            double outputEsperado = 0;

            switch (exemplo) {
                case 0 -> {
                    input1 = new double[][]{
                            {0, 180, 190},
                            {180, 20, 120},
                            {190, 120, 240}
                    };
                    outputEsperado = 240;
                }
                case 1 -> {
                    input1 = new double[][]{
                            {1, 2},
                            {3, 4}
                    };
                    outputEsperado = 4;
                }
                case 2 -> {
                    input1 = new double[][]{
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                    };
                    outputEsperado = 16;
                }
            }
            double[][][] copiaInput1 = new double[input1.length][input1[0].length][1];
            for (int i = 0; i < input1.length; i++) {
                for (int j = 0; j < input1[0].length; j++) {
                    copiaInput1[i][j][0] = input1[i][j];
                }
            }
            double output = Funcionalidade2.valorMaximo(copiaInput1,0);
            if (output != outputEsperado) {
                System.out.println("O teste de Calculo do Valor Máximo não passou no exemplo: " + exemplo);
            }
        }
    }
    public static void testeDistanciaEuclidiana() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] omegaNova = null;
            double[][] omega = null;
            int img = 95;
            double outputEsperado = 0.0;

            switch (exemplo) {
                case 0 -> {
                    omegaNova = new double[][]{
                            {1},
                            {2},
                            {3}
                    };
                    omega = new double[][]{
                            {1, 2, 3},
                            {2, 3, 4},
                            {3, 4, 5}
                    };
                    img = 0;
                    outputEsperado = 0.0;
                }
                case 1 -> {
                    omegaNova = new double[][]{
                            {0},
                            {0},
                            {0}};
                    omega = new double[][]{
                            {3, 6},
                            {4, 8},
                            {0, 5}
                    };
                    img = 1;
                    outputEsperado = Math.sqrt(125);
                }
                case 2 -> {
                    omegaNova = new double[][]{
                            {10},
                            {20}
                    };
                    omega = new double[][]{
                            {5, 10},
                            {10, 15}
                    };
                    img = 1;
                    outputEsperado = 5.00;
                }
            }
            double output = (Funcionalidade3.distanciaEuclidiana(omegaNova, omega, img) * 100.00) / 100.00;
            if (output != outputEsperado) {
                System.out.println("O teste de Calculo da Distancia Euclidiana não passou no exemplo: " + exemplo);
            }
        }
    }

    public static void testeIdentificarImagem() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] matrizImagens = null;
            double[][] distanciasNovaParaImagens = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    matrizImagens = new double[][]{
                            {1, 2, 3},
                            {4, 5, 6},
                            {7, 8, 9}
                    };
                    distanciasNovaParaImagens = new double[][]{
                            {5},
                            {2},
                            {10}
                    };
                    outputEsperado = new double[][]{
                            {2},
                            {5},
                            {8}
                    };
                }
                case 1 -> {
                    matrizImagens = new double[][]{
                            {10, 20},
                            {30, 40}
                    };
                    distanciasNovaParaImagens = new double[][]{
                            {50},
                            {25}
                    };
                    outputEsperado = new double[][]{
                            {20},
                            {40}
                    };
                }
                case 2 -> {
                    matrizImagens = new double[][]{
                            {0, 1, 2},
                            {3, 4, 5},
                            {6, 7, 8}
                    };
                    distanciasNovaParaImagens = new double[][]{
                            {3},
                            {2},
                            {1}
                    };
                    outputEsperado = new double[][]{
                            {2},
                            {5},
                            {8}
                    };
                }
            }
            double[][] output = Funcionalidade3.identificarImagem(matrizImagens, distanciasNovaParaImagens);
            comparacao(exemplo, outputEsperado, output, "Identificar Imagem");
        }
    }

    public static void testeDistanciasEuclidianasNovaParaImagens() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] omegaNova = null;
            double[][] omega = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    omegaNova = new double[][]{
                            {1},
                            {2}
                    };
                    omega = new double[][]{
                            {1, 2},
                            {2, 3}
                    };
                    outputEsperado = new double[][]{
                            {0.0},
                            {1.41}
                    };
                }
                case 1 -> {
                    omegaNova = new double[][]{
                            {3},
                            {4},
                            {0}
                    };
                    omega = new double[][]{
                            {3, 6},
                            {4, 8},
                            {0, 5}
                    };
                    outputEsperado = new double[][]{
                            {0.00},
                            {7.07}
                    };
                }
                case 2 -> {
                    omegaNova = new double[][]{
                            {1},
                            {1},
                            {1}
                    };
                    omega = new double[][]{
                            {1, 2, 3},
                            {1, 1, 1},
                            {1, 1, 1}
                    };
                    outputEsperado = new double[][]{
                            {0.0},
                            {1.00},
                            {2.00}
                    };
                }
            }
            double[][] output = Funcionalidade3.distanciasEuclidianasNovaParaImagens(omegaNova, omega);
            output = arredondarMatriz(output);
            comparacao(exemplo, outputEsperado, output, "Distâncias Euclidianas Nova para Imagens");
        }
    }

    public static void testeCriacaoDeImagens() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][] vetorMedia = null;
            double[][] u = null;
            int k = 0;
            double[][] matrizNumerosAleatorios = null;
            double[][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    vetorMedia = new double[][]{{10}, {20}, {30}};
                    u = new double[][]{
                            {1, 0},
                            {0, 1},
                            {1, 1}
                    };
                    k = 2;
                    matrizNumerosAleatorios = new double[][]{
                            {5},
                            {10}
                    };
                    outputEsperado = new double[][]{
                            {15},
                            {30},
                            {45}
                    };
                }
                case 1 -> {
                    vetorMedia = new double[][]{{0}, {0}, {0}};
                    u = new double[][]{
                            {2, 1},
                            {3, 2},
                            {4, 3}
                    };
                    k = 1;
                    matrizNumerosAleatorios = new double[][]{
                            {1}
                    };
                    outputEsperado = new double[][]{
                            {2},
                            {3},
                            {4}
                    };
                }
                case 2 -> {
                    vetorMedia = new double[][]{{5}, {10}};
                    u = new double[][]{
                            {0.5, 1},
                            {1.5, 2}
                    };
                    k = 1;
                    matrizNumerosAleatorios = new double[][]{
                            {2}
                    };
                    outputEsperado = new double[][]{
                            {6},
                            {13}
                    };
                }
            }
            double[][] output = Funcionalidade4.criacaoDeImagens(vetorMedia, u, k, matrizNumerosAleatorios);
            comparacao(exemplo, outputEsperado, output, "Criação de Imagens");
        }
    }

    public static void testeNormalizarDados() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][][] dados = null;
            int n = 12;
            double[][][] outputEsperado = null;

            switch (exemplo) {
                case 0 -> {
                    dados = new double[][][]{
                            {{0, 100}, {50, 200}},
                            {{25, 150}, {75, 255}}
                    };
                    n = 1;
                    outputEsperado = new double[][][]{
                            {{0, 0}, {50, 164.52}},
                            {{25, 82.26}, {75, 255}}
                    };
                }
                case 1 -> {
                    dados = new double[][][]{
                            {{10, 20}, {30, 40}},
                            {{50, 60}, {70, 80}}
                    };
                    n = 0;
                    outputEsperado = new double[][][]{
                            {{0, 20}, {85, 40}},
                            {{170, 60}, {255, 80}}
                    };
                }
                case 2 -> {
                    dados = new double[][][]{
                            {{5, 10}, {15, 20}},
                            {{25, 30}, {35, 40}}
                    };
                    n = 1;
                    outputEsperado = new double[][][]{
                            {{5, 0}, {15, 85}},
                            {{25, 170}, {35, 255}}
                    };
                }
            }
            double[][][] output = Funcionalidade4.normalizarDados(dados, n);
            for (int i = 0; i < output.length; i++) {
                output[i] = arredondarMatriz(output[i]);
                comparacao(exemplo, outputEsperado[i], output[i], "Normalizar Dados");
            }
        }
    }

    public static void testeValorMinimo() {
        for (int exemplo = 0; exemplo < 3; exemplo++) {
            double[][][] matriz = null;
            int n = 12;
            double outputEsperado = 0.0;

            switch (exemplo) {
                case 0 -> {
                    matriz = new double[][][]{
                            {{10, 20}, {30, 40}},
                            {{50, 60}, {70, 80}}
                    };
                    n = 0;
                    outputEsperado = 10.0;
                }
                case 1 -> {
                    matriz = new double[][][]{
                            {{5, 1}, {15, 2}},
                            {{25, 3}, {35, 4}}
                    };
                    n = 1;
                    outputEsperado = 1.0;
                }
                case 2 -> {
                    matriz = new double[][][]{
                            {{5, 10}, {15, 20}},
                            {{25, 30}, {35, 40}}
                    };
                    n = 0;
                    outputEsperado = 5.0;
                }
            }
            double output = Funcionalidade4.valorMinimo(matriz, n);
            comparacao(exemplo, new double[][]{{outputEsperado}}, new double[][]{{output}}, "Valor Mínimo");
        }
    }
}
