package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Category;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @所属包名: com.bilicute.spacetime.controller
 * @类名: CategoryController
 * @作者: i囡漫笔
 * @描述: 文章分类实现类
 * @创建时间: 2024-04-19 12:18
 */

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * @param category:  接收category的所有数据
     * @return Result
     * @author i囡漫笔
     * @description 通过接收前端传来的category的所有数据并添加到数据库中
     * @date 2024/4/24
     */
    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        if(!QuickMethods.isAdmin()){
            return Result.error("此操作只有管理员可执行");
        }
        String categoryName = category.getCategoryName();
        Category hasCategory = categoryService.findByName(categoryName);
        Category hasCategoryByAlias = categoryService.findByAliasName(category.getCategoryAlias());
        if (hasCategory != null) {
            return Result.error("该分类名已经存在");
        }
        if (hasCategoryByAlias != null) {
            return Result.error("该分类别称已经存在");
        }
        categoryService.add(category);
        log.info("新增分类："+categoryName+"\t操作用户"+ QuickMethods.getLoggedInUserName());
        return Result.success();
    }

    /**
     * @return Result<List < Category>>
     * @author i囡漫笔
     * @description 通过GET，获取所有分类
     * @date 2024/4/24
     */
    @GetMapping
    public Result<List<Category>> list(){
        List<Category> cs = categoryService.list();
        log.info(QuickMethods.getLoggedInUserName()+"查看所有分类");
        return Result.success(cs);
    }

    /**
     * @param id:  [id]
     * @return Result<Category>
     * @author i囡漫笔
     * @description 通过GET传递的分类ID，来响应指定分类详情
     * @date 2024/4/24
     */
    @GetMapping("/detail")
    public Result<Category> detail(Integer id){
        Category category = categoryService.findById(id);
        if (category == null) {
            return Result.error("未找到id为 "+id+" 的分类详细");
        }
        return Result.success(category);
    }

    /**
     * @param category:  [category]
     * @return Result
     * @author i囡漫笔
     * @description 通过处理Put传递的JSON，对文章分类进行更新
     * @date 2024/4/24
     */
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        if(!QuickMethods.isAdmin()){
            return Result.error("此操作只有管理员可执行");
        }
        //获取文章ID
        Integer ID = category.getCategoryId();
        //查找是否含有输入的id的文章分类
        Category byId = categoryService.findById(ID);
        if (byId == null) {
            return Result.error("找不到指定分类");
        }
        //查找是否有重复名字
        String categoryName = category.getCategoryName();
        Category hasCategory = categoryService.findByName(categoryName);
        if (hasCategory != null) {
            return Result.error("该分类名已经存在");
        }
        log.info("更新文章分类："+ID+"\t操作用户"+QuickMethods.getLoggedInUserName());
        categoryService.update(category);
        return Result.success();
    }

    /**
     * @param id:  [id]
     * @return Result
     * @author i囡漫笔
     * @description 删除指定id的文章分类
     * @date 2024/4/24
     */
    @DeleteMapping
    public Result delete(@Validated(Category.Delete.class) Integer id){
        if(!QuickMethods.isAdmin()){
            return Result.error("此操作只有管理员可执行");
        }
        //查找是否含有输入的id的文章分类
        Category byId = categoryService.findById(id);
        if (byId == null) {
            return Result.error("找不到指定分类");
        }
        log.info("删除文章分类："+byId.getCategoryName()+"\t操作用户"+QuickMethods.getLoggedInUserName());
        categoryService.delete(id);
        return Result.success();
    }
}
