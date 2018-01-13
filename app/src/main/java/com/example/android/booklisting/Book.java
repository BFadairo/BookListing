package com.example.android.booklisting;

import android.net.Uri;

import java.net.URI;
import java.net.URL;

/**
 * Created by Brandon on 1/13/2018.
 */

public class Book {
    /** Information for the Author of the book */
    private String mAuthor;

    /** Information for the Title of the book */
    private String mTitle;

    /** Information regarding the details of the book */
    private String mDescription;

    /** Information regarding the image of the book */
    private String mImageUrl;

    /** Information regarding where to find more info on book */
    private String mBookUrl;

    public Book(String author, String title, String description) {
        mAuthor = author;
        mTitle = title;
        mDescription = description;
    }
    public Book(String author, String title, String description, String imageUrl, String bookUrl) {
        mAuthor = author;
        mTitle = title;
        mDescription = description;
        mImageUrl = imageUrl;
        mBookUrl = bookUrl;
    }

    /**
     * Get the Author of the book
     */
    public String getAuthor(){
        return mAuthor;
    }

    /**
     * Get the Title of the book
     */
    public String getTitle(){
        return mTitle;
    }

    /**
     * Get the Title of the book
     */
    public String getDescription(){
        return mDescription;
    }

    /**
     * Get the image related to the book
     */
    public String getImageUrl(){
        return mImageUrl;
    }

    /**
     * Get the URL related to the book
     */
    public String getBookUrl(){
        return mBookUrl;
    }
}
