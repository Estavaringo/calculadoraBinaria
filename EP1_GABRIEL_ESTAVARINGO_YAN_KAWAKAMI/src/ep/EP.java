/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Gabriel Estavaringo Ferreira 10391246
 */
public class EP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int operacao, modo, j, qtdBits; //variaveis auxiliares
        //variaveis da conta em inteiro
        int dec1, dec2, res;
        ArrayList<Integer> bin1, bin2, resBin = new ArrayList<>();
        //variaveis da conta em float
        int[] float1 = new int[32];
        int[] float2 = new int[32];
        int[] resFloat = new int[32];
        boolean ex = true;
        ArrayList<Integer> bias = new ArrayList<>();        //bias utilizado para armazenar o expoente com excesso
        bias = Calculadora.converter(127, 8);
        ArrayList<Integer> exp = new ArrayList<>();

        Scanner scan = new Scanner(System.in);

        while (ex) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
            exp.clear();
            System.out.println("\t\t#### Calculadora de Números Binários ####");
            System.out.println("[1] - Inteiros");
            System.out.println("[2] - Ponto Flutuante");
            System.out.println("[0] - Sair");
            System.out.println("\nDigite a opção que deseja executar: ");
            modo = scan.nextInt();

            switch (modo) {
                case 0:
                    ex = false;
                    break;
                case 1:
                    for (int i = 0; i < 50; i++) {
                        System.out.println();
                    }
                    System.out.println("\nDigite a quantidade de bits (sinal incluso): ");
                    qtdBits = scan.nextInt();
                    System.out.println("\nDigite o primeiro número em base decimal: ");
                    dec1 = scan.nextInt();
                    System.out.println("\n\n[1] - Soma");
                    System.out.println("[2] - Subtração");
                    System.out.println("[3] - Divisão");
                    System.out.println("[4] - Multiplicação");
                    System.out.println("Digite a operação: ");
                    operacao = scan.nextInt();
                    System.out.println("\nDigite o segundo número em base decimal: ");
                    dec2 = scan.nextInt();

                    //converte os dois valores para binário
                    bin1 = Calculadora.converter(dec1, qtdBits);
                    bin2 = Calculadora.converter(dec2, qtdBits);
                    if (bin1.isEmpty() || bin2.isEmpty()) {
                        System.out.println("\n\nQuantidade de bits insuficiente!!");
                        scan.next();
                        continue;
                    }

                    //verifica a operação que o usuario escolheu e chama o metodo correspondente da classe Calculadora
                    switch (operacao) {
                        case 1:
                            for (int i = 0; i < 50; i++) {
                                System.out.println();
                            }
                            resBin = Calculadora.somar(bin1, bin2);
                            System.out.println("\n\nOperação: Soma");
                            break;
                        case 2:
                            for (int i = 0; i < 50; i++) {
                                System.out.println();
                            }
                            resBin = Calculadora.subtrair(bin1, bin2);
                            System.out.println("\n\nOperação: Subtração");
                            break;
                        case 3:
                            for (int i = 0; i < 50; i++) {
                                System.out.println();
                            }
                            resBin = Calculadora.dividir(bin1, bin2, false);
                            System.out.println("\n\nOperação: Divisão");
                            break;
                        case 4:
                            for (int i = 0; i < 50; i++) {
                                System.out.println();
                            }
                            resBin = Calculadora.multiplicarBooth(bin1, bin2, false);
                            if (resBin.isEmpty()) {
                                System.out.println("\n\nQuantidade de bits insuficiente para armazenar resultado da multiplicação");
                                scan.next();
                                continue;
                            }
                            System.out.println("\n\nOperação: Multiplicação");
                            break;
                        default:
                            System.out.println("\n\nOperação Inválida!! Tente Novamente!");
                            scan.next();
                            continue;

                    }
                    System.out.println("Quantidade de bits: " + qtdBits);
                    System.out.println("Número 1:");
                    System.out.print("\t Decimal: " + dec1 + "\tBinário: ");
                    Calculadora.imprime(bin1);
                    System.out.println("");
                    System.out.println("Número 2:");
                    System.out.print("\t Decimal: " + dec2 + "\tBinário: ");
                    Calculadora.imprime(bin2);
                    System.out.println("");
                    System.out.println("Resultado:");
                    System.out.print("\t Decimal: " + Calculadora.converter(resBin) + "\tBinário: ");
                    Calculadora.imprime(resBin);
                    System.out.println("");
                    System.out.println("\n\nEntre com qualquer valor para retornar ao menu");
                    scan.next();
                    break;
                case 2:
                    for (int i = 0; i < 50; i++) {
                        System.out.println();
                    }
                    System.out.println("Exemplo de entrada: sinal + mantissa normalizada + expoente sem excesso (Ex: 0 1,00101000000000000000000 00000010)");
                    System.out.println("\nDigite o primeiro número em ponto flutuante padrão IEE754: ");
                    scan.nextLine();
                    String strFloat1 = scan.nextLine();
                    strFloat1 = strFloat1.replace(" ", ""); //retira os espaços do numero em ponto flutuante

                    //pega o sinal do ponto flutuante e armazena na primeira posição do vetor de inteiros
                    float1[0] = Character.getNumericValue(strFloat1.charAt(0));

                    //armazena a mantissa no vetor de inteiros
                    j = 9;
                    for (int i = 3; i < 26; i++) {
                        float1[j] = Character.getNumericValue(strFloat1.charAt(i));
                        j++;
                    }

                    //armazena o expoente no vetor de inteiros
                    for (int i = 33; i > 25; i--) {
                        exp.add(Character.getNumericValue(strFloat1.charAt(i)));
                    }
                    
                    
                    exp = Calculadora.somar(exp, bias);
                    
                    if(exp.size()>8) exp.remove(exp.size()-1);
                  
                    j = 1;
                    for (int i = exp.size()-1; i >= 0; i--) {
                        float1[j] = exp.get(i);
                        j++;
                    }

                    
                    System.out.println("\n\n[1] - Soma");
                    System.out.println("[2] - Subtração");
                    System.out.println("[3] - Divisão");
                    System.out.println("[4] - Multiplicação");
                    System.out.println("\nDigite a operação: ");
                    operacao = scan.nextInt();
                    System.out.println("\nDigite o segundo número em ponto flutuante padrão IEE754: ");

                    scan.nextLine();
                    String strFloat2 = scan.nextLine();
                    strFloat2 = strFloat2.replace(" ", "");
                    exp.clear();

                    //pega o sinal do ponto flutuante e armazena na primeira posição do vetor de inteiros
                    float2[0] = Character.getNumericValue(strFloat2.charAt(0));

                    //armazena a mantissa no vetor de inteiros
                    j = 9;
                    for (int i = 3; i < 26; i++) {
                        float2[j] = Character.getNumericValue(strFloat2.charAt(i));
                        j++;
                    }

                    //armazena o expoente no vetor de inteiros
                    for (int i = 33; i > 25; i--) {
                        exp.add(Character.getNumericValue(strFloat2.charAt(i)));
                    }
                    
                    exp = Calculadora.somar(exp, bias);
                    
                    if(exp.size()>8) exp.remove(exp.size()-1);
                    
                    j = 1;
                    for (int i = exp.size()-1; i >= 0; i--) {
                        float2[j] = exp.get(i);
                        j++;
                    }

                    //verifica a operação que o usuario escolheu e chama o metodo correspondente da classe Calculadora
                    switch (operacao) {
                        case 1:
                            resFloat = Calculadora.somarSubtrair(float1, float2, 0);
                            System.out.println("\n\nOperação: Soma");
                            break;
                        case 2:
                            resFloat = Calculadora.somarSubtrair(float1, float2, 1);
                            System.out.println("\n\nOperação: Subtração");
                            break;
                        case 3:
                            resFloat = Calculadora.dividir(float1, float2);
                            System.out.println("\n\nOperação: Divisão");
                            break;
                        case 4:
                            resFloat = Calculadora.multiplicar(float1, float2);
                            System.out.println("\n\nOperação: Multiplicação");
                            break;
                        default:
                            System.out.println("\n\nOperação Inválida!! Tente Novamente!");
                            scan.next();
                            continue;
                    }
                    System.out.println("Número 1:");
                    System.out.print("\t Decimal: " + Calculadora.converter(float1) + "\tBinário: ");
                    for (int i = 0; i < 32; i++) {
                        System.out.print(float1[i]);
                    }
                    System.out.println("");
                    System.out.println("Número 2:");
                    System.out.print("\t Decimal: " + Calculadora.converter(float2) + "\tBinário: ");
                    for (int i = 0; i < 32; i++) {
                        System.out.print(float2[i]);
                    }
                    System.out.println("");
                    System.out.println("Resultado:");
                    System.out.print("\t Decimal: " + Calculadora.converter(resFloat) + "\tBinário: ");
                    for (int i = 0; i < 32; i++) {
                        System.out.print(resFloat[i]);
                    }
                    System.out.println("");
                    System.out.println("\n\nEntre com qualquer valor para retornar ao menu");
                    scan.next();
                    break;
                default:
                    System.out.println("\n\nOpção Inválida!! Tente Novamente!");
                    scan.next();
                    break;
            }

        }
    }

}
