/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep;

import java.util.ArrayList;

/**
 *
 * @author gabri
 */
public class Calculadora {

    //converte o numero de decimal para binario (inteiro)
    public static ArrayList<Integer> converter(int dec, int qtdBits) {
        ArrayList<Integer> bin = new ArrayList<>();
        boolean negativo = false;

        if (dec < 0) {
            negativo = true;    //se o numero for negativo, seta a flag de negativo e converte para positivo
            dec = dec * -1;
        }

        while (dec / 2 >= 1) {   //enquanto a divisão por 2 for maior ou igual a 1
            if (dec % 2 == 1) {
                bin.add(1); //seta o bit se o resto da divisao por 2 for igual a 1
            } else {
                bin.add(0);
            }
            dec = dec / 2;  //divide por 2
        }
        if (dec % 2 == 1) {
            bin.add(1);
        } else {
            bin.add(0);
        }

        bin.add(0); //sinal

        if (qtdBits == 0) {
            return bin;
        }

        if (bin.size() > qtdBits) { // se o número possuir mais bits do que o solicitado, retorna o arraylist vazio
            bin.clear();
            return bin;
        }
        while (bin.size() < qtdBits) { //enquanto o numero não possuir a quantidade de bits necessária, adiciona 0 no MSB
            bin.add(0);
        }

        if (negativo) {
            return complemento2(bin); //transforma o numero para negativo em binario
        }
        return bin;
    }

    //converte o número de binário para decimal (inteiro)
    public static int converter(ArrayList<Integer> bin) {
        int dec = 0;
        boolean negativo = false;

        //se o numero for negativo, faz o complemento de 2 e seta a flag negativo
        if (bin.get(bin.size() - 1) == 1) {
            bin = complemento2(bin);
            negativo = true;
        }

        for (int i = 0; i < bin.size() - 1; i++) {
            if (bin.get(i) == 1) {
                dec = (int) (dec + Math.pow(2, i)); //adiciona 2 elevado ao indice do bit que é 1
            }
        }
        if (negativo) {
            return dec * -1;
        }
        return dec;
    }

    //soma dois números binários (inteiro)
    public static ArrayList<Integer> somar(ArrayList<Integer> bin1, ArrayList<Integer> bin2) {
        if (bin1.isEmpty()) {
            return bin2;
        }
        if (bin2.isEmpty()) {
            return bin1;
        }

        ArrayList<Integer> soma = new ArrayList<>();
        int resto = 0;

        //soma bit a bit os dois elementos
        for (int i = 0; i < bin1.size() - 1; i++) {
            if (bin1.get(i) == 0 && bin2.get(i) == 0) {
                if (resto == 0) {   //se os dois bits forem 0 e o resto 0, adiciona o bit 0 ao resultado e vai pra proxima iteração
                    soma.add(0);
                    continue;
                }
                soma.add(1);    //se o resto for 1, adiciona o bit 1 ao resultado e coloca 0 no resto
                resto = 0;
                continue;
            }
            if (bin1.get(i) == 1 && bin2.get(i) == 1) {
                if (resto == 0) {   //se os dois bits forem 1 e o resto 0, adiciona 0 no resultado e 1 no resto
                    resto = 1;
                    soma.add(0);
                    continue;
                }
                soma.add(1);    //se o resto for 1, adiciona 1 no resultado e vai pra proxima iteração (resto mantém 1)
                continue;
            }
            if (resto == 0) {   //se o resto for 0 e os bits forem 0 e 1, adiciona 1 no resultado e vai pra proxima iteração
                soma.add(1);
                continue;
            }
            soma.add(0);    //se o resto for 1 e os bits forem 0 e 1, adiciona 0 no resultado e vai pra proxima iteração (resto mantém 1) 
        }

        //SINAL
        if (resto == 1) {
            if (bin1.get(bin1.size() - 1) == 0 && bin2.get(bin2.size() - 1) == 0) {
                soma.add(1);
                soma.add(0);
                return soma;
            }
            if (bin1.get(bin1.size() - 1) == 1 && bin2.get(bin2.size() - 1) == 1) {
                soma.add(1);
                return soma;
            }

            soma.add(0);
            return soma;
        }

        if (bin1.get(bin1.size() - 1) == 1 && bin2.get(bin2.size() - 1) == 1) {
            soma.add(0);
            soma.add(1);
            return soma;
        }

        if (bin1.get(bin1.size() - 1) == 0 && bin2.get(bin2.size() - 1) == 0) {
            soma.add(0);
            return soma;
        }

        soma.add(1);
        return soma;
    }

    //retorna o complemento de 2 de um número binário recebido como parâmetro
    public static ArrayList<Integer> complemento2(ArrayList<Integer> bin) {
        ArrayList<Integer> um;
        ArrayList<Integer> aux = new ArrayList<>();
        um = converter(1, bin.size());
        //inverte os bits do numero
        for (Integer bit : bin) {
            if (bit == 1) {
                aux.add(0);
            } else {
                aux.add(1);
            }
        }
        //retorna o numero invertido adicionado 1
        return somar(aux, um);
    }

    //apenas para imprimir o numero binario na ordem correta
    public static void imprime(ArrayList<Integer> bin) {

        for (int i = bin.size() - 1; i >= 0; i--) {
            System.out.print(bin.get(i));
        }
    }

    //subtrai dois numeros binários (inteiro)
    static ArrayList<Integer> subtrair(ArrayList<Integer> bin1, ArrayList<Integer> bin2) {
        //retorna a soma do primeiro com o complemento de 2 do segundo
        return somar(bin1, complemento2(bin2));
    }

    //multiplica dois numeros binarios inteiros utilizando o metodo de booth
    static ArrayList<Integer> multiplicarBooth(ArrayList<Integer> bin1, ArrayList<Integer> bin2, boolean mantissa) {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> s = new ArrayList<>();
        ArrayList<Integer> p = new ArrayList<>();
        ArrayList<Integer> aux = complemento2(bin1);         //complemento de 2 do multiplicando
        a.add(0);
        s.add(0);
        p.add(0);
        for (int i = 0; i < bin2.size(); i++) {
            a.add(0);
            s.add(0);
            p.add(bin2.get(i));
        }
        for (int i = 0; i < aux.size(); i++) {
            a.add(bin1.get(i));
            s.add(aux.get(i));
            p.add(0);
        }
        for (int i = 0; i < bin2.size(); i++) {
            if (p.get(0) == 0 && p.get(1) == 1) {
                p = somar(p, s);
            }
            if (p.get(0) == 1 && p.get(1) == 0) {
                p = somar(p, a);
            }
            if (p.get(p.size() - 1) == 1) {
                p.add(1);
            } else {
                p.add(0);
            }
            p.remove(0);
        }
        p.remove(0);

        //se for calculo de mantissa, retorna o resultado
        if (mantissa) {
            return p;
        }

        //se for calculo de inteiros, deixa o numero com a mesma quantidade de bits dos operadores
        int aux2 = converter(p);

        //retira um bit enquanto o numero não mudar de valor. (se a quantidade de bits não for suficiente, retorna vazio)  
        while (p.size() > bin1.size()) {
            if (aux2 != converter(p)) {
                p.clear();
                return p;
            }
            p.remove(p.size() - 1);
        }
        return p;
    }

    //divide dois numero binários (inteiro)
    static ArrayList<Integer> dividir(ArrayList<Integer> bin1, ArrayList<Integer> bin2, boolean mantissa) {
        boolean negativo = false;
        ArrayList<Integer> resBin;
        //se o numero for negativo, armazena ele da forma positiva e muda a flag de resultado negativo
        if (!mantissa) {
            if (bin2.get(bin2.size() - 1) == 1) {
                bin2 = complemento2(bin2);
                negativo = true;
            }

            if (bin1.get(bin1.size() - 1) == 1) {
                bin1 = complemento2(bin1);
                negativo = !negativo;
            }
        }

        while (bin1.size() < bin2.size()) {
            bin1.add(0);
        }
        while (bin2.size() < bin1.size()) {
            bin2.add(0);
        }

        //subtrai o divisor do dividendo e incrementa o resultado para cada subtração (enquanto o resultado da subtração for maior que 0)
        ArrayList<Integer> aux = bin1;
        int res = 0;
        while (converter(subtrair(aux, bin2)) >= 0) {
            res++;
            aux = subtrair(aux, bin2);
        }

        //se for divisão de mantissa, faz a divisão considerando o resto
        if (mantissa) {

            resBin = converter(res, 0);
            //enquanto o resto não for 0 
            while (converter(aux) != 0 && resBin.size() <= 50) {

                //se o resto não puder ser divido pelo dividendo, adiciona um 0 no LSB do resto
                if (converter(subtrair(aux, bin2)) < 0) {
                    aux.add(0, 0);
                    bin2.add(0);
                }

                //enquanto o resto não puder ser divido pelo dividendo, adiciona um 0 no LSB do resto e do quociente 
                while (converter(subtrair(aux, bin2)) < 0) {
                    aux.add(0, 0);
                    bin2.add(0);
                    resBin.add(0, 0);
                }
                //faz a divisão e incrementa coloca 1 bit no quociente
                while (converter(subtrair(aux, bin2)) >= 0) {
                    resBin.add(0, 1);
                    aux = subtrair(aux, bin2);
                }

            }

        }else resBin = converter(res, bin1.size());
        if (negativo) {
            return complemento2(resBin);
        }

        return resBin;
    }
    //método para converter ponto flutuante binário para base decimal. (recebe numero no padrao IEE 754)

    static float converter(int[] pontoFlutuante) {
        int expoenteDec; //armazena o expoente em decimal
        float dec = 1;
        int j = -1;  //iterador para somar parte decimal (-1,-2,-3..)

        ArrayList<Integer> mantissa = new ArrayList<>();    //armazena a mantissa em binario
        ArrayList<Integer> expoente = new ArrayList<>();    //armazena o expoente em binario
        ArrayList<Integer> bias;        //bias (somente para subtrair com o expoente)
        bias = converter(127, 8);

        //separa a mantissa e o expoente
        for (int i = 31; i >= 9; i--) {
            mantissa.add(pontoFlutuante[i]);
        }
        for (int i = 8; i >= 1; i--) {
            expoente.add(pontoFlutuante[i]);
        }
        if (converter(mantissa) == 0 && converter(expoente) == 0) {
            return 0;
        }

        expoente.add(0); //sinal (somente para fazer a subtração)
        expoente = subtrair(expoente, bias);
        expoenteDec = converter(expoente);  //converte o expoente para decimal

        //converte a mantissa em decimal
        for (int i = mantissa.size() - 1; i >= 0; i--) {
            if (mantissa.get(i) == 1) {
                dec = (float) (dec + Math.pow(2, j)); //adiciona 2 elevado ao indice do bit que é 1
            }
            j--;
        }

        dec = (float) (dec * Math.pow(2, expoenteDec)); //resultado é igual a mantissa elevada ao expoente

        //se for negativo, retorna o resultado multiplicado por -1
        if (pontoFlutuante[0] == 1) {
            return dec * -1;
        }
        return dec;
    }

    //metodo para somar ou subtrair ponto flutuante (soma: op = 0; subtração: op = 1)
    static int[] somarSubtrair(int[] pontoFlutuante, int[] pontoFlutuante2, int op) {
        int comparaExpoente; //usado para comparar os expoentes e verificar qual é menor
        int j;  //iterador para somar parte decimal (-1,-2,-3..)
        int[] res = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};   //armazena o resultado da soma
        ArrayList<Integer> um;  //utilizado para incrementar/decrementar o expoente
        um = converter(1, 8);

        ArrayList<Integer> mantissa1 = new ArrayList<>();    //armazena a mantissa 1 em binario
        ArrayList<Integer> mantissa2 = new ArrayList<>();    //armazena a mantissa 2 em binario
        ArrayList<Integer> mantissaRes;    //armazena a mantissa do resultado em binario
        ArrayList<Integer> expoente1 = new ArrayList<>();    //armazena o expoente 1 em binario
        ArrayList<Integer> expoente2 = new ArrayList<>();    //armazena o expoente 2 em binario
        ArrayList<Integer> expoenteRes;    //armazena o expoente do resultado em binario

        for (int i = 31; i >= 9; i--) {
            mantissa1.add(pontoFlutuante[i]);
        }

        for (int i = 31; i >= 9; i--) {
            mantissa2.add(pontoFlutuante2[i]);
        }

        for (int i = 8; i >= 1; i--) {
            expoente1.add(pontoFlutuante[i]);
        }

        for (int i = 8; i >= 1; i--) {
            expoente2.add(pontoFlutuante2[i]);
        }
        mantissa1.add(1); //adiciona parte inteira da mantissa 1 (usado para deslocar mantissa ao incrementar expoente)
        mantissa2.add(1); //adiciona parte inteira da mantissa 1 (usado para deslocar mantissa ao incrementar expoente)
        expoente2.add(0); //sinal para poder utilizar o metodo de soma 
        expoente1.add(0); //sinal para poder utilizar o metodo de soma
        mantissa1.add(0); //sinal para poder utilizar o metodo de soma
        mantissa2.add(0); //sinal para poder utilizar o metodo de soma

        comparaExpoente = compara(expoente1, expoente2);

        //enquanto os expoentes forem diferentes, incrementa o menor expoente e faz o right shift
        while (comparaExpoente != 0) {
            if (comparaExpoente == 1) {
                rightShift(mantissa2);  //desloca a mantissa para incrementar o expoente
                expoente2 = somar(expoente2, um);
            } else {
                rightShift(mantissa1);  //desloca a mantissa para incrementar o expoente
                expoente1 = somar(expoente1, um);
            }
            comparaExpoente = compara(expoente1, expoente2);
        }

        //se o ponto flutuante for negativa, armazena a mantissa em complemento de 2 (necessário para a adição/subtração)
        if (pontoFlutuante[0] == 1) {
            mantissa1 = complemento2(mantissa1);
        }
        if (pontoFlutuante2[0] == 1) {
            mantissa2 = complemento2(mantissa2);
        }

        //faz a adição ou subtração das mantissas
        if (op == 0) {
            mantissaRes = somar(mantissa1, mantissa2);
        } else {
            mantissaRes = subtrair(mantissa1, mantissa2);
        }

        expoenteRes = expoente1;//os expoentes estão igualados, portanto armazena o expoente do resultado sera igual a qualquer um dos expoentes

        //se a soma das mantissas tiver mais que 23 bits, incrementa o expoente e desconsidera o LSB
        if (mantissaRes.size() > 25) {
            mantissaRes.remove(0);
            expoenteRes = somar(expoenteRes, um);
            if (converter(expoenteRes) > 255) {
                System.out.println("overflow no expoente");
            }
        }

        //retira o sinal da mantissa e armazena no vetor final de ponto flutuante
        res[0] = mantissaRes.get(mantissaRes.size() - 1);
        if (res[0] == 1) {
            mantissaRes = complemento2(mantissaRes);
        }
        mantissaRes.remove(mantissaRes.size() - 1);

        if (converter(mantissaRes) == 0) {
            return res;
        }

        while (mantissaRes.get(mantissaRes.size() - 1) != 1) {    //enquanto o MSB não for 1, faz o left shit e decrementa o expoente (normalizar)
            leftShift(mantissaRes);
            expoenteRes = subtrair(expoenteRes, um);
            if (converter(expoenteRes) < 0) {
                System.out.println("underflow no expoente");
            }
        }

        expoenteRes.remove(expoenteRes.size() - 1); //remove o sinal no expoente

        mantissaRes.remove(mantissaRes.size() - 1); //retira a parte inteira da mantissa da mantissa

        //armazena o resultado no vetor soma
        j = 1;
        for (int i = expoenteRes.size() - 1; i >= 0; i--) {
            res[j] = expoenteRes.get(i);
            j++;
        }
        for (int i = mantissaRes.size() - 1; i >= 0; i--) {
            res[j] = mantissaRes.get(i);
            j++;
        }

        return res;

    }

    //compara dois binarios (retorna 0 se forem iguais; -1 se o primeiro for menor; 1 se o primeiro for maior)
    private static int compara(ArrayList<Integer> bin1, ArrayList<Integer> bin2) {
        int dec1, dec2;
        dec1 = converter(bin1);  //converte o expoente para decimal
        dec2 = converter(bin2);  //converte o expoente para decimal

        if (dec1 == dec2) {
            return 0;
        }
        if (dec1 > dec2) {
            return 1;
        }
        return -1;

    }

    //faz o right shift no numeo binario (utilizad para ponto flutuante)
    private static void rightShift(ArrayList<Integer> mantissa) {
        mantissa.add(0);//adiciona 0 no MSB
        mantissa.remove(0);//remove o LSB
    }

    //faz o left shift no numeo binario (utilizad para ponto flutuante)
    private static void leftShift(ArrayList<Integer> mantissa) {
        mantissa.add(0, 0); //adiciona 0 no LSB
        mantissa.remove(mantissa.size() - 1); //remove o MSB
    }

    //multiplica dois numeros de ponto flutuante
    static int[] multiplicar(int[] pontoFlutuante, int[] pontoFlutuante2) {

        int negativo; //flag que indica se o resultado sera negativo ou não
        int j;
        int[] res = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};   //armazena o resultado da soma
        ArrayList<Integer> um;  //utilizado para incrementar/decrementar o expoente
        um = converter(1, 8);
        ArrayList<Integer> bias;  //utilizado para adicionar/remover o excesso
        bias = converter(127, 8);

        ArrayList<Integer> mantissa1 = new ArrayList<>();    //armazena a mantissa 1 em binario
        ArrayList<Integer> mantissa2 = new ArrayList<>();    //armazena a mantissa 2 em binario
        ArrayList<Integer> mantissaRes;    //armazena a mantissa do resultado em binario
        ArrayList<Integer> expoente1 = new ArrayList<>();    //armazena o expoente 1 em binario
        ArrayList<Integer> expoente2 = new ArrayList<>();    //armazena o expoente 2 em binario
        ArrayList<Integer> expoenteRes;    //armazena o expoente do resultado em binario

        //separa as mantissas e os expoentes recebidos no vetor de inteiros
        for (int i = 31; i >= 9; i--) {
            mantissa1.add(pontoFlutuante[i]);
        }
        for (int i = 31; i >= 9; i--) {
            mantissa2.add(pontoFlutuante2[i]);
        }
        for (int i = 8; i >= 1; i--) {
            expoente1.add(pontoFlutuante[i]);
        }
        for (int i = 8; i >= 1; i--) {
            expoente2.add(pontoFlutuante2[i]);
        }

        mantissa1.add(1); //adiciona parte inteira da mantissa 1 (usado para deslocar mantissa ao incrementar expoente)
        mantissa2.add(1); //adiciona parte inteira da mantissa 1 (usado para deslocar mantissa ao incrementar expoente)
        expoente1.add(0); //sinal para poder utilizar o metodo de soma/subtração
        expoente2.add(0); //sinal para poder utilizar o metodo de soma/subtração
        mantissa1.add(0); //sinal para poder utilizar os metodos de multiplicação
        mantissa2.add(0); //sinal para poder utilizar os metodos de multiplicação

        //se os sinais forem diferentes, ativa a flag negativo
        if ((pontoFlutuante[0] == 1 && pontoFlutuante2[0] == 0) || (pontoFlutuante[0] == 0 && pontoFlutuante2[0] == 1)) {
            negativo = 1;
        } else {
            negativo = 0;
        }

        mantissaRes = multiplicarBooth(mantissa1, mantissa2, true); //multiplica as mantissas

        //retira o excesso dos expoentes
        expoente1 = subtrair(expoente1, bias);
        expoente2 = subtrair(expoente2, bias);

        expoenteRes = somar(expoente1, expoente2);//soma os expoentes

        //adiciona o excesso no expoente do resultado
        expoenteRes = somar(expoenteRes, bias);

        if (converter(expoenteRes) > 255) {
            System.out.println("\n\nOverflow no expoente");
            return res;
        }
        //acerta a virgula apos a multiplicação das mantissas
        if (mantissaRes.get(mantissaRes.size() - 1) == 0 && mantissaRes.get(mantissaRes.size() - 2) == 0) {
            expoenteRes = somar(expoenteRes, um);
            mantissaRes.remove(mantissaRes.size() - 1);
        }

        //desconsidera o LSB enquanto a mantissa for maior que 23bits
        while (mantissaRes.size() > 25) {
            mantissaRes.remove(0);
        }

        //se o expoente for maior que 255, indica overflow
        //retira o sinal da mantissa e armazena no vetor final de ponto flutuante
        res[0] = negativo;
        mantissaRes.remove(mantissaRes.size() - 1);

        //se a mantissa for 0, retorna o 0
        if (converter(mantissaRes) == 0) {
            return res;
        }

        while (mantissaRes.get(mantissaRes.size() - 1) != 1) {    //enquanto o MSB não for 1, faz o left shit e decrementa o expoente (normalizar)
            leftShift(mantissaRes);
            expoenteRes = subtrair(expoenteRes, um);
            if (converter(expoenteRes) < 0) {
                System.out.println("\n\nUnderflow no expoente");
                return res;
            }
        }

        expoenteRes.remove(expoenteRes.size() - 1); //remove o sinal no expoente
        mantissaRes.remove(mantissaRes.size() - 1); //retira a parte inteira da mantissa da mantissa

        //armazena o resultado no vetor res
        j = 1;
        for (int i = expoenteRes.size() - 1; i >= 0; i--) {
            res[j] = expoenteRes.get(i);
            j++;
        }
        for (int i = mantissaRes.size() - 1; i >= 0; i--) {
            res[j] = mantissaRes.get(i);
            j++;
        }

        return res;

    }

    //divide dois numeros de ponto flutuante
    static int[] dividir(int[] pontoFlutuante, int[] pontoFlutuante2) {

        int negativo; //flag que indica se o resultado sera negativo ou não
        int j;
        int[] res = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};   //armazena o resultado da divisão
        ArrayList<Integer> um;
        um = converter(1, 8);
        ArrayList<Integer> bias;  //utilizado para incrementar/decrementar o expoente
        bias = converter(127, 8);

        ArrayList<Integer> mantissa1 = new ArrayList<>();    //armazena a mantissa 1 em binario
        ArrayList<Integer> mantissa2 = new ArrayList<>();    //armazena a mantissa 2 em binario
        ArrayList<Integer> mantissaRes;    //armazena a mantissa do resultado em binario
        ArrayList<Integer> expoente1 = new ArrayList<>();    //armazena o expoente 1 em binario
        ArrayList<Integer> expoente2 = new ArrayList<>();    //armazena o expoente 2 em binario
        ArrayList<Integer> expoenteRes;    //armazena o expoente do resultado em binario

        //separa as mantissas e os expoentes recebidos no vetor de inteiros
        for (int i = 31; i >= 9; i--) {
            mantissa1.add(pontoFlutuante[i]);
        }
        for (int i = 31; i >= 9; i--) {
            mantissa2.add(pontoFlutuante2[i]);
        }
        for (int i = 8; i >= 1; i--) {
            expoente1.add(pontoFlutuante[i]);
        }
        for (int i = 8; i >= 1; i--) {
            expoente2.add(pontoFlutuante2[i]);
        }

        mantissa1.add(1);   //adiciona parte inteira da mantissa 1 (usado para deslocar mantissa ao incrementar expoente)
        mantissa2.add(1);  //adiciona parte inteira da mantissa 1 (usado para deslocar mantissa ao incrementar expoente)
        expoente1.add(0); //sinal para poder utilizar o metodo de subtração
        expoente2.add(0); //sinal para poder utilizar o metodo de subtração
        mantissa1.add(0); //sinal para poder utilizar os metodos de divisão
        mantissa2.add(0); //sinal para poder utilizar os metodos de divisão

        //se os sinais forem diferentes, ativa a flag negativo
        if ((pontoFlutuante[0] == 1 && pontoFlutuante2[0] == 0) || (pontoFlutuante[0] == 0 && pontoFlutuante2[0] == 1)) {
            negativo = 1;
        } else {
            negativo = 0;
        }

        //retira o excesso dos expoentes
        expoente1 = subtrair(expoente1, bias);
        expoente2 = subtrair(expoente2, bias);

        expoenteRes = subtrair(expoente1, expoente2);//subtrai os expoentes

        //adiciona o excesso no expoente do resultado
        expoenteRes = somar(expoenteRes, bias);

        mantissaRes = dividir(mantissa1, mantissa2, true); //divide as mantissas

        
        //acerta a virgula apos a divisão das mantissas
        if (mantissaRes.get(mantissaRes.size() - 1) == 0 && mantissaRes.get(mantissaRes.size() - 2) == 0) {
            expoenteRes = subtrair(expoenteRes, um);
            mantissaRes.remove(mantissaRes.size() - 1);
        }

        //se o expoente do resultado for menor que 0, indica underflow
        if (converter(expoenteRes) < 0) {
            System.out.println("\n\nUnderflow no expoente!!!");
            return res;
        }
        
        while (mantissaRes.get(mantissaRes.size() - 1) == 0 && mantissaRes.size() > 1 ) {
            mantissaRes.remove(mantissaRes.size() - 1);
        }
        

        //deixa a mantissa com os 23 bits (24, se considerar a parte inteira)
        while (mantissaRes.size() >= 25) {
            mantissaRes.remove(0);
        }
        //armazena o sinal no vetor final de ponto flutuante
        res[0] = negativo;

        expoenteRes.remove(expoenteRes.size() - 1); //remove o sinal no expoente

        mantissaRes.remove(mantissaRes.size() - 1); //retira a parte inteira da mantissa da mantissa

        //armazena o resultado no vetor res
        j = 1;
        for (int i = expoenteRes.size() - 1; i >= 0; i--) {
            res[j] = expoenteRes.get(i);
            j++;
        }
        for (int i = mantissaRes.size() - 1; i >= 0; i--) {
            res[j] = mantissaRes.get(i);
            j++;
        }

        return res;
    }

}
