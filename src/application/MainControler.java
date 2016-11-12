package application;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
public class MainControler {
	@FXML
	Label timeLabel;
	@FXML
	Rectangle rect;
	@FXML
	Button restart;
	@FXML
	AnchorPane pane;
	@FXML
	Label labelUp;
	@FXML
	Label labelRight;
	@FXML
	Label labelDown;
	@FXML
	Label labelLeft;


	
	HashSet<String> currentlyActiveKeys;
	public MainControler(){
		currentlyActiveKeys = new HashSet<String>();
	}
	
	public void startMode(ActionEvent event){
		Robo robo;
		try {
			robo = FXMLLoader.load(MainControler.class.getResource("/application/Robo.fxml"));
			robo.setX(30.0);
			robo.setY(210.0);
			robo.setAngle(0.0);
			rect.setX(30.0);
			rect.setY(210.0);
			rect.setRotate(0.0);
			rect.setWidth(robo.getWidth());
			rect.setHeight(robo.getHeight());
			final long startNanoTime = System.nanoTime();
			AnimationTimer at = new StartAnimationTimer(this,startNanoTime,robo);
			at.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addPressedKey(KeyEvent event){
		currentlyActiveKeys.add(event.getCode().toString());
		//timeLabel.setText(event.getCode().toString());
		timeLabel.setText(rect.getX() +"" + rect.getY());
	}
	public void removeReleasedKey(KeyEvent event){
		currentlyActiveKeys.remove(event.getCode().toString());
		//timeLabel.setText("");
	}
}
