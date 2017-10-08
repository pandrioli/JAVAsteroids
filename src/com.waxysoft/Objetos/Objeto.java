package com.waxysoft.Objetos;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Pablo on 19/03/2017.
 */
public abstract class Objeto {
    protected GameSystem gameSystem;
    protected Coordenada coordenadas;
    protected Coordenada velocidad;
    protected Double diametro;
    protected Color color;
    protected Integer timer;
    protected Boolean targeted;


    public Objeto(GameSystem gameSystem,Coordenada coordenadas, Coordenada velocidad, Color color) {
        this.gameSystem = gameSystem;
        this.coordenadas = coordenadas;
        this.velocidad = velocidad;
        this.diametro = 0.0;
        this.color = color;
        this.timer = 0;
        this.targeted = false;
    }

    public Coordenada getCoordenadas() {
        return coordenadas;
    }

    public Coordenada getVelocidad() {
        return velocidad;
    }

    public Double getDiametro() {
        return diametro;
    }

    public Color getColor() {
        return color;
    }

    public Double area() {return Math.pow(diametro/2,2.0)*3.1416;}
    public void destruir() {
        gameSystem.getObjetosDestruir().add(this);
    }
    public void avanzar(){
        timer++;
        coordenadas.sumar(velocidad);
    }
    public void dibujar(){
        Graphics2D g = gameSystem.getGraphics();
        Double x = coordenadas.getX()-(diametro/2);
        Double y = coordenadas.getY()-(diametro/2);
        g.setColor(color);
        g.fillOval(x.intValue(),y.intValue(),diametro.intValue(),diametro.intValue());
    }

    public void detectarChoque() {
        ArrayList<Objeto> objetos = gameSystem.getObjetos();
        if (coordenadas.getX()<diametro/2) {
            coordenadas.setX(diametro/2);
            velocidad.setX(-velocidad.getX());
        }
        if (coordenadas.getY()<diametro/2) {
            coordenadas.setY(diametro/2);
            velocidad.setY(-velocidad.getY());
        }
        if (coordenadas.getX()>gameSystem.getTamaño().getX()-diametro/2) {
            coordenadas.setX(gameSystem.getTamaño().getX()-diametro/2);
            velocidad.setX(-velocidad.getX());
        }
        if (coordenadas.getY()>gameSystem.getTamaño().getY()-diametro/2) {
            coordenadas.setY(gameSystem.getTamaño().getY()-diametro/2);
            velocidad.setY(-velocidad.getY());
        }
        for (Objeto obj : objetos) {
                if (obj != this && !(obj instanceof Particula)) {
                    if (choco(obj)) {
                        this.accionAlChocar(obj);
                    }
                }
            }
    }

    protected Boolean choco(Objeto elOtroObjeto) {
        Double distancia = coordenadas.distancia(elOtroObjeto.coordenadas);
        return distancia < diametro * .5 + elOtroObjeto.diametro * .5;
    }

    protected void accionAlChocar(Objeto elOtroObjeto) {
    }

    protected void rebotar(Objeto elOtroObjeto) {
        Coordenada velocidad2 = elOtroObjeto.velocidad;
        Coordenada coordenadas2 = elOtroObjeto.coordenadas;
        Double x1 = coordenadas.getX();
        Double y1 = coordenadas.getY();
        Double x2 = coordenadas2.getX();
        Double y2 = coordenadas2.getY();
        Double vx1 = velocidad.getX();
        Double vy1 = velocidad.getY();
        Double vx2 = velocidad2.getX();
        Double vy2 = velocidad2.getY();
        Double mass1 = .01*area();
        Double mass2 = .01*elOtroObjeto.area();
        if (this instanceof Particula) mass1 = 0.001;
        if (elOtroObjeto instanceof Particula) mass2 = 0.001;

        Double collisionision_angle = Math.atan2((y2 - y1), (x2 - x1));
        Double speed1 = Math.sqrt(vx1*vx1+vy1*vy1);
        Double speed2 = Math.sqrt(vx2*vx2+vy2*vy2);

        Double direction_1 = Math.atan2(vy1, vx1);
        Double direction_2 = Math.atan2(vy2, vx2);
        Double new_xspeed_1 = speed1 * Math.cos(direction_1 - collisionision_angle);
        Double new_yspeed_1 = speed1 * Math.sin(direction_1 - collisionision_angle);
        Double new_xspeed_2 = speed2 * Math.cos(direction_2 - collisionision_angle);
        Double new_yspeed_2 = speed2 * Math.sin(direction_2 - collisionision_angle);

        Double final_xspeed_1 = ((mass1 - mass2) * new_xspeed_1 + (mass2 + mass2) * new_xspeed_2) / (mass1 + mass2);
        Double final_xspeed_2 = ((mass1 + mass1) * new_xspeed_1 + (mass2 - mass1) * new_xspeed_2) / (mass1 + mass2);
        Double final_yspeed_1 = new_yspeed_1;
        Double final_yspeed_2 = new_yspeed_2;

        Double cosAngle = Math.cos(collisionision_angle);
        Double sinAngle = Math.sin(collisionision_angle);

        velocidad.setX(cosAngle * final_xspeed_1 - sinAngle * final_yspeed_1);
        velocidad.setY(sinAngle * final_xspeed_1 + cosAngle * final_yspeed_1);
        Coordenada newVelocidad = new Coordenada(
                cosAngle * final_xspeed_2 - sinAngle * final_yspeed_2,
                sinAngle * final_xspeed_2 + cosAngle * final_yspeed_2);
        elOtroObjeto.velocidad = newVelocidad;

        // get the mtd
        Double posDiffx = x1 - x2;
        Double posDiffy = y1 - y2;
        Double d = Math.sqrt(posDiffx*posDiffx+posDiffy*posDiffy);
        Double b1r=diametro/2;
        Double b2r=elOtroObjeto.diametro/2;

        // minimum translation distance to push balls apart after intersecting
        Double mtdx = posDiffx * (((b1r + b2r+1) - d) / d);
        Double mtdy = posDiffy * (((b1r + b2r+1) - d) / d);

        // resolve intersection —
        // computing inverse mass quantities
        Double im1 = 1 / mass1;
        Double im2 = 1 / mass2;

        // push-pull them apart based off their mass
        x1 = x1 + mtdx * (im1 / (im1 + im2));
        y1 = y1 + mtdy * (im1 / (im1 + im2));
        x2 = x2 - mtdx * (im2 / (im1 + im2));
        y2 = y2 - mtdy * (im2 / (im1 + im2));
        coordenadas = new Coordenada(x1,y1);
        elOtroObjeto.coordenadas = new Coordenada(x2,y2);
    }
    protected void dibujarPropulsor(Objeto objeto, Double angulo, Integer ancho, Integer largo, Integer offset) {
        Graphics2D g = gameSystem.getGraphics();
        Double x = objeto.coordenadas.getX();
        Double y = objeto.coordenadas.getY();
        Double a = angulo + Math.toRadians(180);
        Double x1 = x+Math.sin(a)*offset;
        Double y1 = y+Math.cos(a)*offset;
        for (int i=0; i<largo; i++) {
            a+= ThreadLocalRandom.current().nextDouble(-1,1)*.1;
            Double x2 = x1+Math.sin(a);
            Double y2 = y1+Math.cos(a);
            g.setStroke(new BasicStroke(ancho-i*ancho/largo));
            g.setColor(Color.red);
            g.drawLine(x1.intValue(),y1.intValue(),x2.intValue(),y2.intValue());
            x1 = x2;
            y1 = y2;
        }
        g.setColor(Color.orange);
        a = angulo + Math.toRadians(180);
        x1 = x+Math.sin(a)*offset;
        y1 = y+Math.cos(a)*offset;
        for (int i=0; i<largo/2; i++) {
            a+= ThreadLocalRandom.current().nextDouble(-1,1)*.1;
            Double x2 = x1+Math.sin(a);
            Double y2 = y1+Math.cos(a);
            g.setStroke(new BasicStroke(ancho*0.5f-i*ancho/largo*0.5f));
            g.drawLine(x1.intValue(),y1.intValue(),x2.intValue(),y2.intValue());
            x1 = x2;
            y1 = y2;
        }

    }
}
