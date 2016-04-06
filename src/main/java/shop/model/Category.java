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

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    private List<Category> childCategories;

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

    public List<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<Category> childCategories) {
        this.childCategories = childCategories;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentCategory.id=" + parentCategory.getId() +
                '}';
    }
}
