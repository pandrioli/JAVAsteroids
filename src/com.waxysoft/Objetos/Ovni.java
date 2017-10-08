package com.waxysoft.Objetos;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Pablo on 08/04/2017.
 */
public class Ovni extends Objeto {
    private Double velocidadMaxima;
    private Double distancia;
    private Coordenada vectorDistancia;
    private Integer fireRate;
    private Double velocidadBalas;
    private Double vida;
    public Ovni(GameSystem gameSystem, Coordenada coordenadas, Double diametro,
                Double velocidadMaxima, Integer fireRate, Double velocidadBalas) {
        super(gameSystem, coordenadas, new Coordenada(), Color.gray);
        this.diametro = diametro;
        this.velocidadMaxima = velocidadMaxima;
        this.fireRate = fireRate;
        this.velocidadBalas = velocidadBalas;
        this.vida = diametro;
    }

    public void avanzar() {
        super.avanzar();
        if (timer%50==1) velocidad.sumar(new Coordenada(
                ThreadLocalRandom.current().nextDouble(-velocidadMaxima,velocidadMaxima),
                ThreadLocalRandom.current().nextDouble(-velocidadMaxima,velocidadMaxima)));
        velocidad.limitar(velocidadMaxima);
        Nave laNave = gameSystem.getNave();
        if (laNave==null) return;
        vectorDistancia = coordenadas.copia();
        vectorDistancia.restar(laNave.coordenadas);
        distancia = vectorDistancia.modulo();
        vectorDistancia.mult(-1 / distancia);
        if (timer%fireRate==0 && distancia<velocidadBalas*60) disparar();
    }

    protected void accionAlChocar(Objeto elOtroObjeto) {
        if (elOtroObjeto instanceof Nave) rebotar(elOtroObjeto);
        if (elOtroObjeto instanceof Asteroide) rebotar(elOtroObjeto);
        if (elOtroObjeto instanceof Ovni) rebotar(elOtroObjeto);
    }

    private void disparar() {
        Coordenada origen = vectorDistancia.copia();
        origen.mult(diametro/2+10);
        origen.sumar(coordenadas);
        Coordenada veloBala = vectorDistancia;
        veloBala.mult(velocidadBalas);
        veloBala.sumar(velocidad);
        gameSystem.agregarObjeto(new BalaEnemiga(gameSystem,origen,vectorDistancia));
    }

    public void disminuirVida(Double cantidad) {
        vida-=cantidad;
        if (vida<0) destruir();
    }

    public void destruir() {
        super.destruir();
        gameSystem.explosion(this);
    }

    public void dibujar() {
        super.dibujar();
        Graphics2D g = gameSystem.getGraphics();
        Integer cant = 8;
        Double anguloSeparacion = Math.toRadians(360/cant);
        Double rotacion = timer*.1;
        g.setStroke(new BasicStroke((int)(diametro/50)));
        for (Integer i=0; i<cant; i++) {
            Double x = coordenadas.getX()+diametro/2.7 * Math.cos(rotacion+anguloSeparacion*i);
            Double y = coordenadas.getY()+diametro/2.7 * Math.sin(rotacion+anguloSeparacion*i);
            g.setColor(Color.lightGray);
            circulo(g,x.intValue(),y.intValue(),(int)(diametro/15));
            x = coordenadas.getX()+diametro/2 * Math.cos(rotacion+anguloSeparacion*i+anguloSeparacion/2);
            y = coordenadas.getY()+diametro/2 * Math.sin(rotacion+anguloSeparacion*i+anguloSeparacion/2);
            g.setColor(Color.black);
            g.drawLine(coordenadas.getX().intValue(), coordenadas.getY().intValue(),x.intValue(),y.intValue());
        }
        g.setColor(Color.lightGray);
        circulo(g,coordenadas.getX().intValue(),coordenadas.getY().intValue(),(int)(diametro/1.6));
        g.setColor(Color.cyan);
        circulo(g,coordenadas.getX().intValue(),coordenadas.getY().intValue(),(int)(diametro/2));
        g.setColor(Color.white);
        brillo(g,coordenadas.getX().intValue(),coordenadas.getY().intValue(),(int)(diametro/2.2));
        g.setColor(Color.cyan);
        circulo(g,coordenadas.getX().intValue(),coordenadas.getY().intValue(),(int)(diametro/3.5));
        Double anchoTotal = diametro/3;
        Double anchoVida = anchoTotal*vida/diametro;
        g.setColor(Color.darkGray);
        g.fillRect((int)(coordenadas.getX()-anchoTotal/2),(int)(coordenadas.getY()-diametro/2.3),
                anchoTotal.intValue(),(int)(diametro/10));
        g.setColor(Color.white);
        g.fillRect((int)(coordenadas.getX()-anchoTotal/2),(int)(coordenadas.getY()-diametro/2.3),
                anchoVida.intValue(),(int)(diametro/10));

    }

    private void circulo(Graphics2D g, Integer x, Integer y, Integer diametro) {
        g.fillOval(x-diametro/2, y-diametro/2, diametro, diametro);
    }
    private void brillo(Graphics2D g, Integer x, Integer y, Integer diametro) {
        g.fillArc(x-diametro/2, y-diametro/2, diametro, diametro,90,45);
    }

}
