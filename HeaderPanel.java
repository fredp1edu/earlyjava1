/** CST3606 2/8/2016
 *  So this class is totally not needed for the project but makes for good practice;
 *  practice, practice, code, code, code - this class sets up the header panel with police badge image 
 *  which changes to a "violation" image when there is a violation
 *  @author Fred
 */
package parkingmeterlog;

import java.awt.*;
import java.applet.*;
import java.net.URL;
import javax.swing.*;

public class HeaderPanel extends JPanel {
   
    private final JLabel lblCompName;
    private final Color defColor = new Color(0,0,255);
    private JPanel panelCompName;
    private final ImageIcon iconBadge = createImageIcon("img/badge.png"); 
    private final ImageIcon iconCop = createImageIcon("img/cop.png"); 
    private Image imgBadge, imgCop, imgSelect;
    LogoPanel panelLogo = new LogoPanel();
    private AudioClip whistle;
    
    public HeaderPanel() {
        audioSetup();
        lblCompName = new JLabel("CITY PARKING ENFORCEMENT");
        lblCompName.setFont(new Font("Arial", Font.BOLD, 16));
        lblCompName.setForeground(defColor);
        imgBadge = iconBadge.getImage();
        imgCop = iconCop.getImage();
        imgSelect = imgBadge;
        panelSetup();
    }
    private void audioSetup() {
         URL audioSrc = getClass().getResource("img/whistle.wav");
         whistle = Applet.newAudioClip(audioSrc);         
    }
    private void panelSetup() {
        panelCompName = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCompName.add(lblCompName);
        setLayout(new FlowLayout());
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
