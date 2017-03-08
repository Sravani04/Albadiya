package app.mamac.albadiya;


import android.net.Uri;
import android.os.Bundle;
import android.app.ListActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;




import java.util.ArrayList;

/**
 * Created by T on 14-11-2016.
 */

public class AndroidActivity extends ListActivity {
    ArrayList list = new ArrayList<>();
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.android_activity);
        Button btn = (Button) findViewById(R.id.btnAdd);
        Button btnDel = (Button) findViewById(R.id.btnDel);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice,list);
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.txtItem);
                list.add(edit.getText().toString());
                edit.setText("");
                adapter.notifyDataSetChanged();

            }
        };
        OnClickListener listenerDel1 = new OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItemPositions = getListView().getCheckedItemPositions();
                int itemCount = getListView().getCount();
                for (int i = itemCount - 1; i >= 0; i--) {
                    if (checkedItemPositions.get(i)) {
                        adapter.remove(list.get(i));
                    }
                }
                checkedItemPositions.clear();
                adapter.notifyDataSetChanged();

            }
        };

        btn.setOnClickListener(listener);
        btnDel.setOnClickListener(listenerDel1);
        setListAdapter(adapter);



    }



}
