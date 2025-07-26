import com.google.gson.JsonArray;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


class ReceiverClient  implements Runnable {
    private final Socket socket;
    private final JTextArea chatArea;
    private String Nume=null;
    private PokerGamePlayersInt x=null;
    private JsonArray json_array_transf =null;
    public ReceiverClient(Socket socket, JTextArea chatArea, String Nume, PokerGamePlayersInt x, JsonArray json_array_transf) {
        this.socket = socket; this.chatArea = chatArea; this.Nume=Nume; this.x=x; this.json_array_transf = json_array_transf;
    }
    @Override
    public void run() {
        try {
            BufferedReader intrare_receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String mesaj_player;
            while ((mesaj_player = intrare_receiver.readLine()) != null) {
                if(mesaj_player.startsWith("./cards")){ x.DealerPuneJos(mesaj_player); }
                else{
                    if(mesaj_player.startsWith("!./cards")){ x.Jucator_1_PuneJos(mesaj_player.substring(1));  }
                    else{
                        if(mesaj_player.startsWith("#./cards")){ x.Jucator_2_PuneJos(mesaj_player.substring(1));}
                        else{
                            if(mesaj_player.startsWith("Fold")){ x.afiseazaDecizia(); }
                            else {
                                if(mesaj_player.startsWith("%")){ chatArea.append("\n"+mesaj_player.substring(1)); }
                                else{
                                    if (mesaj_player.startsWith("@")) { chatArea.append("\nTo everyone: " + mesaj_player.substring(1)); }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

