package proyectoSpring.postmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyectoSpring.postmanagement.dto.PostDTO;
import proyectoSpring.postmanagement.service.PostManagementService;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostManagementService service;

    @GetMapping(value = "/greet/{name}")// el PathVariable determine la variable que ingresamos en el endpoint
    public String greet(@PathVariable(value = "name") String name){
        return  "Hello, "+name;
    }

    @GetMapping(value = "/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity add(@RequestBody PostDTO post) {// el RequestBody mapea el objeto json recibido en un objeto java
    return new ResponseEntity(service.add(post), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/update")
    public ResponseEntity edit(@PathVariable (value = "id") String id, @RequestBody PostDTO post){
        return new ResponseEntity(service.edit(id, post), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity delete(@PathVariable(value = "id")String id){
        return new ResponseEntity(service.delete(id),HttpStatus.OK);
    }


}
