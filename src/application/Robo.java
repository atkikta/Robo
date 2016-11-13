package application;
import javafx.beans.NamedArg;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Robo {
    private final double width ;
    private final double height ;
    private final double wRadius;
    private final double wDistance;
    private double x;
    private double y;
    private double vr = 0.0;
    private double vl = 0.0;
    private double angle = 0.0;
    private double distance =0.0;
    private double battery  =100.0;
    private final double  MAXSP = 2.0;
    private final double MAXDIS = 800;

    public Robo(@NamedArg("width") double width, @NamedArg("height") double height,
    		@NamedArg("wRadius") double wRadius,@NamedArg("wDistance") double wDistance) {
        this.width = width;
        this.height = height;
        this.wRadius = wRadius;
        this.wDistance = wDistance;
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

	public double[] forward(String direction) {
		double[] state = new double[5];
		double x,y,vr,vl,angle;
		if(direction.equals("LEFT")){
			vr = MAXSP;
			vl = MAXSP/2.0;
		}else if(direction.equals("UP")){
			vr = MAXSP;
			vl = MAXSP;
		}else if(direction.equals("RIGHT")){
			vr = MAXSP/2.0;
			vl = MAXSP;
		}else{
			vr = -MAXSP;
			vl = -MAXSP;
		}
		angle = this.angle - (vr - vl)/wDistance*360/(2*Math.PI);
		x = this.x + (vr + vl)/2.0 * Math.sin(angle/180 *Math.PI);
		y = this.y - (vr + vl)/2.0 * Math.cos(angle/180 *Math.PI); 
		state[0] = x; state[1] = y; state[2] = vr; state[3] = vl; state[4] = angle;
		return state;
	}

	public void updateState(double[] state) {
		distance += Math.sqrt(Math.pow((x+width/2.0*Math.cos(angle/180*Math.PI)
										-(state[0]+width/2.0*Math.cos(state[4]/180*Math.PI))), 2) 
							+ Math.pow((y-width/2.0*Math.sin(angle/180*Math.PI)
										-(state[1]-width/2.0*Math.sin(state[4]/180*Math.PI))), 2));
		battery = (MAXDIS - distance)/MAXDIS*100;
		this.x = state[0];
		this.y = state[1];
		this.vr = state[2];
		this.vl = state[3];
		this.angle = state[4];
	}
}
