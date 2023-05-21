import java.io.*;
import java.net.Socket;

/**
 * @Author: Francis
 * @Date: 2023-05-19
 * @Description: written by xiaoshizi
 */
public class Send_Thread extends Thread {
    String data;
    String result =null;
    String user_64;
    String pwd_64;
    String mailfrom;
    String RCPT;
    Socket socket = null;
    public Send_Thread(Socket socket, String data, String mailfrom, String RCPT, String user_64, String pwd_64) {
        this.socket = socket;
        this.data=data;
        this.mailfrom=mailfrom;
        this.RCPT = RCPT;
        this.user_64 = user_64;
        this.pwd_64 = pwd_64;
    }
    public String return_from_smtp(){
        return this.result;
    }
    @Override
    public void run(){
        InputStream input = null;
        InputStreamReader inreader = null;
        OutputStream output = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            input = socket.getInputStream();
            inreader = new InputStreamReader(input);
            reader = new BufferedReader(inreader);
            output = socket.getOutputStream();
            writer = new PrintWriter(output);
            String messages =null;
            messages =reader.readLine();

            System.out.println(messages);
            if (messages.startsWith("220")) {
                writer.write("HELO smtp.qq.com\r\n");
                writer.flush();
//                System.out.println("EHLO smtp.qq.com");
                Send_Thread.sleep(1000);
                System.out.println(reader.readLine());
                writer.write("auth login\r\n");
                writer.flush();
//                System.out.println("auth login");
                Send_Thread.sleep(1000);


                writer.write(user_64 + "\r\n");//input username
                writer.flush();
                System.out.println(user_64);
                System.out.println( reader.readLine());
                Send_Thread.sleep(2000);

                writer.write(pwd_64 + "\r\n");//input pwd
                writer.flush();
                System.out.println(pwd_64);
                System.out.println( reader.readLine());
                Send_Thread.sleep(2000);

                writer.write(mailfrom + "\r\n");//input maifrom
                writer.flush();
                System.out.println(mailfrom);
                System.out.println(reader.readLine());
                reader.readLine();
                Send_Thread.sleep(1000);

                writer.write(RCPT + "\r\n");//input rcpt
                writer.flush();
                System.out.println(RCPT);
                System.out.println(reader.readLine());
                writer.flush();
                Send_Thread.sleep(1000);

                writer.write("DATA\r\n");
                writer.flush();
                System.out.println("DATA");
                Send_Thread.sleep(100);
                System.out.println(reader.readLine());
//                sndThread.sleep(100);
//                System.out.println(reader.readLine());
                writer.write(data + "\r\n");
                writer.flush();
                System.out.println(data);
                System.out.println(result =reader.readLine());
                Send_Thread.sleep(1000);
                writer.write("QUIT\r\n");
                writer.flush();
                System.out.println("QUIT");
                Send_Thread.sleep(1000);
                reader.readLine();
//                System.out.println( reader.readLine());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (inreader != null) {
                    inreader.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
