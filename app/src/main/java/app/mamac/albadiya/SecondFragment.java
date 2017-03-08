package app.mamac.albadiya;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


/**
 * Created by T on 30-11-2016.
 */

public class SecondFragment extends Fragment {
    EditText firstname;
    EditText lastname;
    EditText username;
    EditText password;
    EditText email;
    EditText phone;

    TextView signin_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        firstname = (EditText) view.findViewById(R.id.firstname);
        lastname  = (EditText) view.findViewById(R.id.lastname);
        username  = (EditText) view.findViewById(R.id.email);
        password  = (EditText) view.findViewById(R.id.password);
        email     = (EditText) view.findViewById(R.id.email);
        phone     = (EditText) view.findViewById(R.id.phone);
        signin_btn = (TextView) view.findViewById(R.id.signin_btn);
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String EMAIL_REGEX="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                //final String PHONE_REGEX= "\\d{3}-\\d{7}";

                String fname = firstname.getText().toString();
                fname = fname.trim();
                String lname = lastname.getText().toString();
                lname = lname.trim();
                String uname = username.getText().toString();
                uname = uname.trim();
                String pass = password.getText().toString();
                String Email = email.getText().toString();
                Email = Email.trim();
                String number = phone.getText().toString();

                if (fname.equals("")){
                    Toast.makeText(getActivity(),"please enter firstname",Toast.LENGTH_SHORT).show();
                    firstname.requestFocus();
                }else if (!fname.matches("[a-zA-Z|\\s]+")){
                    Toast.makeText(getActivity(),"please enter valid firstname",Toast.LENGTH_SHORT).show();
                }else if (lname.equals("")){
                    Toast.makeText(getActivity(),"please eneter lastname",Toast.LENGTH_SHORT).show();
                    lastname.requestFocus();
                }else if (!lname.matches("[a-zA-Z|\\s]+")){
                    Toast.makeText(getActivity(),"please enter valid lastname",Toast.LENGTH_SHORT).show();
                }else if (uname.equals("")){
                    Toast.makeText(getActivity(),"please enter username",Toast.LENGTH_SHORT).show();
                    username.requestFocus();
                }else if (!uname.matches("[a-zA-Z|[0-9]\\s]+")){
                    Toast.makeText(getActivity(),"please enter valid username",Toast.LENGTH_SHORT).show();
                }else if (pass.equals("")){
                    Toast.makeText(getActivity(),"please enter password",Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }else if (Email.equals("")){
                    Toast.makeText(getActivity(),"please enter email",Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }else if (!Email.matches(EMAIL_REGEX)){
                    Toast.makeText(getActivity(),"please enter valid email",Toast.LENGTH_SHORT).show();
                }else if (number.equals("")) {
                    Toast.makeText(getActivity(), "please enter phone number", Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                }else {
                    Ion.with(SecondFragment.this)
                            .load("http://clients.yellowsoft.in/lawyers/api/add-member.php")
                            .setBodyParameter("fname",fname)
                            .setBodyParameter("lname",lname)
                            .setBodyParameter("username",uname)
                            .setBodyParameter("phone",number)
                            .setBodyParameter("email",Email)
                            .setBodyParameter("password",pass)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")) {
                                        Toast.makeText(getActivity(), result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getActivity(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
            }
        });
        return view;
    }
}
