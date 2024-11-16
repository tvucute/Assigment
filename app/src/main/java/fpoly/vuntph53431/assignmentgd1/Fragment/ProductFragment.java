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

import fpoly.vuntph53431.assignmentgd1.Adapter.RCAdapterProduct;
import fpoly.vuntph53431.assignmentgd1.DAO.ManagerDAO;
import fpoly.vuntph53431.assignmentgd1.Model.Product;
import fpoly.vuntph53431.assignmentgd1.R;


public class ProductFragment extends Fragment {

    private ImageView imgAddIcon;
    private RecyclerView rcListProduct;
    private RCAdapterProduct adapterProduct;
    private ManagerDAO managerDAO;
    private Context context;
    private ArrayList<Product> list;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
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
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        managerDAO = new ManagerDAO(context);
        rcListProduct = view.findViewById(R.id.rclistproduct);
        adapterProduct = new RCAdapterProduct(context,managerDAO.getProduct());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcListProduct.setLayoutManager(linearLayoutManager);
        rcListProduct.setAdapter(adapterProduct);

        imgAddIcon = view.findViewById(R.id.imgaddicon);
        imgAddIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.popup_add_product,null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                EditText name = view1.findViewById(R.id.edtaddname);
                EditText price = view1.findViewById(R.id.edtaddprice);
                EditText size = view1.findViewById(R.id.edtaddsize);
                Button oke = view1.findViewById(R.id.btnaddoke);
                Button cancel = view1.findViewById(R.id.btnaddcancel);

                oke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String addname = name.getText().toString();
                        String addprice = price.getText().toString();
                        String addsize = size.getText().toString();
                        if (addname.isEmpty() || addprice.isEmpty() || addsize.isEmpty()){
                            Toast.makeText(context, "Không hợp lệ mời nhập lại", Toast.LENGTH_SHORT).show();
                        }else {
                            boolean add = managerDAO.addProduct(new Product(addname,Integer.parseInt(addprice),addsize));
                            if (add == true){
                                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                adapterProduct = new RCAdapterProduct(context,managerDAO.getProduct());
                                rcListProduct.setAdapter(adapterProduct);
                                dialog.dismiss();
                            }else {
                                Toast.makeText(context, "Tên sản phẩm đã tồn tại", Toast.LENGTH_SHORT).show();
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