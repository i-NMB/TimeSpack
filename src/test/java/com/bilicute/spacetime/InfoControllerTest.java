package com.bilicute.spacetime;

import com.bilicute.spacetime.controller.InfoController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @所属包名: com.bilicute.spacetime
 * @类名: InfoControllerTest
 * @作者: i囡漫笔
 * @描述: InfoController测试类
 * @创建时间: 2024-07-31 10:06
 */

@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.MethodName.class)
public class InfoControllerTest {
    private final HttpServletRequest request = new MockHttpServletRequest();
    private final HttpServletResponse response = new MockHttpServletResponse();

    @Autowired
    private InfoController infoController;

    @Test
    public void a() {
        UserControllerTest.loginTestUser(response);
        infoController.maxViewArticle();
        infoController.maxLikeArticle();
        infoController.maxLikeComment();
        infoController.allArticle();
        infoController.allComment();
        infoController.allViewByArticle();
        infoController.allLikeByComment();
        infoController.allLikeByArtiCle();
    }

}
