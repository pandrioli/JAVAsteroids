package com.waxysoft;

import com.waxysoft.Mensajes.*;
import com.waxysoft.Niveles.*;
import com.waxysoft.Objetos.*;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.prefs.Preferences;

import static com.waxysoft.Main.fullscreen;

/**
 * Created by Pablo on 25/03/2017.
 */
public class GameSystem {
    private final Integer maxParticulas = 250;
    private Nave nave;
    private GameFrame gameFrame;
    private ArrayList<Objeto> objetos;
    private ArrayList<Objeto> objetosAgregar;
    private ArrayList<Objeto> objetosDestruir;
    private ArrayList<Explosion> explosiones;
    private ArrayList<Particula> particulas;
    private ArrayList<Arma> armas;
    private Integer armaSeleccionada;
    private Coordenada tamaño;
    private Input inputTeclas;
    private Graphics2D graphics;
    private Integer timer;
    private Integer contadorParticulas;
    private MediaPlayer musicPlayer;
    private Integer estado;
    private List<Mensaje> mensajes;
    private Mensaje mensajePrincipal;
    private Integer nroNivel;
    private iNivel nivel;
    private List<iNivel> niveles;
    private Estrellas fondoEstrellas;
    private Preferences gameData;
    private Integer nivelAlcanzado;

    public GameSystem() {
        musicPlayer = new MediaPlayer();
        setEstado(0);
        nave = null;
        mensajes = new ArrayList<>();
        inputTeclas = new Input();
        tamaño = new Coordenada(1280.0, 720.0);
        timer = 0;
        contadorParticulas = 0;
        explosiones = new ArrayList<>();
        particulas = new ArrayList<>();
        armaSeleccionada = 0;
        fondoEstrellas = new Estrellas(this);
        gameData = Preferences.userNodeForPackage(this.getClass());
        nroNivel = gameData.getInt("nivelAlcanzado", 0);
        nivelAlcanzado = nroNivel;
        iniciarDisplay();
        cargarNiveles();
    }

    public Nave getNave() {
        return nave;
    }

    public void setNave(Nave nave) {
        this.nave = nave;
    }

    public Integer getNivelAlcanzado() {
        return nivelAlcanzado;
    }

    public void agregarArma(Arma arma) {
        if (armas.contains(arma)) {
            for (Arma arm : armas) {
                if (arm.equals(arma)) {
                    arm.setCarga(arm.getCarga()+arma.getCarga());
                    arm.setCargaTotal(arm.getCarga());
                }
            }
        } else armas.add(arma);
    }

    public Integer getEstado() {
        return estado;
    }

    public Integer getTimer() {
        return timer;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
        musicPlayer.stop();
        if (estado==0) {
            try {Thread.sleep(500);}catch(Exception e){};
            musicPlayer = playSound("musicaMenu.wav",1.0, true);
        }
        if (estado==1) {
            try {Thread.sleep(500);}catch(Exception e){};
            musicPlayer = playSound("musicaJuego.wav",1.0, true);
        }
        if (estado==2) mensajePrincipal = new MsjPerder(this);
    }

    public void setNroNivel(Integer nroNivel) {
        this.nroNivel = nroNivel;
    }

    public void setMensajePrincipal(Mensaje mensajePrincipal) {
        this.mensajePrincipal = mensajePrincipal;
    }

    public void agregarMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
    }
    public void quitarMensaje(Mensaje mensaje) {
        mensajes.remove(mensaje);
    }

    public Integer getNroNivel() {
        return nroNivel;
    }
    public iNivel getNivel() {
        return nivel;
    }

    public void restarParticula() {
        this.contadorParticulas--;
    }

    public Integer getContadorParticulas() {
        return contadorParticulas;
    }

    public Graphics2D getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics2D graphics) {
        this.graphics = graphics;
    }

    public Input getInputTeclas() {
        return inputTeclas;
    }

    public Coordenada getTamaño() {
        return tamaño;
    }

    public void setTamaño(Coordenada tamaño) {
        this.tamaño = tamaño;
    }

    public ArrayList<Objeto> getObjetos() {
        return objetos;
    }

    public ArrayList<Objeto> getObjetosAgregar() {
        return objetosAgregar;
    }

    public ArrayList<Objeto> getObjetosDestruir() {
        return objetosDestruir;
    }

    public void iniciarNivel() {
        contadorParticulas=0;
        objetos = new ArrayList<>();
        objetosDestruir = new ArrayList<>();
        objetosAgregar = new ArrayList<>();
        mensajes = new ArrayList<>();
        armas = new ArrayList<>();
        armas.add(new Arma("Cañón",500));
        armaSeleccionada=0;
        timer = 0;
        nave = new Nave(this, new Coordenada(tamaño.getX()/2, tamaño.getY()/2));
        nave.setArma(armas.get(0));
        objetos.add(nave);
        nivel = niveles.get(nroNivel);
        mensajePrincipal = new MsjNivel(this);
        nivel.cargar(this);
        Asteroide.resetearContadorDestruidos();
        gameFrame.menuPanel.setVisible(false);
        setEstado(1);
    }

    private void acciones() {
        if (nave != null) {
            if (inputTeclas.getTeclaDerecha()) nave.girarDerecha();
            if (inputTeclas.getTeclaIzquierda()) nave.girarIzquierda();
            if (inputTeclas.getTeclaArriba()) nave.acelerar();
            if (!inputTeclas.getTeclaArriba()) nave.dejarDeAcelerar();
            if (inputTeclas.getTeclaAbajo()) nave.frenar();
            if (inputTeclas.getTeclaDisparo()) nave.disparar();
            if (!inputTeclas.getTeclaDisparo()) nave.dejarDeDisparar();
            if (inputTeclas.getTeclaCambiarArma()) {
                nave.dejarDeDisparar();
                armaSeleccionada++;
                if (armaSeleccionada+1>armas.size()) armaSeleccionada = 0;
                nave.setArma(armas.get(armaSeleccionada));
                inputTeclas.setTeclaCambiarArma(false);
            }
        }
        if (inputTeclas.getTeclaSalir()) {
            if (estado==0) System.exit(0);
            setEstado(0);
            gameFrame.mostrarMenu();
            inputTeclas.setTeclaSalir(false);
        }
    }

    public void agregarObjeto(Objeto objeto) {
        objetosAgregar.add(objeto);
    }

    public void refrescar() {
        gameFrame.repaint();
    }

    public void update() {
        timer++;
        fondoEstrellas.update();
        acciones();
        if(estado==0) {
            Logo.dibujar(graphics, tamaño);
            return;
        }
        if (nave!=null && armaSeleccionada>0 && armas.get(armaSeleccionada).getCarga()==0) {
            inputTeclas.setTeclaDisparo(false);
            nave.dejarDeDisparar();
            armaSeleccionada = 0;
            armas.remove(nave.getArma());
            nave.setArma(armas.get(0));
        }
        Boolean quedanCosas = false;
        for (Objeto obj : objetos) {
            if (obj instanceof Asteroide) quedanCosas = true;
            if (obj instanceof Ovni) quedanCosas = true;
            obj.avanzar();
        }
        if (!quedanCosas && estado == 1) {
            setEstado(3);
            if (nroNivel<6) mensajePrincipal = new MsjGanar(this);
            else mensajePrincipal = new MsjFin(this);
            nroNivel++;
            if (nroNivel>nivelAlcanzado) {
                nivelAlcanzado++;
                gameData.putInt("nivelAlcanzado", nivelAlcanzado);
            }
        }
        for (Objeto obj : objetos) {
            obj.detectarChoque();
        }
        for (Objeto obj : objetosDestruir) {
            objetos.remove(obj);
        }
        for (Objeto obj : objetosAgregar) {
            objetos.add(obj);
        }
        for (Objeto obj : objetos) {
            obj.dibujar();
        }
        for (Explosion exp : explosiones) {
            exp.dibujar();
        }

        ArrayList<Explosion> nuevaListaExp = new ArrayList<>();
        for (Explosion exp : explosiones) {
            if (!exp.apagar()) nuevaListaExp.add(exp);
        }
        explosiones = nuevaListaExp;
        nivel.update(this);
        dibujarStats();
        for (Integer i=mensajes.size()-1; i>=0; i--) {
            mensajes.get(i).update();
        }
        if (mensajePrincipal!=null) mensajePrincipal.update();
        objetosAgregar = new ArrayList<>();
        objetosDestruir = new ArrayList<>();
    }

    private void dibujarStats() {
        if (nave==null) return;
        Integer x = tamaño.getX().intValue()-210;
        Integer y = tamaño.getY().intValue()-40;
        mostrarEstado("Escudo", nave.getEscudo(), 100.0, 10, y);
        Arma armaSelec = nave.getArma();
        mostrarEstado(armaSelec.getNombre(),
                armaSelec.getCarga().doubleValue(),armaSelec.getCargaTotal().doubleValue(),
                x, y);
        Integer xx=0;
        y+=25;
        for (Arma arma : armas) {
            if (arma.equals(armaSelec)) graphics.setColor(Color.white);
            else graphics.setColor(new Color(255,255,255,100));
            graphics.fillRect(x+xx, y,35,10);
            xx+=41;
        }
    }

    private void mostrarEstado(String texto, Double valor, Double total, Integer x, Integer y) {
        if (total>0) texto+=" : "+valor.intValue(); else {total=1.0;valor=1.0;}
        Font font = new Font("SansSerif", Font.BOLD, 18);
        graphics.setFont(font);
        graphics.setColor(new Color(0, 0, 0, 200));
        graphics.drawString(texto,x,y-5);
        graphics.setColor(new Color(255, 255, 255, 200));
        graphics.drawString(texto, x+2, y-3);
        Double proporcion = valor/total;
        graphics.setColor(new Color(150, 150, 150, 150));
        graphics.fillRect(x,y,200,20);
        graphics.setColor(new Color(255, 255, 255, 150));
        graphics.fillRect(x,y,(int)(200*proporcion),20);
    }

    public void explosion(Objeto objeto) {
        Double diam;
        if (objeto instanceof Nave) diam=objeto.getDiametro()*3; else diam=objeto.getDiametro();
        Explosion exp = new Explosion(this, objeto.getCoordenadas(), diam);
        explosiones.add(exp);
        Double area = objeto.area();
        Double rc = objeto.getDiametro();
        Double xc = objeto.getCoordenadas().getX();
        Double yc = objeto.getCoordenadas().getY();
        Integer cantidad = (int) Math.sqrt(area);
        if (objeto instanceof Nave) cantidad*=5;
        for (Integer i = 0; i < cantidad; i++) {
            Double a = Math.toRadians(ThreadLocalRandom.current().nextDouble(360));
            Double r = ThreadLocalRandom.current().nextDouble(rc);
            Double x = Math.cos(a);
            Double y = Math.sin(a);
            Coordenada pos = new Coordenada(xc + x * r, yc + y * r);
            Coordenada vel = new Coordenada(x * 10, y * 10);
            vel.mult(diam*.01);
            vel.sumar(objeto.getVelocidad());
            if (contadorParticulas<maxParticulas) {
                contadorParticulas++;
                agregarObjeto(new Particula(this, pos, vel, objeto.getColor()));
            }
        }
        if (objeto instanceof Test) playSound("explosion.wav",0.1);
        else if (objeto instanceof Bomba) playSound("explosionBomba.wav",1.0);
        else playSound("explosion.wav",0.4);
    }

    private void iniciarDisplay() {
        DisplayMode newDisplayMode;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getScreenDevices()[0];
        // create a Frame, select desired DisplayMode from the list of modes
        // returned by gd.getDisplayModes() ...
        newDisplayMode = new DisplayMode(1280,720,16,50);
        gameFrame = new GameFrame(this);
        if (gd.isFullScreenSupported()&&fullscreen) {
            gd.setFullScreenWindow(gameFrame);
        } else {
            // proceed in non-full-screen mode
            gameFrame.setSize(1280,720);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
        }

        if (gd.isDisplayChangeSupported()) {  // Sometime it does return false, however the Display Change is still possible. So, this checking is not a must.
            gd.setFullScreenWindow(gameFrame); // Important!! Call this before setDisplayMode, otherwise you'll got UnsupportedOperationExaption.
            gd.setDisplayMode(newDisplayMode);
        }
    }

    public MediaPlayer playSound(final String url) {
        return playSound(url,1.0,false);
    }
    public MediaPlayer playSound(final String url, Double volumen) {
        return playSound(url,volumen,false);
    }

    public MediaPlayer playSound(final String file, Double volumen, Boolean loop) {
        URL url=getClass().getClassLoader().getResource("sonidos/"+file);
        MediaPlayer mp = new MediaPlayer(url,loop);
        mp.setVolume(volumen.floatValue());
        Thread mpThread = new Thread(mp);
        mpThread.start();
        mpThread.interrupt();
        return mp;
    }

    private void cargarNiveles() {
        niveles = new ArrayList<>();
        niveles.add(new Nivel01());
        niveles.add(new Nivel02());
        niveles.add(new Nivel03());
        niveles.add(new Nivel04());
        niveles.add(new Nivel05());
        niveles.add(new Nivel06());
        niveles.add(new Nivel07());
        niveles.add(new Nivel08());
    }
}
