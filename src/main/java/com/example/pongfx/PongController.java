package com.example.pongfx;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PongController {

    private StackPane track;
    private Rectangle player1, player2, rightWall, leftWall, topWall, topWall2, bottomWall, bottomWall2;
    private Circle ball;
    private Label score;
    private Timeline player1Up, player1Down, player2Up, player2Down, ballMove;
    private double velocity;
    
    public PongController(StackPane track, Rectangle player1, Rectangle player2, Rectangle rightWall,
                          Rectangle leftWall, Rectangle topWall, Rectangle topWall2, Rectangle bottomWall,
                          Rectangle bottomWall2, Circle ball, Label score){
        this.track = track;
        this.player1 = player1;
        this.player2 = player2;
        this.rightWall = rightWall;
        this.leftWall = leftWall;
        this.topWall = topWall;
        this.topWall2 = topWall2;
        this.bottomWall = bottomWall;
        this.bottomWall2 = bottomWall2;
        this.ball = ball;
        this.score = score;
        this.velocity = 1;

        initGame();
        initControlls();

    }

    private void initControlls() {

        track.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.W){
                    player1Up.play();
                    player1Down.stop();
                }
                if(keyEvent.getCode() == KeyCode.S){
                    player1Down.play();
                    player1Up.stop();
                }
                if(keyEvent.getCode() == KeyCode.UP){
                    player2Up.play();
                    player2Down.stop();
                }
                if(keyEvent.getCode() == KeyCode.DOWN){
                    player2Down.play();
                    player2Up.stop();
                }
            }
        });

        track.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.W){
                    player1Up.stop();
                }
                if(keyEvent.getCode() == KeyCode.S){
                    player1Down.stop();
                }
                if(keyEvent.getCode() == KeyCode.UP){
                    player2Up.stop();
                }
                if(keyEvent.getCode() == KeyCode.DOWN){
                    player2Down.stop();
                }
            }
        });
        track.setFocusTraversable(true);
    }

    private void initGame() {

        this.player1Up = new Timeline(new KeyFrame(Duration.millis(3), t -> {
            movePlayer1Up();
        }));
        player1Up.setCycleCount(Animation.INDEFINITE);

        this.player1Down = new Timeline(new KeyFrame(Duration.millis(3), t -> {
            movePlayer1Down();
        }));
        player1Down.setCycleCount(Animation.INDEFINITE);

        this.player2Up = new Timeline(new KeyFrame(Duration.millis(3), t -> {
            movePlayer2Up();
        }));
        player2Up.setCycleCount(Animation.INDEFINITE);

        this.player2Down = new Timeline(new KeyFrame(Duration.millis(3), t -> {
            movePlayer2Down();
        }));
        player2Down.setCycleCount(Animation.INDEFINITE);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                detectCollide(player1, player2, topWall, topWall2, bottomWall, bottomWall2);
            }
        };
        animationTimer.start();
    }

    private void movePlayer1Up() {
        player1.setTranslateY(player1.getTranslateY()-1);
    }

    private void movePlayer1Down() {
        player1.setTranslateY(player1.getTranslateY()+1);
    }

    private void movePlayer2Up() {
        player2.setTranslateY(player2.getTranslateY()-1);
    }

    private void movePlayer2Down() {
        player2.setTranslateY(player2.getTranslateY()+1);
    }

    private void detectCollide(Rectangle player1, Rectangle player2, Rectangle topWall, Rectangle topWall2,
                               Rectangle bottomWall, Rectangle bottomWall2) {
        if(player1.getBoundsInParent().intersects(topWall2.getBoundsInParent())) {
            player1Up.setRate(0);
        }else{
            player1Up.setRate(1);
        }
        if(player1.getBoundsInParent().intersects(bottomWall2.getBoundsInParent())) {
            player1Down.setRate(0);
        }else{
            player1Down.setRate(1);
        }
        if(player2.getBoundsInParent().intersects(topWall2.getBoundsInParent())) {
            player2Up.setRate(0);
        }else{
            player2Up.setRate(1);
        }
        if(player2.getBoundsInParent().intersects(bottomWall2.getBoundsInParent())) {
            player2Down.setRate(0);
        }else{
            player2Down.setRate(1);
        }
    }
}