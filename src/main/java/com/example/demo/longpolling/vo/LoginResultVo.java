package com.example.demo.longpolling.vo;

public class LoginResultVo implements Comparable<LoginResultVo> {
    private Integer status;

    private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoginResultVo{");
        sb.append("status=").append(status);
        sb.append(", msg='").append(msg).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(LoginResultVo o) {
        return Integer.compare(this.hashCode(), o.hashCode());
    }
}
