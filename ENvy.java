import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ItemEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font; /// abstract windows toolkit
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.ScrollPaneLayout;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;/// the modern pack 'swing'
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;/// mysql database
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.File;/// input-output

public class ENvy extends JFrame /*implements ActionListener*/{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//==================================================================upper====================================================================================================
    private JPanel upperPanel;
    private JCheckBox boldButton, italicButton;
    private Font font = new Font("Serif", Font.PLAIN, 14);
    private static final String[] color_names = {"Black", "Blue", "Cyan", "Dark Gray", "Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"};
    private static final Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
    private JComboBox<String> lista_culori;
    private JButton new_window;
    //==================================================================center==================================================================================================
    private JScrollPane scrollablePane;
    private JTextArea textarea;
    private ScrollPaneLayout centrez;
    //===================================================================lower==================================================================================================
    private JButton changeColorJButton, saveJButton, filePickerJButton;
    private JPanel lowerPanel;
    private JFileChooser fileChooser;
    //===================================================================generic================================================================================================
    private Color color = Color.LIGHT_GRAY;
    //==================================================================database================================================================================================
    private static String db_name="root", db_password="nick7777777", db_connection="jdbc:mysql://127.0.0.1:3306/envy_database", db_table="envy_table";
    
    public ENvy(){
//precalculated operations
        super("ENvy: The Text Editor");
        //=================================================================generic operations=================================================================================
        CheckBoxHandler handler = new CheckBoxHandler();
        lista_culori = new JComboBox<String>(color_names);
        lista_culori.setMaximumRowCount(5);
        try {
        	UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch(UnsupportedLookAndFeelException e) {
			e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
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
        new_window.addActionListener(/// ~~~
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    try{
                        fir_exec fir = new fir_exec(multithreading.thG, "fir" + Integer.toString(multithreading.contor));
                        Thread new_th = new Thread(fir);
                        new_th.start();
                    }catch(Exception exc){
                    	JDialog show = new JDialog();
                    	JLabel lab = new JLabel("Error!");
                    	show.add(lab);
                    	show.setSize(200, 100);
                    	show.setVisible(true);
                    }
                }
        });/// ~~~

        changeColorJButton.addActionListener(// $$$
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    try{
                        color = JColorChooser.showDialog(ENvy.this, "Pick a color", color);
                        if( color == null ) color = Color.LIGHT_GRAY;
                        textarea.setBackground( color );
                        //String opt = "backgroundColour";
                        //databaseQuery(opt, color);
                    }catch(Exception exc){
                        System.err.println(exc.getMessage());
                    }
                }
        });// $$$
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
        });//+++
        saveJButton.addActionListener(// |||
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                	JFileChooser fileChooser = new JFileChooser();
                	int option = fileChooser.showSaveDialog(getParent());
                	try {
                		File f = null;
	                	if(option == JFileChooser.APPROVE_OPTION) f = fileChooser.getSelectedFile();
	                    PrintWriter scriitor = new PrintWriter(f);
	                    scriitor.write(textarea.getText());
	                    scriitor.close();
                	}catch(FileNotFoundException exc){
                        System.err.println( exc.getMessage() );
                        ENvy.errorMessage("The file could not be found!");
                    }catch(Exception exc) {
                        System.err.println( exc.getMessage() );
                        ENvy.errorMessage("The type of file could not be opened!");
                    }
                }
                //JFrame.EXIT_ON_CLOSE;
        });// |||
        filePickerJButton.addActionListener(/// @@@
            new ActionListener()
            {
                public synchronized void actionPerformed(ActionEvent e)
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
                        ENvy.errorMessage("Eroare de citire!");
                    }
                }
        });/// @@@
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

	            
            	if(boldButton.isSelected()) {
                	databaseQuery("bold", true);
                	System.out.println("db");
            	}
            	if(italicButton.isSelected()) {
            		databaseQuery("italic", true);
            		System.out.println("db");
            	}
	            textarea.setFont(font);
            }catch(Exception exc){
                System.err.println(exc.getMessage());
            }
        }
    }

//methods--------------------------------------------------------------------------------------------------------------------------------------------
    //the method for the file chooser of "save" which returns the file within the textarea's content is saved
    private synchronized File getFile(){ // ~~~
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
    
    private static void errorMessage(String error_message){
    	JDialog dial = new JDialog();
    	JLabel lab = new JLabel(error_message);
    	dial.add(lab);
    	dial.setSize(300, 150);
    	dial.setVisible(true);
    }
    private void databaseQuery(String optiune, boolean val) {
    	try {
    		Connection con = DriverManager.getConnection(db_connection, db_name, db_password);//Establishing connection
    		String query = "INSERT INTO " + db_table+"(optiune) VALUES(\""+optiune+"\")";
    		PreparedStatement st = con.prepareStatement(query);
    		st.executeUpdate();
    		con.close();
    		
    	}catch(SQLException e) {
    		System.out.println(e.getMessage());
    	}
    }
}
