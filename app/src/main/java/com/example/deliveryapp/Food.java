package com.example.deliveryapp;

public class Food {

        private int imageResource;
        private String title;

        public Food(int imageResource, String title) {
            this.imageResource = imageResource;
            this.title = title;
        }

        public int getImageResource() {
            return imageResource;
        }

        public String getTitle() {
            return title;
        }

}
