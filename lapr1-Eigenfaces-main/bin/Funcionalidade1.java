package bin;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Funcionalidade1 {
    //executa o processo de inserção de dados conforme a escolha do utilizador para então começar a funcionalidade
    public static void escolhaFun1() throws IOException {
        int caminho = Menu.escolhaDoCaminho();
        if (caminho == 1) {
            fun1(Menu.CSV_FILE);
        } else {
            System.out.print("Introduza o caminho para a matriz CSV: ");
            String caminhoFicheiro = Menu.ler.nextLine();
            fun1(caminhoFicheiro);
        }
    }

    //recebe como parâmetro o ficheiro com o csv
    //chama o método de conversão do csv para array 2D (matriz), chama o método que verifica a array (matriz), chama o método dos cálculos da funcionalidade 1 e volta a chamar o Menu
    public static void fun1(String caminho) throws IOException {
        double[][] matriz = csvParaMatriz(caminho);
        if (verificacao(matriz)) {
            System.out.print("\nNúmero de vetores próprios a utilizar (-1 usa o número de vetores máximo): ");
            int k = lerParametro(matriz.length, 1, "K");
            calculosFuncionalidade1(matriz, k, 1);
            Menu.MENU();
        }
    }

    //recebe como parâmetros o caminho para a base de imagens e o número de eigenfaces, argumentos dados no cmd
    //executa a funcionalidade 1 no modo não-interativo
    public static void fun1Auto(String caminhoBase, String numeroEigenfaces) throws IOException {
        double[][] matrix = csvParaMatriz(caminhoBase);
        if (verificacao(matrix)) {
            int k = Integer.parseInt(numeroEigenfaces);
            if (k == -1) {
                k = matrix[0].length;
            }
            calculosFuncionalidade1(matrix, k,2);
        }
    }

    //recebe como parâmetros a matriz a decompor e o número de eigenfaces
    //chama todos os métodos de cálculos para a funcionalidade 1 e chama o método dos outputs
    public static void calculosFuncionalidade1(double[][] matrix, int k, int flag) throws IOException {
        double[][] p = decomposicao(matrix)[0];
        double[][] d = decomposicao(matrix)[1];
        double[][] a = multiplicadorDeMatrizes(multiplicadorDeMatrizes(p, d), transposta(p));
        double[][] pOrganizado = organizar(d, p)[0];
        double[][] dOrganizado = organizar(d, p)[1];
        double[][] pK = reduzirMatriz(pOrganizado, dOrganizado, k)[0];
        double[][] dK = reduzirMatriz(pOrganizado, dOrganizado, k)[1];
        double[][] aK = multiplicadorDeMatrizes(multiplicadorDeMatrizes(pK, dK), transposta(pK));
        double eam = calcularEAM(a, aK);
        outputsFuncionalidade1(aK, eam, k, d, p, flag);
    }

    //recebe como parâmetro o ficheiro com a matriz inicial em csv
    //cria uma array 2D (matriz) com base no csv lido
    //retorna esse array
    public static double[][] csvParaMatriz(String origem) throws IOException {
        String csv = Files.readString(Paths.get(origem));
        String[] rows = csv.split("\n");

        // Cria uma matriz 2D a partir do CSV
        double[][] matrix = new double[rows.length][rows[0].split(",").length];
        for (int i = 0; i < rows.length; i++) {
            String[] cols = rows[i].split(",");
            for (int j = 0; j < rows[0].split(",").length; j++) {
                matrix[i][j] = Double.parseDouble(cols[j].trim());
            }
        }
        return matrix;
    }

    //recebe como parâmetro a array 2D (matriz)
    //verifica se a array possui dimensões inferiores ao limite (256), se ela é quadrada e se ela é simétrica
    //retorna falso se alguma das verificações não baterem ou retorna sim se todas baterem
    public static boolean verificacao(double[][] matrix) {
        if (matrix.length > Menu.MAX_SIZE_MATRIX) { // Verificação se a matriz possui dimensão superior ao limite
            return false;
        } else {
            if (matrix.length == matrix[0].length) {
                int n = matrix.length;  // Tamanho da matriz (n x n)
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (matrix[i][j] != matrix[j][i]) {
                            return false;
                        }
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

    //recebe como parâmetros o valor da dimensão do array 2D/matriz inicial (máximo), o valor de dimensão mínimo para um array (1) e o nome do parâmetro a ser lido
    //pede o valor do parâmetro ao utilizador e verifica se o valor inserido está entre o mínimo e o máximo
    //retorna o valor lido e verificado
    public static int lerParametro(int maximo, int minimo, String nomeParametro) {
        int valor = Menu.ler.nextInt();
        if (valor == -1){
            valor = maximo;
        }
        while (valor < minimo || valor > maximo) {
            System.out.println("O valor de " + nomeParametro + " tem de estar entre " + minimo + " e " + maximo + ", ou introduza -1 para ultilizar o máximo. Introduza novamente:");
            valor = Menu.ler.nextInt();
            if (valor == -1){
                valor = maximo;
            }
        }
        return valor;
    }

    //recebe como parâmetro a array 2D/matriz inicial
    //utilizando da biblioteca Commons Math, realiza a decomposição própria da matriz/array 2D, criando uma array 3D que conterá duas arrays 2D: autovetores e autovalores
    //retorna a array 3D com os outros dois arrays 2D
    public static double[][][] decomposicao(double[][] matrix) {
        Array2DRowRealMatrix realMatrix = new Array2DRowRealMatrix(matrix);
        EigenDecomposition eigenDecomposition = new EigenDecomposition(realMatrix); // calcula os autovalores e os autovetores

        double[][] autovalores = new double[matrix.length][matrix.length]; // obtém uma matriz diagonal
        for (int i = 0; i < matrix.length; i++) {
            autovalores[i][i] = eigenDecomposition.getRealEigenvalue(i);
        }
        double[][] autovetores = eigenDecomposition.getV().getData(); // obtém uma matriz
        return new double[][][]{autovetores, autovalores};
    }

    //recebe como parâmetro duas arrays 2D quaisquer
    //multiplica os valores de ambos os arrays seguindo a lógica da multiplicação de duas matrizes
    //retorna um array 2D, resultado das multiplicações entre os dois iniciais
    public static double[][] multiplicadorDeMatrizes(double[][] matriz1, double[][] matriz2) {
        double[][] matrizMultiplicada = new double[matriz1.length][matriz2[0].length];
        for (int i = 0; i < matriz1.length; i++) {
            for (int j = 0; j < matriz2[0].length; j++) {
                double soma = 0;
                for (int k = 0; k < matriz1[0].length; k++) {
                    soma += matriz1[i][k] * matriz2[k][j];
                }
                matrizMultiplicada[i][j] = soma;
            }
        }
        return matrizMultiplicada;
    }

    //recebe como parâmetro duas arrays 2D quaisquer.
    //a matriz diagonal vai ser organizada com os valores próprios em ordem descrescente e a matriz dos autovetores vai ser organizada com os autovetores de maiores valores próprios também em ordem descrescente
    //retorna um array 3D que vai possuir os dois arrays correspondentes respetivamente a matriz diagonal e a matriz dos autovetores
    public static double[][][] organizar(double[][] matriz1, double[][] matriz2) {
        int n = matriz1.length;

        // Passo 1: Extrair a diagonal principal (valores próprios)
        double[] valoresDiagonal = new double[n];
        for (int i = 0; i < n; i++) {
            valoresDiagonal[i] = matriz1[i][i];
        }
        for (int i = 1; i < n; i++) {
            if (valoresDiagonal[i] > valoresDiagonal[i - 1]) {
                double temp = valoresDiagonal[i];
                valoresDiagonal[i] = valoresDiagonal[i - 1];
                valoresDiagonal[i - 1] = temp;
                for (int j = 0; j < n; j++) {
                    double temp2 = matriz2[j][i];
                    matriz2[j][i] = matriz2[j][i - 1];
                    matriz2[j][i - 1] = temp2;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            matriz1[i][i] = valoresDiagonal[i];
        }

        return new double[][][]{matriz2, matriz1};
    }

    //recebe como parâmetro dois arrays organizados pelo método 'organizar' e um valor inteiro
    //trunca os dois arrays/matrizes de acordo com o valor inteiro dado
    //retorna os dois arrays/matrizes truncados através de um array tridimensional
    public static double[][][] reduzirMatriz(double[][] pOrganizada, double[][] dOrganizada, int k) {
        int n = pOrganizada.length;

        // Criar as matrizes truncadas
        double[][] pK = new double[pOrganizada.length][k];
        double[][] dK = new double[k][k];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                pK[i][j] = pOrganizada[i][j];
            }
        }
        for (int i = 0; i < k; i++) {
            dK[i][i] = dOrganizada[i][i];
        }

        // Retornar as matrizes truncadas
        return new double[][][]{pK, dK};
    }

    //recebe como parâmetro um array/matriz
    //cria um array 'transposta' com base na lógica de transposição de matrizes
    //retorna um array/matriz transposta
    public static double[][] transposta(double[][] matrizOriginal) {
        double[][] matrizTransposta = new double[matrizOriginal[0].length][matrizOriginal.length];
        for (int i = 0; i < matrizOriginal.length; i++) {
            for (int j = 0; j < matrizOriginal[0].length; j++) {
                matrizTransposta[j][i] = matrizOriginal[i][j];
            }
        }
        return matrizTransposta;
    }

    //leva como parâmetros os array bidimensionais correspondentes a matriz inicial e a matriz final obtida
    //calcula o erro absoluto médio envolvido na operação feita pela funcionalidade
    //retorna o valor do EAM
    public static double calcularEAM(double[][] matrizOriginal, double[][] matrizReconstruida) {
        double soma = 0;
        for (int i = 0; i < matrizOriginal.length; i++) {
            for (int j = 0; j < matrizOriginal[i].length; j++) {
                soma += Math.abs(matrizOriginal[i][j] - matrizReconstruida[i][j]);
            }
        }
        return soma / (matrizOriginal.length * matrizOriginal[0].length);
    }

    //recebe como parâmetros a array correspondente a matriz truncada, o valor do EAM, o parâmetro inserido pelo utilizador, um array correspondente a matriz diagonal obtida, um array correspondente a matriz dos autovetores e o número da flag
    //cria todo o output da funcionalidade
    public static void outputsFuncionalidade1(double[][] aK, double eam, int K, double[][] d, double[][] p, int flag) throws IOException {
        if (flag == 1) {
            String outputFolder = "ModoI_OutputMatrizes/";
            new File(outputFolder).mkdirs();
            System.out.println("========== Resultados ==========");
            System.out.printf("%s%.2f%n","Erro Absoluto Médio (EAM): " , eam);

            System.out.println("\nMatriz de valores proprios");
            Menu.outputMatriz(d,2);

            System.out.println("\nMatriz de vetores proprios");
            Menu.outputMatriz(p,2);

            System.out.println("\nK utilizado:" + K);

            System.out.println("\nMatriz Reconstruída:");
            Menu.outputMatriz(aK,2);


            Menu.imprimirFicheiro(aK, outputFolder + "matriz_reconstruida.csv",2);
            System.out.println("\nOs resultados foram salvos na pasta 'ModoI_OutputMatrizes/'.");
            System.out.println();

        } else {
            String outputFolder = "ModoNI_OutputMatrizes/";
            File directory = new File(outputFolder);

            if (!directory.exists() && !directory.mkdirs()) {
                System.err.println("Erro ao criar o diretório: " + outputFolder);
                return;
            }

            try (PrintWriter pr = new PrintWriter(new FileWriter(outputFolder + "resultados.txt"))) {
                pr.printf("%s%.2f","Erro Absoluto Médio (EAM): " , eam);
                pr.println("\nK utilizado: " + K);

                Menu.imprimirFicheiro(d, outputFolder + "matriz_de_valores_proprios.csv",2);
                Menu.imprimirFicheiro(p, outputFolder + "matriz_de_vetores_proprios.csv",2);
                Menu.imprimirFicheiro(aK, outputFolder + "matriz_reconstruida.csv",2);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\nOs resultados foram salvos na pasta 'ModoNI_OutputMatrizes/'.");
            System.out.println();
        }
    }
}
