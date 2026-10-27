// Ezt az egy fájlt kell átírnod, ha más címre költözik a backend vagy a Firebase-projekt.

// A backend (Spring Boot) címe, záró perjel nélkül.
// Helyben a gépedről megnyitva a localhost-ot használja, élesben az éles címet.
const API_BASE = ['localhost', '127.0.0.1'].includes(location.hostname)
  ? 'http://localhost:9090'
  : 'https://IDE-A-BACKEND-CIME.onrender.com';

// Firebase konzol → Project settings → Your apps → Web app → firebaseConfig
const FIREBASE_CONFIG = {
  apiKey: "AIzaSyCfCet7ctRXM2lbIIaIbeEsVYNVhEwX4uo",
  authDomain: "konyvklub-e8095.firebaseapp.com",
  projectId: "konyvklub-e8095"
};
