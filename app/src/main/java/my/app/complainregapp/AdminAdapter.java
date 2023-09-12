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

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> implements Filterable {

        List<Admin> data;
       private Context context;
       List<Admin> dataAll;
private LayoutInflater inflater;

        AdminAdapter(Context context, List<Admin> data) {
        this.context = context;
        this.data = data;
            this.dataAll=new ArrayList<>(data);
        }



@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = inflater.from(parent.getContext()).inflate(R.layout.admin_item, parent, false);

    return new ViewHolder(v, context);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.complainId.setText(""+data.get(position).get_id());
        holder.status.setText(""+data.get(position).getStatus());
        holder.time.setText("" + data.get(position).getTime());
        holder.time1.setText("" + data.get(position).getTime1());
        holder.title.setText(""+data.get(position).getTitle());
        holder.desc.setText(""+data.get(position).getComplaint());
        holder.to.setText(""+data.get(position).getTo());
        holder.from.setText(""+data.get(position).getFrom());
        holder.loc.setText(""+data.get(position).getLocation());
        holder.type.setText(""+data.get(position).getType());

    }



@Override
public int getItemCount() {
        return data.size();
        }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Admin> filteredList=new ArrayList<>();
            if(constraint.toString().isEmpty())
            {
                filteredList.addAll(dataAll);
            }
            else {
                for(Admin data:dataAll)
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
            data.addAll((Collection<? extends Admin>)results.values);
            notifyDataSetChanged();

        }
    };

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView  complainId, status;
    private TextView time, time1,desc,to,from,loc,title,type;

    ViewHolder(@NonNull View itemView, final Context ctx) {
        super(itemView);
        context=ctx;


        complainId=itemView.findViewById(R.id.textView1Admin);
        status=itemView.findViewById(R.id.textView2Admin);
        desc=itemView.findViewById(R.id.descriptionAdmin);
        type=itemView.findViewById(R.id.textView1TypeAdmin);
        to=itemView.findViewById(R.id.ToAdmin);
        from=itemView.findViewById(R.id.fromAdmin);
        loc=itemView.findViewById(R.id.LocationAdmin);
        title=itemView.findViewById(R.id.titleAdmin);
        time = (TextView) itemView.findViewById(R.id.getTimeAdmin);
        time1 = itemView.findViewById(R.id.textView4Admin);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Admin adminI = data.get(getAdapterPosition());
                    Intent intent = new Intent(context, WatchActivity.class);
                    intent.putExtra("complaintAdmin", adminI);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    context.startActivity(intent);
                }
            });


    }

    @Override
    public void onClick(View v) {

    }
}
}
