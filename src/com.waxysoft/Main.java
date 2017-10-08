package com.waxysoft;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static Boolean fullscreen = false;
    public static void main(String[] args) {
        // write your code here
 //       System.setProperty("sun.java2d.noddraw", "true");
        if (!fullscreen) System.setProperty("sun.java2d.opengl", "True");
        GameSystem gameSystem = new GameSystem();
        //gameSystem.iniciarNivel();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameSystem.refrescar();
            }
        },0,1000/50);
    }
}
