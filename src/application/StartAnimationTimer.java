package application;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Translate;

public class StartAnimationTimer extends AnimationTimer{

	MainControler mainCon;
	Robo robo;
	long startTime;
	ImageView left,right,up,down,leftG,rightG,upG,downG;

	public StartAnimationTimer(MainControler main,long startTime,Robo robo) {
		this.mainCon = main;
		this.robo = robo;
		this.startTime = startTime;
		left = new ImageView(new Image("s_left.png"));
		right = new ImageView(new Image("s_right.png"));
		up = new ImageView(new Image( "s_up.png" ));
		down   = new ImageView(new Image( "s_down.png" ));
		leftG = new ImageView(new Image("s_leftG.png"));
		rightG = new ImageView(new Image("s_rightG.png"));
		upG = new ImageView(new Image( "s_upG.png" ));
		downG   = new ImageView(new Image( "s_downG.png" ));
	}
	@Override
	public void handle(long currentTime) {
		double t = (currentTime - startTime) / 1000000000.0; 
		mainCon.timeLabel.setText(Double.toString(t));

		tickAndRender();
	}


	private void tickAndRender(){
		String[] keys = {"LEFT","RIGHT","UP","DOWN"};
		for(String k :keys){
			switch(k){
			case "LEFT":
				if (mainCon.currentlyActiveKeys.contains(k)){
					mainCon.labelLeft.setGraphic(leftG);
					updateRect(k);
				}
				else{
					mainCon.labelLeft.setGraphic(left);
				}
			case "RIGHT":
				if (mainCon.currentlyActiveKeys.contains(k)){
					mainCon.labelRight.setGraphic(rightG);
					updateRect(k);
				}
				else{
					mainCon.labelRight.setGraphic(right);
				}
			case "UP":
				if (mainCon.currentlyActiveKeys.contains(k)){
					mainCon.labelUp.setGraphic(upG);
					updateRect(k);
				}
				else{
					mainCon.labelUp.setGraphic(up);
				}
			case "DOWN":
				if (mainCon.currentlyActiveKeys.contains(k)){
					mainCon.labelDown.setGraphic(downG);
					updateRect(k);
				}
				else{
					mainCon.labelDown.setGraphic(down);
				}
			}
		}
	}
//		if (mainCon.currentlyActiveKeys.contains("LEFT")){
//			mainCon.labelLeft.setGraphic(leftG);
//			double[] nextState  = robo.forward("LEFT");
//			if(true){
//				robo.updateState(nextState);
//				mainCon.rect.setX(robo.getX());
//				mainCon.rect.setY(robo.getY());
//				mainCon.rect.setRotate(robo.getAngle());
//			}
//		}else{
//			mainCon.labelLeft.setGraphic(left);
//		}
//		
//		if (mainCon.currentlyActiveKeys.contains("RIGHT")){
//			mainCon.labelRight.setGraphic(rightG);
//			double[] nextState  = robo.forward("RIGHT");
//			if(true){
//				robo.updateState(nextState);
//				mainCon.rect.setX(robo.getX());
//				mainCon.rect.setY(robo.getY());
//				mainCon.rect.setRotate(robo.getAngle());
//			}
//		}else{
//			mainCon.labelRight.setGraphic(right);
//		}
//		
//		if (mainCon.currentlyActiveKeys.contains("UP")){
//			mainCon.labelUp.setGraphic(upG);
//			double[] nextState  = robo.forward("UP");
//			if(true){
//				robo.updateState(nextState);
//				mainCon.rect.setX(robo.getX());
//				mainCon.rect.setY(robo.getY());
//				mainCon.rect.setRotate(robo.getAngle());
//			}
//		}else{
//			mainCon.labelUp.setGraphic(up);
//		}
//		
//		if (mainCon.currentlyActiveKeys.contains("DOWN")){
//			mainCon.labelDown.setGraphic(downG);
//			double[] nextState  = robo.forward("DOWN");
//			if(true){
//				robo.updateState(nextState);
//				mainCon.rect.setX(robo.getX());
//				mainCon.rect.setY(robo.getY());
//				mainCon.rect.setRotate(robo.getAngle());
//			}
//		}else{
//			mainCon.labelDown.setGraphic(down);
//		}
	private void updateRect(String k){
		double[] nextState  = robo.forward(k);
		if(true){
			robo.updateState(nextState);
			mainCon.rect.setX(robo.getX());
			mainCon.rect.setY(robo.getY());
			mainCon.rect.setRotate(robo.getAngle());
		}
	}
}
