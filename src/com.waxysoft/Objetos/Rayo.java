package com.waxysoft.Objetos;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;
import com.waxysoft.MediaPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Pablo on 27/03/2017.
 */
public class Rayo extends Objeto {
    List<Coordenada> puntos;
    private final Integer largo = 30;
    private final Double salto = 5.0;
    private MediaPlayer mediaplayer;

    public Rayo(GameSystem gameSystem) {
        super(gameSystem, new Coordenada(0.0,0.0), new Coordenada(0.0,0.0), Color.magenta);
        this.puntos = new ArrayList<>();
    }

    public void avanzar() {
        if (timer % 100 == 1) mediaplayer = gameSystem.playSound("rayo.wav");
        timer++;
        Nave laNave = gameSystem.getNave();
        Double dir = laNave.angulo;
        Double radio = laNave.diametro /2;
        Coordenada p1;
        Coordenada p2;
        puntos = new ArrayList<>();
        for (Integer i = 0; i<largo; i++) {
            if (i==0) {
                p1 = laNave.coordenadas.copia();
                p1.sumar(new Coordenada(Math.sin(dir)*radio,Math.cos(dir)*radio));
            } else {
                p1 = puntos.get(i-1);
            }
            p2 = p1.copia();
            dir+= ThreadLocalRandom.current().nextDouble(-1.0,1.0)*.25;
            p2.sumar(new Coordenada(Math.sin(dir)*salto,Math.cos(dir)*salto));
            puntos.add(p2);
            Test test = new Test(gameSystem, p2.copia(), 2.0, Color.white);
            Boolean choco = false;
            for (Objeto obj : gameSystem.getObjetos()) {
                if (obj instanceof Asteroide) {
                    Asteroide ast = (Asteroide)obj;
                    if (ast.choco(test)) {
                        choco=true;
                        test.color = obj.color;
                        ast.partir(test);
                    }
                }
                if (obj instanceof Ovni) {
                    Ovni ovni = (Ovni)obj;
                    if (ovni.choco(test)) {
                        ovni.disminuirVida(1.5);
                        choco = true;
                        test.color = ovni.color;
                        test.diametro = 20.0;
                        if (timer%5==1) gameSystem.explosion(test);
                    }
                }
            }
            if (choco) break;
        }
    }

    public void dibujar() {
        Graphics2D g = gameSystem.getGraphics();
        g.setColor(color);
        for (Integer i=0; i<puntos.size()-1; i++) {
            float w = 5.0f - i.floatValue()/puntos.size() * 4.0f;
            g.setStroke(new BasicStroke(w));
            Coordenada p1 = puntos.get(i);
            Coordenada p2 = puntos.get(i+1);
            g.drawLine(p1.getX().intValue(),p1.getY().intValue(),p2.getX().intValue(),p2.getY().intValue());
        }
    }

    public void destruir(){
        super.destruir();
        if (mediaplayer!=null) mediaplayer.stop();
        timer=0;
    }
}
