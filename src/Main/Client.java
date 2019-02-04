package networksA1;
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
    JTextArea txtS;

    public Client() {
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
        btnProcess = new JButton("Process");
        btnProcess.setBounds(200, 40, 90, 21);
        btnProcess.addActionListener(this);
        add(btnProcess);

        txtS = new JTextArea();
        txtS.setBounds(10, 85, 290, 120);
        add(txtS);

        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnProcess)) {
            try {
                processInformation();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    
    
    public void processInformation() throws UnknownHostException, IOException {

        String serverAddress = txtAddress.getText();
        int port = Integer.parseInt(txtPort.getText());
        Socket socket = new Socket(serverAddress, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));   
        ObjectOutputStream p = new ObjectOutputStream(socket.getOutputStream());
        
        
        p.writeObject("hi babe");
        p.flush();

        // Here we read the details from server
        BufferedReader response = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        txtS.setText("The server respond: " + response.readLine());
        p.close();
        response.close();
        socket.close();
 

    }

    public static void main(String[] args) throws IOException {
       new Client();
    	 
}}
