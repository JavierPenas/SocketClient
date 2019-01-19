import java.io.*;
import java.net.Socket;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.file.*;

public class SocketRequester {

    private static Socket clientSocket;
    private static BufferedReader inFromServer;
    private static BufferedWriter writer;

    private static void start(String host, int port) throws Exception {
        try {
            //CREATES SOCKET CONNECTION
            if (clientSocket == null) {
                clientSocket = new Socket(host, port);
                clientSocket.setSoTimeout(1500);
            }
            //EXECUTES THE FILE DOWNLOAD
            download();
            //CLOSES THE SOCKET
            clientSocket.close();
        } catch (ConnectException e) {
            System.out.println("Error connecting to server. Check if it is running");
        }
    }

    private static void download() throws Exception {
        try {
            //Path destination = Paths.get("/Users/evalorenzo/Documents/LSANC.txt");
            File fnew=new File("/Users/javier/netcat/RECEIVED.txt");
            Path destination = Paths.get("/Users/javier/netcat/RECEIVED.txt");
            if (inFromServer == null) {
                inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = Files.newBufferedWriter(destination, StandardOpenOption.APPEND);
            }
            String line;
            while (inFromServer!=null && (line = inFromServer.readLine()) != null) {
                writer.write(line);
                writer.newLine();
                //System.out.println(line);
            }
        } catch (SocketTimeoutException e) {
            writer.close();
            inFromServer.close();
            System.out.println("Reader Reached Timeout");
        }
    }

/*    public static void main(String[] args) throws Exception {
        start("127.0.0.1",23);
        System.out.println("FIN DE EJECUCION");
    }*/
}
