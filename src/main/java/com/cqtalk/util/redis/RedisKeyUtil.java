package com.cqtalk.util.redis;

public class RedisKeyUtil {

    private static final String SPLIT = ":"; // 分隔符
    private static final String PREFIX_ACTIVATION = "activation";
    private static final String PREFIX_KAPTCHA = "kaptcha";
    private static final String PREFIX_TICKET = "ticket";
    private static final String PREFIX_USER_FILE_DOWNLOAD = "user:file:download";
    private static final String PREFIX_ENTITY_LIKE = "like"; // 统计这个实体类下的点赞数量
    // private static final String PREFIX_USER_LIKE = "like:user"; // 统计一个用户所有的实体类获得的点赞数
    private static final String PREFIX_ENTITY_COMMENT = "comment";
    private static final String PREFIX_ENTITY_COLLECT = "collect";
    private static final String PREFIX_ENTITY_BROWSE = "browse";
    private static final String PREFIX_ENTITY_NOTICE_SYSTEM_READ = "notice:system:read";
    private static final String PREFIX_USER_FOLLOW = "user:follow";
    private static final String PREFIX_USER_BE_FOLLOWED = "user:beFollowed";

    // 注册验证码存储
    public static String getActivationKey(String email) {
        return PREFIX_ACTIVATION + SPLIT + email;
    }

    // 登录验证码
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    // 登录的凭证
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    // 检查用户是否下载过文件（具体业务逻辑见设计文档）
    public static String getUserFileDownloadKey(Long fileId, Integer userId) {
        return PREFIX_USER_FILE_DOWNLOAD + SPLIT + fileId + SPLIT + userId;
    }

    // 某个实体获得的赞的数量
    // like:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, long entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    // 某个实体的评论数
    public static String getEntityCommentKey(int entityType, long entityId) {
        return PREFIX_ENTITY_COMMENT + SPLIT + entityType + SPLIT + entityId;
    }

    // 某个实体的收藏数
    public static String getEntityCollectKey(int entityType, long entityId) {
        return PREFIX_ENTITY_COLLECT + SPLIT + entityType + SPLIT + entityId;
    }

    // 某个用户的赞
    // like:user:userId -> int
    /*public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }*/

    // 某个实体的浏览量
    public static String getEntityBrowseKey(int entityType, long entityId) {
        return PREFIX_ENTITY_BROWSE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getNoticeSystemReadInfoKey(int entityType, long entityId) {
        return PREFIX_ENTITY_NOTICE_SYSTEM_READ + SPLIT + entityType + SPLIT + entityId;
    }

    // 用户关注
    public static String getUserFollowKey(int userId) {
        return PREFIX_USER_FOLLOW + SPLIT + userId;
    }

    // 用户被关注
    public static String getUserBeFollowedKey(int userId) {
        return PREFIX_USER_BE_FOLLOWED + SPLIT + userId;
    }


}
