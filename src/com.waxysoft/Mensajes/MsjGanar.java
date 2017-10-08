package com.waxysoft.Mensajes;

import com.waxysoft.GameSystem;

/**
 * Created by Pablo on 03/04/2017.
 */
public class MsjGanar extends Mensaje {
    public MsjGanar(GameSystem gameSystem) {
        super(gameSystem);
        texto = "¡Misión cumplida!";
        gameSystem.playSound("bien.wav");
    }
    protected void finalizar() {
        super.finalizar();
        gameSystem.iniciarNivel();
    }
}
