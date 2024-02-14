import * as React from 'react';
import {useEffect} from 'react';
import { useState } from 'react';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import axios from 'axios';
import { Paper } from '@mui/material';


export default function ShowDoctorInfo(){
    
    const [info,setInfo] = useState();

    useEffect(() => {
        
        const fetchUserInfo = async () => {
            const userId = localStorage.getItem('id');
            const token = localStorage.getItem('token');
            try{

                const response = await axios.get("http://localhost:8080/api/medical_office/physicians/" + userId, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization': token
                    }   
                })

                if(response.status === 200){
                    console.log(response.data);
                    setInfo(response.data);
                }
                else{
                    console.log(response.status);
                }
            }
            catch(error){
                console.log(error);
            }
        }
        fetchUserInfo();
    },[]);
  
    return (
      <Container sx={{ marginTop: 2, marginBottom: 4 }}>
        {info && (
          <Container sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <Paper
            elevation={6} 
            sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}>
              <Typography variant="h3" 
              sx={{margin: '10px'}}>
                Your account information:
              </Typography>
              <Typography variant='h5'
              sx={{margin: '10px'}}>
                Name: {info.nume} {info.prenume}
              </Typography>
              <Typography variant='h5'
              sx={{margin: '10px'}}>
                Email: {info.email}
              </Typography>
              <Typography variant='h5'
              sx={{margin: '10px'}}>
                Specialization: {info.specialization}
              </Typography>
              <Typography variant='h5'
              sx={{margin: '10px'}}>
                Phone: {info.telefon}
              </Typography>
            </Paper>
          </Container>
        )}
      </Container>
    );
}