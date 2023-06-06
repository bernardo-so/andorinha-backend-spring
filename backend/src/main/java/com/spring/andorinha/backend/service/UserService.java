package com.spring.andorinha.backend.service;

import com.spring.andorinha.backend.entity.UserEntity;
import com.spring.andorinha.backend.record.UserRecord;
import com.spring.andorinha.backend.repository.UserRepository;
import com.spring.andorinha.backend.utils.helper.ConverterHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserRecord> getAll() {
        List<UserEntity> userEntity = userRepository.findAll();
        return ConverterHelper.convertListEntitiesToListRecords(userEntity, UserRecord.class);
    }
}
