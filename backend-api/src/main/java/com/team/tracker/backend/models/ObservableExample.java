package com.team.tracker.backend.models;

import java.util.Observable;
import java.util.Observer;

import org.hibernate.annotations.SourceType;

class FirstNewsReader implements Observer {
    public void update(Observable obj, Object arg) {
        System.out.println("FirstNewsReader got the News: " + (String) arg);
    }
}

class SecondNewsReader implements Observer {
    public void update(Observable obj, Object arg) {
        System.out.println("SecondNewsReader got the News: " + (String) arg);
    }
}

class News extends Observable {
    void news() {
        String[] news = { "News 1", "News 2", "News 3" };
        for (String s : news) {
            setChanged();
            notifyObservers(s);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Error!");
            }
        }
    }
}

public class ObservableExample {
    public static void main(String args[]) {
        News observedNews = new News();
        FirstNewsReader reader1 = new FirstNewsReader();
        SecondNewsReader reader2 = new SecondNewsReader();
        observedNews.addObserver(reader1);
        observedNews.addObserver(reader2);
        observedNews.news();
    }
}