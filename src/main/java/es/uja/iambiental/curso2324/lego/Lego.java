/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package es.uja.iambiental.curso2324.lego;

import es.uja.iambiental.curso2324.gui.Ciudad;
import es.uja.iambiental.curso2324.gui.Ciudad2;
import es.uja.iambiental.curso2324.gui.Interfaz;
import es.uja.iambiental.curso2324.gui.ListaEntregas;
import static es.uja.iambiental.curso2324.utils.Constantes.BROKER_IP;
import static es.uja.iambiental.curso2324.utils.Constantes.CLIENT_ID;
import static es.uja.iambiental.curso2324.utils.Constantes.QOS;
import static es.uja.iambiental.curso2324.utils.Constantes.TOPIC_RECIBIR_MAPA;
import static es.uja.iambiental.curso2324.utils.Constantes.TOPIC_RECIBIR_POSICION;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Alberto Limas
 */
public class Lego {

    public static void main(String[] args) throws IOException, MqttException, InterruptedException {
        String codigoCiudad = "0202000105"
                + "0307050002"
                + "0004110906"
                + "0110031000"
                + "0002000801"
                + "0110011000"
                + "0106010701";
        Ciudad2 ciudad = new Ciudad2(codigoCiudad);
        Interfaz interfaz = new Interfaz(ciudad);
        interfaz.setVisible(true);
        ciudad.setVisible(true);
        Random aleatorio = new Random();
        while(true){
            Thread.sleep(1);
            //interfaz.camionero(aleatorio.nextInt(7), aleatorio.nextInt(5));
        }
/*
        System.out.println("HA INICIADO LA EJECUCIÓN");
        Interfaz[] interfaz = {null};
        boolean[] mapaCreado = {false};
        String[] mapaCiudad = {""};
        Ciudad2[] ciudad = {null};

        System.out.println("CONEXIÓN MQTT...");
        MqttClient client = new MqttClient(BROKER_IP, CLIENT_ID);
        MqttConnectOptions options = new MqttConnectOptions();
        client.connect(options);

        client.setCallback(new MqttCallback() {
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("topic: " + topic);
                System.out.println("qos: " + message.getQos());
                System.out.println("message content: " + new String(message.getPayload()));

                if (topic.equals(TOPIC_RECIBIR_MAPA)) {
                    if (!mapaCreado[0]) {
                        System.out.println("RECIBE MAPA");
                        mapaCiudad[0] = new String(message.getPayload());
                        ciudad[0] = new Ciudad2(mapaCiudad[0]);
                        ciudad[0].setVisible(true);
                        mapaCreado[0] = true;
                        interfaz[0] = new Interfaz(ciudad[0]);
                        interfaz[0].setVisible(true);
                        interfaz[0].setCliente(client);
                    }
                } else if (topic.equals(TOPIC_RECIBIR_POSICION)) {
                    System.out.println("RECIBE POSICIÓN");
                }

            }

            public void connectionLost(Throwable cause) {
                System.out.println("connectionLost: " + cause.getMessage());
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("deliveryComplete: " + token.isComplete());
            }
        });

        client.subscribe(TOPIC_RECIBIR_MAPA, QOS);
        client.subscribe(TOPIC_RECIBIR_POSICION, QOS);
*/
        //client.disconnect();
        //client.close();
    }
}
