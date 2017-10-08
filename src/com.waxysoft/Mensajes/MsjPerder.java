package com.waxysoft.Mensajes;

import com.waxysoft.GameSystem;

/**
 * Created by Pablo on 03/04/2017.
 */
public class MsjPerder extends Mensaje {
    public MsjPerder(GameSystem gameSystem) {
        super(gameSystem);
        texto="¡Fallaste!";
    }
    protected void finalizar() {
        super.finalizar();
        gameSystem.iniciarNivel();
    }
}
