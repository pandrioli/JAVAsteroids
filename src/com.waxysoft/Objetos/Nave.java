package com.waxysoft.Objetos;

import com.waxysoft.Arma;
import com.waxysoft.Coordenada;
import com.waxysoft.GameSystem;
import com.waxysoft.Mensajes.Mensaje;
import com.waxysoft.Mensajes.MsjPowerUp;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Pablo on 20/03/2017.
 */
public class Nave extends Objeto {
    private Arma arma;
    private Double escudo;
    private Boolean acelerando;
    private Rayo rayo;
    private Laser laser;
    private Bomba bomba;
    protected Double angulo;
    private Boolean rayoEncendido;
    private Boolean laserEncendido;
    private Integer contadorDisparo;
    private Integer tiempoGolpe;
    public Nave(GameSystem gameSystem, Coordenada coordenadas) {
        super(gameSystem, coordenadas, new Coordenada(0.0,0.0), Color.lightGray);
        this.diametro = 50.0;
        this.angulo = (Double)Math.toRadians(180.0);
        this.escudo = 100.0;
        this.contadorDisparo = 0;
        this.rayo = new Rayo(gameSystem);
        this.laser = new Laser(gameSystem);
        this.rayoEncendido = false;
        this.laserEncendido = false;
        this.acelerando = false;
        this.tiempoGolpe = 0;
   }

    public Double getEscudo() {
        return escudo;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }


    private void disminuirEscudo(Double cantidad) {
        tiempoGolpe = timer;
        escudo -= cantidad;
        if (escudo<0) {
            escudo = 0.0;
            destruir();
        }
    }

    public void destruir() {
        gameSystem.playSound("explosionNave.wav");
        gameSystem.explosion(this);
        gameSystem.setNave(null);
        gameSystem.setEstado(2);
        apagarRayo();
        apagarLaser();
        super.destruir();
    }

    public void accionAlChocar(Objeto elOtroObjeto) {
        rebotar(elOtroObjeto);
        if (elOtroObjeto instanceof PowerUp) {
            PowerUp powerup = (PowerUp) elOtroObjeto;
                Mensaje msj = new MsjPowerUp(gameSystem, powerup);
                gameSystem.agregarMensaje(msj);
                gameSystem.playSound("powerUp.wav");
                if (powerup.arma != null) {
                    gameSystem.agregarArma(powerup.arma);
                } else {
                    escudo = Math.min(100.0, escudo + powerup.cargaEscudo);
                }
                powerup.destruir();
        }
        if (timer<100) return;
        if ((elOtroObjeto instanceof Asteroide || elOtroObjeto instanceof Ovni)) {
            gameSystem.playSound("rebote.wav");
            Coordenada velocidadChoque = velocidad.copia();
            velocidadChoque.restar(elOtroObjeto.velocidad);
            Double fuerzaImpacto = elOtroObjeto.diametro * velocidadChoque.modulo()*.1;
            disminuirEscudo(fuerzaImpacto);
        }
        if (elOtroObjeto instanceof BalaEnemiga) {
            disminuirEscudo(10.0);
            elOtroObjeto.diametro=20.0;
            gameSystem.explosion(elOtroObjeto);
            elOtroObjeto.destruir();
        }
        if (elOtroObjeto instanceof Bomba) {
            if (((Bomba) elOtroObjeto).explotar) destruir();
        }
    }

    public void dibujar() {
        Graphics2D g = gameSystem.getGraphics();
        Double d = diametro;
        Double x = coordenadas.getX();
        Double y = coordenadas.getY();
        Double xc = x-(d/2);
        Double yc = y-(d/2);
        Double xc2 = x-(d/10.0)+Math.sin(angulo)*d/8.0;
        Double yc2 = y-(d/10.0)+Math.cos(angulo)*d/8.0;
        float dash1[]={5.0f,2.5f};
        g.setStroke(new BasicStroke(2.0f,0,0,1,dash1,timer));
        if (timer-tiempoGolpe<20) g.setColor(Color.red);
        else g.setColor(Color.green);
        if (timer<100) g.setColor(Color.magenta);
        g.drawOval(xc.intValue(),yc.intValue(),d.intValue(),d.intValue());
        g.setStroke(new BasicStroke(2.0f));

        Double xd1 = Math.sin(angulo)*d/2;
        Double yd1 = Math.cos(angulo)*d/2;
        Double xd2 = Math.sin(angulo+Math.toRadians(130))*d/3;
        Double yd2 = Math.cos(angulo+Math.toRadians(130))*d/3;
        Double xd3 = Math.sin(angulo+Math.toRadians(230))*d/3;
        Double yd3 = Math.cos(angulo+Math.toRadians(230))*d/3;

        g.setColor(Color.lightGray);
        g.fillPolygon(  new int[]{x.intValue()+xd1.intValue(), x.intValue()+xd2.intValue(), x.intValue()+xd3.intValue()},
                        new int[]{y.intValue()+yd1.intValue(), y.intValue()+yd2.intValue(), y.intValue()+yd3.intValue()},3);

        xd1 = Math.sin(angulo+Math.toRadians(15))*d/2.2;
        yd1 = Math.cos(angulo+Math.toRadians(15))*d/2.2;
        xd2 = Math.sin(angulo+Math.toRadians(165))*d/3;
        yd2 = Math.cos(angulo+Math.toRadians(165))*d/3;

        g.setColor(Color.black);
        g.drawLine(x.intValue()+xd1.intValue(),y.intValue()+yd1.intValue(),
                   x.intValue()+xd2.intValue(),y.intValue()+yd2.intValue());

        xd1 = Math.sin(angulo+Math.toRadians(-15))*d/2.2;
        yd1 = Math.cos(angulo+Math.toRadians(-15))*d/2.2;
        xd2 = Math.sin(angulo+Math.toRadians(-165))*d/3;
        yd2 = Math.cos(angulo+Math.toRadians(-165))*d/3;

        g.drawLine(x.intValue()+xd1.intValue(),y.intValue()+yd1.intValue(),
                   x.intValue()+xd2.intValue(),y.intValue()+yd2.intValue());

        g.setColor(Color.darkGray);
        g.fillOval(xc2.intValue(),yc2.intValue(),d.intValue()/5,d.intValue()/5);

        if (acelerando) {
            dibujarPropulsor(this,angulo,10,18, 15);
        }

    }

    public void girarDerecha() {
        if (gameSystem.getInputTeclas().getTeclaApuntar()) angulo -= .03; else angulo -= .1;
    }

    public void girarIzquierda() {
        if (gameSystem.getInputTeclas().getTeclaApuntar()) angulo += .03; else angulo += .1;
    }

    public void acelerar() {
            Coordenada vector = new Coordenada((Double)Math.sin(angulo), (Double)Math.cos(angulo));
            vector.mult(0.15);
            velocidad.sumar(vector);
            if (velocidad.modulo()>5.0) frenar();
            acelerando = true;
    }
    public void dejarDeAcelerar() {
        acelerando = false;
    }
    public void frenar() {
        Double velocidadLinear = velocidad.modulo();
        Double anguloVector = velocidad.angulo();
        velocidadLinear*=.95;
        velocidad.setCoordPolar(anguloVector, velocidadLinear);
    }

    public void disparar() {
        if (arma.getCarga()==0) return;
        if (!arma.getNombre().equals("Bombas") && bomba!=null) {
            bomba.explotar = true;
            bomba = null;
        }
        if (!arma.getNombre().equals("Rayo")) apagarRayo();
        if (!arma.getNombre().equals("Láser")) apagarLaser();
        switch(arma.getNombre()) {
            case "Cañón":
                lanzarProyectiles();
                break;
            case "Misiles":
                lanzarProyectiles();
                break;
            case "Bombas":
                lanzarProyectiles();
                break;
            case "Rayo":
                if (!rayoEncendido) encenderRayo();
                if (timer%2==0) arma.restarCarga();
                break;
            case "Láser":
                if (!laserEncendido) encenderLaser();
                if (timer%4==0) arma.restarCarga();
                break;
        }
    }

    public void dejarDeDisparar() {
        switch(arma.getNombre()) {
            case "Cañón":
                contadorDisparo = 0;
                break;
            case "Misiles":
                contadorDisparo = 0;
                break;
            case "Bombas":
                contadorDisparo = 0;
                break;
            case "Rayo":
                if (rayoEncendido) apagarRayo();
                break;
            case "Láser":
                if (laserEncendido) apagarLaser();
                break;
        }
    }

    private void encenderRayo() {
        rayoEncendido = true;
        rayo.avanzar();
        gameSystem.agregarObjeto(rayo);
    }

    private void encenderLaser() {
        laserEncendido = true;
        laser.avanzar();
        gameSystem.agregarObjeto(laser);
    }

    private void apagarRayo() {
        rayoEncendido = false;
        rayo.destruir();
    }
    private void apagarLaser() {
        laserEncendido = false;
        laser.destruir();
    }

    private void lanzarProyectiles() {
        if (contadorDisparo>0) {
            contadorDisparo--;
            return;
        }
        gameSystem.playSound("disparo.wav");
        Double a=angulo;
        contadorDisparo = 10;
        if (arma.getNombre().equals("Misiles")) {
            a+=ThreadLocalRandom.current().nextDouble(-1,1)*.3;
        }
        Double x = Math.sin(a);
        Double y = Math.cos(a);
        Coordenada disparoPos = new Coordenada(x,y);
        disparoPos.mult(diametro*.7);
        disparoPos.sumar(coordenadas);
        Coordenada disparoVel = new Coordenada(x,y);
        switch(arma.getNombre()) {
            case "Cañón":
                disparoVel.mult(5.0);
                gameSystem.agregarObjeto(new Bala(gameSystem, disparoPos, disparoVel));
                arma.restarCarga();
                break;
            case "Misiles":
                disparoVel.mult(3.0);
                gameSystem.agregarObjeto(new Misil(gameSystem, disparoPos, disparoVel));
                arma.restarCarga();
                break;
            case "Bombas":
                disparoVel.mult(7.0);
                if (bomba == null) {
                    bomba = new Bomba(gameSystem, disparoPos, disparoVel);
                    gameSystem.agregarObjeto(bomba);
                } else {
                    arma.restarCarga();
                    bomba.explotar = true;
                    bomba = null;
                }
                break;
        }
        disparoVel.sumar(velocidad);
    }
}
