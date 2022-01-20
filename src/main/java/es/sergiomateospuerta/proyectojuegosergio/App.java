package es.sergiomateospuerta.proyectojuegosergio;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {
  
    Pane root = new Pane(); // PANEL PRINCIPAL QUE CONTENDRÁ LOS ELEMENTOS DE LA PANTALLA
    ImageView fondo1;
    ImageView fondo2;
    ImageView avion;
    final int SCENE_TAM_X = 720; // TAMAÑO X DE LA VENTANA
    final int SCENE_TAM_Y = 360; // TAMAÑO Y DE LA VENTANA
    int background1PositionX = 0; // POSICION X DE LA IMAGEN DE FONDO
    int background2PositionX = 720; // POSICION X DE LA IMAGEN DE FONDO
    
    @Override
      public void start(Stage stage) {
          
          Scene scene = new Scene(root, SCENE_TAM_X, SCENE_TAM_Y);
          stage.setTitle("Juego Sergio"); // TITULO DE LA VENTANA
          stage.setScene(scene);
          stage.show();
          stage.setResizable(false); // BLOQUEAR REESCALADO DE LA VENTANA
          Image fondoImg = new Image(getClass().getResourceAsStream("/images/FondoLejos.png")); // CARGA LA IMAGEN DE FONDO
          Image avionImg = new Image(getClass().getResourceAsStream("/images/avion.png")); // CARGA LA IMAGEN AVION
          fondo1 = new ImageView(fondoImg); // CREA EL OBJETO fondo1
          fondo2 = new ImageView(fondoImg); // CREA EL OBJETO fondo2
          avion = new ImageView(avionImg); // CREA EL OBJETO avion
          root.getChildren().add(fondo1); // AÑADE EL OBJETO fondo1 A LA PANTALLA
          root.getChildren().add(fondo2); // AÑADE EL OBJETO fondo2 A LA PANTALLA
          root.getChildren().add(avion); // AÑADE EL OBJETO fondo2 A LA PANTALLA
          
          ////////////////////////////////////////////////////////////
          // IGUALAMOS LA POSICION INICIAL DE LAS IMAGENES DE FONDO //
          ////////////////////////////////////////////////////////////
          
          fondo1.setLayoutX(background1PositionX);
          fondo2.setLayoutX(background2PositionX);
          
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
          
          fondoScroll.setCycleCount(Timeline.INDEFINITE);
          fondoScroll.play(); // EJECUTAR EL TIMELINE
          
          
      }
      
}