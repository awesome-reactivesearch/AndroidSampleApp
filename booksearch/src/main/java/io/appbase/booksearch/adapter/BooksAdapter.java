package io.appbase.booksearch.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import io.appbase.booksearch.R;
import io.appbase.booksearch.model.Book;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
  private static final String TAG = BooksAdapter.class.getSimpleName();

  private Context context;
  private List<Book> bookListFiltered;
  private BooksAdapterListener listener;

  public BooksAdapter(Context context, List<Book> bookList, BooksAdapterListener listener) {
    this.context = context;
    this.listener = listener;
    this.bookListFiltered = bookList;
  }

  public interface BooksAdapterListener {
    void onBookSelected(Book book);
  }

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.book_row_item, parent, false);
    return new ViewHolder(itemView);
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final Book book = bookListFiltered.get(position);

    Log.i(TAG, book.toString());

    holder.name.setText(book.getTitle());
    holder.author.setText(book.getAuthors());

    Glide.with(context)
        .load(book.getImage())
        .apply(RequestOptions.circleCropTransform())
        .into(holder.thumbnail);
  }

  @Override public int getItemCount() {
    return bookListFiltered.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    TextView name, author;
    ImageView thumbnail;

    ViewHolder(View view) {
      super(view);
      name = view.findViewById(R.id.name);
      author = view.findViewById(R.id.author);
      thumbnail = view.findViewById(R.id.thumbnail);
      view.setOnClickListener(
          view1 -> listener.onBookSelected(bookListFiltered.get(getAdapterPosition())));
    }
  }
}
