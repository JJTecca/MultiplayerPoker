import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class ServerPoker {
    private final int PORT;
    private final List<HandlerClient> clients;
    public ServerPoker(int port) {
        this.PORT = port;
        this.clients = new ArrayList<>();
    }
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("\nServer started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept(); //va accepta la nesfarsit
                System.out.println("\nClient connected");
                HandlerClient clientHandler = new HandlerClient(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void broadcast(String message) {
        for (HandlerClient client : clients) { client.send_Message(message); }
         //merge pe fiecare client in parte si afiseaza
    }
    public void broadcastCard(String message){
        for (HandlerClient client : clients) { client.send_MessageCard(message); }
    }
    public void broadcastCardPlayer(String message){
        for (HandlerClient client : clients) { client.send_MessageCardPlayer(message); }
    }
}
