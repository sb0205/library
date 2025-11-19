package com.ctos.dummy.library.service;

import com.ctos.dummy.library.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;

}