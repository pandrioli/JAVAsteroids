package com.waxysoft.Objetos;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;

import java.awt.*;

/**
 * Created by Pablo on 26/03/2017.
 */
public class Particula extends Objeto {
    public Particula(GameSystem gameSystem, Coordenada coordenadas, Coordenada velocidad, Color color) {
        super(gameSystem, coordenadas, velocidad, color);
        this.diametro = 5.0;
    }
    public void avanzar() {
        super.avanzar();
        diametro *= 0.98;
        velocidad.mult(0.95);
        if (diametro<2.0) {
            gameSystem.restarParticula();
            destruir();
        }
    }

    protected void accionAlChocar(Objeto elOtroObjeto) {

    }

    public void detectarChoque() {

    }

}
