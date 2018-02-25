package com.kanu_lp.gajjarroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity{


    @BindView(R.id.btn_byage)
    Button btnByage;
    @BindView(R.id.btn_byid)
    Button btnNormal;
    @BindView(R.id.edit_like_firstname)
    EditText editLikeFirstname;
    @BindView(R.id.edit_like_lastname)
    EditText editLikeLastname;
    @BindView(R.id.edit_like_age)
    EditText editLikeAge;
    @BindView(R.id.text_count)
    TextView textCount;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    List<User> list;
    UserRecyclerAdapter userRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this); // :)


        // let's learn cool things everytime
        // if you find this useful and have not seen this,
        // don't let yourself stop from giving like, subscribe !
        //thanks for reading above messages :)


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AddUserDataActivity.class);
                startActivity(i);
            }
        });
    }


    @OnClick(R.id.btn_byage)
    public void orderByAge(){
        try {
            list = AppDatabase.getAppDatabase(MainActivity.this).userDao().orderByAge();
            generateList(list);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.btn_byid)
    public void orderById(){
        try {
            list = AppDatabase.getAppDatabase(MainActivity.this).userDao().orderById();
            generateList(list);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @OnTextChanged(value = R.id.edit_like_firstname,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterFirstNameInput(Editable editable) {
        if (editable.length()>0) {
            list = AppDatabase.getAppDatabase(MainActivity.this).userDao().findLikeFirstName(editable.toString());
            generateList(list);
        }
    }
    @OnTextChanged(value = R.id.edit_like_lastname,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterLastNameInput(Editable editable) {
        if (editable.length()>0) {
            list = AppDatabase.getAppDatabase(MainActivity.this).userDao().findLikeLastName(editable.toString());
            generateList(list);
        }
    }
    @OnTextChanged(value = R.id.edit_like_age,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterAgeInput(Editable editable) {
        if (editable.length()>0) {
            list = AppDatabase.getAppDatabase(MainActivity.this).userDao().findInAge(Integer.parseInt(editable.toString()));
            generateList(list);
        }
    }




    /*@OnTextChanged(value = { R.id.edit_like_firstname, R.id.edit_like_lastname,R.id.edit_like_age },
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inputName(EditText editText, Editable editable) {
        // Greet user...
        switch (editText.getId()){
            case R.id.edit_like_firstname:
                List<User> list = AppDatabase.getAppDatabase(MainActivity.this).userDao().findLikeFirstName(editable.toString());
                generateList(list);
                break;

            case R.id.edit_like_lastname:
                List<User> list1 = AppDatabase.getAppDatabase(MainActivity.this).userDao().findLikeLastName(editable.toString());
                generateList(list1);
                break;

            case  R.id.edit_like_age:
                List<User> list2 = AppDatabase.getAppDatabase(MainActivity.this).userDao().findInAge(Integer.parseInt(editable.toString()));
                generateList(list2);
                break;
        }
    }*/

    public void myOnClick(final int position){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle("Operation")
                .setMessage("Edit or Delete Entry")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        AppDatabase.getAppDatabase(MainActivity.this).userDao().delete(list.get(position));
                        Toast.makeText(MainActivity.this, "Deleted !", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        //rather than querying in another activity simply passing bundle
                        Intent i = new Intent(MainActivity.this,AddUserDataActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("id",list.get(position).getId());
                        b.putString("first",list.get(position).getFirstName());
                        b.putString("last",list.get(position).getLastName());
                        b.putInt("age",list.get(position).getAge());
                        i.putExtras(b);
                        startActivity(i);
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        //Toast.makeText(getApplicationContext(),list.get(position).getFirstName()+" "+list.get(position).getId(),Toast.LENGTH_SHORT).show();

    }

    private void generateList(List<User> list) {
        if(list.size()>0) {
            recyclerview.setVisibility(View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
            recyclerview.setLayoutManager(manager);
             userRecyclerAdapter = new UserRecyclerAdapter(getApplicationContext(), list);
            recyclerview.setAdapter(userRecyclerAdapter);
            userRecyclerAdapter.setOnClickListener(new UserRecyclerAdapter.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    myOnClick(position);
                }
            });

        }else {
            Snackbar.make(findViewById(R.id.cord), "No Data", Snackbar.LENGTH_SHORT).show();
            recyclerview.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = AppDatabase.getAppDatabase(MainActivity.this).userDao().getAll();
        generateList(list);

        int count = AppDatabase.getAppDatabase(MainActivity.this).userDao().countUsers();
        textCount.setText("Total Registered User : "+count);
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<User> list = AppDatabase.getAppDatabase(MainActivity.this).userDao().getAll();
        generateList(list);
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
