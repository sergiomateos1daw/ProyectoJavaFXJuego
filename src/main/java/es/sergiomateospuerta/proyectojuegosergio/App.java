package es.sergiomateospuerta.proyectojuegosergio;

import java.io.BufferedReader;
import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    ImageView fondo1;
    ImageView fondo2;
    ImageView avion;
    Rectangle rectAvion = new Rectangle(53, 33);
    Group groupAvion = new Group();
    ImageView misil1;
    Rectangle rectMisil1 = new Rectangle(53, 33);
    Group groupMisil1 = new Group();
    ImageView misil2;
    Rectangle rectMisil2 = new Rectangle(53, 33);
    Group groupMisil2 = new Group();
    ImageView misil3;
    Rectangle rectMisil3 = new Rectangle(53, 33);
    Group groupMisil3 = new Group();
    ImageView coin;
    Rectangle rectCoin = new Rectangle(53, 33);
    Group groupCoin = new Group();
    ImageView explosion;
    Label labelPuntos;
    Label labelPuntosFinal;
    Label labelPuntosRecord;
    Label labelIndicadoresReinicio;

    //////////////////////////////
    ///// VARIABLES //////////////
    //////////////////////////////
    
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
    int numAleatorio1 = 0;
    int numAleatorio2 = 0;
    int numAleatorio3 = 0;
    int numAleatorio4 = 0;
    int rondas = 0;
    double velocidadMisiles = 1;
    int puntos = 0;
    boolean reinicio = false;
    boolean borrarTextos = false;
    int confirmacionBorrado = 0;
    final int volumenMusicaFondo = 10;
    boolean sonidoDeath = true;
    String usuarioActual = "";
    String usuarioRecord = "";
    //int record = readFile("files/record.txt");
    URL urlAudioCoin = getClass().getResource("/audio/coin.wav");
    URL urlAudioDeath = getClass().getResource("/audio/death_sound.wav");
    URL urlAudioBackground = getClass().getResource("/audio/sound_background.mp3");
    
    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
    
    private void crearLabelPuntos(){
        labelIndicadoresReinicio = new Label("Pulsa ENTER para jugar otra vez, o pulsa ESC para salir");
        labelPuntos = new Label("Coins: "+puntos);
        Font font = Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 25);
        labelPuntos.setFont(font);
        labelPuntos.setTextFill(Color.BLACK);
        labelPuntos.setTranslateX(300);
        labelPuntos.setTranslateY(25);
        root.getChildren().add(labelPuntos);
    }
    private void crearLabelPuntosFinal(){
        labelPuntosFinal = new Label("HAS CONSEGUIDO "+puntos+" PUNTOS");
        Font font = Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 25);
        labelPuntosFinal.setFont(font);
        labelPuntosFinal.setTextFill(Color.BLACK);
        labelPuntosFinal.setTranslateX(150);
        labelPuntosFinal.setTranslateY(140);
        root.getChildren().add(labelPuntosFinal);
    }
    private void crearLabelPuntosRecord(){
        labelPuntosRecord = new Label("RÉCORD: "+readFileString("files/usuarioRecord.txt")+" "+readFileInt("files/record.txt")+" PUNTOS");
        Font font = Font.font("Arial Black", FontWeight.BOLD, FontPosture.REGULAR, 20);
        labelPuntosRecord.setFont(font);
        labelPuntosRecord.setTextFill(Color.BLACK);
        labelPuntosRecord.setTranslateX(205);
        labelPuntosRecord.setTranslateY(220);
        root.getChildren().add(labelPuntosRecord);
    }
    private void cambiarLabelPuntos(){
        labelPuntos.setText("");
        labelPuntos.setText("Coins: "+puntos);
    }
    
    private void sonidoBackground(){
        AudioClip audioClip3;
        try {
            audioClip3 = new AudioClip(urlAudioBackground.toURI().toString());
            audioClip3.stop();
            audioClip3.play();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        
    }
    
    private void sonidoCoin(){
        AudioClip audioClip1;
        try {
            audioClip1 = new AudioClip(urlAudioCoin.toURI().toString());
            audioClip1.play();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
    
    private void sonidoDeath(){
        System.out.println("se reproduce sonidoDeath();");
        AudioClip audioClip2;
        try {
            audioClip2 = new AudioClip(urlAudioDeath.toURI().toString());
            audioClip2.play();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
            
        }
    }
   
    private void writeFileRecord()
    {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("files/record.txt");
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
    
    private void writeFileUsuario()
    {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("files/usuarioRecord.txt");
            pw = new PrintWriter(fichero);
            pw.println(usuarioActual);

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
    
    
    private int readFileInt(String ruta){
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
    
    private String readFileString(String ruta){
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
    
    private void registroNombre(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Rocket Boom");
        dialog.setHeaderText("Introduce tu nombre para guardar tu puntuación");
        dialog.setContentText("Introduce tu nombre:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            usuarioActual = result.get();
        }

        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> System.out.println("Usuario actual: " + name));
    }
    
   private void reiniciarPartida(){
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
        rondas = 0;
        velocidadMisiles = 1;
        puntos = 0;
        reinicio = false;
        sonidoDeath = true;
        confirmacionBorrado = 0;
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
          registroNombre();
          readFileInt("files/record.txt");
          sonidoBackground();
          Scene scene = new Scene(root, SCENE_TAM_X, SCENE_TAM_Y);
          stage.setTitle("Rocket Boom"); // TITULO DE LA VENTANA
          stage.getIcons().add(new Image("/images/misil.png"));
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
          groupAvion.getChildren().addAll(avion,rectAvion);
          groupMisil1.getChildren().addAll(misil1,rectMisil1);
          groupMisil2.getChildren().addAll(misil2,rectMisil2);
          groupMisil3.getChildren().addAll(misil3,rectMisil3);
          groupCoin.getChildren().addAll(coin,rectCoin);
          rectAvion.setVisible(false);
          rectMisil1.setVisible(false);
          rectMisil2.setVisible(false);
          rectMisil3.setVisible(false);
          rectCoin.setVisible(false);
          
          ////////////////////////////////////////////////////////////
          //////////////////////// LABEL DE PUNTOS ///////////////////
          ////////////////////////////////////////////////////////////
          
          crearLabelPuntos();
          
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

          Timeline fondoScroll = new Timeline(
                  new KeyFrame(Duration.seconds(0.017), (ActionEvent ae) -> {
                      background1PositionX = background1PositionX -1;
                      fondo1.setLayoutX(background1PositionX);
                      background2PositionX = background2PositionX -1;
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
          
          Timeline movimientoAvion = new Timeline(
                  new KeyFrame(Duration.seconds(0.017), (ActionEvent ae) -> {
                      scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            switch (event.getCode()){
                                case UP: // PULSAR TECLA ARRIBA
                                    if (avionPositionY > 50){
                                        avionPositionY = avionPositionY - 50;
                                        groupAvion.setLayoutY(avionPositionY);
                                    }
                                    break;
                                case DOWN: // PULSAR TECLA ABAJO
                                    if (avionPositionY < 250){
                                        avionPositionY = avionPositionY + 50;
                                        groupAvion.setLayoutY(avionPositionY);
                                    }
                                    break;
                            }
                        }
                      });
                      
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
                      if (reinicio==true){
                          scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                switch (event.getCode()){
                                    case ENTER: // PULSAR TECLA ENTER
                                        reinicio = false;
                                        borrarTextos = true;
                                        reiniciarPartida();
                                        movimientoMisiles.play();
                                        detectarColision.play();
                                        break;
                                    case ESCAPE: // PULSAR TECLA ESCAPE
                                        if(puntos > readFileInt("files/record.txt")){
                                            writeFileRecord();
                                            writeFileUsuario();
                                        }
                                        System.exit(0);
                                        break;
                                }
                            }
                         });
                      }
                  })
          );
          detectarReinicio.setCycleCount(Timeline.INDEFINITE); // DEFINIR QUE SE EJECUTE INDEFINIDAMENTE
          detectarReinicio.play(); // EJECUTAR EL TIMELINE
      }
}