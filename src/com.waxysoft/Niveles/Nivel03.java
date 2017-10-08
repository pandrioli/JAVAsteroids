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
public class Nivel03 implements iNivel {
    public void cargar(GameSystem gameSystem) {
        gameSystem.getObjetos().add(new Asteroide(gameSystem, 1.0,
                200.00, 4, Color.orange));
        gameSystem.getObjetos().add(new PowerUp(gameSystem,
                new Arma("Bombas",5),new Coordenada(gameSystem.getTamaño().getX()/2, 100.0)));
    }

    public void update(GameSystem gameSystem) {
        switch(gameSystem.getTimer()) {
            case 1:
                gameSystem.agregarMensaje(new MsjTutorial(gameSystem, "Aprendé a usar las bombas... ¡Con cuidado!"));
                break;
        }
    }

    public void powerUps(GameSystem gameSystem, Asteroide asteroide) {
    }
}
