package com.sexyuncle.springboot.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.loserico.orm.dao.CriteriaOperations;
import com.loserico.orm.dao.EntityOperations;
import com.sexyuncle.springboot.security.entity.Book;
import com.sexyuncle.springboot.security.exception.BookIdMismatchException;
import com.sexyuncle.springboot.security.exception.BookNotFoundException;

@RestController
@RequestMapping("/api/books")
public class BookController {
 
    @Autowired
    private EntityOperations entityOperations;
    
    @Autowired
    private CriteriaOperations criteriaOperations;
 
    @GetMapping
    public Iterable findAll() {
        return entityOperations.findAll(Book.class);
    }
 
    @GetMapping("/title/{bookTitle}")
    public List<Book> findByTitle(@PathVariable String bookTitle) {
        return criteriaOperations.findByProperty(Book.class, "title", bookTitle);
    }
 
    @GetMapping("/{id}")
    public Book findOne(@PathVariable Long id) {
        return entityOperations.findOne(Book.class, id)
          .orElseThrow(BookNotFoundException::new);
    }
 
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return entityOperations.save(book);
    }
 
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        entityOperations.findOne(Book.class, id)
          .orElseThrow(BookNotFoundException::new);
//        bookRepository.delete(id);
    }
 
    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (book.getId() != id) {
          throw new BookIdMismatchException();
        }
        entityOperations.findOne(Book.class, id)
          .orElseThrow(BookNotFoundException::new);
        return entityOperations.save(book);
    }
}