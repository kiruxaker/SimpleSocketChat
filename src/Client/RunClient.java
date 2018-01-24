package Client;

import java.io.IOException;

public class RunClient {

    public static void main(String[] args) throws IOException {
        new Client().run("localhost", 8080);
    }

}
