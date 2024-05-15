/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uja.iambiental.curso2324.gui;

import es.uja.iambiental.curso2324.utils.Casilla;
import es.uja.iambiental.curso2324.utils.Constantes;
import static es.uja.iambiental.curso2324.utils.Constantes.NCOLUMNAS;
import static es.uja.iambiental.curso2324.utils.Constantes.NFILAS;
import es.uja.iambiental.curso2324.utils.ImageLoader;
import es.uja.iambiental.curso2324.utils.pair;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Alberto Limas
 */
public class Ciudad2 extends JPanel {

    private final String codigoCiudad;
    private final int[][] matrizPiezas;
    private final ArrayList<pair> listaDestinos;
    private final ArrayList<pair> listaInicios;

    private final Casilla[][] matrizCasillas;
    private final Boolean[][] matrizPuntosRecogida; //booleana, 1 si se puede, 0 si no
    private int xPaquete, xDestino, yPaquete, yDestino, xCamion, yCamion;

    //Para alg Floyd
    private int[][] matrizAdyacencia;
    private int[][] matrizD;
    private int[][] matrizP;

    public int[][] getMatrizP() {
        return matrizP;
    }

    /**
     * Creates new form Ciudad
     *
     * @param codigoCiudad
     * @throws java.io.IOException
     */
    public Ciudad2(String codigoCiudad) throws IOException {
        this.setLayout(new GridBagLayout());
        xPaquete = xDestino = yPaquete = yDestino = -1;
        xCamion = 6;
        yCamion = 0;
        this.codigoCiudad = codigoCiudad;
        this.matrizPiezas = new int[NFILAS][NCOLUMNAS];
        this.matrizCasillas = new Casilla[NFILAS][NCOLUMNAS];
        this.matrizPuntosRecogida = new Boolean[NFILAS][NCOLUMNAS];
        this.setSize(1000, 800);
        crearCiudad();
        determinarPuntosRecogida();
        floyd();
        listaDestinos = new ArrayList<>();
        listaInicios = new ArrayList<>();

    }

    /**
     * Algoritmo de floyd para calcular todos los caminos posibles entre
     * vértices del grafo, es decir, entre pares de casillas
     */
    private void floyd() {
        int NFC = NFILAS * NCOLUMNAS;

        matrizAdyacencia = new int[NFC][NFC];
        matrizD = new int[NFC][NFC];
        matrizP = new int[NFC][NFC];

        //Calcular la matriz de adyacencia NFC * NFC para ver
        //las conexiones existentes entre casillas
        for (int i = 0; i < NFILAS; i++) {
            //Comprobar conexiones con casillas próximas a ella
            for (int j = 0; j < NCOLUMNAS; j++) {
                int posActual = i * 5 + j;
                int posOtra;
                if (j - 1 >= 0) {
                    if (matrizCasillas[i][j].comprobarConectadas(matrizCasillas[i][j - 1], -1, 0)) {
                        posOtra = i * 5 + (j - 1);
                        matrizAdyacencia[posActual][posOtra] = 1;
                        matrizAdyacencia[posOtra][posActual] = 1;
                    }
                }
                if (j + 1 < NCOLUMNAS) {
                    if (matrizCasillas[i][j].comprobarConectadas(matrizCasillas[i][j + 1], 1, 0)) {
                        posOtra = i * 5 + (j + 1);
                        matrizAdyacencia[posActual][posOtra] = 1;
                        matrizAdyacencia[posOtra][posActual] = 1;
                    }
                }
                if (i - 1 >= 0) {
                    if (matrizCasillas[i][j].comprobarConectadas(matrizCasillas[i - 1][j], 0, 1)) {
                        posOtra = (i - 1) * 5 + j;
                        matrizAdyacencia[posActual][posOtra] = 1;
                        matrizAdyacencia[posOtra][posActual] = 1;
                    }
                }
                if (i + 1 < NFILAS) {
                    if (matrizCasillas[i][j].comprobarConectadas(matrizCasillas[i + 1][j], 0, -1)) {
                        posOtra = (i + 1) * 5 + j;
                        matrizAdyacencia[posActual][posOtra] = 1;
                        matrizAdyacencia[posOtra][posActual] = 1;
                    }
                }
            }
        }

        /*      for (int i = 0; i < NFC; i++) {
            for (int j = 0; j < NFC; j++) {
                System.out.print(matrizAdyacencia[i][j] + ",");
            }
            System.out.println("");
        }
         */
        for (int i = 0; i < NFC; i++) {
            for (int j = 0; j < NFC; j++) {
                if (i == j) {
                    matrizD[i][j] = 0;
                } else if (matrizAdyacencia[i][j] == 0) {
                    matrizD[i][j] = 1000;
                } else {
                    matrizD[i][j] = matrizAdyacencia[i][j];
                }

            }
        }

        for (int k = 0; k < NFC; k++) {
            for (int i = 0; i < NFC; i++) {
                for (int j = 0; j < NFC; j++) {
                    if (matrizD[i][k] + matrizD[k][j] < matrizD[i][j]) {
                        matrizD[i][j] = matrizD[i][k] + matrizD[k][j];
                        matrizP[i][j] = k;
                    }
                }
            }
        }
        /*System.out.println("--------------------------------------");
        for (int i = 0; i < NFC; i++) {
            for (int j = 0; j < NFC; j++) {
                System.out.print(matrizP[i][j]+",");
            }
            System.out.println("");
        }*/
    }

    /**
     *
     * @throws IOException
     */
    private void crearCiudad() throws IOException {

        this.removeAll();
        this.repaint();

        //Título de la ventana
        //this.setTitle("Entrega número " + nPedido);
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

                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setSize(button.getSize().width - 5, button.getSize().height - 5);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setSize(button.getSize().width + 5, button.getSize().height + 5);
                }

                @Override
                public void mouseClicked(MouseEvent e) {

                    if (e.getButton() == MouseEvent.NOBUTTON) {

                    }
                    //¿Es un punto de recogida?
                    JButton comprobar = (JButton) e.getSource();
                    int x = Integer.parseInt(comprobar.getName().split(",")[0]);
                    int y = Integer.parseInt(comprobar.getName().split(",")[1]);
                    if (matrizPuntosRecogida[x][y]) {
                        if (e.getButton() == MouseEvent.BUTTON1) {

                            Component[] c = getComponents();

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
                            try {
                                imagenPulsada = ImageLoader.getImagenDinámica(
                                        button.getName().split(",")[2],
                                        Constantes.TipoImagen.PAQUETE
                                );
                            } catch (IOException ex) {
                                Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            button.setIcon(new ImageIcon(imagenPulsada));
                            button.repaint();

                            System.out.println("\nhas presionado el botón(1) " + button.getName());

                            xPaquete = Integer.parseInt(button.getName().split(",")[0]);
                            yPaquete = Integer.parseInt(button.getName().split(",")[1]);
                            System.out.println(c[xPaquete * 5 + yPaquete].getName());
                            System.out.println(matrizCasillas[xPaquete][yPaquete]);

                        } else if (e.getButton() == MouseEvent.BUTTON3) {

                            Component[] c = getComponents();
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
                            try {
                                imagenPulsada = ImageLoader.getImagenDinámica(
                                        button.getName().split(",")[2],
                                        Constantes.TipoImagen.ENTREGA
                                );
                            } catch (IOException ex) {
                                Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            button.setIcon(new ImageIcon(imagenPulsada));
                            button.repaint();
                            System.out.println("\nhas presionado el botón(3) " + button.getName());

                            xDestino = Integer.parseInt(button.getName().split(",")[0]);
                            yDestino = Integer.parseInt(button.getName().split(",")[1]);
                            System.out.println(c[xDestino * 5 + yDestino].getName());
                            System.out.println(matrizCasillas[xDestino][yDestino]);
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

    public String confirmarPuntos() {

        if (xPaquete != -1 && yPaquete != -1 && xDestino != -1 && yDestino != -1) {
            listaInicios.add(new pair(xPaquete, yPaquete));
            listaDestinos.add(new pair(xDestino, yDestino));
            //return "(" + xPaquete + "," + yPaquete + ") ---[DESTINO]---> " + "(" + xDestino + "," + yDestino + ")";
            return "" + xPaquete + "," + yPaquete + ";" + xDestino + "," + yDestino + "";

        }
        return "";
    }

    public void pintarCamion(int x, int y) {

        if (matrizPiezas[x][y] != 0) {

            if (xCamion != x || yCamion != y) {
                //Casilla antigua -> DEFAULT
                Component c[] = this.getComponents();
                JButton anterior = (JButton) c[xCamion * 5 + yCamion];
                // System.out.println(anterior.getName());

                //Coger la imagen del paquete, de manera dinámica
                BufferedImage imagenPulsada = null;
                try {
                    imagenPulsada = ImageLoader.getImagenDefecto(anterior.getName().split(",")[2]);
                } catch (IOException ex) {
                    Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
                }
                anterior.setIcon(new ImageIcon(imagenPulsada));
                anterior.repaint();
                //Casilla nueva -> CAMIÓN
                JButton nuevo = (JButton) c[x * 5 + y];
                imagenPulsada = null;
                try {
                    imagenPulsada = ImageLoader.getImagenDinámica(
                            nuevo.getName().split(",")[2],
                            Constantes.TipoImagen.CAMION
                    );
                } catch (IOException ex) {
                    Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
                }
                nuevo.setIcon(new ImageIcon(imagenPulsada));
                nuevo.repaint();
            }
            xCamion = x;
            yCamion = y;
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Util para construir la interfaz
     *
     * @param x
     * @param y
     * @return
     */
    private static GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = y;
        gbc.gridy = x;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int gap = 0;
        gbc.insets = new Insets(gap, gap + 2 * gap * x, gap, gap);
        return gbc;
    }

    private void determinarPuntosRecogida() {
        //Inicializar matriz
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                matrizPuntosRecogida[i][j] = false;
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
                    System.out.println(i + " : " + j + " es punto recogida.");
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

    public String obtenerPosCamion() {
        return "" + xCamion + ";" + yCamion + "";
    }

    void setCoordenadasCamion(int xFin, int yFin) {
        this.xCamion = xFin;
        this.yCamion = yFin;
    }

}
