package com.example.es.temp;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by shilei on 2016/10/4.
 */
public class Document {
    private DocumentData data = new DocumentData();

	public long getId() {
        return data.id;
    }

    public void setId(long id) {
        this.data.id = id;
    }

    public String getTitle() {
        return data.title;
    }

    public void setTitle(String title) {
        this.data.title = title;
    }

    public String[] getTags() {
        return data.tags;
    }

    public void setTags(String[] tags) {
        this.data.tags = tags;
    }

    public Date getPublishTime() {
        return data.publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.data.publishTime = publishTime;
    }

    public String getAuthor() {
        return data.author;
    }

    public void setAuthor(String author) {
        this.data.author = author;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + data.id +
                ", title='" + data.title + '\'' +
                ", author='" + data.author + '\'' +
                ", tags=" + Arrays.toString(data.tags) +
                ", publishTime=" + data.publishTime +
                '}';
    }
}
