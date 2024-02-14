import * as React from 'react';
import {useEffect} from 'react';
import { useState } from 'react';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import axios from 'axios';


export default function ManagePatients(){

    const [patients,setPatients] = useState([]);

    useEffect(() => {
        const fetchPatients = async () => {
            const token = localStorage.getItem('token');
            try{

                const response = await axios.get("http://localhost:8080/api/medical_office/patients", {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization': token
                    }
                })

                if(response.status === 200){
                    console.log(response.data._embedded.patientDTOList);
                    setPatients(response.data._embedded.patientDTOList);
                }
                else{
                    console.log(response.status);
                }
            }
            catch(error){
                console.log(error);
            }
        }

        fetchPatients();
    },[]);

    const handleDeletePatient = async (cnp) => {
        const token = localStorage.getItem('token');
            try{

                const response = await axios.delete("http://localhost:8080/api/medical_office/patients/" + cnp, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization': token
                    }
                })

                if(response.status === 200){
                    console.log("User deleted succesfully")
                }
                else{
                    console.log(response.status);
                }
            }
            catch(error){
                console.log(error);
            }
    }

    return (
      <Container sx={{ marginTop: 2, marginBottom: 4 }}>
        <Container sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Typography variant="h4" gutterBottom>
          All Patients :
        </Typography>
        {patients.map((patient) => (
          <Paper key={patient.idUser} elevation={6} sx={{ margin: '16px', padding: '16px' }}>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Patient: {patient.nume} {patient.prenume}
            </Typography>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Email: {patient.email}
            </Typography>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Phone: {patient.telefon}
            </Typography>
            <Button 
            type="primary"
            variant="contained"
            onClick={() => handleDeletePatient(patient.cnp)}>
                Delete
            </Button>
          </Paper>
        ))}
        </Container>
      </Container>
    );
}