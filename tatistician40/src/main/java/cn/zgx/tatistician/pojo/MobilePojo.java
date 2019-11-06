package cn.zgx.tatistician.pojo;

import lombok.Data;

import java.util.List;
@Data
public class MobilePojo {
    private String owner;
    private String mobile;

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
}
