package com.example.musicappcsia;
import java.util.ArrayList;

public class UserType {
}



abstract class User
{

    private String name;
    private String email;

    private int assign_count;

    public abstract Integer assignmentScore();
    public Integer get_assign_count()
    {
        return assign_count;
    }

    public void set_assign_count(int x)
    {
        this.assign_count=x;
    }


    public String get_name()
    {
        return this.name;
    }

    public void set_name(String x)
    {
        this.name=x;
    }

    public String get_email()
    {
        return email;
    }

    public void set_email(String x)
    {
        this.email=x;
    }


}

class Student extends User
{

    public Student(int x, String name)
    {
        set_assign_count(x);
        set_name(name);
    }

    public Integer assignmentScore()
    {
        return get_assign_count();
    }
}

class Teacher extends User
{
    private ArrayList<Student> student_list;

    public Teacher(int x, String name)
    {
        set_assign_count(x);
        set_name(name);
    }

    public Student getStudentById(int student_id)
    {
        return student_list.get(student_id);
    }

    public Integer assignmentScore()
    {
        Integer total = 0;
        for (int i = 0; i < student_list.size(); i++)
        {
            total += student_list.get(i).get_assign_count();
        }
        return total;
    }



}


