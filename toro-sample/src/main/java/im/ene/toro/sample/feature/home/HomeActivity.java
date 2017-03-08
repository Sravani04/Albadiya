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

package im.ene.toro.sample.feature.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import im.ene.toro.sample.BaseActivity;
import im.ene.toro.sample.R;
import im.ene.toro.sample.feature.advance1.Advance1Activity;
import im.ene.toro.sample.feature.average1.Average1Activity;
import im.ene.toro.sample.feature.basic1.Basic1Activity;
import im.ene.toro.sample.feature.basic2.Basic2Activity;
import im.ene.toro.sample.feature.basic3.Basic3Activity;
import im.ene.toro.sample.feature.extended.ExtendedListActivity;
import im.ene.toro.sample.feature.legacy.LegacyActivity;
import im.ene.toro.sample.widget.SampleItemButton;

//import im.ene.toro.sample.feature.facebook.FacebookTimelineActivity;

/**
 * Created by eneim on 6/30/16.
 */
public class HomeActivity extends BaseActivity {

   Toolbar toolbar;
    SampleItemButton sample_1,sample_2,sample_3,sample_4,sample_5,sample_6,sample_7,sample_8;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    toolbar = (Toolbar) findViewById(R.id.toolbar);

    sample_1 = (SampleItemButton) findViewById(R.id.basic_sample_1);
    sample_2 = (SampleItemButton) findViewById(R.id.basic_sample_2);
    sample_3 = (SampleItemButton) findViewById(R.id.basic_sample_3);

    sample_4 = (SampleItemButton) findViewById(R.id.average_sample_1);
    sample_5 = (SampleItemButton) findViewById(R.id.advance_sample_1);
    sample_6 = (SampleItemButton) findViewById(R.id.custom_sample_1);

    sample_7 = (SampleItemButton) findViewById(R.id.legacy_sample_1);
    sample_8 = (SampleItemButton) findViewById(R.id.exoplayer2_sample);

    sample_1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openBasicSample1();
      }
    });
    sample_2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openBasicSample2();
      }
    });
    sample_3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openBasicSample3();
      }
    });
    sample_4.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openAverageSample1();
      }
    });
    sample_5.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openAdvanceSample1();
      }
    });
//    sample_6.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        openCustomSample1();
//      }
//    });
//    sample_7.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        openLegacySample1();
//      }
//    });
    sample_8.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openExoPlayer2Sample1();
      }
    });


    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
  }

   void openBasicSample1() {
    startActivity(new Intent(this, Basic1Activity.class));
  }

   void openBasicSample2() {
    startActivity(new Intent(this, Basic2Activity.class));
  }

   void openBasicSample3() {
    startActivity(new Intent(this, Basic3Activity.class));
  }

   void openAverageSample1() {
    startActivity(new Intent(this, Average1Activity.class));
  }

   void openAdvanceSample1() {
    startActivity(new Intent(this, Advance1Activity.class));
  }

//   void openCustomSample1() {
//    startActivity(new Intent(this, FacebookTimelineActivity.class));
//  }

   void openLegacySample1() {
    startActivity(new Intent(this, LegacyActivity.class));
  }

   void openExoPlayer2Sample1() {
    startActivity(new Intent(this, ExtendedListActivity.class));
  }
}
