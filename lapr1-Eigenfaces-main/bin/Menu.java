package bin;

import java.io.*;
import java.util.Scanner;

public class Menu {
    //constantes globais
    public static final String CSV_FILE = "Inputs/matrixTeste1.csv";
    public static final int n = 64;
    public static final String CSV_DATABASE = "Inputs/Exemplos" + n + "por" + n;
    public static final int MAX_SIZE_MATRIX = 256;
    public static final String CSV_TO_SEARCH = "Inputs/exemplosFun3/image_038.csv";
    //variáveis globais
    public static final Scanner ler = new Scanner(System.in);

    //Main da aplicação
    public static void main(String[] args) throws IOException {
        if (args.length != 0) {
            executarModoNaoInterativo(args);
        } else {
            MENU();
        }
    }

    //tem como parâmetros os argumentos 'args' dados na linha de comando do cmd
    //executa o modo não-interativo
    public static void executarModoNaoInterativo(String[] args) throws IOException {
        if (args.length < 5) {
            System.out.println("Erro ao executar Modo Não Interativo: Parâmetros Insuficientes.");
            System.out.println("Usar: java -jar part1.jar -f <funcionalidade> -k <numeroEigenfaces> -i <caminhoEntrada> -d <caminhoBase> <ficheiroSaida>");
            return;
        }
        //extrai os parâmetros
        String funcionalidade = "";
        String numeroEigenfaces = "";
        String caminhoEntrada = "";
        String caminhoBase = "";
        String ficheiroSaida = "";

        // Processa os argumentos
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-f")) {
                    funcionalidade = args[++i].replace("-f", "");
                } else if (args[i].equals("-k")) {
                    numeroEigenfaces = args[++i].replace("-k", "");
                } else if (args[i].equals("-i")) {
                    caminhoEntrada = args[++i].replace("-i", "");
                } else if (args[i].equals("-d")) {
                    caminhoBase = args[++i].replace("-d", "");
                } else {
                    ficheiroSaida = args[i]; // Assume que o último argumento é o ficheiro de saída
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Erro: Valor ausente para o parâmetro " + args[args.length - 1]);
            return;
        }


        //apresentação das escolhas feitas pelo utilizador
        System.out.println("Executando funcionalidade: " + funcionalidade);
        System.out.println("Número de eigenfaces: " + numeroEigenfaces);
        System.out.println("Caminho de entrada: " + caminhoEntrada);
        System.out.println("Caminho da base: " + caminhoBase);
        System.out.println("Ficheiro de saída: " + ficheiroSaida);

        if (funcionalidade.equals("1")) {
            Funcionalidade1.fun1Auto(caminhoBase, numeroEigenfaces);
        } else if (funcionalidade.equals("2")) {
            Funcionalidade2.fun2Auto(caminhoBase, numeroEigenfaces);
        } else if (funcionalidade.equals("3")) {
            Funcionalidade3.fun3Auto(CSV_DATABASE,numeroEigenfaces,caminhoBase);
        } else if (funcionalidade.equals("4")) {
            Funcionalidade4.fun4Auto(caminhoBase, numeroEigenfaces);
        } else {
            System.out.println("Opção inválida.");
        }
    }

    //executa o menu da aplicação para inicializá-la
    public static void MENU() throws IOException {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Funcionalidade 1: Redimensionar a imagem");
        System.out.println("2. Funcionalidade 2: Recontruir a imagem");
        System.out.println("3. Funcionalidade 3: Comparar imagens");
        System.out.println("4. Funcionalidade 4: Criar Imagem");
        System.out.println("5. Encerrar o programa.");
        System.out.print("Escolha uma opção: ");
        int escolha = ler.nextInt();
        while (escolha < 1 || escolha > 5) {
            System.out.println("Opção inválida. Tente novamente.");
            escolha = ler.nextInt();
        }
        ler.nextLine(); // Limpa o buffer após nextInt()

        if (escolha == 1) {
            Funcionalidade1.escolhaFun1();
        } else if (escolha == 2) {
            Funcionalidade2.escolhaFun2();
        } else if (escolha == 3) {
            Funcionalidade3.escolhaFun3();
        } else if (escolha == 4) {
            Funcionalidade4.escolhaFun4();
        } else {
            System.exit(0);
        }
    }

    //cria um menu que permite ao utilizador escolher o processo pelo qual quer inserir os dados da imagem
    //retorna para o método das funcionalidades (fun1, fun2, Fun3) o número correspondente ao caminho escolhido
    public static int escolhaDoCaminho() {
        System.out.println("\nEscolha o caminho para inserir os dados: ");
        System.out.println("1. Processo Automático (usar o caminho padrão)");
        System.out.println("2. Processo Manual (introduzir caminho para matriz CSV)");
        System.out.print("Caminho: ");
        int caminho = ler.nextInt();
        while (caminho != 1 && caminho != 2) {
            System.out.println("Opção inválida. Tente novamente.");
            caminho = ler.nextInt();
        }
        ler.nextLine();
        return caminho;
    }

    //tem como parâmetro uma array 2D (matriz) qualquer e um inteiro para o número de casas decimais
    //imprime a array 2D (matriz) formatada
    public static void outputMatriz(double[][] matriz, int casasDecimais) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.printf("%15."+casasDecimais+"f", matriz[i][j]);
                System.out.print("  ");
                if (j == matriz[0].length - 1) {
                    System.out.println();
                }
            }
        }
        System.out.println();
    }

    //recebe como parâmetros uma array 2D (matriz) e uma string com o caminho para o ficheiro, além do inteiro com o número de casas decimais
    //imprime no ficheiro especificado a array 2D (matriz) dada
    public static void imprimirFicheiro(double[][] matriz, String caminhoFicheiro, int casasDecimais) throws IOException {
        PrintWriter pr = new PrintWriter(new FileWriter(caminhoFicheiro));
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                pr.printf("%15."+casasDecimais+"f",matriz[i][j]);
                if (j < matriz[i].length - 1) {
                    pr.print(",");
                }
            }
            pr.println();
        }
        pr.close();
    }
}
