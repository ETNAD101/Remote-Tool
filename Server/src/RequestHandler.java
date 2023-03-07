import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class RequestHandler extends Thread
{
    private Socket client;

    private String target;
    private  BufferedReader in;
    private PrintWriter out;
    private boolean running;
    RequestHandler(Socket socket) {
        this.client = socket;
    }

    public void sendCommand(String command) {
        out.println(command);
        out.println();
    }

    @Override
    public void run() {
        try {
            System.out.println("Received a connection");

            // Get input and output streams
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            running = true;
            while (running) {
                String line = in.readLine();
                while(line != null && line.length() > 0) {
                    if (line.equals("target.name")) {
                        line = in.readLine();
                        target = line;
                        System.out.println("Target name: " + target);
                    }
                    if (line.equals("target.quit")) {
                        running = false;
                    }
                    System.out.println("Client: " + line);
                    line = in.readLine();
                }

//            Scanner sc = new Scanner(System.in);
//            String command = sc.nextLine();
//            out.println(command);
                sendCommand("name");
                sendCommand("quit");
            }


            // Close our connection
            in.close();
            out.close();
            client.close();

            System.out.println("Connection closed");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}