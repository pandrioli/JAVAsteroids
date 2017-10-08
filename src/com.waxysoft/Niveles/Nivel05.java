package com.waxysoft.Niveles;

import com.waxysoft.Arma;
import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;
import com.waxysoft.Mensajes.MsjTutorial;
import com.waxysoft.Objetos.Asteroide;
import com.waxysoft.Objetos.PowerUp;

import java.awt.*;

/**
 * Created by Pablo on 08/04/2017.
 */
public class Nivel05 implements iNivel {
    public void cargar(GameSystem gameSystem) {
        gameSystem.getObjetos().add(new Asteroide(gameSystem,2.0,
                200.00, 3, Color.orange));
        gameSystem.getObjetos().add(new PowerUp(gameSystem,
                new Arma("Rayo",100),new Coordenada(50.0, 50.0)));
    }

    public void update(GameSystem gameSystem) {
        switch(gameSystem.getTimer()) {
            case 1:
                gameSystem.agregarMensaje(new MsjTutorial(gameSystem, "El rayo dura poco y tiene corto alcance pero es muy eficaz"));
                break;
        }
    }

    public void powerUps(GameSystem gameSystem, Asteroide asteroide) {
        if (asteroide.getContadorDestruidos()%30==0) {
            gameSystem.agregarObjeto(new PowerUp(gameSystem,
                    new Arma("Rayo",100),asteroide.getCoordenadas()));
        }
        if (asteroide.getContadorDestruidos()%55==0) {
            gameSystem.agregarObjeto(new PowerUp(gameSystem,
                    50,asteroide.getCoordenadas()));
        }
    }
}
