package fpoly.vuntph53431.assignmentgd1.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.vuntph53431.assignmentgd1.Adapter.RCAdapterUser;
import fpoly.vuntph53431.assignmentgd1.DAO.ManagerDAO;
import fpoly.vuntph53431.assignmentgd1.Model.User;
import fpoly.vuntph53431.assignmentgd1.R;


public class UserFragment extends Fragment {

    private ImageView imgAddIconUser;
    private RecyclerView rcListUser;
    private RCAdapterUser adapterUser;
    private ManagerDAO managerDAO;
    private Context context;
    private ArrayList<User> list;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        managerDAO = new ManagerDAO(context);

        rcListUser = view.findViewById(R.id.rclistuser);

        list = managerDAO.getAllUser();
        adapterUser = new RCAdapterUser(context,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcListUser.setLayoutManager(linearLayoutManager);
        rcListUser.setAdapter(adapterUser);
        imgAddIconUser = view.findViewById(R.id.imgaddiconuser);
        imgAddIconUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.popup_add_user,null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                EditText username = view1.findViewById(R.id.edtusername);
                EditText password = view1.findViewById(R.id.edtpassword);
//                EditText role = view1.findViewById(R.id.edtrole);
                Button oke = view1.findViewById(R.id.btnokeuser);
                Button cancel = view1.findViewById(R.id.btncanceluser);

                oke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String addnameuser = username.getText().toString();
                        String addpassword = password.getText().toString();

                        if (addnameuser.isEmpty() || addpassword.isEmpty()){
                            Toast.makeText(context, "Không được để trống, mời nhập lại", Toast.LENGTH_SHORT).show();
                        }else {
                            User newUser = new User(addnameuser,addpassword);
                            boolean add = managerDAO.addUser(newUser);
                            if (add){
                                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                list.add(newUser);
                                adapterUser.notifyDataSetChanged();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(context, "Thêm thất bại, tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
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
        });
    }
}