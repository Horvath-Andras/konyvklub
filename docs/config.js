// Ezt az egy fájlt kell átírnod, ha más címre költözik a backend vagy a Firebase-projekt.

// A backend (Spring Boot) címe, záró perjel nélkül.
// Helyben a gépedről megnyitva a localhost-ot használja, élesben az éles címet.
const API_BASE = ['localhost', '127.0.0.1'].includes(location.hostname)
  ? 'http://localhost:9090'
  : 'https://konyvklub.onrender.com';

// Firebase konzol → Project settings → Your apps → Web app → firebaseConfig
const FIREBASE_CONFIG = {
  apiKey: "AIzaSyAMEIudAq-r7xlTy1Vgm9Nbhbdtz7mgv8A",
  authDomain: "konyvklubbackend.firebaseapp.com",
  projectId: "konyvklubbackend"
};
