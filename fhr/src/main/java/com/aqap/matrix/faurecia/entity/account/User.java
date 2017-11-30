package com.aqap.matrix.faurecia.entity.account;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.aqap.matrix.faurecia.entity.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 用户详情表
 * 
 * @author lyh
 * @date 2017-5-17 16:10:10
 * 
 */
@Entity
@Table(name = "sys_user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class User extends IdEntity implements Serializable{
	
	/**
	 * 0：默认，正常
	 */
	public final static Long STATUS_SUCCESS = 0l;
	/**
	 *:1：离职
	 */
	public final static Long STATUS_LEAVE = 1l;
	/**
	 * 2：失效
	 */
	public final static Long STATUS_FAIL = 2l;
	
	/**
	 * 是管理员
	 */
	public final static Long IS_ADMIN_YSE = 1l;
	/**
	 * 超级管理员
	 */
	public final static Long IS_ADMIN_SUPER = 2l;
	/**
	 * 不是管理员
	 */
	public final static Long IS_ADMIN_NO = 0l;
	
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	// 用户姓名
	private String userName;
	// 账号状态(0有效 1 离职 2失效)
	private Long status = 0L;
	//创建时间
	private Date createTime = new Date();
	//更新时间
	private Date updateTime = new Date();
	//手机号
	private String mobile;
	// 昵称
	private String nickName;
	//openid
	private String openid;
	// 头像
	private String headImgUrl;
	// 性别 1时是男性，值为2时是女性，值为0时是未知
	private Integer sex;
	// 国家
	private String country;
	// 省份
	private String province;
	// 城市
	private String city;
	//是否是管理员 0不是  1是
	private Long isAdmin = 0L;
	
	//是否关注
	private String subscribe;
	
	/**
	 * 用户企业信息
	 */
	private	String	bizuserid	;	//成员UserID。对应管理端的帐号
	private	String	bizname	;	//成员名称
	private	String	bizmobile	;	//手机号码，第三方仅通讯录套件可获取
	//private	List<FHRDepartment>    bizdepartment = new ArrayList<FHRDepartment>()	;	//成员所属部门id列表
	private	Long 	bizorder	 = 0l;	//部门内的排序值，默认为0。数量必须和department一致，数值越大排序越前面。值范围是[0, 2^32)
	private	String	bizposition	;	//职位信息
	private	int	bizgender	;	//性别。0表示未定义，1表示男性，2表示女性
	private	String	bizemail	;	//邮箱，第三方仅通讯录套件可获取
	private	String	bizisleader	;	//标示是否为上级。
	private	String	bizavatar	;	//头像url。注：如果要获取小图将url最后的”/0”改成”/100”即可
	private	String	biztelephone	;	//座机。第三方仅通讯录套件可获取
	private	String	bizenglish_name	;	//英文名。
	private	int	bizstatus	;	//激活状态: 1=已激活，2=已禁用，4=未激活 已激活代表已激活企业微信或已关注微信插件。未激活代表既未激活企业微信又未关注微信插件。
	private	String	bizextattr	;	//扩展属性，第三方仅通讯录套件可获取
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Long getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Long isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getBizuserid() {
		return bizuserid;
	}
	public void setBizuserid(String bizuserid) {
		this.bizuserid = bizuserid;
	}
	public String getBizname() {
		return bizname;
	}
	public void setBizname(String bizname) {
		this.bizname = bizname;
	}
	public String getBizmobile() {
		return bizmobile;
	}
	public void setBizmobile(String bizmobile) {
		this.bizmobile = bizmobile;
	}
	
	/*@ManyToMany(mappedBy = "bizUsers")
	public List<FHRDepartment> getBizdepartment() {
		return bizdepartment;
	}
	public void setBizdepartment(List<FHRDepartment> bizdepartment) {
		this.bizdepartment = bizdepartment;
	}*/
	public Long getBizorder() {
		return bizorder;
	}
	public void setBizorder(Long bizorder) {
		this.bizorder = bizorder;
	}
	public String getBizposition() {
		return bizposition;
	}
	public void setBizposition(String bizposition) {
		this.bizposition = bizposition;
	}
	public int getBizgender() {
		return bizgender;
	}
	public void setBizgender(int bizgender) {
		this.bizgender = bizgender;
	}
	public String getBizemail() {
		return bizemail;
	}
	public void setBizemail(String bizemail) {
		this.bizemail = bizemail;
	}
	public String getBizisleader() {
		return bizisleader;
	}
	public void setBizisleader(String bizisleader) {
		this.bizisleader = bizisleader;
	}
	public String getBizavatar() {
		return bizavatar;
	}
	public void setBizavatar(String bizavatar) {
		this.bizavatar = bizavatar;
	}
	public String getBiztelephone() {
		return biztelephone;
	}
	public void setBiztelephone(String biztelephone) {
		this.biztelephone = biztelephone;
	}
	public String getBizenglish_name() {
		return bizenglish_name;
	}
	public void setBizenglish_name(String bizenglish_name) {
		this.bizenglish_name = bizenglish_name;
	}
	public int getBizstatus() {
		return bizstatus;
	}
	public void setBizstatus(int bizstatus) {
		this.bizstatus = bizstatus;
	}
	public String getBizextattr() {
		return bizextattr;
	}
	public void setBizextattr(String bizextattr) {
		this.bizextattr = bizextattr;
	}
	public String getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	
	
}
