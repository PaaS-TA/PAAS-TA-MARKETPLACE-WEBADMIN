package org.openpaas.paasta.marketplace.web.admin.code;

import org.openpaas.paasta.marketplace.web.admin.common.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Custom Code 모델
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomCode extends BaseModel {

    private Long codeId;

    // ex) BC
    private String groupCode;

    // ex) 비즈니스 타입 코드
    private String groupCodeName;

    // ex) GOVERNMENT, COMPANY, PERSON, ETC
    private String unitCode;

    // ex) 공공기관, 기업, 개인, 기타
    private String unitCodeName;

}
