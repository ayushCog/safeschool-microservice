package com.cognizant.userservice.service;

import com.cognizant.userservice.classexception.StudentException;
import com.cognizant.userservice.dto.StudentRegistrationDto;
import com.cognizant.userservice.entity.Student;
import com.cognizant.userservice.entity.User;
import com.cognizant.userservice.projection.StudentProjection;
import com.cognizant.userservice.projection.SuccessResponseProjection;
import com.cognizant.userservice.repository.StudentRepository;
import com.cognizant.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StudentServiceImpl implements IStudentService{
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public SuccessResponseProjection<StudentProjection> addStudent(StudentRegistrationDto studentRegistrationDto) throws StudentException {
        log.info("Received service request: Student registration attempt for Email: {}", studentRegistrationDto.getEmail());

        if(userRepository.findUserByEmail(studentRegistrationDto.getEmail()) != null){
            log.error("Student registration failed: Email: {} is already taken", studentRegistrationDto.getEmail());
            throw new StudentException("User already registered", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setName(studentRegistrationDto.getName());
        user.setEmail(studentRegistrationDto.getEmail());
        user.setRole(studentRegistrationDto.getRole().toUpperCase());
        user.setPhone(studentRegistrationDto.getPhone());
        user.setStatus(studentRegistrationDto.getStatus());

        User savedUser = userRepository.save(user);

        Student student = new Student();
        student.setUser(savedUser);
        student.setDob(studentRegistrationDto.getDob());
        student.setGender(studentRegistrationDto.getGender());
        student.setAddress(studentRegistrationDto.getAddress());

        Student savedStudent = studentRepository.save(student);

        log.info("Successfully created Student profile for User: {}. StudentID: {} linked to UserID: {}", savedUser.getEmail(), savedStudent.getStudentId(), savedUser.getUserId());

        return new SuccessResponseProjection<>(true, "Student registered successfully",
                new StudentProjection(savedUser.getUserId(), savedUser.getName(), savedUser.getRole(), savedUser.getEmail(),
                        savedUser.getPhone(), savedUser.getStatus(), savedStudent.getGender(),
                        savedStudent.getAddress(), savedStudent.getDob()));
    }
}
