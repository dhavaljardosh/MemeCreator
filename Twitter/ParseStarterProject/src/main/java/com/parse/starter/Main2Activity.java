package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    ArrayList<String> users = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setTitle("User List");

        Log.i("Hello", String.valueOf(ParseUser.getCurrentUser().get("isFollowing")));
        if (ParseUser.getCurrentUser().get("isFollowing") == null) {

            List<String> emptyList = new ArrayList<>();
            ParseUser.getCurrentUser().put("isFollowing", emptyList);

        }

        final ListView listView = (ListView) findViewById(R.id.listView);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);


        arrayAdapter  =new ArrayAdapter<>(this,android.R.layout.simple_list_item_checked, users);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view;

                if(checkedTextView.isChecked()){
                    Log.i("Info","Row is Checked");
                    Log.i("ADD: ", String.valueOf(ParseUser.getCurrentUser().get("isFollowing")));

                    ParseUser.getCurrentUser().add("isFollowing", users.get(position));
                    ParseUser.getCurrentUser().saveInBackground();

                } else{
                    Log.i("Info","Row is not Checked");

//
//                    ParseUser.getCurrentUser().remove("isFollowing",users.get(position));
                    ParseUser.getCurrentUser().getList("isFollowing").remove(users.get(position));
                    Log.i("Remove:  ", String.valueOf(ParseUser.getCurrentUser().getList("isFollowing")));

                    List newlist = ParseUser.getCurrentUser().getList("isFollowing");
                    ParseUser.getCurrentUser().remove("isFollowing");
                    ParseUser.getCurrentUser().put("isFollowing", newlist);
                    ParseUser.getCurrentUser().saveInBackground();
                }
            }
        });

        users.clear();

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        for(ParseUser user : objects){
                            users.add(user.getUsername());
                        }

                        arrayAdapter.notifyDataSetChanged();


                        for(String username : users){

                            listView.setItemChecked(users.indexOf(username),true);

                        }
                    }
                }
            }
        });

    }
}
