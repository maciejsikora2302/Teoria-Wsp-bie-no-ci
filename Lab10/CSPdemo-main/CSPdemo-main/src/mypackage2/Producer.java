package mypackage2;
import org.jcsp.lang.*;

/** Producer class: produces one random integer and sends on
  * output channel, then terminates.
  */
public class Producer implements CSProcess
  { private One2OneChannelInt channel;

    public Producer (final One2OneChannelInt out)
      { channel = out;
      } // constructor

    public void run ()
      {
        int item = (int)(Math.random()*100)+1;
        System.out.println("I am sending "+ item);
        channel.out().write(item);

      } // run

  } // class Producer
