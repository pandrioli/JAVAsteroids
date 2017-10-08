package com.waxysoft.Mensajes;

import com.waxysoft.GameSystem;

/**
 * Created by Pablo on 13/04/2017.
 */
public class MsjFin extends Mensaje {
    public MsjFin(GameSystem gameSystem) {
        super(gameSystem);
        texto = "Continuará...";
        gameSystem.playSound("bien.wav");
    }
    protected void finalizar() {
        super.finalizar();
        System.exit(0);
    }

}
