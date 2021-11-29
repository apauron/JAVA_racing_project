import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;


public class Piste {
	BufferedImage img;
	Color couleur_fond;
	CopyOnWriteArrayList<Rectangle> flaques;
	CopyOnWriteArrayList<Rectangle> murs;
	public Piste(BufferedImage image, Color couleur) {
		img = image;
		couleur_fond = couleur;

	}
	
	public void colorierfond(int x, int y, int largeur, int hauteur) // Permet de colorier le fond de la bonne couleur
	{BufferedImage image = new BufferedImage(img.getColorModel(),img.copyData(null),img.isAlphaPremultiplied(),null);
	Graphics2D g = img.createGraphics();
	g.setColor(couleur_fond);
	g.fillRect(x, y, largeur, hauteur);
	g.drawImage(image, x, y, null);
	}
	

	
	public int[] ajouterlignedepart(int hauteur, int largeur) {
		
		int testx = (int) largeur/3;
		int testy = 0;
		while (img.getRGB(testx, testy) == couleur_fond.getRGB()) {
			testy+=1;
		}
		int largeurpiste = 0;
		while (img.getRGB(testx, testy) != couleur_fond.getRGB()) {
			largeurpiste +=1;
			testy+=1;
		}
		for (int i =testx; i<= testx +10; i++) {
			for (int j =testy; j>= testy -largeurpiste; j--) {
				img.setRGB(i, j, Color.GRAY.getRGB());
			}
			
		}
		int tab[] = {testx,testy,largeurpiste};
		return tab;
	}
	

}

	