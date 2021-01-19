package mypackage2;
import org.jcsp.lang.*;

/** Consumer class: reads one int from input channel, displays it, then
  * terminates.
  */
public class Consumer implements CSProcess
  { private One2OneChannelInt channel;

    public Consumer (final One2OneChannelInt in)
      { channel = in;
      } // constructor

    public void run ()
      {  int item = channel.in().read();
         System.out.println("I got "+ item);
      } // run

  } // class Consumer
