<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const alumnos = ref([]);

// Función para cargar los alumnos
const cargarAlumnos = async () => {
  const response = await axios.get('http://localhost:8081/alumnos/traer-alumnos');
  alumnos.value = response.data;
};

onMounted(() => {
  cargarAlumnos();
});

// Campos del formulario
const nuevoAlumno = ref({
  id: '',
  nombre: '',
  apellido: '',
  email: '',
  carrera: '',
  imagenURL: '',
  numeroControl: ''
});

// Agregar nuevo alumno
const agregarAlumno = async () => {
  try {
    // Verificar los datos antes de enviarlos
    console.log('Datos del nuevo alumno:', nuevoAlumno.value);

    // Realizar la solicitud POST para agregar el alumno
    const response = await axios.post('http://localhost:8081/alumnos/insertar-alumnos', nuevoAlumno.value);

    // Si la respuesta es exitosa, recargar la lista de alumnos
    if (response.status === 200) {
      console.log('Alumno agregado con éxito:', response.data);
      await cargarAlumnos(); // Recargar la tabla
      // Limpiar formulario
      nuevoAlumno.value = {
        id: '',
        nombre: '',
        apellido: '',
        email: '',
        carrera: '',
        imagenURL: '',
        numeroControl: ''
      };
    } else {
      console.error('Error al agregar alumno:', response.statusText);
    }
  } catch (error) {
    console.error('Error al agregar alumno:', error);
  }
};
</script>

<template>
  <div class="container mt-4">
    <h2 class="mb-3">Registrar Alumno</h2>

    <!-- Formulario para agregar un nuevo alumno -->
    <form @submit.prevent="agregarAlumno" class="mb-5">
      <div class="mb-3">
        <label>ID</label>
        <input v-model="nuevoAlumno.id" type="text" class="form-control" />
      </div>
      <div class="mb-3">
        <label>Nombre</label>
        <input v-model="nuevoAlumno.nombre" type="text" class="form-control" />
      </div>
      <div class="mb-3">
        <label>Apellido</label>
        <input v-model="nuevoAlumno.apellido" type="text" class="form-control" />
      </div>
      <div class="mb-3">
        <label>Email</label>
        <input v-model="nuevoAlumno.email" type="email" class="form-control" />
      </div>
      <div class="mb-3">
        <label>Carrera</label>
        <input v-model="nuevoAlumno.carrera" type="text" class="form-control" />
      </div>
      <div class="mb-3">
        <label>Imagen URL</label>
        <input v-model="nuevoAlumno.imagenURL" type="text" class="form-control" />
      </div>
      <div class="mb-3">
        <label>Número de Control</label>
        <input v-model="nuevoAlumno.numeroControl" type="text" class="form-control" />
      </div>
      <button type="submit" class="btn btn-success">
        <i class="fas fa-save"></i> Agregar Alumno
      </button>
    </form>

    <!-- Tabla de alumnos -->
    <h2>Tabla de alumnos</h2>
    <table class="table table-bordered">
      <thead class="table-light">
        <tr>
          <th>ID</th>
          <th>Nombre</th>
          <th>Apellido</th>
          <th>Email</th>
          <th>Carrera</th>
          <th>Imagen</th>
          <th>Número de Control</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="alumno in alumnos" :key="alumno.id">
          <td>{{ alumno.id }}</td>
          <td>{{ alumno.nombre }}</td>
          <td>{{ alumno.apellido }}</td>
          <td>{{ alumno.email }}</td>
          <td>{{ alumno.carrera }}</td>
          <td>
            <img :src="alumno.imagenURL" alt="Imagen de Alumno" width="50" />
          </td>
          <td>{{ alumno.numeroControl }}</td>
          <td>
            <div class="d-flex gap-2">
              <button class="btn btn-warning btn-sm">
                <i class="fas fa-edit"></i> Editar
              </button>
              <button class="btn btn-danger btn-sm">
                <i class="fas fa-trash-alt"></i> Eliminar
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
form .form-control {
  border-radius: 0.375rem;
}
</style>
