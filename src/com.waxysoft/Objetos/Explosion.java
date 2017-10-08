package com.waxysoft.Objetos;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Pablo on 30/03/2017.
 */
public class Explosion {
    private final Integer tiempoExplosion=8;
    private GameSystem gameSystem;
    private Coordenada coordenadas;
    private Double diametro;
    private Integer timer;

    public Explosion(GameSystem gameSystem, Coordenada coordenadas, Double diametro) {
        this.coordenadas = coordenadas;
        this.diametro = diametro;
        this.timer = 0;
        this.gameSystem = gameSystem;
    }

    public Boolean apagar() {
        return timer>tiempoExplosion;
    }

    public Double getDiametro() {
        return diametro;
    }

    public void setDiametro(Double diametro) {
        this.diametro = diametro;
    }

    public Coordenada getCoordenadas() {
        return coordenadas;
    }

    public void dibujar() {
        timer++;
        Graphics2D g = gameSystem.getGraphics();
        Polygon explosion = new Polygon();
        for (Integer i=0; i<30; i++) {
            Double a = Math.toRadians(360)/30*i;
            Double d;
            if (i % 2 == 0) {
                d = diametro *0.6 + ThreadLocalRandom.current().nextDouble(1)*diametro*.2;
            } else {
                d = diametro *0.4 + ThreadLocalRandom.current().nextDouble(1)*diametro*.1;
            }
            Double xx = coordenadas.getX()+Math.cos(a)*d;
            Double yy = coordenadas.getY()+Math.sin(a)*d;
            explosion.addPoint(xx.intValue(),yy.intValue());
        }
        g.setColor(Color.red);
        g.fillPolygon(explosion);
        explosion = new Polygon();
        for (Integer i=0; i<30; i++) {
            Double a = Math.toRadians(360)/30*i;
            Double d;
            if (i % 2 == 0) {
                d = diametro *0.3 + ThreadLocalRandom.current().nextDouble(1)*diametro*.2;
            } else {
                d = diametro *0.2 + ThreadLocalRandom.current().nextDouble(1)*diametro*.1;
            }
            Double xx = coordenadas.getX()+Math.cos(a)*d;
            Double yy = coordenadas.getY()+Math.sin(a)*d;
            explosion.addPoint(xx.intValue(),yy.intValue());
        }
        g.setColor(Color.orange);
        g.fillPolygon(explosion);
    }

}
