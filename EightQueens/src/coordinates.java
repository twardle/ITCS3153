
public class coordinates {
	
	private int x,y;
	
	coordinates(){
		x = -1;
		y = -1;
	}
	
	coordinates(int x_, int y_){
		x = x_;
		y = y_;
	}
	
	public void setX(int x_){
		x = x_;
	}

	public void setY(int y_){
		y = y_;
	}
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
}
