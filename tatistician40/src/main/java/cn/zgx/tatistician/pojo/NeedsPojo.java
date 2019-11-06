package cn.zgx.tatistician.pojo;

import lombok.Data;

import java.util.List;
@Data
public class NeedsPojo {
    private String owner;
    private String needs;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNeeds() {
        return needs;
    }

    public void setNeeds(String needs) {
        this.needs = needs;
    }
}
