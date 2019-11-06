package cn.zgx.tatistician.timer;

import cn.zgx.tatistician.dao.TimerDao;
import cn.zgx.tatistician.pojo.CustomerNamePojo;
import cn.zgx.tatistician.pojo.EmployeePojo;
import cn.zgx.tatistician.pojo.MobilePojo;
import cn.zgx.tatistician.pojo.NeedsPojo;
import cn.zgx.tatistician.service.CustomerService;
import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Future;

@Component//@Component注解用于对那些比较中立的类进行注释；相对与在持久层、业务层和控制层分别采用 @Repository、@Service 和 @Controller 对分层中的类进行注释
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class MultithreadScheduleTask {

    @Autowired
    CustomerService customerService;
    @SuppressWarnings("all")
    @Autowired
    TimerDao timerDao;

    @Async
    @Scheduled(cron="0 41 * * * ?")//"0 */${time.corn} * * * *" "30 38 * * * ?"
    public void timerInsert() throws InterruptedException {  //采用Future来知道开启的这条子线程什么时候执行完毕和执行的结果
        System.out.println("定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        List<EmployeePojo> employeePojoList = customerService.checkAllOrder();
        int insertResult = timerDao.insertTimerBatch(employeePojoList);
        System.out.println("-------------开始备份详情数据--------------");
        //客户姓名
        List<CustomerNamePojo> nameList = new ArrayList();
        HashMap<String, List> nameOutMap = customerService.getNameOutMap();
        Iterator<Map.Entry<String, List>> it = nameOutMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, List> entry = it.next();
            CustomerNamePojo customerNamePojo=new CustomerNamePojo();
            customerNamePojo.setOwner(entry.getKey());
            customerNamePojo.setCustomerName(entry.getValue().toString());
            nameList.add(customerNamePojo);
        }
        int i = timerDao.insertCustomerNameBatch(nameList);
        System.out.println("----------------插入姓名详情"+i+"条-----------------------");
        //需求
        List<NeedsPojo> needsList = new ArrayList();
        HashMap<String, List> needsOutMap = customerService.getNeedsOutMap();
        Iterator<Map.Entry<String, List>> it1 = needsOutMap.entrySet().iterator();
        while(it1.hasNext()){
            Map.Entry<String, List> entry = it1.next();
            NeedsPojo needsPojo=new NeedsPojo();
            needsPojo.setOwner(entry.getKey());
            needsPojo.setNeeds(entry.getValue().toString());
            needsList.add(needsPojo);
        }
        int j=timerDao.insertNeedsBatch(needsList);
        System.out.println("----------------------插入需求详情"+j+"条------------------");
        //手机
        List<MobilePojo> mobileList = new ArrayList();
        HashMap<String, List>  mobileOutMap= customerService.getMobileOutMap();
        Iterator<Map.Entry<String, List>> it2 = mobileOutMap.entrySet().iterator();
        while(it2.hasNext()){
            Map.Entry<String, List> entry = it2.next();
            MobilePojo mobilePojo=new MobilePojo();
            mobilePojo.setOwner(entry.getKey());
            mobilePojo.setMobile(entry.getValue().toString());
            mobileList.add(mobilePojo);
        }
        int z=timerDao.insertMobileBatch(mobileList);
        System.out.println("------------------------插入手机详情"+z+"条--------------------------------");
        //清空详情数据库

        System.out.println("定时任务结束 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName()+"\n插入了"+insertResult+"条记录");
    }

   /* @Async
    @Scheduled(fixedDelay = 2000)//间隔2秒
    public void second() {
        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println();
    }*/
}
