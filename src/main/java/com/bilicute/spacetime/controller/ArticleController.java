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

//@CrossOrigin(origins = "*")
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
    @PostMapping("/add")
    public Result add(@RequestBody @Validated Article article){
        if (article == null) {
            return Result.error("文章上传错误");
        }

        if (article.getTitle() == null||article.getTitle().isBlank()) {
            return Result.error("文章标题为空");
        }
        if (article.getCategoryId() == null) {
            return Result.error("文章所属分类为空");
        }
        if (article.getCategoryId()!=3&&(article.getContent() == null||article.getContent().isBlank())) {
            return Result.error("文章内容为空");
        }

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
    @GetMapping(value = {"","/admin"})
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
        if (auditingState!=null&&!auditingState){
            //如果不是管理员
            if (!QuickMethods.isAdmin()){
                PageBean<Article> pageBean = new PageBean<Article>();
                Article article = new Article();
                article.setTitle("您不是管理员");
                List<Article> list = List.of(new Article[]{article});
                pageBean.setItems(list);
                return Result.errorData(pageBean);
            }
            log.info("管理员查询审核列表：用户"+ QuickMethods.getLoggedInUserName());
        }
        PageBean<Article> articlePageBean = articleService.list(pageNum,pageSize,categoryId,state,auditingState);
        return Result.success(articlePageBean);
    }

    @GetMapping("/listWeighting")
    public Result<PageBean<Article>> listWeighting(
            //当前页码
            Integer pageNum,
            //每页条数
            Integer pageSize,
            //文章分类ID（可选）
            @RequestParam(required = false) Integer categoryId,
            //发布状态（可选）
            @RequestParam(required = false) String state
    ){
        PageBean<Article> articlePageBean = articleService.listWeighting(pageNum,pageSize,categoryId,state);
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
        if (!article.getAuditingState()){
            Article articleError = new Article();
            articleError.setTitle("查询的文章未被审核");
            return Result.errorData(articleError);
        }
        //TODO 同ip进行判断，检测IV和PV算法
        articleService.view(id);
        return Result.success(article);
    }

    @GetMapping("/detailAuditing")
    public Result<Article> detailAuditing(@Min(1) @NotNull(message = "请选择详情文章") Integer id){
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

//        articleService.view(id);
        log.info("获取文章：用户"+QuickMethods.getLoggedInUserName()+"获取《{}》(id:{})",article.getTitle(),id);
        return Result.success(article);
    }

    @GetMapping("/like")
    @PatchMapping("/like")
    public Result like(@Validated @Min(1) @NotNull(message = "请指定点赞文章") Integer id){
//        TODO 修改赞只能赞一次
        articleService.like(id);
        return Result.success();
    }

    @GetMapping("/my")
    public Result<Map<String,Integer>> querySelfInfo(){
        Integer loggedInUserId = QuickMethods.getLoggedInUserId();
        Map<String,Integer> total =  articleService.querySelfInfo(loggedInUserId);
        return Result.success(total);
    }


    @PatchMapping("/checked")
    public Result checked(@Min(1) @NotNull(message = "请指定审核文章") Integer id){
        if (!QuickMethods.isAdmin()){
            return Result.error("权限不足");
        }
        Article article = articleService.findById(id);
        if (article == null) {
            return Result.error("指定对象不存在");
        }
        if (Objects.equals(QuickMethods.getLoggedInUserId(), article.getCreateUser())){
            return Result.error("不能审核自己发送的文章");
        }
        if (article.getAuditingState()){
            return Result.error("该文章已经被审核了，无需重复操作");
        }
        articleService.check(id);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(@Min(1) @NotNull(message = "请指定审核文章") Integer id){
        Article article = articleService.findById(id);
        if (article == null) {
            return Result.error("指定对象不存在");
        }
        Integer userId=QuickMethods.getLoggedInUserId();
        if (!QuickMethods.isAdmin()){
            if (!userId.equals(article.getCreateUser())){
                System.out.println(""+!QuickMethods.isAdmin()+!userId.equals(article.getCreateUser()));
                System.out.println("登陆用户id"+QuickMethods.getLoggedInUserId()+",文章创建者id"+article.getCreateUser()+".");
                return Result.error("权限不足");
            }
        }

        articleService.delete(id);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated Article article){
        System.out.println(article);
        if (article.getArticleId()==null) {
            return Result.error("文章ID为空");
        }
        if (!Objects.equals(articleService.findById(article.getArticleId()).getCreateUser(), QuickMethods.getLoggedInUserId())){
            return Result.error("请勿修改他人创建的文章");
        }
        log.info("更新文章：用户"+QuickMethods.getLoggedInUserName()+
                "\t文章原标题《"+articleService.findById(article.getArticleId()).getTitle()+
                "》修改为《"+article.getTitle()+"》");
        articleService.update(article);
        return Result.success();
    }

    @GetMapping("/oneself")
    public Result<PageBean<Article>> listByOneself(
            //当前页码
            Integer pageNum,
            //每页条数
            Integer pageSize,
            //文章分类ID（可选）
            @RequestParam(required = false) Integer categoryId,
            //发布状态（可选）
            @RequestParam(required = false) String state
    ){
        Integer userId = QuickMethods.getLoggedInUserId();
        PageBean<Article> articlePageBean = articleService.listByOneself(pageNum,pageSize,categoryId,state,userId);
        return Result.success(articlePageBean);
    }

}
