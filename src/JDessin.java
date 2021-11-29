import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class JDessin extends JPanelAnime implements KeyListener {
	static final int L_CANVAS = 1200;// taille du Canvas en pixels (carré)
	static final int H_CANVAS = 800;
	Piste piste; // la piste
	Vehicule vehicule;// le vehicule
	int departx;
	int departy;
	int largeurpiste;
	BufferedImage image;
	String nomdujoueur;
	TreeMap <Double,String> mapScore = new TreeMap <Double,String>();

	public JDessin(int d) {
		super(d);
		try {
			// appel du constructeur de la classe mère
			setPreferredSize(new Dimension(L_CANVAS, H_CANVAS));
			vehicule = new Vehicule(L_CANVAS - 100, H_CANVAS - 100, -90, 0, 0,
					ImageIO.read(new File("C:\\Users\\Adrien\\Desktop\\Imagejava\\voiture_exemple.png")), "NORMAL");
			piste = new Piste(
					toBufferedImage(ImageIO.read(new File("C:\\Users\\Adrien\\Desktop\\Imagejava\\piste_exemple.png"))
							.getScaledInstance(L_CANVAS, H_CANVAS, Image.SCALE_DEFAULT)),
					Color.WHITE);
			piste.colorierfond(0, 0, L_CANVAS, H_CANVAS);
			int[] tab = piste.ajouterlignedepart(H_CANVAS, L_CANVAS);
			departx = tab[0];
			departy = tab[1];
			vehicule.x = departx - 20;
			largeurpiste = tab[2];
			vehicule.y = departy - (int) largeurpiste / 2;

			addKeyListener(this); // rend le canvas réactif aux touches du clavier
		} catch (IOException exc) {
			exc.printStackTrace();
		}
		image = new BufferedImage(piste.img.getColorModel(), piste.img.copyData(null), piste.img.isAlphaPremultiplied(),
				null);
	}

	public void ajouteruneflaque() {
		boolean danspiste = false;
		int X = 0;
		int Y = 0;
		while (!danspiste) {
			int x = (int) (Math.random() * (L_CANVAS - 30));
			int y = (int) (Math.random() * (H_CANVAS - 30));
			for (int j = y; j <= y + 30; j++) {
				for (int i = x; i <= x + 30; i++) {

					if (piste.img.getRGB(i, j) != piste.couleur_fond.getRGB()) {
						danspiste = true;
					} else {
						danspiste = false;
						break;
					}

				}
				if (!danspiste) {
					break;
				}
			}
			X = x;
			Y = y;
		}
		BufferedImage image = new BufferedImage(piste.img.getColorModel(), piste.img.copyData(null),
				piste.img.isAlphaPremultiplied(), null);
		Graphics2D g = piste.img.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.setColor(Color.BLUE);
		g.fillRect(X, Y, 30, 30);

	}

	public void ajouterunobstacle() {
		boolean danspiste = false;
		int X = 0;
		int Y = 0;
		while (!danspiste) {
			int x = (int) (Math.random() * (L_CANVAS - 20));
			int y = (int) (Math.random() * (H_CANVAS - 20));
			for (int j = y; j <= y + 20; j++) {
				for (int i = x; i <= x + 20; i++) {

					if (piste.img.getRGB(i, j) != piste.couleur_fond.getRGB() && !toucheflaque()) {
						danspiste = true;
					} else {
						danspiste = false;
						break;
					}
				}
				if (!danspiste) {
					break;
				}
			}
			X = x;
			Y = y;
		}

		BufferedImage image = new BufferedImage(piste.img.getColorModel(), piste.img.copyData(null),
				piste.img.isAlphaPremultiplied(), null);
		Graphics2D g = piste.img.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.setColor(Color.RED);
		g.fillRect(X, Y, 10, 10);

	}

	public boolean lignefranchie() {
		if (vehicule.x >= departx && vehicule.x < departx + 10) {
			if ((vehicule.y >= (departy - largeurpiste)) && (vehicule.y <= departy)) {
				return true;
			}
		}
		return false;
	}

	/* dessin sur le Canvas (méthode invoquée automatiquement lorsque nécessaire) */
	public void paint(Graphics g) {
		double locationX = vehicule.image.getWidth() / 2;
		double locationY = vehicule.image.getHeight() / 2;
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(vehicule.a), locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		// On dessine le fond, puis le véhicule et la piste :
		g.drawImage(piste.img, 0, 0, null);
		g.setColor(Color.BLUE);
		g2d.drawImage(op.filter(vehicule.image, null), (int) vehicule.x, (int) vehicule.y, null);
		try {
			FileReader in = new FileReader("MeilleursScores" + ".txt");
			Scanner sc = new Scanner(in);
			g.setColor(Color.BLUE);
			g.drawString("Meilleur scores", L_CANVAS - 200, H_CANVAS - 200);

			for (int i = 0; i < 4; i++) {
				if (i == 0)
					g.setColor(new Color(255, 215, 0));
				if (i == 1)
					g.setColor(new Color(192, 192, 192));
				if (i == 2)
					g.setColor(new Color(184, 115, 51));
				if(i==3)
				g.setColor(Color.BLACK);
				if (sc.hasNextLine()) {
					String temps = sc.nextLine();
					String[] ligneSplitee = temps.split(":");
					if (ligneSplitee.length > 1) {
					double temp = Double.valueOf(ligneSplitee[1]);
					temps = ""  + (int) temp/60000 + ":" + (int) ((temp%60000)/1000) + ":" +
			(int) (temp%1000);
					g.drawString(ligneSplitee[0] + " : " + temps, L_CANVAS - 200, H_CANVAS - 180+ 15*i);}
					g.drawString(ligneSplitee[0] + " : " , L_CANVAS - 200, H_CANVAS - 180+ 15*i);
				}
			}
			sc.close();
			in.close();

		} catch (IOException e) {
		}
	}

	public boolean estsortidepiste() {
		for (int i = 0; i < vehicule.image.getHeight(); i++) {
			for (int j = 0; j < vehicule.image.getWidth(); j++) {
				if (vehicule.image.getRGB(i, j) != Color.TRANSLUCENT) {
					if (piste.img.getRGB((int) vehicule.x + i, (int) vehicule.y + j) == piste.couleur_fond.getRGB()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean toucheflaque() {
		for (int i = 0; i < vehicule.image.getHeight(); i++) {
			for (int j = 0; j < vehicule.image.getWidth(); j++) {
				if (vehicule.image.getRGB(i, j) != Color.TRANSLUCENT) {
					if (piste.img.getRGB((int) vehicule.x + i, (int) vehicule.y + j) == Color.BLUE.getRGB()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean touchemur() {
		for (int i = 0; i < vehicule.image.getHeight(); i++) {
			for (int j = 0; j < vehicule.image.getWidth(); j++) {
				if (vehicule.image.getRGB(i, j) != Color.TRANSLUCENT) {
					if (piste.img.getRGB((int) vehicule.x + i, (int) vehicule.y + j) == Color.RED.getRGB()) {
						return true;
					}
				}
			}
		}
		return false;
	}



	public int[] entiermur() {
		for (int i = 0; i < vehicule.image.getHeight(); i++) {
			for (int j = 0; j < vehicule.image.getWidth(); j++) {
				if (vehicule.image.getRGB(i, j) != Color.TRANSLUCENT) {
					if (piste.img.getRGB((int) vehicule.x + i, (int) vehicule.y + j) == Color.RED.getRGB()) {
						return new int[] { (int) vehicule.x + i, (int) vehicule.y + j };
					}
				}
			}
		}
		return new int[] { 0, 0 };
	}

	public void saveScore() {
		try {
			PrintWriter out1 = new PrintWriter(new FileWriter("MeilleursScores.txt", true));
			out1.println(nomdujoueur + ":" + JeuKarting.minutes + ":" + JeuKarting.secondes + ":" + JeuKarting.milli);
			out1.close();
			FileReader in = new FileReader("MeilleursScores.txt");
			Scanner sc = new Scanner(in);
			while (sc.hasNextLine()) {
				String[] ligneSplitee = sc.nextLine().split(":");
				if (ligneSplitee.length > 2) {
					double total = Double.valueOf(ligneSplitee[1]) * 60000 + Double.valueOf(ligneSplitee[2]) * 1000
							+ Double.valueOf(ligneSplitee[3]) + 23;
					mapScore.put(total, nomdujoueur);
				
				}
				else {
					mapScore.put(Double.valueOf(ligneSplitee[1]),ligneSplitee[0]);
				}
			}
			sc.close();
			PrintWriter writer = new PrintWriter("MeilleursScores.txt");
			writer.print("");
			writer.close();
			PrintWriter out2 = new PrintWriter(new FileWriter("MeilleursScores" + ".txt", true));
			double cle = mapScore.firstEntry().getKey();
			String nom = mapScore.firstEntry().getValue();
			out2.println(nom + ":" + cle );
			for (int k = 0; k < mapScore.size() -1 && k < 4; k++) {
				cle = mapScore.ceilingKey(cle+1);
				nom = mapScore.get(cle);
				out2.println(nom + ":" + cle );
			}
			out2.close();
		} catch (IOException e) {
		}
	}

	public void reinitialiser() {
		try {
			vehicule = new Vehicule(departx - 20, departy - largeurpiste / 2, -90, 0, 0,
					ImageIO.read(new File("C:\\Users\\Adrien\\Desktop\\Imagejava\\voiture_exemple.png")), "NORMAL");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		touche_h = false;
		touche_b = false;
		touche_d = false;
		touche_g = false;
		addKeyListener(this);
		Graphics2D g = piste.img.createGraphics();
		g.drawImage(image, 0, 0, null);
	}

	public void deplacer() {
		boolean test = (touche_d == false && touche_b == false && touche_h == false && touche_g == false);
		if (test) {
			vehicule.ralentirdx();
		}
		boolean sortie = estsortidepiste();
		vehicule.mode = "NORMAL";
		if (toucheflaque()) {
			vehicule.mode = "GLISSADE";
		}
			if (touche_d == true) {
				if (vehicule.mode == "GLISSADE") {
					vehicule.tourner_d();
					vehicule.tourner_d();
				}
				if (vehicule.mode == "NORMAL") {
					vehicule.tourner_d();
				}
			}
			if (touche_h == true) {
				if (vehicule.mode == "GLISSADE") {
					vehicule.avancer();
					vehicule.tourner_d();
				}
				if (vehicule.mode == "NORMAL") {
					vehicule.avancer();
				}
			}
			if (touche_g == true) {
				if (vehicule.mode == "GLISSADE") {
					vehicule.tourner_g();
					vehicule.tourner_g();
				}
				if (vehicule.mode == "NORMAL") {
					vehicule.tourner_g();
				}
			}
			if (touche_b == true) {
				if (vehicule.mode == "GLISSADE") {
					vehicule.reculer();
					vehicule.tourner_d();
				}
				if (vehicule.mode == "NORMAL") {
					vehicule.reculer();
				}
			}

		if (sortie == true) {
				
				vehicule.dx *= 0.8;


				if (vehicule.x >= L_CANVAS) {
					vehicule.x -= 10;
				}
				if (vehicule.y >= H_CANVAS) {
					vehicule.y -= 10;
				}
				if (vehicule.x <= 0) {
					vehicule.x += 10;
				}
				if (vehicule.y <= 0) {
					vehicule.y += 10;
				

			}
		}
		if (touchemur() == true) {
			if (touche_h == true) {
				touche_h = false;
			}

			if (touche_b == true) {
				touche_b = false;
			}

			if (touche_g == true) {
				touche_g = false;
			}

			if (touche_d == true) {
				touche_d = false;
			}

			vehicule.dx *= -1;
			if (vehicule.dx == 0) {
				int r = entiermur()[0];
				int s = entiermur()[1];

				if (piste.img.getRGB(r + 1, s) == Color.RED.getRGB()) {
					vehicule.x -= 1;
				}
				if (piste.img.getRGB(r, s + 1) == Color.RED.getRGB()) {
					vehicule.y -= 1;
				}
				if (piste.img.getRGB(r - 1, s) == Color.RED.getRGB()) {
					vehicule.x += 1;
				}
				if (piste.img.getRGB(r, s - 1) == Color.RED.getRGB()) {
					vehicule.y += 1;
				}

			}
		}
		if (lignefranchie()) {
			saveScore();
			removeKeyListener(this);
		}

	}



	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	boolean touche_g = false;
	boolean touche_h = false;
	boolean touche_d = false;
	boolean touche_b = false;

	public void keyPressed(KeyEvent e) {

			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				touche_g = true;
				break;

			case KeyEvent.VK_RIGHT:
				touche_d = true;
				break;

			case KeyEvent.VK_UP:
				touche_h = true;
				break;

			case KeyEvent.VK_DOWN:
				touche_b = true;
				break;

			
		}
	}

	public void keyReleased(KeyEvent e) {


			switch (e.getKeyCode()) {

			case KeyEvent.VK_LEFT:
				vehicule.tourner_g();
				touche_g = false;
				break;

			case KeyEvent.VK_RIGHT:
				vehicule.tourner_d();
				touche_d = false;
				break;

			case KeyEvent.VK_UP:
				vehicule.avancer();
				touche_h = false;
				break;

			case KeyEvent.VK_DOWN:
				vehicule.avancer();
				vehicule.reculer();
				touche_b = false;
				break;
			}
		}


	public void keyTyped(KeyEvent e) {
	}

}
