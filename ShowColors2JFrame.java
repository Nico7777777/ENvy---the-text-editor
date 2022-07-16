import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ScrollPaneLayout;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ShowColors2JFrame extends JFrame{
    //==================================================================upper====================================================================================================
    private JPanel upperPanel;
    private JCheckBox boldButton, italicButton;
    private Font font = new Font("Serif", Font.PLAIN, 14);
    private static final String[] color_names = {"Black", "Blue", "Cyan", "Dark Gray", "Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"};
    private static final Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
    private JComboBox<String> lista_culori;
    private JButton new_window;
    //==================================================================center================================================================================================
    private JScrollPane scrollablePane;
    private JTextArea textarea;
    private ScrollPaneLayout centrez;
    //===================================================================lower================================================================================================
    private JButton changeColorJButton, saveJButton, filePickerJButton;
    private JPanel lowerPanel;
    private JFileChooser fileChooser;
    //===================================================================generic=================================================================================================
    private Color color = Color.LIGHT_GRAY;
    

    public ShowColors2JFrame(){
//precalculated operations
        super("ENvy: The Text Editor");
        //=================================================================generic operations=================================================================================
        CheckBoxHandler handler = new CheckBoxHandler();
        lista_culori = new JComboBox<String>(color_names);
        lista_culori.setMaximumRowCount(5);
        
        //==================================================================upper operations=================================================================================
        upperPanel = new JPanel();
        //bold button stuff
        boldButton = new JCheckBox("bold", false);
        boldButton.addItemListener(handler);
        upperPanel.add(boldButton);
        //italic button stuff
        italicButton = new JCheckBox("italic", false);
        italicButton.addItemListener(handler);
        upperPanel.add(italicButton);
        //new window stuff
        new_window = new JButton("new window");
        upperPanel.add(new_window);
        //other stuff
        upperPanel.add(lista_culori);
        upperPanel.setBackground(Color.BLACK);
        add(upperPanel, BorderLayout.NORTH);//we add the upper panel itself effectively to the JFrame
        //===================================================================center operations============================================================================
        textarea = new JTextArea();
        scrollablePane = new JScrollPane(textarea);
        centrez = new ScrollPaneLayout();
        centrez.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centrez.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollablePane.setLayout(centrez);
        add(scrollablePane, BorderLayout.CENTER);
        setSize(300, 120);
        setVisible(true);
        //====================================================================lower operations===============================================================================
        lowerPanel = new JPanel();
        changeColorJButton = new JButton("bkg color");
        lowerPanel.add(changeColorJButton);
        saveJButton = new JButton("save");
        lowerPanel.add(saveJButton);
        filePickerJButton = new JButton("select file");
        lowerPanel.add(filePickerJButton);
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
        //lowerPanel.setLayout( new FlowLayout() );
        add(lowerPanel, BorderLayout.SOUTH);
        
//anonymous listeners---------------------------------------------------------------------------------------------------------------------------------------------------------------
        new_window.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    try{
                        fir_exec fir = new fir_exec(multithreading.thG, "fir" + Integer.toString(multithreading.contor));
                        Thread new_th = new Thread(fir);
                        new_th.start();
                    }catch(Exception exc){
                        System.err.println(exc.getMessage());
                    }
                }
            }
        );

        changeColorJButton.addActionListener(// $$$
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    try{
                        color = JColorChooser.showDialog(ShowColors2JFrame.this, "Pick a color", color);
                        if( color == null ) color = Color.LIGHT_GRAY;
                        textarea.setBackground( color );
                    }catch(Exception exc){
                        System.err.println(exc.getMessage());
                    }
                }
            }
        );// $$$
        lista_culori.addItemListener(// +++
            new ItemListener()
            {
                public void itemStateChanged(ItemEvent e)
                {
                    try{
                        textarea.setForeground( colors[ lista_culori.getSelectedIndex() ] );
                    }catch(Exception exc){
                        System.err.println(exc.getMessage());
                    }
                }
            }
        );//+++
        saveJButton.addActionListener(// |||
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    File f = getFile();
                    try{
                        PrintWriter scriitor = new PrintWriter(f);
                        scriitor.write(textarea.getText());
                        scriitor.close();
                    }catch(Exception exc){
                        System.err.println( exc.getMessage() );
                    }
                }
                //JFrame.EXIT_ON_CLOSE;
            }
        );// |||
        filePickerJButton.addActionListener(
            new ActionListener()
            {
                synchronized public void actionPerformed(ActionEvent e)
                {
                    File f = getFile();
                    try{
                        BufferedReader cititor = new BufferedReader( new FileReader(f) );
                        textarea.setText("");
                        String line = cititor.readLine();
                        while( line != null){
                            textarea.append(line);
                            line = cititor.readLine();
                        }
                        cititor.close();
                    }catch(Exception exc){
                        System.err.println( exc.getMessage() );
                    }
                }
            }
        );
        JFrame.setDefaultLookAndFeelDecorated(true);
    }
//explicit listeners-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private class CheckBoxHandler implements ItemListener{//listener for the checkable font option buttons(italic and bold)
        public void itemStateChanged(ItemEvent e)
        {
            try{
            if(boldButton.isSelected() && italicButton.isSelected()) font = new Font(font.getFamily(), Font.BOLD + Font.ITALIC, font.getSize());
            else if(boldButton.isSelected()) font = new Font(font.getFamily(), Font.BOLD, font.getSize());
            else if(italicButton.isSelected()) font = new Font(font.getFamily(), Font.ITALIC, font.getSize());
            else font = new Font(font.getFamily(), Font.PLAIN, font.getSize());

            textarea.setFont(font);
            }catch(Exception exc){
                System.err.println(exc.getMessage());
            }
        }
    }

//methods--------------------------------------------------------------------------------------------------------------------------------------------
    protected void CloseAction(){
        System.out.println("am oprit un thread!");
        multithreading.active--;
    }
    //the method for the file chooser of "save" which returns the file within the textarea's content is saved
    private File getFile(){ // ~~~
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
        int result = fileChooser.showOpenDialog(this);
        if(result == JFileChooser.CANCEL_OPTION) System.exit(1);
        File fileName = fileChooser.getSelectedFile();
        if( fileName == null || fileName.getName().equals("") )
        {
            JOptionPane.showMessageDialog(this, "Invalid Name", "Invalid Name", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return fileName;
    }// ~~~
}
