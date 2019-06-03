package org.openpaas.paasta.marketplace.web.admin.profiles;


import org.openpaas.paasta.marketplace.web.admin.common.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 판매자 프로필 모델
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-05-07
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SellerProfile extends BaseModel {

    private Long id;
    private String sellerId;
    private String sellerName;
    private String businessType;
    private String managerName;
    private String email;
    private String homepageUrl;
    private String deleteYn;

//    private List<CustomCode> businessTypeList;

}
