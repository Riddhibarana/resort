package service;

import database.FeedbackDAO;
import model.Feedback;

public class FeedbackService {

    private FeedbackDAO dao;

    public FeedbackService() {

        dao = new FeedbackDAO();

    }

    // ================= ADD FEEDBACK =================

    public void addFeedback(Feedback feedback) {

        if(!dao.checkCustomerId(feedback.getCustomerId())) {

            System.out.println("Invalid Customer ID.");
            return;

        }

        if(feedback.getRating() < 1 || feedback.getRating() > 5) {

            System.out.println("Rating must be between 1 and 5.");
            return;

        }

        if(feedback.getComment() == null ||
                feedback.getComment().trim().isEmpty()) {

            System.out.println("Comment cannot be empty.");
            return;

        }

        dao.addFeedback(feedback);

    }
    // ================= ADD FEEDBACK BY CUSTOMER NAME =================

    public void addFeedbackByCustomerName(String customerName,
                                          Feedback feedback) {

        int customerId = dao.getCustomerIdByName(customerName);

        if(customerId == -1) {

            System.out.println("Customer Not Found.");
            return;

        }

        feedback.setCustomerId(customerId);

        addFeedback(feedback);

    }
    public int getCustomerIdByName(String customerName) {

        return dao.getCustomerIdByName(customerName);

    }

}