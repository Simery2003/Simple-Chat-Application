import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    @Override
    public void run() {
    try {
        String username = reader.readLine();
        System.out.println("New user connected: " + username);

        ChatServer.broadcastMessage(username + " has joined the chat.", this);

        String clientMessage;
        while ((clientMessage = reader.readLine()) != null) {
            ChatServer.broadcastMessage(username + ": " + clientMessage, this);
        }
    } catch (IOException e) {
        // Handle the exception if needed (e.g., log it)
    } finally {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


    public void sendMessage(String message) {
        writer.println(message);
    }
}
