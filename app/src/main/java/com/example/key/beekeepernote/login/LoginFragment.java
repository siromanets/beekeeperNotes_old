package com.example.key.beekeepernote.login;


import android.support.v4.app.Fragment;

public class LoginFragment extends Fragment  {

//    private static final int MAX_PHONE_NUMBER_VALUE = 16;
//
//    private static final int MIN_PHONE_NUMBER_VALUE = 5;
//
//    private LoginFragmentBinding binding;
//
//    private NavController navController;
//
//    private LoginFragmentVM viewModel;
//
//    private String countryCode;
//
//    private AlertDialog progressDialog;
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        viewModel = ViewModelProviders.of(this).get(LoginFragmentVM.class);
//        viewModel.getUserData().observe(this, this::userDataLoaded);
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        navController = Navigation.findNavController(binding.getRoot());
//        binding.loginButton.setOnClickListener((v) -> onLoginButtonClick());
//        binding.signInButton.setOnClickListener((v) -> onSignUpButtonClick());
//        binding.resetPasswordButton.setOnClickListener((v) -> onResetPasswordClick());
//        binding.phoneNumberInput.addTextChangedListener(this);
//        binding.passwordTextInput.addTextChangedListener(this);
//        setupDefaultLocale();
//    }
//
//    private void userDataLoaded(Resource<UserData> userDataResource) {
//        switch (userDataResource.status) {
//            case Status.SUCCESS:
//                hideProgress();
//                showMainActivity();
//                break;
//            case Status.ERROR:
//                Snackbar.make(binding.getRoot(), userDataResource.message, Snackbar.LENGTH_LONG).show();
//                hideProgress();
//                break;
//            case Status.LOADING:
//                showProgress();
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void showMainActivity() {
//        MainActivity.showMainActivity(getContext());
//        Objects.requireNonNull(getActivity()).finish();
//    }
//
//    private void showProgress() {
//        progressDialog = new SpotsDialog.Builder()
//               .setContext(getContext())
//                .setTheme(R.style.CustomProgressDialog)
//                .build();
//        progressDialog.show();
//    }
//
//    private void hideProgress() {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
//    }
//
//    private void setupDefaultLocale() {
//        String country = getCountryConfigurationFromDevice();
//        if (!country.isEmpty()) {
//            binding.ccp.setDefaultCountryUsingNameCode(country);
//            binding.ccp.resetToDefaultCountry();
//            countryCode = binding.ccp.getSelectedCountryCodeWithPlus();
//            binding.phoneNumberInput.setText(countryCode);
//            Selection.setSelection(binding.phoneNumberInput.getText(), binding.phoneNumberInput.getText().length());
//            binding.ccp.setOnCountryChangeListener(country1 -> onCountrySelected());
//            binding.phoneNumberInput.addTextChangedListener(textWatcherForPhoneInput);
//        }
//    }
//
//    private String getCountryConfigurationFromDevice() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return Objects.requireNonNull(getContext()).getResources().getConfiguration()
//                    .getLocales().get(0).getCountry();
//        } else {
//            return Objects.requireNonNull(getContext()).getResources().getConfiguration().locale.getCountry();
//        }
//    }
//
//    private void onResetPasswordClick() {
////        Intent intent = new Intent(getContext(), ResetPasswordActivity.class);
////        startActivity(intent);
//    }
//
//    private void onLoginButtonClick() {
//        hideSystemKeyBoard();
//        if (ConnectionUtils.isConnected(Objects.requireNonNull(getContext()))) {
//            String userCountryCode = binding.ccp.getSelectedCountryCodeWithPlus();
//            String userPhoneNumber = binding.phoneNumberInput.getText().toString();
//            UserPhone userPhone = new UserPhone(userCountryCode, userPhoneNumber.substring(userCountryCode.length()));
//            viewModel.onLoginUser(userPhone, binding.passwordTextInput.getText().toString());
//        } else {
//            showConnectionToast();
//        }
//    }
//
//    private void hideSystemKeyBoard() {
//        View view = getActivity().getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }
//
//    private void showConnectionToast() {
//        Snackbar.make(binding.getRoot(), R.string.no_internet_connection, Snackbar.LENGTH_LONG).show();
//    }
//
//    private void onSignUpButtonClick() {
//        navController.navigate(R.id.action_loginFragment_to_signInFragment);
//    }
//
//    private boolean isEmpty() {
//        return binding.passwordTextInput.getText().toString().isEmpty()
//                || binding.phoneNumberInput.getText().toString().isEmpty()
//                || binding.ccp.getFullNumberWithPlus().isEmpty()
//                || !validNumber();
//    }
//
//    private boolean validNumber() {
//        return (binding.phoneNumberInput.getText().toString().length() <= MAX_PHONE_NUMBER_VALUE &&
//                binding.phoneNumberInput.getText().toString().length() > MIN_PHONE_NUMBER_VALUE);
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//        if (!isEmpty()) {
//            binding.loginButton.setEnabled(true);
//            binding.loginButton.setClickable(true);
//        } else {
//            binding.loginButton.setEnabled(false);
//            binding.loginButton.setClickable(false);
//        }
//    }
//
//    private void onCountrySelected() {
//        countryCode = binding.ccp.getSelectedCountryCodeWithPlus();
//        binding.phoneNumberInput.setText(binding.ccp.getSelectedCountryCodeWithPlus());
//        Selection.setSelection(binding.phoneNumberInput.getText(), binding.phoneNumberInput.getText().length());
//    }
//
//   private TextWatcher textWatcherForPhoneInput = new TextWatcher() {
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            if (s != null && !s.toString().startsWith(countryCode)) {
//                binding.phoneNumberInput.setText(countryCode);
//                Selection.setSelection(binding.phoneNumberInput.getText(), binding.phoneNumberInput.getText().length());
//            }
//        }
//
//    };
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
//    }
}
