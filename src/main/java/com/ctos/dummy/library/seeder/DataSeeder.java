package com.ctos.dummy.library.seeder;

import com.ctos.dummy.library.entity.Aisle;
import com.ctos.dummy.library.entity.Book;
import com.ctos.dummy.library.entity.Library;
import com.ctos.dummy.library.repository.AisleRepo;
import com.ctos.dummy.library.repository.BookRepo;
import com.ctos.dummy.library.repository.LibraryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {
    private final LibraryRepo libraryRepo;
    private final AisleRepo aisleRepo;
    private final BookRepo bookRepo;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting database seeding...");

        Library centralLibrary = createLibrary("CENTRAL LIBRARY");
        Library cityLibrary = createLibrary("CITY LIBRARY");
        Library publicLibrary = createLibrary("PUBLIC LIBRARY");

        log.info("Created {} libraries", 3);

        Aisle naturalHistory = createAisle("NATURAL HISTORY", centralLibrary);
        addBooksToAisle(naturalHistory,
                "The Origin of Species",
                "A Brief History of Time",
                "Sapiens"
        );
        log.debug("Added {} books to aisle '{}'", 3, naturalHistory.getIsleName());

        Aisle scienceFiction = createAisle("SCIENCE FICTION", centralLibrary);
        addBooksToAisle(scienceFiction,
                "Dune",
                "Foundation",
                "1984",
                "Brave New World"
        );
        log.debug("Added {} books to aisle '{}'", 4, scienceFiction.getIsleName());

        log.info("Seeded library '{}' with {} aisles", centralLibrary.getLibraryName(), 2);

        Aisle mystery = createAisle("MYSTERY", cityLibrary);
        addBooksToAisle(mystery,
                "The Da Vinci Code",
                "Gone Girl"
        );
        log.debug("Added {} books to aisle '{}'", 2, mystery.getIsleName());

        Aisle romance = createAisle("ROMANCE", cityLibrary);
        addBooksToAisle(romance,
                "Pride and Prejudice",
                "The Notebook",
                "Me Before You"
        );
        log.debug("Added {} books to aisle '{}'", 3, romance.getIsleName());

        Aisle biography = createAisle("BIOGRAPHY", cityLibrary);
        addBooksToAisle(biography,
                "Steve Jobs",
                "Becoming",
                "Long Walk to Freedom",
                "The Diary of a Young Girl"
        );
        log.debug("Added {} books to aisle '{}'", 4, biography.getIsleName());

        log.info("Seeded library '{}' with {} aisles", cityLibrary.getLibraryName(), 3);

        Aisle fantasy = createAisle("FANTASY", publicLibrary);
        addBooksToAisle(fantasy,
                "Harry Potter and the Philosopher's Stone",
                "The Hobbit",
                "The Lord of the Rings"
        );
        log.debug("Added {} books to aisle '{}'", 3, fantasy.getIsleName());

        Aisle classics = createAisle("CLASSICS", publicLibrary);
        addBooksToAisle(classics,
                "To Kill a Mockingbird",
                "Moby Dick",
                "War and Peace",
                "The Great Gatsby"
        );
        log.debug("Added {} books to aisle '{}'", 4, classics.getIsleName());

        log.info("Seeded library '{}' with {} aisles", publicLibrary.getLibraryName(), 2);

        long totalLibraries = libraryRepo.count();
        long totalAisles = aisleRepo.count();
        long totalBooks = bookRepo.count();

        log.info("=".repeat(50));
        log.info("Database seeded successfully!");
        log.info("Total Libraries: {}", totalLibraries);
        log.info("Total Aisles: {}", totalAisles);
        log.info("Total Books: {}", totalBooks);
        log.info("=".repeat(50));
    }

    private Library createLibrary(String name) {
        Library library = new Library();
        library.setLibraryName(name);
        Library savedLibrary = libraryRepo.save(library);
        log.debug("Created library: {}", name);
        return savedLibrary;
    }

    private Aisle createAisle(String name, Library library) {
        Aisle aisle = new Aisle();
        aisle.setIsleName(name);
        aisle.setLibrary(library);
        Aisle savedAisle = aisleRepo.save(aisle);
        log.debug("Created aisle: {} in library: {}", name, library.getLibraryName());
        return savedAisle;
    }

    private void addBooksToAisle(Aisle aisle, String... bookTitles) {
        for (String title : bookTitles) {
            Book book = new Book();
            book.setBookName(title);
            book = bookRepo.save(book);

            aisle.addBook(book);
            log.trace("Added book '{}' to aisle '{}'", title, aisle.getIsleName());
        }

        aisleRepo.save(aisle);
    }
}