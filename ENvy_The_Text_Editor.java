public class ENvy_The_Text_Editor {
    public static void main(String[] args) {
        fir_exec th = new fir_exec(multithreading.thG, "fir1");
        th.start();
        while(true) if(multithreading.active == 0) System.exit(0); //IT WORKS
    }
}