package MG2D;

import MG2D.geometrie.Dessin;
import MG2D.geometrie.Image;
import MG2D.geometrie.Point;
import MG2D.geometrie.Rectangle;
import MG2D.geometrie.Texte;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import java.awt.Graphics;

public class Panneau extends JPanel {
    private List<Dessin> listeDessins = new ArrayList<>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Dessin d : listeDessins) {
            if (d instanceof Rectangle) {
                drawRectangle((Rectangle) d, g);
            } else if (d instanceof Image) {
                drawImage((Image) d, g);
            } else if (d instanceof Texte) {
                drawText((Texte) d, g);
            }
        }
    }

    private void drawRectangle(Rectangle rect, Graphics g) {
        Point pos = rect.getPosition();
        Couleur c = rect.getCouleur();
        g.setColor(new java.awt.Color(c.getR(), c.getG(), c.getB(), c.getA()));
        if (rect.isPlein()) {
            g.fillRect(pos.getX(), pos.getY(), rect.getLargeur(), rect.getHauteur());
        } else {
            g.drawRect(pos.getX(), pos.getY(), rect.getLargeur(), rect.getHauteur());
        }
    }

    private void drawImage(Image img, Graphics g) {
        try {
            java.awt.Image awtImage = javax.imageio.ImageIO.read(new java.io.File(img.getChemin()));
            if (awtImage != null) {
                g.drawImage(
                    awtImage,
                    img.getPosition().getX(),
                    img.getPosition().getY(),
                    img.getLargeur(),
                    img.getHauteur(),
                    this
                );
            }
        } catch (Exception e) {
            System.err.println("Error drawing image: " + img.getChemin());
            g.setColor(java.awt.Color.RED);
            g.fillRect(img.getPosition().getX(), img.getPosition().getY(), 
                       img.getLargeur(), img.getHauteur());
        }
    }

    private void drawText(Texte txt, Graphics g) {
        g.setFont(txt.getPolice());
        Couleur c = txt.getCouleur();
        g.setColor(new java.awt.Color(c.getR(), c.getG(), c.getB()));
        g.drawString(txt.getTexte(), txt.getPosition().getX(), txt.getPosition().getY());
    }

    public List<Dessin> getListeDessins() {
        return new ArrayList<>(listeDessins);
    }

    public void ajouter(Dessin d) {
        if (d != null) {
            listeDessins.add(d);
            repaint();
        }
    }

    public void ajouterTous(List<Dessin> dessins) {
        if (dessins != null) {
            listeDessins.addAll(dessins);
            repaint();
        }
    }

    public void supprimer(Dessin d) {
        if (d != null && listeDessins.contains(d)) {
            listeDessins.remove(d);
            repaint();
        }
    }

    public void clear() {
        listeDessins.clear();
        repaint();
    }

    public int getHauteur() {
        return this.getHeight();
    }

    public int getLargeur() {
        return this.getWidth();
    }

    public void repaintPanneau() {
        this.repaint();
    }

    public void remplacer(Dessin ancien, Dessin nouveau) {
        supprimer(ancien);
        ajouter(nouveau);
}
}
