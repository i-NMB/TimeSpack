package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.Comment;
import com.bilicute.spacetime.pojo.PageBean;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.ArticleService;
import com.bilicute.spacetime.service.CommentService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/comment")
@Slf4j
@Validated
public class CommentController {
    private static ArticleService articleService;
    @Autowired
    private void setArticleService(ArticleService articleService){
        CommentController.articleService = articleService;
    }

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public Result addComment(String content,Integer articleId) {
        Article article = articleService.findById(articleId);
        if (article == null) {
            return Result.error("所属文章不存在");
        }
        commentService.add(content,articleId);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result deleteComment(@NotNull(message = "请输入待删除的评论") Integer commentId) {
        Comment theComment = commentService.findById(commentId);
        if (!QuickMethods.isAdmin()|| !Objects.equals(theComment.getCreateUser(), QuickMethods.getLoggedInUserId())){
            return Result.error("没有权限删除");
        }
        commentService.delete(commentId);
        return Result.success();
    }

    @GetMapping("/num")
    public Result commentNumber(@Min(1) @NotNull(message = "请指定文章") Integer articleId){
        Article article = articleService.findById(articleId);
        if (article == null) {
            return Result.error("指定对象不存在");
        }
        Integer number = commentService.getCommentNumber(articleId);
        return Result.success(number);
    }

    @GetMapping("/get")
    public Result<PageBean<Comment>> list(
            //当前页码
            Integer pageNum,
            //每页条数
            Integer pageSize,
            //文章ID
            @RequestParam(required = false) Integer articleId,
            //审核状态（可选）
            @RequestParam(required = false) Boolean auditingState
    ){
//        if (!(articleId != null&&articleService.findById(articleId)!=null)) {
//            PageBean<Comment> pageBean = new PageBean<Comment>();
//            Comment comment = new Comment();
//            comment.setContent("指定文章不存在");
//            List<Comment> list = List.of(new Comment[]{comment});
//            pageBean.setItems(list);
//            return Result.errorData(pageBean);
//        }
        //如果获取审核的列表
        if (auditingState!=null&&!auditingState){
            //如果不是管理员
            if (!QuickMethods.isAdmin()){
                PageBean<Comment> pageBean = new PageBean<Comment>();
                List<Comment> list = null;
                Comment comment = new Comment();
                comment.setContent("您不是管理员");
                assert false;
                list.add(comment);
                pageBean.setItems(list);
                return Result.errorData(pageBean);
            }
            log.info("管理员查询审核列表：用户"+ QuickMethods.getLoggedInUserName());
        }
        PageBean<Comment> commentPageBean = commentService.list(pageNum,pageSize,articleId,auditingState);
        return Result.success(commentPageBean);
    }

    @PatchMapping("/checked")
    public Result<?> checked(@NotNull(message = "未指定评论id") Integer id){
        Comment comment = commentService.findById(id);
        if (comment == null) {
            return Result.error("指定评论不存在");
        }
        if(!QuickMethods.isAdmin()){
            return Result.error("权限不足");
        }
        if (comment.getAuditingState()){
            return Result.error("该评论已通过审核，无需再次审核");
        }
        commentService.check(id);
        return Result.success();
    }

}
