package org.openpaas.paasta.marketplace.web.admin.common;

/**
 * Constants 클래스
 *
 * @author REX
 * @version 1.0
 * @since 2018.08.02
 */
public class Constants {

    // COMMON
    public static final String RESULT_STATUS_SUCCESS = "SUCCESS";
    public static final String RESULT_STATUS_FAIL = "FAIL";


    public static final String TARGET_CF_API = "cfApi";
    public static final String TARGET_MARKET_API = "marketApi";

    public static final String MARKET_BASE_URL = "/";
    public static final String MARKET_INIT_URL = "/admin";



    // TODO :: Market WEB URI
    public static final String URI_INTRO_OVERVIEW = "/caas/intro/overview";
    public static final String URI_INTRO_ACCESS_INFO = "/caas/intro/accessInfo";

    public static final String URI_WORKLOAD_OVERVIEW = "/caas/workloads/overview";
    public static final String URI_WORKLOAD_DEPLOYMENTS = "/caas/workloads/deployments";
    public static final String URI_WORKLOAD_PODS = "/caas/workloads/pods";
    public static final String URI_WORKLOAD_REPLICA_SETS = "/caas/workloads/replicaSets";

    public static final String URI_SERVICES = "/caas/services";

    public static final String URI_USERS = "/caas/users";

    public static final String URI_ROLES = "/caas/roles";


    // TODO :: Market API URI



    private Constants() {
        throw new IllegalStateException();
    }

}
