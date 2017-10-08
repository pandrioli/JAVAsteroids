package com.waxysoft;

import java.awt.*;
import java.util.Random;

/**
 * Created by Pablo on 13/04/2017.
 */
public class Estrellas {
    private GameSystem gameSystem;
    private Integer timer;
    private Integer rx;

    public Estrellas(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
        timer = 0;
        rx = 0;
    }

    public void update() {
        timer++;
        Graphics2D g=gameSystem.getGraphics();
        g.setColor(Color.lightGray);
        Integer stars = 100;
        Integer xl =gameSystem.getTamaño().getX().intValue();
        Integer xd=timer % xl;
        if (xd==0) rx--;
        Random rnd = new Random(rx);
        for (Integer i=0; i<stars; i++) {
            Integer t = rnd.nextInt(5)+2;
            Integer xx = rnd.nextInt(gameSystem.getTamaño().getX().intValue());
            Integer yy = rnd.nextInt(gameSystem.getTamaño().getY().intValue());
            g.fillOval(xx+xd-xl,yy,t,t);
        }
        rnd = new Random(rx+1);
        for (Integer i=0; i<stars; i++) {
            Integer t = rnd.nextInt(5)+2;
            Integer xx = rnd.nextInt(gameSystem.getTamaño().getX().intValue());
            Integer yy = rnd.nextInt(gameSystem.getTamaño().getY().intValue());
            g.fillOval(xx+xd,yy,t,t);
        }
    }
}
