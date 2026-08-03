import { BrowserRouter, Routes, Route } from "react-router-dom";

import Landing from "./pages/Landing/Landing";
import Login from "./pages/Login/Login";
import Signup from "./pages/Signup/Signup";
import CandidateDashboard from "./pages/CandidateDashboard/CandidateDashboard";
import RecruiterDashboard from "./pages/RecruiterDashboard/RecruiterDashboard";
import CreateJob from "./pages/RecruiterDashboard/CreateJob";
import EditJob from "./pages/RecruiterDashboard/EditJob";
import Applicants from "./pages/RecruiterDashboard/Applicants";
import CodingAssessment from "./pages/Assessment/CodingAssessment";
import RecruiterAssessmentPage from "./pages/RecruiterDashboard/RecruiterAssessmentPage";
import McqInstructions from "./pages/CandidateDashboard/mcq/McqInstructions";
import McqExam from "./pages/CandidateDashboard/mcq/McqExam";
import ProtectedRoute from "./pages/ProtectedRoute/ProtectedRoute";

function App() {

  return (

    <BrowserRouter>

      <Routes>

        {/* Public Routes */}

        <Route path="/" element={<Landing />} />

        <Route path="/login" element={<Login />} />

        <Route path="/signup" element={<Signup />} />

        {/* Protected Routes */}

        <Route
          path="/candidate/dashboard"
          element={
            <ProtectedRoute>
              <CandidateDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/recruiter/dashboard"
          element={
            <ProtectedRoute>
              <RecruiterDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/recruiter/create-job"
          element={
            <ProtectedRoute>
              <CreateJob />
            </ProtectedRoute>
          }
        />

        <Route
          path="/recruiter/edit-job/:jobId"
          element={
            <ProtectedRoute>
              <EditJob />
            </ProtectedRoute>
          }
        />

        <Route
          path="/recruiter/applicants/:jobId"
          element={
            <ProtectedRoute>
              <Applicants />
            </ProtectedRoute>
          }
        />

        <Route
          path="/assessment/:assessmentId"
          element={
            <ProtectedRoute>
              <CodingAssessment />
            </ProtectedRoute>
          }
        />

        <Route
          path="/recruiter/assessment/:jobId"
          element={
            <ProtectedRoute>
              <RecruiterAssessmentPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/candidate/mcq/:assessmentId"
          element={
            <ProtectedRoute>
              <McqInstructions />
            </ProtectedRoute>
          }
        />

        <Route
          path="/candidate/mcq/exam/:attemptId"
          element={
            <ProtectedRoute>
              <McqExam />
            </ProtectedRoute>
          }
        />

      </Routes>

    </BrowserRouter>

  );

}

export default App;