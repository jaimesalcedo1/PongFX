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

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class PongController {

    private StackPane track;
    private Rectangle player1, player2, rightWall, leftWall, topWall, topWall2, bottomWall, bottomWall2;
    private Circle ball;
    private Label score;
    private int axisX, axisY, scoreP1, scoreP2;;
    private Timeline player1Up, player1Down, player2Up, player2Down, ballMove;
    private boolean game;
    private double ballVelocity, playerVelocity;

    private AudioInputStream backgroundMusic;
    private Clip trackBG;
    private String collisionWav = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+
            File.separator+"resources"+File.separator+"collision.wav";
    private String pointWav = System.getProperty("user.dir")+ File.separator+"src"+File.separator+"main"+
            File.separator+"resources"+File.separator+"point.wav";
    private String backgroundWav = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+
            File.separator+"resources"+File.separator+"background.wav";
    
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
        this.scoreP1 = 0;
        this.scoreP2 = 0;
        this.ballVelocity = 1;
        this.playerVelocity = 1;
        this.axisX = random();
        this.axisY = random();
        this.game = false;

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
                if(keyEvent.getCode() == KeyCode.ENTER){
                    if(!game){
                        try {
                            startGame();
                        } catch (UnsupportedAudioFileException e) {
                            e.printStackTrace();
                        } catch (LineUnavailableException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            restartGame();
                        } catch (UnsupportedAudioFileException e) {
                            e.printStackTrace();
                        } catch (LineUnavailableException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    detectCollision(player1, player2, topWall, topWall2, bottomWall, bottomWall2, ball);
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        animationTimer.start();

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

        this.ballMove = new Timeline(new KeyFrame(Duration.millis(10), t -> {
            moveBall();
        }));
        ballMove.setCycleCount(Animation.INDEFINITE);

    }

    private void startGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        startMusic();
        game = true;
        ballMove.play();
    }

    private void restartGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        trackBG.stop();
        ball.setTranslateX(0);
        ball.setTranslateY(0);
        scoreP1 = 0;
        scoreP2 = 0;
        score.setText("0   0");
        startGame();
    }

    private void movePlayer1Up() {
        player1.setTranslateY(player1.getTranslateY()-playerVelocity);
    }

    private void movePlayer1Down() {
        player1.setTranslateY(player1.getTranslateY()+playerVelocity);
    }

    private void movePlayer2Up() {
        player2.setTranslateY(player2.getTranslateY()-playerVelocity);
    }

    private void movePlayer2Down() {
        player2.setTranslateY(player2.getTranslateY()+playerVelocity);
    }

    private void moveBall(){
        if(axisX == 1 && axisY == -1){
            ball.setTranslateX(ball.getTranslateX()+ballVelocity);
            ball.setTranslateY(ball.getTranslateY()+ballVelocity);
        }
        if(axisX == -1 && axisY == -1){
            ball.setTranslateX(ball.getTranslateX()-ballVelocity);
            ball.setTranslateY(ball.getTranslateY()+ballVelocity);
        }
        if(axisX == 1 && axisY == 1){
            ball.setTranslateX(ball.getTranslateX()+ballVelocity);
            ball.setTranslateY(ball.getTranslateY()-ballVelocity);
        }
        if(axisX == -1 && axisY == 1){
            ball.setTranslateX(ball.getTranslateX()-ballVelocity);
            ball.setTranslateY(ball.getTranslateY()-ballVelocity);
        }
    }

    private void detectCollision(Rectangle player1, Rectangle player2, Rectangle topWall, Rectangle topWall2,
                                 Rectangle bottomWall, Rectangle bottomWall2, Circle ball) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

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

        if(player2.getBoundsInParent().intersects(bottomWall2.getBoundsInParent())){
            player2Down.setRate(0);
        }else{
            player2Down.setRate(1);
        }

        if(ball.getBoundsInParent().intersects(topWall.getBoundsInParent())){
            axisY = -1;
            collisionSound();
        }

        if(ball.getBoundsInParent().intersects(bottomWall.getBoundsInParent())){
            axisY = 1;
            collisionSound();
        }

        if(ball.getBoundsInParent().intersects(player1.getBoundsInParent())){
            axisX = 1;
            ballVelocity = ballVelocity + 0.05;
            collisionSound();
        }

        if(ball.getBoundsInParent().intersects(player2.getBoundsInParent())){
            axisX = -1;
            ballVelocity = ballVelocity + 0.05;
            collisionSound();
        }

        if(ball.getBoundsInParent().intersects(leftWall.getBoundsInParent())){
            ball.setTranslateX(0);
            ball.setTranslateY(0);
            pointSound();
            scoreP2 = scoreP2 + 1;
            score.setText(scoreP1+"   "+scoreP2);
        }

        if(ball.getBoundsInParent().intersects(rightWall.getBoundsInParent())){
            ball.setTranslateX(0);
            ball.setTranslateY(0);
            pointSound();
            scoreP1 = scoreP1 + 1;
            score.setText(scoreP1+"   "+scoreP2);
        }

    }

    private int random(){
        int number = new Random().nextInt(2);
        if(number == 0){
            return -1;
        }else{
            return 1;
        }
    }

    private void startMusic() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        backgroundMusic = AudioSystem.getAudioInputStream(new File(backgroundWav).getAbsoluteFile());
        trackBG = AudioSystem.getClip();
        trackBG.open(backgroundMusic);
        trackBG.start();
    }
    private void pointSound() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(pointWav).getAbsoluteFile());
        Clip track = AudioSystem.getClip();
        track.open(ais);
        track.start();
    }

    private void collisionSound() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(collisionWav).getAbsoluteFile());
        Clip track = AudioSystem.getClip();
        track.open(ais);
        track.start();
    }

}