import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 8080;

    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
    	System.out.println("Starting");
    	System.out.flush();
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server listening on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    private static void handleClient(Socket socket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            FileInputStream byteFileIn = new FileInputStream("/home/miguel/Desktop/b.mp4");
            OutputStream bytesOut = socket.getOutputStream();	
        ) {
            String message;
            System.out.println("Preparing to send receive message");
            while ((message = in.readLine()) != null) {
            	if(message.equals("filme")) {
                    System.out.println("It is Filme");
                    byte[] bytesBuffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = byteFileIn.read(bytesBuffer)) != -1) {
                        bytesOut.write(bytesBuffer, 0, bytesRead);
                    }
                    System.out.println("Filme Sent");

            	}
                 else {
                	System.err.println("Its not filme");
	                System.out.println("Received: " + message);
	                out.println("Server received message");
                 }
            }
   
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("Client disconnected");
        }
    }
}