package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private Map<Integer, String> clients = new HashMap<>();
    private int id;

    void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {

            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server started");

            while (true) {
                Socket client = serverSocket.accept();


                DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());

                String in = "";
                String out = "";

                while (!in.equals("end")) {
                    in = dataInputStream.readUTF();
                    System.out.println(in);
                    out = bufferedReader.readLine();
                    dataOutputStream.writeUTF(out);
                    dataOutputStream.flush();
                }
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}