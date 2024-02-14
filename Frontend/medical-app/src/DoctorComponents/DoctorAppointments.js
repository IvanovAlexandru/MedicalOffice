import * as React from 'react';
import {useEffect} from 'react';
import { useState } from 'react';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Paper from '@mui/material/Paper';
import Alert from '@mui/material/Alert';
import InputLabel from '@mui/material/InputLabel';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import axios from 'axios';


export default function DoctorAppointments(){

    const [alert,setAlert] = useState(false);
    const [appointments,setAppointments] = useState([]);
    const [selectedAppointment, setSelectedAppointment] = useState(null);
    const [consultations,setConsultations] = useState([]);
    const [updatedConsultations, setUpdatedConsultation] = useState({});

    useEffect(() => {
        
        const fetchDoctorApointments = async () => {
            const token = localStorage.getItem('token');
            const doctorId = localStorage.getItem('id');
            try{

                const response = await axios.get("http://localhost:8080/api/medical_office/physicians/" + doctorId 
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
        fetchDoctorApointments();
    },[]);
    
    const fetchConsultations = async (patient,date) => {
      const token = localStorage.getItem('token');
      const doctorId = localStorage.getItem('id');
      try{

          const response = await axios.get("http://localhost:8081/api/medical_office/consultatii/" + patient 
          + "/" + doctorId 
          + "?date=" + date, {
              headers: {
                  'Content-Type': 'application/x-www-form-urlencoded',
                  'Authorization': token
              }
          })

          if(response.status === 200){
              console.log(response.data._embedded.consultatieDTOList);
              setConsultations(response.data._embedded.consultatieDTOList);
              setUpdatedConsultation(response.data._embedded.consultatieDTOList)
          }
          if(response.status === 404){
            setConsultations([]);
          }
      }
      catch(error){
          setConsultations([]);
          console.log(error);
      }
  }

    const handleConsultations = async (appointment,patient,date) => {
      setSelectedAppointment(appointment);
      fetchConsultations(patient,date);
    }


    const handleUpdateConsultation = async (consultationId) => {
      try {

        const token = localStorage.getItem('token');
        delete updatedConsultations[0]["_links"];
        const newConsultation = updatedConsultations[0];
        console.log(newConsultation);
        const response = await axios.put(
          `http://localhost:8081/api/medical_office/consultatii/${consultationId}`,
          {
            newConsultation,
          },
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: token,
            },
          }
        );
  
        if (response.status === 200) {
          console.log('Consultation updated successfully.');
          setAlert(false);
        } else {
          setAlert(true);
          console.log('Failed to update consultation:', response.status);
        }
      } catch (error) {
        setAlert(true);
        console.error('Error updating consultation:', error);
      }
    };

    const handleConsultationChange = (e, field) => {
      const updatedConsultationCopy = { ...updatedConsultations };
      updatedConsultationCopy[0]['investigatii'][0][field] = e.target.value;
      setUpdatedConsultation(updatedConsultationCopy);
    };
    
    const handleDiagnosticChange = (e) => {
      const updatedConsultationCopy = { ...updatedConsultations };
      updatedConsultationCopy[0]['diagnostic'] = e.target.value;
      setUpdatedConsultation(updatedConsultationCopy);
    };
    return (
      <Container sx={{ marginTop: 2, marginBottom: 4 }}>
        <Container sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Typography variant="h4" gutterBottom>
          All your appointments :
        </Typography>
        {alert && <Alert severity="warning">Wrong information entered in Consultation</Alert>}
        {appointments.map((appointment) => (
          <Paper key={appointment.id}
            elevation={6}
            sx={{ margin: '16px', padding: '16px' }}>
            <Typography variant="h6"
            sx={{margin: '8px'}}>
              Date: {new Date(appointment.date).toLocaleDateString()}
            </Typography>
            <Typography variant="h6"
            sx={{margin: '8px'}}>Patient Name: {appointment.patients.nume} {appointment.patients.prenume}</Typography>
            <Typography variant="h6"
            sx={{margin: '8px'}}>Patient Email: {appointment.patients.email}</Typography>
            <Typography variant="h6"
            sx={{margin: '8px'}}>Patient Phone: {appointment.patients.telefon}</Typography>
            <Typography variant="h6"
            sx={{margin: '8px'}}>Status: {appointment.status}</Typography>
            <Button type='primary'
            sx={{margin: '5px'}}
            onClick={() => handleConsultations(appointment.id,appointment.patients.idUser,appointment.date)}>
              Manage Consultations
            </Button>
            {consultations && 
            selectedAppointment === appointment.id &&
            consultations.map((consultation) => (
              <Paper key={consultation.id} elevation={6} sx={{ margin: '16px', padding: '16px' }}>
                <Typography variant='h5'>
                  Consultation for: {appointment.patients.nume}
                </Typography>
                <InputLabel>Diagnostic</InputLabel>
                <Select
                label="Diagnostic"
                onChange={handleDiagnosticChange}
                value={consultation.diagnostic}
                >
                    <MenuItem value={"Bolnav"}>Bolnav</MenuItem>
                    <MenuItem value={"Sanatos"}>Sanatos</MenuItem>
                </Select> 
                { consultation.investigatii && consultation.investigatii.map( (investigation,investigationIndex) => (
                  <Paper key={investigationIndex}>
                    <TextField
                    label="Denumire investigatie"
                    variant="outlined"
                    margin="normal"
                    fullWidth
                    defaultValue={investigation.denumire}
                    onChange={(e) => handleConsultationChange(e, 'denumire')}
                  />
                  <TextField
                    label="Durata de procesare"
                    variant="outlined"
                    margin="normal"
                    fullWidth
                    defaultValue={investigation.durata_de_procesare}
                    onChange={(e) => handleConsultationChange(e, 'durata_de_procesare')}
                  />
                  <TextField
                    label="Rezultat"
                    variant="outlined"
                    margin="normal"
                    fullWidth
                    defaultValue={investigation.rezultat}
                    onChange={(e) => handleConsultationChange(e, 'rezultat')}
                  />
                  </Paper>
                ))}
                  <Button type='primary'
                  variant="contained"
                  sx={{margin: '5px'}}
                  onClick={() => handleUpdateConsultation(consultation.id,consultation.pacient,consultation.doctor)}>
                    Edit Consultation
                  </Button>
              </Paper>
            ))}
          </Paper>
        ))}
        </Container>
      </Container>
    );
}