package org.openpaas.paasta.marketplace.web.admin;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openpaas.paasta.marketplace.web.admin.service.AdminCategoryServiceTest;
import org.openpaas.paasta.marketplace.web.admin.service.AdminSellerProfileServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
        // @formatter:off
        AdminCategoryServiceTest.class,
        AdminSellerProfileServiceTest.class,
        // @formatter:on
})
public class ServiceTests {
}
