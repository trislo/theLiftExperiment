/**
 * Created by Tricia on 11/27/15.
 */
public class RiderDude {

    public int startFloor;
    public int stopFloor;
    private boolean onLift;
    private int extraDoorOpens;
    private int timeWaiting;
    private int liftTooFull;

    public RiderDude() {
        startFloor = getRandomFloor();
        stopFloor = startFloor;  // force the following loop - at least once
        while (startFloor == stopFloor) stopFloor = getRandomFloor();

        onLift = false;
        extraDoorOpens = 0;
        timeWaiting = 0;
        liftTooFull = 0;
    }

    public boolean isOnLift() {
        return onLift;
    }

    /**
     * getRandomFloor
     *
     * @return - zero relative floor of the buildin
     */
    private int getRandomFloor() {
        int randomFloor = (int) Math.round(Math.random() * (double) TheLift.MAX_FLOORS);
        if (randomFloor == TheLift.MAX_FLOORS) randomFloor = TheLift.MAX_FLOORS-1;
        return(randomFloor);
    }

}
