/** CST3606 2/8/2016
 *  This main class incorporates the ParkedCar, ParkingMeter and PoliceOfficer class to set up info, then issues
 *  a ticket from the Parking Ticket class if appropriate
 *  @author Fred
 */
package parkingmeterlog;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParkingMeterLog extends JFrame {
    
    private HeaderPanel panelHeader;
    private ParkedCar car;
    private ParkingMeter meter;
    private ParkingAgent agent;
    private JButton btnExit, btnClear, btnProcess, btnTicket;
    private JLabel lblStatus;
    private JTextField tbxDisplay;
    private int overtime;
    private Color colorWarning;
    
    public ParkingMeterLog() {
        classSetup();
        btnSetup();
        displaySetup();
        panelSetup();
    }
    private void classSetup() {
        panelHeader = new HeaderPanel();
        car = new ParkedCar();
        meter = new ParkingMeter();
        agent = new ParkingAgent();
    }
    private void btnSetup() {
        btnProcess = new JButton("Process");
        ProcessBtnListener processListener = new ProcessBtnListener();
        btnProcess.addActionListener(processListener);
        btnTicket = new JButton("TICKET");
        btnTicket.setEnabled(false);
        TicketBtnListener tkBtnListener = new TicketBtnListener();
        btnTicket.addActionListener(tkBtnListener);
        btnClear = new JButton("Clear");
        btnClear.addActionListener((ActionEvent e) -> {
           resetPanel(); 
        });
        btnExit = new JButton("EXIT");
        btnExit.setForeground(Color.red);
        btnExit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
    }
    private void displaySetup() {
        tbxDisplay = new JTextField(20);
        Color defTbxColor = tbxDisplay.getBackground();
        tbxDisplay.setEditable(false);
        tbxDisplay.setBackground(defTbxColor);
        tbxDisplay.setFont(new Font("Impact", Font.PLAIN, 22));
        tbxDisplay.setHorizontalAlignment(JTextField.CENTER);
        lblStatus = new JLabel();
        lblStatus.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        colorWarning = new Color(250,128,114);
        lblStatus.setForeground(colorWarning);
    }
    private void panelSetup() {
        JPanel panelMeter = new JPanel(new BorderLayout());
        panelMeter.add(meter, BorderLayout.NORTH);
        panelMeter.add(agent, BorderLayout.SOUTH);
        
        JPanel panelTop = new JPanel(new BorderLayout(1, 8));
        panelTop.add(car, BorderLayout.NORTH);
        panelTop.add(panelMeter, BorderLayout.SOUTH);
        
        JPanel panelTopTop = new JPanel(new BorderLayout());
        panelTopTop.add(panelHeader, BorderLayout.NORTH);
        panelTopTop.add(panelTop, BorderLayout.SOUTH);
        
        JPanel panelBtn = new JPanel(new GridLayout(1,4));
        panelBtn.add(btnExit);
        panelBtn.add(btnClear);
        panelBtn.add(btnProcess);
        panelBtn.add(btnTicket);
        
        JPanel panelDisplay = new JPanel(new BorderLayout());
        panelDisplay.add(lblStatus, BorderLayout.NORTH);
        panelDisplay.add(tbxDisplay, BorderLayout.SOUTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout(1, 12));
        mainPanel.add(panelTopTop, BorderLayout.NORTH);
        mainPanel.add(panelDisplay, BorderLayout.CENTER);
        mainPanel.add(panelBtn, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);
    }
    private void clearDisplay() {
        lblStatus.setText("");
        tbxDisplay.setText("");
    }
    private class ProcessBtnListener implements ActionListener {
        String errorMsg = "";
        int meterTime, carTime;
        @Override
        public void actionPerformed(ActionEvent e) {
            clearDisplay();
            if (!passCar() || !passMeter() || !passAgent()) {    // validation tests before proceeding 
                displayError();
                return;
            }
            carTime = car.getParkedTime();
            if (carTime <= meterTime) {
                tbxDisplay.setForeground(Color.green);
                tbxDisplay.setText("PARKING OK!");
                System.out.println(printLog());
            }
            else {
                overtime = carTime - meterTime;
                tbxDisplay.setForeground(Color.red);
                tbxDisplay.setText("PARKING VIOLATION!");
                panelHeader.dispViolationImg();
                btnTicket.setEnabled(true);
            }
            btnProcess.setEnabled(false);
        }
        private void displayError() { 
            tbxDisplay.setForeground(colorWarning);
            tbxDisplay.setText("INPUT ERROR");
            lblStatus.setText(errorMsg);
            pack();
        }
        private boolean passCar() {
            String carStatus = car.checkCarInput();
            if (!carStatus.equals("0")) {
               errorMsg = "Bad car data in " + carStatus;
               return false;
            }
            return true;
        }
        private boolean passMeter() {
            if (meter.getMeterRegion().equals(ParkingMeter.DEF_METER_TXT) || 
                meter.getMeterId().equals(ParkingMeter.DEF_METER_TXT) || meter.getMeterId().equals("")) {
                errorMsg = "Missing Parking Meter Data";
                return false;
            }
            meterTime = meter.getAllottedTime();
            switch(meterTime) {
                case -1: {
                    errorMsg = String.format("Meter won\'t accept more than $%.2f", ParkingMeter.MAX_AMOUNT);
                    break;
                }
                case -2: {
                    errorMsg = "Negative amount in meter amount.";
                    break;
                }
                case -999: {
                    errorMsg = "Invalid input in meter amount.";
                    break;
                }
            }                      
            return meterTime >= 0;
        }
        private boolean passAgent() {
            if (agent.getAgentIndex() == 0) {
                errorMsg = "Add Parking Agent Info.";
                return false;
            }
            return true;
        }
    }
    public void resetPanel() {
        clearDisplay();
        panelHeader.resetPanel();
        car.resetPanel();
        meter.resetPanel();
        agent.resetPanel();
        btnProcess.setEnabled(true);
        btnTicket.setEnabled(false);
        pack();
    }
    private class TicketBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {                 // this should also write to a file
            ParkingTicket ticket = new ParkingTicket(overtime, car.toString(), meter.toString(), agent.toString());
            System.out.println(ticket);
            btnTicket.setEnabled(false);
        }
    }
    public String printLog() {       
        Date dateTime = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        String header = "----------------------PARKING LOG----------------------";
        header += "\nDATE: " + dt.format(dateTime) + "\n";
        String info = car + "\n" + meter + "\n" + agent + "\n";
        String footer = "----------------------PARKING OK!----------------------\n";
        return header + info + footer;
    }
    public static void main(String[] args) {
        ParkingMeterLog log = new ParkingMeterLog();
        log.setTitle("Municipal Parking Meter Log");
        log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        log.pack();
        log.setLocationRelativeTo(null);
        log.setVisible(true);
    }    
}
