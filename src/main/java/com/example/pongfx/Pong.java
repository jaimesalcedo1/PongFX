package com.example.pongfx;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class Pong extends BorderPane {

    private StackPane track;
    private Rectangle player1, player2, rightWall, leftWall, topWall, topWall2,bottomWall, bottomWall2, centerLine;
    private Circle ball;
    private Label score;
    private PongController controller;

    public Pong(){

        this.track = new StackPane();
        this.player1 = new Rectangle();
        this.player2 = new Rectangle();
        this.rightWall = new Rectangle();
        this.leftWall = new Rectangle();
        this.topWall = new Rectangle();
        this.topWall2 = new Rectangle();
        this.bottomWall = new Rectangle();
        this.bottomWall2 = new Rectangle();
        this.centerLine = new Rectangle();
        this.ball = new Circle();
        this.score = new Label("0   0");
        this.controller = new PongController(track, player1, player2, rightWall, leftWall, topWall, topWall2, bottomWall, bottomWall2, ball, score);

        initView();
    }

    private void initView(){

        track.prefHeightProperty().bind(this.heightProperty());
        track.prefHeightProperty().bind(this.widthProperty());
        track.setMinSize(0,0);
        track.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        track.setStyle("-fx-background-color: black");
        rightWall.heightProperty().bind(track.heightProperty());
        rightWall.widthProperty().bind(track.widthProperty().divide(200));
        rightWall.setFill(Color.rgb(188, 19, 254));
        leftWall.heightProperty().bind(track.heightProperty());
        leftWall.widthProperty().bind(track.widthProperty().divide(200));
        leftWall.setFill(Color.rgb(188, 19, 254));
        topWall.heightProperty().bind(track.heightProperty().divide(120));
        topWall.widthProperty().bind(track.widthProperty());
        topWall.setFill(Color.rgb(188, 19, 254));
        topWall2.heightProperty().bind(track.heightProperty().divide(120));
        topWall2.widthProperty().bind(track.widthProperty());
        topWall2.translateYProperty().bind(topWall.translateXProperty().add(7));
        topWall2.setFill(Color.BLACK);
        bottomWall.heightProperty().bind(track.heightProperty().divide(120));
        bottomWall.widthProperty().bind(track.widthProperty());
        bottomWall.setFill(Color.rgb(188, 19, 254));
        bottomWall2.heightProperty().bind(track.heightProperty().divide(120));
        bottomWall2.widthProperty().bind(track.widthProperty());
        bottomWall2.translateYProperty().bind(bottomWall.translateXProperty().subtract(7));
        bottomWall2.setFill(Color.BLACK);
        centerLine.heightProperty().bind(track.heightProperty());
        centerLine.widthProperty().bind(track.widthProperty().divide(400));
        centerLine.setFill(Color.rgb(188, 19, 254));
        ball.radiusProperty().bind(track.heightProperty().divide(75));
        ball.setFill(Color.RED);
        player1.heightProperty().bind(track.heightProperty().divide(10));
        player1.widthProperty().bind(track.widthProperty().divide(100));
        player1.translateXProperty().bind(rightWall.translateXProperty().add(20));
        player1.setFill(Color.WHITE);
        player2.heightProperty().bind(track.heightProperty().divide(10));
        player2.widthProperty().bind(track.widthProperty().divide(100));
        player2.translateXProperty().bind(rightWall.translateXProperty().subtract(20));
        player2.setFill(Color.WHITE);
        score.prefHeightProperty().bind(track.heightProperty().divide(60));
        score.prefWidthProperty().bind(track.widthProperty().divide(20));
        score.translateYProperty().bind(topWall.translateXProperty().add(5));
        score.setAlignment(Pos.CENTER);
        score.setTextFill(Color.WHITE);

        track.getChildren().addAll(topWall2, bottomWall2, rightWall, leftWall, topWall, bottomWall, centerLine, ball, player1, player2, score);
        track.setAlignment(topWall2, Pos.TOP_CENTER);
        track.setAlignment(bottomWall2, Pos.BOTTOM_CENTER);
        track.setAlignment(rightWall, Pos.CENTER_RIGHT);
        track.setAlignment(leftWall, Pos.CENTER_LEFT);
        track.setAlignment(topWall, Pos.TOP_CENTER);
        track.setAlignment(bottomWall, Pos.BOTTOM_CENTER);
        track.setAlignment(centerLine, Pos.CENTER);
        track.setAlignment(ball, Pos.CENTER);
        track.setAlignment(player1, Pos.CENTER_LEFT);
        track.setAlignment(player2, Pos.CENTER_RIGHT);
        track.setAlignment(score, Pos.TOP_CENTER);
        track.setStyle("-fx-background-image: url(background.jpg)");

        this.setCenter(track);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.setMinSize(0, 0);
    }

}
