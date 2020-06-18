package com.MillionaireGPS.ListVisitor;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MillionaireGPS.Customer.CustomerFragment;
import com.MillionaireGPS.Location;
import com.MillionaireGPS.Setting.SendData;

import java.util.ArrayList;
import java.util.List;

public class AdapterVisitor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder{
        RelItemVisitor View;
        public MyViewHolder(@NonNull RelItemVisitor itemView) {
            super(itemView);
            View = itemView;
        }
    }
    List<Location> visitorsList;
    ListVisitorFragment parent;
    List<Boolean> isSelects = new ArrayList<>();

    public AdapterVisitor(List<Location> visitorsList , ListVisitorFragment parent) {
        this.visitorsList = visitorsList;
        this.parent = parent;
        for (int i = 0; i < visitorsList.size(); i++) {
            isSelects.add(false);
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(new RelItemVisitor(viewGroup.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        RelItemVisitor itemView = (RelItemVisitor) viewHolder.itemView;
        if (SendData.newInstance().isListVisitor()){
            itemView.onStart(visitorsList.get(i) , parent , this , isSelects.get(i) , i);
        } else {
          //  itemView.onStart(visitorsList.get(i) , Customerparent , this , isSelects.get(i) , i);
        }
    }

    @Override
    public int getItemCount() {
        return visitorsList.size();
    }

    void setSelect(int position, boolean isSelect)
    {
        for (int i =0;i < visitorsList.size();i++){
            if (isSelects.get(i)){
                isSelects.set(i , false);
                notifyItemChanged(i);
            }
        }
        isSelects.set(position,isSelect);
        notifyItemChanged(position);
    }
}
