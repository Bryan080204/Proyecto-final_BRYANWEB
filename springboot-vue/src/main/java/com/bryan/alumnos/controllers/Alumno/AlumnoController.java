package com.bryan.alumnos.controllers.Alumno;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bryan.alumnos.model.Alumno;
import com.bryan.alumnos.repository.AlumnoRepository;

@RestController
@RequestMapping("/alumnos")
@CrossOrigin(origins = "*")
public class AlumnoController {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @GetMapping("/traer-alumnos")
    public List<Alumno> TraerAlumnos() {
        return alumnoRepository.findAll();
    }

    @GetMapping("/traer-alumno/{id}")
    public ResponseEntity<Alumno> TraerUnAlumno(@PathVariable Long id) {
        return alumnoRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/insertar-alumno")
    public ResponseEntity<Alumno> insertarAlumno(@RequestBody Alumno alumno) {
        Alumno nuevoAlumno = alumnoRepository.save(alumno);
        return ResponseEntity.status(201).body(nuevoAlumno);
    }

    @PutMapping("/editar-alumno/{id}")
    public ResponseEntity<Alumno> actualizarAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
        return alumnoRepository.findById(id).map(alumnoExistente -> {
            alumnoExistente.setNombre(alumno.getNombre());
            alumnoExistente.setApellido(alumno.getApellido());
            alumnoExistente.setEmail(alumno.getEmail());
            alumnoExistente.setNumeroControl(alumno.getNumeroControl());
            alumnoExistente.setCarrera(alumno.getCarrera());
            alumnoExistente.setImagenURL(alumno.getImagenURL());
            Alumno actualizado = alumnoRepository.save(alumnoExistente);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar-alumno/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id) {
        if (!alumnoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        alumnoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
