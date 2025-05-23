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

      <!-- Contenedor centrado -->
      <div class="container">
        <div class="form-container">
          <h1>Agregar Claves</h1>
          <ion-list class="custom-list">
            <ion-item>
              <ion-label position="stacked">Estatus</ion-label>
              <ion-input v-model="estatus" placeholder="Ej: activa" />
            </ion-item>

            <ion-button expand="block" class="custom-button" @click="agregarClave">
              Agregar Clave
            </ion-button>
          </ion-list>

          <ion-alert
            :is-open="alerta"
            header="¡Éxito!"
            message="La clave fue registrada automáticamente"
            :buttons="['Aceptar']"
            @didDismiss="alerta = false"
          />
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
  IonLabel,
  IonInput,
  IonList,
  IonButton,
  IonAlert
} from '@ionic/vue';

import { ref, push, set, child, get } from 'firebase/database';
import { getAuth } from 'firebase/auth';
import { db } from '../router/firebase';

export default {
  name: 'Tab3Page',
  components: {
    IonPage,
    IonContent,
    IonItem,
    IonLabel,
    IonInput,
    IonList,
    IonButton,
    IonAlert
  },
  data() {
    return {
      estatus: '',
      alerta: false
    };
  },
  methods: {
    async agregarClave() {
      try {
        const auth = getAuth();
        const usuarioActual = auth.currentUser;
        if (!usuarioActual) {
          throw new Error("No hay usuario logueado");
        }

        const uid = usuarioActual.uid;
        const usuarioSnap = await get(child(ref(db), `usuarios/${uid}`));
        const nombre = usuarioSnap.exists() ? usuarioSnap.val().nombre : 'Desconocido';

        const clavesRef = ref(db, 'claves');
        const nuevaClaveRef = push(clavesRef);
        await set(nuevaClaveRef, {
          estatus: this.estatus,
          usuario: nombre
        });

        this.estatus = '';
        this.alerta = true;
      } catch (error) {
        console.error('Error al agregar clave:', error);
      }
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
  max-width: 480px;
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
