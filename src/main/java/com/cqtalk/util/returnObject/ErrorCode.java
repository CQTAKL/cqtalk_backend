package com.cqtalk.util.returnObject;

public enum ErrorCode {

    UNKNOWN_ERROR("99999", "未知错误"),
    POST_INSERT_ERROR("400", "帖子插入失败，请重试或联系管理员"),
    POST_ID_ERROR("400", "此帖子不存在，无法删除。请更改id后重试"),
    POST_DELETE_ERROR("400", "帖子删除失败，请重试或联系管理员"),
    FILE_INSERT_ERROR("400", "文件插入错误"),
    POST_BANNED_ERROR("400", "此帖子已被封禁"),
    POST_DELETED_ERROR("400", "此帖子已被删除"),

    REGISTER_NOT_GET_CODE_ERROR("400", "尚未获得验证码"),
    REGISTER_CODE_ERROR("400", "验证码输入错误，请重试"),
    REGISTER_USED_EMAIL_ERROR("400", "该邮箱已注册过，请更换邮箱。若无法接收信息请联系管理员"),

    LOGIN_NO_CAPTCHA_ERROR("400", "图形验证码未获取到，有可能未加载出来，请刷新一下验证码"),
    LOGIN_CAPTCHA_ERROR("400", "验证码有误，请重新输入"),
    LOGIN_INPUT_TEXT_ERROR("400", "输入框检测失败，请刷新网页重试"), // 属于系统bug级别，应在调试阶段避免
    LOGIN_NO_USER_ERROR("400", "该用户信息不存在，请您重新输入"),
    LOGIN_PASSWORD_ERROR("400", "您输入的密码不正确，请重新输入"),
    LOGIN_NO_FUNCTION_ERROR("400", "此功能暂未上线，请使用邮箱和密码登录哈~ "),

    EMAIL_ACTIVATION_USED_EMAIL("400", "该邮箱已被注册，请更换一个邮箱重新尝试"),
    EMAIL_ACTIVATION_NO_EMAIL("400", "对不起，发送失败。请检查您的邮箱是否真实存在，或者请稍后重试"),

    MODIFY_EMAIL_NOT_GET_CODE_ERROR("400", "尚未获得验证码"),
    MODIFY_EMAIL_CODE_ERROR("400", "验证码输入错误，请重试"),
    MODIFY_EMAIL_USED_EMAIL_ERROR("400", "该邮箱已被使用过，请更换邮箱。若无法接收信息请联系管理员：qq：1020238657"),

    MODIFY_NO_LAST_HEADER_URL_ERROR("400", "抱歉，您当前没有上一张头像可更换哦~ "),
    MODIFY_NO_FILE_FORMAT_ERROR("400", "抱歉，目前不支持此格式的文件上传作为您的头像，请上传jpg、jpeg、png格式的文件哦~ "),

    FILE_TOO_BIG_ERROR("400", "文件过大，此服务器目前只支持200MB及以下大小的文件，超出请联系张创琦：qq：1020238657。后续会开发大文件上传的功能，敬请期待"),

    SERVER_FAIL("500", "服务器故障"),

    VERIFY_NO_CAPTCHA_CODE_ERROR("400", "尚未获得验证码或验证码已过期，请点击刷新按钮重新获取"),

    USER_NOT_LOGIN_REDIRECT("300", "该用户尚未登录，请返回到用户登录界面"),

    USER_IDENTIFY_CODE_ERROR("400", "身份验证错误，请检查您的姓名或者身份证号是否输入有误"),

    USER_NO_PERMISSION_ERROR("403", "您没有修改权限。"),

    USER_FOLLOW_ONE_PERSON_ERROR("400", "您不能关注自己哦"),
    USER_FOLLOW_ALREADY_ERROR("400", "您已经关注此人了哦，不可重复关注哦"),
    USER_UNFOLLOW_ONE_PERSON_ERROR("400", "您不能对自己的关注状态进行操作哦"),
    USER_UNFOLLOW_NO_ERROR("400", "您还没有关注这个人哦，不用取消关注"),

    FILE_FORMAT_NOT_SUPPORT_ERROR("400", "不支持此文件格式，如果有问题请联系张创琦：qq：1020238657"),
    FILE_SIZE_TOO_BIG_LIMIT("400", "本服务器支持文件最大的上传大小为200MB，超出请联系张创琦：qq：1020238657。后续会开发大文件上传的功能，敬请期待"),
    FILE_MARKDOWN_NO_PICTURE_ERROR("400", "本服务器目前只支持图片格式的上传。敬请期待后续功能的开发~ "),

    FILE_RECOMMEND_RECOMMEND_ORDER_ERROR("400", "上传的推荐序号有误。"), // 前端可以规避此错误
    FILE_RECOMMEND_EXISTS_FIRST_ERROR("400", "推荐顺序为1的资料已经存在"), // 前端可以规避此错误
    FILE_RECOMMEND_INTERVAL_RECOMMEND_ORDER_ERROR("400", "请按照上传顺序进行上传推荐文件，不可以跨越推荐序列号"), // 前端可以规避此错误
    FILE_RECOMMEND_EXISTS_THIS_RECOMMEND_ERROR("400", "此推荐的文件重复"), // 前端可以规避此错误

    IP_NO_PROVINCE_ERROR("400", "没有这个省份"),

    JWT_INVALID_SIGNATURE_ERROR("400", "jwt::无效签名"),
    JWT_TOKEN_OVERDUE_ERROR("400", "jwt::token过期"),
    JWT_ALGORITHM_MISMATCH_ERROR("400", "jwt::token算法不一致"),
    JWT_ERROR("40001", "jwt::使用中出现错误"),

    ES_INSERT_POST_ERROR("400", "在es中插入帖子或专栏失败"),
    ES_SEARCH_POST_ERROR("400", "es内容查询失败"),

    ;

    private final String code;
    private final String msg;

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
