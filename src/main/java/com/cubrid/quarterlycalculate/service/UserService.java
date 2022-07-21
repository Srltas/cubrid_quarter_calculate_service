package com.cubrid.quarterlycalculate.service;

import com.cubrid.quarterlycalculate.model.User;
import com.cubrid.quarterlycalculate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
