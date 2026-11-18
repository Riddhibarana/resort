package service;

import database.RoomDAO;
import datastructure.RoomArrayList;
import model.Room;

public class RoomService {

    private RoomDAO roomDAO;
    private RoomArrayList roomDS;

    public RoomService() {

        roomDAO = new RoomDAO();
        roomDS = new RoomArrayList();

    }

    // ================= CHECK ROOM AVAILABILITY =================

    public boolean checkRoomAvailability(int roomId) {

        if (roomId <= 0) {

            System.out.println("Invalid Room ID.");
            return false;

        }

        return roomDAO.checkRoomAvailability(roomId);

    }

    // ================= ADD ROOM =================

    public void addRoom(Room room) {

        if (room.getRoomNumber() != null && !room.getRoomNumber().trim().isEmpty()) {

            if (!room.getRoomNumber().matches("\\d{1,3}")) {

                System.out.println("Room Number Must Contain Only 1 To 3 Digits.");
                return;

            }

        }
        if (room.getPricePerNight() < 500 || room.getPricePerNight() > 5000) {

            System.out.println("Price Must Be Between 500 And 5000.");
            return;

        }
         if (room.getCapacity() < 1 || room.getCapacity() > 5) {

            System.out.println("Capacity Must Be Between 1 And 5.");
            return;

        }

        if (room.getFloor() < 0 || room.getFloor() > 20) {

            System.out.println("Floor Must Be Between 0 And 20.");
            return;

        }

        boolean added = roomDAO.addRoom(room);

        if (added) {

            roomDS.add(room);
            System.out.println("Room Added Successfully.");

        }

    }

    // ================= DISPLAY ROOMS =================

    public void displayRooms() {

        roomDAO.displayRooms();

    }

    // ================= SEARCH ROOM =================

    public void searchRoom(int roomId) {

        if (roomId <= 0) {

            System.out.println("Invalid Room ID.");
            return;

        }

        roomDAO.searchRoom(roomId);

    }

    // ================= UPDATE ROOM =================

    public void updateRoom(Room room) {

        if (room.getRoomId() <= 0) {

            System.out.println("Invalid Room ID.");
            return;

        }

        if (room.getRoomNumber() != null && !room.getRoomNumber().trim().isEmpty()) {

            if (!room.getRoomNumber().matches("\\d{1,3}")) {

                System.out.println("Room Number Must Contain Only 1 To 3 Digits.");
                return;

            }

        }

        if (room.getPricePerNight() != 0) {

            if (room.getPricePerNight() < 500 || room.getPricePerNight() > 5000) {

                System.out.println("Price Must Be Between 500 And 5000.");
                return;

            }

        }

        if (room.getCapacity() != 0) {

            if (room.getCapacity() < 1 || room.getCapacity() > 5) {

                System.out.println("Capacity Must Be Between 1 And 5.");
                return;

            }

        }

        if (room.getFloor() != 0) {

            if (room.getFloor() < 0 || room.getFloor() > 20) {

                System.out.println("Floor Must Be Between 0 And 20.");
                return;

            }

        }

        boolean updated = roomDAO.updateRoom(room);

        if (updated) {

            System.out.println("Room Updated Successfully.");

        }

    }
    // ================= DELETE ROOM =================

    public void deleteRoom(int roomId) {

        if (roomId <= 0) {

            System.out.println("Invalid Room ID.");
            return;

        }

        boolean deleted = roomDAO.deleteRoom(roomId);

        if (deleted) {

            roomDS.remove(roomId);
            System.out.println("Room Deleted Successfully.");

        }

    }

    // ================= ROOM ID EXISTS =================

    public boolean isRoomIdExists(int roomId) {

        return roomDAO.isRoomIdExists(roomId);

    }

    // ================= ROOM NUMBER EXISTS =================

    public boolean isRoomNumberExists(String roomNumber) {

        return roomDAO.isRoomNumberExists(roomNumber);

    }

    // ================= ROOM STATUS =================

    public String getRoomStatus(int roomId) {

        return roomDAO.getRoomStatus(roomId);

    }

    public void updateRoomStatus(int roomId, String status) {

        roomDAO.updateRoomStatus(roomId, status);

    }

    // ================= AVAILABLE ROOMS =================

    public void displayAvailableRooms() {

        roomDAO.displayAvailableRooms();

    }

    // ================= ROOM PRICE =================

    public double getRoomPrice(int roomId) {

        return roomDAO.getRoomPrice(roomId);

    }

    // ================= ROOM AVAILABLE =================

    public boolean isRoomAvailable(int roomId) {

        return roomDAO.isRoomAvailable(roomId);

    }

    // ================= ROOM CAPACITY =================

    public int getRoomCapacity(int roomId) {

        return roomDAO.getRoomCapacity(roomId);

    }
    public boolean isRoomNumberExistsForOtherRoom(String roomNumber, int roomId) {

        return roomDAO.isRoomNumberExistsForOtherRoom(roomNumber, roomId);

    }


}