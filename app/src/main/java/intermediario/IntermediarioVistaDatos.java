package intermediario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import POJO.Quotation;
import upv.dadm.quotesapp.R;

public class IntermediarioVistaDatos extends RecyclerView.Adapter<IntermediarioVistaDatos.ViewHolder>{

    private List<Quotation> listaQuotes;

    private OnItemClickListener interOnItemClickListener;

    public IntermediarioVistaDatos(List<Quotation> theListaQuotes, OnItemClickListener theInterOnItemClickListener){
        listaQuotes = theListaQuotes;
        interOnItemClickListener = theInterOnItemClickListener;
    }

    @NonNull
    @Override
    public IntermediarioVistaDatos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quotation_item, parent, false);
        IntermediarioVistaDatos.ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull IntermediarioVistaDatos.ViewHolder holder, int position) {
    holder.tvAuthor.setText(listaQuotes.get(position).getQuoteAuthor());
    holder.tvQuotes.setText(listaQuotes.get(position).getQuoteText());
    }

    @Override
    public int getItemCount() {
        return listaQuotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvQuotes;
        public TextView tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvQuotes = itemView.findViewById(R.id.tvQuote);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interOnItemClickListener.onItemClick(listaQuotes.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Quotation quotation);
    }
}
