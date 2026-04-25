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
        		DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());	
        ) {
            String message;
            System.out.println("Preparing to send receive message");
            while (true) {
            	message = in.readUTF();
            	if(message.equals("filme")) {
                    System.out.println("It is Filme");
                    FileInputStream byteFileIn = new FileInputStream("/home/miguel/Desktop/rias.mp4");
                    
                    File file = new File("/home/miguel/Desktop/rias.mp4");
                    out.writeLong(file.length());
                    out.flush();
                    
                    byte[] bytesBuffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = byteFileIn.read(bytesBuffer)) != -1) {
                        out.write(bytesBuffer, 0, bytesRead);
                    }
                    out.flush();
                    System.out.println("Filme Sent");

            	}
                 else {
                	System.out.println("Its not filme");
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