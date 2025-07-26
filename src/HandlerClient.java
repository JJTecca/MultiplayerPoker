import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HandlerClient implements Runnable {
    private final Socket socket;
    private final ServerPoker serverPoker;
    private PrintWriter iesire_client;

    public HandlerClient(Socket socket, ServerPoker serverPoker) {
        this.socket = socket;
        this.serverPoker = serverPoker;
    }
    @Override
    public void run() {
        try {
            iesire_client = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader intrare_handler = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            while ((message = intrare_handler.readLine()) != null) {
                if(message.startsWith("CARD DEALER:")){
                    serverPoker.broadcastCard(message.substring(12)); //doar partea de path
                }
                if(message.startsWith("CARD PLAYER:")){
                    serverPoker.broadcastCardPlayer(message.substring(12)); //doar partea de path impreuna cu !./
                    //se sterge un punct
                }
                if(message.startsWith("Fold")){
                    serverPoker.broadcast(message);
                }
                if(message.startsWith("@")) {
                    System.out.println("Received: " + message.substring(1)); //apare pe server mesajul
                    serverPoker.broadcast(message);
                }
                if(message.startsWith("%")){
                    System.out.println(message.substring(1));
                    serverPoker.broadcast(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                new OptionPane("Jucatorul a iesit");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void send_Message(String message) {
        //aici verificam daca exista cartea aia sau nu
        if (iesire_client != null) {
            iesire_client.println(message);
        }
    }
    public void send_MessageCard(String message){
        if (iesire_client != null) {
            iesire_client.println(message);
        }
    }
    public void send_MessageCardPlayer(String  message){
        if (iesire_client != null) {
            iesire_client.println(message);
        }
    }
}
//pasul 1: apas pe buton si se da iesire.println
//pasul 2: incepe firul de executie din handler si trimite broadcast la server
//pasul 3: serverul trimite broadcast la toti (adica un iesire.println)
//pasul 4: se face append
//OBS: iesire_client.println() devine intrare_receiver
