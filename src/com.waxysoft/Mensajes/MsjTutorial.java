package com.waxysoft.Mensajes;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;

/**
 * Created by Pablo on 07/04/2017.
 */
public class MsjTutorial extends Mensaje {
    public MsjTutorial(GameSystem gameSystem, String texto) {
        super(gameSystem);
        tamaño = 20;
        this.texto = texto;
        Double x = gameSystem.getTamaño().getX()/2;
        Double y = gameSystem.getTamaño().getY()/1.5;
        posicion = new Coordenada(x,y);
        duracion = 400;
    }
}
