import './App.css';
import HomePage from './Page/HomePage/HomePage';
import VerifyPage from './Page/VerifyPage/VerifyPage';
import EnrollPage from './Page/EnrollPage/EnrollPage';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

function App() {
  return (
    <Router>
      <div className = 'App'>
      <Switch>
        <Route path = '/verify' component = {VerifyPage}/>
        <Route path = '/enroll' component = {EnrollPage}/>
        <Route path = '/' component = {HomePage}/>
      </Switch>
    </div>
  </Router>
  );
}

export default App;
