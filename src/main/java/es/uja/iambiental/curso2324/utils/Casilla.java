/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uja.iambiental.curso2324.utils;
/**
 * Clase que representa una casilla del tablero de reparto
 * @author Alberto Limas
 */
public class Casilla {

    private final boolean norte, sur, este, oeste;

    /**
     * Constructor de casilla
     * @param codigoPieza : código que representa la pieza, depende del mismo
     * la forma de la pieza.
     */
    public Casilla(String codigoPieza) {
        switch (codigoPieza) {
            case "00":
                this.norte = this.sur = this.este = this.oeste = false;
                break;
            case "01":
                this.norte = this.sur = false;
                this.este = this.oeste = true;
                break;
            case "02":
                this.este = this.oeste = false;
                this.norte = this.sur = true;
                break;
            case "03":
                this.sur = this.oeste = false;
                this.norte = this.este = true;
                break;
            case "04":
                this.oeste = this.norte = false;
                this.sur = this.este = true;
                break;
            case "05":
                this.norte = this.este = false;
                this.sur = this.oeste = true;
                break;
            case "06":
                this.sur = this.este = false;
                this.norte = this.oeste = true;
                break;
            case "07":
                this.sur = false;
                this.norte = this.este = this.oeste = true;
                break;
            case "08":
                this.oeste = false;
                this.norte = this.sur = this.este = true;
                break;
            case "09":
                this.norte = false;
                this.sur = this.este = this.oeste = true;
                break;
            case "10":
                this.este = false;
                this.norte = this.sur = this.oeste = true;
                break;
            case "11":
                this.norte = this.sur = this.este = this.oeste = true;
                break;
            default:
                System.out.println("EL CÓDIGO DE PIEZA NO EXISTE : " + codigoPieza);
                norte=sur=oeste=este=false;
        }
    }
    
    /**
     * Método que comprueba dada una casilla, si se encuentra conectada a otra
     * @param otra
     * @param posX: -1 si está a la izq otra respecto a casilla, 1 si está derecha de casilla, 0 en otro caso
     * @param posY: -1 debajo, +1 arriba, 0 otro caso
     * @return conectadas
     */
    public boolean comprobarConectadas(Casilla otra, int posX, int posY){
        if(posX == -1){
            return this.oeste && otra.este;
        }
        if(posX == 1){
            return this.este && otra.oeste;
        }
        if(posY == -1){
            return this.sur && otra.norte;
        }
        if(posY == 1){
            return this.norte && otra.sur;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Casilla{" + "norte=" + norte + ", sur=" + sur + ", este=" + este + ", oeste=" + oeste + '}';
    }

}
