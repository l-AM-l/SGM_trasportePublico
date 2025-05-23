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

      <!-- CONTENEDOR CENTRADO -->
      <div class="container">
        <div class="table-container">
          <h1>Usuarios Registrados</h1>
          <ion-grid>
            <ion-row class="header-row">
              <ion-col>Nombre</ion-col>
              <ion-col>Apellido</ion-col>
              <ion-col>Carrera</ion-col>
              <ion-col>Semestre</ion-col>
              <ion-col>Correo</ion-col>
            </ion-row>
            <ion-row v-for="(usuario, index) in listaUsuarios" :key="index">
              <ion-col>{{ usuario.nombre }}</ion-col>
              <ion-col>{{ usuario.apellido }}</ion-col>
              <ion-col>{{ usuario.carrera }}</ion-col>
              <ion-col>{{ usuario.semestre }}</ion-col>
              <ion-col>{{ usuario.correo }}</ion-col>
            </ion-row>
          </ion-grid>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script setup>
import {
  IonPage,
  IonContent,
  IonGrid,
  IonRow,
  IonCol
} from '@ionic/vue';

import { getDatabase, ref, onValue } from 'firebase/database';
import { onMounted, ref as vueRef } from 'vue';

const listaUsuarios = vueRef([]);

onMounted(() => {
  const db = getDatabase();
  const usuariosRef = ref(db, 'usuarios/');

  onValue(usuariosRef, (snapshot) => {
    const temp = [];
    snapshot.forEach(child => {
      temp.push(child.val());
    });
    listaUsuarios.value = temp;
  });
});
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

/* CENTRADO ABSOLUTO */
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
}

.table-container {
  background-color: #f9f9f9;
  padding: 50px 60px;
  border-radius: 20px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.12);
  max-width: 1100px;
  width: 100%;
  text-align: center;
  font-size: 18px;
  z-index: 1;
}

h1 {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 30px;
  color: #000;
}

.header-row ion-col {
  background-color: #be3455;
  font-weight: bold;
  color: #fff;
  font-size: 18px;
}

ion-col {
  color: #333;
  font-weight: bold;
  text-align: center;
  border-bottom: 1px solid #ccc;
  padding: 12px 8px;
}

/* CÍRCULOS */
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
