import { useState } from "react";
import Editor from "@monaco-editor/react";
import "./CodingAssessment.css";
import { useEffect } from "react";
import { useParams } from "react-router-dom";

import { getCodingQuestions} from "../../api/axiosService";

import {runCode} from "../../api/axiosService";

function CodingAssessment() {

const { assessmentId } = useParams();
const [questions, setQuestions] = useState([]);
const [selectedQuestion, setSelectedQuestion] = useState(null);
const [consoleOutput, setConsoleOutput] = useState("Click 'Run Code' to execute your program.");

const [codeMap, setCodeMap] = useState({});

    const [language, setLanguage] = useState("java");

    const defaultCode = `public class Main {

    public static void main(String[] args){

    }

}`;

    useEffect(() => {

    loadQuestions();

}, []);

useEffect(() => {

    setConsoleOutput("Click 'Run Code' to execute your program.");

}, [selectedQuestion]);

const handleRunCode = async () => {
    try{
        const request = {
            language: language,

           sourceCode:
    codeMap[selectedQuestion.codingQuestionId] || defaultCode,

            testcases: selectedQuestion.sampleTestCases
        };
        const response = await runCode(request);
        console.log(response);
          if (response.compileError) {
            setConsoleOutput(response.compileError);
            return;
        }
       
        if (response.runtimeError) {
            setConsoleOutput(response.runtimeError);
            return;
        }

         let output = "";

        output += `Status : ${response.status}\n`;
        output += `Passed : ${response.passedTestCases}/${response.totalTestCases}\n`;
        output += `Execution Time : ${response.executionTime} ms\n\n`;

        response.results.forEach(result => {

            output += `${result.passed ? "✅" : "❌"} Test Case ${result.testCaseNumber}\n`;
            output += `Input            : ${result.input}\n`;
            output += `Expected Output  : ${result.expectedOutput}\n`;
            output += `Actual Output    : ${result.actualOutput.trim()}\n`;
            output += `Result           : ${result.passed ? "Passed" : "Failed"}\n`;
            output += "----------------------------------------\n";

        });

        setConsoleOutput(output);

    }catch(error){
        console.log(error);
    }
};

const loadQuestions = async () => {
    try{
        const data = await getCodingQuestions(assessmentId);
        setQuestions(data);
        console.log(data);
        if(data.length>0){
            setSelectedQuestion(data[0]);
        }
    }catch(error){
        console.log(error);
    }
};

    return (

        <div className="assessment-container">
<div className="sidebar">

    <h3>Questions</h3>
    {
        questions.map((question)=>(

            <div

                key={question.codingQuestionId}

                className={
                    selectedQuestion?.codingQuestionId===question.codingQuestionId
                    ?"question active"
                    :"question"
                }

                onClick={()=>setSelectedQuestion(question)}

            >

                {question.title}

            </div>

        ))

    }

</div>
            <div className="editor-section">

                <div className="top-bar">

                    <h2>TalentHire Coding Assessment</h2>

                    <select
                        value={language}
                        onChange={(e)=>setLanguage(e.target.value)}
                    >
                        <option value="java">Java</option>
                        <option value="cpp">C++</option>
                        <option value="python">Python</option>
                    </select>

                </div>

                <div className="problem">

    {
        selectedQuestion &&
        <>
            <h3>

                {selectedQuestion.title}

            </h3>
            <p>

                {selectedQuestion.problemStatement}
            </p>
        </>
    }

    <div className="sample">

    <h4>Sample Test Case</h4>

    <strong>Input</strong>
    <pre>
        {selectedQuestion?.sampleTestCases[0].input}

    </pre>

    <strong>Expected Output</strong>

    <pre>

        {selectedQuestion?.sampleTestCases[0].expectedOutput}

    </pre>

</div>

</div>

               <Editor
    height="450px"
    language={language}
    theme="vs"
    value={codeMap[selectedQuestion?.codingQuestionId] || defaultCode}
    onChange={(value) =>
        setCodeMap(prev => ({
            ...prev,
            [selectedQuestion.codingQuestionId]: value || ""
        }))
    }
/>
                <div className="action-bar">

    <button className="run-btn" onClick={handleRunCode}>
        ▶ Run Code
    </button>

    <button className="submit-btn">
        ✓ Submit Assessment
    </button>

</div>

<div className="console">

    <h4>Console</h4>

      <pre>{consoleOutput}</pre>

</div>

            </div>

        </div>

    );

}

export default CodingAssessment;