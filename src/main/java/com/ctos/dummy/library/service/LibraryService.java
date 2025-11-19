package com.ctos.dummy.library.service;

import com.ctos.dummy.library.entity.Aisle;
import com.ctos.dummy.library.entity.Book;
import com.ctos.dummy.library.entity.Library;
import com.ctos.dummy.library.repository.AisleRepo;
import com.ctos.dummy.library.repository.LibraryRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryService {
    private final LibraryRepo libraryRepo;
    private final AisleRepo aisleRepo;

    public List<Aisle> getAllAislesByLibrary(Long libraryId) {
        Library library = libraryRepo.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Library not found with id: " + libraryId));
        return aisleRepo.findAislesByLibrary(library);
    }

    public Library saveLibrary(Library library) {
        log.info("Saving library: {}", library.getLibraryName());
        return libraryRepo.save(library);
    }

    public Library updateLibrary(Long libraryId, Library libraryDetails) {
        log.info("Updating library with id: {}", libraryId);

        Library library = libraryRepo.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Library not found with id: " + libraryId));

        library.setLibraryName(libraryDetails.getLibraryName());

        Library updatedLibrary = libraryRepo.save(library);
        log.info("Library updated successfully: {}", updatedLibrary.getLibraryName());

        return updatedLibrary;
    }

    public Page<Book> getBooksByAisleAndLibrary(String libraryName, String aisleName, int page, int size) {
        List<Library> libraries = libraryRepo.findByNameLike(libraryName);
        if (libraries.isEmpty()) {
            throw new EntityNotFoundException("Library not found with name: " + libraryName);
        }
        Library library = libraries.get(0);

        List<Aisle> aisles = aisleRepo.findAislesByLibrary(library);

        Aisle targetAisle = aisles.stream()
                .filter(aisle -> aisle.getIsleName().equalsIgnoreCase(aisleName))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aisle not found with name: " + aisleName));

        List<Book> allBooks = targetAisle.getBooks();

        Pageable pageable = PageRequest.of(page, size);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allBooks.size());

        List<Book> pagedBooks = allBooks.subList(start, end);

        return new PageImpl<>(pagedBooks, pageable, allBooks.size());
    }

    public List<Library> getAllLibraries() {
        log.info("Fetching all libraries");
        return libraryRepo.findAll();
    }
}