package vn.thudlm.hometestjava.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import vn.thudlm.hometestjava.R;
import vn.thudlm.hometestjava.models.Deal;
import vn.thudlm.hometestjava.utils.AppUtils;
import vn.thudlm.hometestjava.utils.Validation;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Deal> list;

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name, price;

        MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_deal);
            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_price);
        }

    }

    public DealAdapter(Context context, ArrayList<Deal> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_deal, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Deal deal = list.get(position);
        holder.name.setText(deal.getName());
        holder.price.setText(AppUtils.addDotToNumber(deal.getPrice()));
        if(!Validation.checkNullOrEmpty(deal.getImg()))
            Glide.with(context).load(deal.getImg()).apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground))
                    .into(holder.img);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}