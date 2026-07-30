import { BrowserRouter, Routes, Route } from "react-router-dom";

import Landing from "./pages/Landing/Landing";
import Login from "./pages/Login/Login";
import Signup from "./pages/Signup/Signup";
import CandidateDashboard from "./pages/CandidateDashboard/CandidateDashboard";
import RecruiterDashboard from "./pages/RecruiterDashboard/RecruiterDashboard";
import CreateJob from "./pages/RecruiterDashboard/CreateJob";
import EditJob from "./pages/RecruiterDashboard/EditJob";
import Applicants from "./pages/RecruiterDashboard/Applicants"
import CodingAssessment from "./pages/Assessment/CodingAssessment";


function App() {

  return (

    <BrowserRouter>

      <Routes>

        <Route path="/" element={<Landing />} />

        <Route path="/login" element={<Login />} />

        <Route path="/signup" element={<Signup />} />

        <Route path="/candidate/dashboard" element={<CandidateDashboard />} />

        <Route path="/recruiter/dashboard" element={<RecruiterDashboard />} />

        <Route path="/recruiter/create-job" element={<CreateJob />} />

        <Route path="/recruiter/edit-job/:jobId" element={<EditJob />} />

         <Route path="/recruiter/applicants/:jobId" element={<Applicants />}/>

         <Route path="/assessment/:assessmentId" element={<CodingAssessment />}
/>

  

         
      </Routes>

    </BrowserRouter>

  );


  // return <CandidateDashboard />

}

export default App;