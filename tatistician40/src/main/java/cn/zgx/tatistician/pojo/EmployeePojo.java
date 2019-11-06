package cn.zgx.tatistician.pojo;

import lombok.Data;

@Data
public class EmployeePojo implements Comparable<EmployeePojo>{
    private int order; //排序
    private String customerOwner;
    private String OwnerDepartment;
    private int customerNum;
    private int customerName;
    private int needs;
    private int mobile;
    private String percent;//分析不通过记录总数+占比
    private int compare;

    //重写Comparable接口的compareTo方法，升序排列，降序修改相减顺序即可
    @Override
    public int compareTo(EmployeePojo o) {
        return this.compare - o.compare;
    }

    public int getCompare() {
        return compare;
    }

    public void setCompare(int compare) {
        this.compare = compare;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPercent() { return percent; }

    public void setPercent(String percent) { this.percent = percent; }

    public int getCustomerNum() { return customerNum; }

    public void setCustomerNum(int customerNum) { this.customerNum = customerNum; }

    public String getCustomerOwner() {
        return customerOwner;
    }

    public void setCustomerOwner(String customerOwner) {
        this.customerOwner = customerOwner;
    }

    public String getOwnerDepartment() {
        return OwnerDepartment;
    }

    public void setOwnerDepartment(String ownerDepartment) {
        OwnerDepartment = ownerDepartment;
    }

    public int getCustomerName() {
        return customerName;
    }

    public void setCustomerName(int customerName) {
        this.customerName = customerName;
    }

    public int getNeeds() {
        return needs;
    }

    public void setNeeds(int needs) {
        this.needs = needs;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }
}
