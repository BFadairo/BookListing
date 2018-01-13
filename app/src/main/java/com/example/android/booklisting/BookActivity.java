package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity
                implements LoaderManager.LoaderCallbacks<List<Book>> {

    /**
     * Link to be used for HTTP request
     */
    private static final String GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";

    private static final String LOG_TAG = BookActivity.class.getName();

    private BookAdapter mAdapter;

    public String requestURL;

    private TextView mEmptyStateTextView;

    private ProgressBar mLoadingBarProgressView;

    private static final int BOOK_LOADER_ID = 1;

    private ListView bookListView;

    private ImageView bookImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        //Check the internet connection of the phone
        ConnectivityManager cm =
                (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Find the ImageView in the book_list_item
        bookImageView = (ImageView) findViewById(R.id.book_image_view);

        // Find a reference to the {@link ListView} in the layout
        bookListView = (ListView) findViewById(R.id.list);

        // Find TextView for when there are no earthquake results
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        // Find the ProgressBarView for when earthquakes are loading
        mLoadingBarProgressView = (ProgressBar) findViewById(R.id.bar_view);

        // Create a new adapter that takes an empty list of books as input

        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            mEmptyStateTextView.setText(R.string.no_connection);
            // Sets the loading bar to gone when loading is finished
            mLoadingBarProgressView.setVisibility(View.GONE);
        }

        // Set the Adapter on the (@link ListView)
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book.
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Book currentbook = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentbook.getBookUrl());

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, requestURL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        //Clear the adapter of previous book data
        mAdapter.clear();
        // Set empty state text to display "No book found."
        mEmptyStateTextView.setText(R.string.no_books);
        // Sets the loading bar to gone when loading is finished
        mLoadingBarProgressView.setVisibility(View.GONE);

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        //Clear the adapter of previous book data
        mAdapter.clear();
        //Set the empty state text to display "Searching for Books..."
        mEmptyStateTextView.setText(R.string.searching_books);

    }

    public void searchForNewBook(View view){
        //Clear the adapter of the current book data
        mAdapter.clear();
        // Find the EditTextView and return the Text currently inputted inside of it
        // Store it in a searchParam value variable for future use
        EditText editTextView = (EditText) findViewById(R.id.book_input_view);
        String searchParam = editTextView.getText().toString().toLowerCase();
        requestURL = "https://www.googleapis.com/books/v1/volumes?q=" + searchParam + "&maxResults=5";
        LoaderManager loaderManager = getLoaderManager();
        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        getLoaderManager().restartLoader(BOOK_LOADER_ID,null,this);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the Adapter on the (@link ListView)
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);
    }


}
