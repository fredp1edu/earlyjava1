/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpguimagic.parkingmetermav;

import java.awt.*;
import java.applet.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class HeaderPanel extends JPanel {
   
    private final JLabel lblCompName;
    private final Color defColor = new Color(0,0,255);
    private JPanel panelCompName;
    private final ImageIcon iconBadge = new ImageIcon("src/main/resources/badge.png"); 
    private final ImageIcon iconCop = new ImageIcon("src/main/resources/cop.png"); 
    private Image imgBadge, imgCop, imgSelect;
    LogoPanel panelLogo = new LogoPanel();
    private AudioClip whistle;
    
    public HeaderPanel() {
        try {
            audioSetup();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        lblCompName = new JLabel("CITY PARKING ENFORCEMENT");
        lblCompName.setFont(new Font("Arial", Font.BOLD, 16));
        lblCompName.setForeground(defColor);
        imgBadge = iconBadge.getImage();
        imgCop = iconCop.getImage();
        imgSelect = imgBadge;
        panelSetup();
    }
    private void audioSetup() throws MalformedURLException {
        try {
            URL audioSrc = this.getClass().getClassLoader().getResource("src/main/resources/whistle.wav");
            whistle = Applet.newAudioClip(audioSrc);
        } catch (Exception ex) {
            
        }             
    } 
    private void panelSetup() {
        panelCompName = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCompName.add(lblCompName);
        this.setLayout(new FlowLayout());
        add(panelCompName);
        add(panelLogo);
    }
    private ImageIcon createImageIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    private class LogoPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(imgSelect, 0, 0, getWidth(), getHeight(), this);
        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        } 
    }
    public void dispViolationImg() {
        imgSelect = imgCop;
        lblCompName.setForeground(Color.red);
        repaint();
        whistle.play();
    }
    public void resetPanel() {
        imgSelect = imgBadge;
        lblCompName.setForeground(defColor);
        repaint();
    } 
}
