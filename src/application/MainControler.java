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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

/**
 * This class describes the action to be taken by the application when 
 * <ul> <li> a button is pressed.
 *      <li> a key is pressed.
 * </ul>
 * @author Kita
 *
 */
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
	Button replay;
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
	BufferedReader bufferedreader;
	HashSet<String> currentlyActiveKeys;
	
	public MainControler(){
		currentlyActiveKeys = new HashSet<String>();
	}
	/**
	 * This method is called when the Start button (at the left in the window) is clicked.
	 * Initialise the robo and the rectangle, and then start the animation timer.
	 * @param event
	 */
	public void startMode(ActionEvent event){
		if(restart.getText().equals("Restart")){ at.stop(); }
		restart.setText("Restart");
		Robo robo = initializeRobo();	
		initializeRect(robo);
		final long startNanoTime = System.nanoTime();
		at = new StartAnimationTimer(this,startNanoTime,robo);
		at.start();
	}
	/**
	 * This method is called when the Record button (at the middle in the window) is clicked.
	 * Initialise the robo and the rectangle, create the file object, and then start the animation timer.
	 * During recording, other buttons are disabled, and the text on record button becomes "stop".
	 * When the recording is stopped, other buttons are enabled and the animation timer stops.
	 * 
	 * @param event
	 */
	public void recordMode(ActionEvent event){
		// if current recording process is on, stop it.
		if(restart.getText().equals("Restart")){
			at.stop(); 
			restart.setText("Start");}
		if(record.getText().equals("Stop")){ 
			restart.setDisable(false);
			replay.setDisable(false);
			at.stop(); 
			record.setText("Record");
			try {
				filewriter.close();
			}catch (IOException e) {
				System.out.println(e);
			}
		}//if currently not recording, start recording.
		else{
			restart.setDisable(true);
			replay.setDisable(true);
			record.setText("Stop");
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
	/**
	 * This method is called when the Replay button (at the right in the window) is clicked.
	 * Initialise the robo and the rectangle, create the file object, and then start the animation timer.
	 * During recording, other buttons are disabled, and the text on replay button becomes "stop".
	 * When the replay is stopped, other buttons are enabled and the animation timer stops.
	 *
	 * @param event
	 */
	public void replayMode(ActionEvent event){
		// if current recording process is on, stop it.
		if(restart.getText().equals("Restart")){
			at.stop(); 
			restart.setText("Start");}
		if(replay.getText().equals("Stop")){ 
			restart.setDisable(false);
			record.setDisable(false);
			at.stop(); 
			replay.setText("Replay");
			try {
				bufferedreader.close();
			}catch (IOException e) {
				System.out.println(e);
			}
		}//if currently not recording, start recording.
		else{
			restart.setDisable(true);
			record.setDisable(true);
			replay.setText("Stop");
			try{
				bufferedreader = new BufferedReader(new FileReader(new File("record.txt")));
				Robo robo = initializeRobo();	
				initializeRect(robo);
					
				final long startNanoTime = System.nanoTime();
				at = new ReplayAnimationTimer(this,startNanoTime,robo,bufferedreader);
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
	/**
	 * When a key is pressed, the name of the key is stored in "currentlyActiveKeys".
	 * It is stored in the HashSet in order to avoid duplication.
	 * 
	 * @param event
	 */
	public void addPressedKey(KeyEvent event){
		currentlyActiveKeys.add(event.getCode().toString());
	}
	/**
	 * When a key is released, the key is removed from the HashSet "currentlyActiveKeys".
	 * @param event
	 */
	public void removeReleasedKey(KeyEvent event){
		currentlyActiveKeys.remove(event.getCode().toString());
	}
}
