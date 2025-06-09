package com.perfortival.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.perfortival.common.db.DBUtil;
import com.perfortival.member.dto.MemberDTO;

public class MemberDAO {

    // 로그인 메서드
    public MemberDTO login(String id, String pw) {
        MemberDTO member = null;

        String sql = "SELECT id, pw, name, email, is_admin, address FROM members WHERE id = ? AND pw = ?";

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
                member.setAdmin(rs.getBoolean("is_admin"));
                member.setAddress(rs.getString("address"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }

    // 회원가입 메서드
    public boolean signup(MemberDTO member) {
        String sql = "INSERT INTO members (id, pw, name, email, is_admin, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getPw());
            pstmt.setString(3, member.getName());
            pstmt.setString(4, member.getEmail());
            pstmt.setBoolean(5, member.isAdmin());
            pstmt.setString(6, member.getAddress());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 회원 정보 업데이트 메서드
    public boolean update(MemberDTO member) {
        String sql = "UPDATE members SET pw = ?, name = ?, email = ?, is_admin = ?, address = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, member.getPw());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getEmail());
            pstmt.setBoolean(4, member.isAdmin());
            pstmt.setString(5, member.getAddress());
            pstmt.setString(6, member.getId());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 회원 탈퇴 메서드
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

    // 아이디 중복 확인
    public boolean isDuplicateId(String id) {
        boolean isDuplicate = false;
        String sql = "SELECT COUNT(*) FROM members WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    isDuplicate = rs.getInt(1) > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isDuplicate;
    }
    
    public String findIdByNameAndEmail(String name, String email) {
        String sql = "SELECT id FROM members WHERE name = ? AND email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String findPwByNameIdEmail(String name, String id, String email) {
        String sql = "SELECT pw FROM members WHERE name = ? AND id = ? AND email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, id);
            pstmt.setString(3, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("pw");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
