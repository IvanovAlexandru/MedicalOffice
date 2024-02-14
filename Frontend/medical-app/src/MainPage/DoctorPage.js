import * as React from 'react';
import {useEffect} from 'react';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import { AppBar, Toolbar } from '@mui/material';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import MedicalServicesIcon from '@mui/icons-material/MedicalServices';
import axios from 'axios';
import Typography from '@mui/material/Typography';


export default function DoctorPage({onPageChange,onActionChange}){

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
            handleActionChange('');
            localStorage.clear();
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
          onClick={() => handleActionChange('doctor-info')}>
            Your Info
          </Button>
          <Button sx={{ flexGrow: 1 }}
          onClick={() => handleActionChange('patients')}>
            View Patients
          </Button>
          <Button sx={{flexGrow: 1 }}
          onClick={() => handleActionChange('doctor-appointments')}>
            View Appointments
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