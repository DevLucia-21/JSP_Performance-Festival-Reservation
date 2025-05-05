package model.dao;

import java.sql.*;
import model.dto.MemberDTO;
import util.DBUtil;

public class MemberDAO {

	public MemberDTO login(String id, String pw) {
	    MemberDTO member = null;

	    String sql = "SELECT * FROM member WHERE id = ? AND pw = ?";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, id);
	        pstmt.setString(2, pw);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            member = new MemberDTO();
	            member.setId(rs.getString("id"));
	            member.setPw(rs.getString("pw"));
	            member.setName(rs.getString("name"));
	            member.setEmail(rs.getString("email"));  // 컬럼명에 따라 수정
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return member;
	}

	public boolean signup(MemberDTO member) {
	    String sql = "INSERT INTO member (id, pw, name, email) VALUES (?, ?, ?, ?)";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, member.getId());
	        pstmt.setString(2, member.getPw());
	        pstmt.setString(3, member.getName());
	        pstmt.setString(4, member.getEmail());

	        int rows = pstmt.executeUpdate();
	        return rows > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean update(MemberDTO member) {
	    String sql = "UPDATE member SET pw = ?, name = ?, email = ? WHERE id = ?";
	    
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, member.getPw());
	        pstmt.setString(2, member.getName());
	        pstmt.setString(3, member.getEmail());
	        pstmt.setString(4, member.getId());

	        int rows = pstmt.executeUpdate();
	        return rows > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean deleteMember(String id) {
	    String sql = "DELETE FROM member WHERE id = ?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, id);
	        int result = pstmt.executeUpdate();
	        return result > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
