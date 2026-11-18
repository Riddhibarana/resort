package Collection;

import java.util.ArrayList;
import java.util.Iterator;
import model.Room;

public class RoomCollection {

    private ArrayList<Room> roomCollection;


    // Constructor
    public RoomCollection() {

        roomCollection = new ArrayList<>();
    }


    // Add Room
    public void addRoom(Room r) {

        roomCollection.add(r);
        System.out.println("Room Added Successfully");
    }


    // Remove Room
    public void removeRoom(int roomId) {

        Iterator<Room> iterator = roomCollection.iterator();

        while(iterator.hasNext()) {

            Room r = iterator.next();

            if(r.getRoomId() == roomId) {

                iterator.remove();
                System.out.println("Room Removed Successfully");
                return;
            }
        }

        System.out.println("Room Not Found");
    }


    // Search Room
    public Room searchRoom(int roomId) {

        for(Room r : roomCollection) {

            if(r.getRoomId() == roomId) {

                return r;
            }
        }

        return null;
    }


    // Display All Rooms
    public void displayRooms() {

        if(roomCollection.isEmpty()) {

            System.out.println("No Rooms Available");
            return;
        }


        System.out.println("----- Room Collection -----");


        for(Room r : roomCollection) {

            System.out.println(r);
        }
    }


    // Size
    public int getSize() {

        return roomCollection.size();
    }


    // Check Empty
    public boolean isEmpty() {

        return roomCollection.isEmpty();
    }
}