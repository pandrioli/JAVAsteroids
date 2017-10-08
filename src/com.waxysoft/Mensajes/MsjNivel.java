package com.waxysoft.Mensajes;

import com.waxysoft.GameSystem;

/**
 * Created by Pablo on 03/04/2017.
 */
public class MsjNivel extends Mensaje {
    public MsjNivel(GameSystem gameSystem) {
        super(gameSystem);
        texto = "Nivel " + (gameSystem.getNroNivel()+1);
    }
}
