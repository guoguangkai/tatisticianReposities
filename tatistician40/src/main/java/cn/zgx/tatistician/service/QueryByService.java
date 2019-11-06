package cn.zgx.tatistician.service;

import cn.zgx.tatistician.Util.DataUtil.CommonUtil;
import cn.zgx.tatistician.Util.DataUtil.JudgeName;
import cn.zgx.tatistician.dao.CustomerDao;
import cn.zgx.tatistician.pojo.Customer;
import cn.zgx.tatistician.pojo.CustomerNumPojo;
import cn.zgx.tatistician.pojo.EmployeePojo;
import cn.zgx.tatistician.pojo.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class QueryByService {
    @SuppressWarnings("all")
    @Autowired
    private CustomerDao customerDao;

    private long total;
    HashMap<String, List>  nameOutMap=new HashMap<>();
    HashMap<String, List>  needsOutMap=new HashMap<>();
    HashMap<String, List>  mobileOutMap=new HashMap<>();

    public List<EmployeePojo> checkAllOrder(Like like){
        List<EmployeePojo> list = checkAll(like);
        Collections.sort(list, new Comparator<EmployeePojo>() {
            @Override
            public int compare(EmployeePojo o1, EmployeePojo o2) {
                return o2.getCompare()-o1.getCompare();
            }
        });
        for (int i = 0; i < list.size(); i++) {
            EmployeePojo employeePojo = list.get(i);
            employeePojo.setOrder(i+1);
        }
        return list;
    }

    /**
     * 获取数据库查询到的记录总数
     *
     * @return
     */
    public long getTotal() {
        return total;
    }

    /**
     * 获取校验后的记录总数
     *
     * @return
     */
    /*public long getTotalAfterCheck() {
        return pojoList.size();
    }*/

    @SuppressWarnings("all")
    private  Map<String,Integer> customerNumMap() {
        Map<String, Integer> map = new HashMap<>();
        List<CustomerNumPojo> list = new ArrayList<>();
        list = customerDao.getCustomerNumOfOwner();
        if (list != null && !list.isEmpty()) {
            for (CustomerNumPojo customerNumPojo : list) {
                map.put(customerNumPojo.getOwner(), customerNumPojo.getCustomerNum());
            }
        }
        return map;
    }


    @SuppressWarnings("all")
    public List<EmployeePojo> checkAll(Like like) {
        List<Customer> customerList = new ArrayList<>();
        customerList = customerDao.likeQuery(like);
        total = customerList.size();
        List<EmployeePojo> pojoList = new ArrayList();
        int sum = customerList.size();
        List precentList = new ArrayList();
        System.out.println("----------------------checkAll-----------------------");
        boolean percentFlag;
        for (int i = 0; i < sum; i++) {
            //设置flag 控制执行顺序
            int flag = 1;
            //比重算法 标记 降维度
            percentFlag = false;

            Customer customer = customerList.get(i);
            //判断空记录
            if (customer == null) continue;
            String owner = customer.getOwner().trim();
            if (owner == null) continue;
            String customerName = customer.getCustomerName();
            String needs = customer.getNeeds();
            String mobile = customer.getMobile();
            String department = customer.getDepartment() == null ? "空" : customer.getDepartment().getDepartment().substring(6);
            //校验客户姓名 是否为Null 有数字或特殊字符 全中文<=4 全英文<20
            if (!privateCheckCustomerName(customerName, owner)) {
                //校验不通过
                //如果pojo集合为空
                if (null == pojoList || pojoList.size() ==0) {
                    //new pojo放入集合
                    EmployeePojo employeePojo = new EmployeePojo();
                    employeePojo.setCustomerOwner(owner);
                    employeePojo.setCustomerName(1);
                    employeePojo.setOwnerDepartment(department);
                    employeePojo.setCustomerNum(customerNumMap().get(owner));
                    pojoList.add(employeePojo);
                    flag=2;
                }
                //遍历集合
                if (flag == 1) {
                    for (int j = 0; j < pojoList.size(); j++) {
                        //找到对应员工pojo
                        if (pojoList.get(j).getCustomerOwner().equals(owner)) {
                            //更新客户姓名统计不通过的结果
                            pojoList.get(j).setCustomerName(pojoList.get(j).getCustomerName() + 1);
                            flag = 2;
                            //结束内层for循环
                            break;
                        }
                    }
                }
                if (flag == 1) {
                    //new pojo放入集合
                    EmployeePojo employeePojo = new EmployeePojo();
                    employeePojo.setCustomerOwner(owner);
                    employeePojo.setCustomerName(1);
                    employeePojo.setOwnerDepartment(department);
                    employeePojo.setCustomerNum(customerNumMap().get(owner));
                    pojoList.add(employeePojo);
                }
                percentFlag = true;
            }

            //校验需求 不为Null 不含有数字
            if (flag == 1 || flag == 2) {
                if (!privateCheckNeeds(needs, owner)) {
                    //校验不通过
                    //如果pojo集合为空
                    if (null == pojoList || pojoList.size() == 0) {
                        //new pojo放入集合
                        EmployeePojo employeePojo = new EmployeePojo();
                        employeePojo.setCustomerOwner(owner);
                        employeePojo.setNeeds(1);
                        employeePojo.setOwnerDepartment(department);
                        employeePojo.setCustomerNum(customerNumMap().get(owner));
                        pojoList.add(employeePojo);
                        flag = 3;
                    }
                    //遍历集合
                    for (int j = 0; j < pojoList.size(); j++) {
                        //找到对应员工pojo
                        if (pojoList.get(j).getCustomerOwner().equals(owner)) {
                            //更新客户姓名统计不通过的结果
                            pojoList.get(j).setNeeds(pojoList.get(j).getNeeds() + 1);
                            flag = 3;
                            break;
                        }
                    }
                    if (flag == 1 || flag == 2) {
                        EmployeePojo employeePojo = new EmployeePojo();
                        employeePojo.setCustomerOwner(owner);
                        employeePojo.setNeeds(1);
                        employeePojo.setOwnerDepartment(department);
                        employeePojo.setCustomerNum(customerNumMap().get(owner));
                        pojoList.add(employeePojo);
                    }
                    percentFlag = true;
                }
            }

            //校验手机号  符合11位手机规则
            if (flag == 1 || flag == 2 || flag == 3) {
                if (!privateCheckMobile(mobile, owner)) {
                    //校验不通过
                    //如果pojo集合为空
                    if (null == pojoList || pojoList.size() == 0) {
                        //new pojo放入集合
                        EmployeePojo employeePojo = new EmployeePojo();
                        employeePojo.setCustomerOwner(owner);
                        employeePojo.setMobile(0);
                        employeePojo.setOwnerDepartment(department);
                        employeePojo.setCustomerNum(customerNumMap().get(owner));
                        pojoList.add(employeePojo);
                        flag = 4;
                    }
                    //遍历集合
                    for (int j = 0; j < pojoList.size(); j++) {
                        //找到对应员工pojo
                        if (pojoList.get(j).getCustomerOwner().equals(owner)) {
                            //更新客户姓名统计不通过的结果
                            pojoList.get(j).setMobile(pojoList.get(j).getMobile() + 1);
                            flag = 4;
                            break;
                        }
                    }
                    if (flag == 1 || flag == 2 || flag == 3) {
                        EmployeePojo employeePojo = new EmployeePojo();
                        employeePojo.setCustomerOwner(owner);
                        employeePojo.setMobile(1);
                        employeePojo.setOwnerDepartment(department);
                        employeePojo.setCustomerNum(customerNumMap().get(owner));
                        pojoList.add(employeePojo);
                    }
                    percentFlag = true;
                }
            }


            if (percentFlag) {
                precentList.add(owner);
            }
        }
        Map precentMap = CommonUtil.frequencyOfListElements(precentList);
        for (EmployeePojo employeePojo : pojoList) {
            int sumForEachOwner = (int) precentMap.get(employeePojo.getCustomerOwner());
            int all=employeePojo.getCustomerNum();
            DecimalFormat df=new DecimalFormat("0.00%");//设置保留位数
            float num = (float) sumForEachOwner / all;
            String divided = df.format(num);
            if (employeePojo.getOwnerDepartment() == "空"){
                employeePojo.setCompare(-100000);
            }else {
                employeePojo.setCompare (sumForEachOwner);
            }
            String percent = sumForEachOwner + "  /  " + divided;
            employeePojo.setPercent(percent);
        }
        System.out.println("----------------------checkAll-----------done------------");
        return pojoList;
    }


    /**
     * 校验姓名的方法
     *
     * @param customerName
     * @param owner
     * @return true为符合规则
     */
    @SuppressWarnings("all")
    private Boolean privateCheckCustomerName(String customerName, String owner) {
        //判断是否为Null
        Boolean isNull = CommonUtil.isNull(customerName);
        Boolean ifNewList = nameOutMap == null || !nameOutMap.containsKey(owner) || nameOutMap.get(owner) == null;
        if (isNull) {
            if (ifNewList) {
                List<String> nameInnerList = new ArrayList();
                nameInnerList.add("【客户姓名为空】】");
                nameOutMap.put(owner, nameInnerList);
            }else {
                List<String> nameInnerList = nameOutMap.get(owner);
                nameInnerList.add("【客户姓名为空】】");
                nameOutMap.put(owner, nameInnerList);
            }
            return false;
        }
        //首尾去空格
        String name = customerName.trim();
        Boolean hasSpecial = JudgeName.isSpecialChar(name);
        Boolean hasDigit = CommonUtil.hasDigit(name);
        //有数字或特殊字符
        if (hasSpecial & hasDigit) {
            if (ifNewList) {
                List<String> nameInnerList = new ArrayList();
                nameInnerList.add(customerName+"【有数字或特殊字符】】");
                nameOutMap.put(owner, nameInnerList);
            }else {
                List<String> nameInnerList = nameOutMap.get(owner);
                nameInnerList.add(customerName + "【有数字或特殊字符】】");
                nameOutMap.put(owner, nameInnerList);
            }
            return false;
        }
        Boolean isAllChinese = JudgeName.isAllChinese(name);
        //中文名判断
        if (isAllChinese) {
            Boolean lenIsOk = JudgeName.len(name) <= 8;
            if (lenIsOk) {
                return true;
            }
            if (ifNewList) {
                List<String> nameInnerList = new ArrayList();
                nameInnerList.add(customerName+"【中文名长度越界】】");
                nameOutMap.put(owner, nameInnerList);
            }else {
                List<String> nameInnerList=nameOutMap.get(owner);
                nameInnerList.add(customerName+"【中文名长度越界】】");
                nameOutMap.put(owner,nameInnerList);
            }
            return false;
        }
        Boolean hasChinese = JudgeName.isContainChinese(name);
        //英文名判断，不含中文
        if (hasChinese) {
            if (ifNewList) {
                List<String> nameInnerList = new ArrayList();
                nameInnerList.add(customerName+"【不是纯中/英文名 或有其它字符】】");
                nameOutMap.put(owner, nameInnerList);
            }else {
                List<String> nameInnerList = nameOutMap.get(owner);
                nameInnerList.add(customerName + "【不是纯中/英文名 或有其它字符】】");
                nameOutMap.put(owner, nameInnerList);
            }
            return false;
        }
        //英文名判断，长度小20
        Boolean judgeEngLen = JudgeName.judgEngLenRight(name);
        if (!judgeEngLen) {
            if (ifNewList) {
                List<String> nameInnerList = new ArrayList();
                nameInnerList.add(customerName+"【英文名长度越界】】");
                nameOutMap.put(owner, nameInnerList);
            }else {
                List<String> nameInnerList = nameOutMap.get(owner);
                nameInnerList.add(customerName + "【英文名长度越界】】");
                nameOutMap.put(owner, nameInnerList);
            }
            return false;
        }
        return true;
    }

    /**
     * 校验需求的方法
     *
     * @return true为符合规则
     */
    @SuppressWarnings("all")
    private Boolean privateCheckNeeds(String needs, String owner) {
        //判断是否为Null
        Boolean isNull = CommonUtil.isNull(needs);
        Boolean ifNewList = needsOutMap == null || !needsOutMap.containsKey(owner) || needsOutMap.get(owner) == null;
        if (isNull) {
            if (ifNewList) {
                List<String> needsInnerList = new ArrayList();
                needsInnerList.add(needs+"【需求为空】】");
                needsOutMap.put(owner, needsInnerList);
            }else {
                List<String> nameInnerList = needsOutMap.get(owner);
                nameInnerList.add(needs + "【需求为空】】");
                needsOutMap.put(owner, nameInnerList);
            }
            return false;
        }
        String need = needs.trim();
        Boolean hasDigit = CommonUtil.hasDigit(need);
        //是否含有数字，且数字连续出现4次以上
        if (hasDigit) {
            if (ifNewList) {
                List<String> needsInnerList = new ArrayList();
                needsInnerList.add(needs+"【需求有四个以上连续数字】】");
                needsOutMap.put(owner, needsInnerList);
            }else {
                List<String> nameInnerList = needsOutMap.get(owner);
                nameInnerList.add(needs + "【需求有四个以上连续数字】】");
                needsOutMap.put(owner, nameInnerList);
            }
            return false;
        }
        return true;
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @param owner
     * @return true为符合规则
     */
    private Boolean privateCheckMobile(String mobile, String owner) {
        Boolean mobileIsNull = CommonUtil.isNull(mobile);
        Boolean ifNewList = mobileOutMap == null || mobileOutMap.get(owner) == null || !mobileOutMap.containsKey(owner) ;
        if (mobileIsNull) {
            if (ifNewList) {
                List<String> mobileInnerList = new ArrayList();
                mobileInnerList.add("【手机号为空】】");
                mobileOutMap.put(owner, mobileInnerList);
            }else {
                List<String> mobileInnerList = mobileOutMap.get(owner);
                mobileInnerList.add("【手机号为空】】");
                mobileOutMap.put(owner,mobileInnerList);
            }
            return false;
        }
        String  mobileNotNull = mobile.trim();
        Boolean isContainChinese=JudgeName.isContainChinese(mobileNotNull);
        Boolean isSpecialChar=JudgeName.isSpecialChar(mobileNotNull);
        //是否符合手机号规则
        if (mobileNotNull.length() != 11 && isContainChinese && isSpecialChar) {
            if (ifNewList) {
                List<String> mobileInnerList = new ArrayList();
                mobileInnerList.add(mobile+"【手机号不符合规则】】");
                mobileOutMap.put(owner, mobileInnerList);
            }else {
                List<String> mobileInnerList = mobileOutMap .get(owner);
                mobileInnerList.add("【手机号为空】】");
                mobileOutMap.put(owner, mobileInnerList);
            }
            return false;
        }
        return true;
    }
}
