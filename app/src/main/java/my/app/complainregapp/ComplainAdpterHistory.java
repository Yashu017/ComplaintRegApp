package my.app.complainregapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ComplainAdpterHistory extends RecyclerView.Adapter<ComplainAdpterHistory.ViewHolder> {

    List<ComplainHistory> data;
    Context context;
    private LayoutInflater inflater;

    ComplainAdpterHistory(Context context, List<ComplainHistory> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ComplainAdpterHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v=inflater.from(context).inflate(R.layout.compl_history_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComplainAdpterHistory.ViewHolder holder, int position) {

        holder.complainId.setText("" + data.get(position).get_id());
        holder.action.setText("" + data.get(position).getAction());
        holder.to.setText("" + data.get(position).getTo());
        holder.from.setText("" + data.get(position).getFrom());
        holder.time.setText("" + data.get(position).getTime());
        holder.time1.setText("" + data.get(position).getTime1());
    }

    @Override
    public int getItemCount() {
     return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        private TextView complainId, action, to, from;
        private TextView time, time1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            complainId = itemView.findViewById(R.id.textView1ComplainHist);
            action = itemView.findViewById(R.id.textView2ComplainHist);
            time = (TextView) itemView.findViewById(R.id.getTime);
            time1 = itemView.findViewById(R.id.textView4ComplainHist);
            to = itemView.findViewById(R.id.to);
            from = itemView.findViewById(R.id.fromComplainHist);


        }
    }
}









