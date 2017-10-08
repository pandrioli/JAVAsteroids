package com.waxysoft.Objetos;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Pablo on 19/03/2017.
 */
public class Asteroide extends Objeto {
    private static Integer contadorDestruidos=0;
    private Double vida;
    private Polygon figura;
    private Polygon lineas;
    private Integer fragmentos;
//    protected Boolean destruido;
    public Asteroide(GameSystem gameSystem, Coordenada coordenadas, Coordenada velocidad, Double diametro, Integer fragmentos, Color color) {
        super(gameSystem, coordenadas, velocidad, color);
        this.fragmentos = fragmentos;
        this.diametro = diametro;
//        this.destruido = false;
        this.targeted = false;
        generarPoligonos();
        vida=vidaInicial();
    }

    public Asteroide(GameSystem gameSystem, Double velocidadInicial, Double diametro, Integer fragmentos, Color color) {
        super(gameSystem, new Coordenada(), new Coordenada(), color);
        this.coordenadas = new Coordenada(gameSystem);
        Double angulo = ThreadLocalRandom.current().nextDouble(6.28);
        velocidad.setX(Math.cos(angulo) * velocidadInicial);
        velocidad.setY(Math.sin(angulo) * velocidadInicial);
        this.diametro = diametro;
        this.fragmentos = fragmentos;
        this.color = color;
//        this.destruido = false;
        this.targeted = false;
        generarPoligonos();
        vida=vidaInicial();
    }

    private Double vidaInicial() {
        return diametro*.2;
    }

    public void disminuirVida() {
        vida--;
        if (vida<0) partir(this);
    }

    public static void resetearContadorDestruidos() {
        contadorDestruidos=0;
    }

    public Integer getContadorDestruidos() {
        return contadorDestruidos;
    }

    public Color getColor() {
        return color;
    }

    @Override
    protected void accionAlChocar(Objeto elOtroObjeto) {
        if (elOtroObjeto instanceof Asteroide) rebotar(elOtroObjeto);
        if (elOtroObjeto instanceof Nave) rebotar(elOtroObjeto);
    }

    public void avanzar() {
        super.avanzar();
        //velocidad.mult(0.99);
    }

    protected void partir(Objeto bala) {
        Double diamPartes = diametroPartes(diametro, fragmentos);
        if (diamPartes > 20) {
            Coordenada vectorChoque = bala.coordenadas.copia();
            vectorChoque.restar(coordenadas);
            Double angulo = Math.toRadians(360 / fragmentos);
            Double anguloInicial = vectorChoque.angulo() + angulo / 2;
            for (int i = 0; i < fragmentos; i++) {
                Double vx = Math.cos(angulo * i + anguloInicial);
                Double vy = Math.sin(angulo * i + anguloInicial);
                Coordenada coord = new Coordenada(vx * diametro / 2, vy * diametro / 2);
                Coordenada velo = new Coordenada(vx, vy);
                coord.sumar(coordenadas);
                velo.sumar(velocidad);
                gameSystem.agregarObjeto(new Asteroide(gameSystem, coord, velo, diamPartes, fragmentos, color));
            }
        }
        destruir();
        if (!(bala instanceof Bomba)) gameSystem.explosion(this);
    }

    public void destruir() {
        super.destruir();
        contadorDestruidos++;
        gameSystem.getNivel().powerUps(gameSystem,this);
//        destruido = true;
    }

    private Double diametroPartes(Double diametro, Integer fragmentos) {
        Double radio = diametro / 2;
        Double PI = 3.1416;
        Double area = Math.pow(radio, 2) * PI;
        return Math.sqrt(area / PI / fragmentos) * 2.0;
    }

    private void generarPoligonos() {
        Random rnd = new Random();
        rnd.setSeed(this.hashCode());
        figura = new Polygon();
        Integer nroVertices = 50;
        Double a = Math.toRadians(360)/nroVertices;
        for(Integer i=0; i<nroVertices; i++) {
            Double d = diametro*0.5 + (rnd.nextDouble()-0.3) * diametro*.05;
            Integer xd = (int)(Math.cos(a*i)*d);
            Integer yd = (int)(Math.sin(a*i)*d);
            figura.addPoint(xd,yd);
        }
        lineas = new Polygon();
        nroVertices = 40;
        a = Math.toRadians(360)/nroVertices;
        for(Integer i=0; i<nroVertices; i++) {
            Double d = diametro*0.35 + (rnd.nextDouble()-0.3) * diametro*.05;
            Integer xd = (int)(Math.cos(a*i)*d);
            Integer yd = (int)(Math.sin(a*i)*d);
            lineas.addPoint(xd,yd);
        }
    };

    public void dibujar(){
        Graphics2D g = gameSystem.getGraphics();
        figura.translate(coordenadas.getX().intValue(), coordenadas.getY().intValue());
        lineas.translate(coordenadas.getX().intValue(), coordenadas.getY().intValue());
        g.setColor(color);
        g.fillPolygon(figura);
        float l=diametro.floatValue()*0.5f;
        float dash[]={l,l*0.8f};
        Random rnd = new Random();
        rnd.setSeed(hashCode());
        g.setStroke(new BasicStroke(2.0f,0,0,1,dash,rnd.nextFloat()*1000));
        g.setColor(Color.black);
        g.drawPolygon(lineas);
        figura.translate(-coordenadas.getX().intValue(), -coordenadas.getY().intValue());
        lineas.translate(-coordenadas.getX().intValue(), -coordenadas.getY().intValue());
    }
}
