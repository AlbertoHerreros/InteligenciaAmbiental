# Inteligencia Ambiental - Empresa de reparto usando LEGO

La aplicación interfaz encargada de la comunicación **MQTT** con el robot de reparto está escrita en Java, usando el sdk 17. La interfaz de usuario se ha desarrollado por medio de las librerías de Java Swing. La comunicación MQTT se ha realizado mediante el uso de la librería [paho](https://github.com/eclipse/paho.mqtt.java).

## Guía de Uso

Para utilizar la aplicación será necesario disponer del sdk 17 de Java en primera instancia. Es recomendable que si se desean cambiar parámetros como la ip del broker MQTT, el nombre del cliente MQTT o el topic de recepción de mensajes se edite el archivo constantes, ubicado en la siguiente ruta dentro del proyecto: `src/main/java/es/uja/iambiental/curso2324/utils/Constantes.java`

Allí podremos observar aquellas variables relativas a lo anteriormente comentado:
```
public interface Constantes {
    ...
    String BROKER_IP = "tcp://192.168.0.105:1883";
    String CLIENT_ID = "GRUPO_S";
    String TOPIC_RECIBIR_MAPA = "map";
    String TOPIC_RECIBIR_POSICION = "posicion";
    String TOPIC_PUBLICAR_VIAJE = "GrupoS/viaje";
    ...
}
```
Para ponerla en marcha se observan varias alternativas:
- Utilizar Netbeans 14 para la ejecución del proyecto contenido en el repositorio. Será necesario abrir el proyecto con dicho software, tener configurado el lenguaje de programación Java así como la variable **JAVA_HOME** en las variables del entorno del sistema haciendo referencia al sdk 17.x y pulsar el botón de ejecución.
- Utilizar el archivo .jar para la ejecución. Para ello se facilita un archivo con extensión **.sh** para su puesta en marcha desde el sistema operativo Windows. 

## Intefaz de Usuario
Una vez la aplicación reciba por MQTT por medio del topic `TOPIC_RECIBIR_MAPA` la cadena de texto relativa a la estructura del mapa de dimensiones 7x5, la aplicación abrirá una interfaz para comenzar a comunicarse con el robot de reparto por medio del topic `TOPIC_PUBLICAR_VIAJE`. 

![](https://github.com/AlbertoHerreros/InteligenciaAmbiental/blob/main/img/interfaz.png?raw=true)

Para marcar la ubicación de un paquete, se hará click izquierdo sobre una casilla que sea punto de entrega. Para marcar un destino se hará click derecho. Una vez tengamos ambos puntos marcados, bastará con pulsar el botón de confirmar y se hará el reparto.

![](https://github.com/AlbertoHerreros/InteligenciaAmbiental/blob/main/img/interfaz2.png?raw=true)