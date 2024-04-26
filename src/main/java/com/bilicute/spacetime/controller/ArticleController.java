package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.PageBean;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.ArticleService;
import com.bilicute.spacetime.utils.StringUtilsFromTime;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @所属包名: com.bilicute.spacetime.controller
 * @类名: ArticleController
 * @作者: i囡漫笔
 * @描述: 文章controller响应
 * @创建时间: 2024-04-24 19:20
 */

@RestController
@RequestMapping("/article")
@Validated
@Slf4j
public class ArticleController {

    private static ArticleService articleService;
    @Autowired
    private void setArticleService(ArticleService articleService){
        ArticleController.articleService = articleService;
    }

    /**
     * @param article:  [article] 文章实体
     * @return Result
     * @author i囡漫笔
     * @description 添加文章
     * @date 2024/4/25
     */
    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        log.info("创建文章：用户"+ QuickMethods.getLoggedInUserName()+"\t文章标题："+article.getTitle());
        return Result.success();
    }

    /**
     * @param pageNum: 页数
     * @param pageSize: 页面大小
     * @param categoryId: 文章所属分类(可选)
     * @param state: 文章状态：草稿/已发布(可选)
     * @param auditingState: 审核状态：true/false
     * @return Result<PageBean<Article>>
     * @author i囡漫笔
     * @description 分页查询，
     * @date 2024/4/25
     */
    @GetMapping
    public Result<PageBean<Article>> list(
            //当前页码
            Integer pageNum,
            //每页条数
            Integer pageSize,
            //文章分类ID（可选）
            @RequestParam(required = false) Integer categoryId,
            //发布状态（可选）
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Boolean auditingState
    ){
        //如果获取审核的列表
        if (!auditingState){
            //如果不是管理员
            if (!QuickMethods.isAdmin()){
                PageBean<Article> pageBean = new PageBean<Article>();
                List<Article> list = null;

                Article article = new Article();
                article.setTitle("您不是管理员");
                assert false;
                list.add(article);
                pageBean.setItems(list);
                return Result.errorData(pageBean);
            }
            log.info("管理员查询审核列表：用户"+ QuickMethods.getLoggedInUserName());
        }
        PageBean<Article> articlePageBean = articleService.list(pageNum,pageSize,categoryId,state,auditingState);
        return Result.success(articlePageBean);
    }

    @GetMapping("/detail")
    public Result<Article> detail(@Min(1) @NotNull(message = "请选择详情文章") Integer id){
        Article article =articleService.findById(id);
        if (article == null) {
            Article articleNull = new Article();
            articleNull.setTitle("查询的文章数据为空");
            return Result.errorData(articleNull);
        }
        //用户如果不是管理员权限不能查看还未审核的文章
        if (article.getAuditingState()||!Objects.equals(QuickMethods.getLoggedInUserId(), article.getCategoryId())){
            if (!QuickMethods.isAdmin()){
                Article articleErr = new Article();
                articleErr.setTitle("没有权限查看未审核的文章");
                return Result.errorData(articleErr);
            }
        }
        //TODO 同ip进行判断，检测IV和PV算法
        articleService.view(id);
        log.info("获取文章：用户"+QuickMethods.getLoggedInUserName()+"获取《{}》(id:{})",article.getTitle(),id);
        return Result.success(article);
    }

    @PatchMapping("/like")
    public Result like(@Validated @Min(1) @NotNull(message = "请指定点赞文章") Integer id){
        articleService.like(id);
        return Result.success();
    }

    @GetMapping("/my")
    public Result<Map<String,Integer>> querySelfInfo(){
        Integer loggedInUserId = QuickMethods.getLoggedInUserId();
        Map<String,Integer> total =  articleService.querySelfInfo(loggedInUserId);
        return Result.success(total);
    }
    @GetMapping("/all")
    public Result queryAllInfo(){
        //TODO 待完成查询全部用户平均点赞阅览量数据
        articleService.queryAllInfo();
        return Result.success();
    }

    @PatchMapping("/checked")
    public Result checked(@Min(1) @NotNull(message = "请指定审核文章") Integer id){
        if (!QuickMethods.isAdmin()){
            return Result.error("权限不足");
        }
        Article article = articleService.findById(id);
        if (Objects.equals(QuickMethods.getLoggedInUserId(), article.getCategoryId())){
            return Result.error("不能审核自己发送的文章");
        }
        if (article.getAuditingState()){
            return Result.error("该文章已经被审核了，无需重复操作");
        }
        articleService.check(id);
        return Result.success();
    }

    //用户关注
    @PostMapping("/follow")
    public Result follow(@RequestParam("followedUserId") Integer followedUserId) {
        articleService.follow(followedUserId);
        return Result.success();
    }

//    @GetMapping("/view/{id}")
//    public Result viewArticle(@PathVariable Integer id) {
//        // 增加浏览次数
//        articleService.incrementViewCount(id);
//
//        //获取文章详情
//        Article article = articleService.findById(id);
//
//        // 返回文章详情
//        return Result.success(article);
//    }
}
