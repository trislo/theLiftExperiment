import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tricia on 11/15/15.
 */

public class LiftOP {
    private static final int MAX_QUEUED = TheLift.MAX_PASSENGERS * 4;

    private static List<RiderDude> bellRingers = new ArrayList<RiderDude> ();
    private static List<RiderDude> liftRiders = new ArrayList<RiderDude> ();

    /**
     *
     * @param args
     */
    public static void main(String [] args) {
        /* queue for each floor of building -- timer loop where a rider
        is create and queued to a random floor to go to another floor --
        button push  -- the lift could ask what if it has to stop at t floor or
        if any one is in the current direction of travel before switching floors.
        to switch floors the lift will use a timer factored with the number of floors
        needing to travel -- or just move one floor at a time -- the lift can become
        ideal -- possible to have no one get on at a timer interupt
         */
        RiderDudeGenerator dudeGenerator = new RiderDudeGenerator();
        TheLift liftA = new TheLift(dudeGenerator);

        boolean liftToggle = false; //start with creating a dude
        for (int i = 0; i < 20; i++) {
            if (liftToggle) {
               liftA.openDoor();
                System.out.println("");
            }
            else {
                int floor = dudeGenerator.addRider();
                liftA.setStopReq(floor);
            }

            liftToggle = !liftToggle;

            try {
                Thread.sleep((long) 200);  // door close - will notify if lift empty, else timeout
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // run lift until all passengers off
        while (liftA.getDirection() != TheLift.liftState.IDLE) {
            liftA.openDoor();
            try{ Thread.sleep((long) 200); } catch (InterruptedException e) {;};
        }
    }
}
