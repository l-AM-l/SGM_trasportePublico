// firebase.js
import { initializeApp } from "firebase/app";
import { getDatabase } from "firebase/database";
import { getAuth } from "firebase/auth";

// Tu configuración de Firebase
const firebaseConfig = {
  apiKey: "AIzaSyA9k_Ypxc6sbGQK9DY0oAVi1dEz8XUfncQ",
  authDomain: "sgmtransporte-9bb14.firebaseapp.com",
  databaseURL: "https://sgmtransporte-9bb14-default-rtdb.firebaseio.com",
  projectId: "sgmtransporte-9bb14",
  storageBucket: "sgmtransporte-9bb14.firebasestorage.app",
  messagingSenderId: "236783977789",
  appId: "1:236783977789:web:238947f90de07c83b2f154"
};

// Inicializar la app
const app = initializeApp(firebaseConfig);

// Exportar la base de datos
const db = getDatabase(app);
const auth = getAuth(app);

export { db , auth};
