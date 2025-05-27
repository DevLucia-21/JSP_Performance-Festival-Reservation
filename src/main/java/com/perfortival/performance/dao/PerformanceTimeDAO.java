package com.perfortival.performance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.perfortival.common.db.DBUtil;
import com.perfortival.performance.dto.PerformanceTimeDTO;

public class PerformanceTimeDAO {

    public boolean insertTimes(List<PerformanceTimeDTO> timeList) {
        String sql = "INSERT INTO performance_times (performance_id, time) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (PerformanceTimeDTO dto : timeList) {
                pstmt.setString(1, dto.getPerformanceId());
                pstmt.setString(2, dto.getTime());
                pstmt.addBatch();
            }

            int[] results = pstmt.executeBatch();
            return results.length == timeList.size();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<PerformanceTimeDTO> getTimesByPerformanceId(String performanceId) {
        List<PerformanceTimeDTO> list = new ArrayList<>();
        String sql = "SELECT id, time FROM performance_times WHERE performance_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, performanceId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                PerformanceTimeDTO dto = new PerformanceTimeDTO();
                dto.setId(rs.getInt("id"));
                dto.setPerformanceId(performanceId);
                dto.setTime(rs.getString("time"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
