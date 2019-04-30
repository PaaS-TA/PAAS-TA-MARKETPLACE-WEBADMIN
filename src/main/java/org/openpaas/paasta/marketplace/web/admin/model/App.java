package org.openpaas.paasta.marketplace.web.admin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * App 모델
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-01-15
 */
@Data
public class App {
    private int appInstanceIndex;
    private String guid;
    private String name;
    private String newName;
    private String orgName;
    private String spaceName;
    private Map<String, String> environment;
    private UUID serviceGuid;
    private String serviceName;
    private String serviceNewName;
    private List<String> urls;
    private List<String> services;
    private int instances = 0;
    private int memory = 0;
    private int diskQuota = 0;
    private String state;
    private String createdAt;
    @JsonProperty("package_updated_at")
    private String updatedAt;
    private int totalUserCount;
    private String buildPack;
    private String stackName;
    private Staging staging;
    private String host;
    private String domainName;

    @Data
    public class Staging {
        private String detectedBuildpack;
        private String stack;
    }
}
