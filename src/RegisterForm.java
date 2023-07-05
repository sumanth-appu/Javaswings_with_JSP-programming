import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterForm extends JDialog {
    private JTextField tfphone;
    private JTextField tfname;
    private JTextField tfmail;
    private JTextField tfaddress;
    private JButton cancelButton;
    private JButton registerButton;
    private JPanel Registerpannel;
    private JPasswordField pfpassword;
    private JPasswordField pfconfirm;


    public  RegisterForm(JFrame parent){
            setTitle("create new account");
            setContentPane(Registerpannel);
            setMinimumSize(new Dimension(1200,800   ));
            setModal(true);
            setLocationRelativeTo(parent);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        registerButton.addActionListener(e -> registeruser());
        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }

    private void registeruser() {

        String name=tfname.getText();
        String phone=tfphone.getText();
        String email=tfmail.getText();
        String address=tfaddress.getText();
        String password=String.valueOf(pfpassword.getPassword());
        String confirmpassword=String.valueOf(pfconfirm.getPassword());

        if (name.isEmpty()||email.isEmpty()||phone.isEmpty()||address.isEmpty()||password.isEmpty()||confirmpassword.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "plese enter all fields",
                    "try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmpassword)){
            JOptionPane.showMessageDialog(this,
                    "plese enter all fields",
                    "try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
       user= addUserToDatabase(name,phone,email,address,password);
        if (user==null){
            dispose();
        }

    }
public user user;
    private user addUserToDatabase(String name, String phone, String email, String address, String password) {
        user user= null;

        final String DB_URL="jdbc:mysql://localhost/MyStore?ServerTimeZone=UTC";
        final String USERNAME="root";
        final String PASSWORD="";

        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt=conn.createStatement();
            String sql="INSERT INTO user(name,phone,email,address,password)"+
            "VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,phone);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,address);
            preparedStatement.setString(5,password);


            int addRows=preparedStatement.executeUpdate();
            if (addRows>0)
            {
                user=new user();
                user.name=name;
                user.phone=phone;
                user.email=email;
                user.address=address;
                user.password=password;
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static void main(String[] args) {
        RegisterForm myform=new RegisterForm(null);
        user user= myform.user;
         if (user!=null){
             System.out.println(" Registration SucessFull "+user.name);

         }
         else {
             System.out.println("Registration unsucessfull");
         }

    }


}
