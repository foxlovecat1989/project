package com.spring.project.portfolio.observer;


import com.spring.project.portfolio.entities.TStock;
import com.spring.project.portfolio.observer.subject.Subject;
import java.util.Date;
import java.util.Set;

public class OBDisplayment implements Observer, Displayment{
    
        private Set<TStock> obStocks;
        private Set<TStock> osStocks;
        private Date date;
        private Subject subject;

	// constructor 需要主題物件作為登記註冊用
	public OBDisplayment(Subject subject) {
		this.subject = subject;
		subject.registerOberserver(this);
	}
	@Override
	public void display() {
                System.out.println(date);
                System.out.print("OB: ");
		obStocks.stream()
                        .map(stock -> stock.getName())
                        .forEach(s -> System.out.print(s + " , "));
                System.out.println("");
	}
	
	// 將數據更新後, 顯示出來
        @Override
        public void update(Set<TStock> obStocks, Set<TStock> osStocks, Date date) {
            this.obStocks = obStocks;
            this.osStocks = osStocks;
            this.date = date;
            display();
        }

}
