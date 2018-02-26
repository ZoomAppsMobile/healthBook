package com.example.d.healthbook.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.d.healthbook.API.API_Controller;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Bottom_helper.SharedPreference;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Controller.RequestController;
import com.example.d.healthbook.Controller.RequestInterface;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.LoginResponseModel;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.KeyBoard;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.Arrays;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener, RequestInterface {

    @BindView(R.id.activity_start_bts_container)
    LinearLayout activity_start_bts_container;

    @BindView(R.id.activity_start_login_btn)
    Button activity_start_login_btn;

    @BindView(R.id.activity_start_login_ets_container)
    LinearLayout activity_start_login_ets_container;

    @BindView(R.id.activity_start_social_container)
    LinearLayout activity_start_social_container;

    @BindView(R.id.activity_start_login_btns_container)
    LinearLayout activity_start_login_btns_container;

    @BindView(R.id.activity_start_back_tv)
    TextView activity_start_back_tv;

    @BindView(R.id.activity_welcome_action_authenticate)
    Button activity_welcome_action_authenticate;

    @BindView(R.id.emailET)
    EditText emailET;

    @BindView(R.id.passwordET)
    EditText passwordET;

    @BindView(R.id.activity_welcome_action_register)
    Button activity_welcome_action_register;
    protected RequestController authPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        authPresenter = new RequestController(this);
        //View Bind Initialize
        ButterKnife.bind(this);
        init_clicks();
//        emailET.setText("drugoi.nik@gmail.com");
        //  emailET.setText("alpamis.nurtas@mail.ru");
        // passwordET.setText("123");
        if (getIntent().getStringExtra("email") == null) {
            emailET.setText("miko_982@mail.ru");
            passwordET.setText("apa91ata");
        } else {
            emailET.setText(getIntent().getStringExtra("email"));
            passwordET.setText(getIntent().getStringExtra("pass"));
            forStartViews(View.GONE);
            forLoginViews(View.VISIBLE);
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();


            String message = "Для завершения процесса регистрации Вам будет выслано сообщение на указанный e-mail  " +
                    getIntent().getStringExtra("email") + ". После подтверждения Вам станут доступны сервисы и привилегии зарегистрированных пользователей.";
            String button1String = "Ok";


            final AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setMessage(message); // сообщение
            ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {


                }
            });


            ad.show();


        }

        authPresenter.<LoginResponseModel>makeRequest(
                App.getApi().loginUser(API_Controller.loginJson(emailET.getText().toString(), passwordET.getText().toString(), false))
        );
    }

    private LoginResponseModel registeredUser;
    private Boolean blockLogin = false;

    public void logInMethod(String email, String password, Boolean rememberMe) {
        if (!blockLogin)
            blockLogin = true;
        else
            return;

        App.getApi().loginUser(API_Controller.loginJson(email, password, rememberMe)).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {

                if (response.code() == 200) {
                    registeredUser = response.body();
                    MainController.showPreparedToast(AuthActivity.this, "Success");
                    GlobalVariables.user_auth_token = registeredUser.getAuthToken();
                    GlobalVariables.user_id = registeredUser.getUserId();
                    SharedPreference.setToken(AuthActivity.this, registeredUser.getAuthToken());
                    SharedPreference.setId(AuthActivity.this, String.valueOf(registeredUser.getUserId()));
                    Intent intent = new Intent(AuthActivity.this, UserActivityInfo.class);
                    startActivity(intent);
                    finish();

                } else {
                    blockLogin = false;
                    MainController.showPreparedToast(AuthActivity.this, "Failed");
                }

            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                blockLogin = false;
                Toast.makeText(AuthActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void init_clicks() {
        activity_welcome_action_authenticate.setOnClickListener(this);
        activity_start_back_tv.setOnClickListener(this);
        activity_start_login_btn.setOnClickListener(this);
        activity_welcome_action_register.setOnClickListener(this);
    }

    void viewConvertion(String typeOfConvertion) {
        final String type = typeOfConvertion;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case "ShowLogin": {
                        forStartViews(View.GONE);
                        forLoginViews(View.VISIBLE);
                        break;
                    }
                    case "BackLogin": {
                        forStartViews(View.VISIBLE);
                        forLoginViews(View.GONE);
                        break;
                    }
                    default:
                        break;
                }
            }
        });
    }

    void forStartViews(int visiblitiltype) {
        activity_start_bts_container.setVisibility(visiblitiltype);
        activity_start_social_container.setVisibility(visiblitiltype);
    }

    void forLoginViews(int visiblitiltype) {
        activity_start_login_btns_container.setVisibility(visiblitiltype);
        activity_start_login_ets_container.setVisibility(visiblitiltype);
    }

    @OnClick({R.id.vk_login, R.id.fc_login, R.id.ok_login, R.id.myworld_login})
    public void onClickSocial(View view) {
        switch (view.getId()) {
            case R.id.vk_login:
                Toast.makeText(this, "vk_login", Toast.LENGTH_SHORT).show();
                getSocial("https://oauth.vk.com/authorize?client_id=5145029&response_type=code&redirect_uri=https%3A%2F%2Fidocta.kz%2Fsocialauth%2Fvk&xoauth_displayname=My%20Application&scope=email", "Вход vk");
                break;
            case R.id.fc_login:
                Toast.makeText(this, "fc_login", Toast.LENGTH_SHORT).show();
                getSocial("https://www.facebook.com/login.php?skip_api_login=1&api_key=843653442418053&signed_next=1&next=https%3A%2F%2Fwww.facebook.com%2Fv2.5%2Fdialog%2Foauth%3Fredirect_uri%3Dhttps%253A%252F%252Fidocta.kz%252Fsocialauth%252Ffb%26scope%3Demail%26response_type%3Dcode%26client_id%3D843653442418053%26ret%3Dlogin%26logger_id%3Dfb75185f-4c0c-9b56-9147-b476555b9919&cancel_url=https%3A%2F%2Fidocta.kz%2Fsocialauth%2Ffb%3Ferror%3Daccess_denied%26error_code%3D200%26error_description%3DPermissions%2Berror%26error_reason%3Duser_denied%23_%3D_&display=page&locale=ru_RU&logger_id=fb75185f-4c0c-9b56-9147-b476555b9919", "Вход facebook");
                break;
            case R.id.ok_login:
                Toast.makeText(this, "ok_login", Toast.LENGTH_SHORT).show();
                getSocial("https://connect.ok.ru/dk?st.cmd=OAuth2Login&st.redirect=%252Fdk%253Fst.cmd%253DOAuth2Permissions%2526amp%253Bst.response_type%253Dcode%2526amp%253Bst.show_permissions%253Doff%2526amp%253Bst.redirect_uri%253Dhttps%25253A%25252F%25252Fidocta.kz%25252Fsocialauth%25252FokRu%2526amp%253Bst.client_id%253D1248558848&st.client_id=1248558848", "Вход однокласники");
                break;
            case R.id.myworld_login:
                Toast.makeText(this, "myworld_login", Toast.LENGTH_SHORT).show();
                getSocial("https://connect.mail.ru/oauth/authorize?client_id=749296&response_type=code&redirect_uri=https%3A%2F%2Fidocta.kz%2Fsocialauth%2FmailRu&xoauth_displayname=My%20Application", "Вход мой мир");
                break;
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_welcome_action_authenticate: {
                viewConvertion("ShowLogin");
                break;
            }
            case R.id.activity_start_back_tv: {
                viewConvertion("BackLogin");
                break;
            }
            case R.id.activity_start_login_btn: {
                //Login
                if (emailET.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "Пожалуйста введите eMail", Toast.LENGTH_SHORT).show();
                } else if (passwordET.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "Пожалуйста введите пароль", Toast.LENGTH_SHORT).show();
                } else {
                    logInMethod(emailET.getText().toString(), passwordET.getText().toString(), true);
                    break;
                }
//                Intent intent= new Intent(AuthActivity.this,MainPageActivity.class);
//                startActivity(intent);
//                break;
            }
            case R.id.activity_welcome_action_register: {
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            }
            default: {
                MainController.showToast(AuthActivity.this, "Not implemented");
                break;
            }
        }
    }

    @Override
    public <T> void onRequestSuccess(T out) {
        LoginResponseModel data = (LoginResponseModel) out;
    }

    @Override
    public void onRequestFailure() {
        MainController.showPreparedToast(this, "Error");
    }


    void getSocial(String url, String title) {


        final WebView webView = new WebView(this);


        webView.setWebViewClient(new WebViewClient() {
            boolean clearHistory = false;

            public void clearHistory() {
                clearHistory = true;
            }


            public void onPageFinished(WebView view, String url) {

                try{
                    String code = parseUrl(webView.getUrl(), "code").toString();
                Log.e("code", code);
                if (code != null) {
                    App.getApi().setTokenVk(code).enqueue(new Callback<LoginResponseModel>() {
                        @Override
                        public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                            Log.e("responseVk", response.body().getAuthToken().toString());
                        }

                        @Override
                        public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                            Log.e("errorResponseVk", t.getMessage().toString());
                        }
                    });

                }
                    WebSettings mWebSettings = webView.getSettings();
                    mWebSettings.setSavePassword(false);
                    mWebSettings.setSaveFormData(false);
                    CookieManager cookieManager = CookieManager.getInstance();
                    cookieManager.setAcceptCookie(false);

                }catch (Exception ex){

                }




//                webView.clearCache(true);
//                webView.clearHistory();
//                clearHistory = true;
            }


        });
        webView.loadUrl(url);





        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title(title)
                .customView(webView, false)
                .positiveText("Закрыть");

        MaterialDialog dialog = builder.build();
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                KeyboardVisibilityEvent.setEventListener(
                        AuthActivity.this,
                        new KeyboardVisibilityEventListener() {
                            @Override
                            public void onVisibilityChanged(boolean isOpen) {
                                if (isOpen == true) {

                                } else {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                                }
                            }
                        });


                return false;
            }
        });
        dialog.show();


//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle(title);
//
//
//
//
//
//
//
//        final WebView wv = new WebView(this);
//        WebSettings webSettings = wv.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        EditText editTextWebView = (EditText) findViewById(R.id.editTextWebView);
//        editTextWebView.requestFocus();
//        editTextWebView.setFocusable(true);
//        wv.loadUrl(url);
//
//        wv.requestFocusFromTouch();
//
//        wv.requestFocus(View.FOCUS_DOWN);
//        wv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0,0);
//                return false;
//            }
//        });
//        wv.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//
//                return true;
//            }
//        });
//
//        alert.setView(wv);
//        alert.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.dismiss();
//            }
//        });
//        alert.show();


    }


    public String parseUrl(String url, String parseParamentr) {
        Uri uri = Uri.parse(url);
        String protocol = uri.getScheme();
        String server = uri.getAuthority();
        String path = uri.getPath();
        Set<String> args = uri.getQueryParameterNames();
        String limit = uri.getQueryParameter(parseParamentr);
        return limit;
    }


}
