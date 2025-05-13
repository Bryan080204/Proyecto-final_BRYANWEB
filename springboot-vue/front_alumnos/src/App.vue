<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const alumnos = ref([]);
const alumnoSeleccionado = ref(null);

const nuevoAlumno = ref({
  id: '',
  nombre: '',
  apellido: '',
  email: '',
  carrera: '',
  imagenURL: '',
  numeroControl: ''
});

const cargarAlumnos = async () => {
  try {
    const response = await axios.get('http://localhost:8081/alumnos/traer-alumnos');
    alumnos.value = response.data;
  } catch (error) {
    console.error('Error al cargar los alumnos:', error);
  }
};

onMounted(() => {
  cargarAlumnos();
});

const editarAlumno = (alumno) => {
  alumnoSeleccionado.value = alumno;
  nuevoAlumno.value = { ...alumno };
};

const agregarOEditarAlumno = async () => {
  try {
    let response;
    if (alumnoSeleccionado.value) {
      response = await axios.put(`http://localhost:8081/alumnos/editar-alumno/${nuevoAlumno.value.id}`, nuevoAlumno.value);
    } else {
      response = await axios.post('http://localhost:8081/alumnos/insertar-alumno', nuevoAlumno.value);
    }

    if (response.status === 200 || response.status === 201) {
      await cargarAlumnos();
      nuevoAlumno.value = {
        id: '',
        nombre: '',
        apellido: '',
        email: '',
        carrera: '',
        imagenURL: '',
        numeroControl: ''
      };
      alumnoSeleccionado.value = null;
    }
  } catch (error) {
    console.error('Error al guardar el alumno:', error);
  }
};

const eliminarAlumno = async (id) => {
  try {
    const response = await axios.delete(`http://localhost:8081/alumnos/eliminar-alumno/${id}`);
    if (response.status === 200) {
      await cargarAlumnos();
    }
  } catch (error) {
    console.error('Error al eliminar alumno:', error);
  }
};
</script>

<template>
  <div class="container my-5">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card p-4 shadow-sm">
          <h3 class="text-center mb-4 text-primary">
            <i class="bi bi-person-lines-fill"></i>
            {{ alumnoSeleccionado ? 'Editar Alumno' : 'Registrar Alumno' }}
          </h3>

          <form @submit.prevent="agregarOEditarAlumno">
            <div class="row g-3">
              <div class="col-md-6">
                <label class="form-label"><i class="bi bi-key"></i> ID</label>
                <input v-model="nuevoAlumno.id" type="number" class="form-control" :disabled="alumnoSeleccionado" required />
              </div>
              <div class="col-md-6">
                <label class="form-label"><i class="bi bi-person-fill"></i> Nombre</label>
                <input v-model="nuevoAlumno.nombre" type="text" class="form-control" required />
              </div>
              <div class="col-md-6">
                <label class="form-label"><i class="bi bi-person-fill"></i> Apellido</label>
                <input v-model="nuevoAlumno.apellido" type="text" class="form-control" required />
              </div>
              <div class="col-md-6">
                <label class="form-label"><i class="bi bi-envelope-fill"></i> Email</label>
                <input v-model="nuevoAlumno.email" type="email" class="form-control" required />
              </div>
              <div class="col-md-6">
                <label class="form-label"><i class="bi bi-mortarboard-fill"></i> Carrera</label>
                <input v-model="nuevoAlumno.carrera" type="text" class="form-control" required />
              </div>
              <div class="col-md-6">
                <label class="form-label"><i class="bi bi-image-fill"></i> Imagen URL</label>
                <input v-model="nuevoAlumno.imagenURL" type="text" class="form-control" required />
              </div>
              <div class="col-md-12">
                <label class="form-label"><i class="bi bi-hash"></i> Número de Control</label>
                <input v-model="nuevoAlumno.numeroControl" type="text" class="form-control" required />
              </div>
            </div>
            <button type="submit" class="btn btn-primary w-100 mt-4">
              <i class="bi bi-check-circle-fill"></i>
              {{ alumnoSeleccionado ? 'Actualizar Alumno' : 'Agregar Alumno' }}
            </button>
          </form>
        </div>
      </div>
    </div>

    <h3 class="mt-5 text-center text-secondary"><i class="bi bi-table"></i> Tabla de Alumnos</h3>
    <div class="table-responsive mt-3">
      <table class="table table-bordered table-hover align-middle">
        <thead class="table-primary text-center">
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
        <tbody class="text-center">
          <tr v-for="alumno in alumnos" :key="alumno.id">
            <td>{{ alumno.id }}</td>
            <td>{{ alumno.nombre }}</td>
            <td>{{ alumno.apellido }}</td>
            <td>{{ alumno.email }}</td>
            <td>{{ alumno.carrera }}</td>
            <td><img :src="alumno.imagenURL" alt="Foto" width="50" class="rounded" /></td>
            <td>{{ alumno.numeroControl }}</td>
            <td>
              <button class="btn btn-sm btn-warning me-1" @click="editarAlumno(alumno)">
                <i class="bi bi-pencil-fill"></i>
              </button>
              <button class="btn btn-sm btn-danger" @click="eliminarAlumno(alumno.id)">
                <i class="bi bi-trash-fill"></i>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.card {
  border-radius: 15px;
  background-color: #f8f9fa;
}

.table th,
.table td {
  vertical-align: middle;
}

img {
  object-fit: cover;
}
</style>
