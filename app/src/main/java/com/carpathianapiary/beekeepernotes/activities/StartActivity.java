package com.carpathianapiary.beekeepernotes.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.carpathianapiary.beekeepernotes.R;
import com.carpathianapiary.beekeepernotes.adapters.RecyclerAdapter;
import com.carpathianapiary.beekeepernotes.adapters.ViewPagerAdapter;
import com.carpathianapiary.beekeepernotes.fragments.ApiaryFragment;

import com.carpathianapiary.beekeepernotes.fragments.HistoryFragment;
import com.carpathianapiary.beekeepernotes.interfaces.Communicator;
import com.carpathianapiary.beekeepernotes.models.Apiary;
import com.carpathianapiary.beekeepernotes.models.BeeColony;
import com.carpathianapiary.beekeepernotes.models.Beehive;
import com.carpathianapiary.beekeepernotes.models.Notifaction;
import com.carpathianapiary.beekeepernotes.utils.ToolbarActionModeCallback;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shawnlin.numberpicker.NumberPicker;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.RECEIVE_BOOT_COMPLETED;
import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission.WAKE_LOCK;
import static com.carpathianapiary.beekeepernotes.fragments.ApiaryFragment.DADAN;
import static com.carpathianapiary.beekeepernotes.fragments.ApiaryFragment.UKRAINIAN;
import static com.carpathianapiary.beekeepernotes.utils.AlarmService.HISTORY_INT;

@EActivity
public class StartActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, Communicator {
    private static final String TAG = "StartActivity";
    public static final int RC_SIGN_IN = 9001;
    public static final int MODE_CLEAN_ITEM = 0;
    public static final int MODE_SELECT_ALL = 3;
    public static final int MODE_MULTI_SELECT = 2;
    private static final String STATE_PAGE_NUMBER = "start_page_number";
    public AlertDialog alertDialog;
    public  DatabaseReference myRef;
    private ViewPagerAdapter pagerAdapter;
    private int mNumberBeehiveInt = 0;
    private int startPageNumber = 0;
    private int pasteSettingsChecker;
	private int mSelectMode = 0;
    private boolean multiSelectMode = false;
    private DataSnapshot mDataSnapshot;
    private ToolbarActionModeCallback toolbarActionModeCallback = null;
    private Calendar mCalendar;
    private String mUserUid;

    @ViewById(R.id.appBarlayout)
    AppBarLayout appBarLayout;
    @ViewById(R.id.buttonAddNewBeehive)
    FloatingActionButton buttonAddNewBeehive;

    private int position;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private String[] mPermissionList = new String[]{INTERNET, VIBRATE, WAKE_LOCK, RECEIVE_BOOT_COMPLETED};
    private boolean mTypeMode;

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;
    @ViewById(R.id.userAcountImage)
    CircleImageView userAccountImage;
    @ViewById(R.id.facebokLoginButton)
    Button facebookLoginButton;
    @ViewById(R.id.googleLoginButton)
    Button googleLoginButton;
    @ViewById(R.id.textViewEmail)
    TextView textViewEmail;
    @ViewById(R.id.googleButton)
    SignInButton loginGoogle;
    @ViewById(R.id.buttonLogout)
    Button buttonLogaut;
    @ViewById(R.id.buttonGoogleLogout)
    Button buttonGoogleLogout;
    @ViewById(R.id.facebookButton)
    LoginButton loginFacebook;
    @ViewById(R.id.email)
    EditText inputEmail;
    @ViewById(R.id.password)
    EditText inputPassword;
    @ViewById(R.id.progressBar)
    ProgressBar progressBar;
    @ViewById(R.id.emailLoginButton)
    Button emailLoginButton;
    @ViewById(R.id.inputEmailLayout)
    TextInputLayout inputEmailLayout;
    @ViewById(R.id.inputPasswordLayout)
    TextInputLayout inputPasswordLayout;
    @ViewById(R.id.buttonResetPassword)
    Button buttonResetPassword;
    @ViewById(R.id.buttonCreateNewAccount)
    Button buttonCreateNewAccount;
    @ViewById(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.viewpager)
    ViewPager viewPager;
    @ViewById(R.id.tabs)
    TabLayout tabLayout;

    ActionBarDrawerToggle actionBarDrawerToggle;
    private ArrayList<Notifaction> notifactionList = new ArrayList<Notifaction>();
    private List<Apiary> apiaries = new ArrayList<>();
    private int mTypeBeehive;
    private int mQuantityOfColonies;
    private Menu mOptionMenu;
    private ActionMode mActionMode;

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_PAGE_NUMBER, startPageNumber);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start1);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            startPageNumber = savedInstanceState.getInt(STATE_PAGE_NUMBER);
        }
        mCalendar = Calendar.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
              if (firebaseAuth.getCurrentUser() == null){
                  checkCurentUser();
              }
            }
        };
        tabLayout.setupWithViewPager(viewPager);
            checkCurentUser();
            checkAllPermission();


    }

    private void setupActionView(int tabPos) {
       if( tabPos > 0){
           buttonAddNewBeehive.show();
           mOptionMenu.findItem(R.id.actionDelete).setVisible(true);
           mOptionMenu.findItem(R.id.actionNewApiary).setVisible(true);
       }else {
           buttonAddNewBeehive.hide();
           mOptionMenu.findItem(R.id.actionDelete).setVisible(false);
           mOptionMenu.findItem(R.id.actionNewApiary).setVisible(false);
       }

    }

    private void checkCurentUser() {
        mUser = mAuth.getCurrentUser();
        if (mUser == null) {
            showableLogInGroup(true);
            mTypeMode = false;
            showLoginDialog();

        } else {
            if (mUser.getProviderData().get(0).equals("google.com")) {
                mTypeMode = true;
                loginGoogle();
                mUserUid = mUser.getUid();
                getDataFromFirebase(mUserUid);
                updateUI(mUser.getProviderData().get(0).getDisplayName(),
                        String.valueOf(mUser.getProviderData().get(0).getPhotoUrl()));
                buttonGoogleLogout.setVisibility(View.VISIBLE);
            } else if (mUser.getProviderData().get(0).equals("facebook.com")) {

                mTypeMode = true;
                mUserUid = mUser.getUid();
                getDataFromFirebase(mUserUid);
                loginFacebook();
                updateUI(mUser.getProviderData().get(0).getDisplayName(),
                        String.valueOf(mUser.getProviderData().get(0).getPhotoUrl()));
            } else if (mUser.getProviderData().get(0).equals("password")) {
                mTypeMode = true;
                mUserUid = mUser.getUid();
                getDataFromFirebase(mUserUid);
                updateUI(mUser.getProviderData().get(0).getEmail(), null);
                buttonLogaut.setVisibility(View.VISIBLE);
                buttonLogaut.setText("LOG OUT");
            }
        }

    }
    public boolean isOnline() {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) StartActivity.this
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }
    private void loginFacebook() {
        if(isOnline()) {
            loginFacebook.setVisibility(View.VISIBLE);
        }
        // If using in a fragment
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                Log.d(TAG, "onCurrentAccessTokenChanged()");
                if (accessToken == null) {
                    showableLogInGroup(false);
                    loginFacebook.setVisibility(View.VISIBLE);
                } else if (accessToken2 == null) {
                    LoginManager.getInstance().logOut();
                    mAuth.signOut();
                    showableLogInGroup(true);
                }
            }
        };
        mCallbackManager = CallbackManager.Factory.create();

        loginFacebook.setReadPermissions("email");
        // Callback registration
        loginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(StartActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                showableLogInGroup(true);
            }

            @Override
            public void onError(FacebookException exception) {
                showableLogInGroup(true);
            }
        });


    }
    private void loginGoogle() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        if (mCallbackManager != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                updateUI(null, null);
                // [END_EXCLUDE]
            }
        }
    }
    private void handleFacebookAccessToken(AccessToken token) {
        //Log.d(TAG, "handleFacebookAccessToken:" + token)

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the mUser. If sign in succeeds
                        // the mAuth state listener will be notified and logic to handle the
                        // signed in mUser can be handled in the listener.
                        if (!task.isSuccessful()) {

                            //Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(StartActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Profile profile = Profile.getCurrentProfile();
                            updateUI(profile.getFirstName(), profile.getProfilePictureUri(200, 200).toString());
                            mUserUid = mAuth.getCurrentUser().getUid();
                            getDataFromFirebase(mUserUid);
                            mTypeMode = true;

                        }
                    }
                });
    }


    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in mUser's information
                            Log.d(TAG, "signInWithCredential:success");
                            loginGoogle.setVisibility(View.GONE);
                            buttonGoogleLogout.setVisibility(View.VISIBLE);
                            updateUI(acct.getEmail(), String.valueOf(acct.getPhotoUrl()));
                            mUserUid = mAuth.getCurrentUser().getUid();
                            getDataFromFirebase(mUserUid);

                        } else {
                            // If sign in fails, display a message to the mUser.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(StartActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, null);
                        }
                    }
                });
    }


    private void showableLogInGroup(boolean b) {
        if (b) {
            inputPasswordLayout.setVisibility(View.VISIBLE);
            inputEmailLayout.setVisibility(View.VISIBLE);
            emailLoginButton.setVisibility(View.VISIBLE);
            buttonResetPassword.setVisibility(View.VISIBLE);
            buttonCreateNewAccount.setVisibility(View.VISIBLE);
            facebookLoginButton.setVisibility(View.VISIBLE);
            googleLoginButton.setVisibility(View.VISIBLE);
            loginFacebook.setVisibility(View.GONE);
            loginGoogle.setVisibility(View.GONE);
            buttonLogaut.setVisibility(View.GONE);
            buttonGoogleLogout.setVisibility(View.GONE);
        } else {
            inputPasswordLayout.setVisibility(View.GONE);
            inputEmailLayout.setVisibility(View.GONE);
            emailLoginButton.setVisibility(View.GONE);
            buttonResetPassword.setVisibility(View.GONE);
            buttonCreateNewAccount.setVisibility(View.GONE);
            facebookLoginButton.setVisibility(View.GONE);
            googleLoginButton.setVisibility(View.GONE);
            buttonGoogleLogout.setVisibility(View.GONE);
        }
    }
    private void updateUI(String email, String photoUrl) {
        if (photoUrl != null) {
            Glide
                    .with(this)
                    .load(photoUrl)
                    .into(userAccountImage);
        } else {
            userAccountImage.setImageResource(R.drawable.user_default);
        }
        if (email != null) {
            textViewEmail.setText(email);
        } else {
            textViewEmail.setText("");
        }
    }
    private void checkAllPermission() {
    List<String> mListPerm = new ArrayList<>();
        for (int i = 0; i < mPermissionList.length; i ++){
        if (ContextCompat.checkSelfPermission(StartActivity.this,
                mPermissionList[i])
                != PackageManager.PERMISSION_GRANTED){
            mListPerm.add( mPermissionList[i]);
        } else {
             Toast.makeText(this, "" + mPermissionList[i].toString() + " is already granted.", Toast.LENGTH_SHORT).show();

        }
    }
        if (mListPerm.size() > 0){
        String[] permision = new String[mListPerm.size()];
        for (int i = 0; i < mListPerm.size(); i++){
            permision[i] = mListPerm.get(i);
        }

        ActivityCompat.requestPermissions(StartActivity.this, permision, 69);
    }



}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 69 && grantResults.length > 0){
            for (int i = 0; i < permissions.length; i++){

            }

        } else  {
            Toast.makeText(StartActivity.this, " You do not gave any permission ", Toast.LENGTH_SHORT).show();



        }

    }

    private void loadDataSnapshotApiary(DataSnapshot dataSnapshot) {
       tabLayout.getTabCount();
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            Apiary apiary = postSnapshot.getValue(Apiary.class);
            if (apiaries.size() == 0) {
                apiaries. add(apiary);
            }else{
                boolean alrediExist = false;
                for(int i = 0; i < apiaries.size(); i++){
                    if (apiaries.get(i).getNameApiary().equals(apiary.getNameApiary())){
                        apiaries.set(i, apiary);
                        alrediExist = true;
                    }
                }
                if (!alrediExist){
                    apiaries.add(apiary);
                }
            }
//	       if ( tabLayout.getTabCount() > 1 ) {
//                if (alreadyExist(apiary.nameApiary)) {
//                    ApiaryFragment apu = (ApiaryFragment) pagerAdapter.getItem(position);
//                    apu.setData(apiary, mSelectMode);
//                }else{
//                    ApiaryFragment apiaryFragment = new ApiaryFragment_().builder()
//                            .apiary(apiary)
//                            .build();
//                    pagerAdapter.addFragment(apiaryFragment, apiary.getNameApiary());
//                    pagerAdapter.notifyDataSetChanged();
//                }
//	       }else {
//		       ApiaryFragment apiaryFragment = new ApiaryFragment_().builder()
//                       .apiary(apiary)
//                       .build();
//		       pagerAdapter.addFragment(apiaryFragment, apiary.getNameApiary());
//               pagerAdapter.notifyDataSetChanged();
//	       }

        }

       if (viewPager.getAdapter() == null) {
	       viewPager.setAdapter(pagerAdapter);
       }
       addOnLongClickListener();
        viewPager.setCurrentItem(startPageNumber);
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount() -1);
    }

    private boolean alreadyExist(String name) {
        boolean isExist = false;
        if (pagerAdapter != null) {
            for (int i = 0; i < pagerAdapter.getCount(); i++) {
                if (name.equals(pagerAdapter.getPageTitle(i).toString())) {
                    isExist = true;
                    position = i;
                    break;
                }
            }
        }
        return isExist;
    }


    private void addOnLongClickListener() {
        LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);

        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setId(i);
            tabStrip.getChildAt(i).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showDeleteApiaryDialog(String.valueOf(tabLayout.getTabAt(v.getId()).getText()), v.getId());
                    return true;
                }
            });
        }

    }

    private void showDeleteApiaryDialog(final String s, final int position) {
        if (position > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("WARNING");
            builder.setMessage("Do you want to delete " + s + " Apiary?" + "All data in this section will be lost forever");
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            deleteApiary(s, position);
                            dialog.cancel();
                        }
                    });
            builder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                    /**        if (toolbarActionModeCallback != null) {
                                getSupportFragmentManager()
                                        .beginTransaction().
                                        remove(toolbarActionModeCallback).commit();
                                toolbarActionModeCallback.dismiss();
                                toolbarActionModeCallback = null;
                                multiSelectMode = false;
                                loadDataSnapshotApiary(mDataSnapshot);
                            }*/
                        }
                    });

            alertDialog = builder.create();
            alertDialog.show();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Action");
            builder.setMessage("You can not delete history page");
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private void deleteApiary(String name, final int position) {
        if (tabLayout.getTabCount() >= 1 && position < tabLayout.getTabCount() && position != 0) {
           // tabLayout.removeTabAt(position);
            pagerAdapter.deleteFragment(position);
            pagerAdapter.notifyDataSetChanged();
            mSelectMode = 0;
            multiSelectMode = false;
            myRef.child(mUserUid).child("apiary").child(name).removeValue();
            Toast.makeText(StartActivity.this, "Apiary was delete", Toast.LENGTH_SHORT).show();
        }
    }


    void createApiaryDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NEW APIARY");
        builder.setMessage("Please, Enter name and count beehive Apiary");
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final TextInputEditText inputName = new TextInputEditText(this);
        inputName.setHint("Name");
        inputName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        inputName.setLayoutParams(lp1);
        builder.setView(inputName);
        final TextInputEditText inputCount = new TextInputEditText(this);
        inputCount.setHint("Count of beehive");
        inputCount.setInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        inputCount.setLayoutParams(lp2);
        linearLayout.addView(inputName);
        linearLayout.addView(inputCount);
        builder.setView(linearLayout);

        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputName.getText().toString().length() == 0) {
                    inputName.setError("Please enter Name");

                } else if (inputCount.getText().toString().equals("")) {
                    inputCount.setError("Please enter count");
                } else {
                    createNewApiary(inputName.getText().toString(), Integer.parseInt(inputCount.getText().toString()));
                    alertDialog.dismiss();
                }
            }
        });
    }
    void createNewApiary(String name, int number){
        if (!name.equals("")){
            mNumberBeehiveInt = Integer.parseInt(String.valueOf(number));
            BeeColony beeColony = new BeeColony();
            beeColony.setHaveFood(true);
            beeColony.setNoteBeeColony("ffd");
            beeColony.setOutput("ff");
            beeColony.setQueen(mCalendar.getTime().getTime());
            beeColony.setTimeReminder(mCalendar.getTime().getTime());
            beeColony.setWorm(mCalendar.getTime().getTime());
            beeColony.setCheckedTime(mCalendar.getTime().getTime());
            beeColony.setBeeEmptyFrame(5);
            beeColony.setBeeHoneyFrame(0);
            beeColony.setBeeWormsFrame(0);


            List<BeeColony> BeeColonyList = new ArrayList<>();
            BeeColonyList.add(beeColony);
            BeeColonyList.add(beeColony);
            List<Beehive> beehiveList = new ArrayList<>();
            if (mNumberBeehiveInt > 0) {
                for (int i = 1; i<= mNumberBeehiveInt; i++){
                    Beehive beehive = new Beehive();
                    beehive.setFounded(mCalendar.getTime().getTime());
                    beehive.setNumberBeehive(i);
                    beehive.setNoteBeehive("dkfsdlkj;k");
                    beehive.setTypeBeehive(DADAN);
                    beehive.setBeeColonies(BeeColonyList);
                    beehiveList.add(beehive);
                }
            }


            Apiary apiary = new Apiary();
            apiary.setuId((int)Math.round(Math.random()*1000));
            apiary.setLocationApiary("flfsdldlk");
            apiary.setNameApiary(name);
            apiary.setNoteApiary("kdslfsdlk");
            apiary.setBeehives(beehiveList);

            myRef.child(mUserUid).child("apiary").child(name).setValue(apiary);
            Notifaction notifaction = new Notifaction();
            notifaction.setNameNotifaction("Created Apiary");
            notifaction.setTextNotifaction("you did create new Apiary " + apiary.getNameApiary()+ "with " + beehiveList.size() + "beehives");
            notifaction.setuId(apiary.getuId());
            notifaction.setSchowTime(Calendar.getInstance().getTime().getTime());
            notifaction.setPathNotifaction(new Uri.Builder().appendPath(apiary.getNameApiary()).build().toString());
            notifaction.setTypeNotifaction(HISTORY_INT);
            myRef.child(mUserUid).child("history").child(String.valueOf(notifaction.getSchowTime())).setValue(notifaction);


        }
    }
    private void addBeehive(Beehive mBeehive, Apiary apiary) {
        apiary.getBeehives().add(mBeehive.getNumberBeehive() - 1, mBeehive);
        for (int i = 0; i < apiary.getBeehives().size(); i ++ ){
            apiary.getBeehives().get(i).setNumberBeehive(i + 1);
        }
        FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("apiary").child(apiary.getNameApiary())
                .setValue(apiary);
        Notifaction notifaction = new Notifaction();
        notifaction.setNameNotifaction("Created BeehiveActivity");
        notifaction.setTextNotifaction("you did create new BeehiveActivity № " + mBeehive.getNumberBeehive() + "in Apiary  " + apiary.getNameApiary());
        notifaction.setuId(apiary.getuId());
        notifaction.setSchowTime(Calendar.getInstance().getTime().getTime());
        notifaction.setPathNotifaction(new Uri.Builder().appendPath(apiary.getNameApiary()).appendPath(String.valueOf(mBeehive.getNumberBeehive() - 1)).build().toString());
        notifaction.setTypeNotifaction(HISTORY_INT);
        myRef.child(mUserUid).child("history").child(String.valueOf(notifaction.getSchowTime())).setValue(notifaction);



    }

    @Override
    public void saveBeehive(Beehive beehive, String nameApiary) {
        myRef.child(mUserUid).child("apiary").child(nameApiary).child("beehives").child(String.valueOf(beehive.getNumberBeehive() - 1)).setValue(beehive);

    }


    @Override
    public androidx.appcompat.view.ActionMode setDataForTools(final RecyclerAdapter recyclerAdapter, final List<Beehive> beehiveList, final Beehive itemBeehive, final String nameApiary, final int mode) {

        if (toolbarActionModeCallback == null && mode == 1) {
            toolbarActionModeCallback = new ToolbarActionModeCallback(StartActivity.this, recyclerAdapter, beehiveList, nameApiary, 1);
            mActionMode = ((AppCompatActivity) StartActivity.this).startSupportActionMode(toolbarActionModeCallback);
            return mActionMode;
        }else if(toolbarActionModeCallback != null && mode == 1){
            toolbarActionModeCallback.setData(recyclerAdapter, beehiveList, itemBeehive, nameApiary, 1);
            return mActionMode;
        }else if (toolbarActionModeCallback != null && mode == 2){
                toolbarActionModeCallback.setData(recyclerAdapter, beehiveList, itemBeehive, nameApiary, 1);
                return mActionMode;
        }else{
            return null;
        }
    }

    @Override
    public void selectAll() {
        multiSelectMode = true;
        loadDataSnapshotApiary(mDataSnapshot);
        ApiaryFragment fr = (ApiaryFragment) pagerAdapter.getItem(tabLayout.getSelectedTabPosition());
        fr.selectMode(MODE_SELECT_ALL);

    }

    @Override
    public void moveTabTo(String nameApiary, int numberBeehive) {
        if ( tabLayout.getTabCount() > 1 ) {
            if (alreadyExist(nameApiary)) {
              tabLayout.getTabAt(position).select();
              if (numberBeehive != -1) {
                  ApiaryFragment apu = (ApiaryFragment) pagerAdapter.getItem(position);
                  apu.showItem(numberBeehive);
              }
            }else{
                //todo need show dialog  about this apiary is not exist any more and do not show this notification any more
            }
        }

    }

    @Override
    public void deleteBeehive( final List<Beehive> beehives, final String nameApiary, final boolean refreshBeehivesListItem) {

        Query apiary = myRef.child(mUserUid).child("apiary").child(nameApiary);
        apiary.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Apiary apiary = dataSnapshot.getValue(Apiary.class);
                if (apiary.getBeehives().size() == beehives.size()) {
                    showDeleteApiaryDialog(nameApiary, tabLayout.getSelectedTabPosition() );

                } else {
                    if (beehives.size() != 0) {
                        int numberDeletedBeehives = 0;
                        for (int numberBeehivesInDatabase = 0; numberDeletedBeehives < beehives.size(); numberBeehivesInDatabase++) {
                            if (beehives.get(numberDeletedBeehives).getNumberBeehive() == apiary.getBeehives().get(numberBeehivesInDatabase).getNumberBeehive()) {
                                apiary.getBeehives().remove(numberBeehivesInDatabase);
                                numberDeletedBeehives++;
                                numberBeehivesInDatabase = -1;
                            }
                        }

                    }
                    int finalNumberBeehives = apiary.getBeehives().size();
                    for (int a = 0; a < finalNumberBeehives; a++) {
                        apiary.getBeehives().get(a).setNumberBeehive(a + 1);

                    }
                    myRef.child(mUserUid).child("apiary").child(nameApiary).setValue(apiary);
                    toolbarActionModeCallback = null;
                    if (refreshBeehivesListItem) {
                        mSelectMode = 2;
                        multiSelectMode = true;
                    } else {
                      //  toolbarActionModeCallback = null;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mOptionMenu = menu;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setupActionView(tab.getPosition());
                if (tab.getPosition() > 0) {
                    startPageNumber = tab.getPosition();
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
             /**   if (mActionMode != null && !multiSelectMode){
                    mActionMode.finish();
                    mActionMode = null;
                    multiSelectMode = false;
                    loadDataSnapshotApiary(mDataSnapshot);
                    Toast.makeText(StartActivity.this, "Ви не можете вибирати одразу в обох вкладках", Toast.LENGTH_SHORT).show();

                }*/

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionNewApiary:
                createApiaryDialog();
                return true;
            case R.id.actionDelete:
                showDeleteApiaryDialog(String.valueOf(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText()), tabLayout.getSelectedTabPosition());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void moveBeehive(final List<Beehive> moveBeehives, final Beehive itemBeehive,
                            final String fromWhichApiary, final String inWhichApiary, final boolean inFront) {

            Query apiary = myRef.child(mUserUid).child("apiary").child(inWhichApiary);
            apiary.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Apiary apiaryInWhich = dataSnapshot.getValue(Apiary.class);
                    List<Beehive> beehives = apiaryInWhich.getBeehives();
                    List<Beehive> pastedBeehives = new ArrayList<Beehive>();
                    pastedBeehives.addAll(moveBeehives);

                    if (inFront){
                        for (int i = 0; i < pastedBeehives.size(); i++) {
                            beehives.add(itemBeehive.getNumberBeehive() - 1 + i, new Beehive(pastedBeehives.get(i)));
                        }
                    }else {
                        for (int i = 0; i < pastedBeehives.size(); i++) {
                            beehives.add(itemBeehive.getNumberBeehive() + i, new Beehive(pastedBeehives.get(i)));
                        }
                    }
                    for (int a = 0; a < beehives.size(); a++) {
                        beehives.get(a).setNumberBeehive(a + 1);
                    }
                    apiaryInWhich.setBeehives(beehives);
                    myRef.child(mUserUid).child("apiary").child(inWhichApiary).setValue(apiaryInWhich);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
	    mSelectMode = 0;
        multiSelectMode = false;
        //toolbarActionModeCallback = null;
        toolbarActionModeCallback.onDestroyActionMode(mActionMode);
    }

    @Click(R.id.buttonAddNewBeehive)
    public void buttonAddNewBeehiveWasClicked(){
        showDialogNewBeehive();
    }

    private void showDialogNewBeehive() {

       AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New BeehiveActivity");
        LayoutInflater inflater = StartActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_new_beehive, null);
        builder.setView(dialogView);
        final RadioGroup radiogroupTypeBeehive = (RadioGroup) dialogView.findViewById(R.id.radiogroupTypebeehive);
        final RadioGroup radiogroupQuantityColonies = (RadioGroup) dialogView.findViewById(R.id.radiogroupQuantityColonies);
        final Button createNewBeehive = (Button) dialogView.findViewById(R.id.buttonCreateNewBehive);
        final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(apiaries.get(tabLayout.getSelectedTabPosition() - 1).getBeehives().size() + 1);
        numberPicker.setValue(apiaries.get(tabLayout.getSelectedTabPosition() - 1).getBeehives().size() + 1);
        radiogroupTypeBeehive.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.radioButtonDadan:
                        Toast.makeText(StartActivity.this, "Type beehive is dadan.",
                                Toast.LENGTH_SHORT).show();
                        mTypeBeehive = DADAN;
                        break;
                    case R.id.radioButtonUkrainian:
                        Toast.makeText(StartActivity.this, "Type beehive is ukrainian.",
                                Toast.LENGTH_SHORT).show();
                        mTypeBeehive = UKRAINIAN;
                        break;
                    default:
                        break;
                }
            }
        });
        radiogroupQuantityColonies.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.radioButtonOneColony:
                        Toast.makeText(StartActivity.this, "Type beehive is dadan.",
                                Toast.LENGTH_SHORT).show();
                        mQuantityOfColonies = 1;
                        break;
                    case R.id.radioButtonTwoColony:
                        Toast.makeText(StartActivity.this, "Type beehive is ukrainian.",
                                Toast.LENGTH_SHORT).show();
                        mQuantityOfColonies = 2;
                        break;
                    default:
                        break;
                }
            }
        });
        createNewBeehive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberPicker.getValue() != 0 && mQuantityOfColonies != 0){
                    Beehive mBeehive = new Beehive();
                    mBeehive.setNumberBeehive(numberPicker.getValue());
                    mBeehive.setTypeBeehive(mTypeBeehive);
                    mBeehive.setFounded(Calendar.getInstance().getTime().getTime());

                    List<BeeColony> beeColonyList = new ArrayList<BeeColony>();
                    for (int i = 0; i < mQuantityOfColonies; i++){
                        BeeColony beeColony = new BeeColony();
                        beeColony.setBeeEmptyFrame(5);
                        beeColony.setCheckedTime(Calendar.getInstance().getTime().getTime());
                        beeColonyList.add(beeColony);
                    }
                    mBeehive.setBeeColonies(beeColonyList);

                    addBeehive(mBeehive, apiaries.get(tabLayout.getSelectedTabPosition() - 1));
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(StartActivity.this,"Please enter all necessary values ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void replaceBeehive(final List<Beehive> replaceBeehives, final Beehive itemBeehive, final String fromWhichApiary,
                               final String inWhichApiary) {
        multiSelectMode = false;
        toolbarActionModeCallback = null;
	    mSelectMode = 0;
        if (fromWhichApiary.equals(inWhichApiary)) {
            Query apiary = myRef.child(mUserUid).child("apiary").child(fromWhichApiary);
            apiary.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Apiary apiary = dataSnapshot.getValue(Apiary.class);
                    List<Beehive> pastedBeehives = new ArrayList<Beehive>();

                    pastedBeehives.addAll(replaceBeehives);
                    if (pastedBeehives.size() != 0) {
                        int eqwalsBeehivesApiary = apiary.getBeehives().size();
                        for (int numberBeehivesInDatabase = 0; numberBeehivesInDatabase < eqwalsBeehivesApiary; numberBeehivesInDatabase++) {
                            if (itemBeehive.getNumberBeehive() == apiary.getBeehives().get(numberBeehivesInDatabase).getNumberBeehive()) {
                                apiary.getBeehives().remove(numberBeehivesInDatabase);
                                for (int x = 0; x < pastedBeehives.size(); x++) {
	                                Beehive addBeehive = new Beehive(pastedBeehives.get(x));
	                                addBeehive.setNumberBeehive(itemBeehive.getNumberBeehive());
                                    apiary.getBeehives().add(itemBeehive.getNumberBeehive() - 1 + x, new Beehive(addBeehive));
                                }
                            }
                            if (pastedBeehives.get(0).getNumberBeehive() == apiary.getBeehives().get(numberBeehivesInDatabase).getNumberBeehive()) {
                                apiary.getBeehives().remove(numberBeehivesInDatabase);
	                            Beehive addBeehive = new Beehive(itemBeehive);
	                            addBeehive.setNumberBeehive(pastedBeehives.get(0).getNumberBeehive());
                                apiary.getBeehives().add(pastedBeehives.get(0).getNumberBeehive() - 1, new Beehive(addBeehive));
                            }
                        }
                    }
                    myRef.child(mUserUid).child("apiary").child(fromWhichApiary).setValue(apiary);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }else{
            Query apiary = myRef.child(mUserUid).child("apiary").child(inWhichApiary);
            apiary.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Apiary apiaryInWhich = dataSnapshot.getValue(Apiary.class);
                    List<Beehive> beehives = apiaryInWhich.getBeehives();
                    List<Beehive> pastedBeehives = new ArrayList<Beehive>();
                    pastedBeehives.addAll(replaceBeehives);

                    beehives.remove(itemBeehive.getNumberBeehive() - 1);
                    beehives.add(itemBeehive.getNumberBeehive() -1, new Beehive(pastedBeehives.get(0)));
                    for (int a = 0; a < beehives.size(); a++) {
                        beehives.get(a).setNumberBeehive(a + 1);
                    }
                    apiaryInWhich.setBeehives(beehives);
                    myRef.child(mUserUid).child("apiary").child(inWhichApiary).setValue(apiaryInWhich);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            Query apiary1 = myRef.child(mUserUid).child("apiary").child(fromWhichApiary);
            apiary1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Apiary apiaryFromWhich = dataSnapshot.getValue(Apiary.class);
                    List<Beehive> beehives = apiaryFromWhich.getBeehives();
                    List<Beehive> pastedBeehives = new ArrayList<Beehive>();
                    pastedBeehives.addAll(replaceBeehives);
                    beehives.remove(pastedBeehives.get(0).getNumberBeehive() - 1);
                    beehives.add(pastedBeehives.get(0).getNumberBeehive() -1, new Beehive(itemBeehive));
                    for (int a = 0; a < beehives.size(); a++) {
                        beehives.get(a).setNumberBeehive(a + 1);
                    }
                    apiaryFromWhich.setBeehives(beehives);
                    myRef.child(mUserUid).child("apiary").child(fromWhichApiary).setValue(apiaryFromWhich);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }


    }

    @Override
    public void dissmisActionMode() {
        if (toolbarActionModeCallback != null){
            toolbarActionModeCallback = null;

        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null, null);
        showableLogInGroup(true);

    }
    @Click(R.id.facebokLoginButton)
    void facebookLoginButtonWasClicked() {
        if (isOnline()) {
            showableLogInGroup(false);
            loginFacebook.setVisibility(View.VISIBLE);
            loginFacebook();
        }else{
            showLoginDialog();
        }
    }

    @Click(R.id.googleLoginButton)
    void googleLoginButtonWasClicked() {
        if (isOnline()) {
            showableLogInGroup(false);
            loginGoogle();
            loginGoogle.setVisibility(View.VISIBLE);
        }else{
            showLoginDialog();
        }
    }

    @Click(R.id.buttonLogout)
    void buttonLogoutWasClicked() {
        if (isOnline()) {
            signOut();
            showableLogInGroup(true);
        }else{
            showLoginDialog();
        }
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autorisation!");
        builder.setMessage(" Please select way to registring");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            mDrawerLayout.openDrawer(Gravity.RIGHT, true);
            }
        });
        builder.create().show();

    }
    private void googleSignOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            signOut();
                        }
                        // ...
                    }
                });
    }


    @Click(R.id.buttonGoogleLogout)
    void buttonGoogleLogout() {
        if(isOnline()) {
            googleSignOut();
        }else{
            noConnectionDialog();
        }
    }
    @Click(R.id.googleButton)
    public void buttonGoogleLoginWasClicked() {
        if (isOnline()) {
            signIn();
        }else{
            noConnectionDialog();
        }
    }

    private void noConnectionDialog() {

    }

    @Override
    public void onBackPressed() {
        if (mActionMode != null){
        /**    getSupportFragmentManager()
                    .beginTransaction().
                    remove(toolbarActionModeCallback).commit();*/
            mActionMode.finish();
            mActionMode = null;
            multiSelectMode = false;
            mSelectMode = 0;
            loadDataSnapshotApiary(mDataSnapshot);
        }
    }

    @Override
    protected void onPause() {
        if (mActionMode != null){
         /**   getSupportFragmentManager()
                    .beginTransaction().
                    remove(toolbarActionModeCallback).commit();*/
            mActionMode.finish();
            mActionMode = null;
            multiSelectMode = false;
            loadDataSnapshotApiary(mDataSnapshot);
        }
        super.onPause();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    public void getDataFromFirebase(String userUID) {
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        Query myHistory = myRef.child(userUID).child("history").orderByChild("typeNotifaction").startAt(1).endAt(4);
        myHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) {
                    mDataSnapshot = dataSnapshot;
                    loadDataSnapshotHistory(mDataSnapshot);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Query myApiaries = myRef.child(userUID).child("apiary");
        myApiaries.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) {
                    mDataSnapshot = dataSnapshot;
                    loadDataSnapshotApiary(mDataSnapshot);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadDataSnapshotHistory(DataSnapshot mDataSnapshot) {
        if (notifactionList.size() != 0){
            notifactionList.clear();
        }
        for (DataSnapshot postSnapshot : mDataSnapshot.getChildren()) {
            Notifaction notifaction = postSnapshot.getValue(Notifaction.class);
            notifactionList.add(notifaction);
        }
            if ( tabLayout.getTabCount() > 0 && alreadyExist("History" ) ){
                    HistoryFragment fragmentHistory = (HistoryFragment) pagerAdapter.getItem(position);
                    fragmentHistory.setDate(notifactionList);

            }else {
                HistoryFragment fragmentHistory = new HistoryFragment();
                fragmentHistory.setDate(notifactionList);
                pagerAdapter.addFragment(fragmentHistory, "History");
                pagerAdapter.notifyDataSetChanged();
            }


        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(pagerAdapter);
        }
        addOnLongClickListener();
        viewPager.setCurrentItem(startPageNumber);
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount() -1);
    }

}
