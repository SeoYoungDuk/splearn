package com.tobyspring.learningtest.archunit.application;

import com.tobyspring.learningtest.archunit.adapter.MyAdapter;

public class MyService {
    MyService2 myService2;

    void run() {
        myService2 = new MyService2();
        System.out.println(myService2);
    }
}
