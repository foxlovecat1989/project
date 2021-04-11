package observer;


import com.spring.project.portfolio.entities.TStock;
import com.spring.project.portfolio.observer.Displayment;
import com.spring.project.portfolio.observer.OBDisplayment;
import com.spring.project.portfolio.observer.OSDisplayment;
import com.spring.project.portfolio.observer.subject.OBOSNews;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;

public class testObserver {
    
    @Test
    public void t1(){
        
        OBOSNews oBOSNews = new OBOSNews();
	Set<TStock> osStocks = new LinkedHashSet<>();
        Set<TStock> obStocks = new LinkedHashSet<>();
        
        TStock stockA = new TStock("stockA");
        TStock stockB = new TStock("stockB");
        TStock stockC = new TStock("stockC");
        TStock stockD = new TStock("stockD");
         
	Displayment oBDisplayment = new OBDisplayment(oBOSNews);
        Displayment oSDisplayment = new OSDisplayment(oBOSNews);
        
        osStocks.add(stockA);
        osStocks.add(stockB);
        obStocks.add(stockC);
        obStocks.add(stockD);
        
        oBOSNews.setNews(obStocks, osStocks, new Date());
        System.out.println("----------------------");
        osStocks.remove(stockA);
        obStocks.remove(stockD);
        oBOSNews.setNews(obStocks, osStocks, new Date());
        System.out.println("----------------------");
        osStocks.add(stockD);
        obStocks.add(stockA);
        oBOSNews.setNews(obStocks, osStocks, new Date());
        System.out.println("----------------------");
    }
}
