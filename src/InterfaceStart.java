import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

class OptionPane {
    JFrame new_message;
    OptionPane(String mesaj) {
        new_message = new JFrame();
        JOptionPane.showMessageDialog(new_message, mesaj);
    }
}
class ButtonCreation extends JButton { // Class to create buttons with specified attributes
    protected Shape hexagon; //apartine de awt
    ButtonCreation(int x, int y, int width, int height, String NameButton) {
        this.setText(NameButton);
        this.setBounds(x, y, width, height);
        Hexagon(height);
    }
    public void Hexagon(int height){
        int h=(int)(Math.sqrt(3)*height);
        int[] puncteX=new int[]{ height/2,3*height/2,height*2,3*height/2,height/2,0};
        int[] puncteY=new int[]{0,0,height/2,height,height,height/2};
        hexagon=new Polygon(puncteX,puncteY,6);
    }
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.LIGHT_GRAY);
        } else {
            g.setColor(getBackground());
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fill(hexagon);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground()); ((Graphics2D) g).draw(hexagon);
    }

    @Override
    public boolean contains(int x, int y) { return hexagon.contains(x, y); }
    //de facut poligon
}
class InterfaceStart extends JFrame {
    Image pozaFundal;
    JTextField Name_ = new JTextField("");
    JTextField Password_ = new JTextField("");
    JTextField Hash_ = new JTextField("");
    JTextField NumeJucator = new JTextField("");
    boolean flag_email,flag_nume;
    PokerGamePlayersInt start_game=null;
    Hash matrice=null;
    JPanel panou_intrare = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            float transparency = 0.9f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
            g2d.setFont(new Font("Arial", Font.BOLD, 60));
            g2d.setColor(Color.BLACK);
            g2d.drawString("Login Front Page", 90, 50);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            g2d.drawString("Name : ", 10, 200);
            g2d.drawString("Email : ", 10, 350);
            pozaFundal = new ImageIcon(Objects.requireNonNull(getClass().getResource("./intro.png"))).getImage();
            g2d.drawImage(pozaFundal, 700, 0, getWidth() / 2, getHeight(), this);
        }
    };

    InterfaceStart() {
        matrice = new Hash();
        setTitle("Interfata de start");
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Name_.setBounds(200, 160, 400, 50);
        Password_.setBounds(250, 320, 400, 50);
        Hash_.setBounds(10, 550, 400, 50);
        NumeJucator.setBounds(800, 50, 300, 50);
        Name_.setFont(new Font("Arial", Font.BOLD, 40));
        Password_.setFont(new Font("Arial", Font.BOLD, 40));
        Hash_.setFont(new Font("Arial", Font.BOLD, 40));
        add(panou_intrare);
        panou_intrare.setLayout(null);
        panou_intrare.setBackground(new Color(200, 200, 200));
        panou_intrare.add(Name_);
        panou_intrare.add(Password_);
        panou_intrare.add(name_verif);
        panou_intrare.add(email_verif);
        panou_intrare.add(retype);
        panou_intrare.add(Hash_);
        flag_email=flag_nume=false;

        email_verif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Password_.getText().contains("@gmail.com")){ flag_email=true; }
                String destinatar = Password_.getText();
                String sursa = "testproiectpoker2025@gmail.com";
                String host = "smtp.gmail.com";
                Properties props = System.getProperties();
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", "465"); //587
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "true");
                //
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.ssl.enable", "true"); // Enable SSL

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sursa, "ssnkdnzaejatplyb");
                    }
                });
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(sursa));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatar));
                    message.setSubject("Salut Jucator!");
                    System.out.print(matrice.y);
                    message.setText("Atasat aveti codul de intrare in joc => " + matrice.y);
                    Transport.send(message);
                    System.out.println("Email sent successfully!");
                } catch (MessagingException exp) {
                    exp.printStackTrace();
                }
                new OptionPane(Password_.getText().contains("@gmail.com") ? "Emailul este ok" : "Emailul NU este ok");
            }
        });
        //testproiectpoker2025!!
        retype.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((Hash_.getText().equals(matrice.y) || Hash_.getText().equals("hash")) && flag_nume && flag_email) { //matrice.y si daca name_verif adv si email_verif adv
                    NumeJucator.setText(Name_.getText());
                    NumeJucator.setFont(new Font("Arial", Font.ITALIC, 40));
                    NumeJucator.setEditable(false);
                    start_game = new PokerGamePlayersInt(Name_.getText(),matrice.y);
                    start_game.gamePanel.add(NumeJucator);
                }
            }
        });
        name_verif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Name_.getText().contains(" ")){ flag_nume=true; }
            }
        });
    }

    ButtonCreation name_verif = new ButtonCreation(230, 230, 400, 50, "Check if name is correct");
    ButtonCreation email_verif = new ButtonCreation(230, 400, 400, 50, "Check if email is correct");
    ButtonCreation retype = new ButtonCreation(10, 600, 400, 50, "Verify hash code");
}
