import * as React from 'react';
import {useEffect} from 'react';
import { useState } from 'react';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import axios from 'axios';


export default function GetPatients(){

    const [patients,setPatients] = useState([]);
    const [selectedPatient, setSelectedPatient] = useState(null);
    const [appointments,setAppointments] = useState([]);

    useEffect(() => {
        const fetchPatients = async () => {
            const token = localStorage.getItem('token');
            const doctorId = localStorage.getItem('id');
            try{

                const response = await axios.get("http://localhost:8080/api/medical_office/physicians/" + doctorId + "/patients", {
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
    
    const fetchUserAppointments = async (cnp) => {
      const token = localStorage.getItem('token');
      try{

          const response = await axios.get("http://localhost:8080/api/medical_office/patients/" + cnp 
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


  const handleGetAppointments = async (cnp) => {
    setSelectedPatient(cnp);
    fetchUserAppointments(cnp);
  };

    return (
      <Container sx={{ marginTop: 2, marginBottom: 4 }}>
        <Container sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Typography variant="h4" gutterBottom>
          Your patients :
        </Typography>
        {patients.map((patient) => (
          <Paper key={patient.id_doctor} elevation={6} sx={{ margin: '16px', padding: '16px' }}>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Patient: {patient.nume} {patient.prenume}
            </Typography>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Email: {patient.email}
            </Typography>
            <Typography variant="h6" sx={{ margin: '8px' }}>
              Phone: {patient.telefon}
            </Typography>
            <Button type="primary"
              variant="contained"
              onClick={() => handleGetAppointments(patient.cnp)}
              >
                View Patient's Appointments
            </Button>
            {selectedPatient === patient.cnp && appointments.map((appointment) => (
              <Paper key={appointment.id} elevation={6} sx={{ margin: '16px', padding: '16px' }}>
                <Typography variant="h6" sx={{ margin: '8px' }}>
                  Date: {new Date(appointment.date).toLocaleDateString()}
                </Typography>
                <Typography variant="h6" sx={{ margin: '8px' }}>
                  Doctor: {appointment.physicians.nume} {appointment.physicians.prenume}
                </Typography>
                <Typography variant="h6" sx={{ margin: '8px' }}>
                  Specialization: {appointment.physicians.specialization}
                </Typography>
                <Typography variant="h6" sx={{ margin: '8px' }}>
                  Status: {appointment.status}
                </Typography>
              </Paper>
            ))}
          </Paper>
        ))}
        </Container>
      </Container>
    );
}