/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.pokerdefinitivo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Pedro
 */
public class PokerDefinitivo {
    
    public static String EJERCICIO_UNO = "1- Mejor jugada 5 manos\n";
    public static String EJERCICIO_DOS = "2- Mejor jugada 2 cartas y de 3 a 5 cartas\n";
    public static String EJERCICIO_TRES = "3- Mejor jugada de n jugadores\n";
    public static String EJERCICIO_CUATRO = "4- Omaha\n";
    public static String SALIR = "0- Salir\n";
    public static String RESPUESTA = "Introduzca el modo deseado:";
    public static String ERROR = "[ERROR] NUMERO NO VALIDO";
    public static String MENU = EJERCICIO_UNO + EJERCICIO_DOS + EJERCICIO_TRES + EJERCICIO_CUATRO + SALIR + RESPUESTA;
    public static String FICHERO_ENTRADA = "Introduzca el nombre del fichero: ";
    public static String FICHERO_SALIDA = "Introduzca el nombre que quiere para el fichero de salida: ";
    
    
    public static void main(String[] args) throws IOException {
        int i = 1;
        Scanner lectura = new Scanner(System.in);
        String ficheroS = "";
        String ficheroE = "";
        
        System.out.println(MENU);
        i = lectura.nextInt();
        while(i != 0){
            if (i > 0 && i < 5){
                System.out.println(FICHERO_ENTRADA);
                ficheroE = lectura.next();
                System.out.println(FICHERO_SALIDA);
                ficheroS = lectura.next();
            }
            switch (i) {
                case 1:
                    Mano mano = new Mano(ficheroE, ficheroS);
                    mano.leerMano();
                    break;
                case 2:
                    Mano mano2 = new Mano(ficheroE, ficheroS);
                    mano2.leerManoEjercicio2();
                    break;
                case 3:
                    Partida partida = new Partida(ficheroE, ficheroS);
                    partida.leerPartida();
                    break;
                case 4:
                    
                    break;
                case 0:
                    break;
                default:
                    System.out.println(ERROR);
            }
            System.out.println(MENU);
            i = lectura.nextInt();
        }
        //Partida partida = new Partida();
        //partida.leerPartida();
    }
    
}
