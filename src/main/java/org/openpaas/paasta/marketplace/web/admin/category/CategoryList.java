package org.openpaas.paasta.marketplace.web.admin.category;

import java.util.List;

import org.openpaas.paasta.marketplace.web.admin.common.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryList extends BaseModel {

	private List<Category> items;

}
