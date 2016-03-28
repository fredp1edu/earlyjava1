/** CST3606 2/8/2016
 *  This class takes in the data for the ParkedCar, including make, model color, license, number 
 *  when the car started parking and current time, which determines how long the car has been parked.
 *  @author Fred
 */
package parkingmeterlog;

import java.awt.*;
import javax.swing.*;

public class ParkedCar extends JPanel {
    
    private final int INPUT_FIELDS = 7;
    private final JTextField tbxCarInput[] = new JTextField[INPUT_FIELDS];
    private final JLabel lblCarInput[] = new JLabel[INPUT_FIELDS];
    private final String txtCarInput[] = {"License State: ", "#:", "Make:", "Model:", 
        "Color:", "Time Parked  Hours:", "Min:"};
    private JLabel lblTimeDisplay;
    private int timeParked;
    
    public ParkedCar() {
        inputSetup();
        panelSetup();
    }
    private void inputSetup() {
        for (int i = 0; i < lblCarInput.length; i++)
            lblCarInput[i] = new JLabel(txtCarInput[i]);
        tbxCarInput[0] = new JTextField(2);
        for (int i = 1; i < 5; i++) 
            tbxCarInput[i] = new JTextField(8);
        for (int i = 5; i < tbxCarInput.length; i++) {
            tbxCarInput[i] = new JTextField(2);
            tbxCarInput[i].setHorizontalAlignment(JTextField.LEFT);
        }   
    }
    private void panelSetup() {
        JPanel panelCar[] = new JPanel[3];
        for (int i = 0; i < panelCar.length; i++)
            panelCar[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (int i = 0; i < 2; i++) {
            panelCar[0].add(lblCarInput[i]);
            panelCar[0].add(tbxCarInput[i]);
        }
        for (int i = 2; i < 5; i++) {
            panelCar[1].add(lblCarInput[i]);
            panelCar[1].add(tbxCarInput[i]);
        }
        for (int i = 5; i < tbxCarInput.length; i++) {
            panelCar[2].add(lblCarInput[i]);
            panelCar[2].add(tbxCarInput[i]);
        }
        lblTimeDisplay = new JLabel();
        panelCar[2].add(lblTimeDisplay);
        setLayout(new GridLayout(panelCar.length, 1));
        for (JPanel panel : panelCar)
            add(panel);
        setBorder(BorderFactory.createTitledBorder("CAR INFO"));        
    }
    public String checkCarInput() {
        String inputState = tbxCarInput[0].getText();
        boolean isState = false;
        String states[] = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC", "DE", "FL","GA","HI","IA","ID", 
                            "IL","IN","KS","KY","LA","MA","MD","ME","MI","MN","MO","MS","MT","NC",
                            "ND","NE","NH","NJ","NM","NV","NY", "OH","OK","OR","PA","RI","SC",
                            "SD","TN","TX","UT","VA","VT","WA","WI","WV","WY", "US"};  //"US" for diplomat or federal
        for (String state : states) {
            if (inputState.equalsIgnoreCase(state)) {
                isState = true;
            }
        }
        if (!isState) return "state abbrev. (invalid)";
        for (int i = 1; i < tbxCarInput.length; i++) {
            if (tbxCarInput[i].getText().equals("")) 
                return txtCarInput[i];
        }
        try {
            int hours = Integer.parseInt(tbxCarInput[5].getText().trim());
            int min = Integer.parseInt(tbxCarInput[6].getText().trim());
            timeParked = hours * 60 + min;
            if (hours > 0)
                lblTimeDisplay.setText("= " + timeParked + " min");
        }
        catch (Exception ex) {
            return "either hours or minutes";
        }
        return "0";
    }  
    public int getParkedTime() {    
        return timeParked;
    }
    public void resetPanel() {
        for (JTextField tbx : tbxCarInput)
            tbx.setText("");
        lblTimeDisplay.setText("");
        timeParked = 0;
    }
    public static String timeDisplay(int time) {
        int hour = time / 60;
        int minute = (hour > 0) ? time % 60: time;
        String txtHours = (hour > 1) ? "hours" : "hour"; 
        String txtMinutes = (minute > 1) ? "minutes" : "minute"; 
        String txtTime = (hour > 0 || minute > 0) ? String.format("%,d %s, %d %s", hour, txtHours, minute, txtMinutes) : 
                (hour > 0 || minute < 1) ? String.format("%,d %s", hour, txtHours) : String.format("%d %s", minute, txtMinutes);
        return txtTime;
    }
    @Override
    public String toString() {
        return String.format("Car with %s Lic# %s, a %s %s %s\n  parked for %s", tbxCarInput[0].getText().toUpperCase(), 
                tbxCarInput[1].getText(), tbxCarInput[4].getText(), tbxCarInput[2].getText(), 
                tbxCarInput[3].getText(), timeDisplay(timeParked));
    }
}

