package com.bryan.alumnos.controllers.Alumno;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
}
      