package com.spring.project.portfolio.observer.subject;

import com.spring.project.portfolio.observer.Observer;


public interface Subject {
    public void registerOberserver(Observer o);
    public void removeOberserver(Observer o);
    public void notifyObservers();
}
