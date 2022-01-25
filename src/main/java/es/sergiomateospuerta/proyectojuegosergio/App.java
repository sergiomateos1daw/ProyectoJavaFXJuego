package es.sergiomateospuerta.proyectojuegosergio;

import java.io.BufferedReader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


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
    final int SCENE_TAM_X = 720; // TAMAÑO X DE LA VENTANA
    final int SCENE_TAM_Y = 360; // TAMAÑO Y DE LA VENTANA
    int background1PositionX = 0; // POSICION X DE LA IMAGEN DE FONDO
    int background2PositionX = 720; // POSICION X DE LA IMAGEN DE FONDO2
    int avionPositionX = 50; // POSICION X DE LA IMAGEN AVION
    int avionPositionY = 150; // POSICION Y DE LA IMAGEN AVION
    int misil1PositionX = 1000; // POSICION X DE LA IMAGEN MISIL1
    int misil1PositionY = 250; // POSICION Y DE LA IMAGEN MISIL1
    int misil2PositionX = 750; // POSICION X DE LA IMAGEN MISIL2
    int misil2PositionY = 150; // POSICION Y DE LA IMAGEN MISIL2
    int misil3PositionX = 1200; // POSICION X DE LA IMAGEN MISIL3
    int misil3PositionY = 50; // POSICION Y DE LA IMAGEN MISIL3
    int coinPositionX = 2000; // POSICION X DE LA IMAGEN COIN
    int coinPositionY = 100; // POSICION Y DE LA IMAGEN COIN
    int explosionPositionX = 2000; // POSICION X DE LA IMAGEN COIN
    int explosionPositionY = 100; // POSICION Y DE LA IMAGEN COIN
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
    double velocidadMisiles = 0.005;
    int puntos = 0;
    boolean reinicio = false;
    Label label;
   
    private void crearLabelPuntos(){
        
        label = new Label("Coins: "+puntos);
        Font font = Font.font("Brush Script MT", FontWeight.BOLD, FontPosture.REGULAR, 25);
        label.setFont(font);
        label.setTextFill(Color.BLACK);
        label.setTranslateX(300);
        label.setTranslateY(25);
        root.getChildren().add(label);
    }
    private void cambiarLabelPuntos(){
        label.setText("");
        label.setText("Coins: "+puntos);
    }
    
    @Override
    
      public void start(Stage stage) throws FileNotFoundException, IOException {
          
          Scene scene = new Scene(root, SCENE_TAM_X, SCENE_TAM_Y);
          stage.setTitle("Juego Sergio"); // TITULO DE LA VENTANA
          stage.setScene(scene);
          stage.show();
          stage.setResizable(false); // BLOQUEAR REESCALADO DE LA VENTANA
          Image fondoImg = new Image(getClass().getResourceAsStream("/images/FondoLejos.png")); // CARGA LA IMAGEN DE FONDO
          Image avionImg = new Image(getClass().getResourceAsStream("/images/avion.gif")); // CARGA LA IMAGEN AVION
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
                  new KeyFrame(Duration.seconds(velocidadMisiles), (ActionEvent ae) -> {
                      misil1PositionX = misil1PositionX -1;
                      groupMisil1.setLayoutX(misil1PositionX);
                      misil2PositionX = misil2PositionX -1;
                      groupMisil2.setLayoutX(misil2PositionX);
                      misil3PositionX = misil3PositionX -1;
                      groupMisil3.setLayoutX(misil3PositionX);
                      coinPositionX = coinPositionX -1;
                      groupCoin.setLayoutX(coinPositionX);
                      groupMisil1.setLayoutY(misil1PositionY);
                      groupMisil2.setLayoutY(misil2PositionY);
                      groupMisil3.setLayoutY(misil3PositionY);
                      groupCoin.setLayoutY(coinPositionY);
                      if (misil1PositionX == -40){
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
                      if (misil2PositionX == -40){
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
                      if (misil3PositionX == -40){
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
                      if(colisionVaciaMisil1 == false){
                          movimientoMisiles.stop();
                          System.out.println("Has chocado con misil1");
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
                          System.out.println("Has chocado con misil2");
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
                          System.out.println("Has chocado con misil3");
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
                          puntos++;
                          System.out.println("Tienes "+puntos+" puntos");
                          cambiarLabelPuntos();
                      }
                      
                  })
          );
          
          detectarColision.setCycleCount(Timeline.INDEFINITE); // DEFINIR QUE SE EJECUTE INDEFINIDAMENTE
          detectarColision.play(); // EJECUTAR EL TIMELINE
          
          Timeline detectarReinicio = new Timeline(
                  new KeyFrame(Duration.seconds(1.455), (ActionEvent ae) -> {
                      if(reinicio == true){
                          detectarColision.stop();
                          explosion.setLayoutX(5000);
                          explosion.setLayoutY(5000);
                      }
                  })
          );
          
          detectarReinicio.setCycleCount(Timeline.INDEFINITE); // DEFINIR QUE SE EJECUTE INDEFINIDAMENTE
          detectarReinicio.play(); // EJECUTAR EL TIMELINE
          
      }
      
}