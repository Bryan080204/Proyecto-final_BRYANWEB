package com.bryan.alumnos.repository;



    import org.springframework.data.jpa.repository.JpaRepository;

import com.bryan.alumnos.model.Alumno;



  public interface AlumnoRepository extends JpaRepository<Alumno, Long> { 
    

}
 