package com.ding.crowd;

import com.ding.crowd.constant.CrowdContant;
import com.ding.crowd.util.CrowdUtil;
import org.junit.Test;

/**
 * @author Qidong Ding
 * @description TODO：
 * @date 2021-10-23-15:57
 * @since JDK 1.8
 */

public class StringTest {
    @Test
    public void testMd5() {
        String source = "123123";
        String ecode = CrowdUtil.md5(source);
        System.out.println(ecode);
    }

}
