package com.example.springstarter.controller;

import java.util.ArrayList;
import java.util.List;

public class TestMain {
    private static class Shape {

    }

    private static class Square extends Shape {

    }

    private static class Circle extends Shape {

    }

    public static void main(String[] args) {
        List<Shape> list = new ArrayList<>();
        list = new ArrayList<Shape>();
        List<Circle> circleList = new ArrayList<>();
        list = new ArrayList<>(circleList);
        list.clear();
        list.add(new Circle());

        System.out.println(list);
    }
}
