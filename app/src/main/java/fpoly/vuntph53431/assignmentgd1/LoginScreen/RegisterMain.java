package fpoly.vuntph53431.assignmentgd1.LoginScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.vuntph53431.assignmentgd1.DAO.ManagerDAO;
import fpoly.vuntph53431.assignmentgd1.Model.User;
import fpoly.vuntph53431.assignmentgd1.R;

public class RegisterMain extends AppCompatActivity {
    private EditText edtreUser,edtrePassword,edtConfirmPassword;
    private Button btnRegis,btnCancel;

    private ManagerDAO managerDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);
        edtreUser = findViewById(R.id.edtreuser);
        edtrePassword = findViewById(R.id.edtrepassword);
        edtConfirmPassword = findViewById(R.id.edtconfirm);
        btnRegis = findViewById(R.id.btnregis);
        btnCancel = findViewById(R.id.btnrecancel);
        managerDAO = new ManagerDAO(RegisterMain.this);

        Log.d("hihihi", "onCreate: " + managerDAO.getAdminaccount().getUser());

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reuser = edtreUser.getText().toString();
                String repass = edtrePassword.getText().toString();
                String confirm = edtConfirmPassword.getText().toString();
                
                if (reuser.isEmpty() || repass.isEmpty() || confirm.isEmpty()){
                    Toast.makeText(RegisterMain.this, "Không được để trống mời nhập lại", Toast.LENGTH_SHORT).show();
                } else if (!confirm.equals(repass)) {
                    Toast.makeText(RegisterMain.this, "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
                } else {
                    boolean adduser = managerDAO.addUser(new User(reuser,repass));
                    if (adduser == true){
                        Toast.makeText(RegisterMain.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        getOnBackPressedDispatcher().onBackPressed();
                    }else {
                        Toast.makeText(RegisterMain.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterMain.this,LoginMain.class);
                startActivity(intent);
            }
        });
    }
}