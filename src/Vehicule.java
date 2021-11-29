import java.awt.image.BufferedImage;

public class Vehicule {
	double x;
	double y;
	double a;
	double dx;
	double da;
	BufferedImage image;
	String mode;
	
	public Vehicule(double x, double y, double a, double dx, double da, BufferedImage image, String mode) {
		this.x = x;
		this.y = y;
		this.a = a;
		this.dx = dx;
		this.da = da;
		this.image = image;	
		mode = "NORMAL";
	}

	public void tourner_d() {
		if (da < 0) {
			da = 0; }
		if (Math.abs(da) < (20)) 
		{da += (1);}
		a += da;
	}
	
	public void tourner_g() {
		if (da > 0) {
			da = 0; }
		if (Math.abs(da) < (20)) 
		{da -= (1);}
		a += da;
	}
	
	public void avancer() {
		if (dx < 0) {
		dx = 0; }
		if (Math.abs(dx) < 10)
		{dx +=0.1;}
		y += -1*dx*Math.cos(Math.toRadians(a));
		x += dx*Math.sin(Math.toRadians(a));
	}
	
	public void reculer() {
		if (dx >0) {
		dx = 0;}
		if (Math.abs(dx) < 10)
		{dx -=0.1;}
		y += -1*dx*Math.cos(Math.toRadians(a));
		x += dx*Math.sin(Math.toRadians(a));
	}
	
	public void ralentirdx( ) {
		if (dx >= 0.2 ) {
			dx -= 0.2;
		}
		if (dx <= -0.2 ) {
			dx += 0.2;
		}
		
		if (dx <= 0.2 && dx >= -0.2 ) {
			dx = 0;
		}
		y += -1*dx*Math.cos(Math.toRadians(a));
		x += dx*Math.sin(Math.toRadians(a));
	}
		public void ralentirda( ) {
			if (da >= 2 ) {
				da -= 2;
			}
			if (da <= -2 ) {
				da += 2;
			}
			
			if (da <= 2 && da >= -2 ) {
				da = 0;
			}
			a+= da;
		}
		
	public void arreter() {
		dx = 0;
		da=0;
		
	}
	
	

}
	

	
	