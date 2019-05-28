package ru.nikitasemiklit.diploma.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.vk.api.sdk.VK;

import ru.nikitasemiklit.diploma.R;
import ru.nikitasemiklit.diploma.ui.activities.SingupActivity;
import ru.nikitasemiklit.diploma.ui.activities.TabBarActivity;

public class LoginFragment extends Fragment {
    EditText mLoginEditText;
    EditText mPasswordEditText;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoginEditText = view.findViewById(R.id.et_email);
        mPasswordEditText = view.findViewById(R.id.et_password);

        view.findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        view.findViewById(R.id.bt_login_vk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VK.initialize(context);
                VK.login((TabBarActivity) context);
            }
        });

        view.findViewById(R.id.bt_google_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestServerAuthCode("AIzaSyAxh7RXHVBIMXIu4_RJwB1RxFguUzM2qBg")
                        .requestEmail()
                        .requestScopes(new Scope("https://www.googleapis.com/auth/youtube.readonly"))
                        .build();
                GoogleApiClient mApiClient = new GoogleApiClient.Builder(context)
                        .enableAutoManage((TabBarActivity) context, new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                            }
                        })
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mApiClient);
                startActivityForResult(signInIntent, 12);
            }
        });


        view.findViewById(R.id.bt_singup_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SingupActivity.class);
                startActivity(i);
            }
        });

    }

}
