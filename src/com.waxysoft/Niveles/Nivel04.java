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
public class Nivel04 implements iNivel {
    public void cargar(GameSystem gameSystem) {
        gameSystem.getObjetos().add(new Asteroide(gameSystem,
                new Coordenada(gameSystem.getTamaño().getX()/2,gameSystem.getTamaño().getY()),
                new Coordenada(0.0,0.0),
                200.00, 4, Color.pink));
        gameSystem.getObjetos().add(new PowerUp(gameSystem,
                new Arma("Misiles",30),new Coordenada(gameSystem.getTamaño().getX()/2, 100.0)));
    }

    public void update(GameSystem gameSystem) {
        switch(gameSystem.getTimer()) {
            case 1:
                gameSystem.agregarMensaje(new MsjTutorial(gameSystem, "Los misiles son teledirigidos pero tienen alcance limitado"));
                break;
        }
    }

    public void powerUps(GameSystem gameSystem, Asteroide asteroide) {
        if (asteroide.getContadorDestruidos()%30==0) {
            gameSystem.agregarObjeto(new PowerUp(gameSystem,
                    new Arma("Misiles",30),asteroide.getCoordenadas()));
        }
    }
}
