# earlyjava1
This is an early java project using swing components to create a GUI interface for entering in parking meter information, which assesses whether a traffic violation has occurred due to the time the car has been parked and the amount tendered into the meter.
User enters data for car license, make, model, region of meter location and amount entered into meter. The original IDE version had sound, but the sound won't work in the maven version. Have to change the way sound is played (that old school applet way is no longer feasible).
Next:
1. Modify so meter data reads from an xml file and the data that's being entered is also written to another xml file. 
2. Deploy to Heroku / AWS / Google Cloud, whichever works easiest, so that it can be displayed. 