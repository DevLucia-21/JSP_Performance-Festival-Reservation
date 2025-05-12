package com.perfortival.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.perfortival.common.db.DBUtil;
import com.perfortival.member.dto.MemberDTO;

public class MemberDAO {

    //로그인 메서드
    public MemberDTO login(String id, String pw) {
        MemberDTO member = null;

        String sql = "SELECT id, pw, name, email, is_admin FROM members WHERE id = ? AND pw = ?";

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
                member.setEmail(rs.getString("email"));
                member.setAdmin(rs.getBoolean("is_admin"));  // 관리자 여부 반영
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }

    //회원가입 메서드
    public boolean signup(MemberDTO member) {
        String sql = "INSERT INTO members (id, pw, name, email, is_admin) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getPw());
            pstmt.setString(3, member.getName());
            pstmt.setString(4, member.getEmail());
            pstmt.setBoolean(5, member.isAdmin());  // 관리자 여부 저장

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //회원 정보 업데이트 메서드
    public boolean update(MemberDTO member) {
        String sql = "UPDATE members SET pw = ?, name = ?, email = ?, is_admin = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, member.getPw());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getEmail());
            pstmt.setBoolean(4, member.isAdmin());  // 관리자 여부 업데이트
            pstmt.setString(5, member.getId());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //회원 탈퇴 메서드
    public boolean deleteMember(String id) {
        String sql = "DELETE FROM members WHERE id = ?";

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
