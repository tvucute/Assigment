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
import fpoly.vuntph53431.assignmentgd1.Model.Product;
import fpoly.vuntph53431.assignmentgd1.R;

public class RCAdapterProduct extends RecyclerView.Adapter<RCAdapterProduct.ProductViewholder> {
    private Context context;
    private ArrayList<Product> list;
    private ManagerDAO managerDAO;

    public RCAdapterProduct(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
        managerDAO = new ManagerDAO(context);
    }

    @NonNull
    @Override
    public ProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_product,parent,false);
        return new ProductViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewholder holder, int position) {
        Product product = list.get(position);
        holder.tvNameProduct.setText(product.getName());
        holder.tvPriceProduct.setText(String.valueOf(product.getPrice()));
        holder.tvSizeProduct.setText("SL: " + product.getSize());
        holder.imgAvatarProduct.setImageResource(R.drawable.foodpack);

        holder.imgDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletedialog(position,product);
            }
        });

        holder.imgUpdateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedialog(position,product);
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

    public static class ProductViewholder extends RecyclerView.ViewHolder{
        private ImageView imgAvatarProduct,imgDeleteIcon,imgUpdateIcon;
        private TextView tvNameProduct, tvPriceProduct, tvSizeProduct;
        public ProductViewholder(@NonNull View itemView) {
            super(itemView);
            imgAvatarProduct = itemView.findViewById(R.id.imgavatarproduct);
            imgDeleteIcon = itemView.findViewById(R.id.imgdeleteicon);
            imgUpdateIcon = itemView.findViewById(R.id.imgupdateicon);
            tvNameProduct = itemView.findViewById(R.id.tvnameproduct);
            tvPriceProduct = itemView.findViewById(R.id.tvpriceproduct);
            tvSizeProduct = itemView.findViewById(R.id.tvsizeproduct);
        }
    }

    public void deletedialog(int index,Product product){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này");
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean checkdel = managerDAO.deleteProduct(product.getId());
                if (checkdel){
                    list.remove(index);
                    notifyDataSetChanged();
                }
            }
        });
        builder.show();
    }

    public void updatedialog(int index, Product product){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popup_update_product,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        EditText name = view.findViewById(R.id.edtupname);
        EditText price = view.findViewById(R.id.edtupprice);
        EditText size = view.findViewById(R.id.edtupsize);
        Button oke = view.findViewById(R.id.btnupoke);
        Button cancel = view.findViewById(R.id.btnupcancel);

        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));
        size.setText(product.getSize());

        oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String newname = name.getText().toString();
                    String newprice = price.getText().toString();
                    String newsize = size.getText().toString();
                if (newname.isEmpty() || newprice.isEmpty() || newsize.isEmpty()){
                    Toast.makeText(context, "Không được để trống mời nhập lại !", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        list.get(index).setName(newname);
                        list.get(index).setPrice(Integer.parseInt(newprice));
                        list.get(index).setSize(newsize);
                        managerDAO.updateProduct(list.get(index));
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
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
