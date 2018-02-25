package com.kanu_lp.gajjarroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddUserDataActivity extends AppCompatActivity {

    @BindView(R.id.edit_user_first_name)
    EditText editUserFirstName;
    @BindView(R.id.edit_user_last_name)
    EditText editUserLastName;
    @BindView(R.id.edit_user_age)
    EditText editUserAge;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    boolean update = false;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_data);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("first").equals("")||bundle.getString("first").isEmpty()){
            update=false;
        }else {
            update=true;
            String fname = bundle.getString("first");
            String lname = bundle.getString("last");
            int age = bundle.getInt("age");
            id = bundle.getInt("id");
            editUserFirstName.setText(fname);
            editUserLastName.setText(lname);
            editUserAge.setText(String.valueOf(age));

        }

    }

    @OnClick(R.id.btn_submit)
    public void addUserData() {
        String userFirstName, userLastName;
        int age;
        userFirstName = editUserFirstName.getText().toString();
        userLastName = editUserLastName.getText().toString();
        age = Integer.parseInt(editUserAge.getText().toString());

        if (userFirstName.isEmpty()) {
            editUserFirstName.setError("Required");
            return;
        }
        if (userLastName.isEmpty()) {
            editUserLastName.setError("Required");
            return;
        }
        if (editUserAge.getText().toString().isEmpty()) {
            editUserAge.setError("Required");
            return;
        }
        if (update) {
            try {
                AppDatabase.getAppDatabase(AddUserDataActivity.this).userDao().updateOne(new User(id,userFirstName, userLastName, age));
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            try {
                AppDatabase.getAppDatabase(AddUserDataActivity.this).userDao().insertAll(new User(userFirstName, userLastName, age));
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
