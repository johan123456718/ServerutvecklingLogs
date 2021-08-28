package com.serverutveckling.logs.repositories;


import com.serverutveckling.logs.objects.logPost;
import org.springframework.data.repository.CrudRepository;

/**
 * A repostitory fetched logs from a database
 */
public interface LogRepository extends CrudRepository<logPost, Integer> {
    Iterable<logPost> findBySender(String UUID);
    Iterable<logPost> findByReciever(String UUID);
}
