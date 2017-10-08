package com.waxysoft;

import com.waxysoft.GameSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JLayeredPane {
    private GameSystem gameSystem;
    private RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

    public MenuPanel(GameSystem gameSystem) {
        super();
        this.gameSystem = gameSystem;
        setBackground(new Color(0,0,0,0));
        Integer width = (int)(gameSystem.getTamaño().getX()*0.7);
        Integer height = (int)(gameSystem.getTamaño().getY()*0.4);
        Integer x = (int)(gameSystem.getTamaño().getX()/2-width/2);
        Integer y = (int)(gameSystem.getTamaño().getY()/2-height/4);
        setBounds(x,y,width,height);
        GridLayout grid = new GridLayout(3,10);
        grid.setHgap(5);
        grid.setVgap(5);
        setLayout(grid);
        generarBotones();
        setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void generarBotones() {
        Integer nivel = 0;
        for (Integer i=0; i<30; i++) {
                nivel++;
                JButton botonNivel = new LevelButton(nivel, gameSystem.getNivelAlcanzado());
                botonNivel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton boton = (JButton)e.getSource();
                        Integer nivel=Integer.parseInt(boton.getText())-1;
                        gameSystem.setNroNivel(nivel);
                        gameSystem.iniciarNivel();
                    }
                });
                add(botonNivel);
        }
    }
}
