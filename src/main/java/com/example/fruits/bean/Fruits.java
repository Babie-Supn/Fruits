package com.example.fruits.bean;

public class Fruits {
        private String name;
        private double price;
        private  int count;
        private  String remark;
        private int id;

        public Fruits() {
        }

        public Fruits(String name, double price, int count, String remark, int id) {
            this.name = name;
            this.price = price;
            this.count = count;
            this.remark = remark;
            this.id = id;
        }
    public Fruits(String name, double price, int count, String remark) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.remark = remark;
    }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Fruits{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    ", count=" + count +
                    ", remark='" + remark + '\'' +
                    ", id=" + id +
                    '}';
        }
    }


