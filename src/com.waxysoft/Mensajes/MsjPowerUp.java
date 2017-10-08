package com.waxysoft.Mensajes;

import com.waxysoft.Arma;
import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;
import com.waxysoft.Objetos.PowerUp;

/**
 * Created by Pablo on 06/04/2017.
 */
public class MsjPowerUp extends Mensaje {
    public MsjPowerUp(GameSystem gameSystem, PowerUp powerUp) {
        super(gameSystem);
        posicion = powerUp.getCoordenadas();
        posicion.sumar(new Coordenada(0.0,20.0));
        tamaño = 18;
        duracion = 100;
        Arma arma = powerUp.getArma();
        if (arma!=null) {
            texto = arma.getNombre() + " x " + arma.getCarga();
        } else {
            texto = "Escudo +" + powerUp.getCargaEscudo().intValue();
        }
    }
    public void update(){
        super.update();
        posicion.sumar(new Coordenada(0.0,-1.0));
    }
}
