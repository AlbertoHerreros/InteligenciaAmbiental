/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uja.iambiental.curso2324.utils;

import es.uja.iambiental.curso2324.utils.Constantes.TipoImagen;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Alberto Limas
 */
public final class ImageLoader {
    /**
     * Devuelve una imagen acorde a la casilla correspondiente
     * @param img : número de la imagen correspondiente al tipo de pieza del tablero
     * @return BufferedImage de la casilla
     * @throws IOException 
     */
    public static BufferedImage getImagenDefecto(String img) throws IOException {
        BufferedImage image = ImageIO.read(new File("./img/casilla/"+img+".png"));
        return image;
    }
    
    public static BufferedImage getImagenDinámica(String img, TipoImagen tipo) throws IOException{
        BufferedImage image;
        
        switch (tipo) {
            case PAQUETE -> image = ImageIO.read(new File("./img/paquete/"+img+"_paquete.png"));
            case ENTREGA -> image = ImageIO.read(new File("./img/entrega/"+img+"_entrega.png"));
            case CAMION -> image = ImageIO.read(new File("./img/camion/"+img+"_camion.png"));
            default -> throw new AssertionError();
        }
        return image;
    }
    
}
