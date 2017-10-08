package com.waxysoft.Objetos;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by pabloandrioli on 28/03/17.
 */
public class Misil extends Objeto {
    private List<Coordenada> cola = new ArrayList<>();
    private final Integer tiempoDeVida = 140;
    private final Integer tiempoInactivo = 40;
    private Objeto target;

    public Misil(GameSystem gameSystem, Coordenada coordenadas, Coordenada velocidad) {
        super(gameSystem, coordenadas, velocidad, Color.white);
        timer=0;
        diametro=6.0;
        target = null;
    }
    protected void accionAlChocar(Objeto elOtroObjeto){
        if (elOtroObjeto instanceof Nave) rebotar(elOtroObjeto);
        if (elOtroObjeto instanceof Asteroide) {
            ((Asteroide) elOtroObjeto).partir(this);
            destruir();
            if (target!=null) target.targeted=false;
        }
        if (elOtroObjeto instanceof Ovni) {
            ((Ovni) elOtroObjeto).disminuirVida(8.0);
            destruir();
        }
    }

    public void avanzar() {
        super.avanzar();
        if (timer>tiempoInactivo/2) {
            cola.add(coordenadas.copia());
            if (cola.size()>30) cola.remove(0);
        }
        if (timer==tiempoInactivo/2) gameSystem.playSound("misil.wav");
        if (timer<tiempoInactivo) return;
        if (timer>tiempoDeVida) {
            if (target!=null) target.targeted = false;
            destruir();
            return;
        }
        Objeto newTarget = getTargetMasCercano();
        if (newTarget!=target) {
            if(target!=null)target.targeted=false;
            target = newTarget;
        }
        if (target != null) {
            if (target instanceof Asteroide) target.targeted=true;
            Coordenada coord = coordenadas.copia();
            coord.restar(target.coordenadas);
            coord.mult(1/coord.modulo());
            coord.mult(-3.0);
            Coordenada vel = velocidad;
            vel.mult(0.5);
            vel.sumar(coord);
            velocidad=vel.copia();
        }
    }

    private Objeto getTargetMasCercano(){
        Double minimaDistancia = 100000.00;
        Objeto targetCercano = null;
        for (Objeto obj : gameSystem.getObjetos()) {
            if (obj instanceof Asteroide || obj instanceof Ovni) {
                Double distancia = coordenadas.distancia(obj.coordenadas)-diametro/2;
                if (distancia<minimaDistancia && (!obj.targeted || obj==target)) {
                    minimaDistancia = distancia;
                    targetCercano = obj;
                }
            }
        }
        return targetCercano;
    }

    public void destruir() {
        super.destruir();
        diametro = 20.0;
        gameSystem.explosion(this);
    }


    public void dibujar() {
        if (timer>tiempoInactivo/2) {
            Graphics2D g = gameSystem.getGraphics();
            g.setStroke(new BasicStroke(4));
            g.setColor(new Color(255,255,255,150));
            for (Integer i=0; i<cola.size(); i++) {
                if (i>0) {
                    Integer x1 = cola.get(i-1).getX().intValue();
                    Integer y1 = cola.get(i-1).getY().intValue();
                    Integer x2 = cola.get(i).getX().intValue();
                    Integer y2 = cola.get(i).getY().intValue();
                    g.setColor(new Color(255,255,255, (int)(i.doubleValue()/cola.size()*150.0)));
                    g.drawLine(x1,y1,x2,y2);
                }
            }
            dibujarPropulsor(this,-velocidad.angulo()+Math.toRadians(90),8,15,diametro.intValue());
        }
        diametro += Math.sin(timer)*3;
        super.dibujar();
    }

}
