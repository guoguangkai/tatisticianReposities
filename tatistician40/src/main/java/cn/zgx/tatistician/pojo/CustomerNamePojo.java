package cn.zgx.tatistician.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CustomerNamePojo {
    private String owner;
    private String customerName;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
