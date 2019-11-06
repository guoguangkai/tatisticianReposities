package cn.zgx.tatistician.dao;

import cn.zgx.tatistician.pojo.EmployeePojo;
import cn.zgx.tatistician.pojo.Like;
import cn.zgx.tatistician.service.CustomerService;
import cn.zgx.tatistician.timer.MultithreadScheduleTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDao {
    @SuppressWarnings("all")
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerService customerService;
    @Autowired
    MultithreadScheduleTask multithreadScheduleTask;

    @Test

    public void getAbnormalCustomerCount() {
        Like like = new Like();
        like.setDep("中海纳");
        List list = customerDao.likeQuery(like);
        System.out.println(list.toString());
    }

    @Test
    public void sss() throws InterruptedException {
       multithreadScheduleTask.timerInsert();
    }
}
