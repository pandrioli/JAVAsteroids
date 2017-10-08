package com.waxysoft.Niveles;

import com.waxysoft.GameSystem;
import com.waxysoft.Objetos.Asteroide;

/**
 * Created by Pablo on 07/04/2017.
 */
public interface iNivel {
    void cargar(GameSystem gameSystem);
    void update(GameSystem gameSystem);
    void powerUps(GameSystem gameSystem, Asteroide asteroide);
}
