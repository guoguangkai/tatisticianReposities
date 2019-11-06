package cn.zgx.tatistician.pojo;

import lombok.Data;

@Data
public class TimerEmployeePojo {
    private int order;
    private String customerOwner;
    private String OwnerDepartment;
    private int customerNum;
    private int customerName;
    private int needs;
    private int mobile;
    private String percent;
    private String percentChange;

    public String getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(String percentChange) {
        this.percentChange = percentChange;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

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

    public int getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(int customerNum) {
        this.customerNum = customerNum;
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

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
