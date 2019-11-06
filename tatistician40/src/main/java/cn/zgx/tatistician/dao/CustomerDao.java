package cn.zgx.tatistician.dao;

import cn.zgx.tatistician.pojo.Customer;
import cn.zgx.tatistician.pojo.CustomerNumPojo;
import cn.zgx.tatistician.pojo.Like;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Mapper
public interface CustomerDao {
    /**
     * 获取整张表的记录
     * @return
     */
    ArrayList<Customer> getAbnormalCustomerCount();

    /**
     * 获取每个员工对应的客户数量，封装在CustomerNumPojo中
     * @return
     */
    List<CustomerNumPojo> getCustomerNumOfOwner();

    /**
     * 通过部门 和 员工 模糊查询
     * @return
     */
    List<Customer> likeQuery(Like like);

    /**
     * 获取整张表的记录总条数
     * @return
     */
    int getTotalCount();
}
