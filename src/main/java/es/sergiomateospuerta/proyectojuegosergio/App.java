/**
* Juego realizado en JavaFX con Maven
* El objetivo trata de un avion que tiene que coger monedas
* mientras esquiva misiles, con cada moneda cogida la
* dificultad del juego aumenta.
* 
* @author  Sergio Mateos
* @version 1.0
* @since   2022-02-04
*/

package es.sergiomateospuerta.proyectojuegosergio;

//////////////////////////////////////////////////////////////
///////////////     IMPORTACIÓN DE PAQUETES    ///////////////
//////////////////////////////////////////////////////////////

import java.io.BufferedReader;
import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import javafx.scene.media.AudioClip;

public class App extends Application {
  
    Pane root = new Pane(); // PANEL PRINCIPAL QUE CONTENDRÁ LOS ELEMENTOS DE LA PANTALLA
    ImageView fondo1; // AÑADIMOS EL OBJETO FONDO1
    ImageView fondo2; // AÑADIMOS EL OBJETO FONDO2
    ImageView avion; // AÑADIMOS EL OBJETO AVION
    Rectangle rectAvion = new Rectangle(53, 33); //CREAMOS UN RECTANGULO PARA EL OBJETO AVION CON LAS MISMAS DIMENSIONES
    Group groupAvion = new Group();
    ImageView misil1; // AÑADIMOS EL OBJETO MISIL1
    Rectangle rectMisil1 = new Rectangle(53, 33); //CREAMOS UN RECTANGULO PARA EL OBJETO AVION CON LAS MISMAS DIMENSIONES
    Group groupMisil1 = new Group(); // CREAMOS UN GRUPO AL QUE POSTERIORMENTE AÑADIREMOS EL OBJETO(LA IMAGEN QUE SE VE EN PANTALLA) Y EL RECTANGULO
    ImageView misil2; // AÑADIMOS EL OBJETO MISIL2
    Rectangle rectMisil2 = new Rectangle(53, 33); //CREAMOS UN RECTANGULO PARA EL OBJETO AVION CON LAS MISMAS DIMENSIONES
    Group groupMisil2 = new Group(); // CREAMOS UN GRUPO AL QUE POSTERIORMENTE AÑADIREMOS EL OBJETO(LA IMAGEN QUE SE VE EN PANTALLA) Y EL RECTANGULO
    ImageView misil3; // AÑADIMOS EL OBJETO MISIL3
    Rectangle rectMisil3 = new Rectangle(53, 33); //CREAMOS UN RECTANGULO PARA EL OBJETO AVION CON LAS MISMAS DIMENSIONES
    Group groupMisil3 = new Group(); // CREAMOS UN GRUPO AL QUE POSTERIORMENTE AÑADIREMOS EL OBJETO(LA IMAGEN QUE SE VE EN PANTALLA) Y EL RECTANGULO
    ImageView coin; // AÑADIMOS EL OBJETO COIN
    Rectangle rectCoin = new Rectangle(53, 33); //CREAMOS UN RECTANGULO PARA EL OBJETO AVION CON LAS MISMAS DIMENSIONES
    Group groupCoin = new Group(); // CREAMOS UN GRUPO AL QUE POSTERIORMENTE AÑADIREMOS EL OBJETO(LA IMAGEN QUE SE VE EN PANTALLA) Y EL RECTANGULO
    ImageView explosion; // AÑADIMOS EL OBJETO EXPLOSION QUE USAREMOS PARA LA ANIMACION AL SER ELIMINADO (CUANDO TE CHOCAS CON UN MISIL)
    ImageView note; // AÑADIMOS EL OBJETO NOTE
    Label labelPuntos; // AÑADIMOS EL OBJETO LABELPUNTOS
    Label labelPuntosFinal; // AÑADIMOS EL OBJETO LABELPUNTOSFINAL
    Label labelPuntosRecord; // AÑADIMOS EL OBJETO LABELPUTNOSRECORD
    Label labelIndicadoresReinicio; // AÑADIMOS EL OBJETO LABELINDICADORESREINICIO

    //////////////////////////////
    ///// VARIABLES //////////////
    //////////////////////////////
    
    boolean depuracion = true; // CON ESTA VARIABLE ACTIVAMOS O DESACTIVAMOS LA DEPURACION DE CODIGO
    
    final int SCENE_TAM_X = 720; // TAMAÑO X DE LA VENTANA
    final int SCENE_TAM_Y = 360; // TAMAÑO Y DE LA VENTANA
    double background1PositionX = 0; // POSICION X DE LA IMAGEN DE FONDO
    double background2PositionX = 720; // POSICION X DE LA IMAGEN DE FONDO2
    double avionPositionX = 50; // POSICION X DE LA IMAGEN AVION
    double avionPositionY = 150; // POSICION Y DE LA IMAGEN AVION
    double misil1PositionX = 1000; // POSICION X DE LA IMAGEN MISIL1
    double misil1PositionY = 250; // POSICION Y DE LA IMAGEN MISIL1
    double misil2PositionX = 750; // POSICION X DE LA IMAGEN MISIL2
    double misil2PositionY = 150; // POSICION Y DE LA IMAGEN MISIL2
    double misil3PositionX = 1200; // POSICION X DE LA IMAGEN MISIL3
    double misil3PositionY = 50; // POSICION Y DE LA IMAGEN MISIL3
    double coinPositionX = 2000; // POSICION X DE LA IMAGEN COIN
    double coinPositionY = 100; // POSICION Y DE LA IMAGEN COIN
    double explosionPositionX = 2000; // POSICION X DE LA IMAGEN COIN
    double explosionPositionY = 100; // POSICION Y DE LA IMAGEN COIN
    boolean carril1 = true; // Y = 50
    boolean carril2 = false; // Y = 100
    boolean carril3 = true; // Y = 150
    boolean carril4 = false; // Y = 200
    boolean carril5 = true; // Y = 250
    int numAleatorio1 = 0; // POSTERIORMETE ALMACENAREMOS UN VALOR ALEATORIO ENTRE 1 Y 5
    int numAleatorio2 = 0; // POSTERIORMETE ALMACENAREMOS UN VALOR ALEATORIO ENTRE 1 Y 5
    int numAleatorio3 = 0; // POSTERIORMETE ALMACENAREMOS UN VALOR ALEATORIO ENTRE 1 Y 5
    int numAleatorio4 = 0; // POSTERIORMETE ALMACENAREMOS UN VALOR ALEATORIO ENTRE 1 Y 5
    double velocidadMisiles = 1; // VELOCIDAD A LA QUE SE MUEVEN LOS MISILES
    int puntos = 0; // PUNTOS QUE SE OBTIENEB AL COGER UNA MONEDA
    boolean reinicio = false; // ALMACENA TRUE O FALSE PARA CONTROLAR EL CORRECTO FUNCIONAMIENTO DEL METODO DE REINICIO DE PARTIDA(LO DECLARAMOS MAS TARDE)
    boolean borrarTextos = false; // ALMACENA TRUE O FALSE PARA CONTROLAR EL BORRADO DE LOS TEXTOS QUE APARECEN EN PANTALLA
    int confirmacionBorrado = 0; // TAMBIEN LO USAMOS PARA CONTROLAR EL BORRADO DE LOS TEXTOS DE LA PANTALLA
    boolean sonidoDeath = true; // LO USAMOS PARA CONTROLAR EL CORRECTO FUNCIONAMIENTO DEL SONIDO DE EXPLOSION DE LOS MISILES
    int avionCurrentSpeed = 0; // USAMOS ESTE VALOR PARA CONTROLAR EL MOVIENTO DEL AVION
    String usuarioActual = ""; // ALMACENA EL NOMBRE DEL USUARIO QUE ESTA JUGANDO DURANTE LA EJECUCION DEL JUEGO
    String usuarioRecord = ""; // ALMACENA EL NOMBRE DEL USUARIO QUE POSSEE EL RECORD DE PUNTUACION, SE ALMACENA EN UN FICHERO EXTERNO
    URL urlAudioCoin = getClass().getResource("/audio/coin.wav"); // AÑADIMOS EL FICHERO DE AUDIO QUE SE REPRODUCE CUANDO COGEMOS UNA MONEDA
    URL urlAudioDeath = getClass().getResource("/audio/death_sound.wav"); // AÑADIMOS EL FICHERO DE AUDIO QUE SE REPRODUCE CUANDO NOS ESTELLAMOS CONTRA UN MISIL
    URL urlAudioBackground = getClass().getResource("/audio/sound_background.mp3"); // AÑADIMOS EL FICHERO DE AUDIO QUE SE REPRODUCE CUANDO INICIAMOS O REINICIAMOS LAS PARTIDA
    
    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    
    private void crearLabelPuntos(){ // ESTE METODO CREA LOS TEXTOS QUE VAMOS A VER EN PANTALLA Y NOS DICEN LAS PUNTUACIÓN EN EL JUEGO
        if(depuracion==true){
            System.out.println("llamos al metodo crealLabelPutnos()");
        }
        labelIndicadoresReinicio = new Label("Pulsa ENTER para jugar otra vez, o pulsa ESC para salir"); // CREAMOS EL TEXTO DE INDICACIONES CUANDO PERDEMOS
        labelPuntos = new Label("Coins: "+puntos); // ESTO MUESTRA EN PANATALLA LOS PUTNOS ACUTALES
        Font font = Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 25); // ESTABLECEMOS ALGUNAS DE LAS PROPIEDADES DE LA FUENTE DEL TEXTO
        labelPuntos.setFont(font); // CON ESTO LE APLICAMOS LAS POPIEDADES DE LA FUENTE AL TEXTO
        labelPuntos.setTextFill(Color.BLACK); // CAMBIA EL COLOR DEL TEXTO A NEGRO
        labelPuntos.setTranslateX(300); // CAMBIA LAS COORDENADAS X DEL TEXTO
        labelPuntos.setTranslateY(25); // CAMBIA LAS COORDENADAS Y DEL TEXTO
        root.getChildren().add(labelPuntos); // AÑADE EL TEXTO LABELPUTNOS AL ROOT
    }
    private void crearLabelPuntosFinal(){
        if(depuracion==true){
            System.out.println("llamamos al metodo crearLabelPuntosFinal()");
        }
        labelPuntosFinal = new Label("HAS CONSEGUIDO "+puntos+" PUNTOS"); //ESTO MUESTRA LOS PUNTOS QUE HEMOS CONSEGUIDO A LO LARGO DE LA PARTIDA
        Font font = Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 25); // ESTABLECEMOS ALGUNAS DE LAS PROPIEDADES DE LA FUENTE DEL TEXTO
        labelPuntosFinal.setFont(font); // CON ESTO LE APLICAMOS LAS POPIEDADES DE LA FUENTE AL TEXTO
        labelPuntosFinal.setTextFill(Color.BLACK); // CAMBIA EL COLOR DEL TEXTO A NEGRO
        labelPuntosFinal.setTranslateX(150); // CAMBIA LAS COORDENADAS X DEL TEXTO
        labelPuntosFinal.setTranslateY(140); // CAMBIA LAS COORDENADAS Y DEL TEXTO
        root.getChildren().add(labelPuntosFinal); // AÑADE EL TEXTO LABELPUTNOSFINAL AL ROOT
    }
    private void crearLabelPuntosRecord(){
        if(depuracion==true){
            System.out.println("llamamos al metodo crearLabelPuntosRecord()");
        }
        labelPuntosRecord = new Label("RÉCORD: "+readFileString("files/usuarioRecord.txt")+" "+readFileInt("files/record.txt")+" PUNTOS"); // CON ESO MOSTRAMOS EL RECORD ACTUAL AL MOMENTO DE PERDER LA PARTIDA
        Font font = Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 20); // ESTABLECEMOS ALGUNAS DE LAS PROPIEDADES DE LA FUENTE DEL TEXTO
        labelPuntosRecord.setFont(font); // CON ESTO LE APLICAMOS LAS POPIEDADES DE LA FUENTE AL TEXTO
        labelPuntosRecord.setTextFill(Color.BLACK); // CAMBIA EL COLOR DEL TEXTO A NEGRO
        labelPuntosRecord.setTranslateX(205); // CAMBIA LAS COORDENADAS X DEL TEXTO
        labelPuntosRecord.setTranslateY(220); // CAMBIA LAS COORDENADAS Y DEL TEXTO
        root.getChildren().add(labelPuntosRecord); // AÑADE EL TEXTO LABELPUTNOSRECORD AL ROOT
    }
    private void cambiarLabelPuntos(){ // ESTE METODO ACTUALIZA EL LABEL DE PUTNOS QUE SE MUESTRA EN LA PANTALLA DURANTE LA PARTIDA
        if(depuracion==true){
            System.out.println("llamamos al metodo cambiarLabelPuntos()");
        }
        labelPuntos.setText(""); // PRIMERO BORRAMOS EL TEXTO QUE YA SE ESTABA MOSTRANDO
        labelPuntos.setText("Coins: "+puntos); // Y ESCRIBIMOS EL TEXTO NUEVO, CON LA PUNTUACION ACTUALIZADA
    }
    
    private void sonidoBackground(){ // ESTE METODO AÑADE EL SONIDO DE FONDO PARA LA PARTIDA
        if(depuracion==true){
            System.out.println("llamamos al metodo sonidoBackground()");
        }
        AudioClip audioClip3;
        try {
            audioClip3 = new AudioClip(urlAudioBackground.toURI().toString());
            audioClip3.stop();
            audioClip3.play();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        
    }
    
    private void sonidoCoin(){ // ESTE METODO AÑADE EL SONIDO QUE SE REPRODUCE AL COGER UNA MONEDA
        if(depuracion==true){
            System.out.println("se reproduce sonidoCoin()");
        }
        AudioClip audioClip1;
        try {
            audioClip1 = new AudioClip(urlAudioCoin.toURI().toString());
            audioClip1.play();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
    
    private void sonidoDeath(){ // ESTE METODO AÑADE EL SONIDO QUE SE REPRODUCE AL MOMENTO DE CHOCAR CONTRA UNA MISI
        if(depuracion==true){
            System.out.println("se reproduce sonidoDeath()");
        }
        AudioClip audioClip2;
        try {
            audioClip2 = new AudioClip(urlAudioDeath.toURI().toString());
            audioClip2.play();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
            
        }
    }
   
    private void writeFileRecord() // ESTE METODO ALMACENA EL NUMERO DE PUTNOS QUE SE HAN OBTENIDO, SIEMPRE Y CUANDO SE SUPERE EL RECORD ANTERIOR
    {
        if(depuracion==true){
            System.out.println("llamamos al metodo WriteFileRecord()");
        }
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("files/record.txt"); // ESTA ES LA RUTA EN LA QUE SE ALMACENA LA PUNTUACION
            pw = new PrintWriter(fichero);
            pw.println(puntos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
              
           }
        }
    }
    
    private void writeFileUsuario()// ESTE METODO ALMACENA EL NOMBRE DEL JUGADOR ACTUAL EN EL FICHERO DE USUARIORECORD, SIEMPRE Y CUANDO SE SUPERE EL RECORD ANTERIOR
    {
        if(depuracion==true){
            System.out.println("llamamos al metodo WriteFileUsuaio()");
        }
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("files/usuarioRecord.txt"); // ESTA ES LA RUTA EN LA QUE SE ALMACENA EL NOMBRE DEL USUARIO
            pw = new PrintWriter(fichero);
            pw.println(usuarioActual);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
              
           }
        }
    }
    
    
    private int readFileInt(String ruta){ // ESTE METODO LEE Y DEVUELVE EL CONTENIDO DE UN FICHERO, SIEMPRE DEVUELVE UN VALOR DE TIPO INT
        if(depuracion==true){
            System.out.println("llamamos al metodo readFileInt()");
        }
       int record = 0;
       try (BufferedReader reader = new BufferedReader(new FileReader(new File(ruta)))) {
            
            String line;
            line = reader.readLine();
            record = Integer.parseInt(line);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return record;
    }
    
    private String readFileString(String ruta){// ESTE METODO LEE Y DEVUELVE EL CONTENIDO DE UN FICHERO, SIEMPRE DEVUELVE UN VALOR DE TIPO STRING
        if(depuracion==true){
            System.out.println("llamamos al metodo readFileString()");
        }
       String usuarioLeido = "";
       try (BufferedReader reader = new BufferedReader(new FileReader(new File(ruta)))) {
            
            String line;
            line = reader.readLine();
            usuarioLeido = line;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarioLeido;
    }
    
    private void registroNombre(){ // ESTE METODO LANZA UNA PEQUEÑA VENTANA PARA QUE EL USUARIO INTRODUZCA SU NOMBRE ANTES DE QUE COMIENZE A JUGAR
        if(depuracion==true){
            System.out.println("llamamos al metodo registroNombre()");
        }
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setGraphic(note);
        dialog.setTitle("Rocket Boom");
        dialog.setHeaderText("Introduce tu nombre para guardar tu puntuación");
        dialog.setContentText("Introduce tu nombre:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            usuarioActual = result.get();
        }
        if(depuracion==true){
            result.ifPresent(name -> System.out.println("Usuario actual: " + name));
        }
    }
    
   private void reiniciarPartida(){ // CON ESTE METODO REINICIAMOS LA PARTIDA DE MANERA QUE TODAS LAS VARIABLES QUE MODIFICAMOS VUELVAN A SU VALOR INICIAL
       if(depuracion==true){
            System.out.println("llamamos al metodo reiniciarPartida()");
        }
       sonidoBackground();
        if(puntos > readFileInt("files/record.txt")){
            writeFileUsuario();
            writeFileRecord(); 
        }
        // ESTABLECE A CADA VARIABLE SU VALOR POR DEFECTO //
        avionPositionX = 50;
        avionPositionY = 150;
        misil1PositionX = 1000;
        misil1PositionY = 250;
        misil2PositionX = 750;
        misil2PositionY = 150;
        misil3PositionX = 1200;
        misil3PositionY = 50;
        coinPositionX = 2000;
        coinPositionY = 100;
        explosionPositionX = 2000;
        explosionPositionY = 100;
        carril1 = true;
        carril2 = false;
        carril3 = true;
        carril4 = false;
        carril5 = true;
        numAleatorio1 = 0;
        numAleatorio2 = 0;
        numAleatorio3 = 0;
        numAleatorio4 = 0;
        velocidadMisiles = 1;
        puntos = 0;
        reinicio = false;
        sonidoDeath = true;
        confirmacionBorrado = 0;
        avionCurrentSpeed = 0;
        labelIndicadoresReinicio.setTranslateX(3000);
        labelIndicadoresReinicio.setTranslateY(3000);
        labelPuntosFinal.setTranslateX(3000);
        labelPuntosFinal.setTranslateY(3000);
        labelPuntosRecord.setTranslateX(3000);
        labelPuntosRecord.setTranslateY(3000);
        fondo1.setLayoutX(background1PositionX);
        fondo2.setLayoutX(background2PositionX);
        groupAvion.setLayoutX(avionPositionX);
        groupAvion.setLayoutY(avionPositionY);
        groupMisil1.setLayoutX(misil1PositionX);
        groupMisil1.setLayoutY(misil1PositionY);
        groupMisil2.setLayoutX(misil2PositionX);
        groupMisil2.setLayoutY(misil2PositionY);
        groupMisil3.setLayoutX(misil3PositionX);
        groupMisil3.setLayoutY(misil3PositionY);
        groupCoin.setLayoutX(coinPositionX);
        groupCoin.setLayoutY(coinPositionY);
        explosion.setLayoutX(explosionPositionX);
        explosion.setLayoutY(explosionPositionY);
        borrarTextos = false;
        labelPuntosFinal = new Label("");
        labelPuntosRecord = new Label("");
        labelPuntos.setText("");
        labelPuntos.setText("Coins: "+puntos);
        labelIndicadoresReinicio.setText("");
        ////////////////////////////////////////////////////
    }
   
    @Override
    
      public void start(Stage stage) throws FileNotFoundException, IOException {
          Image noteImg = new Image(getClass().getResourceAsStream("/images/note.png")); // CARGA LA IMAGEN NOTE
          note = new ImageView(noteImg); // CREA EL OBJETO note
          registroNombre(); // LLAMAMOS AL METODO REGISTRONOMBRE() PARA QUE SALGA LA VENTANA NECESARIA PARA QUE EL USUARIO INTRODUZCA SU NOMBRE
          readFileInt("files/record.txt");
          sonidoBackground(); // LLAMAMOS AL METODO SONIDOBACKGROUND() PARA REPRODUCIR EL SONIDO DE FONDO DURANTE LA PARTIDA
          Scene scene = new Scene(root, SCENE_TAM_X, SCENE_TAM_Y); // CREAMOS LA VENTANA PRINCIPAL DEL JUEGO
          stage.setTitle("Rocket Boom"); // TITULO DE LA VENTANA // AÑADIMOS EL TITULO A LA VENTAN PRINCIPAL
          stage.getIcons().add(new Image("/images/misil.png")); // AÑADIMOS EL ICONO A LA VENTANA
          stage.setScene(scene);
          stage.show();
          stage.setResizable(false); // BLOQUEAR REESCALADO DE LA VENTANA
          Image fondoImg = new Image(getClass().getResourceAsStream("/images/FondoLejos.png")); // CARGA LA IMAGEN DE FONDO
          Image avionImg = new Image(getClass().getResourceAsStream("/images/avion3.gif")); // CARGA LA IMAGEN AVION
          Image misilImg = new Image(getClass().getResourceAsStream("/images/misil.png")); // CARGA LA IMAGEN MISIL
          Image coinImg = new Image(getClass().getResourceAsStream("/images/coin.gif")); // CARGA LA IMAGEN COIN
          Image explosionImg = new Image(getClass().getResourceAsStream("/images/explosion2.gif")); // CARGA LA IMAGEN EXPLOSION
          fondo1 = new ImageView(fondoImg); // CREA EL OBJETO fondo1
          fondo2 = new ImageView(fondoImg); // CREA EL OBJETO fondo2
          avion = new ImageView(avionImg); // CREA EL OBJETO avion
          misil1 = new ImageView(misilImg); // CREA EL OBJETO misil1
          misil2 = new ImageView(misilImg); // CREA EL OBJETO misil2
          misil3 = new ImageView(misilImg); // CREA EL OBJETO misil3
          coin = new ImageView(coinImg); // CREA EL OBJETO coin
          explosion = new ImageView(explosionImg);
          root.getChildren().add(fondo1); // AÑADE EL OBJETO fondo1 A LA PANTALLA
          root.getChildren().add(fondo2); // AÑADE EL OBJETO fondo2 A LA PANTALLA
          root.getChildren().add(groupAvion); // AÑADE EL OBJETO avion A LA PANTALLA
          root.getChildren().add(groupMisil1); // AÑADE EL OBJETO misil1 A LA PANTALLA
          root.getChildren().add(groupMisil2); // AÑADE EL OBJETO misil2 A LA PANTALLA
          root.getChildren().add(groupMisil3); // AÑADE EL OBJETO misil3 A LA PANTALLA
          root.getChildren().add(groupCoin); // AÑADE EL OBJETO coin A LA PANTALLA
          root.getChildren().add(explosion); // AÑADE EL OBJETO explosion A LA PANTALLA
          groupAvion.getChildren().addAll(avion,rectAvion); // AÑADIMOS AL GRUPO LA IMAGEN Y EL RECTANGULO CORRESPONDIENTES
          groupMisil1.getChildren().addAll(misil1,rectMisil1); // AÑADIMOS AL GRUPO LA IMAGEN Y EL RECTANGULO CORRESPONDIENTES
          groupMisil2.getChildren().addAll(misil2,rectMisil2); // AÑADIMOS AL GRUPO LA IMAGEN Y EL RECTANGULO CORRESPONDIENTES
          groupMisil3.getChildren().addAll(misil3,rectMisil3); // AÑADIMOS AL GRUPO LA IMAGEN Y EL RECTANGULO CORRESPONDIENTES
          groupCoin.getChildren().addAll(coin,rectCoin); // AÑADIMOS AL GRUPO LA IMAGEN Y EL RECTANGULO CORRESPONDIENTES
          rectAvion.setVisible(false); // CON ESTO HACEMOS QUE NO SE VEA EL RECTANGULO
          rectMisil1.setVisible(false); // CON ESTO HACEMOS QUE NO SE VEA EL RECTANGULO
          rectMisil2.setVisible(false); // CON ESTO HACEMOS QUE NO SE VEA EL RECTANGULO
          rectMisil3.setVisible(false); // CON ESTO HACEMOS QUE NO SE VEA EL RECTANGULO
          rectCoin.setVisible(false); // CON ESTO HACEMOS QUE NO SE VEA EL RECTANGULO
          
          ////////////////////////////////////////////////////////////
          //////////////////////// LABEL DE PUNTOS ///////////////////
          ////////////////////////////////////////////////////////////
          
          crearLabelPuntos(); // LLAMAMOS AL METODO CREARLABELPUTNOS() PARA QUE SE MUESTREN LOS TEXTOS DE PUNTUACION EN LA PANTALLA
          
          ////////////////////////////////////////////////////////////
          // IGUALAMOS LA POSICION INICIAL DE LAS IMAGENES DEL JUEGO //
          ////////////////////////////////////////////////////////////
          
          fondo1.setLayoutX(background1PositionX);
          fondo2.setLayoutX(background2PositionX);
          groupAvion.setLayoutX(avionPositionX);
          groupAvion.setLayoutY(avionPositionY);
          groupMisil1.setLayoutX(misil1PositionX);
          groupMisil1.setLayoutY(misil1PositionY);
          groupMisil2.setLayoutX(misil2PositionX);
          groupMisil2.setLayoutY(misil2PositionY);
          groupMisil3.setLayoutX(misil3PositionX);
          groupMisil3.setLayoutY(misil3PositionY);
          groupCoin.setLayoutX(coinPositionX);
          groupCoin.setLayoutY(coinPositionY);
          explosion.setLayoutX(explosionPositionX);
          explosion.setLayoutY(explosionPositionY);
          
          ////////////////////////////////////////////////////////////

          Timeline fondoScroll = new Timeline( // CON ESTE TIMELINE CONSEGUIMOS EL EFECTO DE SCROLL EN LA IMAGEN DE FONDO USANDO DOS OBJETOS QUE CONTIENEN LA MISMA IMAGEN
                  new KeyFrame(Duration.seconds(0.017), (ActionEvent ae) -> {
                      background1PositionX = background1PositionX -1; // RESTAMOS 1 LA POSICION X DEL FONDO 1
                      fondo1.setLayoutX(background1PositionX);
                      background2PositionX = background2PositionX -1; // RESTAMOS 1 LA POSICION X DEL FONDO 2
                      fondo2.setLayoutX(background2PositionX);
                      if (background1PositionX == -720) {
                          background1PositionX = 720;
                      } else if (background2PositionX == -720) {
                          background2PositionX = 720;
                      }
                  })
          );
          
          fondoScroll.setCycleCount(Timeline.INDEFINITE); // DEFINIR QUE SE EJECUTE INDEFINIDAMENTE
          fondoScroll.play(); // EJECUTAR EL TIMELINE
          
          scene.setOnKeyPressed((KeyEvent event) -> { // CON ESTO DETECTAMOS LA PULSACION DE LAS TECLAS
              switch(event.getCode()) {
                  case UP:
                      //PULSADA TECLA ARRIBA
                      if(depuracion==true){
                        System.out.println("Pulsas UP");
                      }
                      avionCurrentSpeed = -6;
                          
                      break;
                  case DOWN:
                      //PULSADA TECLA ABAJO
                      if(depuracion==true){
                        System.out.println("Pulsas DOWN");
                      }
                      avionCurrentSpeed = 6;
                      break;
              }
              if(reinicio==true){
                  switch (event.getCode()){
                                    case ENTER: // PULSAR TECLA ENTER
                                        if(depuracion==true){
                                            System.out.println("Pulsas ENTER");
                                        }
                                        reinicio = false;
                                        borrarTextos = true;
                                        reiniciarPartida(); // LLAMAMOS AL METODO DE REINCIO DE PARTIDA
                                        break;
                                    case ESCAPE: // PULSAR TECLA ESCAPE
                                        if(depuracion==true){
                                            System.out.println("Pulsas ESCAPE");
                                        }
                                        if(puntos > readFileInt("files/record.txt")){
                                            writeFileRecord(); // LLAMAMOS AL METODO DE ESCRITURA DE PUNTUACION EN EL FICHERO DE RECORD
                                            writeFileUsuario(); // LLAMAMOS AL METODO DE ESCRITURA DE NOMBRE EN EL FICHERO DE NOMBRE DE USUARIO
                                        }
                                        System.exit(0);
                                        break;
                                }
              }
          });
          scene.setOnKeyReleased((KeyEvent event) -> {
              avionCurrentSpeed = 0;
          });
          Timeline movimientoAvion = new Timeline(
                  new KeyFrame(Duration.seconds(0.017), (ActionEvent ae) -> {
                      avionPositionY += avionCurrentSpeed;
                      if(avionPositionY < 0){
                          avionPositionY = 0;
                      } else{
                          if(avionPositionY > 300){
                              avionPositionY = 300;
                          }
                      }
                      groupAvion.setLayoutY(avionPositionY);
                  })
          );
          
          movimientoAvion.setCycleCount(Timeline.INDEFINITE); // DEFINIR QUE SE EJECUTE INDEFINIDAMENTE
          movimientoAvion.play(); // EJECUTAR EL TIMELINE
          
          Timeline movimientoMisiles = new Timeline(
                  new KeyFrame(Duration.seconds(0.007), (ActionEvent ae) -> {
                      misil1PositionX = misil1PositionX - velocidadMisiles;
                      groupMisil1.setLayoutX(misil1PositionX);
                      misil2PositionX = misil2PositionX - velocidadMisiles;
                      groupMisil2.setLayoutX(misil2PositionX);
                      misil3PositionX = misil3PositionX - velocidadMisiles;
                      groupMisil3.setLayoutX(misil3PositionX);
                      coinPositionX = coinPositionX - velocidadMisiles;
                      groupCoin.setLayoutX(coinPositionX);
                      groupMisil1.setLayoutY(misil1PositionY);
                      groupMisil2.setLayoutY(misil2PositionY);
                      groupMisil3.setLayoutY(misil3PositionY);
                      groupCoin.setLayoutY(coinPositionY);
                      if (misil1PositionX < -40){
                          misil1PositionX = 730;
                          numAleatorio1= (int) (Math.random() * 5) + 1;
                          if (numAleatorio1 == 1 && carril1 == false){
                              misil1PositionY = 50;
                              carril1 = true;
                              carril2 = false;
                              carril3 = false;
                              carril4 = false;
                              carril5 = false;
                          }
                          if (numAleatorio1 == 2 && carril2 == false){
                              misil1PositionY = 100;
                              carril1 = false;
                              carril2 = true;
                              carril3 = false;
                              carril4 = false;
                              carril5 = false;
                          }
                          if (numAleatorio1 == 3 && carril3 == false){
                              misil1PositionY = 150;
                              carril1 = false;
                              carril2 = false;
                              carril3 = true;
                              carril4 = false;
                              carril5 = false;
                          }
                          if (numAleatorio1 == 4 && carril4 == false){
                              misil1PositionY = 200;
                              carril1 = false;
                              carril2 = false;
                              carril3 = false;
                              carril4 = true;
                              carril5 = false;
                          }
                          if (numAleatorio1 == 5 && carril5 == false){
                              misil1PositionY = 250;
                              carril1 = false;
                              carril2 = false;
                              carril3 = false;
                              carril4 = false;
                              carril5 = true;
                          }
                      }
                      if (misil2PositionX < -40){
                          misil2PositionX = 730;
                          numAleatorio2 = (int) (Math.random() * 5) + 1;
                          if (numAleatorio2 == 1 && carril1 == false){
                              misil2PositionY = 50;
                              carril1 = true;
                              carril2 = false;
                              carril3 = false;
                              carril4 = false;
                              carril5 = false;
                          }
                          if (numAleatorio2 == 2 && carril2 == false){
                              misil2PositionY = 100;
                              carril1 = false;
                              carril2 = true;
                              carril3 = false;
                              carril4 = false;
                              carril5 = false;
                          }
                          if (numAleatorio2 == 3 && carril3 == false){
                              misil2PositionY = 150;
                              carril1 = false;
                              carril2 = false;
                              carril3 = true;
                              carril4 = false;
                              carril5 = false;
                          }
                          if (numAleatorio2 == 4 && carril4 == false){
                              misil2PositionY = 200;
                              carril1 = false;
                              carril2 = false;
                              carril3 = false;
                              carril4 = true;
                              carril5 = false;
                          }
                          if (numAleatorio2 == 5 && carril5 == false){
                              misil2PositionY = 250;
                              carril1 = false;
                              carril2 = false;
                              carril3 = false;
                              carril4 = false;
                              carril5 = true;
                          }
                      }
                      if (misil3PositionX < -40){
                          misil3PositionX = 730;
                          numAleatorio3 = (int) (Math.random() * 5) + 1;
                          if (numAleatorio2 == 1 && carril1 == false){
                              misil3PositionY = 50;
                              carril1 = true;
                              carril2 = false;
                              carril3 = false;
                              carril4 = false;
                              carril5 = false;
                          }
                          if (numAleatorio3 == 2 && carril2 == false){
                              misil3PositionY = 100;
                              carril1 = false;
                              carril2 = true;
                              carril3 = false;
                              carril4 = false;
                              carril5 = false;
                          }
                          if (numAleatorio3 == 3 && carril3 == false){
                              misil3PositionY = 150;
                              carril1 = false;
                              carril2 = false;
                              carril3 = true;
                              carril4 = false;
                              carril5 = false;
                          }
                          if (numAleatorio3 == 4 && carril4 == false){
                              misil3PositionY = 200;
                              carril1 = false;
                              carril2 = false;
                              carril3 = false;
                              carril4 = true;
                              carril5 = false;
                          }
                          if (numAleatorio3 == 5 && carril5 == false){
                              misil3PositionY = 250;
                              carril1 = false;
                              carril2 = false;
                              carril3 = false;
                              carril4 = false;
                              carril5 = true;
                          }
                      }
                      if (coinPositionX < -40){
                          coinPositionX = 730;
                          numAleatorio4 = (int) (Math.random() * 5) + 1;
                          if (numAleatorio4 == 1 && carril1 == false){
                              coinPositionY = 50;
                              carril1 = true;
                              carril2 = false;
                              carril3 = false;
                              carril4 = false;
                              carril5 = false;
                          }
                          if (numAleatorio4 == 2 && carril2 == false){
                              coinPositionY = 100;
                              carril1 = false;
                              carril2 = true;
                              carril3 = false;
                              carril4 = false;
                              carril5 = false;
                          }
                          if (numAleatorio4 == 3 && carril3 == false){
                              coinPositionY = 150;
                              carril1 = false;
                              carril2 = false;
                              carril3 = true;
                              carril4 = false;
                              carril5 = false;
                          }
                          if (numAleatorio4 == 4 && carril4 == false){
                              coinPositionY = 200;
                              carril1 = false;
                              carril2 = false;
                              carril3 = false;
                              carril4 = true;
                              carril5 = false;
                          }
                          if (numAleatorio4 == 5 && carril5 == false){
                              coinPositionY = 250;
                              carril1 = false;
                              carril2 = false;
                              carril3 = false;
                              carril4 = false;
                              carril5 = true;
                          }
                      }
                  })
          );
          
          movimientoMisiles.setCycleCount(Timeline.INDEFINITE); // DEFINIR QUE SE EJECUTE INDEFINIDAMENTE
          movimientoMisiles.play(); // EJECUTAR EL TIMELINE
          
          Timeline detectarColision = new Timeline(
                  new KeyFrame(Duration.seconds(0.017), (ActionEvent ae) -> {
                      Shape colisionMisil1 = Shape.intersect(rectAvion, rectMisil1);
                      boolean colisionVaciaMisil1 = colisionMisil1.getBoundsInLocal().isEmpty();
                      confirmacionBorrado = 1;
                      if(colisionVaciaMisil1 == false){
                          movimientoMisiles.stop();
                          if(depuracion==true){
                              System.out.println("HAS CHOCADO CON UN MISIL");
                          }
                          if(sonidoDeath == true){
                              sonidoDeath();
                              sonidoDeath = false;
                          }
                          explosion.setLayoutX(avionPositionX);
                          explosion.setLayoutY(avionPositionY-50);
                          groupMisil1.setLayoutX(3000);
                          groupMisil1.setLayoutY(3000);
                          groupAvion.setLayoutX(3000);
                          groupAvion.setLayoutY(3000);
                          reinicio = true;
                      }
                      Shape colisionMisil2 = Shape.intersect(rectAvion, rectMisil2);
                      boolean colisionVaciaMisil2 = colisionMisil2.getBoundsInLocal().isEmpty();
                      if(colisionVaciaMisil2 == false){
                          movimientoMisiles.stop();
                          if(depuracion==true){
                              System.out.println("HAS CHOCADO CON UN MISIL");
                          }
                          if(sonidoDeath == true){
                              sonidoDeath();
                              sonidoDeath = false;
                          }
                          explosion.setLayoutX(avionPositionX);
                          explosion.setLayoutY(avionPositionY-50);
                          groupMisil2.setLayoutX(3000);
                          groupMisil2.setLayoutY(3000);
                          groupAvion.setLayoutX(3000);
                          groupAvion.setLayoutY(3000);
                          reinicio = true;
                      }
                      Shape colisionMisil3 = Shape.intersect(rectAvion, rectMisil3);
                      boolean colisionVaciaMisil3 = colisionMisil3.getBoundsInLocal().isEmpty();
                      if(colisionVaciaMisil3 == false){
                          movimientoMisiles.stop();
                          if(depuracion==true){
                              System.out.println("HAS CHOCADO CON UN MISIL");
                          }
                          if(sonidoDeath == true){
                              sonidoDeath();
                              sonidoDeath = false;
                          }
                          explosion.setLayoutX(avionPositionX);
                          explosion.setLayoutY(avionPositionY-50);
                          groupMisil3.setLayoutX(3000);
                          groupMisil3.setLayoutY(3000);
                          groupAvion.setLayoutX(3000);
                          groupAvion.setLayoutY(3000);
                          reinicio = true;
                      }
                      Shape colisionCoin = Shape.intersect(rectAvion, rectCoin);
                      boolean colisionVaciaCoin = colisionCoin.getBoundsInLocal().isEmpty();
                      if(colisionVaciaCoin == false){
                          coinPositionX = -41;
                          if(coinPositionX == -41){
                              puntos++;
                              sonidoCoin();
                              if(depuracion==true){
                                System.out.println("HAS COGIDO UNA MONEDA");
                              }
                          }
                          velocidadMisiles = velocidadMisiles + 0.2;
                          cambiarLabelPuntos();
                      }
                  })
          );
          
          detectarColision.setCycleCount(Timeline.INDEFINITE); // DEFINIR QUE SE EJECUTE INDEFINIDAMENTE
          detectarColision.play(); // EJECUTAR EL TIMELINE
          
          Timeline pantallaPerder = new Timeline(
                  new KeyFrame(Duration.seconds(1.455), (ActionEvent ae) -> {
                      if(reinicio == true){
                          explosion.setLayoutX(5000);
                          explosion.setLayoutY(5000);
                          groupMisil1.setLayoutX(3000);
                          groupMisil1.setLayoutY(3000);
                          groupMisil2.setLayoutX(3000);
                          groupMisil2.setLayoutY(3000);
                          groupMisil3.setLayoutX(3000);
                          groupMisil3.setLayoutY(3000);
                          groupCoin.setLayoutX(3000);
                          groupCoin.setLayoutY(3000);
                          labelPuntos.setText("");
                          detectarColision.stop();
                          if(confirmacionBorrado == 1){
                              confirmacionBorrado = 0;
                              crearLabelPuntosFinal();
                              crearLabelPuntosRecord();
                              readFileInt("files/record.txt");
                              readFileString("files/usuarioRecord.txt");
                              //labelPuntosRecord = new Label("RÉCORD: "+readFileString("files/usuarioRecord.txt")+" "+readFileInt("files/record.txt")+" PUNTOS");
                              labelPuntosFinal.setText("HAS CONSEGUIDO "+puntos+" PUNTOS");
                              labelIndicadoresReinicio.setText("Pulsa ENTER para jugar otra vez, o pulsa ESC para salir");
                              Font font = Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 18);
                              labelIndicadoresReinicio.setFont(font);
                              labelIndicadoresReinicio.setTextFill(Color.GREY);
                              labelIndicadoresReinicio.setTranslateX(80);
                              labelIndicadoresReinicio.setTranslateY(180);
                              root.getChildren().add(labelIndicadoresReinicio);
                          }
                      }
                  })
          );
          
          pantallaPerder.setCycleCount(Timeline.INDEFINITE); // DEFINIR QUE SE EJECUTE INDEFINIDAMENTE
          pantallaPerder.play(); // EJECUTAR EL TIMELINE
          
          Timeline detectarReinicio = new Timeline(
                  new KeyFrame(Duration.seconds(0.017), (ActionEvent ae) -> {
                      if (reinicio==false){
                          movimientoMisiles.play();
                          detectarColision.play();
                      }
                  })
          );
          detectarReinicio.setCycleCount(Timeline.INDEFINITE); // DEFINIR QUE SE EJECUTE INDEFINIDAMENTE
          detectarReinicio.play(); // EJECUTAR EL TIMELINE
      }
      
      public static void main(String[] args) {
          launch();
      }
}