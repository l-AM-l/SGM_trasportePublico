<template>
  <ion-page>
    <ion-content :fullscreen="true" :scroll-y="false" class="login-background">
      <!-- CÍRCULOS DECORATIVOS -->
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
      <div class="circle circle-4"></div>
      <div class="circle circle-5"></div>
      <div class="circle circle-6"></div>
      <div class="circle circle-7"></div>

      <div class="login-container">
        <h1 class="title">Bienvenido</h1>
        <p class="subtitle">Inicia sesión</p>

        <ion-list class="form">
          <ion-item lines="inset">
            <ion-label position="floating">Correo</ion-label>
            <ion-input type="email" v-model="email" placeholder="usuario@correo.com"></ion-input>
          </ion-item>

          <ion-item lines="inset">
            <ion-label position="floating">Contraseña</ion-label>
            <ion-input type="password" v-model="password" placeholder="******"></ion-input>
          </ion-item>

          <ion-button expand="block" class="login-button" @click="iniciarSesion">
            Iniciar sesión
          </ion-button>

          <div class="register-text">
            <small>¿No tienes cuenta? 
              <span @click="$router.push('/register')">Crea una</span>
            </small>
          </div>
        </ion-list>

        <ion-alert
          :is-open="alerta"
          :header="titulo"
          :message="mensaje"
          :buttons="['Aceptar']"
          @didDismiss="alerta = false"
        ></ion-alert>
      </div>
    </ion-content>
  </ion-page>
</template>

<script>
import {
  IonPage, IonContent, IonList, IonItem, IonInput, IonLabel, IonButton, IonAlert
} from '@ionic/vue';
import { signInWithEmailAndPassword } from 'firebase/auth';
import { auth } from '../router/firebase';

export default {
  name: 'LoginPage',
  components: {
    IonPage, IonContent, IonList, IonItem, IonInput, IonLabel, IonButton, IonAlert
  },
  data() {
    return {
      email: '',
      password: '',
      alerta: false,
      titulo: '',
      mensaje: ''
    };
  },
  methods: {
    iniciarSesion() {
      signInWithEmailAndPassword(auth, this.email, this.password)
        .then(() => {
          this.$router.push('/tabs/tab1');
        })
        .catch(() => {
          this.titulo = 'Error';
          this.mensaje = 'Correo o contraseña incorrectos.';
          this.alerta = true;
        });
    }
  }
};
</script>

<style scoped>
.login-background {
  --background: #ffffff;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

ion-content::part(scroll) {
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-container {
  z-index: 1;
  width: 90%;
  max-width: 600px;
  padding: 50px 40px;
  text-align: center;
  background-color: white;
  border-radius: 28px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.12);
}

.title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 6px;
}

.subtitle {
  font-size: 17px;
  color: #555;
  margin-bottom: 20px;
}

.form {
  background-color: transparent;
}

.login-button {
  margin-top: 20px;
  --border-radius: 22px;
  --background: #3478f6;
  font-size: 16px;
  font-weight: 500;
}

.register-text {
  margin-top: 12px;
  font-size: 14px;
  color: #555;
}
.register-text span {
  color: #3478f6;
  font-weight: bold;
  cursor: pointer;
}

.circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.4;
  z-index: 0;
}
.circle-1 {
  top: -50px;
  left: -50px;
  width: 160px;
  height: 160px;
  background-color: #04d2f6;
}
.circle-2 {
  top: 50px;
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
  top: 100px;
  bottom: 90px;
  left: 80px;
  width: 90px;
  height: 90px;
  background-color: #f88bbb;
}
.circle-7 {
  bottom: 100px;
  right: 150px;
  width: 70px;
  height: 70px;
  background-color: #b487fd;
}
</style>
