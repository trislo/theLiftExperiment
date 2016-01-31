/**
 * Created by Tricia on 11/15/15.
 *
 * should this be a singleton -- no not really the building could have multiple elevators
 */
import java.util.Arrays;
import java.util.List;

public class TheLift {
    public static final int MAX_PASSENGERS = 9;
    public static int MAX_FLOORS = 5;
    public enum liftState  { UP, DOWN, IDLE };

    private int liftFloor;
    private liftState direction;
    private int numPassengers;
    private boolean doorOpen;
    private boolean [] stopReq = new boolean[MAX_FLOORS];  //sync required
    private RiderDudeGenerator ridersToService;

    /**
     *
     * @param riders
     */
    public TheLift(RiderDudeGenerator riders)  {
        liftFloor = 0;
        direction = liftState.IDLE;
        numPassengers = 0;
        for (int i = 0; i < MAX_FLOORS; i++)
            stopReq[i] = false;
        doorOpen = false;
        ridersToService = riders;
    }

    /**
     *
     */
    public void openDoor() {
        // any work at this floor -- open door -- notify/wait
        if (getStopReq(liftFloor)) {
            System.out.println("Work on floor : " + liftFloor);
            doorOpen = true;
            // Allow dudes to get off / on
            List<Integer> floorsNeedService = ridersToService.serviceAFloor(liftFloor);
            for (int floor : floorsNeedService) {
                setStopReq(floor);
            }
            resetStopReq(liftFloor);  // work on this floor complete
            doorOpen = false;
        }

        System.out.println("button active " + Arrays.toString(stopReq));

        if (buttonActive()) {
            System.out.println("Button was active");
            if (direction.equals(liftState.IDLE))
                startLift();  //determine direction
            moveAFloor();
        } else { //no one using lift
            this.direction = liftState.IDLE;
        }

        System.out.println("The direction is " + direction);
    }

    private boolean buttonActive() {
        return(Arrays.toString(stopReq).contains("true"));
    }

    //Used when lift is empty to restart motion - will go down first if multiple requests
    //are pending
    private void startLift() {
        //Compare from first floor to floor below current
        if (workBelow())
            this.direction = liftState.DOWN;
        //Compare from floor above current to top floor
        else if (workAbove())
            this.direction = liftState.UP;
        else
            System.out.println("Start Lift call with only work on current or no floor");
    }

    // Change floor watching for the top and bottom where direction of the lift must change
    public void moveAFloor()  {
        //Direction change
        if (direction.equals(liftState.UP) && liftFloor == MAX_FLOORS-1)
            direction = liftState.DOWN;
        else if (direction.equals(liftState.DOWN) && liftFloor == 0)
            direction = liftState.UP;

        //Move the lift
        if (direction.equals(liftState.UP))
            liftFloor++;
        else
            liftFloor--;
        System.out.println("On floor " + liftFloor);
    }

    private boolean workAbove() {
        if (liftFloor == MAX_FLOORS) return false;
        return(Arrays.toString(Arrays.copyOfRange(stopReq, liftFloor+1, MAX_FLOORS)).contains("true"));
    }

    private boolean workBelow() {
        if (liftFloor == 0) return false;
        return(Arrays.toString(Arrays.copyOfRange(stopReq, 0, liftFloor-1)).contains("true"));
    }

    public int getFloor() {
        return liftFloor;
    }

    public liftState getDirection() {
        return direction;
    }

    public int getNumPassengers() {
        return numPassengers;
    }

    public void setNumPassengers(int numPassengers) {
        this.numPassengers = numPassengers;
    }

    public boolean getStopReq(int floor) {
        return (stopReq[floor]);
    }

    public void setStopReq(int floor) {
        this.stopReq[floor] = true;
    }

    public void resetStopReq(int floor) {
        this.stopReq[floor] = false;
    }
}
