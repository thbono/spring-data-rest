package com.example.test.infrastructure;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface CustomQuerydslBinderCustomizer<T extends EntityPath<?>> extends QuerydslBinderCustomizer<T> {

    @Override
    default void customize(QuerydslBindings querydslBindings, T entityPath) {
        querydslBindings.bind(String.class).first(
                (SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}

