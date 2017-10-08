package com.waxysoft;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Pablo on 14/04/2017.
 */
public class LevelButton extends JButton {

    public LevelButton(Integer nivel, Integer nivelAlcanzado) {
        super(nivel.toString());
        setOpaque(false);
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setFocusPainted(false);
        setFocusable(false);
        setContentAreaFilled(false);
        if (nivel>nivelAlcanzado+1) {
            setBorder(BorderFactory.createLineBorder(Color.darkGray,3));
            setBackground(new Color(0,0,0,0));
            setForeground(Color.darkGray);
            setEnabled(false);
        } else {
            setBorder(BorderFactory.createLineBorder(Color.lightGray,3));
            setBackground(new Color(200,200,200,50));
            setForeground(Color.white);
        }
    }

    public void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(Color.lightGray);
        } else if (getModel().isRollover()) {
            g.setColor(Color.darkGray);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
