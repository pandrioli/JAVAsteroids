package com.waxysoft;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Pablo on 19/03/2017.
 */
public class Coordenada {
    private Double x;
    private Double y;
    public Coordenada() {
        this.x = 0.0;
        this.y = 0.0;
    }
    public Coordenada(Double x, Double y) {
        this.x = x;
        this.y = y;
    }
    public Coordenada(Coordenada coord) {
        this.x = coord.getX();
        this.y = coord.getY();
    }
    public Coordenada(GameSystem gameSystem) {
        Coordenada area = gameSystem.getTamaño();
        this.x = ThreadLocalRandom.current().nextDouble(area.getX());
        this.y = ThreadLocalRandom.current().nextDouble(area.getY());
    }
    public Double getX() {
        return x;
    }
    public Double getY() {
        return y;
    }
    public void setX(Double x) {
        this.x = x;
    }
    public void setY(Double y) {
        this.y = y;
    }

    public void sumar(Coordenada coordenadaASumar) {
        this.x += coordenadaASumar.getX();
        this.y += coordenadaASumar.getY();
    }
    public void restar(Coordenada coordenadaARestar) {
        this.x -= coordenadaARestar.getX();
        this.y -= coordenadaARestar.getY();
    }
    public void mult(Double m) {
        this.x *= m;
        this.y *= m;
    }
    public Double distancia(Coordenada otraCoordenada) {
        Double dx = x - otraCoordenada.getX();
        Double dy = y - otraCoordenada.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Coordenada copia() {
        return new Coordenada(x,y);
    }

    public void setCoordPolar(Double angulo, Double modulo) {
        this.x = Math.cos(angulo) * modulo;
        this.y = Math.sin(angulo) * modulo;
    }

    public Double angulo() {
        return Math.atan2(y,x);
    }
    public Double modulo() { return Math.sqrt(x * x + y * y); }
    public void limitar(Double limite) {
        Double m = Math.min(limite,modulo());
        Double a = angulo();
        x = Math.cos(a) * m;
        y = Math.sin(a) * m;
    }
}
