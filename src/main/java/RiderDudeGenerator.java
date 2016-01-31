import java.util.*;

/**
 * Created by Tricia on 12/28/15.
 */
public class RiderDudeGenerator {

    // liftRider is a structure which has a hash of the building floors and a list of
    // riders to be serviced on that floor
    public HashMap <Integer, List<RiderDude>> liftRider = new HashMap <Integer, List<RiderDude>>();

    public int addRider() {
        // Create a rider, add them to list of dudes to be serviced on the floor
        RiderDude dude = new RiderDude();
        List <RiderDude> waiting = liftRider.get(dude.startFloor);
        if (waiting == null) waiting = new ArrayList<RiderDude>();
        waiting.add(dude);
        liftRider.put(dude.startFloor, waiting);  // replace list w/ dude added at start floor
        System.out.println("dude added : " + dude.startFloor + "/" + dude.stopFloor);

        return(dude.startFloor);
    }

    /**
     * service floor
     * find first entry for this floor - remove onLift
     */
    public List<Integer> serviceAFloor(int floor) {
        List<Integer> floorToService = new ArrayList<>();  //List of floors that need work

        List<RiderDude> service = new ArrayList(liftRider.get(floor));

        //Loop through guys on this floor and service them
        for (RiderDude dude : service) {
            System.out.println("service a dude");
            if (!dude.isOnLift()) {  //Dude waiting to get on
                System.out.println("Dude on lift moving to floor");
                List<RiderDude> waiting = liftRider.get(dude.stopFloor);
                if (waiting == null) waiting = new ArrayList<RiderDude>();
                waiting.add(dude);
                liftRider.put(dude.stopFloor, waiting);  //Dude on lift - pushed stop floor
                floorToService.add(dude.stopFloor);
                System.out.println(liftRider);
            }
        }
        // Dudes on lift got off - so ...
        liftRider.remove(floor); //Everyone serviced on this floor

        System.out.println("Serviced floor : " + floor);
        return floorToService;
    }

}
