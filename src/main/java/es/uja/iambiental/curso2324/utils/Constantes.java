/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uja.iambiental.curso2324.utils;

import java.util.Random;

/**
 *
 * @author Alberto Limas
 */
public interface Constantes {
    int NFILAS = 7;
    int NCOLUMNAS = 5;
    //String BROKER_IP = "tcp://192.168.0.105:1883";
    String BROKER_IP = "tcp://192.168.0.152:1883";

    String CLIENT_ID = "GRUPO_S";
    String TOPIC_RECIBIR_MAPA = "map";
    String TOPIC_RECIBIR_POSICION = "GrupoS/posicion";
    String TOPIC_PUBLICAR_VIAJE = "GrupoS/viaje";
    String TOPIC_RECIBIR_CONFIRMACION = "GrupoS/confirmarViaje";
    int QOS = 1;
    enum TipoImagen {
        PAQUETE, ENTREGA, CAMION;
    }
    Random aleatorio = new Random();
}
