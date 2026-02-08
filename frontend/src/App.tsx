import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import './App.css'

function App() {
  return (
    <Router>
      <div className="app">
        <header>
          <h1>🔐 SentinelStream - Threat Detection Dashboard</h1>
        </header>
        <main>
          <Routes>
            <Route path="/" element={<h2>Welcome to SentinelStream</h2>} />
            <Route path="/dashboard" element={<h2>Dashboard</h2>} />
            <Route path="/logs" element={<h2>Logs</h2>} />
            <Route path="/alerts" element={<h2>Alerts</h2>} />
            <Route path="/audit" element={<h2>Audit Trail</h2>} />
          </Routes>
        </main>
      </div>
    </Router>
  )
}

export default App
