package shop.model;

import shop.web.annotation.*;

import java.util.List;

/**
 * Created by Amysue on 2016/4/4.
 */
public class Category {
    private int id;

    @AddFiled
    @UpdateFiled
    @NotNull
    @CheckEmpty(errMsg = "商品分类名不能为空", length = 2)
    private String name;

    private Category parentCategory;
//    分类的深度
    private int depth;
    // TODO: 2016/4/9      用于在页面显示分类是否选中所用，这个方法不太好，需要前端有没有更好的办法
    private int checked;

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(int id) {
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
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

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        String str =  "Category{" +
                "id=" + id +
                ", name='" + name;
        if (this.getParentCategory() != null) {
            str +=  ", parentCategory.id=" + parentCategory.getId() +
                    ", parentCategory.name=" + parentCategory.getName();
        }
        if (this.getDepth() > 0) {
            str += ", depth = " + depth;
        }
        str += "}";
        return str;
    }
}
