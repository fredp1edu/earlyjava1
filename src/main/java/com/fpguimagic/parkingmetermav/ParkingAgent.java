/** CST3606 HW1 #6 2/10/2016
 *  This class sets up the ParkingAgent Panel. An agent ID Combo Box selects the Agent 
 *  name array.
 *  @author Frederick Pierce
 */
package com.fpguimagic.parkingmetermav;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ParkingAgent extends JPanel {
    
    private final String agentId[] = {"Agent ID", "EX-2389", "EX-4889", "PA-1238", 
                                        "PA-8493", "TR-3487", "TR-8734"};
    private final String agentName[] = {"Select ID", "James Spader", "Cooley McDooley", "Louise Tulleese", 
                                        "Bonquifa Johnson", "Timmy ToeJam", "Little Richard"};
    private int index = 0;
    private final JComboBox comboId;
    private final JLabel lblAgentName;
    ComboBoxListener cBoxListener = new ComboBoxListener();
    
    public ParkingAgent() {
        comboId = new JComboBox(agentId);
        comboId.addItemListener(cBoxListener);
        lblAgentName = new JLabel(agentName[index]);
        panelSetup();
        }
    private void panelSetup() {
        setLayout(new FlowLayout());
        add(new JLabel("ID:"));
        add(comboId);
        add(new JLabel("Officer:"));
        add(lblAgentName);
        setBorder(BorderFactory.createTitledBorder("PARKING AGENT INFO"));
    }
    private class ComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            index = comboId.getSelectedIndex();
            lblAgentName.setText(agentName[index]);
        }
    }
    public int getAgentIndex() {
        return index;
    }
    public String getAgentName() {
        return agentName[index];
    }
    public String getAgentId() {
        return agentId[index];
    }
    public void resetPanel() {
        comboId.setSelectedIndex(0);        
    }
    @Override
    public String toString() {
        return String.format("Agent# %s, Name: %s", getAgentId(), getAgentName());
    }
}
