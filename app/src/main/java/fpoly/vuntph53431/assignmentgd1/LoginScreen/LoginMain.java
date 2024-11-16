package fpoly.vuntph53431.assignmentgd1.LoginScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.vuntph53431.assignmentgd1.DAO.ManagerDAO;
import fpoly.vuntph53431.assignmentgd1.Manager.AppManager;
import fpoly.vuntph53431.assignmentgd1.Model.User;
import fpoly.vuntph53431.assignmentgd1.R;

public class LoginMain extends AppCompatActivity {

    private EditText edtUser, edtPassword;
    private Button btnLogin,btnRegister;

    private ManagerDAO managerDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        edtUser = findViewById(R.id.edtuser);
        edtPassword = findViewById(R.id.edtpassword);
        btnRegister = findViewById(R.id.btnregister);
        btnLogin = findViewById(R.id.btnlogin);
        managerDAO = new ManagerDAO(LoginMain.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtUser.getText().toString();
                String pass = edtPassword.getText().toString();

                if (user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(LoginMain.this, "Đăng nhập thất bại, không được để trống !", Toast.LENGTH_SHORT).show();
                } else {
                    User admin = managerDAO.getAdminaccount();
                    //Check admin
                    if (user.equals(admin.getUser())){
                        if (pass.equals(admin.getPassword())) {
                            AppManager.shared().isAdmin = true;
                            Toast.makeText(LoginMain.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginMain.this, Main.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //Thongbao nhap sai
                        }

                    }

                    //Check user
                    else {
                        for (User u: managerDAO.getAllUser()) {
                            if ( user.equals(u.getUser()) ) {
                                if (pass.equals(u.getPassword())) {
                                    // vao man trong
                                    AppManager.shared().isAdmin = false;
                                    Toast.makeText(LoginMain.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT);
                                    Intent intent = new Intent(LoginMain.this, Main.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                } else {
                                    Toast.makeText(LoginMain.this, "Đăng nhập thất bại, sai mật khẩu và tài khoản", Toast.LENGTH_SHORT);
                                }
                            }
                        }

                        Toast.makeText(LoginMain.this, "Đăng nhập thất bại, tài khoản không tồn tại", Toast.LENGTH_SHORT);
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginMain.this, RegisterMain.class);
                startActivity(intent);
            }
        });
    }
}