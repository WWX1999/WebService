package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

public class clientFrame {

    public static void main(String[] args) throws MalformedURLException {

        Frame();

    }

    public static void Frame(){
        JFrame load=new JFrame("Start");
        load.setSize(300,100);
        load.setLayout(new FlowLayout());
        load.setLocation(500,500);
        JButton SendMsg=new JButton("SendEmail");
        JButton TestMail=new JButton("TestMail");
        SendMsg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendFrame();
            }
        });
        TestMail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testFrame();
            }
        });
        load.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        load.add(SendMsg);
        load.add(TestMail);
        load.setVisible(true);
    }

    public static void sendFrame(){
        JFrame sendframe=new JFrame("Send");
        //sendframe.setLayout(new FlowLayout());
        sendframe.setSize(500,300);
        sendframe.setLocation(500,500);
        JPanel jp=new JPanel();
        jp.setLayout(new FlowLayout());
        JLabel s=new JLabel("Email:");
        //JLabel task_lable=new JLabel("Subject:");
        final JTextField InEmail=new JTextField(30);
        //final JTextField In_task=new JTextField(20);
        JButton toSend=new JButton("Send");
        final JTextArea MessageArea=new JTextArea();

        toSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String urls = InEmail.getText();
                String payload = MessageArea.getText();

                if (Soap.sendSoap(urls, payload, "send")) {
                    JOptionPane.showMessageDialog(new JFrame().getContentPane(),
                            "Send successfully!", "Status", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(new JFrame().getContentPane(),
                            "Fail to send!", "Status", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        jp.add(s);
        jp.add(InEmail);
        jp.add(toSend);
        //jp.add(subject);
        //jp.add(InSubject);
        sendframe.add(jp,BorderLayout.NORTH);
        sendframe.add(MessageArea,BorderLayout.CENTER);
        sendframe.setVisible(true);
    }

    public static void testFrame(){
        JFrame testframe=new JFrame("Test email");
        testframe.setSize(500,80);
        testframe.setLocation(500,500);
        JLabel email_label=new JLabel("email:");
        final JTextField in_email=new JTextField(30);
        JButton to_test=new JButton("Test");
        to_test.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email=in_email.getText();
                String payload="";
                if (Soap.sendSoap(email, payload, "test")) {
                    JOptionPane.showMessageDialog(new JFrame().getContentPane(),
                            "Valid mailbox!", "Status", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(new JFrame().getContentPane(),
                            "Invalid maibox!", "Status", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        testframe.add(email_label,BorderLayout.WEST);
        testframe.add(in_email,BorderLayout.CENTER);
        testframe.add(to_test,BorderLayout.EAST);
        testframe.setVisible(true);
    }
}
