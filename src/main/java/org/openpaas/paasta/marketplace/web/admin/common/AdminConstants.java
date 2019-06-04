package org.openpaas.paasta.marketplace.web.admin.common;

/**
 * Constants 클래스
 *
 * @author REX
 * @version 1.0
 * @since 2018.08.02
 */
public class AdminConstants {

    // COMMON
    public static final String RESULT_STATUS_SUCCESS = "SUCCESS";
    public static final String RESULT_STATUS_FAIL = "FAIL";

    public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    public static final String CF_AUTHORIZATION_HEADER_KEY = "cf-Authorization";
    public static final String CONTENT_TYPE = "Content-Type";

    public static final String TARGET_API_CF = "cfApi";
    public static final String TARGET_API_MARKET = "marketApi";

    // general data
    public static final String GROUP_CODE_BUSINESS_TYPE = "BUSINESS_TYPE";

//    public static final String MARKET_INIT_URL = "/admin";
    public static final String MARKET_ADMIN_URL = "/admin";


    // market api uri
    public static final String URI_API_BASE = "/api";
    public static final String URI_API_CUSTOM_CODE = "/api/customCode";
    public static final String URI_API_CATEGORY = "/api/category";
    public static final String URI_API_SELLER_PROFILE = "/api/seller/profile";

    // market web admin page uri
    public static final String URI_WEB_CUSTOM_CODE_LIST = "/customCode/{groupCode}";
    public static final String URI_WEB_CATEGORY_LIST = "/category/list";
    public static final String URI_WEB_CATEGORY_DETAIL = "/category/detail/{id}";
    public static final String URI_WEB_SELLER_PROFILE_LIST = "/seller/profile/list";
    public static final String URI_WEB_SELLER_PROFILE_DETAIL = "/seller/profile/detail/{id}";
    public static final String URI_WEB_SELLER_PROFILE_UPDATE = "/seller/profile/update/{id}";

    // market web admin db uri
    public static final String URI_DB_CUSTOM_CODE_LIST = "/db/customCode/{groupCode}";
    public static final String URI_DB_CATEGORY_LIST = "/db/category/list";
    public static final String URI_DB_CATEGORY_DETAIL = "/db/category/detail/{id}";
    public static final String URI_DB_SELLER_PROFILE_LIST = "/db/seller/profile/list";
    public static final String URI_DB_SELLER_PROFILE_DETAIL = "/db/seller/profile/detail/{id}";

    // market web admin view file uri
    public static final String URI_VIEW_PROFILE = "/profile";

    
    private AdminConstants() {
        throw new IllegalStateException();
    }

}
