package Day5;

// Java program to demonstrate
// scheduleAtFixedRate method of Timer class

import java.util.Timer;
import java.util.TimerTask;
import java.util.*;


class Help extends TimerTask
{
    public static int i = 0;
    public void run()
    {
        ++i;
        if(i == 5 || i%5==0)
        {
            synchronized(Test.obj)
            {
                Test.obj.notify();
            }
        }
    }

}


public class Test
{
    protected static Test obj;
    public static void main(String[] args) throws InterruptedException
    {
       Test t=new Test();
       t.some();
    }
    public    void some() throws InterruptedException
    {
        obj = new Test();
        Timer timer = new Timer();
        TimerTask task = new Help();
        Date date = new Date();
        timer.scheduleAtFixedRate(task, date, 5000);
      //  System.out.println("Timer running");
        synchronized(obj)
        {
            obj.wait();
            timer.cancel();
            timer.purge();
        }
    }
}

