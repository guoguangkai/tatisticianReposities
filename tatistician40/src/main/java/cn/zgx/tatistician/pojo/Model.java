package cn.zgx.tatistician.pojo;

import lombok.Data;

@Data
public class Model {
    private String customerOwner;
    private String col;

    public String getCustomerOwner() {
        return customerOwner;
    }

    public void setCustomerOwner(String customerOwner) {
        this.customerOwner = customerOwner;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }
}
