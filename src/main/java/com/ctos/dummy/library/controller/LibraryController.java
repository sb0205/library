package com.ctos.dummy.library.controller;

import com.ctos.dummy.library.entity.Book;
import com.ctos.dummy.library.entity.Library;
import com.ctos.dummy.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/libraries")
@Slf4j
public class LibraryController {
    private final LibraryService libraryService;

    @PostMapping
    public ResponseEntity<Library> saveLibrary(@RequestBody Library library) {
        log.info("REST: Received request to save library");
        Library savedLibrary = libraryService.saveLibrary(library);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLibrary);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> updateLibrary(
            @PathVariable Long id,
            @RequestBody Library library) {
        log.info("REST: Received request to update library with id: {}", id);
        Library updatedLibrary = libraryService.updateLibrary(id, library);
        return ResponseEntity.ok(updatedLibrary);
    }

    @GetMapping("/books")
    public ResponseEntity<Page<Book>> getBooksByAisleAndLibrary(@RequestParam String libraryName, @RequestParam String aisleName, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Book> books = libraryService.getBooksByAisleAndLibrary(libraryName, aisleName, page, size);
        return ResponseEntity.ok(books);
    }

    @GetMapping
    public ResponseEntity<List<Library>> getAllLibraries() {
        log.info("REST: Received request to get all libraries");
        List<Library> libraries = libraryService.getAllLibraries();
        return ResponseEntity.ok(libraries);
    }
}
