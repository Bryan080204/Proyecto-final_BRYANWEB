package com.bryan.alumnos.controllers.Alumno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bryan.alumnos.model.Alumno;
import com.bryan.alumnos.repository.AlumnoRepository;

@RestController
@RequestMapping("/alumnos")

public class AlumnoController {
   
    @Autowired
    private AlumnoRepository alumnoRepository;
    


    @GetMapping ("/traer-alumnos")
    public List<Alumno>TraerAlumnos(){
    return alumnoRepository.findAll();
    }

     @PostMapping ("/insertar-alumnos")
     public Alumno insertarAlumnos (@RequestBody Alumno alumno){
        return alumnoRepository.save(alumno);
 
}

@PutMapping ("/editar-alumnos/{id}")
public ResponseEntity< Alumno> actualizarAlumno (@PathVariable long id, @RequestBody Alumno alumno){
    return alumnoRepository.findById(id).map(alumnoExistente -> {
 
alumnoExistente.setNombre(alumno.getNombre());

alumnoExistente.setApellido(alumno.getApellido());

alumnoExistente.setEmail(alumno.getEmail());
alumnoExistente.setNumeroControl(alumno.getNumeroControl());
alumnoExistente.setCarrera (alumno.getCarrera());
alumnoExistente.setImagenURL(alumno.getImagenURL());
Alumno actualizado = alumnoRepository.save(alumnoExistente);
return ResponseEntity.ok (actualizado);
}).orElse (ResponseEntity.notFound().build());
 

}

@DeleteMapping("/eliminar-alumnos/{id}")
public void eliminarAlumno (@PathVariable Long id) {

alumnoRepository.deleteById(id);

}     
}    