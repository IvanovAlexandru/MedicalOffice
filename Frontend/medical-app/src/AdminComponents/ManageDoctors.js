import * as React from 'react';
import {useEffect} from 'react';
import { useState } from 'react';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import axios from 'axios';


export default function ManageDoctors(){

    const [doctors,setDoctors] = useState([]);

    useEffect(() => {
        const fetchDoctors = async () => {
            const token = localStorage.getItem('token');
            try{

                const response = await axios.get("http://localhost:8080/api/medical_office/physicians", {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization': token
                    }
                })

                if(response.status === 200){
                    console.log(response.data._embedded.physicianDTOList);
                    setDoctors(response.data._embedded.physicianDTOList);
                }
                else{
                    console.log(response.status);
                }
            }
            catch(error){
                console.log(error);
            }
        }

        fetchDoctors();
    },[]);

    const handleDeleteDoctor = async (id) => {
        const token = localStorage.getItem('token');
            try{

                const response = await axios.delete("http://localhost:8080/api/medical_office/physicians/" + id, {
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
          All Doctors :
        </Typography>
        {doctors.map((doctor) => (
          <Paper key={doctor.idUser} elevation={6} sx={{ margin: '16px', padding: '16px' }}>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Doctor: {doctor.nume} {doctor.prenume}
            </Typography>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Email: {doctor.email}
            </Typography>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Phone: {doctor.telefon}
            </Typography>
            <Button 
            type="primary"
            variant="contained"
            onClick={() => handleDeleteDoctor(doctor.id_doctor)}>
                Delete
            </Button>
          </Paper>
        ))}
        </Container>
      </Container>
    );
}