package vn.thudlm.hometestjava.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vn.thudlm.hometestjava.R;
import vn.thudlm.hometestjava.models.KeyWord;
import vn.thudlm.hometestjava.utils.AppUtils;

public class KeyWordAdapter extends RecyclerView.Adapter<KeyWordAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<KeyWord> list;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtKeyword;
        CardView cvKeyword;

        MyViewHolder(View itemView) {
            super(itemView);
            cvKeyword = itemView.findViewById(R.id.card);
            txtKeyword = itemView.findViewById(R.id.tv_keyword);
        }

    }

    public KeyWordAdapter(Context context, ArrayList<KeyWord> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_keyword, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        KeyWord keyWord = list.get(position);
        holder.txtKeyword.setText(formatKeyword(keyWord.getName()));
        holder.cvKeyword.setCardBackgroundColor(getRandomColor());
    }

    private String formatKeyword(String keyword){
        String[] arr = keyword.split(" ");
        if (arr.length == 1){
            return arr[0];
        } else {
            int mid = keyword.length()/2;
            String head = keyword.substring(0, mid);
            String tail = keyword.substring(mid);
            int index = 0;
            if(head.contains(" ")){
                index = head.lastIndexOf(" ");
            }else {
                index = tail.indexOf(" ");
            }

            return head.substring(0, index) + "\n" + keyword.substring(index);
        }
    }

    private int getRandomColor() {
        int[] arrColor = context.getResources().getIntArray(R.array.keywordBackground);
        return arrColor[AppUtils.randomWithRange(0, arrColor.length-1)];
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}