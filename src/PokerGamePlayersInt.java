import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//am nevoie de copie la deck si sa fac multi thread in alt proiect sau eventual tot in acesta

class PokerGamePlayersInt extends JFrame {
    Dimension dimensiune_ecran = Toolkit.getDefaultToolkit().getScreenSize();
    int latime_initial = (int) dimensiune_ecran.width / 2;
    int lungime_initial = (int) dimensiune_ecran.height / 2;
    int latime_carte = 110, lungime_carte = 140;
    int []folosit_temp=new int[51];
    int numar_carti_extrase_temp=-1, numar_carti_extrase_temp_1 =-1,numar_carti_extrase_temp_2=-1;
    List<String> cardState = new ArrayList<>();
    List<String> cardState1 = new ArrayList<>();
    List<String> cardState2=new ArrayList<>();
    JTextArea Istoric = new JTextArea("POKER CHAT AREA");
    PrintWriter iesire;
    JTextField mesaje_random=new JTextField("");
    Dealer dealer;
    Jucator jucator_1,jucator_2;
    PokerGamePlayersInt copie=null;
    JsonObject json_transf_1 =null;
    JsonObject json_transf_2=null;
    JsonObject json_transf_3=null;
    JsonArray json_array_transf_1=null;
    JsonArray json_array_transf_2=null;
    JsonArray json_array_transf_3=null;
    Resources resurseTotale=null;
    JLabel timer_text=new JLabel("");
    Timer timp=null;
    DecizieFinala adunareCarti=null;
    int Timp_ramas=0;
    boolean flag_decizie_fold=false,flag_player=false;
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int latime = latime_initial - 400,latime1 = latime_initial - 300,latime2=latime_initial-300;
            int lungime1 = lungime_initial - 400,lungime2=lungime_initial+100;
            try {
                for (String card : cardState) {
                    Image cardImage;
                    if (card.equals("HIDDEN")) {
                        cardImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("./cards/BACK.png"))).getImage();
                    } else {
                        cardImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(card))).getImage();
                    }
                    g.drawImage(cardImage, latime, lungime_initial - 200, latime_carte, lungime_carte, null);
                    latime += 150;
                }
                for (String card : cardState1) {
                    Image cardImage;
                    if (card.equals("HIDDEN")) {
                        cardImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("./cards/BACK.png"))).getImage();
                    } else {
                        cardImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(card))).getImage();
                    }
                    g.drawImage(cardImage, latime1, lungime1, latime_carte, lungime_carte, null);
                    latime1 += 150;
                }
                for (String card : cardState2) {
                    Image cardImage;
                    if (card.equals("HIDDEN")) {
                        cardImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("./cards/BACK.png"))).getImage();
                    } else {
                        cardImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(card))).getImage();
                    }
                    g.drawImage(cardImage, latime2, lungime2, latime_carte, lungime_carte, null);
                    latime2 += 150;
                }
            } catch (Exception e) {
                throw new RuntimeException(e); //(cause==null ? null : cause. toString())
            }
            Image banner_joc = new ImageIcon(Objects.requireNonNull(getClass().getResource("./PokerBanner.png"))).getImage();
            Image betano_sponsor= new ImageIcon(Objects.requireNonNull(getClass().getResource("./betano-logo.png"))).getImage();
            g.drawImage(banner_joc, latime_initial / 2 - 400, lungime_initial / 2 + 200, 370, 400, null);
            g.drawImage(betano_sponsor,latime_initial/2 - 400,lungime_initial/2+100,350,100,this);
        }
    };

    ButtonCreation button_dealer = new ButtonCreation(latime_initial - 100, lungime_initial - 50, 200, 50, "Dealer pune jos");
    ButtonCreation serverStartB = new ButtonCreation(latime_initial + 200, lungime_initial - 50, 200, 50, "Start server");
    ButtonCreation inreg_dealer=new ButtonCreation(latime_initial-400,lungime_initial-50,200,50,"Inregistrare dealer");
    ButtonCreation button_player_1 = new ButtonCreation(latime_initial - 600, lungime_initial - 350, 200, 50, "Ia carte de la dealer");
    ButtonCreation button_player_2 = new ButtonCreation(latime_initial - 100, lungime_initial + 250, 200, 50, "Ia carte de la dealer");
    ButtonCreation player_normal_1 = new ButtonCreation(latime_initial - 600, lungime_initial - 300, 200, 50, "Inregistrare jucator 1");
    ButtonCreation player_normal_2 = new ButtonCreation(latime_initial - 400, lungime_initial + 250, 200, 50, "Inregistrare jucator 2");
    ButtonCreation send_message=new ButtonCreation(latime_initial+250,lungime_initial-320,200,50,"Trimite mesaj");
    ButtonCreation fold=new ButtonCreation(latime_initial+350,lungime_initial-380,200,50,"Fold");
    ButtonCreation decizie_castig=new ButtonCreation(latime_initial,lungime_initial+50,200,50,"Decizie castig");

    PokerGamePlayersInt(String NumeleJucatorului,String Hashcode) {
        System.out.println(dimensiune_ecran);
        //latimea din laborator 1280/1024
        //latimea mea este 1536/864
        setTitle("Masa mare de Poker");
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Istoric.setBounds(latime_initial / 2 +500, lungime_initial / 2 +320, 300, 400);
        Istoric.setFont(new Font("Arial",Font.ITALIC,25));
        mesaje_random.setBounds(latime_initial/2+400,lungime_initial/2-100,200,50);
        gamePanel.setLayout(null);
        gamePanel.setBackground(new Color(53, 100, 77));
        timer_text.setBounds(latime_initial - 700, lungime_initial - 400, 400, 60);
        timer_text.setFont(new Font("Arial",Font.BOLD,30)); timer_text.setText("Ceas:"); timer_text.setForeground(Color.RED);
        add(gamePanel);
        gamePanel.add(button_dealer);gamePanel.add(serverStartB);gamePanel.add(button_player_1); gamePanel.add(button_player_2);
        gamePanel.add(player_normal_1); gamePanel.add(player_normal_2); gamePanel.add(Istoric); gamePanel.add(mesaje_random); gamePanel.add(send_message);
        gamePanel.add(inreg_dealer); gamePanel.add(timer_text); gamePanel.add(fold); gamePanel.add(decizie_castig);
        for (int i = 0; i < 5; i++) { cardState.add("HIDDEN"); } //partea de jos
        for (int i = 0; i < 2; i++) { cardState1.add("HIDDEN"); } //partea de sus
        for (int i = 0; i < 2; i++) { cardState2.add("HIDDEN"); } //partea de sus
        copie=this;
        resurseTotale=new Resources(new int[51],Hashcode,flag_player);

        inreg_dealer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    player_normal_1.setEnabled(false); player_normal_2.setEnabled(false);
                    button_player_1.setEnabled(false); button_player_2.setEnabled(false);
                    Istoric.setForeground(Color.red);
                    folosit_temp=new int[51];
                    dealer=new Dealer(folosit_temp,numar_carti_extrase_temp,Hashcode,flag_player);
                    Socket clientSocket=new Socket("192.168.12.243",12345);
                    iesire=new PrintWriter(clientSocket.getOutputStream(),true);
                    json_transf_1 =new JsonObject();
                    json_transf_1.addProperty("CARD DEALER:","./cards/");
                    json_transf_1.addProperty("PORT:",12345); json_transf_1.addProperty("Numarul cartilor extrase:",0);
                    json_transf_1.addProperty("Path carte:","null"); json_transf_1.addProperty("Mesaj Random trimis:","null");
                    json_array_transf_1 =new JsonArray(); json_array_transf_1.add(NumeleJucatorului); json_array_transf_1.add(json_transf_1);
                    new Thread(new ReceiverClient(clientSocket,Istoric,NumeleJucatorului,copie, json_array_transf_1)).start();
                    System.out.println("\nConnected to server");
                }catch (IOException exc){exc.printStackTrace();}
            }
        });
        button_dealer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Timp_ramas == 0) {
                    String card = dealer.IaCarte();
                    dealer.marcatLuat(card, resurseTotale.folosit, dealer.Hashcode); //polimorfism marchez ca este vorba de dealer
                    System.out.println("asta e pt dealer : " + dealer.numar_carti_extrase);
                    if (iesire != null) {
                        JsonObject cheie = json_array_transf_1.get(1).getAsJsonObject();
                        String key_string = cheie.keySet().iterator().next(); // CARD DEALER:
                        String value_string = cheie.entrySet().iterator().next().getValue().getAsString(); // ./cards/
                        cheie.addProperty("Numarul cartilor extrase:", dealer.numar_carti_extrase);
                        cheie.addProperty("Path carte:", card);
                        json_array_transf_1.set(1, cheie);
                        System.out.println(json_array_transf_1);
                        iesire.println(key_string + value_string + card); //inlocuit cu json
                        System.out.println("Carte trimisa cu numarul " + dealer.numar_carti_extrase + " si poza " + card);
                        iesire.println("%Id:" + dealer.Hashcode);
                    }
                }
            }
        });
        serverStartB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                folosit_temp=new int[51];
                new Thread(()-> {
                    ServerPoker DealerServer=new ServerPoker(12345); DealerServer.start();
                }).start();
                serverStartB.setEnabled(false);
            }
        });
        player_normal_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    serverStartB.setEnabled(false); player_normal_2.setEnabled(false);
                    button_player_2.setEnabled(false); button_dealer.setEnabled(false);
                    decizie_castig.setEnabled(false); inreg_dealer.setEnabled(false);
                    Istoric.setForeground(Color.blue);
                    folosit_temp=new int[51];
                    jucator_1 =new Jucator(folosit_temp,Hashcode,numar_carti_extrase_temp_1,flag_player);
                    jucator_1.numar_playeri_live= resurseTotale.numar_playeri_live=2; //tinem evidenta pentru fiecare jucator
                    jucator_1.AfisareNumarPlayeriLive();
                    Socket clientSocket=new Socket("192.168.12.243",12345);
                    iesire=new PrintWriter(clientSocket.getOutputStream(),true);
                    json_transf_2 =new JsonObject();
                    json_transf_2.addProperty("CARD PLAYER:","!./cards/");
                    json_transf_2.addProperty("PORT:",12345);  json_transf_2.addProperty("Numarul cartilor extrase:",0);
                    json_transf_2.addProperty("Path carte:","null"); json_transf_2.addProperty("Mesaj Random trimis:","null");
                    json_array_transf_2 =new JsonArray(); json_array_transf_2.add(NumeleJucatorului); json_array_transf_2.add(json_transf_2);
                    new Thread(new ReceiverClient(clientSocket,Istoric,NumeleJucatorului,copie, json_array_transf_2)).start();
                    //receiver +handler
                    System.out.println("\nConnected to server");

                }catch (IOException exc){exc.printStackTrace();}
            }
        });
        player_normal_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    serverStartB.setEnabled(false); player_normal_1.setEnabled(false);
                    button_player_1.setEnabled(false); button_dealer.setEnabled(false);
                    decizie_castig.setEnabled(false); inreg_dealer.setEnabled(false);
                    Istoric.setForeground(Color.blue);
                    folosit_temp=new int[51];
                    jucator_2 =new Jucator(folosit_temp,Hashcode,numar_carti_extrase_temp_2,flag_player);
                    jucator_2.numar_playeri_live= resurseTotale.numar_playeri_live=3; //tinem evidenta pentru fiecare jucator
                    jucator_2.AfisareNumarPlayeriLive();
                    Socket clientSocket=new Socket("192.168.12.243",12345);
                    iesire=new PrintWriter(clientSocket.getOutputStream(),true);
                    json_transf_3 =new JsonObject();
                    json_transf_3.addProperty("CARD PLAYER:","#./cards/");
                    json_transf_3.addProperty("PORT:",12345);  json_transf_3.addProperty("Numarul cartilor extrase:",0);
                    json_transf_3.addProperty("Path carte:","null"); json_transf_3.addProperty("Mesaj Random trimis:","null");
                    json_array_transf_3 =new JsonArray(); json_array_transf_3.add(NumeleJucatorului); json_array_transf_3.add(json_transf_3);
                    new Thread(new ReceiverClient(clientSocket,Istoric,NumeleJucatorului,copie, json_array_transf_3)).start();
                    //receiver +handler
                    System.out.println("\nConnected to server");
                }catch (IOException exc){exc.printStackTrace();}
            }
        });
        button_player_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String card = jucator_1.IaCarte(); //ca sa nu fie null
                    jucator_1.marcatLuat(card, resurseTotale.folosit, jucator_1.Hashcode); //aici punem cartea care inca n-o fost marcata
                    card = jucator_1.carteLuataJucator;
                    System.out.println("asta e pt jucator : " + jucator_1.numar_carti_extrase);
                    if (jucator_1.numar_carti_extrase == 1) {
                        jucator_1.extrase = true; //System.out.println(jucator_1.extrase);
                    }
                    if (iesire != null) {
                        JsonObject cheie = json_array_transf_2.get(1).getAsJsonObject();
                        String key_string = cheie.keySet().iterator().next();
                        String value_string = cheie.entrySet().iterator().next().getValue().getAsString();
                        cheie.addProperty("Numarul cartilor extrase:", jucator_1.numar_carti_extrase);
                        cheie.addProperty("Path carte:", card);
                        json_array_transf_2.set(1, cheie);
                        System.out.println(json_array_transf_2);
                        iesire.println(key_string + value_string + card);
                        System.out.println("Carte trimisa cu numarul " + jucator_1.numar_carti_extrase + " si poza " + card);
                        iesire.println("%Id:" + jucator_1.Hashcode);
                    }
                }

        });
        button_player_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String card= jucator_2.IaCarte();
                jucator_2.marcatLuat(card, resurseTotale.folosit, jucator_2.Hashcode); //aici punem cartea care inca n-o fost marcata
                card=jucator_2.carteLuataJucator;
                System.out.println("asta e pt jucator : "+ jucator_2.numar_carti_extrase);
                if(jucator_2.numar_carti_extrase==1){ jucator_2.extrase=true; }
                if(iesire!=null) {
                    JsonObject cheie= json_array_transf_3.get(1).getAsJsonObject();
                    String key_string=cheie.keySet().iterator().next();
                    String value_string=cheie.entrySet().iterator().next().getValue().getAsString();
                    cheie.addProperty("Numarul cartilor extrase:", jucator_2.numar_carti_extrase);
                    cheie.addProperty("Path carte:",card);
                    json_array_transf_3.set(1,cheie);
                    System.out.println(json_array_transf_3);
                    iesire.println(key_string + value_string + card);
                    System.out.println("Carte trimisa cu numarul " + jucator_2.numar_carti_extrase + " si poza "+ card);
                    iesire.println("%Id:"+jucator_2.Hashcode);
                }
            }
        });
        send_message.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (iesire!= null && !mesaje_random.getText().isEmpty()) {
                    iesire.println("@"+mesaje_random.getText()); mesaje_random.setText("");
                }
            }
        });
        fold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fold.setEnabled(false); flag_decizie_fold=true;
            }
        });
        decizie_castig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dealer!=null) {
                    adunareCarti = new DecizieFinala(resurseTotale);
                    adunareCarti.contorCarti();
                }
            }
        });
    }
    public void DealerPuneJos(String card){
        numar_carti_extrase_temp++;
        //nu pot scrie aici dealer.marcatLuat pt ca playerii nu vad asta , o iau ca pe null
        resurseTotale.marcatLuat(card,resurseTotale.folosit,"dealer"); //aici se stie ca s-a marcat ce a luat dealeru
        System.out.println("Deck:");
        for(int i=0;i<51;i++){
            if(resurseTotale.folosit[i]==1)
                System.out.println(resurseTotale.carti_existente[i]+" cu codul "+resurseTotale.nume_jucatori_poz[i]);
        }
        if(numar_carti_extrase_temp+numar_carti_extrase_temp_1+numar_carti_extrase_temp_2==6){
            resurseTotale.extrase=true; System.out.println("Extrase!!");
        }
        if(numar_carti_extrase_temp<3){ cardState.set(numar_carti_extrase_temp,card); gamePanel.repaint(); }
        else if (numar_carti_extrase_temp_1>0 && numar_carti_extrase_temp_2>0 && numar_carti_extrase_temp<5) {
            cardState.set(numar_carti_extrase_temp,card); gamePanel.repaint(); Timp_ramas=30;
        }
    }
    public void Jucator_1_PuneJos(String card){
        resurseTotale.marcatLuat(card,resurseTotale.folosit, "player 1");
        if(numar_carti_extrase_temp>=2){ //sau dupa prima runda de checkuri
            numar_carti_extrase_temp_1++;
            cardState1.set(numar_carti_extrase_temp_1,card);
            if(dealer==null){gamePanel.repaint();}
        }
        if(numar_carti_extrase_temp==2){ startCeas(10); }
    }
    public void Jucator_2_PuneJos(String card){
        resurseTotale.marcatLuat(card,resurseTotale.folosit, "player 2");
        if(numar_carti_extrase_temp>=2){ //sau dupa prima runda de checkuri
            numar_carti_extrase_temp_2++;
            cardState2.set(numar_carti_extrase_temp_2,card);
            if(dealer==null){ gamePanel.repaint(); }
        }
        if(numar_carti_extrase_temp==2){ startCeas(10); }
    }
    public void afiseazaDecizia(){
        JFrame frame=new JFrame();
        String loser="Oponentul a iesit din joc";
        JOptionPane.showMessageDialog(frame,loser);
    }
    public void iaDecizie(){
        if(iesire!=null && flag_decizie_fold){ iesire.println("Folding");}
    }
    public void startCeas(final int timp_ramas){
       ActionListener count=new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(Timp_ramas>=0){ timer_text.setText("Timp ramas:"+Timp_ramas+" secunde"); Timp_ramas--; }
               else{
                   Timp_ramas=0; timp.stop(); iaDecizie();
               }
           }
       };
       timp=new javax.swing.Timer(2000,count);
       timp.setInitialDelay(0); Timp_ramas=timp_ramas; timp.start();
    }
}