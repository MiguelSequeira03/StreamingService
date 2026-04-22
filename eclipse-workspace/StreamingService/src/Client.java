import java.io.*;
import java.net.*;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        try (
            Socket socket = new Socket(HOST, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader reader = new BufferedReader(new FileReader("/home/miguel/Desktop/a.txt"));
        	
        		
        ) {
            System.out.println("Connected to server");

            String input;
            while ((input = stdin.readLine()) != null) {
                out.println(input);
                System.out.println("Server: " + in.readLine());
            }
        }
    }
}