package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.CategoryMapper;
import com.bilicute.spacetime.pojo.Category;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @所属包名: com.bilicute.spacetime.service.impl
 * @类名: CategoryServiceImpl
 * @作者: i囡漫笔
 * @描述: 分类实现类
 * @创建时间: 2024-04-24 10:10
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryMapper categoryMapper;
    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Category findByName(String categoryName) {
        return categoryMapper.findByName(categoryName);
    }

    @Override
    public void add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Integer loggedInUserId = QuickMethods.getLoggedInUserId();
        category.setCreateUser(loggedInUserId);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        //传入id到categoryMapper
        return categoryMapper.list();
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public Category findByAliasName(String categoryAlias) {
        return categoryMapper.findByAliasName(categoryAlias);
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
       categoryMapper.delete(id);
    }
}
