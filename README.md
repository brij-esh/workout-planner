# workout-planner
Here’s a design document template based on the requirements you provided for the workout planner. This document is structured to cover the key design aspects of the application, including the architecture, database, components, and API design.

---

# **Workout Planner Design Document**

### **1. Introduction**

#### 1.1 Project Overview
The Workout Planner is a full-stack application that enables users to create, manage, and track their workout activities and exercises. The system will store workout plans, exercises, and daily workout progress and display progress through graphs and statistics. The backend will use Java Spring Boot for APIs, MongoDB for storage, and Angular for the frontend. Eventually, the application will be adapted to mobile using Ionic.

#### 1.2 Objectives
- Allow users to create, update, and delete workout plans and exercises.
- Track daily progress for each user.
- View weekly/monthly progress using charts.
- Provide a mobile-friendly interface using Ionic for cross-platform availability.

---

### **2. System Architecture**

#### 2.1 Architecture Diagram

The architecture follows the MVC (Model-View-Controller) pattern and consists of three layers:
1. **Frontend (Angular/Ionic)**: Handles UI, interactions, and sends API requests to the backend.
2. **Backend (Spring Boot)**: Provides APIs for workout and progress management, processes requests, and interacts with MongoDB.
3. **Database (MongoDB)**: Stores data related to users, workout plans, exercises, and progress.

```
+-----------------------+       +------------------------+       +--------------------+
|  Frontend (Angular)    | <---> |   Backend (Spring Boot) | <---> |  Database (MongoDB)|
|  User Interface        |       |   RESTful API           |       |                    |
+-----------------------+       +------------------------+       +--------------------+

```

---

### **3. Functional Requirements**

#### 3.1 User Stories
- As a user, I want to create, view, update, and delete workout plans.
- As a user, I want to create exercises within workout plans.
- As a user, I want to log my daily workout progress.
- As a user, I want to view my progress over time with statistics and graphs.
- As a user, I want the app to be accessible on my mobile device (using Ionic).

#### 3.2 Key Features
- **Workout Plan Management**: Create, update, delete workout plans.
- **Exercise Management**: Add exercises with details like name, sets, reps, and duration.
- **Progress Tracking**: Log daily progress, track calories burned, and view completed exercises.
- **Progress Visualization**: View weekly/monthly progress charts and statistics.
- **Mobile Accessibility**: Use Ionic to access the workout planner from a mobile device.

---

### **4. Non-Functional Requirements**

#### 4.1 Performance
- The system should handle multiple concurrent users.
- Backend APIs should respond within 200ms for standard requests.

#### 4.2 Security
- Data should be secured with JWT-based authentication (optional).

#### 4.3 Usability
- The UI should be responsive and intuitive.
- The mobile version should function seamlessly on both Android and iOS devices.

#### 4.4 Scalability
- The system should be able to handle an increasing number of users and workouts without performance degradation.

---

### **5. Database Design**

#### 5.1 Data Models

- **User**:
  ```json
  [
  {
    "id": "string",
    "username": "string",
    "firstName": "string",
    "lastName": "string",
    "phone": "string",
    "gender": "MALE",
    "age": 0,
    "email": "string",
    "weight": 0,
    "height": 0,
    "goal": "string",
    "profilePicture": {
      "binaryStream": {}
    },
    "workouts": [
      {
        "id": "string",
        "name": "string",
        "description": "string",
        "duration": 0,
        "timeOfDay": "MORNING",
        "exercises": [
          {
            "id": "string",
            "name": "string",
            "description": "string",
            "muscleGroup": "string",
            "equipment": "string",
            "videoUrl": "string",
            "sets": 0,
            "reps": 0,
            "restTime": {
              "seconds": 0,
              "nano": 0,
              "negative": true,
              "zero": true,
              "units": [
                {
                  "dateBased": true,
                  "timeBased": true,
                  "durationEstimated": true
                }
              ]
            }
          }
        ],
        "createdAt": "2024-09-09T18:22:58.792Z",
        "updatedAt": "2024-09-09T18:22:58.792Z"
      }
    ],
    "exercises": [
      {
        "id": "string",
        "name": "string",
        "description": "string",
        "muscleGroup": "string",
        "equipment": "string",
        "videoUrl": "string",
        "sets": 0,
        "reps": 0,
        "restTime": {
          "seconds": 0,
          "nano": 0,
          "negative": true,
          "zero": true,
          "units": [
            {
              "dateBased": true,
              "timeBased": true,
              "durationEstimated": true
            }
          ]
        }
      }
    ],
    "createdAt": "2024-09-09T18:22:58.792Z",
    "updatedAt": "2024-09-09T18:22:58.792Z"
  }
]
  ```

- **WorkoutPlan**:
  ```json
  {
        "id": "string",
        "name": "string",
        "description": "string",
        "duration": 0,
        "timeOfDay": "MORNING",
        "exercises": [
          {
            "id": "string",
            "name": "string",
            "description": "string",
            "muscleGroup": "string",
            "equipment": "string",
            "videoUrl": "string",
            "sets": 0,
            "reps": 0,
            "restTime": {
              "seconds": 0,
              "nano": 0,
              "negative": true,
              "zero": true,
              "units": [
                {
                  "dateBased": true,
                  "timeBased": true,
                  "durationEstimated": true
                }
              ]
            }
          }
        ],
        "createdAt": "2024-09-09T18:22:58.792Z",
        "updatedAt": "2024-09-09T18:22:58.792Z"
      }
  ```

- **Exercise**:
```json
  {
        "id": "string",
        "name": "string",
        "description": "string",
        "muscleGroup": "string",
        "equipment": "string",
        "videoUrl": "string",
        "sets": 0,
        "reps": 0,
        "restTime": {
          "seconds": 0,
          "nano": 0,
          "negative": true,
          "zero": true,
          "units": [
            {
              "dateBased": true,
              "timeBased": true,
              "durationEstimated": true
            }
          ]
        }
      }
  ```

- **Progress**:
  ```json
  {
    "id": "string",
    "userId": "string",
    "date": "date",
    "workoutPlanId": "string",
    "caloriesBurned": "int",
    "exercisesCompleted": [
      {
        "id": "string",
        "name": "string",
        "description": "string",
        "muscleGroup": "string",
        "equipment": "string",
        "videoUrl": "string",
        "sets": 0,
        "reps": 0,
        "restTime": {
          "seconds": 0,
          "nano": 0,
          "negative": true,
          "zero": true,
          "units": [
            {
              "dateBased": true,
              "timeBased": true,
              "durationEstimated": true
            }
          ]
        }
      }
    ]
  }
  ```

#### 5.2 Database Relationships
- **One-to-Many** relationship between `User` and `WorkoutPlan`.
- **One-to-Many** relationship between `WorkoutPlan` and `Exercise`.
- **One-to-Many** relationship between `User` and `Progress`.

---

### **6. API Design**

#### 6.1 API Endpoints

**Workout Plan Management**:
- `POST /api/workouts`: Create a new workout plan.
- `GET /api/workouts`: Retrieve all workout plans for a user.
- `GET /api/workouts/{id}`: Retrieve a specific workout plan.
- `PUT /api/workouts/{id}`: Update a workout plan.
- `DELETE /api/workouts/{id}`: Delete a workout plan.

**Exercise Management**:
- Exercises will be managed as part of workout plans (within the `WorkoutPlan` object).

**Progress Tracking**:
- `POST /api/progress`: Log daily workout progress.
- `GET /api/progress`: Retrieve all progress logs for a user.
- `GET /api/progress/{id}`: Retrieve a specific progress log.

#### 6.2 Error Handling
- Return appropriate HTTP status codes for errors:
  - `400 Bad Request`: Invalid input data.
  - `404 Not Found`: Resource not found.
  - `500 Internal Server Error`: General server error.

---

### **7. Frontend Design**

#### 7.1 Components

- **Workout List Component**:
  - Displays a list of workout plans.
  - Allows users to create or edit a workout.

- **Workout Detail Component**:
  - Displays details of a specific workout plan.
  - Allows users to add, remove, or edit exercises.

- **Progress Tracker Component**:
  - Allows users to log daily workout progress.
  - Displays progress statistics like calories burned and exercises completed.

- **Progress Visualization Component**:
  - Displays progress over time using charts (e.g., `Chart.js`).

#### 7.2 Navigation Flow

1. **Home Page**: Displays the user’s workout plans.
2. **Workout Plan Page**: Allows users to create or edit a workout plan.
3. **Exercise Page**: Allows users to add or edit exercises in a workout plan.
4. **Progress Page**: Displays daily workout logs and progress charts.

---

### **8. Mobile Development (Ionic)**

#### 8.1 Ionic Integration
- Reuse Angular components.
- Adjust UI for mobile using Ionic’s components (e.g., `ion-card`, `ion-button`, etc.).
- Implement mobile-specific features such as push notifications and offline storage (using Ionic Capacitor).

---

### **9. Testing Strategy**

#### 9.1 Backend Testing
- Unit testing with JUnit for API endpoints.
- Integration testing to validate MongoDB interactions.

#### 9.2 Frontend Testing
- Use Angular’s testing framework with Jasmine and Karma.
- Test component functionality and service interactions.

---

### **10. Deployment Strategy**

#### 10.1 Backend Deployment
- Deploy the Spring Boot backend to a cloud platform like AWS, Azure, or Heroku.

#### 10.2 Frontend Deployment
- Host the Angular application on Netlify, Vercel, or any static hosting platform.

#### 10.3 Mobile Deployment
- Use Ionic’s native build tools to package the mobile app for Android and iOS.

---

### **11. Future Enhancements**

- **Social Features**: Allow users to share workouts or progress on social media.
- **Push Notifications**: Implement reminders to motivate users to complete their daily workouts.
- **Offline Mode**: Add offline support so users can log workouts without internet access.

---

### **12. Conclusion**

The Workout Planner provides users with a complete platform for managing workout plans, tracking progress, and visualizing fitness improvements over time. The app is designed to be scalable, responsive, and accessible on both web and mobile platforms.

---

This design document outlines the technical architecture, functional requirements, and detailed components for your workout planner project.