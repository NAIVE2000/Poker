/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokerdefinitivo;
import static java.awt.PageAttributes.MediaType.C;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
/**
 *
 * @author Pedro
 */
public class Mano {
    static final String ESCALERA_COLOR = "Royal Straight";
    static final String POKER = "Poker";
    static final String FULL = "Full";
    static final String FLUSH = "Flush";
    static final String ESCALERA = "Straight";
    static final String TRIO = "Three of a kind";
    static final String DOBLE_PAREJA = "Two pair";
    static final String PAREJA = "Pair";
    static final String CARTA_ALTA = "High card";
    
    Carta[] listMano = new Carta[14];
    HashMap<String, ArrayList<Carta>> color;
    HashMap<Integer, ArrayList<Carta>> numCartas;
    HashMap<Integer, ArrayList<Carta>> cuarTioParej;
    ArrayList<Carta> solucion;
    int CARTAS, numEnMesa = 0, cont = 1, valorMano = 0;
    String drawColor, drawGutShot, drawOpenEnded, ficheroSalida, ficheroEntrada, solucionString;
    
    public Mano(String ficheroE, String ficheroS){
        ficheroSalida = ficheroS;
        ficheroEntrada = ficheroE;
        solucionString = "";
    }  
    
    public void iniciarNuevo(){
        
        color=new HashMap<String, ArrayList<Carta>>();
        numCartas=new HashMap<Integer, ArrayList<Carta>>();
        cuarTioParej=new HashMap<Integer, ArrayList<Carta>>();
        solucion = new ArrayList<>();
        drawColor = "";
        drawGutShot = "";
        drawOpenEnded = "";
        CARTAS = 6;
    }
    public void leerManoEjercicio2() throws FileNotFoundException, IOException{
        String nombreFichero = ficheroEntrada;
        BufferedReader br = null;
        
        try{
            br = new BufferedReader(new FileReader(nombreFichero));
            String texto = br.readLine();
            while(texto != null) {
                int cont = 1, i = 0;
                iniciarNuevo();
                listMano = new Carta[14];
                while(i < texto.length()){

                    char numero = (char)texto.charAt(i);
                    if(numero == ';'){
                        numero = texto.charAt(i+1);
                        String aux = "" + numero;
                        numEnMesa = Integer.parseInt(aux);
                        i++;
                    }
                    else{
                        char palo = (char) texto.charAt(i+1);
                        String  _numero = "" + numero;
                        String _palo = "" + palo;

                        if(numero == 'A'){
                            Carta carta = new Carta("1",_palo);
                            Carta cartaDos = new Carta("14",_palo);
                            listMano[cont] = carta;
                            listMano[cont+1] = cartaDos;
                            cont++;
                        }
                        else if(numero == 'K'){
                            Carta carta = new Carta("13",_palo);
                            listMano[cont] = carta;
                        }
                        else if(numero == 'Q'){
                            Carta carta = new Carta("12",_palo);
                            listMano[cont] = carta;
                        }
                        else if(numero == 'J'){
                            Carta carta = new Carta("11",_palo);
                            listMano[cont] = carta;
                        }
                        else if(numero == 'T'){
                            Carta carta = new Carta("10",_palo);
                            listMano[cont] = carta;
                        }
                        else{    
                            Carta numPalo = new Carta(_numero, _palo);
                            listMano[cont] = numPalo;
                        }
                        cont++;
                    }
                    i += 2;
                    
                }
                ordenarMano();
                mejorMano();
                texto = br.readLine();
            }
           
        }
        catch(FileNotFoundException ex){
            System.out.println("Error: Fichero no encontrado");
            ex.printStackTrace();
        }
    }
    public void leerMano() throws FileNotFoundException, IOException{
        String nombreFichero = ficheroEntrada;
        BufferedReader br = null;
        
        try{
            br = new BufferedReader(new FileReader(nombreFichero));
            String texto = br.readLine();
            while (texto != null){
                int cont = 1, i = 0;
                iniciarNuevo();
                while(i < texto.length()){
                    
                    int length = texto.length();
                    char numero = (char)texto.charAt(i);
                    char palo = (char) texto.charAt(i+1);
                    String  _numero = ""+numero;
                    String _palo = ""+palo;

                    if(numero == 'A'){
                        Carta carta = new Carta("1",_palo);
                        Carta cartaDos = new Carta("14",_palo);
                        listMano[cont] = carta;
                        listMano[cont+1] = cartaDos;
                        cont++;
                    }
                    else if(numero == 'K'){
                        Carta carta = new Carta("13",_palo);
                        listMano[cont] = carta;
                    }
                    else if(numero == 'Q'){
                        Carta carta = new Carta("12",_palo);
                        listMano[cont] = carta;
                    }
                    else if(numero == 'J'){
                        Carta carta = new Carta("11",_palo);
                        listMano[cont] = carta;
                    }
                    else if(numero == 'T'){
                        Carta carta = new Carta("10",_palo);
                        listMano[cont] = carta;
                    }
                    else{    
                        Carta numPalo = new Carta(_numero, _palo);
                        listMano[cont] = numPalo;
                    }

                    i += 2;
                    cont++;
                }
                ordenarMano();
                iniciarNuevo();
                mejorMano();
                texto = br.readLine();
                listMano = new Carta[14];
            }
        }
            
        catch(FileNotFoundException ex){
            System.out.println("Error: Fichero no encontrado");
            ex.printStackTrace();
        }
    }
    public void mejorMano(){
        boolean sol = false;
        iniciarNuevo();
        iniciarColor();
        iniciarMano();
        iniciarCuarTioParej();
        
        for(int i = 1; i < listMano.length-1; i++){
            if(listMano[i] != null && listMano[i].numInt() != 1){
                //Contar palos para color
                color.get(listMano[i].palo()).add(listMano[i]);
                //Contar numeros para saber lso trios,pareja,etc
                numCartas.get(listMano[i].numInt()).add(listMano[i]);
            }
            else
                break;
        }
        mano();
        if(escaleraColor()){
            sol = true;
            valorMano = 9;
            escribirFicheroBueno(ficheroSalida, manoToString(solucion, ESCALERA_COLOR));
        }
        if(!sol && cuarteto()){
            sol = true;
            valorMano = 8;
            escribirFicheroBueno(ficheroSalida, manoToString(solucion, POKER));
        }
        if(!sol && full()){
            sol = true;
            valorMano = 7;
            escribirFicheroBueno(ficheroSalida, manoToString(solucion, FULL));
        }
        if(!sol && color()){
            sol = true;
            valorMano = 6;
            escribirFicheroBueno(ficheroSalida, manoToString(solucion, FLUSH));
        }
        else if (!sol && numEnMesa < 5){
            if(drawColor()){
                drawColor = "- Draw: Flush";
            }
        }
        if(!sol && escalera()){
            sol = true;
            valorMano = 5;
            escribirFicheroBueno(ficheroSalida, manoToString(solucion, ESCALERA));
        }
        else if (!sol && numEnMesa < 5){
            if(drawGutShot()){
                drawGutShot = "- Draw: Straight Gutshot";
            }
            else if(drawOpenEnded()){
                drawOpenEnded = "- Draw: Straight OpenEnded";
            }
        }
            
        if(!sol && trio()){
            sol = true;
            valorMano = 4;
            escribirFicheroBueno(ficheroSalida, manoToString(solucion, TRIO));
        }
        if(!sol && doblePareja()){
            sol = true;
            valorMano = 3;
            escribirFicheroBueno(ficheroSalida, manoToString(solucion, DOBLE_PAREJA));
        }
        if(!sol && pareja()){
            sol = true;
            valorMano = 2;
            escribirFicheroBueno(ficheroSalida, manoToString(solucion, PAREJA));
        }
        if(!sol){
           cartaAlta();
           valorMano = 1;
           escribirFicheroBueno(ficheroSalida, manoToString(solucion, CARTA_ALTA));
        }
            
            
        
            
    }
    public void mejorManoJugadores(){
        boolean sol = false;
        iniciarNuevo();
        iniciarColor();
        iniciarMano();
        iniciarCuarTioParej();
        
        for(int i = 1; i < listMano.length-1; i++){
            if(listMano[i] != null && listMano[i].numInt() != 1){
                //Contar palos para color
                color.get(listMano[i].palo()).add(listMano[i]);
                //Contar numeros para saber lso trios,pareja,etc
                numCartas.get(listMano[i].numInt()).add(listMano[i]);
            }
            else
                break;
        }
        mano();
        if(escaleraColor()){
            sol = true;
            valorMano = 9;
            manoToStringJugadores(ESCALERA_COLOR);
        }
        if(!sol && cuarteto()){
            sol = true;
            valorMano = 8;
            kicker(1);
            manoToStringJugadores(POKER);
        }
        if(!sol && full()){
            sol = true;
            valorMano = 7;
            manoToStringJugadores(FULL);
        }
        if(!sol && color()){
            sol = true;
            valorMano = 6;
            manoToStringJugadores(FLUSH);
        }
        if(!sol && escalera()){
            sol = true;
            valorMano = 5;
            manoToStringJugadores(ESCALERA);
        }
            
        if(!sol && trio()){
            sol = true;
            valorMano = 4;
            kicker(2);
            manoToStringJugadores(TRIO);
        }
        if(!sol && doblePareja()){
            sol = true;
            valorMano = 3;
            kicker(1);
            manoToStringJugadores(DOBLE_PAREJA);
        }
        if(!sol && pareja()){
            sol = true;
            valorMano = 2;
            kicker(3);
            manoToStringJugadores(PAREJA);
        }
        if(!sol){
           cartaAlta();
           valorMano = 1;
           kicker(4);
           manoToStringJugadores(CARTA_ALTA);
        }
            
            
        
            
    }
    public boolean drawColor(){
        
        if(color.get("h").size() == 4){
            return true;                
        }
        else if(color.get("c").size() == 4){
            return true;
        }
        else if(color.get("d").size() == 4){
            return true;
        }
        else if(color.get("s").size() == 4){
            return true;
        }
        
        return false;
    }
    public void mano(){
        for(int i = 14; i > 0; i--){
            ArrayList<Carta> aux = numCartas.get(i);
            if(aux.size() == 4 && aux.get(0).numInt() != 1)
                cuarTioParej.put(4, aux);
            else if(aux.size() == 3 && aux.get(0).numInt() != 1)
                cuarTioParej.put(3, aux);
            else if(aux.size() == 2 && aux.get(0).numInt() != 1){
                if(cuarTioParej.get(2) != null){
                     ArrayList<Carta> auxiliar = cuarTioParej.get(2);
                     auxiliar.add(aux.get(0));
                     auxiliar.add(aux.get(1));
                }
                else
                    cuarTioParej.put(2, aux);
            }
                
        }
    }
    public void iniciarColor(){
        color.put("h", new ArrayList<>());
        color.put("c", new ArrayList<>());
        color.put("d", new ArrayList<>());
        color.put("s", new ArrayList<>());
        
    }
    public void iniciarMano(){
        for(int i = 1; i < 15; i++)
            numCartas.put(i, new ArrayList<>());
    }
    public void iniciarCuarTioParej(){
        cuarTioParej.put(2, new ArrayList<>());
        cuarTioParej.put(3, new ArrayList<>());
        cuarTioParej.put(4, new ArrayList<>());
    }
    
    public void ordenarMano(){
    boolean sorted = false;
        Carta temp;
        while(!sorted) {
            sorted = true;
            for (int i = 1; i < listMano.length - 1; i++) {
                if(listMano[i]==null || listMano[i+1]==null)
                    break;
                if (listMano[i].numInt() < listMano[i+1].numInt()) {
                    temp = listMano[i];
                    listMano[i] = listMano[i+1];
                    listMano[i+1] = temp;
                    sorted = false;
                }
            }
        }
        
    }
    public boolean escaleraColor(){
        int cont = 1;
        solucion = new ArrayList<>();
        solucion.add(listMano[1]);
        
        for(int i = 1; i < listMano.length-1; i++){
            if((listMano[i] != null) && (listMano[i+1] != null) && (listMano[i].numInt() == listMano[i+1].numInt()+1) && (listMano[i].palo() == listMano[i+1].palo())){
                cont++;
                solucion.add(listMano[i+1]);
            }
            else{
                cont = 1;
                solucion = new ArrayList<>();
                solucion.add(listMano[i+1]);
            }
            
            if(cont == 5)
                break;
        }
        
        if(cont == 5)
            return true;
        else
            return false;
    }
    public boolean cuarteto(){
        if (cuarTioParej.get(4).size() == 4){
            solucion = cuarTioParej.get(4);
            return true;
        }
        return false;
    }
    public boolean full(){
        if (cuarTioParej.get(3).size() == 3 && cuarTioParej.get(2).size() == 2){
            solucion = cuarTioParej.get(3);
            solucion.add(cuarTioParej.get(2).get(0));
            solucion.add(cuarTioParej.get(2).get(1));
            return true;
        }
        return false;
    }
    public boolean color(){
        if(color.get("h").size() == 5){
            solucion = color.get("h");
            return true;
        }
        else if(color.get("c").size() == 5){
            solucion = color.get("c");
            return true;
        }
        else if(color.get("s").size() == 5){
            solucion = color.get("s");
            return true;
        }
        else if(color.get("d").size() == 5){
            solucion = color.get("d");
            return true;
        }
        
        return false;
    }
    public boolean escalera(){
    int cont = 1;
        solucion = new ArrayList<>();
        solucion.add(listMano[1]);
        
        for(int i = 1; i < listMano.length-1; i++){
            if((listMano[i] != null) && (listMano[i+1] != null) &&(listMano[i].numInt() == listMano[i+1].numInt()+1)){
                cont++;
                solucion.add(listMano[i+1]);
            }
            else{
                cont = 1;
                solucion = new ArrayList<>();
                solucion.add(listMano[i+1]);
            }
            
            if(cont == 5)
                break;
        }
        
        if(cont == 5)
            return true;
        else
            return false;
    }
    public boolean trio(){
        if (cuarTioParej.get(3).size() == 3){
            solucion = cuarTioParej.get(3);
            return true;
        }
        return false;
    }
    public boolean doblePareja(){
        if (cuarTioParej.get(2).size() == 4){
            solucion = cuarTioParej.get(2);
            return true;
        }
        return false;
    }
    public boolean pareja(){
        if (cuarTioParej.get(2).size() == 2){
            solucion = cuarTioParej.get(2);
            return true;
        }
        return false;
    }
    public boolean cartaAlta(){
        solucion.add(0, listMano[1]);
        return true;
    }
    public String manoToString(ArrayList<Carta> mano, String jugada){
        String sol = manoInicialToString() + "\n" + "- Best hand: " + jugada + " (";
        
        for(int i = 0; i < mano.size() && mano.get(i) != null; i++){
            if(mano.get(i).numInt() >= 10){
                sol+=numeroToLetra(mano.get(i).numInt());
            }
            else{
                sol += mano.get(i).numero();
            }
            sol += mano.get(i).palo();
        }
        sol += ") \n";
        
        if(drawOpenEnded != "")
            sol+=drawOpenEnded +"\n";
        if(drawGutShot != "")
            sol+=drawGutShot + "\n";
        if(drawColor != "")
            sol+=drawColor + "\n";
        
        return sol;
    }
    public boolean drawOpenEnded(){
        int cont = 1;
        
        for(int i = 1; i < listMano.length-1; i++){
            if((listMano[i] != null) && (listMano[i+1] != null) &&(listMano[i].numInt() == listMano[i+1].numInt()+1)){
                cont++;
            }
            else if(listMano[i]==null || listMano[i+1]==null)
                break;
            else{
                cont = 1;
            }
            
            if(cont == 4)
                break;
        }
        
        if(cont == 4)
            return true;
        else
            return false;
    }
    public boolean drawGutShot(){
        int cont = 1, aux = 0;
        
        for(int i = 1; i < listMano.length-1; i++){
            if((listMano[i] != null) && (listMano[i+1] != null) &&(listMano[i].numInt() == listMano[i+1].numInt()+1)){
                cont++;
            }
            else{
                if(listMano[i]==null || listMano[i+1]==null)
                    break;
                else if(aux == 0 && ((listMano[i].numInt()-listMano[i+1].numInt()) == 2)){
                    cont++;
                    aux++;
                }
                else{
                    cont = 1;
                    aux = 0;
                }
            }
            
            if(cont == 4 && aux == 1)
                break;
        }
                
        if(cont == 4 && aux == 1)
            return true;
        else
            return false;
    }
    public String manoInicialToString(){
        String sol = "";
        int i = 1;
        while (listMano[i] != null && listMano[i].numInt() != 1){
            if(listMano[i].numInt() >= 10){
                sol+=numeroToLetra(listMano[i].numInt());
            }
            else
                sol+=listMano[i].numero();
            
            sol+=listMano[i].palo();
            i++;
        }
        return sol;
    }
    public String numeroToLetra(int numero){
        if(numero == 10)
            return "T";
        else if(numero == 11)
            return "J";
        else if(numero == 12)
            return "Q";
        else if(numero == 13)
            return "K";
        else if(numero == 14)
            return "A";
        return "";
    }
    public void anadirAMano(String texto, int ini, int fin){
        int i = ini;
        while(i < fin){
            char numero = (char)texto.charAt(i);
                char palo = (char) texto.charAt(i+1);
                String  _numero = ""+numero;
                String _palo = ""+palo;
                
                if(numero == 'A'){
                    Carta carta = new Carta("1",_palo);
                    Carta cartaDos = new Carta("14",_palo);
                    listMano[cont] = carta;
                    listMano[cont+1] = cartaDos;
                    cont++;
                }
                else if(numero == 'K'){
                    Carta carta = new Carta("13",_palo);
                    listMano[cont] = carta;
                }
                else if(numero == 'Q'){
                    Carta carta = new Carta("12",_palo);
                    listMano[cont] = carta;
                }
                else if(numero == 'J'){
                    Carta carta = new Carta("11",_palo);
                    listMano[cont] = carta;
                }
                else if(numero == 'T'){
                    Carta carta = new Carta("10",_palo);
                    listMano[cont] = carta;
                }
                else{    
                    Carta numPalo = new Carta(_numero, _palo);
                    listMano[cont] = numPalo;
                }
                
                i += 2;
                cont++;
        }
    }    
    public void escribirFicheroBueno(String ficheroS, String sol){
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            String data = sol;
            File file = new File(ficheroS);
            // Si el archivo no existe, se crea!
            if (!file.exists()) {
                file.createNewFile();
            }
            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(data+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                            //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void kicker(int completar){
        int i = 0;
        int j = 1;
        while(i < completar){
            if(listMano[j].numero() != solucion.get(0).numero()){
                solucion.add(listMano[j]);
                ++i;
            }
            ++j;
        }
    }
    public void manoToStringJugadores(String jugada){
        for(int i = 0; i < solucion.size() && solucion.get(i) != null; i++){
            if(solucion.get(i).numInt() >= 10){
                solucionString+=numeroToLetra(solucion.get(i).numInt());
            }
            else{
                solucionString += solucion.get(i).numero();
            }
            solucionString += solucion.get(i).palo();
        }
        solucionString += " ("+jugada+")";
    }
}
       


