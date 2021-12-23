package bean;

public class Article {
    private int id;
    private String title;
    private String author;
    private String time;
    private String content;
    private String tag;
    private String sort;
    private boolean original;
    private int read;
    private int like;
    private int collect;

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isOriginal() {
        return original;
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }

    public Article(String title, String author, String time, String content, String tag, String sort, boolean original) {
        this.title = title;
        this.author = author;
        this.time = time;
        this.content = content;
        this.tag = tag;
        this.sort = sort;
        this.original = original;
    }
    public Article(){

    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", tag='" + tag + '\'' +
                ", sort='" + sort + '\'' +
                ", original=" + original +
                ", read=" + read +
                ", like=" + like +
                ", collect=" + collect +
                '}';
    }
}
