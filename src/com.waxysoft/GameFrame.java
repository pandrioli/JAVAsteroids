package com.waxysoft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame implements KeyListener {
    private GameSystem gameSystem;
    private JLayeredPane mainPanel;
    public GamePanel gamePanel;
    public MenuPanel menuPanel;
    public GameFrame(GameSystem gameSystem) {
        super("Asteroids");
        this.gameSystem = gameSystem;
        setLayout(null);
        mainPanel = new JLayeredPane();
        menuPanel = new MenuPanel(gameSystem);
        gamePanel = new GamePanel(gameSystem);
        //setSize(gameSystem.getTamaño().getX().intValue(),gameSystem.getTamaño().getY().intValue());
        //setResizable(false);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainPanel.setSize(gameSystem.getTamaño().getX().intValue(), gameSystem.getTamaño().getY().intValue());
        mainPanel.add(menuPanel);
        mainPanel.add(gamePanel);
        getContentPane().add(mainPanel);
        pack();
        setVisible(true);
        addKeyListener(this);
        setFocusTraversalKeysEnabled(false);
    }

    public void mostrarMenu() {
        mainPanel.remove(menuPanel);
        menuPanel = new MenuPanel(gameSystem);
        mainPanel.add(menuPanel);
        mainPanel.moveToFront(menuPanel);
    }

    private void setTeclas(Integer tecla, Boolean seteo) {
        switch (tecla) {
            case 37:
                gameSystem.getInputTeclas().setTeclaIzquierda(seteo);
                break;
            case 39:
                gameSystem.getInputTeclas().setTeclaDerecha(seteo);
                break;
            case 38:
                gameSystem.getInputTeclas().setTeclaArriba(seteo);
                break;
            case 40:
                gameSystem.getInputTeclas().setTeclaAbajo(seteo);
                break;
            case 32:
                gameSystem.getInputTeclas().setTeclaDisparo(seteo);
                break;
            case 27:
                gameSystem.getInputTeclas().setTeclaSalir(seteo);
                break;
            case 9:
                gameSystem.getInputTeclas().setTeclaCambiarArma(seteo);
                break;
            case 16:
                gameSystem.getInputTeclas().setTeclaApuntar(seteo);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        setTeclas(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        setTeclas(e.getKeyCode(), false);
    }
}