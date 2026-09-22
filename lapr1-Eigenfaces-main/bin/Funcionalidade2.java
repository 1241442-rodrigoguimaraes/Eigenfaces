package bin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Funcionalidade2 {
    //executa o processo de inserção de dados conforme a escolha do utilizador para então começar a funcionalidade
    public static void escolhaFun2() throws IOException {
        int caminho = bin.Menu.escolhaDoCaminho();
        if (caminho == 1) {
            fun2(bin.Menu.CSV_DATABASE);
        } else {
            System.out.print("Introduza o caminho para a matriz CSV: ");
            String caminhoFicheiro = bin.Menu.ler.nextLine();
            fun2(caminhoFicheiro);
        }
    }

    //recebe como parâmetro o ficheiro para o csv
    //pede para o utilizador um valor de eigenfaces para reconstruir a imagem e depois chama o método dos cálculos da funcionalidade
    public static void fun2(String caminho) throws IOException {
        double[][] matrizM = lerCsvPeloFicheiro(caminho);
        System.out.print("Nùmero de eigenfaces a utilizar na reconstrução (-1 usa o número de eigenfaces máximo): ");
        int k = Funcionalidade1.lerParametro(matrizM[0].length, 1, "Eigenfaces");
        System.out.println();
        calculosFuncionalidade2(matrizM, k, 1);
        bin.Menu.MENU();
    }

    //recebe como parâmetros o caminho para a base de imagens e o número de eigenfaces, argumentos dados no cmd
    //executa a funcionalidade 2 no modo não-interativo
    public static void fun2Auto(String caminhoBase, String numeroEigenfaces) throws IOException {
        double[][] matrizM = lerCsvPeloFicheiro(caminhoBase);
        System.out.println();
        int k = Integer.parseInt(numeroEigenfaces);
        if (k == -1) {
            k = matrizM[0].length;
        }
        calculosFuncionalidade2(matrizM, k, 2);
    }

    //recebe como parâmetros o array/matriz que possui as imagens da base como colunas/vetores, o número de eigenfaces e um inteiro para a flag
    //chama todos os métodos de cálculos para a funcionalidade 2 e chama o método dos outputs
    public static void calculosFuncionalidade2(double[][] matrizM, int k, int flag) throws IOException {
        double[][] vetorMedia = calcVetorMedio(matrizM);
        double[][] matrizA = calculoMatrizA(matrizM, vetorMedia);
        double[][] matrizU = calcularU(matrizA);
        double[][] matrixPesos = calcularMatrizPeso(matrizU, matrizA, k);
        double[][] iReconstuida = reconstruirImagem(vetorMedia, matrizU, k, matrixPesos);
        double[][][] matriz = vetorParaMatriz(iReconstuida);
        for (int n = 0; n < matriz[0][0].length; n++) {
            if (flag == 1) {
                salvarMatrizesComoCSV(matriz, "ModoI_OutputImagens", 0);
                guardarImagem(matriz, n, "ImagemReconstruida", "ModoI_OutputImagens/", "ImagensReconstruidas/");
            } else {
                salvarMatrizesComoCSV(matriz, "ModoNI_OutputImagens", 0);
                guardarImagem(matriz, n, "ImagemReconstruida", "ModoNI_OutputImagens/", "ImagensReconstruidas/");
            }
        }
        outputsFuncionalidade2Ficheiro(vetorMedia, iReconstuida, k, matrizA, matrixPesos, flag);
    }

    //recebe como parâmetro um array qualquer
    //transforma o array em outro array mas com uma coluna só, isto é, um vetor, 'empilhando' as colunas do array uma abaixo da outra
    //retorna o vetor
    public static double[][] transformarEmVetor(double[][] arr) {
        double[][] imagemEmVetor = new double[arr.length * arr[0].length][1];
        int index = 0;

        // Alterar para iterar primeiro pelas colunas
        for (int j = 0; j < arr[0].length; j++) {
            for (int i = 0; i < arr.length; i++) {
                imagemEmVetor[index][0] = arr[i][j];
                index++;
            }
        }
        return imagemEmVetor;
    }

    //recebe como parâmetro o ficheiro com o csvs
    //verifica a existência de csvs no ficheiro e vai criar um array correspondente a 'matrizM'
    //retorna o array 'matrizM'
    public static double[][] lerCsvPeloFicheiro(final String folderPath) throws IOException {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            System.out.println("A pasta especificada não existe ou é inválida: " + folderPath);
            return new double[0][0]; // Retorna uma matriz vazia se a pasta não for válida
        } else {
            File[] files = folder.listFiles();
            // Verifique se a pasta está vazia
            if (files == null || files.length == 0) {
                System.out.println("A pasta está vazia ou não contém nenhum arquivo CSV.");
                return new double[0][0]; // Retorna uma matriz vazia se não houver arquivos
            } else {
                // Inicializar a matrizM, que será retornada
                int numLinhas = 0;
                int numColunas = 0;
                List<double[][]> csvFiles = new ArrayList<>();
                for (File file : files) {
                    if (file.getName().endsWith(".csv")) { // Verifica se o arquivo é um CSV
                        double[][] minimatriz = Funcionalidade1.csvParaMatriz(file.getAbsolutePath());
                        double[][] vetor = transformarEmVetor(minimatriz);
                        numLinhas = Math.max(numLinhas, minimatriz.length);
                        csvFiles.add(vetor);
                        numColunas++;
                    }
                }
                int numLinhasVetor = (int) Math.pow(numLinhas, 2);
                double[][] matrizM = new double[numLinhasVetor][numColunas];
                for (int i = 0; i < csvFiles.size(); i++) {
                    double[][] vetor = csvFiles.get(i);
                    for (int j = 0; j < vetor.length; j++) {
                        matrizM[j][i] = vetor[j][0];
                    }
                }
                return matrizM;
            }
        }
    }

    //recebe como parâmetros um array qualquer e um valor decimal qualquer
    //seguindo a lógica de multiplicação de matrizes com um escalar, vai multiplicar os valores do array pelo valor decimal dado
    //retorna a array multiplicada
    public static double[][] multiplicadorDeMatrizComEscalar(double[][] arr, double esc) {
        double[][] arrMultiplicada = new double[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arrMultiplicada[i][j] = arr[i][j] * esc;
            }
        }
        return arrMultiplicada;
    }

    //recebe como parâmetro um array
    //vai normalizar, seguindo a lógica de normalização de vetores, as colunas do array dado
    //retorna o array com as suas colunas normalizadas
    public static double[][] normalizarVetores(double[][] matriz) {
        double[][] matrizNormalizada = new double[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz[0].length; i++) {
            double soma = 0;
            double[][] vetor = new double[matriz.length][1];
            for (int j = 0; j < matriz.length; j++) {
                soma += Math.pow(matriz[j][i], 2);
                vetor[j][0] = matriz[j][i];
            }
            double magnitude = (1 / Math.sqrt(soma));
            double[][] vetornormalizado = multiplicadorDeMatrizComEscalar(vetor, magnitude);
            for (int j = 0; j < matriz.length; j++) {
                matrizNormalizada[j][i] = vetornormalizado[j][0];
            }
        }
        return matrizNormalizada;
    }

    //recebe como parâmetro um array
    //vai buscar as colunas (vetores correspondentes cada uma a uma imagem) do array e calcular em cima delas um array-coluna que possua como valores as médias dessas colunas
    public static double[][] calcVetorMedio(double[][] matrizM) {
        double[][] medio = new double[matrizM.length][1];
        for (int i = 0; i < matrizM.length; i++) {
            double soma = 0;
            for (int j = 0; j < matrizM[0].length; j++) {
                soma += matrizM[i][j];
            }
            medio[i][0] = soma / matrizM[0].length;
        }
        return medio;
    }

    //recebe como parâmetros um array para a matriz M e outro para o vetor média
    //vai calcular um novo array que resultará da subtração (seguindo a lógica de subtração de matrizes) entre as colunas da 'matriz M' e do 'vetor média'
    //retorna essa novo array
    public static double[][] calculoMatrizA(double[][] matrizM, double[][] vetormedia) {
        double[][] matrizA = new double[matrizM.length][matrizM[0].length];
        // Subtrai a média (vetormedia) de cada coluna da matrizM
        for (int i = 0; i < matrizM[0].length; i++) { // Percorre as colunas
            for (int j = 0; j < matrizM.length; j++) { // Percorre as linhas
                matrizA[j][i] = matrizM[j][i] - vetormedia[j][0];
            }
        }
        return matrizA;
    }

    //recebe como parâmetros dois arrays
    //seguindo a lógica de soma de matrizes, vai somar os valores dos dois arrays
    //retorna o array somado
    public static double[][] somaDeMatrizes(double[][] matriz1, double[][] matriz2) {
        double[][] matrizSoma = new double[matriz1.length][matriz1[0].length];
        for (int i = 0; i < matriz1.length; i++) {
            for (int j = 0; j < matriz1[0].length; j++) {
                matrizSoma[i][j] = matriz1[i][j] + matriz2[i][j];
            }
        }
        return matrizSoma;
    }

    //recebe como parâmetros um array e um inteiro
    //com base no inteiro, que vai indicar o número da coluna, vai criar um array-coluna/vetor com os valores da coluna especificada
    //retorna esse array-vetor
    public static double[][] pegarVetor(double[][] matriz, int vetorN) {
        double[][] vetor = new double[matriz.length][1];
        for (int i = 0; i < matriz.length; i++) {
            vetor[i][0] = matriz[i][vetorN];
        }
        return vetor;
    }

    //tem como parâmetro um array correspondente a matriz A
    //vai calcular as eigenfaces de cada coluna (correspondente a uma imagem) da array-matriz A e criar um array com elas
    //retorna esse array
    public static double[][] calcularU(double[][] matrizA) {

        double[][][] decomposicaoAtA = Funcionalidade1.decomposicao(Funcionalidade1.multiplicadorDeMatrizes(Funcionalidade1.transposta(matrizA), matrizA));
        double[][] autovetores = decomposicaoAtA[0];


        return normalizarVetores(Funcionalidade1.multiplicadorDeMatrizes(matrizA, autovetores));
    }

    //tem como parâmetros os arrays correspondentes ao array das eigenfaces (matriz U), ao array da matriz A e também um inteiro
    //vai construir uma matriz com os pesos das k eigenfaces principais para cada vetor do array correspondente a matriz A
    //retorna essa matriz
    public static double[][] calcularMatrizPeso(double[][] matrizU, double[][] matrizA, int k) {
        double[][] matrizPeso = new double[k][matrizA[0].length];
        for (int imagemN = 0; imagemN < matrizA[0].length; imagemN++) {
            double[][] fiJ = Funcionalidade2.pegarVetor(matrizA, imagemN);
            for (int j = 0; j < k; j++) {
                double[][] uJ = Funcionalidade2.pegarVetor(matrizU, j);
                double pesoImagem = Funcionalidade1.multiplicadorDeMatrizes(Funcionalidade1.transposta(uJ), fiJ)[0][0];
                matrizPeso[j][imagemN] = pesoImagem;
            }
        }
        return matrizPeso;
    }

    //tem como parâmetros os arrays correspondentes ao vetor média, a matriz das eigenfaces e a matriz peso, assim como um inteiro
    //vai somar o array correspondente ao vetor média com o somatório dos pesos das k eigenfaces vezes as eigenface em si para cada imagem da base
    //retorna o array correspondente as imagens reconstruídas
    public static double[][] reconstruirImagem(double[][] vetorMedia, double[][] u, int k, double[][] matrizPeso) {
        double[][] iReconstruido = new double[u.length][u[0].length];
        for (int imangemN = 0; imangemN < u[0].length; imangemN++) {
            double[][] somatorio = new double[vetorMedia.length][1];
            for (int kEigenfaces = 0; kEigenfaces < k; kEigenfaces++) {
                double[][] uN = pegarVetor(u, kEigenfaces);
                double pesoImagem = matrizPeso[kEigenfaces][imangemN];
                double[][] uVezesW = multiplicadorDeMatrizComEscalar(uN, pesoImagem);
                somatorio = somaDeMatrizes(somatorio, uVezesW);
            }
            double[][] imagemNRecontruida = somaDeMatrizes(vetorMedia, somatorio);
            for (int i = 0; i < u.length; i++) {
                iReconstruido[i][imangemN] = imagemNRecontruida[i][0];
            }
        }
        return iReconstruido;
    }

    //toma como parâmetro um array em forma de vetor
    //vai transformar o vetor em uma matriz tridimensional
    //retorna a matriz tridimensional
    public static double[][][] vetorParaMatriz(double[][] arrVetores) {
        double[][][] matriz = new double[(int) Math.sqrt(arrVetores.length)][(int) Math.sqrt(arrVetores.length)][arrVetores[0].length];
        for (int l = 0; l < arrVetores[0].length; l++) {
            double[][] vetor = pegarVetor(arrVetores, l);
            int k = 0;
            for (int i = 0; i < (int) Math.sqrt(arrVetores.length); i++) {
                for (int j = 0; j < (int) Math.sqrt(arrVetores.length); j++) {
                    matriz[i][j][l] = vetor[k][0];
                    k++;
                }
            }
        }
        return matriz;
    }

    //toma como parâmetro um array tridimensional, uma string para o nome do ficheiro e um inteiro para o nº de casas decimais
    //vai imprimir o array/matriz em forma de csv ao ficheiro
    public static void salvarMatrizesComoCSV(double[][][] matriz, String pasta, int casasDecimais) {
        String folderPath = pasta + "/eigenfaces/";
        File folder = new File(folderPath);

        // Criar a pasta caso não exista
        if (!folder.exists() && !folder.mkdirs()) {
            System.err.println("Erro ao criar o diretório: " + folderPath);
            return;
        }

        try {
            for (int n = 0; n < matriz[0][0].length; n++) {
                String filePath = folderPath + "eigenface_" + n + ".csv";

                try (PrintWriter writer = new PrintWriter(filePath)) {
                    for (int i = 0; i < matriz.length; i++) {
                        for (int j = 0; j < matriz[i].length; j++) {
                            writer.printf("%15." + casasDecimais + "f", matriz[i][j][n]);
                            if (j < matriz[i].length - 1) {
                                writer.print(",");
                            }
                        }
                        writer.println(); // Nova linha após cada linha da matriz
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar os arquivos CSV: " + e.getMessage());
        }
    }

    //toma como parâmetros um array tridimensional que vai possuir todos os arrays correspondentes as imagens da base, um valor inteiro e duas string correspondentes ao nome da imagem e do ficheiro
    //vai guardar as imagens reconstruídas em um ficheiro
    public static void guardarImagem(double[][][] dados, int n, String nome, String pasta, String subPasta) {
        String folderPath = pasta + subPasta;
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String imagemPath = folderPath + nome + n + ".jpg";
        try {
            BufferedImage imagem = criarImagem(dados, n);
            ImageIO.write(imagem, "jpg", new File(imagemPath));
        } catch (IOException e) {
            System.err.println("Erro ao criar a imagem: " + e.getMessage());
        }
    }

    //toma como parâmetros um array tridimensional que vai possuir todos os arrays correspondentes as imagens da base e um valor inteiro
    //vai criar as imagens reconstruídas em formato JPG
    public static BufferedImage criarImagem(double[][][] dados, int n) {
        BufferedImage imagem = new BufferedImage(dados.length, dados[0].length, BufferedImage.TYPE_INT_RGB);

        double valorMax = valorMaximo(dados, n);

        for (int y = 0; y < dados[0].length; y++) {
            for (int x = 0; x < dados.length; x++) {
                int intensidade = (int) ((dados[x][y][n] / valorMax) * 255);
                intensidade = Math.max(0, Math.min(255, intensidade));
                Color cor = new Color(intensidade, intensidade, intensidade);
                imagem.setRGB(x, y, cor.getRGB());
            }
        }

        return imagem;
    }

    //recebe como parâmetros o array tridimensional correspondente a matriz que contém os arrays bidimensionais correspondentes as imagens reconstruídas e um valor inteiro
    //vai buscar a entrada que contenha o valor máximo no array n bidimensional dentro do array 3D
    //retorna esse valor máximo
    public static double valorMaximo(double[][][] matriz, int n) {
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j][n] > max) {
                    max = matriz[i][j][n];
                }
            }
        }
        return max;
    }

    //recebe como parâmetros os arrays para o vetor média, a imagem reconstruída, a matriz A, a matriz dos pesos e dois inteiros: um para o número de eigenfaces usadas e outro para a flag
    //cria o output para a funcionalidade
    public static void outputsFuncionalidade2Ficheiro(double[][] vetorMedia, double[][] iReconstruida, int k, double[][] A, double[][] matrizPeso, int flag) throws IOException {
        if (flag == 1) {
            String outputFolder = "ModoI_OutputImagens/";

            // Criação da pasta (manualmente no sistema de arquivos)
            new File(outputFolder).mkdirs();

            System.out.println("========== Resultados ==========");
            System.out.println("Valor de K utilizado: " + k);
            System.out.println("\nVetor Média:");
            Menu.outputMatriz(vetorMedia, 0);

            System.out.println("\nMatriz C:");
            Menu.outputMatriz(Funcionalidade1.multiplicadorDeMatrizes(Funcionalidade1.transposta(A), A), 0);

            System.out.println("\nVetor peso (para cada imagem reconstruida):");
            Menu.outputMatriz(matrizPeso, 0);

            Menu.imprimirFicheiro(iReconstruida, outputFolder + "imagens_reconstruidas.csv", 0);

            System.out.println("\nOs resultados foram salvos na pasta 'ModoI_OutputImagens/'.");
            System.out.println();
        } else {
            String outputFolder = "ModoNI_OutputImagens/";
            File directory = new File(outputFolder);

            if (!directory.exists() && !directory.mkdirs()) {
                System.err.println("Erro ao criar o diretório: " + outputFolder);
                return;
            }

            try (PrintWriter pr = new PrintWriter(new FileWriter(outputFolder + "resultados.txt"))) {
                pr.println("K utilizado:" + k);

                Menu.imprimirFicheiro(vetorMedia, outputFolder + "Vetor_Média.csv", 0);
                Menu.imprimirFicheiro(Funcionalidade1.multiplicadorDeMatrizes(Funcionalidade1.transposta(A), A), outputFolder + "Matriz_C.csv", 0);
                Menu.imprimirFicheiro(matrizPeso, outputFolder + "Vetores_pesos_imagens.csv", 0);
                Menu.imprimirFicheiro(iReconstruida, outputFolder + "imagens_reconstruidas.csv", 0);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\nOs resultados foram salvos na pasta 'ModoNI_OutputImagens/'.");
            System.out.println();
        }
    }
}
