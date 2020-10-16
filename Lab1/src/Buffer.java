public class Buffer {
    private String innerMessage;
    public Buffer(){
        this.innerMessage = "";
    }

    synchronized public void put(String msg) throws InterruptedException {
        while (!innerMessage.equals("")){
            wait();
        }
        this.innerMessage = msg;
        notifyAll();
    }

    synchronized public String take() throws InterruptedException {
        while (innerMessage.equals("")){
            wait();
        }
        String toRet = this.innerMessage;
        this.innerMessage = "";
        notifyAll();
        return toRet;
    }
}
