<template>
  <ion-page>
    <ion-content :fullscreen="true" class="background">
      <!-- Círculos decorativos -->
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
      <div class="circle circle-4"></div>
      <div class="circle circle-5"></div>
      <div class="circle circle-6"></div>

      <div class="container">
        <div class="form-container">
          <h1>Crear Cuenta</h1>

          <ion-list class="custom-list">
            <ion-item>
              <ion-input v-model="nombre" label="Nombre" placeholder="Ej. Natalia"></ion-input>
            </ion-item>
            <ion-item>
              <ion-input v-model="apellido" label="Apellido" placeholder="Ej. Pérez"></ion-input>
            </ion-item>
            <ion-item>
              <ion-input v-model="carrera" label="Carrera" placeholder="Ej. Ing. Sistemas"></ion-input>
            </ion-item>
            <ion-item>
              <ion-input v-model="semestre" label="Semestre" placeholder="Ej. 5"></ion-input>
            </ion-item>
            <ion-item>
              <ion-input v-model="email" label="Correo electrónico" type="email" placeholder="correo@ejemplo.com"></ion-input>
            </ion-item>
            <ion-item>
              <ion-input v-model="password" label="Contraseña" type="password" placeholder="******"></ion-input>
            </ion-item>
            <ion-item>
              <ion-input v-model="confirmPassword" label="Confirmar contraseña" type="password" placeholder="******"></ion-input>
            </ion-item>

            <ion-button expand="block" class="custom-button" @click="registrarUsuario">
              Registrar
            </ion-button>
          </ion-list>

          <ion-alert
            :is-open="alerta"
            :header="titulo"
            :message="mensaje"
            :buttons="['Aceptar']"
            @didDismiss="alerta = false"
          ></ion-alert>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script>
import {
  IonPage,
  IonContent,
  IonItem,
  IonList,
  IonInput,
  IonButton,
  IonAlert
} from '@ionic/vue';

import { ref as dbRef, set, getDatabase } from 'firebase/database';
import { getAuth, createUserWithEmailAndPassword } from 'firebase/auth';

export default {
  name: 'RegisterPage',
  components: {
    IonPage,
    IonContent,
    IonItem,
    IonList,
    IonInput,
    IonButton,
    IonAlert
  },
  data() {
    return {
      nombre: '',
      apellido: '',
      carrera: '',
      semestre: '',
      email: '',
      password: '',
      confirmPassword: '',
      alerta: false,
      titulo: '',
      mensaje: ''
    };
  },
  methods: {
    registrarUsuario() {
      if (this.password !== this.confirmPassword) {
        this.titulo = 'Error';
        this.mensaje = 'Las contraseñas no coinciden.';
        this.alerta = true;
        return;
      }

      const auth = getAuth();
      const db = getDatabase();

      createUserWithEmailAndPassword(auth, this.email, this.password)
        .then((userCredential) => {
          const uid = userCredential.user.uid;
          const usuarioRef = dbRef(db, 'usuarios/' + uid);
          set(usuarioRef, {
            nombre: this.nombre + ' ' + this.apellido,
            correo: this.email,
            carrera: this.carrera,
            semestre: this.semestre
          });

          this.titulo = 'Registro exitoso';
          this.mensaje = 'Tu cuenta fue creada. Ahora puedes iniciar sesión.';
          this.alerta = true;
          this.$router.push('/login');
        })
        .catch((error) => {
          console.error(error);
          this.titulo = 'Error';
          this.mensaje = 'No se pudo registrar el usuario.';
          this.alerta = true;
        });
    }
  }
};
</script>

<style scoped>
.background {
  --background: #ffffff;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
}

.form-container {
  background-color: #f9f9f9;
  padding: 40px 50px;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.12);
  max-width: 500px;
  width: 100%;
  text-align: center;
  z-index: 1;
}

h1 {
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 24px;
  color: #000;
}

.custom-button {
  --background: #be3455;
  font-weight: bold;
  font-size: 15px;
  border-radius: 14px;
  padding: 10px;
  text-transform: uppercase;
}

.custom-list {
  background: white;
  border-radius: 14px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
  padding: 16px;
}

.circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.4;
  z-index: 0;
}
.circle-1 {
  top: -60px;
  left: -60px;
  width: 160px;
  height: 160px;
  background-color: #04d2f6;
}
.circle-2 {
  top: 30px;
  right: -60px;
  width: 130px;
  height: 130px;
  background-color: #0ad77a;
}
.circle-3 {
  bottom: -40px;
  left: -40px;
  width: 200px;
  height: 200px;
  background-color: #149dce;
}
.circle-4 {
  bottom: 0;
  right: -30px;
  width: 120px;
  height: 120px;
  background-color: #7e2bc7;
}
.circle-5 {
  top: 200px;
  right: 30px;
  width: 100px;
  height: 100px;
  background-color: #ffcb6b;
}
.circle-6 {
  bottom: 60px;
  left: 80px;
  width: 90px;
  height: 90px;
  background-color: #f88bbb;
}
</style>
