import * as React from 'react';
import {useEffect} from 'react';
import { useState } from 'react';
import Container from '@mui/material/Container';
import CssBaseline from '@mui/material/CssBaseline';
import axios from 'axios';
import PatientPage from './PatientPage';
import DoctorPage from './DoctorPage';
import ShowInfo from '../PacientComponents/ShowInfo';
import Appointments from '../PacientComponents/Appointments';
import History from '../PacientComponents/History';
import GetDoctors from '../PacientComponents/GetDoctors';
import ShowDoctorInfo from '../DoctorComponents/ShowDoctorInfo';
import DoctorAppointments from '../DoctorComponents/DoctorAppointments';
import GetPatients from '../DoctorComponents/GetPatients';
import AddDoctor from '../AdminComponents/AddDoctor';
import AdminPage from './AdminPage';
import ManagePatients from '../AdminComponents/ManagePatients';
import ManageDoctors from '../AdminComponents/ManageDoctors';

export default function MainPage({onPageChange}) {

  const [role,setRole] = useState('');
  const [action,setAction] = useState('');

  useEffect(() => {

    const fetchRole = async () => {
      try {

        const token = localStorage.getItem('token');
        
        const response = await axios.get('http://localhost:8080/api/medical_office/authentication/role', {
          headers: {
            'Authorization': token
          },
        });

        if (response.status === 200) {
          const userRole = response.data;
          setRole(userRole);
          console.log(userRole);
          localStorage.setItem('role', userRole);
        }
        else{
          console.log("Failed to fetch role");
        }
      } catch (error) {
        // Handle errors here
        console.error('Error fetching role:', error);
      }
    };

    const fetchId = async () => {
      try {

        const token = localStorage.getItem('token');
        
        const response = await axios.get('http://localhost:8080/api/medical_office/authentication/id', {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': token
          },
        });

        if (response.status === 200) {
          const id = response.data;
          console.log(id);
          localStorage.setItem('id', id);
        }
        else{
          console.log("Failed to fetch role");
        }
      } catch (error) {
        // Handle errors here
        console.error('Error fetching role:', error);
      }
    };

    fetchId();
    fetchRole();
  }, []);
  
  const handleActionChange = (action) => {
    setAction(action);
  }

  return (
    <Container sx={{width: '100%'}}>
    <CssBaseline />
    {role === 'Pacient' ? <PatientPage onPageChange={onPageChange} onActionChange={handleActionChange}/> : <div></div>}
    {role === 'Doctor' ? <DoctorPage onPageChange={onPageChange} onActionChange={handleActionChange}/> : <div></div>}
    {role === 'Admin' ? <AdminPage onPageChange={onPageChange} onActionChange={handleActionChange}/> : <div></div>}
    {action === 'your-information' ? <ShowInfo/> : <div></div>}
    {action === 'appointments' ? <Appointments/> : <div></div>}
    {action === 'history' ? <History/> : <div></div>}
    {action === 'doctors' ? <GetDoctors/> : <div></div>}
    {action === 'doctor-info' ? <ShowDoctorInfo/> : <div></div>}
    {action === 'doctor-appointments' ? <DoctorAppointments/> : <div></div>}
    {action === 'patients' ? <GetPatients/> : <div></div>}
    {action === 'add-doctor' ? <AddDoctor/> : <div></div>}
    {action === 'manage-patients' ? <ManagePatients/> : <div></div>}
    {action === 'manage-doctors' ? <ManageDoctors/> : <div></div>}
    </Container>
  );
}