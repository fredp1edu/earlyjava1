/** CST3606 2/8/2016
 *  This class makes up the Parking Ticket, which is instantiated with 
 *  the various Parking classes toString values; The Parking Ticket toString prints out the ticket.
 *  @author Fred
 */
package parkingmeterlog;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class ParkingTicket {
    
    private final String carInfo, meterInfo, agentInfo;
    private final double fine;
    private final int minutesOver;
    
    public ParkingTicket(int ot, String carInfo, String meterInfo, String agentInfo) {
        this.carInfo = carInfo;
        this.meterInfo = meterInfo;
        this.agentInfo = agentInfo;
        minutesOver = ot;
        fine = 25.0 + Math.floor(minutesOver / 60) * 10.0;
    }
    public String toString() {
        Date dateTime = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        Calendar dueDate = Calendar.getInstance();
        dueDate.setTime(dateTime);
        dueDate.add(Calendar.DATE, 60);
        SimpleDateFormat dd = new SimpleDateFormat("E yyyy.MM.dd");
        
        String header = "----------------------TRAFFIC VIOLATION-----------------------";
        header += "\nISSUE DATE: " + dt.format(dateTime) + "\n";
        String txtFine = String.format("\nTime over Limit: %d minutes   AMOUNT DUE: $%.2f\n", 
                minutesOver, fine);
        String txtDue = "\nFINE DUE 60 DAYS FROM ISSUE DATE:  " + dd.format(dueDate.getTime()) + 
                        "\n           Additional fines imposed after due date.";
        String footer = "\n-------------Remit payment to www.paymyticket.com-----------\n";
        
        return header + carInfo + "\n" + meterInfo + txtFine + agentInfo + txtDue + footer;
    }
}
