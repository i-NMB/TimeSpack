package com.bilicute.spacetime;

import com.bilicute.spacetime.controller.CategoryController;
import com.bilicute.spacetime.pojo.Category;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @所属包名: com.bilicute.spacetime
 * @类名: CategoryControllerTest
 * @作者: i囡漫笔
 * @描述: CategoryController测试类
 * @创建时间: 2024-07-31 01:17
 */

@SpringBootTest
@Slf4j
public class CategoryControllerTest {

    private final HttpServletRequest request = new MockHttpServletRequest();
    private final HttpServletResponse response = new MockHttpServletResponse();
    private final Category category;
    @Autowired
    private CategoryController categoryController;

    public CategoryControllerTest() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("test");
        category.setCategoryAlias("测试分类");
        category.setCreateUser(1);
        this.category = category;
    }

    public static Category newCategorySameAlias() {
        Category category = new Category();
        category.setCategoryId(100);
        category.setCategoryName("noOnly");
        category.setCategoryAlias("测试分类");
        category.setCreateUser(1);
        return category;
    }

    public static Category newCategorySameName() {
        Category category = new Category();
        category.setCategoryId(101);
        category.setCategoryName("test");
        category.setCategoryAlias("不相同");
        category.setCreateUser(1);
        return category;
    }

    public static Category newCategorySameOtherName() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("文章");
        category.setCategoryAlias("测试");
        category.setCreateUser(1);
        return category;
    }

    public static Category newCategorySameOtherAlias() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("测试");
        category.setCategoryAlias("text");
        category.setCreateUser(1);
        return category;
    }


    @Test
    public void a() {
        //非管理员
        //普通用户添加
        UserControllerTest.loginTestUser(response);
        categoryController.add(category, request, response);
        //改密测试
        UserControllerTest.changePasswordTestUser();
        categoryController.add(category, request, response);
        //正确添加
        UserControllerTest.getAdminTestUser(response);
        categoryController.add(category, request, response);
        //存在分类名称
        categoryController.add(newCategorySameName(), request, response);
        //存在分类别称
        categoryController.add(newCategorySameAlias(), request, response);
    }

    @Test
    public void b() {
        categoryController.list();
    }

    @Test
    public void c() {
        //正确查询
        categoryController.detail(1);
        //查询为空
        categoryController.detail(10000);
    }

    @Test
    public void d() {
        //非管理员
        //普通用户更新
        UserControllerTest.loginTestUser(response);
        categoryController.update(category, request, response);
        //改密测试
        UserControllerTest.changePasswordTestUser();
        categoryController.update(category, request, response);
        //正确更新
        UserControllerTest.getAdminTestUser(response);
        categoryController.update(category, request, response);


        //存在分类名称
        Category category2 = newCategorySameName();
        category2.setCategoryId(1);
        categoryController.update(category2, request, response);
        categoryController.update(newCategorySameName(), request, response);
        categoryController.update(newCategorySameOtherName(), request, response);
        //存在分类别称
        Category category3 = newCategorySameAlias();
        category3.setCategoryId(1);
        categoryController.update(category3, request, response);
        categoryController.update(newCategorySameAlias(), request, response);
        categoryController.update(newCategorySameOtherAlias(), request, response);
    }


    @Test
    public void e() {
        //非管理员
        //普通用户删除分类
        UserControllerTest.loginTestUser(response);
        categoryController.delete(1, request, response);
        //改密测试
        UserControllerTest.changePasswordTestUser();
        categoryController.delete(1, request, response);
        //正确删除
        UserControllerTest.getAdminTestUser(response);
        categoryController.delete(1, request, response);
        //删除的id不存在
        categoryController.delete(1, request, response);
    }
}
