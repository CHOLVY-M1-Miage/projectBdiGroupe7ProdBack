package imagoracle.univgrenoblealpes.fr.gromed.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class FirebaseInitializeService {

    @PostConstruct
    public void initialize() {

        try {

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build();

            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}