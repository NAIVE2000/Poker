/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokerdefinitivo;

public class Carta {
    private String pala;
    private String numera;
    
    public Carta(String _numero, String _palo){
        numera = _numero; 
        pala = _palo;
        
    }

    
    public String palo(){return pala;}
    public String numero(){return numera;}
    public int numInt(){return Integer.parseInt(numera);}
}

