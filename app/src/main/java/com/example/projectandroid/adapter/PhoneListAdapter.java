package com.example.projectandroid.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectandroid.R;
import com.example.projectandroid.model.NakornpathomTourismItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PhoneListAdapter extends ArrayAdapter<NakornpathomTourismItem> {

    private Context mContext;
    private int mResource;
    private List<NakornpathomTourismItem> mNakornpathomTourismItemList;

    public PhoneListAdapter(@NonNull Context context,  /// เอาข้อมูลมาแสดงเป็น list
                            int resource,
                            @NonNull List<NakornpathomTourismItem> phoneItemList) {
        super(context, resource, phoneItemList);
        this.mContext = context;
        this.mResource = resource;
        this.mNakornpathomTourismItemList = phoneItemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResource, parent, false);

        TextView titleTextView = view.findViewById(R.id.title_text_view); // ดึงชื่อจาก database มาใช้
        TextView numberTextView = view.findViewById(R.id.number_text_view); // ดึงเบอร์จาก database มาใข้
        ImageView imageView = view.findViewById(R.id.image_view); // ดึงรูปจาก database มาใข้

        NakornpathomTourismItem item = mNakornpathomTourismItemList.get(position);
        String title = item.title;
        String location = item.location;
        String filename = item.image;
        if(title.length() > 20){
            title = title.substring(0,20) + "...";
        }
        titleTextView.setText(title);
        numberTextView.setText(location);

        AssetManager am = mContext.getAssets();
        try {
            InputStream is = am.open(filename);
            Drawable drawable = Drawable.createFromStream(is, "");
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            File privateDir = mContext.getFilesDir();
            File logoFile = new File(privateDir, filename);

            Bitmap bitmap = BitmapFactory.decodeFile(logoFile.getAbsolutePath(), null);
            imageView.setImageBitmap(bitmap);

            e.printStackTrace();
        }

        return view;
    }
}