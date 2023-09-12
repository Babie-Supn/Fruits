import org.junit.Test;

import java.io.InputStream;
import java.lang.annotation.Target;

public class TestIn {
   @Test
    public void testIn(){

       InputStream is = this.getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
       System.out.println(is);
       String abc="22.00";
       Object c=null;
      c = Double.parseDouble(abc);
       System.out.println(c);
   }

}
