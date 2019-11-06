package cn.zgx.tatistician.service;

import cn.zgx.tatistician.Util.DataUtil.CommonUtil;
import cn.zgx.tatistician.dao.TimerDao;
import cn.zgx.tatistician.pojo.EmployeePojo;
import cn.zgx.tatistician.pojo.Like;
import cn.zgx.tatistician.pojo.TimerEmployeePojo;
import cn.zgx.tatistician.timer.MultithreadScheduleTask;
import com.sun.javaws.IconUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimerService {
    @SuppressWarnings("all")
    @Autowired
    TimerDao timerDao;
    @Autowired
    MultithreadScheduleTask multithreadScheduleTask;

    public List<TimerEmployeePojo> getTimerData() {
        //今天的记录集合
        List<EmployeePojo> employeePojoListNewList = timerDao.getTodayData();
        //昨天的记录集合
        List<EmployeePojo> employeePojoListOldList = timerDao.getYesterdayData();
        //最终返回的pojo集合
        List<TimerEmployeePojo> finalPojoList = new ArrayList<>();
        //不通过的员工总数
        int todaySize = employeePojoListNewList.size();
        int yesterdaySize = employeePojoListOldList.size();
        for (int i = 0; i < todaySize; i++) {
            //最终返回的pojo
            TimerEmployeePojo finalPojo = new TimerEmployeePojo();
            EmployeePojo employeePojoNew = employeePojoListNewList.get(i);
            String owner = employeePojoNew.getCustomerOwner();
            if (!CommonUtil.isNull(owner)){
                boolean flag = true;
                for (int z = 0; z < yesterdaySize; z++) {
                    EmployeePojo employeePojoOld = employeePojoListOldList.get(z);
                    if (owner.equals(employeePojoOld.getCustomerOwner())) {
                        //姓名
                        finalPojo.setCustomerName(employeePojoNew.getCustomerName());
                        //需求
                        finalPojo.setNeeds(employeePojoNew.getNeeds());
                        //手机
                        finalPojo.setMobile(employeePojoNew.getMobile());
                        //占比
                        finalPojo.setPercent(employeePojoNew.getPercent());
                        //占比变动
                        String percentNew = employeePojoNew.getPercent();
                        String[] strsNew = percentNew.split("/");
                        int percentNewNum = Integer.parseInt(strsNew[0].trim());
                        String percentOld = employeePojoOld.getPercent();
                        String[] strsOld = percentOld.split("/");
                        int percentOldNum = Integer.parseInt(strsOld[0].trim());
                        int percentChange = percentNewNum - percentOldNum;
                        if (percentChange > 0) {
                            finalPojo.setPercentChange("↑ " + percentChange);
                        } else if (percentChange == 0) {
                            finalPojo.setPercentChange("0");
                        } else {
                            finalPojo.setPercentChange("↓ " + Math.abs(percentChange));
                        }
                        //不变值
                        finalPojo.setCustomerNum(employeePojoNew.getCustomerNum());
                        finalPojo.setOrder(employeePojoNew.getOrder());
                        finalPojo.setOwnerDepartment(employeePojoNew.getOwnerDepartment());
                        finalPojo.setCustomerOwner(employeePojoNew.getCustomerOwner());
                        //加入集合
                        finalPojoList.add(finalPojo);
                        flag = false;
                    }
                    boolean other = z == (yesterdaySize - 1);
                    if (other && flag) {
                        finalPojo.setCustomerOwner(employeePojoNew.getCustomerOwner());
                        finalPojo.setOwnerDepartment(employeePojoNew.getOwnerDepartment());
                        finalPojo.setOrder(employeePojoNew.getOrder());
                        finalPojo.setNeeds(employeePojoNew.getNeeds());
                        finalPojo.setPercent(employeePojoNew.getPercent());
                        finalPojo.setCustomerName(employeePojoNew.getCustomerName());
                        finalPojo.setMobile(employeePojoNew.getMobile());
                        finalPojo.setCustomerNum(employeePojoNew.getCustomerNum());
                        finalPojo.setPercentChange("0");
                        finalPojoList.add(finalPojo);
                    }
                }
            }else {
                finalPojo.setCustomerOwner("空");
                finalPojo.setOwnerDepartment("空");
                finalPojo.setOrder(employeePojoNew.getOrder());
                finalPojo.setNeeds(employeePojoNew.getNeeds());
                finalPojo.setPercent(employeePojoNew.getPercent());
                finalPojo.setCustomerName(employeePojoNew.getCustomerName());
                finalPojo.setMobile(employeePojoNew.getMobile());
                finalPojo.setCustomerNum(employeePojoNew.getCustomerNum());
                finalPojo.setPercentChange("0");
                finalPojoList.add(finalPojo);
            }
        }
        return finalPojoList;
    }

    public List<TimerEmployeePojo> timeLikeQueryOrder(Like like) {
        List<TimerEmployeePojo> list = timeLikeQuery(like);
        for (int i = 0; i < list.size(); i++) {
            TimerEmployeePojo timerEmployeePojo = list.get(i);
            timerEmployeePojo.setOrder(i+1);
        }
        return list;
    }

    private List<TimerEmployeePojo> timeLikeQuery(Like like) {
        //今天的记录集合
        List<EmployeePojo> employeePojoListNewList = timerDao.timerLikeQueryToday(like);
        System.out.println(employeePojoListNewList.toString());
        //昨天的记录集合
        List<EmployeePojo> employeePojoListOldList = timerDao.timerLikeQueryYesterday(like);
        //最终返回的pojo集合
        List<TimerEmployeePojo> finalPojoList = new ArrayList<>();
        //不通过的员工总数
        int todaySize = employeePojoListNewList.size();
        int yesterdaySize = employeePojoListOldList.size();
        for (int i = 0; i < todaySize; i++) {
            //最终返回的pojo
            TimerEmployeePojo finalPojo = new TimerEmployeePojo();
            EmployeePojo employeePojoNew = employeePojoListNewList.get(i);
            String owner = employeePojoNew.getCustomerOwner();
            if (!CommonUtil.isNull(owner)){
                boolean flag = true;
                for (int z = 0; z < yesterdaySize; z++) {
                    EmployeePojo employeePojoOld = employeePojoListOldList.get(z);
                    if (owner.equals(employeePojoOld.getCustomerOwner())) {
                        //姓名
                        finalPojo.setCustomerName(employeePojoNew.getCustomerName());
                        //需求
                        finalPojo.setNeeds(employeePojoNew.getNeeds());
                        //手机
                        finalPojo.setMobile(employeePojoNew.getMobile());
                        //占比
                        finalPojo.setPercent(employeePojoNew.getPercent());
                        //占比变动
                        String percentNew = employeePojoNew.getPercent();
                        String[] strsNew = percentNew.split("/");
                        int percentNewNum = Integer.parseInt(strsNew[0].trim());
                        String percentOld = employeePojoOld.getPercent();
                        String[] strsOld = percentOld.split("/");
                        int percentOldNum = Integer.parseInt(strsOld[0].trim());
                        int percentChange = percentNewNum - percentOldNum;
                        if (percentChange > 0) {
                            finalPojo.setPercentChange("↑ " + percentChange);
                        } else if (percentChange == 0) {
                            finalPojo.setPercentChange("0");
                        } else {
                            finalPojo.setPercentChange("↓ " + percentChange);
                        }
                        //不变值
                        finalPojo.setCustomerNum(employeePojoNew.getCustomerNum());
                        finalPojo.setOrder(employeePojoNew.getOrder());
                        finalPojo.setOwnerDepartment(employeePojoNew.getOwnerDepartment());
                        finalPojo.setCustomerOwner(employeePojoNew.getCustomerOwner());
                        //加入集合
                        finalPojoList.add(finalPojo);
                        flag = false;
                    }
                    boolean other = z == (yesterdaySize - 1);
                    if (other && flag) {
                        finalPojo.setCustomerOwner(employeePojoNew.getCustomerOwner());
                        finalPojo.setOwnerDepartment(employeePojoNew.getOwnerDepartment());
                        finalPojo.setOrder(employeePojoNew.getOrder());
                        finalPojo.setNeeds(employeePojoNew.getNeeds());
                        finalPojo.setPercent(employeePojoNew.getPercent());
                        finalPojo.setCustomerName(employeePojoNew.getCustomerName());
                        finalPojo.setMobile(employeePojoNew.getMobile());
                        finalPojo.setCustomerNum(employeePojoNew.getCustomerNum());
                        finalPojo.setPercentChange("0");
                        finalPojoList.add(finalPojo);
                    }
                }
            }else {
                finalPojo.setCustomerOwner("空");
                finalPojo.setOwnerDepartment("空");
                finalPojo.setOrder(employeePojoNew.getOrder());
                finalPojo.setNeeds(employeePojoNew.getNeeds());
                finalPojo.setPercent(employeePojoNew.getPercent());
                finalPojo.setCustomerName(employeePojoNew.getCustomerName());
                finalPojo.setMobile(employeePojoNew.getMobile());
                finalPojo.setCustomerNum(employeePojoNew.getCustomerNum());
                finalPojo.setPercentChange("0");
                finalPojoList.add(finalPojo);
            }
        }
        return finalPojoList;
    }

}
