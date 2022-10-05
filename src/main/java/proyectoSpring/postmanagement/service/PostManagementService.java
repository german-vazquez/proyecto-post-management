package proyectoSpring.postmanagement.service;

import proyectoSpring.postmanagement.dto.PostDTO;

import javax.annotation.PostConstruct;
import java.util.List;

public interface PostManagementService {

    List<PostDTO> list();

    Boolean add(PostDTO post);

    Boolean edit(String id, PostDTO post);

    Boolean delete(String id);
}
