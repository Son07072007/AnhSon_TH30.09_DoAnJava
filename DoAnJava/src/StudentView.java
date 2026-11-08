import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentView extends JFrame {
    private JTextField txtMaSV, txtHoTen, txtGioiTinh, txtMaLop;
    private JTable table;
    private DefaultTableModel tableModel;
    private StudentDAO studentDAO;

    public StudentView() {
        studentDAO = new StudentDAO();
        setTitle("Quản Lý Sinh Viên - Đồ Án Java");
        setSize(750, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelInput = new JPanel(new GridLayout(4, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));

        panelInput.add(new JLabel("Mã SV:"));
        txtMaSV = new JTextField();
        panelInput.add(txtMaSV);

        panelInput.add(new JLabel("Họ Tên:"));
        txtHoTen = new JTextField();
        panelInput.add(txtHoTen);

        panelInput.add(new JLabel("Giới Tính:"));
        txtGioiTinh = new JTextField();
        panelInput.add(txtGioiTinh);

        panelInput.add(new JLabel("Mã Lớp:"));
        txtMaLop = new JTextField();
        panelInput.add(txtMaLop);

        add(panelInput, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Mã SV", "Họ Tên", "Giới Tính", "Mã Lớp"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        JButton btnLoad = new JButton("Tải danh sách");
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        
        panelButtons.add(btnLoad);
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        add(panelButtons, BorderLayout.SOUTH);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaSV.setText(tableModel.getValueAt(row, 0).toString());
                    txtHoTen.setText(tableModel.getValueAt(row, 1).toString());
                    txtGioiTinh.setText(tableModel.getValueAt(row, 2) != null ? tableModel.getValueAt(row, 2).toString() : "");
                    txtMaLop.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
                    txtMaSV.setEditable(false); 
                }
            }
        });

        btnLoad.addActionListener(e -> {
            loadDataToTable();
            clearForm();
        });

        btnAdd.addActionListener(e -> {
            String maSV = txtMaSV.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String gioiTinh = txtGioiTinh.getText().trim();
            String maLop = txtMaLop.getText().trim();

            if(maSV.isEmpty() || hoTen.isEmpty() || maLop.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ Mã SV, Họ Tên và Mã Lớp!");
                return;
            }

            Student sv = new Student(maSV, hoTen, gioiTinh, maLop);
            if(studentDAO.insertStudent(sv)) {
                JOptionPane.showMessageDialog(null, "Thêm sinh viên thành công!");
                loadDataToTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(null, "Thêm thất bại (Trùng mã SV hoặc sai Mã Lớp).");
            }
        });

        btnUpdate.addActionListener(e -> {
            String maSV = txtMaSV.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String gioiTinh = txtGioiTinh.getText().trim();
            String maLop = txtMaLop.getText().trim();

            if(maSV.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn sinh viên cần sửa từ bảng!");
                return;
            }

            Student sv = new Student(maSV, hoTen, gioiTinh, maLop);
            if(studentDAO.updateStudent(sv)) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                loadDataToTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
            }
        });

        btnDelete.addActionListener(e -> {
            String maSV = txtMaSV.getText().trim();
            if(maSV.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn sinh viên cần xóa từ bảng!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa sinh viên " + maSV + " không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if(studentDAO.deleteStudent(maSV)) {
                    JOptionPane.showMessageDialog(null, "Xóa sinh viên thành công!");
                    loadDataToTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                }
            }
        });

        loadDataToTable();
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<Student> list = studentDAO.getAllStudents();
        for (Student sv : list) {
            tableModel.addRow(new Object[]{
                sv.getMaSV(), sv.getHoTen(), sv.getGioiTinh(), sv.getMaLop()
            });
        }
    }

    private void clearForm() {
        txtMaSV.setText("");
        txtHoTen.setText("");
        txtGioiTinh.setText("");
        txtMaLop.setText("");
        txtMaSV.setEditable(true);
        table.clearSelection();
    }
}