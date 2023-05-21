import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: Francis
 * @Date: 2023-05-19-1:55
 * @Description: written by xiaoshizi
 */
public class Main_socket {
    public static void main(String[] args) throws IOException {
        create_and_send();

    }
    public static String create_and_send(){
        try (Socket socket = new Socket ();
             Scanner sc = new Scanner (System.in)){
            socket.setKeepAlive (true);
            socket.connect(new InetSocketAddress("localhost",25),1000);
//            socket.setSendBufferSize (25565);//设置output缓冲区大小
//            socket.setReceiveBufferSize (25565);//设置input缓冲区大小
            System.out.println ("connected to server !");
            OutputStreamWriter writer = new OutputStreamWriter (socket.getOutputStream ());
            writer.write (sc.nextLine ()+"\n");
            writer.flush ();
//            socket.shutdownOutput ();
            System.out.println ("waiting for server to confirm");
            BufferedReader reader = new BufferedReader (new InputStreamReader(socket.getInputStream ()));
//            System.out.println (reader.readLine ());
            return reader.readLine();
//            socket.shutdownInput ();
        } catch (IOException e) {
            System.out.println (" not connected to server !");
            e.printStackTrace ();
            return"wrong!";
        }
    }
}
