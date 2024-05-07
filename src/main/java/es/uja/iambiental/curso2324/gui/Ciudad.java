/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package es.uja.iambiental.curso2324.gui;

import es.uja.iambiental.curso2324.utils.Casilla;
import es.uja.iambiental.curso2324.utils.Constantes;
import static es.uja.iambiental.curso2324.utils.Constantes.NCOLUMNAS;
import static es.uja.iambiental.curso2324.utils.Constantes.NFILAS;
import es.uja.iambiental.curso2324.utils.pair;
import es.uja.iambiental.curso2324.utils.ImageLoader;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 *
 * @author Alberto Limas
 */
public class Ciudad extends javax.swing.JFrame {

    private int nPedido;
    private String codigoCiudad;
    private int[][] matrizPiezas;
    private int[][] matrizLaberinto;
    private ArrayList<pair> listaEntregas;

    private Casilla[][] matrizCasillas;
    private Boolean[][] matrizPuntosRecogida; //booleana, 1 si se puede, 0 si no

    private int xPaquete, xDestino, yPaquete, yDestino;
    private boolean inicio = true;

    /**
     * Creates new form Ciudad
     */
    public Ciudad(String codigoCiudad) throws IOException {
        initComponents();
        this.setLayout(new GridBagLayout());

        nPedido = 1;
        xPaquete = xDestino = yPaquete = yDestino = -1;

        this.codigoCiudad = codigoCiudad;
        this.matrizPiezas = new int[NFILAS][NCOLUMNAS];
        this.matrizLaberinto = new int[NFILAS][NCOLUMNAS];
        this.matrizCasillas = new Casilla[NFILAS][NCOLUMNAS];
        this.matrizPuntosRecogida = new Boolean[NFILAS][NCOLUMNAS];
        this.setSize(1000, 1000);
        crearCiudad();
        determinarPuntosRecogida();
        pair entrada = new pair(6, 0);
        pair paquete = new pair(0, 3);
        laberinto(entrada, paquete);
        listaEntregas = new ArrayList<>();
    }

    private void laberinto(pair entrada, pair paquete) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrizPiezas[i][j] == 0) {
                    matrizLaberinto[i][j] = 1;
                } else {
                    matrizLaberinto[i][j] = 0;
                }
            }
        }
        matrizLaberinto[entrada.getFirst()][entrada.getSecond()] = 3;//entrada
        matrizLaberinto[paquete.getFirst()][paquete.getSecond()] = 4;//salida

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrizLaberinto[i][j] + " ");
            }
            System.out.println("");

        }
    }

    void crearCiudad() throws IOException {

        this.getContentPane().removeAll();
        this.repaint();

        //Título de la ventana
        this.setTitle("Entrega número " + nPedido);
        this.setBackground(Color.black);

        int longitudTrozos = 2;

        // Calcular el número de trozos
        int numTrozos = (int) Math.ceil((double) codigoCiudad.length() / longitudTrozos);

        // Crear un array para almacenar los trozos
        String[] imagenes = new String[numTrozos];

        // Separar la cadena en trozos de longitud 2
        for (int i = 0; i < numTrozos; i++) {
            int inicio = i * longitudTrozos;
            int fin = Math.min(inicio + longitudTrozos, codigoCiudad.length());
            imagenes[i] = codigoCiudad.substring(inicio, fin);
        }
        final int[] xy = {0, 0};
        // Imprimir los trozos
        for (String codigoPieza : imagenes) {

            JButton button = new JButton();
            matrizPiezas[xy[0]][xy[1]] = Integer.parseInt(codigoPieza);
            matrizCasillas[xy[0]][xy[1]] = new Casilla(codigoPieza);
            button.setName("" + xy[0] + "," + xy[1] + "," + codigoPieza);
            BufferedImage image = ImageLoader.getImagenDefecto(codigoPieza);
            button.setIcon(new ImageIcon(image));
            button.setSize(128, 128);
            button.setBorder(null);
            button.setMargin(null);

            button.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.NOBUTTON) {

                    }
                    //Es un 
                    JButton comprobar = (JButton) e.getSource();
                    int x = Integer.parseInt(comprobar.getName().split(",")[0]);
                    int y = Integer.parseInt(comprobar.getName().split(",")[1]);
                    if(matrizPuntosRecogida[x][y]){
                                            if (e.getButton() == MouseEvent.BUTTON1) {

                        Component[] c = getContentPane().getComponents();

                        if (xPaquete != -1) {
                            JButton anterior = (JButton) c[xPaquete * 5 + yPaquete];
                            //Coger imagen por defecto
                            BufferedImage imagenDefecto = null;
                            try {
                                imagenDefecto = ImageLoader.getImagenDefecto(anterior.getName().split(",")[2]);
                            } catch (IOException ex) {
                                Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            anterior.setIcon(new ImageIcon(imagenDefecto));
                            anterior.repaint();
                        }

                        //Coger la imagen del paquete, de manera dinámica
                        BufferedImage imagenPulsada = null;
                        JButton pressedButton = (JButton) e.getSource();
                        try {
                            imagenPulsada = ImageLoader.getImagenDinámica(
                                    pressedButton.getName().split(",")[2],
                                    Constantes.TipoImagen.PAQUETE
                            );
                        } catch (IOException ex) {
                            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        pressedButton.setIcon(new ImageIcon(imagenPulsada));
                        pressedButton.repaint();

                        System.out.println("has presionado el botón(1) " + pressedButton.getName());

                        xPaquete = Integer.parseInt(pressedButton.getName().split(",")[0]);
                        yPaquete = Integer.parseInt(pressedButton.getName().split(",")[1]);
                        System.out.println(c[xPaquete * 5 + yPaquete].getName());
                        System.out.println(matrizCasillas[xPaquete][yPaquete]);

                    } else if (e.getButton() == MouseEvent.BUTTON3) {

                        Component[] c = getContentPane().getComponents();
                        if (xDestino != -1) {
                            JButton anterior = (JButton) c[xDestino * 5 + yDestino];
                            //Coger imagen por defecto
                            BufferedImage imagenDefecto = null;
                            try {
                                imagenDefecto = ImageLoader.getImagenDefecto(anterior.getName().split(",")[2]);
                            } catch (IOException ex) {
                                Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            anterior.setIcon(new ImageIcon(imagenDefecto));
                            anterior.repaint();
                        }

                        //Coger la imagen del paquete, de manera dinámica
                        BufferedImage imagenPulsada = null;
                        JButton pressedButton = (JButton) e.getSource();

                        try {
                            imagenPulsada = ImageLoader.getImagenDinámica(
                                    pressedButton.getName().split(",")[2],
                                    Constantes.TipoImagen.ENTREGA
                            );
                        } catch (IOException ex) {
                            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        pressedButton.setIcon(new ImageIcon(imagenPulsada));
                        pressedButton.repaint();
                        System.out.println("has presionado el botón(3) " + pressedButton.getName());

                        xDestino = Integer.parseInt(pressedButton.getName().split(",")[0]);
                        yDestino = Integer.parseInt(pressedButton.getName().split(",")[1]);
                        System.out.println(c[xDestino * 5 + yDestino].getName());

                    }
                    }
                    

                }

            });

            //BufferedImage imageRollover = ImageLoader.getImagenDefecto(img+"_rollover");
            //System.out.println(img+"_rollover");
            //button.setRolloverIcon(new ImageIcon(imageRollover));
            this.add(button, createGbc(xy[0], xy[1]));
            xy[1]++;
            if (xy[1] == 5) {
                xy[0]++;
                xy[1] = 0;
            }

        }

    }

    private static GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = y;
        gbc.gridy = x;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int gap = 0;
        gbc.insets = new Insets(gap, gap + 2 * gap * x, gap, gap);
        return gbc;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void determinarPuntosRecogida() {
        //Inicializar matriz
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                matrizPuntosRecogida[i][j]=false;
            }
        }
        int cont = 0;
        for (int i = 0; i < NFILAS; i++) {
            //Comprobar conexiones con casillas próximas a ella
            for (int j = 0; j < NCOLUMNAS; j++) {
                cont = 0;
                if (j - 1 >= 0) {
                    if (matrizCasillas[i][j].comprobarConectadas(matrizCasillas[i][j - 1], -1, 0)) {
                        cont++;
                    }
                }
                if (j + 1 < NCOLUMNAS) {
                    if (matrizCasillas[i][j].comprobarConectadas(matrizCasillas[i][j + 1], 1, 0)) {
                        cont++;
                    }
                }
                if (i - 1 >= 0) {
                    if (matrizCasillas[i][j].comprobarConectadas(matrizCasillas[i - 1][j], 0, 1)) {
                        cont++;
                    }
                }
                if (i + 1 < NFILAS) {
                    if (matrizCasillas[i][j].comprobarConectadas(matrizCasillas[i + 1][j], 0, -1)) {
                        cont++;
                    }
                }
                //Registrar la casilla
                if (cont == 1) {
                    matrizPuntosRecogida[i][j] = true;
                    System.out.println(i+" : "+j+" es punto recogida.");
                } else {
                    matrizPuntosRecogida[i][j] = false;
                }

            }

        }
        System.out.println("MATRIZ DE PTOS RECOGIDA");
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrizPuntosRecogida[i][j] + " ");
            }
            System.out.println("");

        }
        

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
