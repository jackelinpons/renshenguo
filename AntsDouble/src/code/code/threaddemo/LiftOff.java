package code.code.threaddemo;

/**
 * @Auther: ants_double
 * @Date: 2018-12-24 20:54
 * @Description:
 */
public class LiftOff implements Runnable {
    protected  int countDown=10;
    private static  int taskCount=0;
    private final  int id=taskCount++;

    public LiftOff(){}
    public LiftOff(int countDown) {
        this.countDown = countDown;
    }
    public String status(){
        return "#"+id+"("+(countDown>0?countDown:"LiftOff!")+"),";
    }

    @Override
    public void run() {
        while (countDown-->0){
            System.out.println(status());
            Thread.yield();
        }
    }
}
