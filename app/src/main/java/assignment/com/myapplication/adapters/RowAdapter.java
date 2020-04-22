package assignment.com.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import assignment.com.myapplication.R;
import assignment.com.myapplication.model.Rows;
import dmax.dialog.SpotsDialog;

/**
 * Created by SV0147 on 10-08-2019.
 */

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.RowViewHolder>  {

    //this context we will use to inflate the layout
    private Context mCtx;
    int index;
    //we are storing all the products in a list
    private List<Rows> rowData;
    boolean bCheck = false;
    SpotsDialog spotsDialog;

    public RowAdapter(Context mCtx, List<Rows> templateListData1) {
        this.mCtx = mCtx;
        this.rowData = templateListData1;
    }


    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.row_item, null);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RowViewHolder holder, final int position) {
        final Rows rowData = this.rowData.get(position);


        //binding the data with the viewholder views

        if (rowData.getTitle() != null && !rowData.getTitle().isEmpty() && !rowData.getTitle().equals("null"))
        {
            holder.tvtitle.setText(rowData.getTitle());
        }

        if (rowData.getDescription() != null && !rowData.getDescription().isEmpty() && !rowData.getDescription().equals("null"))
        {
            holder.tvdescription.setText(rowData.getDescription());
        }

        Picasso.with(mCtx).load(rowData.getImageHref()).error(R.mipmap.no_image).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("TAG", "onSuccess");
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    public int getItemCount() {
        return rowData.size();
    }


    public class RowViewHolder extends RecyclerView.ViewHolder {
        TextView tvtitle, tvdescription;
        ImageView imageView;
        View itemView;
        public RowViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvtitle = (TextView) itemView.findViewById(R.id.tvtitle);
            tvdescription = (TextView) itemView.findViewById(R.id.tvdescription);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}
