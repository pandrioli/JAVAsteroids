package com.waxysoft.Objetos;

import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;

import java.awt.*;

/**
 * Created by Pablo on 27/03/2017.
 */
public class Test extends Objeto {
    public Test(GameSystem gameSystem, Coordenada coordenadas, Double diametro, Color color) {
        super(gameSystem,coordenadas,new Coordenada(0.0,0.0), color);
        this.diametro = diametro;
    }
}
