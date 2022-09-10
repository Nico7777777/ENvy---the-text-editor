import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class fir_exec extends Thread{
    public fir_exec(ThreadGroup thGr, String name){
        super(thGr, name);
        this.name = name;
        int x = ++multithreading.contor;
        ++multithreading.active;
        System.out.printf("am pornit threadul cu numarul %d, in total: %d\t active:%d\n", x, multithreading.contor, multithreading.active);
    }
    public String name;
    public void run(){
    	ENvy window = new ENvy();
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(1400, 700);
        window.setVisible(true);
        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
            	/// swing elements
				JDialog dial = new JDialog();
				JPanel pan = new JPanel();
				JButton save = new JButton("save"), closewo = new JButton("close without saving"), cancel = new JButton("cancel");
				/// buttons' lsiteners
				save.addActionListener(/// save
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//window.
							window.dispose();
							dial.dispose();
							multithreading.notifiedToStopThread(name);
						}
				});/// save
				closewo.addActionListener(/// closewo
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							window.dispose();
							dial.dispose();
							multithreading.notifiedToStopThread(name);
						}
				});/// closewo
				cancel.addActionListener(/// cancel
					new ActionListener() {
						public void actionPerformed(ActionEvent e) { dial.dispose(); }
				});/// cancel
				
				pan.add(save);
				pan.add(closewo);
				pan.add(cancel);
				
				dial.add(pan);
				dial.setSize(200, 100);
				dial.setVisible(true);
			}
        });
    }
}