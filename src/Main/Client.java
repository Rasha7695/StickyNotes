package Main;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JOptionPane;




public class Client extends JFrame implements ActionListener {
    JLabel lblAddress;
    JLabel lblPort;
    JTextField txtAddress;
    JTextField txtPort;
    JButton send;
    JToggleButton connectDisconnect;
    JTextArea txtS;
    Socket socket;
    JButton getButton;
    JButton pinButton;
    JButton unpinButton;


    public Client() {
        socket = null;
        this.setTitle("Simple Sample");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        

        lblAddress = new JLabel("IP Address: ");
        lblAddress.setBounds(10, 10, 90, 21);
        add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(105, 10, 300, 21);
        add(txtAddress);

        lblPort = new JLabel("Port: ");
        lblPort.setBounds(10, 35, 90, 21);
        add(lblPort);

        txtPort = new JTextField();
        txtPort.setBounds(105, 35, 300, 20);
        add(txtPort);

        send = new JButton("Post");
        send.setBounds(450, 10, 90, 20);
        send.addActionListener(this);
        add(send);

        connectDisconnect = new JToggleButton("Connect");
        connectDisconnect.setBounds(450, 40, 90, 21);
        connectDisconnect.addActionListener(this);
        add(connectDisconnect);

        getButton = new JButton("GET");
        getButton.setBounds(450, 70, 90, 21);
        getButton.addActionListener(this);
        add(getButton);

        pinButton = new JButton("PIN");
        pinButton.setBounds(450, 100, 90, 21);
        pinButton.addActionListener(this);
        add(pinButton);

        unpinButton = new JButton("UNPIN");
        unpinButton.setBounds(450, 130, 90, 21);
        unpinButton.addActionListener(this);
        add(unpinButton);

        txtS = new JTextArea();
        txtS.setBounds(10, 85, 400, 200);
        add(txtS);

        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(getButton)) {
    		getButton.addActionListener(new ActionListener () {
        		public void actionPerformed(ActionEvent arg0) {
        			 String selection = JOptionPane.showInputDialog(getButton, "Enter GET request:"); 
                     try{
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println(selection);
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));   
                        String response=in.readLine();
                        String[] responses = response.split("-");
                        txtS.setText("");
                        if(responses.length>1){
                        for(int i=1;i<responses.length;i++){
                            txtS.append(i+")"+responses[i]+"\n");
                        }
                    }
                    else{
                        txtS.setText(response);
                    }
                        }catch(IOException e1){
                            txtS.setText("Invalid request");
                        }    
                    
                    }
        	});
    		
        }
        if (e.getSource().equals(pinButton)) {
    		pinButton.addActionListener(new ActionListener () {
        		public void actionPerformed(ActionEvent arg0) {
        			 String selection = JOptionPane.showInputDialog(pinButton, "Enter PIN request:"); 
                     try{
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println(selection);
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));   
                        String response=in.readLine();
                        String[] responses = response.split("-");
                        txtS.setText("");
                        if(responses.length>1){
                        for(int i=1;i<responses.length;i++){
                            txtS.append(i+")"+responses[i]+"\n");
                        }
                    }
                    else{
                        txtS.setText(response);
                    }
                        }catch(IOException e1){
                            txtS.setText("Invalid request");
                        }    
                    
                    }
        	});
    		
        }
        if (e.getSource().equals(unpinButton)) {
    		unpinButton.addActionListener(new ActionListener () {
        		public void actionPerformed(ActionEvent arg0) {
        			 String selection = JOptionPane.showInputDialog(unpinButton, "Enter UNPIN request:"); 
                     try{
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println(selection);
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));   
                        String response=in.readLine();
                        String[] responses = response.split("-");
                        txtS.setText("");
                        if(responses.length>1){
                        for(int i=1;i<responses.length;i++){
                            txtS.append(i+")"+responses[i]+"\n");
                        }
                    }
                    else{
                        txtS.setText(response);
                    }
                        }catch(IOException e1){
                            txtS.setText("Invalid request");
                        }    
                    
                    }
        	});
    		
    	}
        if(e.getSource().equals(send)){
            try{
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(txtS.getText());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));   
            String response=in.readLine();
            String[] responses = response.split("-");
            txtS.setText("");
            if(responses.length>1){
            for(int i=1;i<responses.length;i++){
                txtS.append(i+")"+responses[i]+"\n");
            }
        }
        else{
            txtS.setText(response);
        }
            }catch(IOException e1){
                txtS.setText("Invalid request");
            }
        }
        if (e.getSource().equals(connectDisconnect)) {
            if (connectDisconnect.isSelected()){
                try{
                    processInformation();
                    connectDisconnect.setText("Disconnect");
                } catch(IOException e1){
                    txtS.setText("Invalid IP or port");
                }
            }  
            else  {
                try{
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("DISCONNECT");
                    socket.close();
                    connectDisconnect.setText("Connect");  
                    txtS.setText("Disconnected from server");
                }catch(IOException e2){
                    txtS.setText("disconnection failed.");
                }
                
            }
            
        }
    }


    public void processInformation() throws UnknownHostException, IOException {

        String serverAddress = txtAddress.getText();
        int port = Integer.parseInt(txtPort.getText());
        try{
            socket = new Socket(serverAddress, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));   
        // Here we read the details from server
        String response1 = in.readLine();
        String response2 = in.readLine();
        txtS.setText("Connected to the server.\n");
        txtS.append(response1+"\n");
        txtS.append(response2+"\n");
        txtS.append("Delete all the above, type your request then hit send.");
        } catch(IOException e){
                txtS.setText("Server shutdown.");
                socket.close();
        }
    }

    public static void main(String[] args) throws IOException {
       new Client();
    	 
}}
