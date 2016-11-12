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
    private final double  MAXSP = 5.0;

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
    public void setX(double val){x =val;}
    public void setY(double val){y =val;}
    public void setVl(double val){vl = val;}
    public void setVr(double val){vr = val;}
    public void setAngle(double val){angle = val;}
//    public final StringProperty firstNameProperty() { return firstName; }
//    public final String getFirstName() { return firstNameProperty().get(); }
//    public final void setFirstName(final String firstName) { firstNameProperty().set(firstName); }
//    public final StringProperty lastNameProperty() { return lastName; }
//    public final String getLastName() { return lastNameProperty().get(); }
//    public final void setLastName(final String lastName) { lastNameProperty().set(lastName); }

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
		this.x = state[0];
		this.y = state[1];
		this.vr = state[2];
		this.vl = state[3];
		this.angle = state[4];
	}
}
