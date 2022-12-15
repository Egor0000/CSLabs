package services.impl;

import ciphers.AbstractCipher;
import ciphers.CipherFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import domain.dtos.InputDto;
import domain.dtos.UserDto;
import domain.mappers.CipherMapper;
import lombok.extern.slf4j.Slf4j;
import services.CipherAccessService;
import services.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

@Slf4j
public class CipherAccessServiceImpl implements CipherAccessService {
    private HttpServer server;
    private ObjectMapper om = new ObjectMapper();
    private AuthenticatorImpl authenticatorClass;
    public CipherAccessServiceImpl() {
        try {
            initServer();
            server.start();
            log.info("Server successfully started");
            UserService userService = new UserServiceImpl();
            authenticatorClass = new AuthenticatorImpl(userService);
        } catch (Exception ex) {
            log.error("Failed to init or start server");
        }
    }

    public String encrypt (String text, InputDto cipherDto) {
        AbstractCipher cipher = new CipherFactory().getCipher(cipherDto);

        return cipher.encrypt(text);
    }

    public String decrypt (String text, InputDto cipherDto) {
        AbstractCipher cipher = new CipherFactory().getCipher(cipherDto);

        return cipher.decrypt(text);
    }

    private void initServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", 8555), 5);

        server.createContext("/encrypt", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                if (httpExchange.getRequestMethod().equals("POST")) {
                    String body = new String(httpExchange.getRequestBody().readAllBytes());

                    InputDto input = CipherMapper.jsonToObject(body);

                    boolean authenticated = authenticatorClass.verify(input.getUserDto(), input.getUserDto().getCode());

                    boolean authorized = authenticatorClass.authorize(input);
                    String responseContent =  "Authentication failed";

                    if (authenticated) {
                        responseContent = "Authorization failed";
                        if (authorized) {
                            responseContent =  encrypt(input.getText(), input);
                        }
                    }

                    // do body parsing

                    httpExchange.sendResponseHeaders(200, responseContent.length());
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.write(responseContent.getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
            }
        });

        server.createContext("/decrypt", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                if (httpExchange.getRequestMethod().equals("POST")) {
                    String body = new String(httpExchange.getRequestBody().readAllBytes());

                    InputDto input = CipherMapper.jsonToObject(body);

                    boolean authenticated = authenticatorClass.verify(input.getUserDto(), input.getUserDto().getCode());

                    boolean authorized = authenticatorClass.authorize(input);
                    String responseContent =  "Authentication failed";

                    if (authenticated) {
                        responseContent = "Authorization failed";
                        if (authorized) {
                            responseContent =  decrypt(input.getText(), input);
                        }
                    }

                    httpExchange.sendResponseHeaders(200, responseContent.length());
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.write(responseContent.getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
            }
        });

        server.createContext("/login", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                if (httpExchange.getRequestMethod().equals("POST")) {
                    String body = new String(httpExchange.getRequestBody().readAllBytes());

                    UserDto userDto = om.readValue(body, UserDto.class);

                    // do body parsing
                    boolean res = authenticatorClass.verify(userDto, userDto.getCode());
                    String responseContent = "Verified";

                    if (!res) {
                        responseContent = "Not " + responseContent;
                    }
//                    String responseContent = "hello";

                    httpExchange.sendResponseHeaders(200, responseContent.length());
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.write(responseContent.getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
            }
        });

        server.createContext("/register", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                if (httpExchange.getRequestMethod().equals("POST")) {
                    String body = new String(httpExchange.getRequestBody().readAllBytes());

                    UserDto userDto = om.readValue(body, UserDto.class);

                    // do body parsing
                    authenticatorClass.register(userDto);
                    String responseContent = "registered";

                    httpExchange.sendResponseHeaders(200, responseContent.length());
                    OutputStream outputStream = httpExchange.getResponseBody();
                    outputStream.write(responseContent.getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
            }
        });

    }

}
