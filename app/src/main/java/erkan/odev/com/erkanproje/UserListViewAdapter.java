package erkan.odev.com.erkanproje;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserListViewAdapter extends RecyclerView.Adapter<UserListViewAdapter.ViewHolder>{

    private static final String TAG = "UserListViewAdapter";
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<Integer> mIds = new ArrayList<>();
    private Context mContext;

    public UserListViewAdapter(Context mContext, ArrayList<String> mImageNames, ArrayList<String> mImages, ArrayList<Integer> mIds) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
        this.mIds = mIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.imageName.setText(mImageNames.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, UserDetailActivity.class);
                Bundle b = new Bundle();
                int userid = mIds.get(position);
                b.putInt("userid", userid); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView){

            super(itemView);

            Log.d(TAG, "resim yüklendi");
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
