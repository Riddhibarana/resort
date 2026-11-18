package model;
public class customer
 {

        private int customerId;
        private String name;
        private String gender;
        private int age;
        private String phone;
        private String email;
        private String address;
        private String idProof;
        private String checkindate;
        private String ckeckoutdate;
     private String password;
     private int firstLogin;
        // Default Constructor
        public customer()
        {
        }


     public customer(int customerId, String name, String gender, int age,
                     String phone, String email, String address,
                     String idProof, String checkindate,
                     String ckeckoutdate, String password) {

         this.customerId = customerId;
         this.name = name;
         this.gender = gender;
         this.age = age;
         this.phone = phone;
         this.email = email;
         this.address = address;
         this.idProof = idProof;
         this.checkindate = checkindate;
         this.ckeckoutdate = ckeckoutdate;
         this.password = password;
     }



     public String getCheckindate() {
         return checkindate;
     }
     public void setcustomerId(int customerId) {
         this.customerId= customerId;
     }


     public void setCheckindate(String checkindate) {
         this.checkindate = checkindate;
     }

     public String getCkeckoutdate() {
         return ckeckoutdate;
     }

     public void setCkeckoutdate(String ckeckoutdate) {
         this.ckeckoutdate = ckeckoutdate;
     }



     public int getcustomerId() {
         return customerId;
     }


     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getGender() {
         return gender;
     }

     public void setGender(String gender) {
         this.gender = gender;
     }

     public int getAge() {
         return age;
     }

     public void setAge(int age) {
         this.age = age;
     }

     public String getPhone() {
         return phone;
     }

     public void setPhone(String phone) {
         this.phone = phone;
     }

     public String getEmail() {
         return email;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public String getAddress() {
         return address;
     }

     public void setAddress(String address) {
         this.address = address;
     }

     public String getIdProof() {
         return idProof;
     }

     public void setIdProof(String idProof) {
         this.idProof = idProof;
     }
     public String toString() {
         return "customer{" +
                 "customerId=" + customerId +
                 ", name='" + name + '\'' +
                 ", gender='" + gender + '\'' +
                 ", age=" + age +
                 ", phone='" + phone + '\'' +
                 ", email='" + email + '\'' +
                 ", address='" + address + '\'' +
                 ", idProof='" + idProof + '\'' +
                 ", checkindate='" + checkindate + '\'' +
                 ", ckeckoutdate='" + ckeckoutdate + '\'' +
                 '}';
     }
     public String getPassword() {
         return password;
     }

     public void setPassword(String password) {
         this.password = password;
     }
     public  int getCustomerId()
     {
         return customerId;

     }
     public int getFirstLogin() {
         return firstLogin;
     }

     public void setFirstLogin(int firstLogin) {
         this.firstLogin = firstLogin;
     }


 }

