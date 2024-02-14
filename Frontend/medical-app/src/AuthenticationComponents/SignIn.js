import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Alert from '@mui/material/Alert';
import { useState } from 'react';
import axios from 'axios';

function Copyright(props) {
  return (
    <Typography variant="body2" color="text.secondary" align="center" {...props}>
      {'Copyright © '}
      <Link color="inherit" href="https://mui.com/">
        Medical App
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

export default function SignIn({onPageChange}) {
  const [alert,setAlert] = useState(false);
  const handleChangePage = (newPage) => {
    onPageChange(newPage);
  }

  const handleLogin = async (event) => {
    event.preventDefault(); 

    try {
      const formData = new FormData(event.target);
      const username = formData.get('username');
      const password = formData.get('password');

      const response = await axios.post('http://localhost:8080/api/medical_office/authentication/login', 
      {
        username: username,
        password: password
      }, {
        headers: {
        },
      });

      if (response.status === 200) {
        const token = response.data;
        console.log('Login successful. Token:', token);
        localStorage.setItem('token',token);
        handleChangePage('MainPage')
        setAlert(false);
      } else {
        setAlert(true);
        console.log('Login failed.');
      }
    } catch (error) {
      setAlert(true);
      console.error('Error during login:', error);
    }
  };

  return (
        <Container maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <Box component="form" onSubmit={handleLogin} noValidate sx={{ mt: 1 }}>
            <TextField
              margin="normal"
              required
              fullWidth
              id="username"
              label="Username"
              name="username"
              autoComplete="username"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign In
            </Button>
            <Grid container>
              <Grid item xs>
                <Link href="#" variant="body2">
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link href="#" variant="body2" onClick={() => handleChangePage("SignUp")}>
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        {alert && <Alert severity="warning">Wrong credentials.</Alert>}
        <Copyright sx={{ mt: 8, mb: 4 }} />
        </Container>
  );
}