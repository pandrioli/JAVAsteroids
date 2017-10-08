package com.waxysoft;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Pablo on 20/03/2017.
 */
public class GamePanel extends JLayeredPane {
    private GameSystem gameSystem;
    private RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

    public GamePanel(GameSystem gameSystem) {
        super();
        this.gameSystem = gameSystem;
        setBackground(Color.black);
        setOpaque(true);
        setSize(gameSystem.getTamaño().getX().intValue(), gameSystem.getTamaño().getY().intValue());
    }

    public void paintComponent(Graphics g) {
        //this.gameSystem.setTamaño(new Coordenada(this.getSize().getWidth(), this.getSize().getHeight()));
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHints(rh);
        gameSystem.setGraphics(g2d);
        gameSystem.update();
        super.paintComponent(g);
    }
}
