import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String query = "SELECT MaSV, HoTen, GioiTinh, MaLop FROM SinhVien";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Student sv = new Student(
                    rs.getString("MaSV"),
                    rs.getString("HoTen"),
                    rs.getString("GioiTinh"),
                    rs.getString("MaLop")
                );
                list.add(sv);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn dữ liệu: " + e.getMessage());
        }
        return list;
    }

    public boolean insertStudent(Student sv) {
        String query = "INSERT INTO SinhVien (MaSV, HoTen, GioiTinh, MaLop) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, sv.getMaSV());
            pstmt.setString(2, sv.getHoTen());
            pstmt.setString(3, sv.getGioiTinh());
            pstmt.setString(4, sv.getMaLop());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi thêm sinh viên: " + e.getMessage());
        }
        return false;
    }

    public boolean updateStudent(Student sv) {
        String query = "UPDATE SinhVien SET HoTen = ?, GioiTinh = ?, MaLop = ? WHERE MaSV = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, sv.getHoTen());
            pstmt.setString(2, sv.getGioiTinh());
            pstmt.setString(3, sv.getMaLop());
            pstmt.setString(4, sv.getMaSV());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteStudent(String maSV) {
        String query = "DELETE FROM SinhVien WHERE MaSV = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, maSV);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi xóa: " + e.getMessage());
        }
        return false;
    }
}