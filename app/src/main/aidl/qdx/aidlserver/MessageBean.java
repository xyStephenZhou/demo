package qdx.aidlserver;


import android.os.Parcel;
import android.os.Parcelable;

public class MessageBean implements Parcelable {
    private String content;//需求内容
    private int level;//重要等级

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeInt(this.level);
    }
    public MessageBean() {
    }

    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        this.content = dest.readString();
        this.level = dest.readInt();
    }

    public MessageBean(String content, int level) {
        this.content = content;
        this.level = level;
    }

    protected MessageBean(Parcel in) {
        this.content = in.readString();
        this.level = in.readInt();
    }

    public static final Parcelable.Creator<MessageBean> CREATOR = new Parcelable.Creator<MessageBean>() {
        @Override
        public MessageBean createFromParcel(Parcel source) {
            return new MessageBean(source);
        }

        @Override
        public MessageBean[] newArray(int size) {
            return new MessageBean[size];
        }
    };

    @Override
    public String toString() {
        return "【内容==" + content + ",级别==" + level + "】";
    }
}
