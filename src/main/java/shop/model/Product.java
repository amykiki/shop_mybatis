package shop.model;

import shop.enums.PStatus;
import shop.web.annotation.*;

/**
 * Created by Amysue on 2016/4/7.
 */
public class Product {
    private int id;

    @NotNull
    @AddFiled
    @UpdateFiled
    @CheckEmpty(errMsg = "商品名称不能为空")
    private String name;

    // TODO: 2016/4/7  默认值为0，所以在update时候需要注意，必须加上值，不能用默认
    @NotNull
    @AddFiled
    @UpdateFiled
    @CheckPrice(errMsg = "价格单位为元，必须为数字,小数点后最多有两位")
    private double price;

    @AddFiled
    @UpdateFiled
    private String intro;

    @NotNull
    @AddFiled
    @UpdateFiled
    @CheckImg(errMsg = "照片必须为jpg, bmp, png, gif格式")
    private String img;

    // TODO: 2016/4/7  默认值为0，所以在update时候需要注意，必须加上值，不能用默认
    @NotNull
    @AddFiled
    @UpdateFiled
    @CheckNum(errMsg = "商品库存必须为大于等于0整数", length = 1)
    private int stock;

    // TODO: 2016/4/7  默认值为0，所以在update时候需要注意，必须加上值，不能用默认
    private int sales;

    private PStatus status;

    private Category category;

    public Product() {
        price = -1;
        stock = -1;
        sales = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public PStatus getStatus() {
        return status;
    }

    public void setStatus(PStatus status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        String str = "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", stock=" + stock +
                ", sales=" + sales +
                ", status=" + status +
                ", category=" + category;
        if (intro != null) {
            str += ", intro='" + intro + '\'';
        }
        str += "}";
        return str;
    }
}
