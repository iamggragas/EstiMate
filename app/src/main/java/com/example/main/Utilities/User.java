package com.example.main.Utilities;

public class User {
        String name, email, phone, password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public User(String name, String phone, String email, String password) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.password = password;
        }

        public User(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }

        public User() {
        }

}
