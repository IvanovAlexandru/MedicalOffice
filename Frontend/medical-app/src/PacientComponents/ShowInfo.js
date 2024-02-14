import * as React from 'react';
import {useEffect} from 'react';
import { useState } from 'react';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Alert from '@mui/material/Alert';
import axios from 'axios';


export default function ShowInfo(){
    
    const [alert,setAlert] = useState(false);
    const [info,setInfo] = useState();
    const [editedInfo, setEditedInfo] = useState({});

    useEffect(() => {
        
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
                    setInfo(response.data);
                    setEditedInfo(response.data);
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

    const handleInputChange = (e) => {
      const { name, value } = e.target;
      setEditedInfo((prevInfo) => ({
        ...prevInfo,
        [name]: value,
      }));
    };
  
    const handleSubmit = async (e) => {
      e.preventDefault();
      const token = localStorage.getItem('token');
  
      try {
        const response = await axios.put(
          `http://localhost:8080/api/medical_office/patients/${editedInfo.cnp}`,
          editedInfo,
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: token,
            },
          }
        );
  
        if (response.status === 204 || response.status === 201) {
          console.log('Update successful');
          // Optionally, you can update the local state with the new data
          setInfo(editedInfo);
        } else {
          setAlert(true);
          console.log(response.status);
        }
      } catch (error) {
        setAlert(true);
        console.log(error);
      }
    };
  
    return (
      <Container sx={{ marginTop: 2, marginBottom: 4 }}>
        {info && (
          <Container sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}>
              <Typography variant="h3" gutterBottom>
                Your account information:
              </Typography>
              {alert && <Alert severity="warning">You entered wrong data.</Alert>}
              <TextField
                label="Birth Date as yyyy/MM/dd"
                value={editedInfo.data_nasterii || ''}
                onChange={handleInputChange}
                name="data_nasterii"
                variant="outlined"
                margin="normal"
              />
              <TextField
                label="Email"
                value={editedInfo.email || ''}
                onChange={handleInputChange}
                name="email"
                variant="outlined"
                margin="normal"
              />
              <TextField
                label="Last Name"
                value={editedInfo.nume || ''}
                onChange={handleInputChange}
                name="nume"
                variant="outlined"
                margin="normal"
              />
              <TextField
                label="First Name"
                value={editedInfo.prenume || ''}
                onChange={handleInputChange}
                name="prenume"
                variant="outlined"
                margin="normal"
              />
              <TextField
                label="Phone"
                value={editedInfo.telefon || ''}
                onChange={handleInputChange}
                name="telefon"
                variant="outlined"
                margin="normal"
              />
              <Button type="submit"
              variant="contained"
              sx={{ mt: 3, mb: 2 }}>
                Edit your information
              </Button>
            </Box>
          </Container>
        )}
      </Container>
    );
}