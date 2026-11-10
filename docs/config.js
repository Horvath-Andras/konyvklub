const API_BASE = ['localhost', '127.0.0.1'].includes(location.hostname)
  ? 'http://localhost:9090'
  : 'https://konyvklub.onrender.com';

const FIREBASE_CONFIG = {
  apiKey: "AIzaSyAMEIudAq-r7xlTy1Vgm9Nbhbdtz7mgv8A",
  authDomain: "konyvklubbackend.firebaseapp.com",
  projectId: "konyvklubbackend"
};
