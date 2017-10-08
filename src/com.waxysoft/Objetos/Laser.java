package com.waxysoft.Objetos;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;
import com.waxysoft.MediaPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dh3 on 03/04/17.
 */
public class Laser extends Objeto {
    List<Coordenada> puntos;
    private final Integer largo=150;
    private final Double salto=5.0;
    private MediaPlayer mediaplayer;

    public Laser(GameSystem gameSystem) {
        super(gameSystem, new Coordenada(), new Coordenada(), Color.red);
        this.puntos = new ArrayList<>();
    }
    public void avanzar() {
        if (timer % 200 == 1) {
            if (mediaplayer!=null) mediaplayer.stop();
            mediaplayer = gameSystem.playSound("laser.wav",0.5);
        }
        timer++;
        Nave laNave = gameSystem.getNave();
        Double dir = laNave.angulo;
        Double radio = laNave.diametro /2;
        Coordenada p1;
        Coordenada p2;
        puntos = new ArrayList<>();
        for (Integer i = 0; i<largo; i++) {
            Boolean choco=false;
            if (i==0) {
                p1 = laNave.coordenadas.copia();
                p1.sumar(new Coordenada(Math.sin(dir)*radio,Math.cos(dir)*radio));
            } else {
                p1 = puntos.get(i-1);
            }
            if (p1.getY()<salto) dir=Math.toRadians(180)-dir;
            if (p1.getX()<salto) dir=Math.toRadians(0)-dir;
            if (p1.getY()>gameSystem.getTamaño().getY()-salto) dir=Math.toRadians(180)-dir;
            if (p1.getX()>gameSystem.getTamaño().getX()-salto) dir=Math.toRadians(0)-dir;
            p2 = p1.copia();
            p2.sumar(new Coordenada(Math.sin(dir)*salto,Math.cos(dir)*salto));
            puntos.add(p2);
            Test test = new Test(gameSystem, p2.copia(), 3.0, Color.white);
            for (Objeto obj : gameSystem.getObjetos()) {
                if (obj instanceof Asteroide) {
                    Asteroide ast = (Asteroide)obj;
                    if (ast.choco(test)) {
                        ast.disminuirVida();
                        test.color = ast.color;
                        test.diametro = 20.0;
                        if (timer%5==0) gameSystem.explosion(test);
                        choco=true;
                        break;
                    }
                }
                if (obj instanceof Ovni) {
                    Ovni ovni = (Ovni)obj;
                    if (ovni.choco(test)) {
                        ovni.disminuirVida(0.7);
                        test.color = ovni.color;
                        test.diametro = 20.0;
                        if (timer%5==0) gameSystem.explosion(test);
                        choco=true;
                        break;
                    }
                }
            }
            if (choco || gameSystem.getNave().choco(test)) break;
        }
    }
    public void dibujar() {
        Graphics2D g = gameSystem.getGraphics();
        g.setColor(color);
        g.setStroke(new BasicStroke(3));
        for (Integer i=0; i<puntos.size()-1; i++) {
            Float a = 255.0f - (float)Math.pow(i.floatValue()/largo, 4.0) * 255.0f;
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), a.intValue()));
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
