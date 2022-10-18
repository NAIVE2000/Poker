/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokerdefinitivo;

/**
 *
 * @author Pedro
 */
public class Jugador {
    Mano mano;
    Carta[] listMano;
    String nombreJugador;
    
    public Jugador(){
        mano = new Mano("","");
        listMano = new Carta[2];
        nombreJugador="";
    }
    
    public void leerPartida(){
        
    }
    
    public void AnadirAMano(String texto, int ini, int fin){
        mano.anadirAMano(texto, ini, fin);
    }
    public void ordenarCartasJugador(){
        mano.ordenarMano();
    }
    public void jugar(){
        mano.mejorManoJugadores();
    }
}
