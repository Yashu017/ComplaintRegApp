package my.app.complainregapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ComplainAdapter extends RecyclerView.Adapter<ComplainAdapter.ViewHolder> implements Filterable {

    List<Complain> data;
    List<Complain> dataAll;
    Context context;
    private LayoutInflater inflater;

    ComplainAdapter(Context context, List<Complain> data) {
        this.context = context;
        this.data = data;
        this.dataAll=new ArrayList<>(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = inflater.from(context).inflate(R.layout.history_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComplainAdapter.ViewHolder holder, int position) {

        holder.complainId.setText(""+data.get(position).get_id());
        holder.status.setText(""+data.get(position).getStatus());
        holder.title.setText(""+data.get(position).getTitle());
        holder.time.setText("" + data.get(position).getTime());
        holder.type.setText(""+data.get(position).getType());
        holder.time1.setText("" + data.get(position).getTime1());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Complain> filteredList=new ArrayList<>();
            if(constraint.toString().isEmpty())
            {
                filteredList.addAll(dataAll);
            }
            else {
                for(Complain data:dataAll)
                {
                    if (data.get_id().toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        filteredList.add(data);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            data.addAll((Collection<? extends Complain>)results.values);
            notifyDataSetChanged();

        }
    };
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  complainId,title, status,type;
        private TextView time, time1;

        ViewHolder(View itemView) {
            super(itemView);



            complainId=itemView.findViewById(R.id.textView1);
            status=itemView.findViewById(R.id.textView2);
            type=itemView.findViewById(R.id.textView1Type);
            title=itemView.findViewById(R.id.textView1Title);
            time = (TextView) itemView.findViewById(R.id.getTime);
            time1 = itemView.findViewById(R.id.textView4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Complain complain = data.get(getAdapterPosition());
                    Intent intent = new Intent(context, complhistorymain.class);
                    intent.putExtra("complaintAdmin", complain);
                    context.startActivity(intent);
                }
            });

        }
    }
}