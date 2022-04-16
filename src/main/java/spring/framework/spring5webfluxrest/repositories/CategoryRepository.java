package spring.framework.spring5webfluxrest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import spring.framework.spring5webfluxrest.domain.Category;

@Repository
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
