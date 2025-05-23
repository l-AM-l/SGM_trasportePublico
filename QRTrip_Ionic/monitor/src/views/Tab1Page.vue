<template>
  <ion-page>
    <ion-content :fullscreen="true">
      <!-- CÍRCULOS -->
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="circle circle-3"></div>
      <div class="circle circle-4"></div>
      <div class="circle circle-5"></div>
      <div class="circle circle-6"></div>

      <!-- CONTENEDOR FLEX PARA CENTRAR -->
      <div class="center-wrapper">
        <div class="table-container">
          <h1>Lista de Claves</h1>
          <ion-grid>
            <ion-row class="header-row">
              <ion-col>Clave</ion-col>
              <ion-col>Estatus</ion-col>
              <ion-col>Usuario</ion-col>
              <ion-col>Acciones</ion-col>
            </ion-row>
            <ion-row v-for="(item, index) in listaClaves" :key="index">
              <ion-col>{{ listaKeys[index] }}</ion-col>
              <ion-col>{{ item.estatus }}</ion-col>
              <ion-col>{{ item.usuario }}</ion-col>
              <ion-col>
                <ion-button class="eliminar-button" color="danger" size="small" @click="borrarClave(listaKeys[index])">
                  Eliminar
                </ion-button>
              </ion-col>
            </ion-row>
          </ion-grid>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script>
import {
  IonPage,
  IonContent,
  IonCol,
  IonGrid,
  IonRow,
  IonButton
} from '@ionic/vue';
import { ref, onMounted } from 'vue';
import { db } from '../router/firebase.js';
import { ref as dbRef, onValue, remove } from 'firebase/database';

export default {
  name: 'Tab1Page',
  components: {
    IonPage,
    IonContent,
    IonCol,
    IonGrid,
    IonRow,
    IonButton
  },
  setup() {
    const listaClaves = ref([]);
    const listaKeys = ref([]);

    const cargarClaves = () => {
      const clavesRef = dbRef(db, 'claves/');
      onValue(clavesRef, (snapshot) => {
        const claves = [];
        const keys = [];
        snapshot.forEach(element => {
          claves.push(element.val());
          keys.push(element.key);
        });
        listaClaves.value = claves;
        listaKeys.value = keys;
      });
    };

    const borrarClave = async (claveId) => {
      const claveRef = dbRef(db, `claves/${claveId}`);
      await remove(claveRef);
    };

    onMounted(() => {
      cargarClaves();
    });

    return {
      listaClaves,
      listaKeys,
      borrarClave
    };
  }
};
</script>

<style scoped>
/* CÍRCULOS */
.circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.4;
  z-index: 0;
}
.circle-1 { top: -60px; left: -60px; width: 160px; height: 160px; background-color: #04d2f6; }
.circle-2 { top: 30px; right: -60px; width: 130px; height: 130px; background-color: #0ad77a; }
.circle-3 { bottom: -40px; left: -40px; width: 200px; height: 200px; background-color: #149dce; }
.circle-4 { bottom: 0; right: -30px; width: 120px; height: 120px; background-color: #7e2bc7; }
.circle-5 { top: 200px; right: 30px; width: 100px; height: 100px; background-color: #ffcb6b; }
.circle-6 { bottom: 60px; left: 80px; width: 90px; height: 90px; background-color: #f88bbb; }

/* CONTENEDOR CENTRAL */
.center-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  position: relative;
  z-index: 1;
  padding: 20px;
}

/* TARJETA DE CLAVES */
.table-container {
  background-color: #f9f9f9;
  padding: 50px 60px;
  border-radius: 20px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.12);
  max-width: 1000px;
  width: 100%;
  text-align: center;
  font-size: 18px;
}

/* TITULO */
h1 {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 30px;
  color: #000;
}

/* ENCABEZADO */
.header-row ion-col {
  background-color: #be3455;
  font-weight: bold;
  color: #fff;
  font-size: 18px;
}

/* COLUMNAS */
ion-col {
  color: #333;
  font-weight: bold;
  text-align: center;
  border-bottom: 1px solid #ccc;
  padding: 12px 8px;
}

/* BOTÓN */
.eliminar-button {
  font-size: 15px;
  padding: 10px 14px;
  --background: #c62828;
  --color: white;
  --border-radius: 12px;
  text-transform: uppercase;
  font-weight: bold;
}
</style>
