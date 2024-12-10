package com.apaaja.eventapplication;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private List<ClassItem> classList;
    private Context context;
    private int selectedPosition = -1;

    public ClassAdapter(Context context, List<ClassItem> classList) {
        this.context = context;
        this.classList = classList;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassItem classItem = classList.get(position);

        holder.className.setText(classItem.getClassName());
        holder.location.setText(classItem.getLocation());
        holder.time.setText(classItem.getTime());

        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_item));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.default_item));
        }

        holder.itemView.setOnClickListener(view -> {
            selectedPosition = position;
            notifyDataSetChanged();

            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                    == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                ActivityCompat.requestPermissions((android.app.Activity) context,
                        new String[]{android.Manifest.permission.CAMERA}, 100);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView className, location, time;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.class_name);
            location = itemView.findViewById(R.id.location);
            time = itemView.findViewById(R.id.time);
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            ((android.app.Activity) context).startActivityForResult(takePictureIntent, 1);
        } else {
            Toast.makeText(context, "Camera not found!", Toast.LENGTH_SHORT).show();
        }
    }
}
