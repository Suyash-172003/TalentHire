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
import ExamInstructions from "./pages/CandidateDashboard/mcq/ExamInstructions";
import McqExam from "./pages/CandidateDashboard/mcq/McqExam";
import ProtectedRoute from "./pages/ProtectedRoute/ProtectedRoute";
import ForgotPassword from "./pages/Login/ForgotPassword";
import VerifyOtp from "./pages/Login/VerifyOtp";
import ResetPassword from "./pages/Login/ResetPassword";
import CandidateAssessment from "./pages/Assessment/CandidateAssessments";
import CandidateInterviews from "./pages/CandidateDashboard/CandidateInterviews";
import Payment from "./pages/RecruiterDashboard/Payment";


function App() {

  return (

    <BrowserRouter>

      <Routes>

        {/* Public Routes */}

        <Route path="/" element={<Landing />} />

        <Route path="/login" element={<Login />} />

        <Route path="/signup" element={<Signup />} />

        {/* Protected Routes */}


        <Route path="/payment" element={<ProtectedRoute><Payment /></ProtectedRoute>} />

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
              <ExamInstructions />
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



        <Route
          path="/forgot-password"
          element={<ForgotPassword />}
        />


        <Route
          path="/verify-otp"
          element={<VerifyOtp />}
        />


        <Route
          path="/reset-password"
          element={<ResetPassword />}
        />

        <Route
          path="/candidate/assessments"
          element={<CandidateAssessment />}
        />


        <Route
          path="/candidate/interviews"
          element={<CandidateInterviews />}
        />


      </Routes>

    </BrowserRouter>

  );

}

export default App;