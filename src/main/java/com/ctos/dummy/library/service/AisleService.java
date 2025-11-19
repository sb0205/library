package com.ctos.dummy.library.service;

import com.ctos.dummy.library.repository.AisleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AisleService {
    private final AisleRepo aisleRepo;
    
}
