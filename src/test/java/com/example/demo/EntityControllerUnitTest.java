
package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAListOfDoctors() throws Exception {
        Doctor doctor1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        Doctor doctor2 = new Doctor ("Miren", "Iniesta", 24, "m.iniesta@hospital.accwe");
        Doctor doctor3 = new Doctor ("Pedro", "Gomez", 34, "p.gomez@hospital.accwe");
        Doctor doctor4 = new Doctor ("Jose", "Garcia", 26, "j.garcia@hospital.accwe");

        List<Doctor>doctorsList = doctorRepository.findAll();
        doctorsList.add(doctor1);
        doctorsList.add(doctor2);
        doctorsList.add(doctor3);
        doctorsList.add(doctor4);

        when(doctorRepository.findAll()).thenReturn(doctorsList);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldGetEmptyListOfDoctors() throws Exception{
        List<Doctor> doctorsList = new ArrayList<>();
        when(doctorRepository.findAll()).thenReturn(doctorsList);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());

    }

    @Test
    void shouldGetDoctorById() throws Exception{
        Doctor doctor = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        doctor.setId(1);

        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/doctors/{id}" , doctor.getId()))
                .andExpect(status().isOk());

    }

    @Test
    void shouldNotGetAnyAppointmentById() throws Exception{
        long id = 3;
        mockMvc.perform(get("/api/doctors/{id}" , id))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldCreateDoctor() throws Exception {
        Doctor doctor = new Doctor ("Jose", "Garcia", 26, "j.garcia@hospital.accwe");

        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated());

    }

    @Test
    void shouldDeleteDoctorById() throws Exception{
        Doctor doctor = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        doctor.setId(1);

        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/doctors/{id}" , doctor.getId()))
                .andExpect(status().isOk());

    }

    @Test
    void shouldNotDeleteAnyDoctor() throws Exception{
        long id = 3;
        mockMvc.perform(delete("/api/doctors/{id}" , id))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDeleteAllDoctors() throws Exception{
        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }

}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllPatients() throws Exception {
        Patient patient1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        Patient patient2 = new Patient("Paulino", "Antunez", 37, "p.antunez@email.com");
        Patient patient3 = new Patient("Sara", "Mendoza", 28, "s.mendoza@email.com");

        List<Patient> patients = new ArrayList<>();
        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetNoPatients() throws Exception {
        List<Patient> patientList = new ArrayList<>();
        when(patientRepository.findAll()).thenReturn(patientList);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetPatientById() throws Exception {
        Patient patient = new Patient("Paulino", "Antunez", 37, "p.antunez@email.com");
        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/patients/{id}" , patient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNoGetPatientById() throws Exception {
        long id = 1;
        mockMvc.perform(get("/api/patients/{id}" , id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreatePatient() throws Exception {
        Patient patient = new Patient("Jose", "Garcia", 26, "j.garcia@hospital.accwe");

        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());

    }

    @Test
    void shouldDeletePatientById() throws Exception{
        Patient patient = new Patient("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/patients/{id}" , patient.getId()))
                .andExpect(status().isOk());

    }

    @Test
    void shouldNotDeleteAnyPatient() throws Exception{
        long id = 3;
        mockMvc.perform(delete("/api/patients/{id}" , id))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDeleteAllPatients() throws Exception{
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }


}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllRooms() throws Exception {
        Room room1 = new Room("Room 1");
        Room room2 = new Room("Room 2");
        Room room3 = new Room("Room 3");

        List<Room> roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);

        when(roomRepository.findAll()).thenReturn(roomList);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetNoRooms() throws Exception {
        List<Room> roomList = new ArrayList<>();
        when(roomRepository.findAll()).thenReturn(roomList);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetRoomByRoomName() throws Exception {
        Room room1 = new Room("Room 1");

        Optional<Room> opt = Optional.of(room1);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room1.getRoomName());
        assertThat(room1.getRoomName()).isEqualTo("Room 1");

        when(roomRepository.findByRoomName(room1.getRoomName())).thenReturn(opt);
        mockMvc.perform(get("/api/rooms/{roomName}" ,room1.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNoRoomByRoomName() throws Exception {
        String roomName = "NameRoom 1";
        mockMvc.perform(get("/api/rooms/{roomName}" , roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateRoom() throws Exception {
        Room room = new Room("My room");

        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated());

    }

    @Test
    void shouldDeleteRoomByRoomName() throws Exception{
        Room room = new Room("Room 1");

        Optional<Room> opt = Optional.of(room);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo("Room 1");

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        mockMvc.perform(delete("/api/rooms/{roomName}" , room.getRoomName()))
                .andExpect(status().isOk());

    }

    @Test
    void shouldNotDeleteAnyRoom() throws Exception{
        String room = "Room 1";
        mockMvc.perform(delete("/api/rooms/{roomName}" , room))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDeleteAllPatients() throws Exception{
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }

}
