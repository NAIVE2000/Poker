/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokerdefinitivo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Pedro
 */
public class Partida {
    
    private Jugador[] listJug = new Jugador[11];
    private int numJug = 0;
    private String ficheroE, ficheroS;
    
    Partida(String _ficheroE, String _ficheroS){
        ficheroE=_ficheroE;
        ficheroS = _ficheroS;
    }
    
    public void leerPartida() throws IOException{
        String nombreFichero = ficheroE;
        BufferedReader br = null;
        
        try{
            br = new BufferedReader(new FileReader(nombreFichero));
            String texto = br.readLine();
            
            while(texto != null){
                char numero = texto.charAt(0);
                String _numero = ""+numero;
                numJug = Integer.parseInt(_numero);
                crearJugadores();
                anadirCartas(texto);
                ordenarCartasJugadores();
                jugar();
                ordenarJugadores();
                listJug[1].mano.escribirFicheroBueno(ficheroS, texto);
                listaGanadores();
                listJug[1].mano.escribirFicheroBueno(ficheroS, "\n");
                texto = br.readLine();
                listJug = new Jugador[11];
            }
           
        }
        catch(FileNotFoundException ex){
            System.out.println("Error: Fichero no encontrado");
            ex.printStackTrace();
        }
    }
    
    public void listaGanadores(){
        for (int i = 1; i < numJug+1; i++){
            escribirFichero(i);
        }
    }
    public void crearJugadores(){
        for (int i = 1; i < numJug+1; i++){
            listJug[i]= new Jugador();
            listJug[i].nombreJugador = "J"+i;
        }
    }
    public void anadirCartas(String texto){
        int i = 3;
        int aux = 0;
        while(i < texto.length()){
            char numJugador = texto.charAt(i);
            String _numJugador = ""+numJugador;
            int numero = Integer.parseInt(_numJugador);
            listJug[numero].AnadirAMano(texto, i+1, i+4);
            i+=7;
            if(aux+1 == numJug)
                break;
            aux++;
        }
        
        for(int j = 1; j < numJug+1; j++){
            listJug[j].AnadirAMano(texto, i-1, texto.length());
        }
    }
    public void jugar(){
        for(int i = 1; i < numJug+1; i++){
            listJug[i].jugar();
        }
    }
    public void ordenarJugadores(){
        boolean sorted = false;
        Jugador temp;
        while(!sorted) {
            sorted = true;
            for (int i = 1; i < numJug+1; i++) {
                if(listJug[i] == null || listJug[i+1]==null)
                    break;
                if (listJug[i].mano.valorMano < listJug[i+1].mano.valorMano) {
                    temp = listJug[i];
                    listJug[i] = listJug[i+1];
                    listJug[i+1] = temp;
                    sorted = false;
                }
                else if(listJug[i].mano.valorMano == listJug[i+1].mano.valorMano){
                    if(listJug[i].mano.solucion.get(0).numInt() < listJug[i+1].mano.solucion.get(0).numInt()){
                        temp = listJug[i];
                        listJug[i] = listJug[i+1];
                        listJug[i+1] = temp;
                        sorted = false;
                    }
                    else if(listJug[i].mano.solucion.get(0).numInt() == listJug[i+1].mano.solucion.get(0).numInt()){
                        int aux = desempatar(i, i+1);
                        if(aux == i){
                            temp = listJug[i];
                            listJug[i] = listJug[i+1];
                            listJug[i+1] = temp;
                            sorted = false;
                        }
                    }
                }
            }
        }
    }
    public void ordenarCartasJugadores(){
        for (int i = 1; i < numJug+1; i++) {
            listJug[i].ordenarCartasJugador();
        }
    }
    public int desempatar(int i, int j){
        int sol = 0;
        
        for(int k = 1; k < listJug[i].mano.listMano.length; k++){
            if(listJug[i].mano.listMano[k].numInt() < listJug[j].mano.listMano[k].numInt())
                sol = i;
                break;
        }
        
        return sol;
    }
    public void escribirFichero(int jugador){
        String jug = listJug[jugador].nombreJugador+": ";
        jug += listJug[jugador].mano.solucionString;
        listJug[jugador].mano.escribirFicheroBueno(ficheroS, jug);
    }
}
