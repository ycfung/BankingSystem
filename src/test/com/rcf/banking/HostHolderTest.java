package com.rcf.banking;

import com.rcf.banking.util.HostHolder;
import org.junit.jupiter.api.Test;

public class HostHolderTest {
    @Test
    public void testHostHolder() {
        HostHolder.setUserName("Chengfeng");
        assert HostHolder.getUserName().equals("Chengfeng");
    }

    @Test
    public void testClearHostHolder() {
        HostHolder.setUserName("Chengfeng");
        HostHolder.clear();
        assert HostHolder.getUserName() == null;
    }

    @Test
    public void testSetHostHolder() {
        HostHolder.setUserName("Chengfeng");
        HostHolder.setUserName("Chengfeng2");
        assert HostHolder.getUserName().equals("Chengfeng2");
    }
}
