//UDP multiuser chat
//server.java
//multiuser chat
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {
    static byte[] recvData = new byte[1024];
    static byte[] sendData = new byte[1024];
    static DatagramSocket ds;
    static InetAddress group;

    public static void main(String[] args) {
        try {
            // Initialize DatagramSocket on port 9877
            ds = new DatagramSocket(9877);
            // Multicast group address
            group = InetAddress.getByName("230.0.0.0");

            DatagramPacket recvPack;
            while (true) {
                // Receive data
                recvPack = new DatagramPacket(recvData, recvData.length);
                ds.receive(recvPack);

                // Convert received data to a string, and trim null characters
                String sentence = new String(recvPack.getData(), 0, recvPack.getLength());
                System.out.println("RECEIVED: " + sentence);

                // Broadcast the received message
                method(sentence);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast the message to all clients
    public static void method(String sentence) {
        sendData = sentence.getBytes();
        DatagramPacket sendPack = new DatagramPacket(sendData, sendData.length, group, 4447);
        try {
            ds.send(sendPack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
