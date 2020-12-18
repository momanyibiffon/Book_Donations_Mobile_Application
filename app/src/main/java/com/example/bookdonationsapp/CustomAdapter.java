package com.example.bookdonationsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// this is a custom adapter for the recycler view
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Activity activity;
    Animation translate_anim;
    private Context context;
    private ArrayList donation_id, fullName, studentID, bookTitle, bookAuthor, numOfBooks;

    CustomAdapter(Activity activity, Context context, ArrayList donation_id, ArrayList fullName, ArrayList studentID,
                  ArrayList bookTitle, ArrayList bookAuthor, ArrayList numOfBooks) {
        this.activity = activity;
        this.context = context;
        this.donation_id = donation_id;
        this.fullName = fullName;
        this.studentID = studentID;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.numOfBooks = numOfBooks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our row layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.donation_id_txt.setText(String.valueOf(donation_id.get(position)));
        holder.fullName_txt.setText(String.valueOf(fullName.get(position)));
        holder.studentID_txt.setText(String.valueOf(studentID.get(position)));
        holder.bookTitle_txt.setText(String.valueOf(bookTitle.get(position)));
        holder.bookAuthor_txt.setText(String.valueOf(bookAuthor.get(position)));
        holder.numOfBooks_txt.setText(String.valueOf(numOfBooks.get(position)));

        // creating onClickListener for mainLayout this transfers the information to the update activity
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(donation_id.get(position)));
                intent.putExtra("fullName", String.valueOf(fullName.get(position)));
                intent.putExtra("studentID", String.valueOf(studentID.get(position)));
                intent.putExtra("bookTitle", String.valueOf(bookTitle.get(position)));
                intent.putExtra("bookAuthor", String.valueOf(bookAuthor.get(position)));
                intent.putExtra("numOfBooks", String.valueOf(numOfBooks.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.delete_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*UpdateActivity upd = new UpdateActivity();
                upd.confirmDialog();*/
            }
        });


    }

    @Override
    public int getItemCount() {
        return donation_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        // textView objects
        TextView donation_id_txt, fullName_txt, studentID_txt, bookTitle_txt, bookAuthor_txt,
                numOfBooks_txt, edit_txt, delete_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            donation_id_txt = itemView.findViewById(R.id.donation_id_txt);
            fullName_txt = itemView.findViewById(R.id.fullName_txt);
            studentID_txt = itemView.findViewById(R.id.studentID_txt);
            bookTitle_txt = itemView.findViewById(R.id.bookTitle_txt);
            bookAuthor_txt = itemView.findViewById(R.id.bookAuthor_txt);
            numOfBooks_txt = itemView.findViewById(R.id.numOfBooks_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            edit_txt = itemView.findViewById(R.id.edit_txt);
            delete_txt = itemView.findViewById(R.id.delete_txt);

            // animating the recyclerView
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }


    }
}
