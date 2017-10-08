package com.waxysoft.Objetos;

import com.waxysoft.Arma;
import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Pablo on 30/03/2017.
 */
public class PowerUp extends Objeto {
    protected String letra;
    protected Arma arma;
    protected Double cargaEscudo;
    public PowerUp(GameSystem gameSystem, Arma arma, Coordenada coord) {
        super(gameSystem, new Coordenada(), new Coordenada(), Color.white);
        setearCoordenadas(coord);
        this.diametro = 20.0;
        this.arma = arma;
        switch (arma.getNombre()) {
            case "Misiles":
                letra = "M";
                color = Color.cyan;
                break;
            case "Bombas":
                letra = "B";
                color = Color.pink;
                break;
            case "Rayo":
                letra = "R";
                color = Color.magenta;
                break;
            case "Láser":
                letra = "L";
                color = Color.red;
                break;
        }
    }
    public PowerUp(GameSystem gameSystem, Integer cargaEscudo, Coordenada coord) {
        super(gameSystem, new Coordenada(), new Coordenada(), Color.white);
        setearCoordenadas(coord);
        this.diametro = 20.0;
        this.cargaEscudo = cargaEscudo.doubleValue();
        letra = "E";
        color = Color.green;
    }

        private void setearCoordenadas(Coordenada coord) {
        if (coord==null) {
            Coordenada tam = gameSystem.getTamaño();
            Coordenada coordenadasRandom = new Coordenada();
            Double xr = ThreadLocalRandom.current().nextDouble(100, tam.getX() / 2);
            Double yr = ThreadLocalRandom.current().nextDouble(tam.getY());
            if (ThreadLocalRandom.current().nextBoolean()) xr = tam.getX() / 2 + xr;
            else xr = tam.getX() / 2 - xr;
            coordenadasRandom.setX(xr);
            coordenadasRandom.setY(yr);
            this.coordenadas = coordenadasRandom.copia();
        } else coordenadas=coord;
    }

    public Arma getArma() {
        return arma;
    }

    public Double getCargaEscudo() {
        return cargaEscudo;
    }

    public void dibujar() {
        super.dibujar();
        Graphics2D g = gameSystem.getGraphics();
        g.setColor(Color.black);
        g.setFont(new Font("MonoSpaced", Font.BOLD, 18));
        Double x = coordenadas.getX()-5;
        Double y = coordenadas.getY()+6;
        g.drawString(letra,x.intValue(),y.intValue());
    }
    protected void accionAlChocar(Objeto elOtroObjeto) {
        if (!(elOtroObjeto instanceof Nave)) rebotar(elOtroObjeto);
    }
    public void avanzar() {
        super.avanzar();
        velocidad.mult(0.99);
    }
}
