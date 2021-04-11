package com.spring.project.portfolio.observer;


import com.spring.project.portfolio.entities.TStock;
import java.util.Date;
import java.util.Set;

public interface Observer {
    public void  update(Set<TStock> obStocks, Set<TStock> osStocks, Date date);
}
