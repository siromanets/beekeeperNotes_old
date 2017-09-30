package com.example.key.beekeepernote.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.key.beekeepernote.R;
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
import com.google.firebase.auth.GoogleAuthProvider;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A login screen that offers login via email/password.
 */
@EActivity
public class LoginActivity extends AppCompatActivity implements
		GoogleApiClient.OnConnectionFailedListener {
	private static final int RC_SIGN_IN = 9001;
	private static final String TAG ="LoginActivity" ;
	private FirebaseAuth mAuth;
	private CallbackManager mCallbackManager;
	@ViewById(R.id.email)
	AutoCompleteTextView mEmailView;
	@ViewById(R.id.buttonFacebookLogin)
	Button facebookLoginButton;
	@ViewById(R.id.buttonGoogleLogin)
	Button googleLoginButton;
	@ViewById(R.id.login_progress)
	ProgressBar mProgresBar;
	@ViewById(R.id.buttonResetPassword)
	Button buttonResetPassword;
	@ViewById(R.id.buttonCreateNewAccount)
	Button buttonCreateNewAccount;
	@ViewById(R.id.password)
	EditText mPasswordView;
	@ViewById(R.id.buttonLogout)
	Button buttonLogaut;
	@ViewById(R.id.buttonGoogleLogout)
	Button buttonGoogleLogout;
	@ViewById(R.id.email_sign_in_button)
	Button mEmailSignInButton;
	@ViewById(R.id.email_login_form)
	View mLoginFormView;

	@ViewById(R.id.facebookButton)
	LoginButton loginFacebook;
	@ViewById(R.id.userAcountImage)
	CircleImageView userAccountImage;
	@ViewById(R.id.googleButton)
	SignInButton loginGoogle;
	 GoogleApiClient mGoogleApiClient;
	@ViewById(R.id.textName)
	TextView textViewEmail;
	private String mUserUID;
	private boolean mTypeMode;
	private AlertDialog.Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		// Set up the login form.

		mAuth = FirebaseAuth.getInstance();
		populateAutoComplete();


		mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
				if (id == R.id.login || id == EditorInfo.IME_NULL) {
					return true;
				}
				return false;
			}
		});


		mEmailSignInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
			}
		});

	}

	private void populateAutoComplete() {

	}



	public boolean isOnline() {
		boolean connected = false;
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) LoginActivity.this
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

	@Click(R.id.buttonResetPassword)
	public void buttonResetPasswordWasClicked() {
		if (isOnline()) {
			startActivity(new Intent(LoginActivity.this, ResetPasswordActivity_.class));
		}else{
			showLoginDialog();
		}
	}

	@Click(R.id.buttonCreateNewAccount)
	public void buttonCreateNewAccount() {
		if (isOnline()) {
			startActivity(new Intent(LoginActivity.this, SignupActivity_.class));
		}else{
			showLoginDialog();
		}
	}

	@Click(R.id.email_sign_in_button)
	public void emailLoginButtonWasClicked() {
		if (isOnline()) {
			String email = mEmailView.getText().toString();
			final String password = mPasswordView.getText().toString();

			if (TextUtils.isEmpty(email)) {
				Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
				return;
			}

			if (TextUtils.isEmpty(password)) {
				Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
				return;
			}

			//   progressBar.setVisibility(View.VISIBLE);

			//authenticate mUser
			mAuth.signInWithEmailAndPassword(email, password)
					.addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
						@Override
						public void onComplete(@NonNull Task<AuthResult> task) {
							// If sign in fails, display a message to the mUser. If sign in succeeds
							// the mAuth state listener will be notified and logic to handle the
							// signed in mUser can be handled in the listener.
							mProgresBar.setVisibility(View.GONE);
							if (!task.isSuccessful()) {
								// there was an error
								if (password.length() < 6) {
									mPasswordView.setError(getString(R.string.minimum_password));
								} else {
									Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
								}
							} else {
								Intent intent = new Intent(LoginActivity.this, StartActivity_.class);
								startActivity(intent);
								finish();
							}
						}
					});
		}else{
			showLoginDialog();
		}
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
				Toast toast = Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT);
				handleFacebookAccessToken(loginResult.getAccessToken());
				toast.show();
			}

			@Override
			public void onCancel() {
				// App code
			}

			@Override
			public void onError(FacebookException exception) {
				updateUI(null, null);
			}

		});


	}

	private void showableLogInGroup(boolean b) {
		if (b) {
			mLoginFormView.setVisibility(View.VISIBLE);
			buttonResetPassword.setVisibility(View.VISIBLE);
			buttonCreateNewAccount.setVisibility(View.VISIBLE);
			facebookLoginButton.setVisibility(View.VISIBLE);
			googleLoginButton.setVisibility(View.VISIBLE);
			loginFacebook.setVisibility(View.GONE);
			loginGoogle.setVisibility(View.GONE);
			buttonLogaut.setVisibility(View.GONE);
			buttonGoogleLogout.setVisibility(View.GONE);
		} else {
			mLoginFormView.setVisibility(View.GONE);
			buttonResetPassword.setVisibility(View.GONE);
			buttonCreateNewAccount.setVisibility(View.GONE);
			facebookLoginButton.setVisibility(View.GONE);
			googleLoginButton.setVisibility(View.GONE);
			buttonGoogleLogout.setVisibility(View.GONE);
		}
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
							Toast.makeText(LoginActivity.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();

						} else {
							Profile profile = Profile.getCurrentProfile();
							updateUI(profile.getFirstName(), profile.getProfilePictureUri(200, 200).toString());
							mUserUID = mAuth.getCurrentUser().getUid();
							mTypeMode = true;
						}
					}
				});
	}

	private void signIn() {
		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
		startActivityForResult(signInIntent, RC_SIGN_IN);
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
							mUserUID = mAuth.getCurrentUser().getUid();

						} else {
							// If sign in fails, display a message to the mUser.
							Log.w(TAG, "signInWithCredential:failure", task.getException());
							Toast.makeText(LoginActivity.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();
							updateUI(null, null);
						}
					}
				});
	}


	private void googleSignOut() {
		Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
				new ResultCallback<Status>() {
					@Override
					public void onResult(@NonNull Status status) {
						if (status.isSuccess()) {
							mAuth.signOut();
						}
						// ...
					}
				});
	}


	@Click(R.id.googleButton)
	public void buttonGoogleLoginWasClicked() {
		signIn();
	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		Log.d(TAG, "onConnectionFailed:" + connectionResult);
		Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
	}

	@Click(R.id.buttonFacebookLogin)
	void facebookLoginButtonWasClicked() {
		if (isOnline()) {
			showableLogInGroup(false);
			loginFacebook.setVisibility(View.VISIBLE);
			loginFacebook();
		}else{
			showLoginDialog();
		}
	}

	@Click(R.id.buttonGoogleLogin)
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
			mAuth.signOut();
			showableLogInGroup(true);
		}else{
			showLoginDialog();
		}
	}

	@Click(R.id.buttonGoogleLogout)
	void buttonGoogleLogout() {
		if(isOnline()) {
			googleSignOut();
		}else{
			showLoginDialog();
		}
	}
	void showLoginDialog() {
		if (isOnline()) {
			builder = new AlertDialog.Builder(this);
			builder.setTitle("Для того щоб отримати доступ до всіх функцій програми потрібно зареєструватися");
			builder.setMessage("Виберіть спосіб реєстраці");

			builder.setPositiveButton("Зареєструватися", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int arg1) {

				}
			});
			builder.setNegativeButton("Анонімний вхід", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int arg1) {


				}
			});
			builder.setCancelable(true);
			builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
				}
			});

			AlertDialog alert = builder.create();
			alert.show();
		} else {
			builder = new AlertDialog.Builder(this);
			builder.setTitle("Мережа Інтернет");
			builder.setMessage("Нажаль ви зараз ви не підключені до мережі інтернет!" +
					" Керування автентифікацією недоступне! ");

			builder.setPositiveButton("Так", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int arg1) {

				}
			});

			builder.setCancelable(true);


			AlertDialog alert = builder.create();
			alert.show();
		}
	}

}

