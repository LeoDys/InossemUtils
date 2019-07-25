package com.inossem_library.request.http.constant;

/**
 * @author 詹建宇
 * @time on 2019/7/25
 * @email jianyu.zhan@inossem.com
 * @describe 服务器请求码
 */
public class HttpStatus {

    /*
    * 3xx 重定向
    */
    //已移动 — 请求的数据具有新的位置且更改是永久的。
    public final static int HTTP_STATUS_301 = 301;
    //已找到 — 请求的数据临时具有不同 URI。
    public final static int HTTP_STATUS_302 = 302;
    //请参阅其它 — 可在另一 URI 下找到对请求的响应，且应使用 GET 方法检索此响应。
    public final static int HTTP_STATUS_303 = 303;
    //未修改 — 未按预期修改文档。
    public final static int HTTP_STATUS_304 = 304;
    //使用代理 — 必须通过位置字段中提供的代理来访问请求的资源。
    public final static int HTTP_STATUS_305 = 305;
    //未使用 — 不再使用；保留此代码以便将来使用。
    public final static int HTTP_STATUS_306 = 306;

    /*
     * 4xx 客户机中出现的错误
     */
    //错误请求 — 请求中有语法问题，或不能满足请求。
    public final static int HTTP_STATUS_400 = 400;
    //未授权 — 未授权客户机访问数据。
    public final static int HTTP_STATUS_401 = 401;
    //需要付款 — 表示计费系统已有效。
    public final static int HTTP_STATUS_402 = 402;
    //禁止 — 即使有授权也不需要访问。
    public final static int HTTP_STATUS_403 = 403;
    //找不到 — 服务器找不到给定的资源；文档不存在。
    public final static int HTTP_STATUS_404 = 404;
    //代理认证请求 — 客户机首先必须使用代理认证自身。
    public final static int HTTP_STATUS_407 = 407;
    //介质类型不受支持 — 服务器拒绝服务请求，因为不支持请求实体的格式。
    public final static int HTTP_STATUS_415 = 415;

    /*
    * 5xx 服务器中出现的错误
    */
    //内部错误 — 因为意外情况，服务器不能完成请求。
    public final static int HTTP_STATUS_500 = 500;
    //未执行 — 服务器不支持请求的工具。
    public final static int HTTP_STATUS_501 = 501;
    //错误网关 — 服务器接收到来自上游服务器的无效响应。
    public final static int HTTP_STATUS_502 = 502;
    //无法获得服务 — 由于临时过载或维护，服务器无法处理请求。
    public final static int HTTP_STATUS_503 = 503;
}
