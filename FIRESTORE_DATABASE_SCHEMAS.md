# Firebase Firestore Collection Schema: Infinite ASELaR Exams

To handle an infinite repository of REBT (Reglamento Electrotécnico de Baja Tensión) and ASELaR certification exam questions gathered from the web and online forums, a scalable, production-ready Firebase Firestore schema is outlined below.

This structure allows:
1. **Dynamic Addition**: Continuous updates of exams without redeploying the Android application.
2. **Infinite Pagination**: Loading questions on-demand via cursors.
3. **Optimized Storage**: Keeping metadata independent of large question blocks.
4. **Server-Side Grading**: Automated, tamper-proof score verification using Firebase Cloud Functions.

---

## 1. Collection Structure Overview

```
- /active_modules (Collection)
   - [moduleId] (Document) -> Meta information about a particular exam.
      - /questions (Subcollection)
         - [questionId] (Document) -> Individual multiple-choice questions.

- /user_statistics (Collection)
   - [userId] (Document) -> General training progress stats.
      - /completed_exams (Subcollection)
         - [attemptId] (Document) -> Historical results with answers.
```

---

## 2. Document Schemas

### A. Collection: `active_modules`
*Stores general metadata for lists of study subjects or exam editions.*

**Document ID**: `itc_bt_10_dwellings` or `aselar_castillayleon_2024`

```json
{
  "id": "aselar_castillayleon_2024",
  "title": "Examen Oficial Castilla y León 2024",
  "description": "Examen completo con trampas administrativas oficiales sobre esquemas unifilares y CGMP.",
  "category": "Examen Oficial",
  "questionCount": 40,
  "difficulty": "Difícil",
  "isPremium": true,
  "premiumTierRequired": "premium_full",
  "createdAt": "2026-06-18T10:00:00Z",
  "lastUpdate": "2026-06-18T12:00:00Z"
}
```

---

### B. Subcollection: `active_modules/{moduleId}/questions`
*Contains the individual questions. Each document holds a single multiple-choice question.*

**Document ID**: `q_001` to `q_040`

```json
{
  "id": "q_001",
  "questionText": "¿Qué tipo de instalador de Baja Tensión (IBT) es obligatorio para la legalización de una instalación de recarga de vehículos eléctricos (ITC-BT-52) en viviendas unifamiliares?",
  "options": [
    "Un instalador de Categoría Básica (IBTB) únicamente.",
    "No se necesita instalador, un electricista en régimen autónomo general es suficiente.",
    "Un instalador de Categoría Especialista (IBTE) de forma obligatoria.",
    "Cualquier técnico de grado medio con titulación de FP1 general."
  ],
  "correctAnswerIndex": 2, 
  "explanation": "Conforme a la ITC-BT-52, toda instalación que incluya infraestructura de recarga de vehículos eléctricos exige que el instalador habilitado sea de Categoría Especialista (IBTE).",
  "associatedItc": "ITC-BT-52",
  "examReference": "Convocatoria ASELaR / Foros de Industria Castellana",
  "difficultyRating": 3,
  "pointsValue": 15
}
```

---

### C. Collection: `user_statistics`
*Stores aggregate metrics of user prep progress.*

**Document ID**: `user_auth_id_1234`

```json
{
  "userId": "user_auth_id_1234",
  "email": "jj.terapias@gmail.com",
  "totalAnswered": 348,
  "correctAnswered": 298,
  "activeStreak": 7,
  "globalXp": 5950,
  "premiumPlans": {
    "isProActive": true,
    "lastBillingSync": "2026-06-18T12:11:21Z"
  }
}
```

---

### D. Subcollection: `user_statistics/{userId}/completed_exams`
*Keeps the complete records of student attempts to display custom reports, progress analytics, and correct mistakes.*

**Document ID**: `attempt_uuid_98372`

```json
{
  "id": "attempt_uuid_98372",
  "moduleId": "aselar_castillayleon_2024",
  "moduleTitle": "Examen Oficial Castilla y León 2024",
  "startedAt": "2026-06-18T10:30:00Z",
  "finishedAt": "2026-06-18T10:55:00Z",
  "totalQuestionsCount": 40,
  "correctAnswersCount": 38,
  "scorePercentage": 95.0,
  "isPassed": true,
  "userAnswers": {
    "q_001": 2,
    "q_002": 0,
    "q_003": 1
  }
}
```

---

## 3. Server-Side Automated Scoring and Validation Logic
*To prevent client-side modifications and ensure absolute security prior to uploading to Google Play, scoring can be safely computed in a **Firebase Cloud Function**. Below is the TypeScript implementation.*

```typescript
import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';

admin.initializeApp();

/**
 * Cloud Function to securely grade an exam attempt.
 * Receives the collection mapping of questionId -> userSelectedAnswerIndex.
 * Queries correct answers from the protected subcollection, scores, and updates statistics.
 */
export const gradingExamAttempt = functions.https.onCall(async (data, context) => {
  // Ensure the user is authenticated via Google Play services / Firebase Auth
  if (!context.auth) {
    throw new functions.https.HttpsError(
      'unauthenticated', 
      'Se requiere autenticación para poder registrar o calificar un examen.'
    );
  }

  const { moduleId, userAnswers } = data;
  if (!moduleId || !userAnswers) {
    throw new functions.https.HttpsError(
      'invalid-argument', 
      'Parámetros perdidos o estructurados erróneamente.'
    );
  }

  const userId = context.auth.uid;
  const db = admin.firestore();

  // 1. Fetch all correct answers for this module
  const questionsSnapshot = await db.collection('active_modules')
    .doc(moduleId)
    .collection('questions')
    .get();

  let correctCount = 0;
  const totalQuestions = questionsSnapshot.size;

  questionsSnapshot.forEach((doc) => {
    const questionId = doc.id;
    const correctIndex = doc.data().correctAnswerIndex;
    const userSelected = userAnswers[questionId];

    if (userSelected !== undefined && userSelected === correctIndex) {
      correctCount++;
    }
  });

  const percentScore = totalQuestions > 0 ? (correctCount / totalQuestions) * 100.0 : 0;
  const isPassed = percentScore >= 75.0; // Standard Spanish Industrial Habilitation threshold (75%)

  // 2. Perform transactional update of global user statistics
  const userStatsRef = db.collection('user_statistics').doc(userId);
  await db.runTransaction(async (transaction) => {
    const userStatsDoc = await transaction.get(userStatsRef);
    let originalXp = 0;
    
    if (userStatsDoc.exists) {
      originalXp = userStatsDoc.data()?.globalXp || 0;
    }

    const xpEarned = (correctCount * 15) + (isPassed ? 100 : 0);
    const newXp = originalXp + xpEarned;

    transaction.set(userStatsRef, {
      totalAnswered: admin.firestore.FieldValue.increment(totalQuestions),
      correctAnswered: admin.firestore.FieldValue.increment(correctCount),
      globalXp: newXp
    }, { merge: true });
  });

  // 3. Log results inside historical collection
  const attemptRef = db.collection('user_statistics')
    .doc(userId)
    .collection('completed_exams')
    .doc();

  const attemptResult = {
    id: attemptRef.id,
    moduleId: moduleId,
    startedAt: admin.firestore.FieldValue.serverTimestamp(),
    totalQuestionsCount: totalQuestions,
    correctAnswersCount: correctCount,
    scorePercentage: percentScore,
    isPassed: isPassed
  };

  await attemptRef.set(attemptResult);

  return attemptResult;
});
```
