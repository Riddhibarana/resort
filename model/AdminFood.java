package model;

public class AdminFood {

    private int foodId;
    private String foodName;
    private double price;

    // ================= DEFAULT CONSTRUCTOR =================

    public AdminFood() {
    }

    // ================= PARAMETERIZED CONSTRUCTOR =================

    public AdminFood(int foodId, String foodName, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
    }

    // ================= GETTERS =================

    public int getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getPrice() {
        return price;
    }

    // ================= SETTERS =================

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // ================= TO STRING =================

    @Override
    public String toString() {
        return "AdminFood{" +
                "foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", price=" + price +
                '}';
    }
}