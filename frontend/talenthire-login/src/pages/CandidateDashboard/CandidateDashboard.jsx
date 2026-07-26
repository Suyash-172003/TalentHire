import "./CandidateDashboard.css";


function CandidateDashboard(){

return(

<div className="dashboard">


{/* Sidebar */}

<aside className="sidebar">

<h2>
TalentHire
</h2>


<ul>

<li className="active">
🏠 Dashboard
</li>

<li>
💼 Browse Jobs
</li>

<li>
📄 Applications
</li>

<li>
📝 Assessments
</li>

<li>
📅 Interviews
</li>

<li>
👤 Profile
</li>

<li>
📄 Resume
</li>

<li>
⚙ Settings
</li>


<li>
🚪 Logout
</li>


</ul>


</aside>





{/* Main */}

<div className="main">



<header className="topbar">


<input 
placeholder="Search jobs..."
/>


<div>

🔔

<img 
src="https://i.pravatar.cc/100?img=12"
/>

</div>


</header>





<section className="welcome">


<span>
👋 Welcome Back
</span>


<h1>
Find your next
<br/>
career opportunity
</h1>


<p>
Discover jobs from top companies and track your hiring journey.
</p>


<button>
Browse Jobs
</button>


</section>





<section className="stats">


<div>
<h2>12</h2>
<p>Applied Jobs</p>
</div>


<div>
<h2>4</h2>
<p>Shortlisted</p>
</div>


<div>
<h2>2</h2>
<p>Assessments</p>
</div>


<div>
<h2>1</h2>
<p>Interview</p>
</div>


</section>






<section className="content">


<div className="left">


<h2>
Recommended Jobs
</h2>


<div className="job">

<h3>
Infosys
</h3>

<p>
Java Developer
</p>

<span>
Pune • ₹6-8 LPA
</span>

<button>
Apply
</button>

</div>



<div className="job">

<h3>
TCS
</h3>

<p>
React Developer
</p>

<span>
Mumbai • ₹5-7 LPA
</span>

<button>
Apply
</button>

</div>


</div>






<div className="right">


<div className="box">

<h3>
Application Status
</h3>

<p>
✔ Applied</p>

<p>
✔ Resume Submitted
</p>

<p>
🟡 Review Pending
</p>

<p>
○ Assessment
</p>


</div>




<div className="box">


<h3>
Upcoming Test
</h3>


<p>
Capgemini MCQ Exam
</p>


<h2>
02:15:30
</h2>


<button>
Start
</button>


</div>





<div className="box">

<h3>
Profile Completion
</h3>


<h2>
85%
</h2>


</div>



</div>


</section>




</div>


</div>

)

}


export default CandidateDashboard;