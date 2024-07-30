var apiLinks = (function () {
    /*****
     * 用户登录注册操作API
     *****/
        //登录API
    var Login_Link = "http://127.0.0.1:6066/api/user/login";
    //注册API
    var Registration_Link = "http://127.0.0.1:6066/api/user/register";
    //退出登录API
    var Login_Out_Link = "http://127.0.0.1:6066/api/user/out";


    /*****
     * 用户信息获取 API
     *****/
        //查询指定用户详细信息api
    var User_Get_Link = "http://127.0.0.1:6066/api/user/getUser";
    //获取已登录用户信息API
    //http://127.0.0.1:6066/api/user/userInfo 或 './user.json'、../user.json
    var User_Information_Link = "http://127.0.0.1:6066/api/user/userInfo";
    //获取已登录用户“所有文章总阅读量”信息API
    var User_All_View_From_Article_Link = "http://127.0.0.1:6066/api/info/allViewByArt";
    //获取已登录用户“所有文章总赞量”信息API
    var User_All_Like_From_Article_Link = "http://127.0.0.1:6066/api/info/allLikeByArt";
    //获取已登录用户“所有评论总赞量”信息API
    var User_All_Like_From_Comment_Link = "http://127.0.0.1:6066/api/info/allLikeByCom";
    //获取已登陆用户的“所有文章总数”信息api
    var User_All_Number_From_Article_Link = "http://127.0.0.1:6066/api/info/allArt";
    //获取已登陆用户的“所有评论总数”信息api
    var User_All_Number_From_Comment_Link = "http://127.0.0.1:6066/api/info/allCom";
    //获取已登录用户的 “最多获赞数的文章” API
    var User_Max_Like_From_Article_Link = "http://127.0.0.1:6066/api/info/maxLikeArt";
    //获取已登录用户的 “最多阅读数的文章” API
    var User_Max_View_From_Article_Link = "http://127.0.0.1:6066/api/info/maxViewArt";
    //获取已登录用户的 “最多获赞数的评论” API
    var User_Max_Like_From_Comment_Link = "http://127.0.0.1:6066/api/info/maxLikeCom";

    /*****
     * 修改用户信息 API
     *****/
        //更新用户昵称
    var Update_User_Nickname_Link = "http://127.0.0.1:6066/api/user/updateNickname";
    //更新用户邮箱
    var Update_User_Mail_Link = "http://127.0.0.1:6066/api/user/updateMail";
    //更新用户手机号
    var Update_User_Phone_Link = "http://127.0.0.1:6066/api/user/updatePhone";
    //通过邮箱修改密码
    var Update_User_Password_By_Mail_API_Link = "http://127.0.0.1:6066/api/user/changePasswordByMail";
    //通过短信修改密码
    var Update_User_Password_By_Phone_API_Link = "http://127.0.0.1:6066/api/user/changePasswordByPhone";
    //更新用户头像
    var Update_User_Pic_Link = "http://127.0.0.1:6066/api/user/updateAvatar";


    /*****
     * 验证码发送API
     *****/
        //新用户发送邮箱API
    var Email_Sending_API_Link = "http://127.0.0.1:6066/api/getCode/mail";
    //新用户发送手机API
    var Phone_Sending_API_Link = "http://127.0.0.1:6066/api/getCode/phone";
    //已登陆用户发送邮箱验证码
    var Logined_Email_Sending_Link = "http://127.0.0.1:6066/api/getCode/mailByUser";
    //已登陆用户发送短信验证码
    var Logined_Phone_Sending_Link = "http://127.0.0.1:6066/api/getCode/phoneByUser";


    /*****
     * 分类相关API
     *****/
        //查询指定分类详情
    var Category_Detail_Link = "http://127.0.0.1:6066/api/category/detail";
    //获取所有文章分类//../article.json 或 http://127.0.0.1:6066/api/category
    var Category_All_Link = "http://127.0.0.1:6066/api/category";


    /*****
     * 文章相关API
     *****/
        //文章阅览分页API
    var Article_API_Link = "http://127.0.0.1:6066/api/article/listWeighting";
    //文章详情页API
    var Article_Detail_Link = "http://127.0.0.1:6066/api/article/detail";
    //文章点赞
    var Article_Like_Link = "http://127.0.0.1:6066/api/article/like";
    //添加文章
    var Article_Add_Link = "http://127.0.0.1:6066/api/article/add";
    //删除文章
    var Article_Delete_Link = "http://127.0.0.1:6066/api/article/delete";
    //上传图片
    var Img_Up_Link = "http://127.0.0.1:6066/api/upImg";
    //获取已登录用户自身发布的所有文章//http://127.0.0.1:6066/api/article/oneself  或  ../oneself.json（仅供测试）
    var Article_All_Myself_Link = "http://127.0.0.1:6066/api/article/oneself";
    //管理员获取未审核文章//../oneself.json 或 http://127.0.0.1:6066/api/article/admin
    var Article_Admin_Link = "http://127.0.0.1:6066/api/article/admin";
    //管理员审核文章
    var Article_Pass_Link = "http://127.0.0.1:6066/api/article/checked";


    /*****
     * 评论相关API
     *****/
        //获取指定文章的评论数量
    var Comment_Number_Link = "http://127.0.0.1:6066/api/comment/num";
    //分页获取指定文章的评论
    var Comment_Get_By_Paging_Link = "http://127.0.0.1:6066/api/comment/get";
    //点赞评论
    var Comment_Like_Link = "http://127.0.0.1:6066/api/comment/like";
    //添加评论
    var Comment_Add_Link = "http://127.0.0.1:6066/api/comment/add";
    //获取已登录用户自身发布的所有评论//http://127.0.0.1:6066/api/comment/oneself 或  ../comment.json
    var Comment_All_Myself_Link = "http://127.0.0.1:6066/api/comment/oneself";
    //管理员获取未审核评论//../comment.json 或 http://127.0.0.1:6066/api/comment/admin
    var Comment_Admin_Link = "http://127.0.0.1:6066/api/comment/admin";
    //管理员审核评论
    var Comment_Pass_Link = "http://127.0.0.1:6066/api/comment/checked";
    //评论删除
    var Comment_Delete_Link = "http://127.0.0.1:6066/api/comment/delete";


    /*****
     * 文件上传API
     *****/
    var Update_Link = "http://127.0.0.1:6066/api/upload";


    /*****
     * 第三方API
     *****/
        //一言（一句话）
    var yiyan_Link = "https://api.xygeng.cn/openapi/one";


    return {
        Login_Link: Login_Link,
        Registration_Link: Registration_Link,
        Login_Out_Link: Login_Out_Link,
        User_Get_Link: User_Get_Link,
        User_Information_Link: User_Information_Link,
        User_All_View_From_Article_Link: User_All_View_From_Article_Link,
        User_All_Like_From_Article_Link: User_All_Like_From_Article_Link,
        User_All_Like_From_Comment_Link: User_All_Like_From_Comment_Link,
        User_All_Number_From_Article_Link: User_All_Number_From_Article_Link,
        User_All_Number_From_Comment_Link: User_All_Number_From_Comment_Link,
        User_Max_Like_From_Article_Link: User_Max_Like_From_Article_Link,
        User_Max_View_From_Article_Link: User_Max_View_From_Article_Link,
        User_Max_Like_From_Comment_Link: User_Max_Like_From_Comment_Link,
        Update_User_Nickname_Link: Update_User_Nickname_Link,
        Update_User_Mail_Link: Update_User_Mail_Link,
        Update_User_Phone_Link: Update_User_Phone_Link,
        Update_User_Password_By_Mail_API_Link: Update_User_Password_By_Mail_API_Link,
        Update_User_Password_By_Phone_API_Link: Update_User_Password_By_Phone_API_Link,
        Update_User_Pic_Link: Update_User_Pic_Link,
        Email_Sending_API_Link: Email_Sending_API_Link,
        Phone_Sending_API_Link: Phone_Sending_API_Link,
        Logined_Email_Sending_Link: Logined_Email_Sending_Link,
        Logined_Phone_Sending_Link: Logined_Phone_Sending_Link,
        Category_Detail_Link: Category_Detail_Link,
        Category_All_Link: Category_All_Link,
        Article_API_Link: Article_API_Link,
        Article_Detail_Link: Article_Detail_Link,
        Article_Like_Link: Article_Like_Link,
        Article_Add_Link: Article_Add_Link,
        Article_Delete_Link: Article_Delete_Link,
        Img_Up_Link: Img_Up_Link,
        Article_All_Myself_Link: Article_All_Myself_Link,
        Article_Admin_Link: Article_Admin_Link,
        Article_Pass_Link: Article_Pass_Link,
        Comment_Number_Link: Comment_Number_Link,
        Comment_Get_By_Paging_Link: Comment_Get_By_Paging_Link,
        Comment_Like_Link: Comment_Like_Link,
        Comment_Add_Link: Comment_Add_Link,
        Comment_All_Myself_Link: Comment_All_Myself_Link,
        Comment_Admin_Link: Comment_Admin_Link,
        Comment_Pass_Link: Comment_Pass_Link,
        Comment_Delete_Link: Comment_Delete_Link,
        Update_Link: Update_Link,
        yiyan_Link: yiyan_Link
    };
})();
