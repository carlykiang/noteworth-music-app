package com.example.musicappcsia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StudentScoreboardActivity extends AppCompatActivity {
    //private ArrayList<User> student_list = new ArrayList<User>();

    private ArrayList<User> inputArray;

    public void MergeSort(ArrayList<User> inputArray){
        this.inputArray = inputArray;
        divide(0, this.inputArray.size()-1);
    }

    public void divide(int startingIndex,int endingIndex) {
        //Divide using recursion until lists only have one element
        if (startingIndex < endingIndex && (endingIndex - startingIndex) >= 1) {
            int middle = (endingIndex + startingIndex) / 2;

            //divide into left and right sub-arrays
            divide(startingIndex, middle);
            divide(middle + 1, endingIndex);

            //merging Sorted array produce above into one sorted array
            merge(startingIndex, middle, endingIndex);
        }
    }


    public void merge(int startingIndex,int middleIndex,int endingIndex){

        ArrayList<User> tempSortedArray = new ArrayList<User>();
        int leftIndex = startingIndex;
        int rightIndex = middleIndex+1;

        while(leftIndex<=middleIndex && rightIndex<=endingIndex){
            if(inputArray.get(leftIndex).get_assign_count()<=inputArray.get(rightIndex).get_assign_count()){
                tempSortedArray.add(inputArray.get(leftIndex));
                leftIndex++;
            }else{
                tempSortedArray.add(inputArray.get(rightIndex));
                rightIndex++;
            }
        }

        while(leftIndex<=middleIndex){
            tempSortedArray.add(inputArray.get(leftIndex));
            leftIndex++;
        }

        while(rightIndex<=endingIndex){
            tempSortedArray.add(inputArray.get(rightIndex));
            rightIndex++;
        }

        int i = 0;
        int j = startingIndex;
        //Transfer sorted array elements into input array
        while(i<tempSortedArray.size()){
            inputArray.set(j, tempSortedArray.get(i));
            i++;
            j++;
        }
    }


    private ArrayList<User> student_list = new ArrayList<User>();

    private void get_all_student_info_scoreboard() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest("http://47.100.220.252:666/get_all_student_info_scoreboard/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.d("mytest", str);
                        String [] user_item_list = str.split("\n");
                        for(int i=0;i<user_item_list.length;i++)
                        {
                            String item = user_item_list[i];
                            String user_name = item.split(":")[0];
                            String user_assign_count = item.split(":")[1];
                            Log.d("mytest", "user_name="+user_name+"user_assign_count="+user_assign_count);
                            Student s = new Student(Integer.parseInt(user_assign_count), user_name);
                            student_list.add(s);

                            //user_name_list.add(user_name);
                            //user_id_str_list.add(user_id);
                            //username_to_id.put(user_name, user_id);

                        }


                        //sort(student_list_assignments);
                        MergeSort(student_list);
                        Log.d("mytest", "ordered assignments" + student_list);







                        ListView mListView = (ListView) findViewById(R.id.scoreBoardList_student);
                        ArrayList<Person> peopleList = new ArrayList<>();

                        for(int i=0;i<student_list.size();i++) {

                            String name = student_list.get(i).get_name();
                            String score = ""+student_list.get(i).assignmentScore();
                            //Create person objects
                            Person person1 = new Person(name ,score);


                            //Add the Person obejcts to an ArrayList

                            peopleList.add(person1);
                        }

                        //Create person list adapter
                        PersonListAdapter adapter = new PersonListAdapter(StudentScoreboardActivity.this, R.layout.adapter_view_layout, peopleList);
                        mListView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //issue with connection to wifi
                        //add a message box that prompts user to check wifi
                        Log.d("StudentScoreboardAct", volleyError.getMessage(), volleyError);
                    }
                }
        );
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_scoreboard);

        Log.d("mytest", "activity_student_scoreboard");
        student_list.clear();
        get_all_student_info_scoreboard();

        ListView mListView = (ListView) findViewById(R.id.scoreBoardList_student);



    }
}