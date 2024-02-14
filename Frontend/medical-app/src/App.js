import logo from './logo.svg';
import './App.css';
import { useState } from 'react';
import { Container, ThemeProvider} from '@mui/material';
import {createTheme} from '@mui/material/styles';
import SignIn from './AuthenticationComponents/SignIn';
import SignUp from './AuthenticationComponents/SignUp';
import MainPage from './MainPage/MainPage';
import { themeOptions } from './themes/styles';

const defaultTheme = createTheme(themeOptions);

function App() {

  const [page,setPage] = useState("Login");
  const [role,setRole] = useState("");

  const handlePageChange = (newPage) => {
    setPage(newPage);
  };
  
  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main">
        {page === "Login" ? <SignIn onPageChange={handlePageChange} /> : <div></div>}
        {page === "SignUp" ? <SignUp onPageChange={handlePageChange}/> : <div></div>}
        {page === "MainPage" ? <MainPage onPageChange={handlePageChange} role={role}/> : <div></div>}
      </Container>
    </ThemeProvider>
  );
}

export default App;
