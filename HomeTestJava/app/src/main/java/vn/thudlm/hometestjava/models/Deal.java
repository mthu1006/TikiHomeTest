package vn.thudlm.hometestjava.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Deal {

    private String name, img;
    private int price;

    private static String NAME = "name";
    private static String IMG = "img";
    private static String PRICE = "price";

    public Deal() {
    }

    public Deal(String name, String img, int price) {
        this.name = name;
        this.img = img;
        this.price = price;
    }

    public static Deal parseJSON(JSONObject obj) throws JSONException {
        Deal deal = new Deal();
        deal.name = obj.getString(NAME);
        deal.img = obj.getString(IMG);
        deal.price = obj.getInt(PRICE);

        return deal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
