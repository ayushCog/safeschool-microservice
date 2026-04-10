package com.cognizant.userservice.service;

import com.cognizant.userservice.classexception.UserException;
import com.cognizant.userservice.dto.ParentRegistrationDto;
import com.cognizant.userservice.entity.Parent;
import com.cognizant.userservice.entity.User;
import com.cognizant.userservice.projection.ParentProjection;
import com.cognizant.userservice.projection.SuccessResponseProjection;
import com.cognizant.userservice.repository.ParentRepository;
import com.cognizant.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ParentServiceImpl implements IParentService {
    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public SuccessResponseProjection<ParentProjection> addParent(ParentRegistrationDto parentRegistrationDto) throws UserException {
        log.info("Received service request: Parent registration attempt for Email: {} linking to Student Email: {}", parentRegistrationDto.getEmail(), parentRegistrationDto.getStudentEmail());

        User studentUser = userRepository.findUserByEmail(parentRegistrationDto.getStudentEmail());

        if(studentUser == null || studentUser.getStudent() == null) {
            log.error("Parent registration failed: Student with Email: {} not found", parentRegistrationDto.getStudentEmail());
            throw new UserException("Student not registered", HttpStatus.NOT_FOUND);
        }

        if(userRepository.findUserByEmail(parentRegistrationDto.getEmail()) != null) {
            log.error("Parent registration failed: Parent Email: {} already exists", parentRegistrationDto.getEmail());
            throw new UserException("Parent already registered", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setName(parentRegistrationDto.getName());
        user.setEmail(parentRegistrationDto.getEmail());
        user.setRole(parentRegistrationDto.getRole().toUpperCase());
        user.setPhone(parentRegistrationDto.getPhone());
        user.setStatus(parentRegistrationDto.getStatus());

        User savedUser = userRepository.save(user);

        Parent parent = new Parent();
        parent.setUser(savedUser);
        parent.setStudent(studentUser.getStudent());
        parent.setRelation(parentRegistrationDto.getRelation());

        Parent savedParent = parentRepository.save(parent);

        log.info("Successfully registered Parent: {} with Relation: {} for Student: {}", savedUser.getEmail(), savedParent.getRelation(), studentUser.getEmail());

        return new SuccessResponseProjection<>(true, "Parent created successfully",
                new ParentProjection(savedUser.getUserId(), savedUser.getName(), savedUser.getRole(), savedUser.getEmail(),
                        savedUser.getPhone(), savedUser.getStatus(), savedParent.getRelation(),
                        studentUser.getEmail()));
    }
}
