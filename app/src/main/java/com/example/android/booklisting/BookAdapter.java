package com.example.android.booklisting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Brandon on 1/13/2018.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter (Activity context, ArrayList<Book> word) {
        super(context,0, word);
    }

    public static String LOG_TAG = BookAdapter.class.getName();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        // Get the (@link Book) object located at this position in the list
        Book currentBook = getItem(position);

        //Find the TextView in the book_list_item layout with the ID book_author_text_view
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.book_author_view);

        //Get the Author from the current currentBook object and
        // Set this text on the name TextView
        authorTextView.setText(currentBook.getAuthor());

        //Find the TextView in the book_list_item layout with the ID book_title_text_view
        TextView bookTextView = (TextView) listItemView.findViewById(R.id.book_title_view);

        //Get the Title from the current currentBook object and
        // Set this text on the name TextView
        bookTextView.setText(currentBook.getTitle());

        //Find the textview in the book_list_item layout with the ID book_description
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.book_description);

        //Get the description from the current currentBook object and
        // Set this text on the TextView
        descriptionTextView.setText(currentBook.getDescription());

        //Find the ImageView in the book_list_item layout with the ID book_image
        ImageView bookImage = (ImageView) listItemView.findViewById(R.id.book_image_view);
        String imageUrl = currentBook.getImageUrl();
        Picasso.with(getContext()).load(imageUrl).into(bookImage);

        //Get the description from the currentBook object and
        // Set the image on the ImageView

        //Return the new list view (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
