
//import javax.swing.JFrame;
public class fir_exec extends Thread{
    public fir_exec(ThreadGroup thGr, String name){
        super(thGr, name);
        int x = ++multithreading.contor;
        ++multithreading.active;
        System.out.printf("am pornit threadul cu numarul %d, in total: %d\t active:%d\n", x, multithreading.contor, multithreading.active);
    }
    public void run(){
        ShowColors2JFrame fereastra = new ShowColors2JFrame();
        //fereastra.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fereastra.setSize(1400, 700);
        fereastra.setVisible(true);
    }
    
}