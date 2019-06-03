package org.openpaas.paasta.marketplace.web.admin.code;

import java.util.List;

import org.openpaas.paasta.marketplace.web.admin.common.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomCodeList extends BaseModel {

	private List<CustomCode> items;

}
