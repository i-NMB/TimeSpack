package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.Category;

import java.util.List;

public interface CategoryService {
    Category findByName(String categoryName);

    void add(Category category);

    List<Category> list();

    Category findById(Integer id);

    Category findByAliasName(String categoryAlias);

    void update(Category category);

    void delete(Integer id);
}
