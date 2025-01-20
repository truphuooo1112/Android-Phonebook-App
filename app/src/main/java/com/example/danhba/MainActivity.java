package com.example.danhba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<LienheActivity> arrayContact;
    private LienheAdapter adapter;
    private EditText edt_tenlienhe;
    private EditText edt_sodienthoai;
    private RadioButton rbtn_nam;
    private RadioButton rbtn_nu;
    private Button btn_themlienhe, btn_xoa,btn_sua;
    private ListView lv_lienhe;
    private int selectedContactPosition =-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidget();
        arrayContact = new ArrayList<>();
        adapter = new LienheAdapter(this, R.layout.item_activity, arrayContact);
        lv_lienhe.setAdapter(adapter);
 lv_lienhe.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
        lv_lienhe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showEditDialog();
            }
        });
    }



    public void setWidget() {
        edt_tenlienhe = (EditText) findViewById(R.id.edt_tenlienhe);
        edt_sodienthoai = (EditText) findViewById(R.id.edt_sodienthoai);
        rbtn_nam = (RadioButton) findViewById(R.id.rbtn_nam);
        rbtn_nu = (RadioButton) findViewById(R.id.rbtn_nu);
        btn_themlienhe = (Button) findViewById(R.id.btn_themlienhe);
        lv_lienhe = (ListView) findViewById(R.id.lv_lienhe);
         btn_sua =(Button) findViewById(R.id.btn_sua);
        btn_xoa = (Button) findViewById(R.id.btn_xoa);


    }
    private void showEditDialog() {

        List<String> tenLienHe = new ArrayList<>();
        for (LienheActivity lienHe : arrayContact) {
            tenLienHe.add(lienHe.getmName()); // Giả sử 'getmName()' trả về tên liên hệ
        }


        final CharSequence[] items = tenLienHe.toArray(new CharSequence[tenLienHe.size()]);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn liên hệ để sửa");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                selectedContactPosition = item;
                showEditContactDialog(arrayContact.get(item));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showEditContactDialog(final LienheActivity selectedContact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa thông tin liên hệ");


        View view = getLayoutInflater().inflate(R.layout.edit_contact_dialog, null);
        final EditText edtNewName = view.findViewById(R.id.edt_new_name);
        final EditText edtNewPhone = view.findViewById(R.id.edt_new_phone);

        builder.setView(view);


        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lấy thông tin mới từ EditText
                String newName = edtNewName.getText().toString().trim();
                String newPhone = edtNewPhone.getText().toString().trim();

                // Kiểm tra xem liệu thông tin mới có hợp lệ hay không
                if (!TextUtils.isEmpty(newName) && !TextUtils.isEmpty(newPhone)) {
                    // Cập nhật thông tin liên hệ và thông báo
                    selectedContact.setmName(newName);
                    selectedContact.setmNumber(newPhone);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Đã cập nhật thông tin liên hệ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Thiết lập nút Hủy bỏ trong hộp thoại
        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Hiển thị hộp thoại chỉnh sửa
        builder.show();
    }

    public void themLienhe(View view) {
        if (view.getId() == R.id.btn_themlienhe) {
            String ten = edt_tenlienhe.getText().toString().trim();
            String sdt = edt_sodienthoai.getText().toString().trim();
            boolean laNam = true;
            if (rbtn_nam.isChecked()) {
                laNam = true;
            } else {
                laNam = false;
            }
            if (TextUtils.isEmpty(ten) || TextUtils.isEmpty(sdt)) {
                Toast.makeText(this, "Vui long nhap Ten hoac So dien thoai", Toast.LENGTH_SHORT).show();
            } else {
                LienheActivity Lienhe = new LienheActivity(laNam, ten, sdt);
                arrayContact.add(Lienhe);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void setButtonListeners() {
        btn_themlienhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themLienhe(view);
            }
        });
    }
public void removeContact(int position){
        arrayContact.remove(position);
        adapter.notifyDataSetChanged();
}
    private void showDeleteDialog() {
        // Tạo một danh sách tên liên hệ cho hộp thoại
        List<String> tenLienHe = new ArrayList<>();
        for (LienheActivity lienHe : arrayContact) {
            tenLienHe.add(lienHe.getmName()); // Giả sử 'getTen()' trả về tên liên hệ
        }

        // Chuyển đổi danh sách thành mảng
        final CharSequence[] items = tenLienHe.toArray(new CharSequence[tenLienHe.size()]);

        // Tạo một hộp thoại để hiển thị tên liên hệ
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn liên hệ để xóa");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Lấy vị trí của item đã chọn và xóa liên hệ
                removeContact(item);
                Toast.makeText(MainActivity.this, "Đã xóa liên hệ", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
