package bin;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;
import java.io.IOException;

public class Funcionalidade4 {
    //executa o processo de inserção de dados conforme a escolha do utilizador para então começar a funcionalidade
    public static void escolhaFun4() throws IOException {
        int caminho = bin.Menu.escolhaDoCaminho();
        if (caminho == 1) {
            fun4(bin.Menu.CSV_DATABASE);
        } else {
            System.out.print("Introduza o caminho para a matriz CSV: ");
            String caminhoFicheiro = bin.Menu.ler.nextLine();
            fun4(caminhoFicheiro);
        }
    }

    //recebe como parâmetro o ficheiro para o csv
    //pede para o utilizador um valor de eigenfaces para reconstruir a imagem e depois chama o método dos cálculos da funcionalidade. No final, volta a chamar o Menu
    public static void fun4(String caminho) throws IOException {
        double[][] matrizM = Funcionalidade2.lerCsvPeloFicheiro(caminho);
        System.out.print("Nùmero de eigenfaces a utilizar na reconstrução (-1 usa o número de eigenfaces máximo): ");
        int k = Funcionalidade1.lerParametro(matrizM[0].length, 1, "Eigenfaces");
        System.out.println();
        calculosFuncionalidade4(matrizM, k);
        bin.Menu.MENU();
    }

    //recebe como parâmetros o caminho para a base de imagens e o número de eigenfaces, argumentos dados no cmd
    //executa a funcionalidade 4 no modo não-interativo
    public static void fun4Auto(String caminhoBase, String numeroEigenfaces) throws IOException {
        double[][] matrizM = Funcionalidade2.lerCsvPeloFicheiro(caminhoBase);
        int k = Integer.parseInt(numeroEigenfaces);
        if (k == -1) {
            k = matrizM[0].length;
        }
        calculosFuncionalidade4(matrizM, k);
    }

    //toma como parâmetros o array contendo todos os vetores das imagens da base e o número de eigenfaces dado pelo utilizador
    //vai chamar os métodos que fazem cálculos para a funcionalidade
    public static void calculosFuncionalidade4(double[][] matrizM, int k) throws IOException {
        double[][] vetorMedia = Funcionalidade2.calcVetorMedio(matrizM);
        double[][] matrizA = Funcionalidade2.calculoMatrizA(matrizM, vetorMedia);
        double[][] matrizU = Funcionalidade2.calcularU(matrizA);
        double[][] lambda = Funcionalidade1.decomposicao(Funcionalidade1.multiplicadorDeMatrizes(Funcionalidade1.transposta(matrizA), matrizA))[1];
        double[][] matrizNumerosAleatorios = gerarNumerosAleatorios(lambda,k);
        double[][] imagensCriadas = criacaoDeImagens(vetorMedia, matrizU, k, matrizNumerosAleatorios);
        double[][][] matrizesImagens = Funcionalidade2.vetorParaMatriz(imagensCriadas);
        double[][][] imagemNormalizada = normalizarDados(matrizesImagens,0);
        outputsFuncionalidade4Ficheiro(imagemNormalizada,matrizesImagens);
    }

    //tem como parâmetros o array para o vetor lambda e um valor inteiro
    //vai gerar um novo array para um vetor com números aleatórios, calculados com base nas raizes quadradas (limites máximos) e raizes quadradas com sinal negativo (limites mínimos) de entradas do vetor lambda
    //retorna esse array com valores aleatórios
    public static double[][] gerarNumerosAleatorios(double[][] lambda,int k) {
        Random random = new Random();
        double[][] matrizNumerosAleatorios = new double[k][1];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < lambda[0].length; j++) {
                if (lambda[i][j] != 0) {
                    int limiteInferior = (int) (-Math.sqrt(lambda[j][i]));
                    int limiteSuperior = (int) Math.sqrt(lambda[j][i]);
                    matrizNumerosAleatorios[i][0] = random.nextInt((limiteSuperior - limiteInferior) + 1) + limiteInferior;
                }
            }
        }
        return matrizNumerosAleatorios;
    }

    //tem como parâmetros o array do vetor média, o array da matriz das eigenfaces, o número de eigenfaces dado pelo utilizador e a matriz com valores aleatórios
    //vai construir um vetor novo resultante da soma entre o vetor média e o somatório dos produtos entre as eigenfaces com os valores aleatórios do vetor matrizNumerosAleatorios
    //vai retornar esse vetor novo
    public static double[][] criacaoDeImagens(double[][] vetorMedia, double[][] u, int k, double[][] matrizNumerosAleatorios) {
        double[][] iCriado = new double[u.length][1];
        double[][] somatorio = new double[vetorMedia.length][1];
        for (int kEigenfaces = 0; kEigenfaces < k ; kEigenfaces++) {
            double[][] uN = Funcionalidade2.pegarVetor(u,kEigenfaces);
            double[][] uVezesW = Funcionalidade2.multiplicadorDeMatrizComEscalar(uN, matrizNumerosAleatorios[kEigenfaces][0]);
            somatorio = Funcionalidade2.somaDeMatrizes(somatorio, uVezesW);
        }
        double[][] imagemNRecontruida = Funcionalidade2.somaDeMatrizes(vetorMedia, somatorio);
        for (int i = 0; i < u.length; i++) {
            iCriado[i][0] = imagemNRecontruida[i][0];
        }
        return iCriado;
    }

    //tem como parâmetros a matriz tridimensional que possui a imagem nova criada e o número para a dimensão Z dessa matriz, que sempre será 0 por ela possuir somente um nível
    //vai colocar os valores da matriz tridimensional (imagem nova) dentro da escala de 0 a 255
    //retorna a matriz tridimensional normalizada
    public static double[][][] normalizarDados(double[][][] dados, int n) {
        double valorMax = Funcionalidade2.valorMaximo(dados, n);
        double valorMin = valorMinimo(dados, n);

        for (int x = 0; x < dados.length; x++) {
            for (int y = 0; y < dados[x].length; y++) {
                dados[x][y][n] = 255 * (dados[x][y][n] - valorMin) / (valorMax - valorMin);
            }
        }
        return dados;
    }

    //recebe como parâmetros o array tridimensional que possui a imagem nova criada e um valor inteiro
    //vai buscar a entrada que contenha o valor mínimo dentro do array 3D
    //retorna esse valor mínimo
    public static double valorMinimo(double[][][] matriz, int n) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j][n] < min) {
                    min = matriz[i][j][n];
                }
            }
        }
        return min;
    }

    //tem como parâmetros os arrays tridimensionais correspondentes as matrizes: da imagem nova normalizada e da imagem nova antes da normalização
    //cria todo o output para a funcionalidade
    public static void outputsFuncionalidade4Ficheiro(double[][][] imagemNormalizada,double[][][] matrizesImagens) throws IOException {
        String outputFolder = "ModoI_ModoNI_OutputImagemCriada/";

        // Criação da pasta (manualmente no sistema de arquivos)
        new File(outputFolder).mkdirs();

        double[][] matrizI = new double[imagemNormalizada.length][imagemNormalizada[0].length];
        double[][] matrizTransformada = new double[imagemNormalizada.length][imagemNormalizada[0].length];
        for (int i = 0; i < matrizesImagens.length; i++) {
            for (int j = 0; j < matrizesImagens[i].length; j++) {
                matrizI[j][i] = matrizesImagens[i][j][0];
                matrizTransformada[j][i] = imagemNormalizada[i][j][0];
            }
        }

        Funcionalidade2.guardarImagem(imagemNormalizada, 0,"ImagemCriada","ModoI_ModoNI_OutputImagemCriada/","");
        Menu.imprimirFicheiro(matrizI, outputFolder + "matriz_original.csv",0);
        Menu.imprimirFicheiro(matrizTransformada, outputFolder + "matriz_original_transformada.csv",0);
        System.out.println("\nOs resultados foram salvos na pasta 'ModoI_ModoNI_OutputImagemCriada/'.");
        System.out.println();
    }
}
