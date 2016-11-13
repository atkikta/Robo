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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
public class MainControler {
	@FXML
	Label timeLabel;
	@FXML
	Label locLabel;
	@FXML
	Rectangle rect;
	@FXML
	Button restart;
	@FXML
	Button record;
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


	AnimationTimer at;
	FileWriter filewriter;
	HashSet<String> currentlyActiveKeys;
	public MainControler(){
		currentlyActiveKeys = new HashSet<String>();
	}
	
	public void startMode(ActionEvent event){
		Button b = (Button)event.getSource();
		if(b.getText().equals("Restart")){ at.stop(); }
		b.setText("Restart");
		Robo robo = initializeRobo();	
		initializeRect(robo);
		final long startNanoTime = System.nanoTime();
		at = new StartAnimationTimer(this,startNanoTime,robo);
		at.start();
	}
	public void recordMode(ActionEvent event){
		Button b = (Button)event.getSource();
		// if current recording process is on, stop it.
		if(b.getText().equals("Stop")){ 
			at.stop(); 
			b.setText("Record");
			try {
				filewriter.close();
			}catch (IOException e) {
				System.out.println(e);
			}
		}
		else{
			b.setText("Stop");
			try{
				  filewriter = new FileWriter(new File("record.txt"));
				  Robo robo = initializeRobo();	
				  initializeRect(robo);
					
				  final long startNanoTime = System.nanoTime();
				  at = new RecordAnimationTimer(this,startNanoTime,robo,filewriter);
				  at.start();
				  
			}catch(IOException e){
				  System.out.println(e);
			}
		}
		
	}
	private Robo initializeRobo(){
		try{
			Robo robo = FXMLLoader.load(MainControler.class.getResource("/application/Robo.fxml"));
			robo.setX(30.0);
			robo.setY(210.0);
			robo.setAngle(0.0);
			robo.setDistance(0.0);
			robo.setBattery(0.0);
			return robo;
		}catch (IOException e) {
			Robo robo = new Robo();//call constructor in Robo class
			return robo;
		}
	}
	private void initializeRect(Robo robo){
		rect.setX(robo.getX());
		rect.setY(robo.getY());
		rect.setRotate(robo.getAngle());
		rect.setWidth(robo.getWidth());
		rect.setHeight(robo.getHeight());
		locLabel.setText("x: " +String.format("%3.1f", robo.getX())+ ", y: "+String.format("%3.1f", robo.getY())+ ", angle: "+String.format("%3.1f", robo.getAngle()));
		timeLabel.setTextFill(Color.BLACK);
		timeLabel.setText("distance: " + String.format("%3.1f", robo.getDistance())+
				", battery: "+ String.format("%2.1f", robo.getBattery())+"%");

	}
	
	public void addPressedKey(KeyEvent event){
		currentlyActiveKeys.add(event.getCode().toString());
	}
	public void removeReleasedKey(KeyEvent event){
		currentlyActiveKeys.remove(event.getCode().toString());
	}
}
