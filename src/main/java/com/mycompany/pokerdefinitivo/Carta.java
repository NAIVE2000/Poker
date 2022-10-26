/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokerdefinitivo;

public class Carta {
    private String palo;
    private String numera;
    
    public Carta(String _numero, String _palo){
        numera = _numero; 
        palo = _palo;
        
    }

    
    public String palo(){return palo;}
    public String numero(){return numera;}
    public int numInt(){return Integer.parseInt(numera);}
}

