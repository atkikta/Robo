package application;
import javafx.beans.NamedArg;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * This is the class for the robot to be simulated.
 * It reads the attributes from a fxml file.
 * Methods to update the state of the robot are implemented.
 * @author Kita
 */

public class Robo {
    private final double width;
    private final double height ;
    private final double wDistance;
    private double x;
    private double y;
    private double vr = 0.0;
    private double vl = 0.0;
    private double angle = 0.0;
    private double distance =0.0;
    private double battery  =100.0;
    private final double maxSp ;
    private final double maxDis= 800;
    
    public Robo(){
    	width = 10;
    	height = 10;
    	wDistance =10;
    	x = 10;
    	y = 100;
    	maxSp = 2;
    }
    /**
     * Instance is created based on a fxml file.
     * @param width
     * @param height
     * @param wDistance
     * @param speed
     */
    public Robo(@NamedArg("width") double width, @NamedArg("height") double height,
    		@NamedArg("wDistance") double wDistance,@NamedArg("speed") double speed) {
        this.width = width;
        this.height = height;
        this.wDistance = wDistance;
        this.maxSp = speed;
    }

    public double getWidth(){return width;}
    public double getHeight(){return height;}
    public double getX(){return x;}
    public double getY(){return y;}
    public double getVl(){return vl;}
    public double getVr(){return vr;}
    public double getAngle(){return angle;}
    public double getDistance(){return distance;}
    public double getBattery(){return battery;}
    public void setX(double val){x =val;}
    public void setY(double val){y =val;}
    public void setVl(double val){vl = val;}
    public void setVr(double val){vr = val;}
    public void setAngle(double val){angle = val;}
    public void setDistance(double val){distance = val;}
    public void setBattery(double val){battery = val;}

    
    /**
     * Calcurate the one state of the robot one step forward.
     * @param direction String either one of "UP","DOWN","LEFT", or"RIGHT".
     * @return array of length 5; {x, y, vr, vl, angle}.
     * <ul>
	 * <li>x: x coordinate of the left upper corner of the robot.
	 * <li>y: y coordinate of the left upper corner of the robot.
	 * <li>vr: speed of the right wheel.
	 * <li>vl: speed of the left wheel.
	 * <li>angle: rotation of the robot, measured clockwise in radian.
	 * </ul>
     */
	public double[] forward(String direction) {
		double[] state = new double[5];
		double x,y,vr,vl,angle;
		if(direction.equals("LEFT")){
			vr = maxSp;
			vl = maxSp/2.0;
		}else if(direction.equals("UP")){
			vr = maxSp;
			vl = maxSp;
		}else if(direction.equals("RIGHT")){
			vr = maxSp/2.0;
			vl = maxSp;
		}else{
			vr = -maxSp;
			vl = -maxSp;
		}
		angle = this.angle - (vr - vl)/wDistance*360/(2*Math.PI);
		x = this.x + (vr + vl)/2.0 * Math.sin(angle/180 *Math.PI);
		y = this.y - (vr + vl)/2.0 * Math.cos(angle/180 *Math.PI); 
		state[0] = x; state[1] = y; state[2] = vr; state[3] = vl; state[4] = angle;
		return state;
	}
/**
 * Update the state of the robot based on the given next state.
 * Distance covered, battery left, positin, speed, and rotation are updated.
 * @param state array of length 5; {x, y, vr, vl, angle}.
     * <ul>
	 * <li>x: x coordinate of the left upper corner of the robot.
	 * <li>y: y coordinate of the left upper corner of the robot.
	 * <li>vr: speed of the right wheel.
	 * <li>vl: speed of the left wheel.
	 * <li>angle: rotation of the robot, measured clockwise in radian.
	 * </ul>
 */
	public void updateState(double[] state) {
		distance += Math.sqrt(Math.pow((x+width/2.0*Math.cos(angle/180*Math.PI)
										-(state[0]+width/2.0*Math.cos(state[4]/180*Math.PI))), 2) 
							+ Math.pow((y-width/2.0*Math.sin(angle/180*Math.PI)
										-(state[1]-width/2.0*Math.sin(state[4]/180*Math.PI))), 2));
		battery = (maxDis - distance)/maxDis*100;
		x = state[0];
		y = state[1];
		vr = state[2];
		vl = state[3];
		angle = state[4];
	}
}
