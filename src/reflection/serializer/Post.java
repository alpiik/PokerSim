package reflection.serializer;

@Stored
public class Post {

    @Stored
    private String title;

    @Stored("content")
    private String text;

    @Stored
    private int replyCount;

    public Post(String title, String text) {
        this(title, text, 0);
    }

    public Post(String title, String text, int replyCount) {
        this.title = title;
        this.text = text;
        this.replyCount = replyCount;
    }

    public Post() {
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getReplyCount() {
        return replyCount;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", replyCount=" + replyCount +
                '}';
    }
}