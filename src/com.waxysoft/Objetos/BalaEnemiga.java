package com.waxysoft.Objetos;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;

import java.awt.*;

/**
 * Created by Pablo on 08/04/2017.
 */
public class BalaEnemiga extends Bala {
    public BalaEnemiga(GameSystem gameSystem, Coordenada coordenadas, Coordenada velocidad) {
        super(gameSystem, coordenadas, velocidad);
        color = Color.yellow;
    }
    protected void accionAlChocar(Objeto elOtroObjeto){
        if (elOtroObjeto instanceof Asteroide) {
            ((Asteroide) elOtroObjeto).partir(this);
            destruir();
        }
        if (elOtroObjeto instanceof Ovni) rebotar(elOtroObjeto);
    }
}
