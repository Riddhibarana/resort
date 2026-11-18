package menu;

import java.util.Scanner;

import model.Room;
import service.RoomService;

public class RoomMenu {

    private Scanner scanner;
    private RoomService roomService;

    public RoomMenu() {

        scanner = new Scanner(System.in);
        roomService = new RoomService();

    }

    // ================= ROOM MANAGEMENT =================

    public void displayMenu() {

        int choice;

        while (true) {

            System.out.println("\n================================");
            System.out.println("       ROOM MANAGEMENT");
            System.out.println("================================");
            System.out.println("1. Add Room");
            System.out.println("2. View All Rooms");
            System.out.println("3. Search Room");
            System.out.println("4. Update Room");
            System.out.println("5. Delete Room");
            System.out.println("6. Check Room Availability");
            System.out.println("0. Back");
            System.out.println("================================");

            System.out.print("Enter Choice : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Choice Cannot Be Empty.");
                continue;

            }

            try {

                choice = Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");
                continue;

            }

            switch (choice) {

                case 1:
                    addRoom();
                    break;

                case 2:
                    viewRooms();
                    break;

                case 3:
                    searchRoom();
                    break;

                case 4:
                    updateRoom();
                    break;

                case 5:
                    deleteRoom();
                    break;

                case 6:
                    checkAvailability();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid Choice.");

            }

        }

    }

    // ================= ADD ROOM =================

    private void addRoom() {

        Room room = new Room();

        // ROOM NUMBER

        while (true) {

            System.out.print("Enter Room Number : ");

            String roomNo = scanner.nextLine().trim();

            if (roomNo.isEmpty()) {

                System.out.println("Room Number Cannot Be Empty.");
                continue;

            }

            if (!roomNo.matches("^[1-9][0-9]{2}$")) {

                System.out.println("Invalid Room Number! Enter 3 digits only (100-999).");
                continue;

            }
            if (roomService.isRoomNumberExists(roomNo)) {

                System.out.println("Room Number Already Exists.");
                continue;

            }

            room.setRoomNumber(roomNo);
            break;

        }

        // ROOM TYPE

        while (true) {

            System.out.println("\nSelect Room Type");
            System.out.println("1. Single");
            System.out.println("2. Double");
            System.out.println("3. Deluxe");
            System.out.println("4. Suite");

            System.out.print("Enter Choice : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Choice Cannot Be Empty.");
                continue;

            }

            try {

                int type = Integer.parseInt(input);

                switch (type) {

                    case 1:
                        room.setRoomType("Single");
                        break;

                    case 2:
                        room.setRoomType("Double");
                        break;

                    case 3:
                        room.setRoomType("Deluxe");
                        break;

                    case 4:
                        room.setRoomType("Suite");
                        break;

                    default:
                        System.out.println("Invalid Choice.");
                        continue;

                }

                break;

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }
        // ================= PRICE =================

        while (true) {

            System.out.print("Enter Price Per Night : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Price Cannot Be Empty.");
                continue;

            }

            try {

                if (input.contains(".")) {

                    System.out.println("Decimal Price Is Not Allowed.");
                    continue;

                }

                int price = Integer.parseInt(input);

                if (price == 0) {

                    System.out.println("Price Cannot Be Zero.");
                    continue;

                }

                if (price < 500 || price > 5000) {

                    System.out.println("Price Must Be Between 500 And 5000.");
                    continue;

                }

                room.setPricePerNight(price);
                break;

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }
        // ================= CAPACITY =================

        while (true) {

            System.out.print("Enter Capacity : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Capacity Cannot Be Empty.");
                continue;

            }

            try {

                int capacity = Integer.parseInt(input);

                if (capacity < 1 || capacity > 5) {

                    System.out.println("Capacity Must Be Between 1 And 5.");
                    continue;

                }

                room.setCapacity(capacity);
                break;

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }

        // ================= FLOOR =================

        while (true) {

            System.out.print("Enter Floor : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Floor Cannot Be Empty.");
                continue;

            }

            try {

                int floor = Integer.parseInt(input);

                if (floor < 0 || floor > 20) {

                    System.out.println("Floor Must Be Between 0 And 20.");
                    continue;

                }

                room.setFloor(floor);
                break;

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }

        // ================= STATUS =================

        while (true) {

            System.out.println("\nSelect Room Status");
            System.out.println("1. Available");
            System.out.println("2. Booked");
            System.out.println("3. Maintenance");

            System.out.print("Enter Choice : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Choice Cannot Be Empty.");
                continue;

            }

            try {

                int status = Integer.parseInt(input);

                switch (status) {

                    case 1:
                        room.setStatus("Available");
                        break;

                    case 2:
                        room.setStatus("Booked");
                        break;

                    case 3:
                        room.setStatus("Maintenance");
                        break;

                    default:
                        System.out.println("Invalid Choice.");
                        continue;

                }

                break;

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }

        roomService.addRoom(room);

    }
    // ================= VIEW ALL ROOMS =================

    private void viewRooms() {

        roomService.displayRooms();

    }

    // ================= SEARCH ROOM =================

    private void searchRoom() {

        while (true) {

            System.out.print("Enter Room ID : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Room ID Cannot Be Empty.");
                continue;

            }

            try {

                int roomId = Integer.parseInt(input);

                if (roomId <= 0) {

                    System.out.println("Room ID Must Be Greater Than 0.");
                    continue;

                }

                if (!roomService.isRoomIdExists(roomId)) {

                    System.out.println("Room ID Not Found.");
                    continue;

                }

                roomService.searchRoom(roomId);
                return;

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }

    }

    // ================= UPDATE ROOM =================

    private void updateRoom() {

        Room room = new Room();

        while (true) {

            System.out.print("Enter Room ID : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Room ID Cannot Be Empty.");
                continue;

            }

            try {

                int roomId = Integer.parseInt(input);

                if (roomId <= 0) {

                    System.out.println("Room ID Must Be Greater Than 0.");
                    continue;

                }

                if (!roomService.isRoomIdExists(roomId)) {

                    System.out.println("Room ID Not Found.");
                    continue;

                }

                if (roomService.getRoomStatus(roomId).equalsIgnoreCase("Booked")) {

                    System.out.println("Room Is Booked. We Can't Update This Room.");
                    System.out.println("Enter Another Room ID To Update.");
                    continue;

                }

                room.setRoomId(roomId);
                break;
            }
            catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }

        int choice;

        while (true) {

            System.out.println("\n========== UPDATE ROOM ==========");
            System.out.println("1. Room Number");
            System.out.println("2. Room Type");
            System.out.println("3. Price");
            System.out.println("4. Capacity");
            System.out.println("5. Floor");
            System.out.println("6. Status");
            System.out.println("0. Update & Exit");

            System.out.print("Enter Choice : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Choice Cannot Be Empty.");
                continue;

            }

            try {

                choice = Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");
                continue;

            }
            switch (choice) {

                // ================= ROOM NUMBER =================

                case 1:

                    while (true) {

                        System.out.print("Enter New Room Number : ");

                        String roomNo = scanner.nextLine().trim();

                        if (roomNo.isEmpty()) {

                            System.out.println("Room Number Cannot Be Empty.");
                            continue;

                        }

                        if (!roomNo.matches("^[1-9][0-9]{2}$")) {

                            System.out.println("Invalid Room Number! Enter 3 digits only (100-999).");
                            continue;

                        }


                        if (roomService.isRoomNumberExistsForOtherRoom(roomNo, room.getRoomId())) {

                            System.out.println("Room Already Exists. Enter Another Room Number To Update.");
                            continue;

                        }

                        room.setRoomNumber(roomNo);
                        break;

                    }

                    break;
                // ================= ROOM TYPE =================

                case 2:

                    while (true) {

                        System.out.println("\nSelect Room Type");
                        System.out.println("1. Single");
                        System.out.println("2. Double");
                        System.out.println("3. Deluxe");
                        System.out.println("4. Suite");

                        System.out.print("Enter Choice : ");

                        String type = scanner.nextLine().trim();

                        try {

                            int roomType = Integer.parseInt(type);

                            switch (roomType) {

                                case 1:
                                    room.setRoomType("Single");
                                    break;

                                case 2:
                                    room.setRoomType("Double");
                                    break;

                                case 3:
                                    room.setRoomType("Deluxe");
                                    break;

                                case 4:
                                    room.setRoomType("Suite");
                                    break;

                                default:
                                    System.out.println("Invalid Choice.");
                                    continue;

                            }

                            break;

                        } catch (NumberFormatException e) {

                            System.out.println("Enter Numbers Only.");

                        }

                    }

                    break;

                // ================= PRICE =================

                case 3:

                    while (true) {

                        System.out.print("Enter New Price : ");

                        String priceInput = scanner.nextLine().trim();

                        if (priceInput.isEmpty()) {

                            System.out.println("Price Cannot Be Empty.");
                            continue;

                        }

                        try {

                            if (priceInput.contains(".")) {

                                System.out.println("Decimal Price Is Not Allowed.");
                                continue;

                            }

                            int price = Integer.parseInt(priceInput);

                            if (price == 0) {

                                System.out.println("Price Cannot Be Zero.");
                                continue;

                            }

                            if (price < 500 || price > 5000) {

                                System.out.println("Price Must Be Between 500 And 5000.");
                                continue;

                            }

                            room.setPricePerNight(price);
                            break;

                        } catch (NumberFormatException e) {

                            System.out.println("Enter Numbers Only.");

                        }

                    }

                    break;   // ================= CAPACITY =================

                case 4:

                    while (true) {

                        System.out.print("Enter Capacity : ");

                        String capInput = scanner.nextLine().trim();

                        if (capInput.isEmpty()) {

                            System.out.println("Capacity Cannot Be Empty.");
                            continue;

                        }

                        try {

                            int capacity = Integer.parseInt(capInput);

                            if (capacity < 1 || capacity > 5) {

                                System.out.println("Capacity Must Be Between 1 And 5.");
                                continue;

                            }

                            room.setCapacity(capacity);
                            break;

                        } catch (NumberFormatException e) {

                            System.out.println("Enter Numbers Only.");

                        }

                    }

                    break;
                // ================= FLOOR =================

                case 5:

                    while (true) {

                        System.out.print("Enter Floor : ");

                        String floorInput = scanner.nextLine().trim();

                        if (floorInput.isEmpty()) {

                            System.out.println("Floor Cannot Be Empty.");
                            continue;

                        }

                        try {

                            int floor = Integer.parseInt(floorInput);

                            if (floor < 0 || floor > 20) {

                                System.out.println("Floor Must Be Between 0 And 20.");
                                continue;

                            }

                            room.setFloor(floor);
                            break;

                        } catch (NumberFormatException e) {

                            System.out.println("Enter Numbers Only.");

                        }

                    }

                    break;

                // ================= STATUS =================

                case 6:

                    while (true) {

                        System.out.println("\nSelect Room Status");
                        System.out.println("1. Available");
                        System.out.println("2. Booked");
                        System.out.println("3. Maintenance");

                        System.out.print("Enter Choice : ");

                        String statusInput = scanner.nextLine().trim();

                        if (statusInput.isEmpty()) {

                            System.out.println("Choice Cannot Be Empty.");
                            continue;

                        }

                        try {

                            int status = Integer.parseInt(statusInput);

                            switch (status) {

                                case 1:
                                    room.setStatus("Available");
                                    break;

                                case 2:
                                    room.setStatus("Booked");
                                    break;

                                case 3:
                                    room.setStatus("Maintenance");
                                    break;

                                default:
                                    System.out.println("Invalid Choice.");
                                    continue;

                            }

                            break;

                        } catch (NumberFormatException e) {

                            System.out.println("Enter Numbers Only.");

                        }

                    }

                    break;

                // ================= SAVE =================

                case 0:

                    roomService.updateRoom(room);
                    return;

                default:

                    System.out.println("Invalid Choice.");

            }

        }

    }

    // ================= DELETE ROOM =================

    private void deleteRoom() {

        while (true) {

            System.out.print("Enter Room ID : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Room ID Cannot Be Empty.");
                continue;

            }

            try {

                int roomId = Integer.parseInt(input);

                if (roomId <= 0) {

                    System.out.println("Room ID Must Be Greater Than 0.");
                    continue;

                }

                if (!roomService.isRoomIdExists(roomId)) {

                    System.out.println("Room ID Not Found.");
                    continue;

                }

                System.out.print("Are You Sure You Want To Delete? (Y/N) : ");

                String confirm = scanner.nextLine().trim();

                if (confirm.equalsIgnoreCase("Y")) {

                    roomService.deleteRoom(roomId);

                } else {

                    System.out.println("Delete Cancelled.");

                }

                return;

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }

    }

    // ================= CHECK ROOM AVAILABILITY =================

    private void checkAvailability() {

        while (true) {

            System.out.print("Enter Room ID : ");

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {

                System.out.println("Room ID Cannot Be Empty.");
                continue;

            }

            try {

                int roomId = Integer.parseInt(input);

                if (roomId <= 0) {

                    System.out.println("Room ID Must Be Greater Than 0.");
                    continue;

                }

                roomService.checkRoomAvailability(roomId);
                return;

            } catch (NumberFormatException e) {

                System.out.println("Enter Numbers Only.");

            }

        }

    }

}