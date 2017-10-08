package com.waxysoft.Niveles;

import com.waxysoft.Arma;
import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;
import com.waxysoft.Mensajes.MsjTutorial;
import com.waxysoft.Objetos.Asteroide;
import com.waxysoft.Objetos.Ovni;
import com.waxysoft.Objetos.PowerUp;

import java.awt.*;

/**
 * Created by Pablo on 08/04/2017.
 */
public class Nivel07 implements iNivel {
    public void cargar(GameSystem gameSystem) {
        gameSystem.getObjetos().add(new Asteroide(gameSystem,1.0,
                100.00, 3, Color.orange));
        gameSystem.getObjetos().add(new Ovni(gameSystem,
                new Coordenada(gameSystem),100.0, 2.0, 30, 5.0));
        gameSystem.getObjetos().add(new Ovni(gameSystem,
                new Coordenada(gameSystem),100.0, 2.0, 30, 5.0));
        gameSystem.agregarArma(new Arma("Misiles",50));
        gameSystem.agregarArma(new Arma("Láser",300));
        gameSystem.agregarArma(new Arma("Rayo",300));
        gameSystem.agregarArma(new Arma("Bombas",10));
    }

    public void update(GameSystem gameSystem) {
        switch(gameSystem.getTimer()) {
            case 1:
                gameSystem.agregarMensaje(new MsjTutorial(gameSystem, "Hay una raza extraterrestre conservacionista que defiende a los asteroides"));
                break;
            case 500:
                gameSystem.agregarMensaje(new MsjTutorial(gameSystem, "Destruí asteroides para encontrar armas que te ayuden a eliminarlos"));
                break;
        }
    }

    public void powerUps(GameSystem gameSystem, Asteroide asteroide) {
        if (asteroide.getContadorDestruidos()==5) {
            gameSystem.agregarObjeto(new PowerUp(gameSystem,
                    new Arma("Láser",100),asteroide.getCoordenadas()));
        }
    }
}

