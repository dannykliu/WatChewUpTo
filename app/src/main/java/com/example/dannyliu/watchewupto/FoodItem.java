package com.example.dannyliu.watchewupto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dannyliu on 11/15/15.
 */
public class FoodItem implements Parcelable {
    public String name;
    public int serving;
    public boolean vegetarian;
    public String ingredients;
    public int calories;
    public int fat;
    public int saturated;
    public int cholestrol;
    public int sodium;
    public int carbohydrates;
    public int fibre;
    public int sugars;
    public int protein;
    public int vitaminA;
    public int vitaminC;
    public int calcium;
    public int iron;
    public int id;

    public FoodItem() {

    }

    public FoodItem(String name, int serving, String ingredients, int calories, int fat, int saturated, int cholestrol, int sodium, int carbohydrates, int fibre, int sugars, int protein, int vitaminA, int vitaminC, int calcium, int iron, int id) {
        this.name = name;
        this.serving = serving;
        this.ingredients = ingredients;
        this.vegetarian = false;
        this.calories = calories;
        this.fat = fat;
        this.saturated = saturated;
        this.cholestrol = cholestrol;
        this.sodium = sodium;
        this.carbohydrates = carbohydrates;
        this.fibre = fibre;
        this.sugars = sugars;
        this.protein = protein;
        this.vitaminA = vitaminA;
        this.vitaminC = vitaminC;
        this.calcium = calcium;
        this.iron = iron;
        this.id = id;
    }

    protected FoodItem(Parcel in) {
        name = in.readString();
        serving = in.readInt();
        vegetarian = in.readByte() != 0;
        ingredients = in.readString();
        calories = in.readInt();
        fat = in.readInt();
        saturated = in.readInt();
        cholestrol = in.readInt();
        sodium = in.readInt();
        carbohydrates = in.readInt();
        fibre = in.readInt();
        sugars = in.readInt();
        protein = in.readInt();
        vitaminA = in.readInt();
        vitaminC = in.readInt();
        calcium = in.readInt();
        iron = in.readInt();
        id = in.readInt();
    }

    public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(serving);
        dest.writeByte((byte) (vegetarian ? 1 : 0));
        dest.writeString(ingredients);
        dest.writeInt(calories);
        dest.writeInt(fat);
        dest.writeInt(saturated);
        dest.writeInt(cholestrol);
        dest.writeInt(sodium);
        dest.writeInt(carbohydrates);
        dest.writeInt(fibre);
        dest.writeInt(sugars);
        dest.writeInt(protein);
        dest.writeInt(vitaminA);
        dest.writeInt(vitaminC);
        dest.writeInt(calcium);
        dest.writeInt(iron);
        dest.writeInt(id);
    }
}
