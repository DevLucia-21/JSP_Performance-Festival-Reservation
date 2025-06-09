package com.perfortival.member.dto;

public class MemberDTO {
    private String id;
    private String pw;
    private String name;
    private String email;
    private boolean isAdmin;  // 관리자 여부 필드 추가
    private String address;

    public MemberDTO() {}

    public MemberDTO(String id, String pw, String name, String email, boolean isAdmin) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    // Getter & Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public String getAddress() {
    	return address; 
    }
    
    public void setAddress(String address) {
    	this.address = address; 
    }
}
