package com.waxysoft;

import com.waxysoft.Objetos.Asteroide;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Pablo on 14/04/2017.
 */
public abstract class Logo {
    private static Integer timer=0;
    public static void dibujar(Graphics2D g, Coordenada tamaño) {
        timer++;
        g.setColor(Color.white);
        Integer size = tamaño.getY().intValue()/6;
        Font font = new Font("Sans Serif", Font.BOLD, size);
        FontMetrics fm = g.getFontMetrics(font);
        String texto = "JA   Asteroids";
        Integer ancho = fm.stringWidth(texto);
        Integer x = tamaño.getX().intValue()/2-ancho/2;
        Integer y = tamaño.getY().intValue()/4+size/8;
        g.setFont(font);
        g.drawString(texto,x ,y);
        font = new Font("Sans Serif", Font.BOLD, size/5);
        fm = g.getFontMetrics(font);
        texto = "Version Alpha 1.0.0 / 2017 - andriGames        ESC: Salir";
        ancho = fm.stringWidth(texto);
        x = tamaño.getX().intValue()/2-ancho/2;
        g.setFont(font);
        g.drawString(texto, x, tamaño.getY().intValue()-50);



        x = tamaño.getX().intValue()/2+(int)(size*1.59);
        y-= (int)(size*.26);

        Random rnd = new Random();
        Double diametro = size.doubleValue()*.58;
        rnd.setSeed(timer/20);
        Polygon figura = new Polygon();
        Integer nroVertices = 50;
        Double a = Math.toRadians(360)/nroVertices;
        for(Integer i=0; i<nroVertices; i++) {
            Double d = diametro*0.5 + (rnd.nextDouble()-0.3) * diametro*.05;
            Integer xd = (int)(Math.cos(a*i)*d);
            Integer yd = (int)(Math.sin(a*i)*d);
            figura.addPoint(xd,yd);
        }
        Polygon lineas = new Polygon();
        nroVertices = 40;
        a = Math.toRadians(360)/nroVertices;
        for(Integer i=0; i<nroVertices; i++) {
            Double d = diametro*0.35 + (rnd.nextDouble()-0.3) * diametro*.05;
            Integer xd = (int)(Math.cos(a*i)*d);
            Integer yd = (int)(Math.sin(a*i)*d);
            lineas.addPoint(xd,yd);
        }

        figura.translate(x, y);
        lineas.translate(x, y);
        g.setColor(Color.orange);
        g.fillPolygon(figura);
        float l=diametro.floatValue()*0.5f;
        float dash[]={l,l*0.8f};
        g.setStroke(new BasicStroke(2.0f,0,0,1,dash,rnd.nextFloat()*1000));
        g.setColor(Color.black);
        g.drawPolygon(lineas);

        x=(int)(tamaño.getX()/2-size*1.65);
        y-=(int)(size*.2);

        Double angulo=Math.sin(timer.doubleValue()*.1)*.1;

        Double d = diametro * 3;
        Double xc = x-(d/2);
        Double yc = y-(d/2);
        Double xc2 = x-(d/10.0)+Math.sin(angulo)*d/8.0;
        Double yc2 = y-(d/10.0)+Math.cos(angulo)*d/8.0;
        g.setStroke(new BasicStroke(4.0f));

        Double xd1 = Math.sin(angulo)*d/2;
        Double yd1 = Math.cos(angulo)*d/2;
        Double xd2 = Math.sin(angulo+Math.toRadians(130))*d/3;
        Double yd2 = Math.cos(angulo+Math.toRadians(130))*d/3;
        Double xd3 = Math.sin(angulo+Math.toRadians(230))*d/3;
        Double yd3 = Math.cos(angulo+Math.toRadians(230))*d/3;

        g.setColor(Color.lightGray);
        g.fillPolygon(  new int[]{x.intValue()+xd1.intValue(), x.intValue()+xd2.intValue(), x.intValue()+xd3.intValue()},
                new int[]{y.intValue()+yd1.intValue(), y.intValue()+yd2.intValue(), y.intValue()+yd3.intValue()},3);

        g.setColor(Color.darkGray);
        g.fillOval(xc2.intValue()+1,yc2.intValue(),d.intValue()/5,d.intValue()/5);


        xd1 = Math.sin(angulo+Math.toRadians(15))*d/2.2;
        yd1 = Math.cos(angulo+Math.toRadians(15))*d/2.2;
        xd2 = Math.sin(angulo+Math.toRadians(165))*d/3;
        yd2 = Math.cos(angulo+Math.toRadians(165))*d/3;

        g.setColor(Color.black);
        g.drawLine(x.intValue()+xd1.intValue(),y.intValue()+yd1.intValue(),
                x.intValue()+xd2.intValue(),y.intValue()+yd2.intValue());

        xd1 = Math.sin(angulo+Math.toRadians(-15))*d/2.2;
        yd1 = Math.cos(angulo+Math.toRadians(-15))*d/2.2;
        xd2 = Math.sin(angulo+Math.toRadians(-165))*d/3;
        yd2 = Math.cos(angulo+Math.toRadians(-165))*d/3;

        g.drawLine(x.intValue()+xd1.intValue(),y.intValue()+yd1.intValue(),
                x.intValue()+xd2.intValue(),y.intValue()+yd2.intValue());

        if (timer % 500 < 400) return;

        a = angulo+Math.toRadians(180);
        ancho = diametro.intValue() /2;
        Integer largo = diametro.intValue();
        Double offset=diametro*.9;
        Double x1 = x+Math.sin(a)*offset;
        Double y1 = y+Math.cos(a)*offset;
        rnd.setSeed(timer);
        for (int i=0; i<largo; i++) {
            a+= (rnd.nextDouble()-.5)*.1;
            Double x2 = x1+Math.sin(a);
            Double y2 = y1+Math.cos(a);
            BasicStroke bs = new BasicStroke(ancho-i*ancho/largo,1,0);
            g.setStroke(bs);
            g.setColor(Color.red);
            g.drawLine(x1.intValue(),y1.intValue(),x2.intValue(),y2.intValue());
            x1 = x2;
            y1 = y2;
        }
        g.setColor(Color.orange);
        a = angulo + Math.toRadians(180);
        x1 = x+Math.sin(a)*offset;
        y1 = y+Math.cos(a)*offset;
        rnd.setSeed(timer);
        for (int i=0; i<largo/2; i++) {
            a+= (rnd.nextDouble()-.5)*.1;
            Double x2 = x1+Math.sin(a);
            Double y2 = y1+Math.cos(a);
            g.setStroke(new BasicStroke(ancho*0.5f-i*ancho/largo*0.5f,1,1));
            g.drawLine(x1.intValue(),y1.intValue(),x2.intValue(),y2.intValue());
            x1 = x2;
            y1 = y2;
        }

    }
}
