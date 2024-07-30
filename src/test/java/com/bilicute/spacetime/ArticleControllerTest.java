package com.bilicute.spacetime;

import com.bilicute.spacetime.controller.ArticleController;
import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.utils.JwtUtil;
import com.bilicute.spacetime.utils.Sha256;
import com.bilicute.spacetime.utils.ThreadLocalUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @所属包名: com.bilicute.spacetime
 * @类名: ArticleControllerTest
 * @作者: i囡漫笔
 * @描述: ArticleController的测试类
 * @创建时间: 2024-07-30 11:57
 */

@SpringBootTest
@Slf4j
public class ArticleControllerTest {

    private final HttpServletRequest request = new MockHttpServletRequest();
    private final HttpServletResponse response = new MockHttpServletResponse();
    private final Article article;
    @Autowired
    private ArticleController articleController;

    public ArticleControllerTest() {
        Article article = new Article();
        article.setTitle("测试");
        article.setArticleId(1);
        article.setContent("这是一篇测试文章");
        article.setCategoryId(2);
        this.article = article;
    }

    public static Article getNewArticle() {
        Article article = new Article();
        article.setTitle("测试");
        article.setArticleId(1);
        article.setContent("这是一篇测试文章");
        article.setCategoryId(2);
        return article;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void a() {
        UserControllerTest.loginTestUser(response);
        //正确添加
        articleController.add(article, request, response);
        //重复添加测试
        articleController.add(article, request, response);
        //空实体测试
        articleController.add(null, request, response);
        //空标题测试
        Article article1 = getNewArticle();
        article1.setTitle(null);
        articleController.add(article1, request, response);
        article1.setTitle("");
        articleController.add(article1, request, response);
        //空分类测试
        Article article2 = getNewArticle();
        article2.setCategoryId(null);
        articleController.add(article2, request, response);
        //空内容测试
        Article article3 = getNewArticle();
        article3.setContent(null);
        articleController.add(article3, request, response);
        article3.setContent("");
        articleController.add(article3, request, response);
        article3.setCategoryId(3);
        articleController.add(article3, request, response);
        //修改密码敏感操作测试
        UserControllerTest.changePasswordTestUser();
        articleController.add(article, request, response);
    }

    @Test
    public void b() {
        UserControllerTest.loginTestUser(response);
        //非管理正常测试
        articleController.list(1, 1, 2, null, null);
        //管理测试
        //查询审核的
        articleController.list(1, 1, 2, null, true);
        //查询未审核的
        articleController.list(1, 1, 2, null, false);
        //管理员
        UserControllerTest.getAdminTestUser(response);
        articleController.list(1, 1, 2, null, false);
    }

    @Test
    public void c() {
        UserControllerTest.loginTestUser(response);
        //正常测试
        articleController.listWeighting(1, 1, 2, null);
    }

    @Test
    public void d() {
        //测试未通过审核的文章
        articleController.detail(1);
        //测试通过审核的文章
        articleController.detail(2);
        //测试查询未知文章
        articleController.detail(3);
    }

    @Test
    public void e() {
        UserControllerTest.loginTestUser(response);
        //发布者本人测试未通过审核的文章
        articleController.detailAuditing(1);
        //测试查询未知文章
        articleController.detailAuditing(3);
        //测试通过审核的文章
        articleController.detailAuditing(2);
        //管理员查询未通过
        UserControllerTest.getAdminTestUser(response);
        articleController.detailAuditing(1);
        //普通用户查询
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 11);
        claims.put("username", "test2");
        claims.put("SHA2password", Sha256.addSalt(Sha256.addSalt("ValidPass1")));
        String token = JwtUtil.genToken(claims);
        // 假设ThreadLocalUtil.set()是用来设置ThreadLocal的值
        ThreadLocalUtil.set(claims);
        Cookie cookie = new Cookie("jwt", token);
        cookie.setMaxAge(5 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        articleController.detailAuditing(1);
    }

    @Test
    public void f() {
        UserControllerTest.loginTestUser(response);
        //测试正常
        articleController.like(1, request, response);
        //修改密码敏感测试
        UserControllerTest.changePasswordTestUser();
        articleController.like(1, request, response);
    }

    @Test
    public void g() {
        UserControllerTest.loginTestUser(response);
        //测试正常
        articleController.querySelfInfo();
    }

    @Test
    public void h() {
        UserControllerTest.loginTestUser(response);
        //审核自己发布的文章
        articleController.checked(1, request, response);
        //撤销审核自己发布的文章
        articleController.unchecked(1, request, response);
        //文章不存在
        articleController.checked(3, request, response);
        //撤销不存在的文章
        articleController.unchecked(3, request, response);
        //修改密码后敏感操作
        UserControllerTest.changePasswordTestUser();
        articleController.checked(3, request, response);
        UserControllerTest.loginTestUser(response);
        UserControllerTest.changePasswordTestUser();
        articleController.unchecked(3, request, response);

        //普通用户查询（权限不足测试）
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 11);
        claims.put("username", "test2");
        claims.put("SHA2password", Sha256.addSalt(Sha256.addSalt("ValidPass1")));
        String token = JwtUtil.genToken(claims);
        // 假设ThreadLocalUtil.set()是用来设置ThreadLocal的值
        ThreadLocalUtil.set(claims);
        Cookie cookie = new Cookie("jwt", token);
        cookie.setMaxAge(5 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        articleController.checked(1, request, response);
        articleController.unchecked(1, request, response);
        //管理员审核
        UserControllerTest.getAdminTestUser(response);
        articleController.checked(1, request, response);
        articleController.checked(1, request, response);
        articleController.unchecked(1, request, response);
        articleController.unchecked(1, request, response);
    }


    @Test
    public void i() {
        //删除测试
        //普通用户查询（权限不足测试）
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 11);
        claims.put("username", "test2");
        claims.put("SHA2password", Sha256.addSalt(Sha256.addSalt("ValidPass1")));
        String token = JwtUtil.genToken(claims);
        // 假设ThreadLocalUtil.set()是用来设置ThreadLocal的值
        ThreadLocalUtil.set(claims);
        Cookie cookie = new Cookie("jwt", token);
        cookie.setMaxAge(5 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        articleController.delete(1, request, response);
        //删除不存在文章
        articleController.delete(3, request, response);

        //正常删除
        UserControllerTest.loginTestUser(response);
        articleController.delete(1, request, response);
        //正确添加
        articleController.add(article, request, response);
        //修改密码后敏感操作
        UserControllerTest.changePasswordTestUser();
        articleController.delete(1, request, response);


        //管理员删除
        UserControllerTest.getAdminTestUser(response);
        articleController.delete(1, request, response);
    }

    @Test
    public void j() {
        UserControllerTest.loginTestUser(response);
        articleController.listByOneself(1, 1, null, null);
    }
}
