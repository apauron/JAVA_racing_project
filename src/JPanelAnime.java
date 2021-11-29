// -------------------------------------------------------
//    Algorithmique et programmation en Java
// Ecole Nationale SupÃ©rieure des Mines de PARIS
//      Cours d'informatique -  1Ã¨re annÃ©e
// -------------------------------------------------------
//  Interfaces Homme-Machines, utilisation d'AWT
// -------------------------------------------------------

import javax.swing.JPanel;

/** 
 * Cette classe hérite de JPanel ; elle est conçue pour servir à  son tour de classe
 * mère lorsqu'on a besoin d'un JPanel affichant une animation (elle fournit alors
 * la boucle principale d'animation.)
 * 
 * Pour l'utiliser il convient de créer une sous-classe et d'y redéfinir, si besoin, 
 * les méthodes deplacer() et paint(Graphics g)
 */
public class JPanelAnime extends JPanel implements Runnable {
  // Attribut permettant le rÃ©glage de l'animation :
  protected int dureeSommeil;     
  // Attribut permettant la mise en pause de l'animation :
  protected boolean continuerAnimation = true;     

  // Constructeurs :
  public JPanelAnime(int d) { 
    super();    // NOTA-BENE : Un JPanel fonctionne par défaut en mode double-buffering 
    dureeSommeil = d; 
  }
  public JPanelAnime() { 
    this(1);   // Rafraichit tous les millième de seconde
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
  
  // Boucle gérant l'animation :
  public void animer() {
    Thread t = new Thread(this);
    t.start();
  }
  
  // Méthode destinée à être redéfinie, si besoin, dans les classes filles
  public void deplacer() {   
  }
}