import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    private static String name = System.getProperty("user.name");
    private static Socket connection;
    private static PrintStream out;
    private static BufferedReader in;
    private static boolean running;

    public static void close() throws IOException {
        in.close();
        out.close();
        connection.close();
    }
    public static void main(String[] args) {
        try {
            connection = new Socket("192.168.2.119", 58165);
            running = true;

            out = new PrintStream(connection.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            out.println("target.name");
            out.println(name);
            out.println();

            while (running) {
                String line = in.readLine();
                while (line != null && line.length() > 0) {
                    if (line.equals("name")) {
                        out.println(name);
                    }
                    if (line.equals("quit")) {
                        out.println("target.quit");
                        running = false;
                    }

                    line = in.readLine();
                }
            }

            close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}