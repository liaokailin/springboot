package com.lkl.framework;


public class TestEvent {

    public static void main(String[] args) {
        EventSourceObject eventSource = new EventSourceObject();
        eventSource.addListener(new CusEventListener());
        eventSource.addListener(new CusEventListener());
        eventSource.addListener(new CusEventListener());
        eventSource.clickButton();

    }
}
