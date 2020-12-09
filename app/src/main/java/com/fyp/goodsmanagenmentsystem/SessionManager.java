package com.fyp.goodsmanagenmentsystem;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    // Shared Preferences
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    Editor editor;
    SharedPreferences pref;
    // Shared preferences file name
    private static final String PREF_NAME = "AapVideo";
    private static final String KEY_PRODUCT_DATA = "productdata";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_ALL_PRODUCTS = "allproductsdata";
    private static final String KEY_SELECTED_ITEM = "selecteditem";
    private static final String VERIFYING_CONTACT = "vcontact";
    private static final String KEY_MEMBER_SINCE = "membersince";
    private static final String SUB_CATEGORY = "subcategory";
    private static final String Loginwith = "methode";
    private static final String profileimage = "profileimage";
    private static final String ACCESS_TOKEN = "accesstoken";
    public void setKeyUserId(String userid) {
        editor.putString(KEY_USER_ID, userid);
        // commit changes
        editor.commit();
    }
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_USER_CELL = "usercell";

    private static final String KEY_DEAL_TITLE = "title";

    private static final String KEY_DEAL_DESC = "desc";
    private static final String KEY_USER_ADDRESS = "useraddress";
    private static final String KEY_USER_PASSWORD = "userpassword";
    private static final String KEY_SELECTED_CATEGORY = "selectedcat";
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setProfileimage(String image) {
        editor.putString(profileimage, image);
        // commit changes
        editor.commit();
    }
    public String getProfileimage(){
        return pref.getString(profileimage, "******");
    }
    public void setKeyDealTitle(String title) {
        editor.putString(KEY_DEAL_TITLE, title);
        // commit changes
        editor.commit();
    }
    public String getKeyDealTitle(){
        return pref.getString(KEY_DEAL_TITLE, "******");
    }
    public void setKeyAccessToken(String token) {
        editor.putString(ACCESS_TOKEN, token);
        // commit changes
        editor.commit();
    }
    public String getAccessToken(){
        return pref.getString(ACCESS_TOKEN, "******");
    }
    public void setKeyLogiwith(String id) {
        editor.putString(Loginwith, id);
        // commit changes
        editor.commit();
    }
    public String getKeyLoginwith(){
        return pref.getString(Loginwith, "******");
    }

    public void setLoginwith(String title) {
        editor.putString(KEY_DEAL_TITLE, title);
        // commit changes
        editor.commit();
    }
    public String getLoginwith(){
        return pref.getString(KEY_DEAL_TITLE, "******");
    }    public void setKeyDealDesc(String desc) {
        editor.putString(KEY_DEAL_DESC, desc);
        // commit changes
        editor.commit();
    }
    public String getKeyDealDesc(){
        return pref.getString(KEY_DEAL_DESC, "******");
    }
    public void setKeyUserPassword(String cell) {
        editor.putString(KEY_USER_PASSWORD, cell);
        // commit changes
        editor.commit();
    }
    public String getKeyUserPassword(){
        return pref.getString(KEY_USER_PASSWORD, "******");
    }
    public void setKeyUserAddress(String cell) {
        editor.putString(KEY_USER_ADDRESS, cell);
        // commit changes
        editor.commit();
    }
    public String getKeyMemberSince(){
        return pref.getString(KEY_MEMBER_SINCE, "******");
    }
    public void setKeyMemberSince(String memberSince) {
        editor.putString(KEY_MEMBER_SINCE, memberSince);
        // commit changes
        editor.commit();
    }
    public String getKeyUserAddress(){
        return pref.getString(KEY_USER_ADDRESS, "Rawalpindi");
    }

    public void setKeyUserCell(String cell) {
        editor.putString(KEY_USER_CELL, cell);
        // commit changes
        editor.commit();
    }

    public String getKeyUserCell(){
        return pref.getString(KEY_USER_CELL, "+923001234567");
    }

    public void setKeyUserName(String name) {
        editor.putString(KEY_USER_NAME, name);
        // commit changes
        editor.commit();
        Log.d(TAG, "User Dest Latitude session modified!");
    }

    public String getKeyUserName(){
        return pref.getString(KEY_USER_NAME, "Ali");
    }


    public void setSubCategory(String sub_cat_id)
    {
        editor.putString(SUB_CATEGORY, sub_cat_id);
        // commit changes
        editor.commit();
        Log.d(TAG, "Contact "+sub_cat_id);
    }
    public String getSubCategory()
    {
        return pref.getString(SUB_CATEGORY,"6");
    }
    public String getKeyUserId() {
        return pref.getString(KEY_USER_ID,"");
    }
    public void setVerifyingContact(String contact)
    {
                editor.putString(VERIFYING_CONTACT, contact);
        // commit changes
        editor.commit();
        Log.d(TAG, "Contact "+contact);
    }

    public String getVerifyingContact()
    {
        return pref.getString(VERIFYING_CONTACT,"EMAWeZA749m0EOHG6rbvPUTkxJRelMcZCWbVq7FTuZCM8pphSbfEehy7a4XGxyRdtQQA3ZAjyDpatWTl203mrybHbXYu2D8GwYbMpRcXngZAQZDZD");
    }

    public void setKeySelectedItem(int position) {


        editor.putInt(KEY_SELECTED_ITEM, position);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public int getKeySelectedItem(){
        return pref.getInt(KEY_SELECTED_ITEM, R.id.navigation_home);
    }
//
//
//
//
//
//
//
    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
//
    public void setKeySelectedCategory(int position) {
        editor.putInt(KEY_SELECTED_CATEGORY, position);
        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }
    public int getkeySelectedCategory(){
        return pref.getInt(KEY_SELECTED_CATEGORY,0);
    }


}