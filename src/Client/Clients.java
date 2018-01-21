package Client;

import java.io.*;
import java.net.Socket;

public class Clients {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            String in = "";
            String out = "";

            while (!in.equals("end")) {
                out = bufferedReader.readLine();
                dataOutputStream.writeUTF(out);
                in = dataInputStream.readUTF();
                System.out.println(in);
                dataOutputStream.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
