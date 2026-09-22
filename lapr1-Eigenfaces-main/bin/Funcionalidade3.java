package bin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Funcionalidade3 {
    //executa o processo de inserção de dados conforme a escolha do utilizador para então começar a funcionalidade
    public static void escolhaFun3() throws IOException {
        int caminho = Menu.escolhaDoCaminho();
        if (caminho == 1) {
            fun3(Menu.CSV_DATABASE, Menu.CSV_TO_SEARCH);
        } else {
            System.out.print("Introduza o caminho para a matriz CSV: ");
            String caminhoFicheiro = Menu.ler.nextLine();
            System.out.print("Introduza o caminho para a Base de Dados: ");
            String caminhoBaseDeDados = Menu.ler.nextLine();
            fun3(caminhoBaseDeDados, caminhoFicheiro);
        }
    }

    //recebe como parâmetro o caminho para o ficheiro com o csv e o nome da imagem
    //pede para o utilizador um valor de eigenfaces para reconstruir a imagem e depois chama o método dos cálculos da funcionalidade. No final, volta a chamar o Menu
    public static void fun3(String caminho, String imagem) throws IOException {
        double[][] matrizM = Funcionalidade2.lerCsvPeloFicheiro(caminho);
        System.out.print("Nùmero de eigenfaces a utilizar na reconstrução (-1 usa o número de eigenfaces máximo): ");
        int k = Funcionalidade1.lerParametro(matrizM[0].length, 1, "Eigenfaces");
        System.out.println();
        calculosFuncionalidade3(matrizM, k, imagem, 1);
        bin.Menu.MENU();
    }

    //tem como parâmetros o caminho para a base de imagens, o número de eigenfaces e o nome da imagem, argumentos dados no cmd
    //executa a funcionalidade 3 no modo não-interativo
    public static void fun3Auto(String caminhoBase, String numeroEigenfaces, String imagem) throws IOException {
        double[][] matrizM = Funcionalidade2.lerCsvPeloFicheiro(caminhoBase);
        System.out.println();
        int k = Integer.parseInt(numeroEigenfaces);
        if (k == -1) {
            k = matrizM[0].length;
        }
        calculosFuncionalidade3(matrizM, k, imagem, 2);
    }

    //leva como parâmetros a matriz das imagens (matriz M), o nº de eigenfaces, o nome da imagem e um inteiro para a flag
    //vai chamar todos os métodos que realizam cálculos para a funcionalidade. No final, chama o método para guardar a imagem mais próxima
    public static void calculosFuncionalidade3(double[][] matrizM, int eigenfaces, String imagem, int flag) throws IOException {
        double[][] matrizNova = Funcionalidade1.csvParaMatriz(imagem);
        double[][] vetorNova = Funcionalidade2.transformarEmVetor(matrizNova);
        double[][] vetorMedia = Funcionalidade2.calcVetorMedio(matrizM);
        double[][] fiNova = Funcionalidade2.calculoMatrizA(vetorNova, vetorMedia);
        double[][] fi = Funcionalidade2.calculoMatrizA(matrizM, vetorMedia);
        double[][] matrizU = Funcionalidade2.calcularU(fi);
        double[][] omegaNova = Funcionalidade2.calcularMatrizPeso(matrizU, fiNova, eigenfaces);
        double[][] omega = Funcionalidade2.calcularMatrizPeso(matrizU, fi, eigenfaces);
        double[][] distancias = distanciasEuclidianasNovaParaImagens(omegaNova, omega);
        double[][] imagemMaisProxima = identificarImagem(matrizM, distanciasEuclidianasNovaParaImagens(omegaNova, omega));
        outputsFuncionalidade3Ficheiro(omegaNova, omega, distancias, eigenfaces, flag);
        if (flag == 1) {
            Funcionalidade2.guardarImagem(Funcionalidade2.vetorParaMatriz(imagemMaisProxima), 0, "Identificacao", "ModoI_Identificacao/","");
        } else {
            Funcionalidade2.guardarImagem(Funcionalidade2.vetorParaMatriz(imagemMaisProxima), 0, "Identificacao", "ModoNI_OutputImagensNova/","Identificacao/");
        }
    }

    //toma como parâmetros os arrays correspondentes ao vetor com os pesos das K eigenfaces sobre a imagem Nova, a matriz com os pesos das K eigenfaces sobre cada imagem da base e um inteiro
    //vai calcular a distância euclidiana entre o vetor omega Nova e o vetor da coluna correspondente ao número inteiro na matriz Omega
    //retorna essa distância
    public static double distanciaEuclidiana(double[][] omegaNova, double[][] omega, int img) {
        double soma = 0.0;
        for (int i = 0; i < omegaNova.length; i++) {
            soma += Math.pow((omegaNova[i][0] - omega[i][img]), 2);
        }
        return Math.sqrt(soma);
    }

    //leva como parâmetros a matriz das imagens (matriz M) e a matriz com todas as distâncias euclidianas do vetor omegaNova para o vetor omegaImagemI
    //vê qual das distâncias é a menor, ou seja, qual a imagem da base mais próxima da imagem dada
    //retorna o vetor correspondente a imagem mais próxima
    public static double[][] identificarImagem(double[][] matrizImagens, double[][] distanciasNovaParaImagens) {
        double menorDistancia = Double.POSITIVE_INFINITY;
        int imagemMaisProxima = 0;
        for (int i = 0; i < distanciasNovaParaImagens.length; i++) {
            if (distanciasNovaParaImagens[i][0] < menorDistancia) {
                imagemMaisProxima = i;
                menorDistancia = distanciasNovaParaImagens[i][0];
            }
        }
        return Funcionalidade2.pegarVetor(matrizImagens, imagemMaisProxima);
    }

    //tem como parâmetros a matriz com os pesos das k eigenfaces para a imagem Nova e a matriz com os pesos das k eigenfaces para cada imagem da base
    //vai guardar todas as distâncias euclidianas entre o vetor omegaNova e o vetor omegaI em um novo vetor
    //retorna esse vetor
    public static double[][] distanciasEuclidianasNovaParaImagens(double[][] omegaNova, double[][] omega) {
        double[][] distanciasNovaParaImagens = new double[omega[0].length][1];
        double distanciaNovaParaImagem = 0.0;
        for (int i = 0; i < omega[0].length; i++) {
            distanciaNovaParaImagem = distanciaEuclidiana(omegaNova, omega, i);
            distanciasNovaParaImagens[i][0] = distanciaNovaParaImagem;
        }
        return distanciasNovaParaImagens;
    }

    //recebe como parâmetros o array da matriz dos pesos das k eigenfaces para a imagem nova, o array do mesmo tipo mas para cada imagem da base, o array que contém todas as distâncias euclidianas entre o vetor omega Nova e os vetores omega de cada imagem, o número de eigenfaces utilizado e por fim um número para a flag
    //cria todo o output da funcionalidade
    public static void outputsFuncionalidade3Ficheiro(double[][] omeganova, double[][] pesoomegai, double[][] distancia, int E, int flag) throws IOException {
        if (flag == 1) {
            String outputFolder = "ModoI_OutputImagensNova/";

            System.out.println("========== Resultados ==========");

            System.out.println("Matriz omega nova:");
            Menu.outputMatriz(omeganova, 0);


            System.out.println("Vetores pesos omega i:");
            Menu.outputMatriz(pesoomegai, 0);

            System.out.println("Distâncias euclidianas:");
            Menu.outputMatriz(distancia, 0);

            System.out.println("\neignfaces utilizados:" + E);
            System.out.println("\nOs resultados foram salvos na pasta 'ModoI_OutputImagensNova/'.");
            System.out.println();
        } else {
            String outputFolder = "ModoNI_OutputImagensNova/";
            File directory = new File(outputFolder);

            if (!directory.exists() && !directory.mkdirs()) {
                System.err.println("Erro ao criar o diretório: " + outputFolder);
                return;
            }

            try (PrintWriter pr = new PrintWriter(new FileWriter(outputFolder + "resultados.txt"))) {
                pr.println("eigenfaces utilizados:" + E);

                Menu.imprimirFicheiro(omeganova, outputFolder + "Matriz_omega_nova.csv", 0);
                Menu.imprimirFicheiro(pesoomegai, outputFolder + "Vetores_pesos_omega_i.csv", 0);
                Menu.imprimirFicheiro(distancia, outputFolder + "Distâncias_euclidianas.csv", 0);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\nOs resultados foram salvos na pasta 'ModoNI_OutputImagensNova/'.");
            System.out.println();
        }
    }
}
