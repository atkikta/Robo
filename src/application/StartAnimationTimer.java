package application;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
/**
 * This class describes the animation of the application in the Start mode.
 * In the Start mode, the robot moves around the maze according to cursor key.
 * The position and the angle of the robot is shown real time on screen.
 * The distance covered and battery level is shown real time on screen.
 * When the battery level is less than 10 %, the text message becomes red.
 * When the battery level becomes 0, the robot stops.
 * User can restart the game any time.
 * @author Kita
 *
 */
public class StartAnimationTimer extends AnimationTimer{

	MainControler mainCon;
	Robo robo;
	long startTime;
	ImageView left,right,up,down,leftG,rightG,upG,downG;
	protected int time = 0;
	protected double EPS = 0.0000000001;

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
	/**
	 * Update the time and call the function tickAndRender once in 10 msec.
	 */
	@Override
	public void handle(long currentTime) {
		//called evety 10 msec.
		time = time +10;
		tickAndRender();
	}

	/**
	 * Update according to the pressed key.
	 * This function is called at every flame, and check if any key is pressed
	 * (pressed key is stored in "currentlyActiveKeys"), and update the 
	 * labels of picture of cursor key.
	 * Call "updateRect" to update the state of robo and position of rectangle.
	 */
	protected void tickAndRender(){
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
				break;
			case "RIGHT":
				if (mainCon.currentlyActiveKeys.contains(k)){
					mainCon.labelRight.setGraphic(rightG);
					updateRect(k);
				}
				else{
					mainCon.labelRight.setGraphic(right);
				}
				break;
			case "UP":
				if (mainCon.currentlyActiveKeys.contains(k)){
					mainCon.labelUp.setGraphic(upG);
					updateRect(k);
				}
				else{
					mainCon.labelUp.setGraphic(up);
				}
				break;
			case "DOWN":
				if (mainCon.currentlyActiveKeys.contains(k)){
					mainCon.labelDown.setGraphic(downG);
					updateRect(k);
				}
				else{
					mainCon.labelDown.setGraphic(down);
				}
				break;
			}
		}
	}
/*
 * Check if the next step is valid or not by calling "isSafe".
 * If the next step is valid, do the following.
 * 
 * -Update the position, angle, speed, distance and battery of robo.
 * -Update the position of rectangle.
 * 
 */
	protected void updateRect(String k){
		double[] nextState  = robo.forward(k);
		if(robo.getBattery()<0){
			mainCon.timeLabel.setText("No Battery");
			this.stop();
		}
		else if(!isSafe(nextState)){
			mainCon.timeLabel.setText("Crash!!");
		}
		else{
			robo.updateState(nextState);
			double w = robo.getWidth();
			double h = robo.getHeight();
			double ang = robo.getAngle();
			mainCon.rect.setX(robo.getX() +Math.sqrt(w*w+h*h)/2.0*Math.sin(Math.atan(w/h)-ang/180*Math.PI)-w/2.0);
			mainCon.rect.setY(robo.getY() +Math.sqrt(w*w+h*h)/2.0*Math.cos(Math.atan(w/h)-ang/180*Math.PI)-h/2.0);
			mainCon.rect.setRotate(ang);
			if(robo.getBattery()<10.0){
				mainCon.timeLabel.setTextFill(Color.RED);
			}else{
				mainCon.timeLabel.setTextFill(Color.BLACK);
			}
			mainCon.timeLabel.setText("distance: " + String.format("%3.1f", robo.getDistance())+
										", battery: "+ String.format("%2.1f", robo.getBattery())+"%"); 
			mainCon.locLabel.setText("x: " +String.format("%3.1f", robo.getX())+
									", y: "+String.format("%3.1f", robo.getY())+ 
									", angle: "+String.format("%3.1f", robo.getAngle()));

		}
	}
	/*
	 * Checks if the next state is crashing into walls or not.
	 * Receives the information on the next step as array nS = {x,y,vr,vl,angle}.
	 * -x: x coordinate of the left upper corner of the robot.
	 * -y: y coordinate of the left upper corner of the robot.
	 * -vr: speed of the right wheel.
	 * -vl: speed of the left wheel.
	 * -angle: rotation of the robot, measured clockwise in radian.
	 * 
	 * Calculate the position of 4 vertices of robot.
	 * For each combination of walls and sides of the robot, check if the lines are intersected or not.
	 * 
	 * When there is two lines a-b and c-d, if the cross product between the vector ab and achas a different 
	 * sign from the cross product between ab and ad, the points c and d is on the different side of the line ab.
	 * If points c and d are on different side of line ab, and points a and b are on different side of line cd,
	 * then the lines are crossed.
	 * 
	 * 
	 * @param nS Array of length 5 that descrives the next state.
	 * @return Returns true if none of the combinations is intersected.
	 */
	protected boolean isSafe(double[] nS){
		boolean result = true;
		double ang = nS[4]/180*Math.PI;
		double x0 = nS[0];
		double x1 = nS[0]+ robo.getWidth()*Math.cos(ang);
		double x2 = nS[0]+ robo.getWidth()*Math.cos(ang) - robo.getHeight()*Math.sin(ang);
		double x3 = nS[0] -robo.getHeight()*Math.sin(ang);
		double y0 = nS[1];
		double y1 = nS[1] + robo.getWidth()*Math.sin(ang);
		double y2 = nS[1] + robo.getWidth()*Math.sin(ang) + robo.getHeight()*Math.cos(ang);
		double y3 = nS[1] + robo.getHeight()*Math.cos(ang);
		double[] side0 = {x0,y0,x1,y1};
		double[] side1 = {x1,y1,x2,y2};
		double[] side2 = {x2,y2,x3,y3};
		double[] side3 = {x3,y3,x0,y0};
		double[][] sides = {side0,side1,side2,side3};
		double[][] walls = {{0,0,300,0},{300,0,300,300},{300,300,0,300},{0,300,0,0},
							{100,300,100,100},{200,0,200,200}};
		
		for(double[] s:sides){
			for(double[] w:walls){
				if(isIntersected(s,w)){
					result = false;
				}
			}
		}
		return result;
	}
	private boolean isIntersected(double[] s,double[] w){
		return ( cross(s[2]-s[0],s[3]-s[1],w[0]-s[0],w[1]-s[1]) * cross(s[2]-s[0],s[3]-s[1],w[2]-s[0],w[3]-s[1]) < EPS ) &&
		         ( cross(w[2]-w[0],w[3]-w[1],s[0]-w[0],s[1]-w[1]) * cross(w[2]-w[0],w[3]-w[1],s[2]-w[0],s[3]-w[1]) < EPS );
	}
	private double cross(double x0, double y0, double x1, double y1){
		return x0*y1 - x1*y0;
	}
	

}
