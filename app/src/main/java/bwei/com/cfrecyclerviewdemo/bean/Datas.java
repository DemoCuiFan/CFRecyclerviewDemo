package bwei.com.cfrecyclerviewdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Datas implements Parcelable{

    public String goods_name;
    public String goods_img;

    public Datas() {
    }

    protected Datas(Parcel in) {
        goods_name = in.readString();
        goods_img = in.readString();
    }

    public static final Creator<Datas> CREATOR = new Creator<Datas>() {
        @Override
        public Datas createFromParcel(Parcel in) {
            return new Datas(in);
        }

        @Override
        public Datas[] newArray(int size) {
            return new Datas[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goods_name);
        dest.writeString(goods_img);
    }
}
