/*
 * Copyright 2016 eneim@Eneim Labs, nam@ene.im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.ene.toro.sample.feature.facebook;

import android.support.annotation.DrawableRes;

import im.ene.toro.sample.R;

/**
 * Created by eneim on 10/11/16.
 */

public class FbUser {

  private final int profileImageUrl;
  private final String userName;
  private final String userDescription;
  private final String userUrl;
  private final String userTime;
  private final String userLikes;
  private final String userViews;
  private final String userId;
  private final String memberLike;
  private final String personId;



  public FbUser() {
    profileImageUrl = R.drawable.ic_profile;
    userId = "1";
    userName = "Toro Creator";
    userDescription = "Nam Nguyen, nam@ene.im";
    userUrl = "https://github.com/eneim";
    userTime = "";
    userLikes = "";
    userViews = "";
    memberLike = "";
    personId = "";

  }

  public FbUser(String id,String username,String image,String  user_des,String time,
                String total_likes,String total_views,String member_like,String person_id) {
    profileImageUrl = R.drawable.ic_profile;
    userId  = id;
    userName =  username;//"Toro Creator";
    userDescription = user_des;//"Nam Nguyen, nam@ene.im";
    userUrl = image; //"https://github.com/eneim";
    userTime = time;
    userLikes = total_likes;
    userViews = total_views;
    memberLike = member_like;
    personId   = person_id;

  }

  @DrawableRes public int getProfileImageUrl() {
    return profileImageUrl;
  }

  public String getUserId(){ return userId; }

  public String getUserName() {
    return userName;
  }

  public String getUserDescription() {
    return userDescription;
  }

  public String getUserUrl() {
    return userUrl;
  }

  public String getUserTime(){
    return userTime;
  }

  public String getUserLikes(){
    return userLikes;
  }

  public String getUserViews(){
    return userViews;
  }

  public String getMemberLike() { return  memberLike; }

  public String getPersonId() { return  personId; }


}
