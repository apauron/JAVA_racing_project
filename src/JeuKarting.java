import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JeuKarting implements ActionListener {
	JFrame f; // la fenètre principale du jeu
	JDessin c; // le canvas ou on dessine la cible et le(s) palets
	JButton r;
	JButton p;
	JButton l;
	JButton ajouterflaque;
	JButton ajoutermur;
	JPanel d;
	JPanel chrono;
	JPanel tout;
	JLabel millis;
	JLabel seconde;
	JLabel minute;
	static int milli;
	static int secondes;
	static int minutes;
	static boolean etat = true;
	JLabel picLabel;
	JButton nouvellepartie;
	JTextField nomj;

	public JeuKarting()  throws IOException  {

		// création de la fenètre (classe Frame) :
		f = new JFrame("Jeu de Karting...");
		d = new JPanel();

		tout = new JPanel();
		chrono = new JPanel();
		// création du canvas animé pour le jeu :
		c = new JDessin(50);
		c.setFocusable(true);
		// création de boutons :
		l = new JButton("Lancer une partie");
		p = new JButton("Pause");
		r = new JButton("Reprendre la partie");
		nomj = new JTextField("Nom du joueur");
		nouvellepartie = new JButton("Nouvelle partie");
		ajouterflaque = new JButton("Ajouter une flaque");
		ajoutermur = new JButton("Ajouter un mur");

		millis = new JLabel("00  : ");
		seconde = new JLabel("00  : ");
		minute = new JLabel("00  ");
		// ajout des sous composants à la frame et affichage :
		BufferedImage myPicture = (c
				.toBufferedImage(ImageIO.read(new File("C:\\Users\\Adrien\\Desktop\\Imagejava\\image_accueil.jpg"))
						.getScaledInstance(c.L_CANVAS, c.H_CANVAS, Image.SCALE_DEFAULT)));
		picLabel = new JLabel(new ImageIcon(myPicture));
		picLabel.resize(c.L_CANVAS, c.H_CANVAS);
		d.add(l);
		d.add(nomj);
		f.add(picLabel);

		f.add(d, BorderLayout.SOUTH);
		f.pack();

		// pour sortir du programme si on ferme la fenêtre (par défaut la fenêtre est
		// juste masquée) :
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// rend le bouton réactif au clic :
		p.addActionListener(this);
		l.addActionListener(this);
		r.addActionListener(this);
		ajouterflaque.addActionListener(this);
		ajoutermur.addActionListener(this);
		nouvellepartie.addActionListener(this);
		// lancement de l'animation lorsque le bouton "lancer" est sélectionné :

		f.setVisible(true);
		boolean vrai = true;
		while(vrai) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (c.lignefranchie()) {
				JeuKarting.etat = false;
				c.continuerAnimation=false;
				d.remove(p);
				d.add(nouvellepartie);
				f.pack();
			}
		}

	}

	// Méthode invoquée pour les événements de type "action" :
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == l) {
			if(!(nomj.getText().equals("")))
			{f.remove(picLabel);
			f.add(c, BorderLayout.NORTH);
			c.nomdujoueur = nomj.getText();
			d.add(p);
			d.remove(nomj);
			d.remove(l);
			c.ajouteruneflaque();
			c.ajouterunobstacle();
			etat = true;}
		}

		if (e.getSource() == p) {
			d.remove(p);
			d.add(ajouterflaque);
			d.add(ajoutermur);
			d.add(r);
			d.add(nouvellepartie);
			c.continuerAnimation = false;
			tout.add(d);
			tout.add(chrono);
			etat = false;

		}

		if (e.getSource() == r) {
			d.remove(r);
			d.add(p);
			d.remove(ajouterflaque);
			d.remove(ajoutermur);
			d.remove(nouvellepartie);
			etat = true;
			c.continuerAnimation = true;

		}

		if (e.getSource() == ajouterflaque) {
			c.ajouteruneflaque();

		}

		if (e.getSource() == ajoutermur) {
			c.ajouterunobstacle();
		}

		if (e.getSource() == nouvellepartie) {
			milli = 0;
			secondes = 0;
			minutes = 0;
			c.reinitialiser();
			c.continuerAnimation = true;
			d.remove(nouvellepartie);
			d.remove(r);
			d.remove(ajouterflaque);
			d.remove(ajoutermur);
			d.add(p);
			d.remove(l);
			c.ajouteruneflaque();
			c.ajouterunobstacle();
			etat = true;
		}
		Thread t = new Thread() {
			public void run() {
				for (;;) {
					if (etat == true) {
						try {
							sleep(23);
							milli+=23;
							if (milli > 999) {
								milli = 0;
								secondes++;
							}

							if (secondes > 59) {
								secondes = 0;
								minutes++;
							}

							millis.setText(" : " + milli);

							seconde.setText(" : " + secondes);
							minute.setText("" + minutes);

						}

						catch (Exception e) {

						}
					}

					else {
						break;
					}

				}
			}
		};
		t.start();
		chrono.add(minute);
		chrono.add(seconde);
		chrono.add(millis);
		c.setFocusable(true);
		c.animer();
		tout.add(chrono);
		tout.add(d);
		f.add(tout);
		f.pack();
		
	}
	
	

	static public void main(String[] args) throws IOException {
		new JeuKarting();
	}
	
}
