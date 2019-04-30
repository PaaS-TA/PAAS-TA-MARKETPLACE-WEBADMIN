package org.openpaas.paasta.marketplace.web.admin.model;

import lombok.Data;

import java.util.*;

@Data
public class Org {
    private Map metadata;
    private Map entity;

    public Map<String, Object> map;
    private Meta meta;
    private String name;

    //private String name;
    private String orgName;
    private String newOrgName;
    private boolean recursive = true;

    private UUID guid;

    private String status;
    private int memoryUsage;
    private int memoryLimit;


    private List<Space> spaces = new ArrayList<Space>();

    private boolean billingEnabled = false;

    private Quota quota;
    private String quotaGuid;
    private String userId;

    @Data
    public static class Meta {

        private UUID guid;
        private Date created;
        private Date updated;
        private String url;
    }

}
