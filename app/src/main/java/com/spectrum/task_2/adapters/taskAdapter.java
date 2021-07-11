package com.spectrum.task_2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spectrum.task_2.Modelclasss.Tasks;
import com.spectrum.task_2.R;

import java.util.ArrayList;
import java.util.List;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.ViewHolder> {
   private List<Tasks> list=new ArrayList<>();;
   public taskAdapter() {

   }
   public class ViewHolder extends RecyclerView.ViewHolder{
      private TextView task,btn;
      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         task=itemView.findViewById(R.id.mytask);
         btn=itemView.findViewById(R.id.remove);
      }
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_design,parent,false);
      ViewHolder holder=new ViewHolder(view);
      return holder;
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.task.setText(list.get(position).task.toString());
      holder.btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            list.remove(list.get(position));
            notifyDataSetChanged();
         }
      });
   }

   @Override
   public int getItemCount() {
      return  list.size();
   }

   public void setList(List<Tasks> list) {
      this.list = list;
      notifyDataSetChanged();
   }
}

