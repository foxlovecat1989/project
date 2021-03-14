package mail;
  
import com.spring.project.portfolio.mail.Emails;
import com.spring.project.portfolio.mail.SendEmail;
import org.junit.Before;
import org.junit.Test;

public class testSendEmail {
    Emails emails;
    String sender = "台灣證卷模擬交易所"; // sender
    String to = "foxlovecat4work@gmail.com,foxlovecat1989@gmail.com"; // to
    String title = "台灣證卷模擬交易所:Verification mail";  // title of email
    // Content
    String html = "<b>Welcome ~ !!! 台灣證卷模擬交易所</b></br>" +
                    "This is a verification mail, <b>please check!!!</b>";
    
    @Before
    public void beforeTest(){
       emails = new Emails(sender, to, title, html);
    }
    
    @Test
    public void test() throws Exception{
        SendEmail sendEmail = new SendEmail();
        sendEmail.submit(emails);
    }
}
