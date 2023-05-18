package com.cqtalk.service.develop.user;

import com.cqtalk.entity.user.dto.RegisterDTO;
import org.springframework.stereotype.Service;

@Service
public interface SignLoginDevService {

    void register(RegisterDTO registerDTO);

}
