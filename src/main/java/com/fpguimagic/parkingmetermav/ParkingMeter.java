/** CST3606 HW1 #6 2/10/2016
 *  This class sets up the ParkingMeter. First idea was for Agent to input meter ID num and it would 
 *  access the array that would automatically give up the data for that meter (location, region, etc.);
 *  But the combo Box list for all the ids would be too long, so for this project I broke it into two components,
 *  which is cool because I want to create a combobox that populates its parameters based on the results of another combobox
 *  I read this is done with array adapters, but i'm going to try it this way first.
 *  Input of the dollar amount determines amount of time allotted based on what region the meter is located (3 regions), if 
 *  the input is invalid, it will return negative values which tells the ParkingMeterLog to issue error messages.
 *  @author Frederick Pierce
 */
package com.fpguimagic.parkingmetermav;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ParkingMeter extends JPanel {
    
    public static final String DEF_METER_TXT  = "(Select)";
    private final String meterGrid[][] = { {DEF_METER_TXT, ""},
                                           {"Lower Manh", "LM-12783", "LM-23484", "LM-33984", "LM-55184"},
                                           {"Upper Manh", "UM-12783", "UM-23484", "UM-33984", "UM-55184"}, 
                                           {"The Boros", "BX-12783", "BK-23484", "QU-33984", "SI-55184"} }; 
    private final double rate[] = {0.0, 1.0, 0.50, 0.25};    //rate per 15 min based on region, 1st index a placeholder
    private final String listRegion[] = new String[meterGrid.length];    
    private final int BASE_MIN = 15;                    // min 15 min slots
    private final int MAX_TIME = 180;                   // 3 hour maximum time
    public static final double MAX_AMOUNT = 4.0;        // meters accept no more than this amount
    private final String DEF_TIME_TXT = "(Time Allotted)";
    private JComboBox comboRegion, comboMeter;
    private final JTextField tbxInputMeter;
    private final JLabel lblTimeAllotted;
    private String txtMeter, txtRegion;
    private int regionNum, regionNumOld, timeAllotted;
    
    public ParkingMeter() {
        regionNumOld = 0;
        comboBoxSetup();
        tbxInputMeter = new JTextField(7);
        tbxInputMeter.setHorizontalAlignment(JTextField.RIGHT);
        tbxInputMeter.setToolTipText("Enter $ amount entered in meter");
        lblTimeAllotted = new JLabel(DEF_TIME_TXT);
        panelSetup();
    }
    private void comboBoxSetup() {
        for (int i = 0; i < meterGrid.length; i++)
            listRegion[i] = meterGrid[i][0];
        comboRegion = new JComboBox(listRegion);
        ComboBoxListener cboxListener = new ComboBoxListener();
        comboRegion.addItemListener(cboxListener);
        String listMeter[] = {DEF_METER_TXT, "", ""};
        comboMeter = new JComboBox(listMeter);
        comboMeter.setEnabled(false);
        setText();
    }
    public void setText() {
        txtRegion = (comboRegion.getSelectedItem().toString());
        txtMeter = (comboMeter.getSelectedItem().toString());
    }
    private void panelSetup() {
        JPanel panelCombo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCombo.add(comboRegion);
        panelCombo.add(comboMeter);
        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInput.add(new JLabel("Amt Tnd $"));
        panelInput.add(tbxInputMeter);
        JPanel panelTime = new JPanel(new FlowLayout());
        panelTime.add(lblTimeAllotted, BorderLayout.SOUTH);
        setLayout(new BorderLayout());
        add(panelCombo, BorderLayout.WEST);
        add(panelInput, BorderLayout.EAST);
        add(panelTime, BorderLayout.SOUTH);
        setBorder(BorderFactory.createTitledBorder("PARKING METER INFO"));
    }
    private class ComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            regionNum = comboRegion.getSelectedIndex();
            if (regionNum == regionNumOld)           //if the user chooses the same as the last choice, no need for 
                return;                             // combo box to be repopulated
            if (regionNum == 0) {
                comboMeter.setSelectedIndex(0);
                comboMeter.setEnabled(false);
            }
            else {
                comboMeter.removeAllItems();
                comboMeter.addItem(makeObj(meterGrid[0][0]));               //netbeans says boxes shouldn't be populated with 
                for (int i = 1; i < meterGrid[regionNum].length; i++) {     // String copies but objects instead
                    comboMeter.addItem(makeObj(meterGrid[regionNum][i]));   
                }                                           
                comboMeter.setEnabled(true);
                regionNumOld = regionNum;
            }
            setText();
        }
        private Object makeObj(final String item)  {   // I wonder if this is essentially what an arrayadadpter does
            return new Object() { 
                @Override
                public String toString() { 
                    return item; 
                }
            };
        } 
    }
    private int calcTime(int reg, double amount) {
        amount = (amount < rate[reg]) ? 0 : amount;      //any amount submitted less than the base rate results in 0 minutes
        double minutes = amount / rate[reg];               
        int time = (int)(minutes * BASE_MIN);
        return (time > MAX_TIME) ? MAX_TIME : time;
    }
    public int getAllottedTime() {
        try {
            double inputAmount = Double.parseDouble(tbxInputMeter.getText());
            if (inputAmount > MAX_AMOUNT) return timeAllotted = -1;
            if (inputAmount < 0) return timeAllotted = -2;
            setText();
            timeAllotted = calcTime(regionNum, inputAmount);
            lblTimeAllotted.setText(String.format("Time Allotted:  %d min", timeAllotted));
        }
        catch (Exception ex) {
            timeAllotted = -999;
        }
        return timeAllotted;
    }
    public String getMeterRegion() {
        setText();
        return txtRegion;
    }
    public String getMeterId() {
        setText();
        return txtMeter;
    }
    public void resetPanel() {
        comboRegion.setSelectedIndex(0);
        comboMeter.setEnabled(false);
        tbxInputMeter.setText("");
        lblTimeAllotted.setText(DEF_TIME_TXT);
        regionNumOld = 0;
    }
    @Override
    public String toString() {
        setText();
        return String.format("Parked at Meter #%s - %s. time limit: %s", 
                txtMeter, txtRegion, ParkedCar.timeDisplay(timeAllotted)) ;
    }
}
