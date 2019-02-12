package io.appbase.booksearch;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.jakewharton.rxbinding3.widget.RxSearchView;
import com.jakewharton.rxbinding3.widget.SearchViewQueryTextEvent;

import io.appbase.booksearch.adapter.BooksAdapter;
import io.appbase.booksearch.model.Book;
import io.appbase.booksearch.view.DividerItemDecoration;
import io.appbase.client.AppbaseClient;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Response;

import static io.appbase.booksearch.constants.Constants.APP_NAME;
import static io.appbase.booksearch.constants.Constants.MATCH_ALL;
import static io.appbase.booksearch.constants.Constants.PASSWORD;
import static io.appbase.booksearch.constants.Constants.SEARCH;
import static io.appbase.booksearch.constants.Constants.TYPE;
import static io.appbase.booksearch.constants.Constants.URL;
import static io.appbase.booksearch.constants.Constants.USERNAME;

public class MainActivity extends AppCompatActivity implements BooksAdapter.BooksAdapterListener {
  private static final String TAG = MainActivity.class.getSimpleName();

  private AppbaseClient mClient;
  private BooksAdapter mAdapter;

  private JsonParser jsonParser = new JsonParser();
  private Gson gson = new Gson();
  private List<Book> bookList = new ArrayList<>();
  private CompositeDisposable disposable = new CompositeDisposable();
  private PublishSubject<String> publishSubject = PublishSubject.create();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.toolbar_title);
    setSupportActionBar(toolbar);

    mAdapter = new BooksAdapter(this, bookList, this);
    mClient = new AppbaseClient(URL, APP_NAME, USERNAME, PASSWORD);

    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setSystemUiVisibility(
        recyclerView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    getWindow().setStatusBarColor(Color.WHITE);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.addItemDecoration(new DividerItemDecoration(this,
        androidx.recyclerview.widget.DividerItemDecoration.VERTICAL, 36));
    recyclerView.setAdapter(mAdapter);
  }

  @Override public void onBookSelected(Book book) {
    Toast.makeText(getApplicationContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);

    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
        .getActionView();
    searchView.setSearchableInfo(searchManager
        .getSearchableInfo(getComponentName()));
    searchView.setMaxWidth(Integer.MAX_VALUE);

    DisposableObserver<List<Book>> esObserver = getEsObserver();
    disposable.add(publishSubject.debounce(300, TimeUnit.MILLISECONDS)
        .distinctUntilChanged()
        .switchMapSingle(
            (Function<String, SingleSource<List<Book>>>) s -> Single.just(getBooks(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()))
        .subscribeWith(esObserver));

    DisposableObserver<SearchViewQueryTextEvent> searchViewEventsObserver = getSearchViewObserver();
    disposable.add(RxSearchView.queryTextChangeEvents(searchView)
        .skipInitialValue()
        .debounce(300, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(searchViewEventsObserver));

    disposable.add(esObserver);

    publishSubject.onNext("");
    return true;
  }

  private List<Book> getBooks(String term) {
    List<Book> books = new ArrayList<>();
    String query = term.equals("") ? MATCH_ALL : String.format(SEARCH, term, term);
    try (Response response = mClient.prepareSearch(TYPE, query).execute()) {
      if (response == null) {
        return books;
      }
      JsonObject responseObj = jsonParser.parse(response.body().string()).getAsJsonObject();
      JsonObject hitsObj = responseObj.getAsJsonObject().get("hits").getAsJsonObject();
      JsonArray hits = hitsObj.getAsJsonArray("hits");
      if (hits.size() == 0) {
        return books;
      }
      for (JsonElement element : hits) {
        JsonObject source = element.getAsJsonObject().get("_source").getAsJsonObject();
        books.add(gson.fromJson(source, Book.class));
      }
      Log.i(TAG, books.size() + " " + books.toString());
    } catch (IOException e) {
      Log.e(TAG, "error fetching books: " + e.getMessage());
      e.printStackTrace();
    }
    return books;
  }

  private DisposableObserver<List<Book>> getEsObserver() {
    return new DisposableObserver<List<Book>>() {
      @Override public void onNext(List<Book> books) {
        bookList.clear();
        bookList.addAll(books);
        mAdapter.notifyDataSetChanged();
      }

      @Override public void onError(Throwable e) {
        Log.e(TAG, "onError: " + e.getMessage());
      }

      @Override public void onComplete() {
      }
    };
  }

  private DisposableObserver<SearchViewQueryTextEvent> getSearchViewObserver() {
    return new DisposableObserver<SearchViewQueryTextEvent>() {
      @Override public void onNext(SearchViewQueryTextEvent searchViewQueryTextEvent) {
        Log.d(TAG, "Search query: " + searchViewQueryTextEvent.getQueryText());
        publishSubject.onNext(searchViewQueryTextEvent.getQueryText().toString());
      }

      @Override public void onError(Throwable e) {
        e.printStackTrace();
        Log.e(TAG, "onError: " + e);
      }

      @Override public void onComplete() {
      }
    };
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_search) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onDestroy() {
    disposable.clear();
    super.onDestroy();
  }
}
