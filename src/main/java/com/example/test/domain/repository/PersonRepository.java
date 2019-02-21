package com.example.test.domain.repository;

import com.example.test.domain.model.Person;
import com.example.test.domain.model.QPerson;
import com.example.test.infrastructure.CustomQuerydslBinderCustomizer;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "people", collectionResourceRel = "list")
public interface PersonRepository extends PagingAndSortingRepository<Person, Long>, CustomQuerydslBinderCustomizer<QPerson>,
        QuerydslPredicateExecutor<Person> {
}
