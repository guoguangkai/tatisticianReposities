package cn.zgx.tatistician.pojo;

import lombok.Data;

@Data
public class CustomerNumPojo {
    private String owner;
    private int customerNum;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(int customerNum) {
        this.customerNum = customerNum;
    }
}