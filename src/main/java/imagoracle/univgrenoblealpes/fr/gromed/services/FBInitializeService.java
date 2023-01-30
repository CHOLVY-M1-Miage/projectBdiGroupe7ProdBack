package imagoracle.univgrenoblealpes.fr.gromed.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class FBInitializeService {

    @PostConstruct
    public void initialize() {

        try {

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
