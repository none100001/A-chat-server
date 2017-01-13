import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class Server implements ActionListener{
	JTextArea textArea;
	
	JFrame frame  = new JFrame();
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	
	JButton button1,button2,button3;
	int key = 0;
	
	ArrayList<Socket> list = new ArrayList<Socket>();
	HashMap<BufferedReader,PrintStream> map = new HashMap<BufferedReader,PrintStream>();
	HashMap<BufferedReader,String> map2 = new HashMap<BufferedReader,String>();
	int lock = 0;
	public static void main(String args []) {
		int port = 8181;
		Server server = new Server( port );
		server.startServer();
    }
    
    ServerSocket echoServer = null;
    Socket clientSocket = null;
    int port;
	
    public Server(int port) {
    	this.port = port;
    }

    public void stopServer() {
		System.out.println("Server is disconnected");
    }
    

    public void startServer() {
    	
    	JTextField field = new JTextField("             Select one of the following options:");
    	button1 = new JButton("                         Iterative Server");
    	button2 = new JButton("                         Concurrent Server");
    	button3 = new JButton("                         Exit");
    	button1.addActionListener(this);
    	button2.addActionListener(this);
    	button3.addActionListener(this);
    	JPanel panelButton = new JPanel();
    	panelButton.add(BorderLayout.NORTH,button1);
    	panelButton.add(BorderLayout.CENTER,button2);
    	panelButton.add(BorderLayout.SOUTH,button3);
    	panel1.add(BorderLayout.NORTH,field);
    	panel1.add(BorderLayout.SOUTH,panelButton);
    	panel2.setBackground(Color.RED);
    	panel1.setBackground(Color.BLUE);
    	
    	
    	textArea = new JTextArea(70,80);
    	textArea.selectAll();
    	textArea.requestFocus();
    	JScrollPane scroller = new JScrollPane(textArea);
    	scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	//scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	panel2.add(scroller);
    	textArea.setBackground(Color.BLACK);
    	textArea.setForeground(Color.GREEN); // set text Color in JTextArea
    	
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.getContentPane().add(BorderLayout.CENTER,panel2);
    	frame.getContentPane().add(BorderLayout.NORTH,panel1);
    	frame.setSize(350,300);
    	frame.setVisible(true);
    	
	// Try to open a server socket on the given port
	// Note that we can't choose a port less than 1024 if we are not
	// privileged users (root)
	
        try {
        	echoServer = new ServerSocket(port);
        }
        catch (IOException e) {
        	System.out.println(e);
        }
        if(key==0){
        	while(true){
        		if(key==1){
        			keyOne();
        			break;
        		}
        		else if(key==2){
        			keyTwo();
        			break;
        		}
        	}
        }
        //keyOne();
        
    }
    
    
    public void keyOne(){
    	textArea.append("                                                                                                             Welcome to CSE321 Iterative Echo Server! \n");
    	textArea.append("                                                                                                             Echo Server is running on port 8181 \n");
    	Do();
    }
    public void keyTwo(){
    	textArea.append("                                                                                                             Welcome to CSE321 Concurrent Echo Server! \n");
		textArea.append("                                                                                                             Echo Server is running on port 8181 \n");
		Do();
    }
    
    public void Do(){
    	while (true) {
		    try {
				clientSocket = echoServer.accept();
				if(key==1){
					lock++;
				}
				Server2Connection oneconnection = new Server2Connection(clientSocket,key);
				if(key==1){
					if(lock<=1){
						for(int c = 0;c<list.size();c++){
							textArea.append("Client Number : IP-Address  :"+c+" "+list.get(c).getInetAddress().getHostAddress()+" \n");
				    	}
					}
				}
				else{
					for(int c = 0;c<list.size();c++){
						textArea.append("Client Number : IP-Address   :"+c+" "+list.get(c).getInetAddress().getHostAddress()+" \n");
			    	}
				}
				new Thread(oneconnection).start();
		    }   
		    catch (IOException e) {
		    	System.out.println(e);
		    }
		}
    } 
    
    public void actionPerformed(ActionEvent event){
    	if(event.getSource()== button1){
    		key = 1;
    	}
    	else if(event.getSource()== button2){
    		key = 2;
    	}
    	else{
    		stopServer();
    	}
    }
    
    class Server2Connection implements Runnable {
        BufferedReader is;
        PrintStream os;
        int key;

        public Server2Connection(Socket clientSocket,int key) {
        	this.key = key;
        	try {
				is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				os = new PrintStream(clientSocket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	if(lock<=1){
	    		list.add(clientSocket);
	    		textArea.append("Starting session for "+"Client"+list.size()+clientSocket.getInetAddress().getHostAddress()+" \n");
	    		map2.put(is,("Client"+list.size()));
	    	}
	    	map.put(is,os);
        }

        public void run() {
            String line= "";
            while (map.containsKey(is)) {  // ekhanea map2.containsKey dilam na coz iterate server holea amar kaj ekbar takea janao server busy
               try {
					line = is.readLine();
			   } 
               catch (IOException e) {
					e.printStackTrace();
			   }
               if(lock<=1){
            	   receivedWrite(is,line);
               }
               else{
            	   FortheFirstTime(is);
               }
             
            }
        }
        public synchronized void FortheFirstTime(BufferedReader is){
        	PrintStream o = map.get(is);
        	o.println("Sorry Server is Busy");
        	o.flush();
        	map.remove(is);
        	map2.remove(is);
        }
        
        
        
        public synchronized void receivedWrite(BufferedReader is,String line){
        	if(line.equals("end-of-session")){
        		textArea.append("Ending session for client "+map2.get(is)+" \n");
        		map.remove(is);
        		map2.remove(is);
        	}
        	else{
        		textArea.append("Received from "+map2.get(is)+": "+line+" \n");
	        	PrintStream o = map.get(is);
	            o.println(line);
	            o.flush();
        	}
        }
        
        
    }
}





