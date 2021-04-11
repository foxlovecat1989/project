package com.spring.project.portfolio.observer.subject;

import com.spring.project.portfolio.entities.TStock;
import com.spring.project.portfolio.observer.Observer;
import java.util.ArrayList;
import java.util.Date;

import java.util.Set;


public class OBOSNews implements Subject{
	

	private ArrayList observers; // 儲存觀察者
	private Set<TStock> obStocks;
        private Set<TStock> osStocks;
	private Date date;
	
	public OBOSNews() {
		observers = new ArrayList();
	}
	
	// 註冊觀察者
	@Override
	public void registerOberserver(Observer o) {
		observers.add(o);
                System.out.println("registerOberserver" + o);
	}
	
	// 移除觀察者
	@Override
	public void removeOberserver(Observer o) {
		int index = observers.indexOf(o);
		if(index > 0) {
			observers.remove(index);
		}
	}
	
	// 通知觀察者
	@Override
	public void notifyObservers() {
		observers.stream()
			  .forEach( e -> ((Observer) e).update(obStocks, osStocks, date));
	}
	
	// 當主題更新數據時通知觀察者
	public void changed() {
            notifyObservers(); 
	}
	
	// 設定數據後, 通知數據變更
	public void setNews( Set<TStock> obStocks,  Set<TStock> osStocks, Date date) {
		this.obStocks = obStocks;
		this.osStocks = osStocks;
		this.date = date;
		changed();
	}
	
}
