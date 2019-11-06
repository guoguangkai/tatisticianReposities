package cn.zgx.tatistician.dao;

import cn.zgx.tatistician.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TimerDao {
    /**
     *
     * @param timerTableList
     * @return   返回值是批量添加的数据条数
     */
    int insertTimerBatch(List<EmployeePojo> timerTableList);

    /**
     * 获取定时器今天保存的数据
     * @return
     */
    ArrayList<EmployeePojo> getTodayData();

    /**
     * 获取定时器昨天保存的数据
     * @return
     */
    ArrayList<EmployeePojo> getYesterdayData();

    /**
     * 从定时器备份的数据中进行模糊查询今天的数据
     * @return
     */
    ArrayList<EmployeePojo>  timerLikeQueryToday(Like like);

    /**
     * 从定时器备份的数据中进行模糊查询昨天的数据
     * @return
     */
    ArrayList<EmployeePojo>  timerLikeQueryYesterday(Like like);

    /**
     * 插入客户姓名详情
     * @param list
     * @return
     */
    int insertCustomerNameBatch(List<CustomerNamePojo> list);

    /**
     * 插入需求详情
     * @param list
     * @return
     */
    int insertNeedsBatch(List<NeedsPojo> list);

    /**
     * 插入手机详情
     * @param list
     * @return
     */
    int insertMobileBatch(List<MobilePojo> list);

    /**
     * 查询客户姓名详情
     * @param owner
     * @return
     */
    String queryCustomerName(@Param("owner")String owner);

    /**
     * 查询需求详情
     * @param owner
     * @return
     */
    String queryNeedsBatch(@Param("owner")String owner);

    /**
     * 查询手机详情
     * @param owner
     * @return
     */
    String queryMobileBatch(@Param("owner")String owner);
}
