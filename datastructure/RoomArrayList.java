package datastructure;


import model.Room;

public class RoomArrayList {

    private Room[] rooms;

    private int size;
    public RoomArrayList() {

        rooms = new Room[100];

        size = 0;

    }
    public void add(Room room) {

        rooms[size] = room;

        size++;

    }
    public Room search(int roomId) {

        for(int i=0;i<size;i++) {

            if(rooms[i].getRoomId()==roomId) {

                return rooms[i];

            }

        }

        return null;

    }
    public void display() {

        for(int i=0;i<size;i++) {

            rooms[i].displayRoom();

        }

    }
    // Remove Room
    public void remove(int roomId) {

        for (int i = 0; i < size; i++) {

            if (rooms[i].getRoomId() == roomId) {

                for (int j = i; j < size - 1; j++) {
                    rooms[j] = rooms[j + 1];
                }

                rooms[size - 1] = null;
                size--;

                System.out.println("Room Removed Successfully");
                return;
            }
        }

    }

}
