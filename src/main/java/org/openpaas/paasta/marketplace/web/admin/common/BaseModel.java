package org.openpaas.paasta.marketplace.web.admin.common;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BaseModel {

	protected String createId;
    protected String updateId;
    protected LocalDateTime createDate;
    protected LocalDateTime updateDate;

    protected String strCreateDate;
    protected String strUpdateDate;
    
    protected String resultCode;
    protected String resultMessage;

}
