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



public class Client extends JFrame implements ActionListener {
    JLabel lblAddress;
    JLabel lblPort;
    JTextField txtAddress;
    JTextField txtPort;
    JButton btnProcess;
    JToggleButton connectDisconnect;
    JTextArea txtS;
    Socket socket;

    public Client() {
        socket = null;
        this.setTitle("Simple Sample");
        this.setSize(320, 240);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        

        lblAddress = new JLabel("IP Address: ");
        lblAddress.setBounds(10, 10, 90, 21);
        add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(105, 10, 90, 21);
        add(txtAddress);

        lblPort = new JLabel("Port: ");
        lblPort.setBounds(10, 35, 90, 21);
        add(lblPort);

        txtPort = new JTextField();
        txtPort.setBounds(105, 35, 90, 21);
        add(txtPort);
    //     btnProcess = new JButton("Process");
    //     btnProcess.setBounds(200, 40, 90, 21);
    //    btnProcess.addActionListener(this);
    //    add(btnProcess);

        connectDisconnect = new JToggleButton("Connect");
        connectDisconnect.setBounds(200, 40, 90, 21);
        connectDisconnect.addActionListener(this);
        add(connectDisconnect);

        txtS = new JTextArea();
        txtS.setBounds(10, 85, 290, 120);
        add(txtS);

        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // if (e.getSource().equals(btnProcess)) {
        //     try {
        //         processInformation();
        //     } catch (UnknownHostException e1) {
        //         e1.printStackTrace();
        //     } catch (IOException e1) {
        //         e1.printStackTrace();
        //     }
        // }
        if (e.getSource().equals(connectDisconnect)) {
            if (connectDisconnect.isSelected()){
                try{
                    processInformation();
                    connectDisconnect.setText("Disconnect");
                    txtS.setText("Connected to server: Enter your request here.");
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
      //  ObjectOutputStream p = new ObjectOutputStream(socket.getOutputStream());
        
        
       // p.writeObject("hi babe");
       // p.flush();

        // Here we read the details from server
        String response=in.readLine();
        txtS.setText("The server respond: " + response);
        } catch(IOException e){
                txtS.setText("Server shutdown.");
                socket.close();
        }
      
        // p.close();
        // response.close();
        // socket.close();
 
    }

    public static void main(String[] args) throws IOException {
       new Client();
    	 
}}
