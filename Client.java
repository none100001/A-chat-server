import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class Client implements ActionListener{
	JFrame frame;
	JPanel panel,panel2;
	JLabel mainlabel;
	JTextArea textArea;
	
	JLabel label1;
	JLabel label2;
	JTextField  field1;
	JTextField  field2;
	JTextField field;
	
	String IP = "null";
	int Port = 0;
	String line = "";
	
	
	
	
	public static void main(String [] args){
		Client a = new Client();
		a.dod();
	}
	
	public void dod(){
    	
    	frame = new JFrame();
    	panel = new JPanel();
    	panel2 = new JPanel();
    	mainlabel = new JLabel();
    	textArea = new JTextArea(40,49);
    	textArea.setLineWrap(true);
    	textArea.selectAll();
    	textArea.requestFocus();
    	JScrollPane scroller = new JScrollPane(textArea);
    	scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	panel2.add(scroller);
    	textArea.setBackground(Color.BLACK);
    	textArea.setForeground(Color.GREEN);
    	mainlabel = new JLabel("                      Welcome to CSE321 Echo Client!");
    	label1 = new JLabel("Please enter the server name");
    	label2 = new JLabel("Please enter the server port");
    	field1 = new JTextField(20);
    	field2 = new JTextField(20);
    	field1.addActionListener(new IP());
    	field2.addActionListener(new Port());
    	field1.selectAll();
    	field1.selectAll();
    	field1.requestFocus();
    	field2.requestFocus();
    	
    	JPanel extrapanel1 = new JPanel();
    	JPanel extrapanel2 = new JPanel();
    	extrapanel1.setBackground(Color.RED);
    	extrapanel2.setBackground(Color.RED);
    	panel.add(mainlabel);
    	panel.setBackground(Color.RED);
    	extrapanel1.add(label1);
    	extrapanel1.add(field1);
    	extrapanel2.add(label2);
    	extrapanel2.add(field2);
    	JLabel label = new JLabel("Please Enter Text Here");
    	field = new JTextField("");
    	field.selectAll();
    	field.requestFocus();
    	Border border = BorderFactory.createLineBorder(Color.BLUE,9);
    	field.setBorder(border);
    	field.addActionListener(this);
    	textArea.setForeground(Color.GREEN);
    	field.setBackground(Color.GREEN);   	
    	frame.getContentPane().add(BorderLayout.NORTH,panel);
    	frame.getContentPane().add(BorderLayout.WEST,extrapanel1);
    	frame.getContentPane().add(BorderLayout.EAST,extrapanel2);
    	frame.getContentPane().add(BorderLayout.CENTER,panel2);
    	frame.getContentPane().add(BorderLayout.SOUTH,label); 
    	frame.getContentPane().add(BorderLayout.SOUTH,field);
        frame.setSize(300,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        while(IP.equals("null")){
        	while(Port==0){
        		
        	}
        }
        
        Work();
        
	}
	public void Work(){
    	
    	
        
        /*String hostname = "27.147.223.121";
        int port = 8181;*/
        
        
        Socket clientSocket = null;  
        PrintWriter os = null;
        BufferedReader is = null;
        

        
        try {
            clientSocket = new Socket(IP, Port);
            os = new PrintWriter(clientSocket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            
        } catch (IOException e) {
            
        }
       
        
        if (clientSocket == null || os == null || is == null) {
            return;
        }
        
        try {
            while ( true ) {
            	
            	
            	while((line.equals(""))){
            		
            	}
            	os.println(line);
            	os.flush();
            	if(line.equals("end-of-session")){
            		break;
            	}
            	else{
            		String replay = is.readLine();
	            	Write(replay);
	            	line = "";
            	}
            }          
            os.close();
            is.close();
            clientSocket.close();   
        } catch (UnknownHostException e) {
            
        } catch (IOException e) {
            
        }
	}
	
	public synchronized void Write(String line){
		textArea.append("Server response: "+line+" \n");
		//line = "";
	}
	
    
    class IP implements ActionListener{
    	public void actionPerformed(ActionEvent event){
    		IP = field1.getText();
    		
    	}
    }
    class Port implements ActionListener{
    	public void actionPerformed(ActionEvent event){
    		Port = Integer.valueOf(field2.getText());
    	}
    }
	@Override
	public void actionPerformed(ActionEvent event) {
		line = field.getText();
		field.setText("");		
	}
}
