package shop.model;

import shop.web.annotation.NUMBERS;
import shop.web.annotation.NotNULL;

import java.util.List;

/**
 * Created by Amysue on 2016/3/16.
 */
public class User {
    private int id;
    @NotNULL(value = true, errMsg = "用户名不能为空，长度不能小于3位", length = 3)
    private String username;
    @NotNULL(value = true, errMsg = "密码不能为空, 长度不能小于5位", length = 5)
    private String password;
    @NotNULL(value = true, errMsg = "昵称不能为空，长度不能小于3位", length = 3)
    private String nickname;
    private Role role;
    private List<Address> addresses;

    public User() {

    }

    public User(String username, String password, String nickname, Role role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    private User(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", role=" + role +
                '}';
    }
}
