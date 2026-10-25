
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 80;
        HttpServer server = HttpServer.create(
            new InetSocketAddress(port), 0
        );

        server.createContext("/", exchange -> {
            String url = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();
            int status;
            String message;

            if (url.equals("/")) {
                if (method.equals("GET")) {
                    status = 200;
                    message = "vankz server";
                } else if (method.equals("POST")) {
                    status = 200;
                    message = "POST received";
                } else {
                    status = 405;
                    message = "You cant access that";
                }
            } else {
                status = 404;
                message = "404 Not Found";
            }

            byte[] response = message.getBytes(//ubah teks jd byte
                StandardCharsets.UTF_8
            );

            exchange.getResponseHeaders().set(
                "Content-Type", "text/plain; charset=UTF-8"//konten respons
            );
            exchange.sendResponseHeaders(status, response.length);

            try (OutputStream output = exchange.getResponseBody()) {
                output.write(response);
            }
            exchange.close();
        });
        server.start();
        System.out.println("HTTP STATUS : UP");
        System.out.println("Server running on port " + port);
    }
}
