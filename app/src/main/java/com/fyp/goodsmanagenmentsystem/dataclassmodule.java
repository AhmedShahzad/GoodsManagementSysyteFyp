package com.fyp.goodsmanagenmentsystem;

import java.util.ArrayList;
import java.util.List;

public class dataclassmodule {
    String cat_id,cat_name;
    String cat_desc,deal_startdate,deal_enddate,deal_title,deal_link,deal_Coupon,deal_Image,deal_actualPrice,deal_Discount,discount,createddate;
            int deal_Identity,user_Identity,likes,dislikes,votedeal,likedislike;
    public int getLikedislike() {
        return likedislike;
    }
    public void setLikedislike(int likedislike) {
        this.likedislike = likedislike;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public int getVotedeal() {
        return votedeal;
    }
    public void setVotedeal(int votedeal) {
        this.votedeal = votedeal;
    }
    public int getComments() {
        return comments;
    }
    public void setComments(int comments) {
        this.comments = comments;
    }
    public int getExpire_days() {
        return expire_days;
    }
    public void setExpire_days(int expire_days) {
        this.expire_days = expire_days;
    }
    int price,dummyimage,comments,expire_days;
    String description,date,category_Image;
    public String getDiscount() {
        return discount;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public int getLikes() {
        return likes;
    }
    public dataclassmodule(String deal_title, String deal_Image, String deal_actualPrice, String deal_Discount, String discount, int likes, int dislikes) {
        this.deal_title = deal_title;
        this.deal_Image = deal_Image;
        this.deal_actualPrice = deal_actualPrice;
        this.deal_Discount = deal_Discount;
        this.discount = discount;
        this.likes = likes;
        this.dislikes = dislikes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public int getDislikes() {
        return dislikes;
    }
    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public dataclassmodule(String deal_startdate, String deal_enddate, String deal_title, String description, String deal_link, String deal_Coupon, String deal_Image,
                           String deal_actualPrice, String deal_Discount, int deal_Identity, int user_Identity,
                           String discount,int likes,int dislikes,int comments,int expire_days,int votedeal,String createddate) {
        this.deal_startdate = deal_startdate;
        this.deal_enddate = deal_enddate;
        this.deal_title = deal_title;
        this.votedeal=votedeal;
        this.deal_link = deal_link;
        this.createddate=createddate;
        this.deal_Coupon = deal_Coupon;
        this.likes=likes;
        this.expire_days=expire_days;
        this.dislikes=dislikes;
        this.discount=discount;
        this.comments=comments;
        this.deal_Image = deal_Image;
        this.deal_actualPrice = deal_actualPrice;
        this.deal_Discount = deal_Discount;
        this.deal_Identity = deal_Identity;
        this.user_Identity = user_Identity;
        this.description = description;
    }
    public dataclassmodule(String deal_title, String description, String deal_Image,
                           String deal_actualPrice, String deal_Discount, int deal_Identity,String discount) {
        this.deal_startdate = deal_startdate;
        this.deal_enddate = deal_enddate;
        this.deal_title = deal_title;
        this.votedeal=votedeal;
        this.deal_link = deal_link;
        this.createddate=createddate;
        this.deal_Coupon = deal_Coupon;
        this.likes=likes;
        this.expire_days=expire_days;
        this.dislikes=dislikes;
        this.discount=discount;
        this.comments=comments;
        this.deal_Image = deal_Image;
        this.deal_actualPrice = deal_actualPrice;
        this.deal_Discount = deal_Discount;
        this.deal_Identity = deal_Identity;
        this.user_Identity = user_Identity;
        this.description = description;
    }
    public String getCat_desc() {
        return cat_desc;
    }

    public void setCat_desc(String cat_desc) {
        this.cat_desc = cat_desc;
    }

    public String getDeal_startdate() {
        return deal_startdate;
    }

    public void setDeal_startdate(String deal_startdate) {
        this.deal_startdate = deal_startdate;
    }

    public String getDeal_enddate() {
        return deal_enddate;
    }

    public void setDeal_enddate(String deal_enddate) {
        this.deal_enddate = deal_enddate;
    }

    public String getDeal_title() {
        return deal_title;
    }

    public void setDeal_title(String deal_title) {
        this.deal_title = deal_title;
    }

    public String getDeal_link() {
        return deal_link;
    }

    public void setDeal_link(String deal_link) {
        this.deal_link = deal_link;
    }

    public String getDeal_Coupon() {
        return deal_Coupon;
    }

    public void setDeal_Coupon(String deal_Coupon) {
        this.deal_Coupon = deal_Coupon;
    }

    public String getDeal_Image() {
        return deal_Image;
    }

    public void setDeal_Image(String deal_Image) {
        this.deal_Image = deal_Image;
    }

    public String getDeal_actualPrice() {
        return deal_actualPrice;
    }

    public void setDeal_actualPrice(String deal_actualPrice) {
        this.deal_actualPrice = deal_actualPrice;
    }

    public String getDeal_Discount() {
        return deal_Discount;
    }

    public void setDeal_Discount(String deal_Discount) {
        this.deal_Discount = deal_Discount;
    }

    public int getDeal_Identity() {
        return deal_Identity;
    }

    public void setDeal_Identity(int deal_Identity) {
        this.deal_Identity = deal_Identity;
    }

    public int getUser_Identity() {
        return user_Identity;
    }

    public void setUser_Identity(int user_Identity) {
        this.user_Identity = user_Identity;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public int getPrice() {
        return price;
    }
    public dataclassmodule(String name,int id)
    {
        this.name=name;
    }

    public dataclassmodule(int price, String description, String date, int dummyimage) {
        this.price = price;
        this.description = description;
        this.date = date;
        this.dummyimage = dummyimage;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory_Image() {
        return category_Image;
    }

    public void setCategory_Image(String category_Image) {
        this.category_Image = category_Image;
    }

    public int getDummyimage() {
        return dummyimage;
    }
    public void setDummyimage(int dummyimage) {
        this.dummyimage = dummyimage;
    }
    public dataclassmodule(int id,String category_Image,String cat_id,String cat_name) {
        this.category_Image = category_Image;
        this.question = question;
        this.id=id;
        this.cat_id=cat_id;
        this.cat_name=cat_name;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getUc() {
        return uc;
    }
    public void setUc(String uc) {
        this.uc = uc;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getJobdonepercent() {
        return jobdonepercent;
    }
    public void setJobdonepercent(int jobdonepercent) {
        this.jobdonepercent = jobdonepercent;
    }
    public String getSoil_type() {
        return soil_type;
    }
    public void setSoil_type(String soil_type) {
        this.soil_type = soil_type;
    }
    String image,name,question,datee,comment,plantname,plantdescription,alertmsg,plantdesc,area,soil_type,uc,location;
    public int getCid() {
        return cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    int id,userid,activeimage,cid,jobdonepercent;
    public String getPlantdesc() {
        return plantdesc;
    }
    public void setPlantdesc(String plantdesc) {
        this.plantdesc = plantdesc;
    }
    public String getAlertmsg() {
        return alertmsg;
    }
    public void setAlertmsg(String alertmsg) {
        this.alertmsg = alertmsg;
    }
    public String getPlantname() {
        return plantname;
    }
    public void setPlantname(String plantname) {
        this.plantname = plantname;
    }
    public String getPlantdescription() {
        return plantdescription;
    }
    public void setPlantdescription(String plantdescription) {
        this.plantdescription = plantdescription;
    }
    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getComment() {
        return comment;
    }

    public int getActiveimage() {
        return activeimage;
    }

    public void setActiveimage(int activeimage) {
        this.activeimage = activeimage;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public dataclassmodule(String plantname, String plantdesc)
    {
        this.plantname=plantname;
        this.plantdesc=plantdesc;
    }
public dataclassmodule(String alert)
{
    this.name=alert;
}
    public dataclassmodule(String image, int id, String name, String question)
    {
        this.name=name;
        this.id=id;
        this.name=name;
        this.image=image;
        this.question=question;
    }
    public dataclassmodule(String image, int id, String question,String datee ,int userid,String name)
    {
        this.id=id;
        this.datee=datee;
        this.name=name;
        this.userid=userid;
        this.image=image;
        this.question=question;
    }
    public dataclassmodule(String image, int id, String question,String datee ,int userid)
    {
        this.id=id;
        this.datee=datee;
        this.userid=userid;
        this.image=image;
        this.question=question;
    }
    public dataclassmodule(String image,String comment,String datee,String name)
    {
        this.datee=datee;
        this.name=name;
        this.comment=comment;
        this.image=image;
    }
    public dataclassmodule(String image,String comment,String datee,String name,int cid,int userid)
    {
        this.datee=datee;
        this.name=name;
        this.userid=userid;
        this.cid=cid;
        this.comment=comment;
        this.image=image;
    }
    public dataclassmodule(String image,String plantname,String plantdescription,int id)


    {
        this.plantdescription=plantdescription;
        this.plantname=plantname;
        this.image=image;
        this.id=id;
    }
    public dataclassmodule(String image,String plantname,String plantdescription,String area,String soil_type,int id)
    {
        this.plantdescription=plantdescription;
        this.plantname=plantname;
        this.image=image;
        this.soil_type=soil_type;
        this.area=area;
        this.id=id;
    }
    public dataclassmodule(String name,String userimage,int id)
    {
        this.name=name;
        this.id=id;
        this.image=userimage;
    }
    public dataclassmodule(String description,String name,int id,int id1)
    {
        this.name=name;
        this.description=description;
    }
    public dataclassmodule(String description,String date,String image)
    {
        this.deal_startdate=date;
        this.deal_Image=image;
        this.description=description;
    }
    public dataclassmodule(int jobdonepercent,String uc,String location)
    {
        this.jobdonepercent=jobdonepercent;
        this.uc=uc;
        this.location=location;
    }
    public static List<dataclassmodule> createMovies(int itemCount) {
        List<dataclassmodule> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataclassmodule movie = new dataclassmodule("Movie " + (itemCount == 0 ?
                    (itemCount + 1 + i) : (itemCount + i)));
            data.add(movie);
        }
        return data;
    }
}
