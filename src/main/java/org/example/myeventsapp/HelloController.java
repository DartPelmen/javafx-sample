package org.example.myeventsapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloController {
    @FXML
    private Label welcomeText;
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    Exchanger<Boolean> exchanger = new Exchanger<>();
    @FXML
    protected void onHelloButtonClick() {
        service.execute(() -> {
            try {
                URL url  = new URL("http://localhost:4567/hello");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                if(con.getResponseCode()==200){

                    exchanger.exchange(true);
                }
            } catch (IOException | InterruptedException e) {
                try {
                    exchanger.exchange(false);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        try {
            if(exchanger.exchange(true)){
                welcomeText.setText("connected!!");
            } else welcomeText.setText("not connected!!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}