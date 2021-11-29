// -------------------------------------------------------
//    Algorithmique et programmation en Java
// Ecole Nationale Supérieure des Mines de PARIS
//      Cours d'informatique -  1ère année
// -------------------------------------------------------
//  Interfaces Homme-Machines, utilisation d'AWT
// -------------------------------------------------------

import javax.swing.JPanel;

/** 
 * Cette classe h�rite de JPanel ; elle est con�ue pour servir � son tour de classe
 * m�re lorsqu'on a besoin d'un JPanel affichant une animation (elle fournit alors
 * la boucle principale d'animation.)
 * 
 * Pour l'utiliser il convient de cr�er une sous-classe et d'y red�finir, si besoin, 
 * les m�thodes deplacer() et paint(Graphics g)
 */
public class JPanelAnime extends JPanel implements Runnable {
  // Attribut permettant le réglage de l'animation :
  protected int dureeSommeil;     
  // Attribut permettant la mise en pause de l'animation :
  protected boolean continuerAnimation = true;     

  // Constructeurs :
  public JPanelAnime(int d) { 
    super();    // NOTA-BENE : Un JPanel fonctionne par d�faut en mode double-buffering 
    dureeSommeil = d; 
  }
  public JPanelAnime() { 
    this(1);   // Rafraichit tous les milli�me de seconde
  }

  public void run() {
    while ( continuerAnimation == true ) {
      deplacer();                    // calcule la position suivante
      repaint();// demande le re-dessin du Canvas
      try { 
        Thread.sleep(dureeSommeil);  // mise en someil du thread
      } catch (Exception e) {}
    } 
  }
  
  // Boucle g�rant l'animation :
  public void animer() {
    Thread t = new Thread(this);
    t.start();
  }
  
  // M�thode destin�e � �tre red�finie, si besoin, dans les classes filles
  public void deplacer() {   
  }
}