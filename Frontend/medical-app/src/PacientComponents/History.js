import * as React from 'react';
import {useEffect} from 'react';
import { useState } from 'react';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import axios from 'axios';


export default function History(){
    const [cnp,setCnp] = useState('');
    const [appointments,setAppointments] = useState([]);

    useEffect(() => {
        
        const fetchUserApointments = async (cnp) => {
            const token = localStorage.getItem('token');
            try{

                const response = await axios.get("http://localhost:8080/api/medical_office/patients/history/" + cnp 
                + "/appointments", {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization': token
                    }
                })

                if(response.status === 200){
                    console.log(response.data._embedded.physiciansPatientsMappingList);
                    setAppointments(response.data._embedded.physiciansPatientsMappingList);
                }
                else{
                    console.log(response.status);
                }
            }
            catch(error){
                console.log(error);
            }
        }

        const fetchUserCnp = async () => {
            const userId = localStorage.getItem('id');
            const token = localStorage.getItem('token');
            try{

                const response = await axios.get("http://localhost:8080/api/medical_office/patients/id/" + userId, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization': token
                    }
                })

                if(response.status === 200){
                    console.log(response.data.cnp);
                    setCnp(response.data.cnp);
                    fetchUserApointments(response.data.cnp);
                }
                else{
                    console.log(response.status);
                }
            }
            catch(error){
                console.log(error);
            }
        }

        fetchUserCnp();
    },[]);
    

    return (
      <Container sx={{ marginTop: 2, marginBottom: 4 }}>
        <Container sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Typography variant="h4" gutterBottom>
          Your previous appointments :
        </Typography>
        {appointments.map((appointment) => (
          <Paper key={appointment.id}
            elevation={6}
            sx={{ margin: '16px', padding: '16px' }}>
            <Typography variant="h6"
            sx={{margin: '8px'}}>
              Date: {new Date(appointment.date).toLocaleDateString()}
            </Typography>
            <Typography variant="h6"
            sx={{margin: '8px'}}>Physician: {appointment.physicians.nume}</Typography>
            <Typography variant="h6"
            sx={{margin: '8px'}}>Specialization: {appointment.physicians.specialization}</Typography>
            <Typography variant="h6"
            sx={{margin: '8px'}}>Status: {appointment.status}</Typography>
          </Paper>
        ))}
        </Container>
      </Container>
    );
}