package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private int id;
    private List<String> list = new ArrayList<>();
    private ServerSocket serverSocket;

    public void run() {
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Server was run");
            while (true) {
                Socket client = serverSocket.accept();
                new Guest(client, id).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private class Guest extends Thread {

        Socket client;
        int count;

        Guest(Socket client, int count) {
            this.client = client;
            this.count = count;
        }

        @Override
        public void run() {

            new Read().start();
            new Writer().start();

        }

        private class Writer extends Thread {

            public void run(String message) {
                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                    bw.write(message + "\n");
                    bw.flush();

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        private class Read extends Thread {
            Writer writer = new Writer();

            public void run() {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

                    while (true) {
                        String line = br.readLine();

                        if (line.equals("os")) {
                            writer.run("LINUX");
                        }else if (line.equals("shutdown-server")) {
                            serverSocket.close();
                        }else {

                            Process proc = Runtime.getRuntime().exec(line.split("\n")[0]);
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

                            String commandLine = "";
                            StringBuilder builder = new StringBuilder();

                            while ((commandLine = bufferedReader.readLine()) != null) {
                                builder.append(commandLine);
                            }

                            writer.run(builder.toString() + "\n");

                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}