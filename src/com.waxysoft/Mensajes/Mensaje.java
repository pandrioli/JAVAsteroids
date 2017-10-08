package com.waxysoft.Mensajes;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;

import java.awt.*;

/**
 * Created by Pablo on 03/04/2017.
 */
public abstract class Mensaje {
    protected Integer timer;
    protected Integer duracion;
    protected String texto;
    protected Integer tamaño;
    protected Coordenada posicion;
    protected GameSystem gameSystem;
    public Mensaje(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
        timer=0;
        duracion=200;
        texto="Prueba";
        tamaño=50;
        Double x = gameSystem.getTamaño().getX()/2;
        Double y = gameSystem.getTamaño().getY()/3;
        posicion=new Coordenada(x,y);
    }
    public void update() {
        timer++;
        Graphics2D graphics = gameSystem.getGraphics();
        if (timer<duracion) {
            Integer size = (int)(timer.doubleValue()/duracion.doubleValue()*100);
            Font font = new Font("SansSerif", Font.BOLD, tamaño);
            FontMetrics fm = graphics.getFontMetrics(font);
            Integer ancho = fm.stringWidth(texto);
            Integer alto = tamaño/2;
            graphics.setFont(font);
            Double x=posicion.getX()-ancho/2;
            Double y=posicion.getY()-alto/2;
            Integer alpha = Math.min(255,duracion-Math.abs(timer*2-duracion));
            graphics.setColor(new Color(0, 0, 0, alpha));
            graphics.drawString(texto, x.intValue(), y.intValue());
            graphics.setColor(new Color(255, 255, 255, alpha));
            graphics.drawString(texto, x.intValue()+2, y.intValue()+2);
        } else {
            finalizar();
        }
    }
    protected void finalizar() {
        gameSystem.quitarMensaje(this);
    }
}
