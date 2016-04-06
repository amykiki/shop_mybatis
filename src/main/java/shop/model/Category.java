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
