package service;

import database.AdminFoodDAO;
import model.AdminFood;

public class AdminFoodService {

    private AdminFoodDAO dao;

    public AdminFoodService() {

        dao = new AdminFoodDAO();

    }

    // ================= ADD FOOD =================

    public void addFood(AdminFood food) {

        if(food.getFoodName() == null ||
                food.getFoodName().trim().isEmpty()) {

            System.out.println("Food Name Cannot Be Empty.");
            return;

        }

        if(!food.getFoodName().matches("[A-Za-z ]{3,30}")) {

            System.out.println("Food Name Must Contain Only Alphabets.");
            return;

        }

        if(food.getPrice() < 20 || food.getPrice() > 1000)
        {
            System.out.println("Price Must Be Between ₹20 And ₹1000.");
            return;

        }

        dao.addFood(food);

        System.out.println("Food Item Added Successfully.");

    }

    // ================= VIEW FOOD =================

    public void viewFood() {

        dao.viewFood();

    }

    // ================= CHECK FOOD ID =================

    public boolean checkFoodId(int foodId) {

        return dao.checkFoodId(foodId);

    }

    // ================= UPDATE FOOD =================

    public void updateFood(int foodId,
                           int choice,
                           String value) {

        if(!dao.checkFoodId(foodId)) {

            System.out.println("Food ID Not Found.");
            return;

        }

        if(choice == 1) {

            if(value.trim().isEmpty()) {

                System.out.println("Food Name Cannot Be Empty.");
                return;

            }

            if(!value.matches("[A-Za-z ]{3,30}")) {

                System.out.println("Food Name Must Contain Only Alphabets.");
                return;

            }

        }

        if(choice == 2) {

            try {

                double price =
                        Double.parseDouble(value);

                if(price < 20 || price > 1000)
                {
                    System.out.println("Price Must Be Between ₹20 And ₹1000.");
                    return;

                }

            }

            catch(Exception e) {

                System.out.println("Enter Valid Price.");
                return;

            }

        }

        dao.updateFood(foodId, choice, value);

        System.out.println("Food Updated Successfully.");

    }

    // ================= DELETE FOOD =================

    public void deleteFood(int foodId) {

        if(!dao.checkFoodId(foodId)) {

            System.out.println("Food ID Not Found.");
            return;

        }

        dao.deleteFood(foodId);

        System.out.println("Food Deleted Successfully.");

    }
    // ================= VIEW ALL FOOD ORDERS =================

    public void viewAllFoodOrders() {

        dao.viewAllFoodOrders();

    }
    public boolean checkFoodName(String foodName) {

        return dao.checkFoodName(foodName);

    }
    public boolean checkFoodNameForUpdate(int foodId, String foodName) {

        return dao.checkFoodNameForUpdate(foodId, foodName);

    }

}