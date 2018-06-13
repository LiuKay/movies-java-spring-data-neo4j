package movies.spring.data.neo4j.common;

/**
 * Created by kay on 2018/6/9
 */
public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),

    //
    ERROR(1000,"ERROR"),  //自定义消息体

    //用户方面错误
    USER_EXISTS(1001, "用户已存在"),
    USER_UNREGISTER(1002, "用户未注册"),
    USER_UNLOGIN(1003, "用户未登录"),


    ILLEGAL_ARGUMENT(2000, "ILLEGAL_ARGUMENT"),


    //系统
    System_ERROR(5000,"系统错误，请联系管理员");

    private int code;
    private String description;

    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
