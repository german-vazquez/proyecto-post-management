package proyectoSpring.postmanagement.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyectoSpring.postmanagement.dto.PostDTO;
import proyectoSpring.postmanagement.firebase.FirebaseInitializer;
import proyectoSpring.postmanagement.service.PostManagementService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PostManagementServiceImpl implements PostManagementService {

    @Autowired
    private FirebaseInitializer firebase;

    @Override
    public List<PostDTO> list() {
        List<PostDTO> response = new ArrayList<>();
        PostDTO post;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                //DocumentSnapshot contiene datos de la base de datos de Cloud Firestore
                post = doc.toObject(PostDTO.class);
                // con .toObject convierto el doc en un objeto post (PostDTO)
                post.setId(doc.getId());
                // el objeto obtenido carece de id por lo que le seteamos manualmente
                response.add(post);
                // empujamos el objeto resultante a la lista response.
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean add(PostDTO post) {
        Map<String, Object> docData = getDocData(post);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document().create(docData);
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean edit(String id, PostDTO post) {
        Map<String, Object> docData = getDocData(post);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(id).set(docData);
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean delete(String id) {
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(id).delete();
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }    }

    private CollectionReference getCollection() {
        return firebase.getFirestore().collection("post");
    }

    private Map<String, Object> getDocData(PostDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("title", post.getTitle());
        docData.put("content", post.getContent());
        return docData;
    }
}
