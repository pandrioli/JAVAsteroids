package com.waxysoft.Niveles;

import com.waxysoft.Arma;
import com.waxysoft.GameSystem;
import com.waxysoft.Mensajes.MsjTutorial;
import com.waxysoft.Objetos.Asteroide;
import com.waxysoft.Objetos.PowerUp;

import java.awt.*;

/**
 * Created by Pablo on 07/04/2017.
 */
public class Nivel01 implements iNivel {
    public void cargar(GameSystem gameSystem) {
        gameSystem.getObjetos().add(new Asteroide(gameSystem, 1.0,
                100.00, 2, Color.orange));
    }

    public void update(GameSystem gameSystem) {
        switch(gameSystem.getTimer()) {
            case 1:
                gameSystem.agregarMensaje(new MsjTutorial(gameSystem, "\u2191 acelerar  \u2193 frenar  \u2190\u2192 girar"));
                break;
            case 400:
                gameSystem.agregarMensaje(new MsjTutorial(gameSystem, "Barra espaciadora: disparar"));
                break;
            case 800:
                gameSystem.agregarMensaje(new MsjTutorial(gameSystem, "Daño = tamaño asteroide x velocidad choque"));
                break;
        }
    }

    public void powerUps(GameSystem gameSystem, Asteroide asteroide) {
    }
}
