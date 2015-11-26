package by.es.ejb.dao;

import by.es.ejb.dto.BestSellingBook;
import by.es.ejb.dto.MostProfitableBook;
import by.es.ejb.entity.Book;

import javax.ejb.Local;
import java.util.List;

@Local
public interface BookDAO {

    Book save(Book book);

    Book update(Book book);

    void remove(Book book);

    boolean updateBookQuantity(Long bookId, int dNumber);

    Book getBookById(int id);

    List<Book> getBookList();

    List<Book> getDeletedBookList();

    Long getNumberOfBooks();
    
    List<BestSellingBook> getBestSellingBooks(int number);

    List<MostProfitableBook> getMostProfitableBooks(int number);

    List<Book> findByText(String text);
}
