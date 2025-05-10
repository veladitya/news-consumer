package com.natera.consumer.newsconsumer.repository;

import com.natera.consumer.newsconsumer.models.News;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends CrudRepository<News, String> {
}
