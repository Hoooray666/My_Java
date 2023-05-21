import java.io.*;
import java.net.Socket;
import java.util.Base64;

/**
 * @Author: Francis
 * @Date: 2023-05-19
 * @Description: written by xiaoshizi
 */
public class Receive_Thread extends Thread{
    private Socket socket = null;
    private String ip;
    private int port;

    public Receive_Thread(Socket socket, String ip, int port) {
        this.socket = socket;
        this.ip = ip;
        this.port = port;
    }
    @Override
    public void run(){
        String user_64 = null;//username in base64
        String pwd_64 = null;//pwd in base64
        String mailfrom = null;
        String RCPT = null;
        InputStream input = null;
        InputStreamReader inreader = null;
        OutputStream output = null ;
        BufferedReader reader = null;
        PrintWriter writer = null;

        char[] data_array0 = new char[100000];
        char[] data_array1 = new char[100000];
        StringBuilder data = new StringBuilder();

        System.out.println("waiting for connection");
        try {
            String messages = null;
            input = socket.getInputStream();
            inreader = new InputStreamReader(input);
            reader = new BufferedReader(inreader);
            output = socket.getOutputStream();
            writer = new PrintWriter(output);


            writer.write("220 Hello .this is xiaoshizi server\r\n");
            System.out.println("220 Hello .this is xiaoshizi server");
            writer.flush();

            while((messages = reader.readLine())!= null){
                if(messages.startsWith("EHLO") || messages.startsWith("HELO")){
                    System.out.println(messages);

                    writer.write("250-newxmesmtplogicsvrszc2-0.qq.com\n" +
                            "250-PIPELINING\n" +
                            "250-SIZE 73400320\n" +
                            "250-STARTTLS\n" +
                            "250-AUTH LOGIN PLAIN XOAUTH XOAUTH2\n" +
                            "250-AUTH=LOGIN\n" +
                            "250-MAILCOMPRESS\n" +
                            "250 8BITMIME\r\n");
                    writer.flush();
                    sleep(2500);
                    System.out.println("250-newxmesmtplogicsvrszc2-0.qq.com\n" +
                            "250-PIPELINING\n" +
                            "250-SIZE 73400320\n" +
                            "250-STARTTLS\n" +
                            "250-AUTH LOGIN PLAIN XOAUTH XOAUTH2\n" +
                            "250-AUTH=LOGIN\n" +
                            "250-MAILCOMPRESS\n" +
                            "250 8BITMIME");
                    continue;
                }

                if(messages.equals("AUTH")|| messages.equals("auth login") || messages.equals("AUTH LOGIN")){
                    sleep(100);
                    writer.write("334 VXNlcm5hbWU6\r\n");// ask for user in base64
                    writer.flush();

                    user_64 = reader.readLine();// read the username given
                    System.out.println(decode_base64(user_64));

                    writer.write("334 UGFzc3dvcmQ6\r\n");// ask for pwd in base64
                    writer.flush();

                    pwd_64 = reader.readLine();// read the pwd given
                    System.out.println(decode_base64(pwd_64));

                    writer.write("235 OK authentication\r\n");
                    System.out.println("235 OK authentication");
                    writer.flush();
                }

                if(messages.startsWith("MAIL FROM")){
                    String address = messages.substring(messages.lastIndexOf('<')+1,messages.lastIndexOf('>'));
                    if(address.matches( "^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$")){
                        mailfrom = messages;
                        System.out.println(mailfrom);

                        writer.write("250 OK \r\n");
                        System.out.println("250 OK");
                        writer.flush();
                        continue;
                    }else{
                        writer.write("550  wrong format\r\n");
                        writer.flush();
                        socket.close();
                    }
                }

                if(messages.startsWith("RCPT TO")){
                    String address = messages.substring(messages.lastIndexOf('<')+1,messages.lastIndexOf('>'));
                    if(address.matches( "^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$")){
                        RCPT = messages;
                        System.out.println(RCPT);

                        writer.write("250 OK \r\n");
                        System.out.println("250 OK");
                        writer.flush();
                        continue;
                    }else{
                        writer.write("550  wrong format\r\n");
                        writer.flush();
                        socket.close();
                    }
                }

                if(messages.equals("DATA")){
                    writer.write("354 End data with <CR><LF>.<CR><LF>.\r\n");
                    writer.flush();
                    System.out.println(messages);
                    System.out.println("354 End data with <CR><LF>.<CR><LF>.");

                    reader.read(data_array0);
                    Receive_Thread.sleep(2000);
                    reader.read(data_array1);


                    for(int i = 0; data_array0[i] != '\u0000' ; i++){
                        data.append(data_array0[i]);
                    }
                    for(int i = 0; data_array1[i] != '\u0000' ; i++){
                        data.append(data_array1[i]);
                    }
                    System.out.println("-------------data below -----------");
                    System.out.println(data);
                    System.out.println("----------block receive thread------------");
                    Socket socket = new Socket("smtp.qq.com",25);
                    Send_Thread sndthread = new Send_Thread(socket, data.toString(), mailfrom,RCPT,user_64,pwd_64 );
                    sndthread.start();
                    sndthread.join();
                    if (sndthread.return_from_smtp()!=null){
                        writer.write(sndthread.return_from_smtp()+"\r\n");
                        writer.flush();
                    }

                    System.out.println("---------activate receive thread--------");
                    writer.write("250 OK: queued as.\n");
                    writer.flush();
                    System.out.println("250 OK");
                    System.out.println(reader.readLine());
                    writer.write("211 Bye\r\n");
                    writer.flush();
                    System.out.println("211 Bye");
                }

                if(messages.equals("QUIT")){
                    writer.write("211 Bye\r\n");
                    writer.flush();
                    input.close();
                }
            }
        } catch (Exception  e) {
        }finally {

        }
    }
    public String decode_base64(String str){
        byte[] decode = Base64.getDecoder().decode(str);
        String decodeStr = new String(decode);
        return (decodeStr);
    }
}
