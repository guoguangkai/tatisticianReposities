package cn.zgx.tatistician.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Customer {
 private String customerName;
    private String needs;
    private String owner;
    private String mobile;
    private Employee department;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getNeeds() {
        return needs;
    }

    public void setNeeds(String needs) {
        this.needs = needs;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Employee getDepartment() {
        return department;
    }

    public void setDepartment(Employee department) {
        this.department = department;
    }
}
