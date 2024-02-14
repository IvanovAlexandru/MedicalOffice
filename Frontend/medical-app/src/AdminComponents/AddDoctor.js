import * as React from 'react';
import {useEffect} from 'react';
import { useState } from 'react';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import axios from 'axios';


export default function AddDoctor(){
    
    
    const [editedInfo, setEditedInfo] = useState({
        id_user: 15,
        email: "",
        nume: "",
        prenume: "",
        telefon: "",
        specialization: ""
      });
    
      const handleInputChange = (e) => {
        const { name, value } = e.target;
        setEditedInfo((prevInfo) => ({
          ...prevInfo,
          [name]: value,
        }));
      };
    
      const handleSpecializationChange = (e) => {
        setEditedInfo((prevInfo) => ({
          ...prevInfo,
          specialization: e.target.value,
        }));
      };
  
    const handleSubmit = async (e) => {
      e.preventDefault();
      const token = localStorage.getItem('token');
      console.log(editedInfo);
      try {
        const response = await axios.post(
          `http://localhost:8080/api/medical_office/physicians`,
          editedInfo,
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: token,
            },
          }
        );
  
        if (response.status === 201) {
          console.log('Created successfully');
        } else {
          console.log(response.status);
        }
      } catch (error) {
        console.log(error);
      }
    };
  
    return (
      <Container sx={{ marginTop: 2, marginBottom: 4 }}>
          <Container sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}>
              <Typography variant="h3" gutterBottom>
                Add a new Doctor:
              </Typography>
              <TextField
                label="Email"
                onChange={handleInputChange}
                name="email"
                variant="outlined"
                margin="normal"
              />
              <TextField
                label="Last Name"
                onChange={handleInputChange}
                name="nume"
                variant="outlined"
                margin="normal"
              />
              <TextField
                label="First Name"
                onChange={handleInputChange}
                name="prenume"
                variant="outlined"
                margin="normal"
              />
              <TextField
                label="Phone"
                onChange={handleInputChange}
                name="telefon"
                variant="outlined"
                margin="normal"
              />
              <InputLabel>Specialization</InputLabel>
                <Select
                label="Specialization"
                onChange={handleSpecializationChange}
                value={editedInfo.specialization}
                >
                    <MenuItem value={"Dermatologist"}>Dermatologist</MenuItem>
                    <MenuItem value={"Cardiologist"}>Cardiologist</MenuItem>
                    <MenuItem value={"Neurologist"}>Neurologist</MenuItem>
                    <MenuItem value={"Pediatrician"}>Pediatrician</MenuItem>
                    <MenuItem value={"Psychiatrist"}>Psychiatrist</MenuItem>
                </Select>
              <Button type="submit"
              variant="contained"
              sx={{ mt: 3, mb: 2 }}>
                Add Doctor
              </Button>
            </Box>
          </Container>
      </Container>
    );
}