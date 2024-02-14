import * as React from 'react';
import {useEffect} from 'react';
import { useState } from 'react';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Paper from '@mui/material/Paper';
import axios from 'axios';
import { green } from '@mui/material/colors';


export default function GetDoctors(){

    const [user,setUser] = useState({});
    const [doctors,setDoctors] = useState([]);
    const [appointmentDates, setAppointmentDates] = useState({});

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

        const fetchUserInfo = async () => {
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
                    console.log(response.data);
                    setUser(response.data);
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
        fetchUserInfo();
    },[]);
    
    const handleMakeAppointment = async (doctorId, appointmentDate) => {
        try {
          const token = localStorage.getItem('token');
          const userId = localStorage.getItem('id');
      
          const response = await axios.post(
            "http://localhost:8080/api/medical_office/appointments",
            {
              physicians: { id_doctor: doctorId },
              patients: { cnp: user.cnp },
              date: appointmentDates[doctorId] || [],
              status: "Neprezentat",
            },
            {
              headers: {
                "Content-Type": "application/json",
                Authorization: token,
              },
            }
          );
      
          if (response.status === 201) {
            console.log('Appointment created successfully');
          } else {
            console.log(response.status);
          }
        } catch (error) {
          console.log(error);
        }
      };

      const handleDateChange = (doctorId, value) => {
        setAppointmentDates((prevDates) => ({
          ...prevDates,
          [doctorId]: value,
        }));
      };
    return (
      <Container sx={{ marginTop: 2, marginBottom: 4 }}>
        <Container sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Typography variant="h4" gutterBottom>
          All available doctors :
        </Typography>
        {doctors.map((doctor) => (
          <Paper key={doctor.id_doctor} elevation={6} sx={{ margin: '16px', padding: '16px' }}>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Physician: {doctor.nume} {doctor.prenume}
            </Typography>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Specialization: {doctor.specialization}
            </Typography>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Email: {doctor.email}
            </Typography>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Phone: {doctor.telefon}
            </Typography>
            <Paper elevation={6} sx={{ margin: '16px', padding: '16px' }}>
            <Typography variant="h5" sx={{ margin: '8px'}} >
              Make an appointment for this doctor:
            </Typography>
            <TextField
              label="Appointment date as yyyy-MM-dd"
              name="appointment_date"
              variant="outlined"
              margin="normal"
              value={appointmentDates[doctor.id_doctor] || ""}
              onChange={(e) => handleDateChange(doctor.id_doctor, e.target.value)}
            />
            <Button type="primary"
              variant="contained"
              sx={{ mt: 3, mb: 2 , ml: 7}}
              onClick={() => handleMakeAppointment(doctor.id_doctor)}>
                Reserve
            </Button>
            </Paper>
          </Paper>
        ))}
        </Container>
      </Container>
    );
}