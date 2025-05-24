<template>
  <ion-page>
    <ion-tabs>
      <ion-router-outlet></ion-router-outlet>

      <ion-tab-bar slot="bottom">
        <ion-tab-button tab="tab1" href="/tabs/tab1">
          <ion-label>Claves</ion-label>
        </ion-tab-button>
        <ion-tab-button tab="tab2" href="/tabs/tab2">
          <ion-label>Usuarios</ion-label>
        </ion-tab-button>
        <ion-tab-button tab="tab3" href="/tabs/tab3">
          <ion-label>Agregar claves</ion-label>
        </ion-tab-button>
      </ion-tab-bar>
    </ion-tabs>

    <ion-button
      class="logout-button"
      @click="cerrarSesion"
    >
      Cerrar sesión
    </ion-button>
  </ion-page>
</template>

<script>
import {
  IonPage,
  IonTabs,
  IonRouterOutlet,
  IonTabBar,
  IonTabButton,
  IonLabel,
  IonButton
} from '@ionic/vue';

import { getAuth, signOut } from 'firebase/auth';

export default {
  name: 'TabsPage',
  components: {
    IonPage,
    IonTabs,
    IonRouterOutlet,
    IonTabBar,
    IonTabButton,
    IonLabel,
    IonButton
  },
  methods: {
    cerrarSesion() {
      const auth = getAuth();
      signOut(auth)
        .then(() => {
          this.$router.push('/login');
        })
        .catch((error) => {
          console.error('Error al cerrar sesión:', error);
        });
    }
  }
};
</script>

<style scoped>
/* Estilo para las letras del Tab */
ion-tab-button ion-label {
  color: rgb(215, 215, 215) !important;
  font-weight: bold;
  font-size: 16px;
}

/* Botón de cerrar sesión */
.logout-button {
  position: fixed;
  bottom: 80px;
  right: 20px;
  z-index: 999;
  background-color: rgb(208, 67, 67);
  color: white;
}
</style>
