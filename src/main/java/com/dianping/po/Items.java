package com.dianping.po;

import java.util.Date;

public class Items {
//    自动生成的mapper.xml文件，只能用户对items的单表查询，通常我们需要自定义mapper.java和mapper.xml
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column items.id
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column items.name
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column items.price
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    private Float price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column items.pic
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    private String pic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column items.createtime
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column items.detail
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    private String detail;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column items.id
     *
     * @return the value of items.id
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column items.id
     *
     * @param id the value for items.id
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column items.name
     *
     * @return the value of items.name
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column items.name
     *
     * @param name the value for items.name
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column items.price
     *
     * @return the value of items.price
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    public Float getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column items.price
     *
     * @param price the value for items.price
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column items.pic
     *
     * @return the value of items.pic
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    public String getPic() {
        return pic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column items.pic
     *
     * @param pic the value for items.pic
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column items.createtime
     *
     * @return the value of items.createtime
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column items.createtime
     *
     * @param createtime the value for items.createtime
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column items.detail
     *
     * @return the value of items.detail
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    public String getDetail() {
        return detail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column items.detail
     *
     * @param detail the value for items.detail
     *
     * @mbggenerated Thu Jan 26 01:48:46 CST 2017
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }
}