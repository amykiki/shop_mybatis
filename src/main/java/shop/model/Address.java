package shop.model;

import shop.web.annotation.*;

/**
 * Created by Amysue on 2016/3/16.
 */
public class Address {
    private int id;

    @AddFiled @UpdateFiled @NotNull
    @CheckEmpty(errMsg = "收件人不能为空")
    private String recipient;

    @AddFiled @UpdateFiled @NotNull
    @CheckEmpty(length = 3, errMsg = "收货地址不能为空,长度不能小于3位")
    private String addressInfo;

    @AddFiled @UpdateFiled @NotNull
    @CheckNum(length = 6, errMsg = "电话必须为数字，长度不能小于6位")
    private String phone;

    @AddFiled @UpdateFiled
    @CheckNum(length = 3, errMsg = "邮政编码必须为数字，不能小于3位")
    private String zip;

    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", recipient='" + recipient + '\'' +
                ", addressInfo='" + addressInfo + '\'' +
                ", phone='" + phone + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
