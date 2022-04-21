package jpaproject.knockknock.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import jpaproject.knockknock.domain.UserLocationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FirebaseService {

    public static final String COLLECTION_NAME = "userlocation";

    public void selectUser() throws Exception{
        Firestore db = FirestoreClient.getFirestore();
        UserLocationInfo userlocation = null;
        ApiFuture<DocumentSnapshot> apiFuture = db.collection(COLLECTION_NAME).document().get();
    }
}
