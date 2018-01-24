package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;

    public void run(String ip, int port) throws IOException {
        connect(ip, port);
        new Write().start();
        new Read().start();
    }

    public void connect(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        System.out.printf("connect to %s\n", socket);
    }

    public void sendMessage(String line) throws IOException{

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        bw.write(line + "\n");
        bw.flush();

    }
    private class Write extends Thread{


        @Override
        public void run(){
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                Scanner sc = new Scanner(System.in);

                while(true) {
                    System.out.printf("I to server : ");
                    String line = sc.nextLine();
                    bw.write(line + "\n");
                    bw.flush();
                }

            } catch (IOException e){
                System.out.println(e.getMessage());
            }

        }
    }
    private class Read extends Thread{


        @Override
        public void run(){
            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

                while(true){
                    String line = br.readLine();
                    if(line == null) break;
                    System.out.printf("\nserver : %s\n", line);
                }

            } catch (IOException e){
                e.printStackTrace();
            }
            System.out.println("\nserver break the connection\n");
        }
    }

    public String getIp(){
        return socket.getInetAddress().getHostAddress();
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public void disconnect(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
