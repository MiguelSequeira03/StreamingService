import java.io.*;
import java.net.*;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        try (
        	Socket socket = new Socket(HOST, PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        	FileOutputStream fos = new FileOutputStream("/home/miguel/Desktop/destiny.mp4");

        	
        		
        ) {
            System.out.println("Connected to server");

            String input;
            while ((input = stdin.readLine()) != null) {
                out.writeUTF(input);
                out.flush();
                if(input.equals("filme")) {
	                long fileSize = in.readLong();
	                System.out.println("Receiving file: " + fileSize + " bytes");
	                
	                byte[] buffer = new byte[4096];
	                long remaining = fileSize;
	                while (remaining > 0) {
	                    int read = in.read(buffer, 0, (int) Math.min(buffer.length, remaining));
	                    fos.write(buffer, 0, read);
	                    remaining -= read;
	                    fos.flush();
	                }
	                
	                System.out.println("File written");
                }
            }
        }
    }
} 	