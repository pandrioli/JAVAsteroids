package com.waxysoft.Objetos;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;

import java.awt.*;

/**
 * Created by Pablo on 19/03/2017.
 */
public class Bala extends Objeto {
    public Bala(GameSystem gameSystem, Coordenada coordenadas, Coordenada velocidad) {
        super(gameSystem, coordenadas, velocidad, Color.white);
        diametro = 8.0;
    }
    protected void accionAlChocar(Objeto elOtroObjeto){
        if (elOtroObjeto instanceof Nave) rebotar(elOtroObjeto);
        if (elOtroObjeto instanceof Asteroide) {
            ((Asteroide) elOtroObjeto).partir(this);
            destruir();
        }
        if (elOtroObjeto instanceof Ovni) {
            ((Ovni)elOtroObjeto).disminuirVida(3.0);
            destruir();
            diametro = 20.0;
            gameSystem.explosion(this);
        }
    }
    public void dibujar(){
        Graphics2D g = gameSystem.getGraphics();
        Double x = coordenadas.getX()-(diametro/2);
        Double y = coordenadas.getY()-(diametro/2);
        g.setColor(color);
        g.fillOval(x.intValue(),y.intValue(),diametro.intValue(),diametro.intValue());
    }
    public void avanzar() {
        super.avanzar();
        diametro *= 0.985;
        //velocidad.mult(1.01);
        if (diametro<3.5) destruir();
    }


}
