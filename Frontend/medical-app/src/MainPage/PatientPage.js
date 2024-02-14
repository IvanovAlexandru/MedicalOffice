import * as React from 'react';
import {useEffect} from 'react';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import { AppBar, Toolbar, Typography } from '@mui/material';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import MedicalServicesIcon from '@mui/icons-material/MedicalServices';
import axios from 'axios';


export default function PatientPage({onPageChange,onActionChange}){

    const handleChangePage = (newPage) => {
        onPageChange(newPage);
      }
    
    const handleActionChange = (newAction) => {
      onActionChange(newAction);
    }

    const handleLogout = async () => {
    
        try{
          const token = localStorage.getItem('token');
          
          const response = await axios.post('http://localhost:8080/api/medical_office/authentication/logout',{} ,{
            headers: {
              'Authorization': token
            },
          });
          
          if(response.status === 200){
            console.log(response.data);
            handleChangePage('Login');
            localStorage.clear();
            handleActionChange('');
          }
          else{
            console.log(response.data);
          }
        }
        catch(error){
          console.error('Error during login:', error);
        }
      }

    return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static" sx={{ width: '100%', left: 0, right: 0 }}>
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MedicalServicesIcon />
          </IconButton>
          <Typography variant="h5" sx={{ flexGrow: 1 }}>
            Medical App
          </Typography>
          <Button sx={{ flexGrow: 1 }}
          onClick={() => handleActionChange('your-information')}>
            Your Info
          </Button>
          <Button sx={{ flexGrow: 1 }}
          onClick={() => handleActionChange('appointments')}>
            View Appointments
          </Button>
          <Button sx={{flexGrow: 1 }}
          onClick={() => handleActionChange('doctors')}>
            View Doctors
          </Button>
          <Button sx={{flexGrow: 1 }}
          onClick={() => handleActionChange('history')}>
            History
          </Button>
          <Button
            sx={{ flexGrow: 1 }} 
            onClick={() => handleLogout()}>
            Logout
          </Button>
        </Toolbar>
      </AppBar>
    </Box>
    );
}