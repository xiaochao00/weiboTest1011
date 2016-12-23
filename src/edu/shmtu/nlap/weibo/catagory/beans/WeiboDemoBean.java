package edu.shmtu.nlap.weibo.catagory.beans;
/**
 * ����΢�����ݵ� ʵ���װ��
 * @author HP_xiaochao
 *
 */
public class WeiboDemoBean {
	/**
	 * �û�id
�û���
����΢��id
����΢��ʱ��
����΢������
���
	 */
	private String uid;//�û����
	private String nickname;//�ǳ�
	private String wid;//΢��id��
	private String date;//
	private String text = "";//΢������
	private String catagory;//���
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text.trim();
	}
	public String getCatagory() {
		return catagory;
	}
	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}
	
	public String toString(){
		return "text:"+text+"\n date:"+date;
	}
}
