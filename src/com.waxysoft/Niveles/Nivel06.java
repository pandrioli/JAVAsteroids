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
public class Nivel06 implements iNivel {
    public void cargar(GameSystem gameSystem) {
        gameSystem.getObjetos().add(new Asteroide(gameSystem,1.0,
                200.00, 3, Color.orange));
        gameSystem.getObjetos().add(new Asteroide(gameSystem,1.0,
                150.00, 6, Color.pink));
        gameSystem.agregarArma(new Arma("Misiles",50));
        gameSystem.agregarArma(new Arma("Láser",300));
        gameSystem.agregarArma(new Arma("Rayo",300));
        gameSystem.agregarArma(new Arma("Bombas",10));
    }

    public void update(GameSystem gameSystem) {
        switch(gameSystem.getTimer()) {
            case 1:
                gameSystem.agregarMensaje(new MsjTutorial(gameSystem, "¡A practicar con todas las armas!"));
                break;
            case 500:
                gameSystem.agregarMensaje(new MsjTutorial(gameSystem, "Cada asteroide puede dividirse de diferentes maneras"));
                break;
        }
    }

    public void powerUps(GameSystem gameSystem, Asteroide asteroide) {
    }
}
