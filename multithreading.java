public class multithreading {
    static ThreadGroup thG = new ThreadGroup("ventanas");
    static int contor=1, active=0; //contor remains 1 after first window, it starts increasing when 'new window' is pressed
    public static void notifiedToStopThread(String name) {/// %%%
		active--;
    	System.out.println("Inchidem threadul "+name);
    	Thread[] a = new Thread[1000];
    	int n = Thread.enumerate(a);
    	for(int i = 0; i < n; i++) {
    		if(a[i].getName().equals(name)) {
    			System.out.println("Am identificat thread-ul de inchis");
    			a[i].interrupt();
    			break;
    		}
    	}
    }/// %%%
}
