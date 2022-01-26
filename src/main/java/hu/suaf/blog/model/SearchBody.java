package hu.suaf.blog.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SearchBody {

    private String title;

    private String text;

    private String author;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date beginDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
