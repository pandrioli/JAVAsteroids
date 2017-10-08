package com.waxysoft.Objetos;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Pablo on 29/03/2017.
 */
public class Bomba extends Objeto {
    private final Integer duracionExplosion = 10;
    private final Double tamañoExplosion = 250.0;
    protected Boolean explotar;
    private Integer timerBomba;
    public Bomba(GameSystem gameSystem, Coordenada coordenadas, Coordenada velocidad) {
        super(gameSystem, coordenadas, velocidad, Color.gray);
        diametro = 20.0;
        timerBomba = 0;
        explotar = false;
    }

    public Boolean getExplotar() {
        return explotar;
    }

    public void avanzar(){
        if (!explotar) {
            super.avanzar();
        }  else {
            timerBomba++;
            if (timerBomba==1) {
                diametro = tamañoExplosion;
                gameSystem.explosion(this);
            }
            timer++;
            if (timerBomba>duracionExplosion) {
                destruir();
            }
        }
    }
    public void accionAlChocar(Objeto elOtroObjeto) {
        if (explotar) {
            if (elOtroObjeto instanceof Asteroide) ((Asteroide) elOtroObjeto).partir(this);
            if (elOtroObjeto instanceof Ovni) ((Ovni) elOtroObjeto).disminuirVida(5.0);
        } else {
            rebotar(elOtroObjeto);
        }
    }

    public void dibujar() {
        if (!explotar) {
            super.dibujar();
            Graphics2D g = gameSystem.getGraphics();
            g.setColor(Color.darkGray);
            g.setStroke(new BasicStroke(3));
            Double x = coordenadas.getX();
            Double y = coordenadas.getY();
            Double aStep = Math.toRadians(360)/8;
            for (Integer i=0; i<8; i++) {
                Double a = aStep * i - timer*.1;
                Double xd = x+Math.cos(a) * diametro/2;
                Double yd = y+Math.sin(a) * diametro/2;
                g.drawLine(x.intValue(),y.intValue(),xd.intValue(),yd.intValue());
            }
            Double xc = coordenadas.getX()-tamañoExplosion/2;
            Double yc = coordenadas.getY()-tamañoExplosion/2;
            float dash1[]={10f,5f};
            g.setStroke(new BasicStroke(2.0f,0,0,1,dash1,timer*2));
            g.setColor(Color.red);
            g.drawOval(xc.intValue(),yc.intValue(),tamañoExplosion.intValue(),tamañoExplosion.intValue());
        }
    }
}
