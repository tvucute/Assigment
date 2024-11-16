package fpoly.vuntph53431.assignmentgd1.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.vuntph53431.assignmentgd1.DAO.ManagerDAO;
import fpoly.vuntph53431.assignmentgd1.Model.User;
import fpoly.vuntph53431.assignmentgd1.R;

public class RCAdapterUser extends RecyclerView.Adapter<RCAdapterUser.UserViewholder>{
    private Context context;
    private ManagerDAO managerDAO;
    private ArrayList<User> list;

    public RCAdapterUser(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
        managerDAO = new ManagerDAO(context);
    }

    @NonNull
    @Override
    public UserViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_user,parent,false);
        return new UserViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewholder holder, int position) {
        User user = list.get(position);
        holder.tvNameUser.setText(user.getUser());
        holder.tvPassUser.setText("Password: "+user.getPassword());
        holder.tvRoleUser.setText("Role: "+user.getRole());
        holder.imgAvatarUser.setImageResource(R.drawable.user);

        holder.imgDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog(position,user);
            }
        });

        holder.imgUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog(position,user);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    public static class UserViewholder extends RecyclerView.ViewHolder{
        private ImageView imgAvatarUser, imgDeleteUser, imgUpdateUser;
        private TextView tvNameUser,tvPassUser,tvRoleUser;
        public UserViewholder(@NonNull View itemView) {
            super(itemView);
            imgAvatarUser = itemView.findViewById(R.id.imgavataruser);
            imgDeleteUser = itemView.findViewById(R.id.imgdeleteuser);
            imgUpdateUser = itemView.findViewById(R.id.imgupdateuser);
            tvNameUser = itemView.findViewById(R.id.tvnameuser);
            tvPassUser = itemView.findViewById(R.id.tvpassuser);
            tvRoleUser = itemView.findViewById(R.id.tvroleuser);
        }
    }

    public void deleteDialog(int index,User user){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa người dùng này ?");
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean checkdel = managerDAO.deleteUser(user.getId());
                if (checkdel){
                    list.remove(index);
                    notifyDataSetChanged();
                }
            }
        });
        builder.show();
    }

    public void updateDialog(int index, User user){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popup_update_user,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        EditText username = view.findViewById(R.id.edtupuser);
        EditText password = view.findViewById(R.id.edtuppassword);
        Button oke = view.findViewById(R.id.btnupokeuser);
        Button cancel = view.findViewById(R.id.btnupcanceluser);

        username.setText(user.getUser());
        password.setText(user.getPassword());

        oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newuser = username.getText().toString();
                String newpass = password.getText().toString();

                if (newuser.isEmpty() || newpass.isEmpty()){
                    Toast.makeText(context, "Không được để trống!", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        list.get(index).setUser(newuser);
                        list.get(index).setPassword(newpass);
                        managerDAO.updateUser(list.get(index));
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Cập nhật tài khoản thành công!", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(context, "Cập nhật tài khoản thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
