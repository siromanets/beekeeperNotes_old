package com.example.key.beekeepernote.login;


import android.support.v4.app.Fragment;


public class SignInFragment extends Fragment {

//    private static final int MAX_PHONE_NUMBER_VALUE = 16;
//
//    private static final int MIN_PHONE_NUMBER_VALUE = 5;
//
//    private SignInFragmentBinding binding;
//
//    private NavController navController;
//
//    private String countryCode;
//
//    private SignInFragmentVM viewModel;
//
//    private AlertDialog progressDialog;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        viewModel = ViewModelProviders.of(this).get(SignInFragmentVM.class);
//        viewModel.getSignInData().observe(this, this::signInResponse);
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        navController = Navigation.findNavController(binding.getRoot());
//        binding.loginButton.setOnClickListener((v) -> onLoginButtonClick());
//        binding.signInButton.setOnClickListener((v) -> onSignInButtonClick());
//        binding.surnameTextInput.addTextChangedListener(this);
//        binding.userPhoneInput.addTextChangedListener(this);
//        binding.userNameInput.addTextChangedListener(this);
//        setupDefaultLocale();
//    }
//
//    private void signInResponse(Resource<ResponseSignInData> signInDataResource) {
//        switch (signInDataResource.status) {
//            case Status.SUCCESS:
//                hideProgress();
//               showConfirmFragment(signInDataResource.data);
//                break;
//            case Status.ERROR:
//                Snackbar.make(binding.getRoot(), signInDataResource.message, Snackbar.LENGTH_LONG).show();
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
//    private void showConfirmFragment(ResponseSignInData data) {
//        navController.navigate(R.id.action_signInFragment_to_confirmSMSFragment, ConfirmSMSFragment.createBundle(data));
//    }
//
//    private void showProgress() {
//        progressDialog = new SpotsDialog.Builder()
//                .setContext(getContext())
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
//            binding.userPhoneInput.setText(countryCode);
//            Selection.setSelection(binding.userPhoneInput.getText(), binding.userPhoneInput.getText().length());
//            binding.ccp.setOnCountryChangeListener((Country selectedCountry) -> onCountrySelected());
//            binding.userPhoneInput.addTextChangedListener(textWatcherForPhoneInput);
//        }
//    }
//
//    private void onSignInButtonClick() {
//        hideSystemKeyBoard();
//        if (ConnectionUtils.isConnected(Objects.requireNonNull(getContext()))) {
//            String userCountryCode = binding.ccp.getSelectedCountryCodeWithPlus();
//            String userPhoneNumber = binding.userPhoneInput.getText().toString();
//            UserName userName = new UserName(
//                    binding.userNameInput.getText().toString(),
//                    binding.surnameTextInput.getText().toString());
//            UserPhone userPhone = new UserPhone(
//                    userCountryCode,
//                    userPhoneNumber.substring(userCountryCode.length()));
//            viewModel.onRegisterSeller(userName, userPhone);
//        } else {
//            showNoConnectionToast();
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
//    private void onLoginButtonClick() {
//        navController.navigate(R.id.action_signInFragment_to_loginFragment);
//    }
//
//    private void showNoConnectionToast() {
//        Snackbar.make(binding.getRoot(), R.string.no_internet_connection, Snackbar.LENGTH_LONG).show();
//    }
//
//    private boolean isEmptyUserInputViews() {
//        if (binding.userNameInput.getText().toString().isEmpty()) {
//            return true;
//        }
//        if (binding.surnameTextInput.getText().toString().isEmpty()) {
//            return true;
//        }
//        if (binding.userPhoneInput.getText().toString().isEmpty()) {
//            return true;
//        }
//        if (!isValidNumber()) {
//            return true;
//        }
//        return false;
//    }
//
//    private boolean isValidNumber() {
//        return binding.userPhoneInput.getText().toString().length() <= MAX_PHONE_NUMBER_VALUE
//                && binding.userPhoneInput.getText().toString().length() > MIN_PHONE_NUMBER_VALUE;
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
//        if (!isEmptyUserInputViews()) {
//            binding.signInButton.setEnabled(true);
//            binding.signInButton.setClickable(true);
//        } else {
//            binding.signInButton.setEnabled(false);
//            binding.signInButton.setClickable(false);
//        }
//    }
//
//    private void onCountrySelected() {
//        countryCode = binding.ccp.getSelectedCountryCodeWithPlus();
//        binding.userPhoneInput.setText(binding.ccp.getSelectedCountryCodeWithPlus());
//        Selection.setSelection(binding.userPhoneInput.getText(), binding.userPhoneInput.getText().length());
//    }
//
//    private String getCountryConfigurationFromDevice() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return Objects.requireNonNull(getContext()).getResources().getConfiguration().getLocales().get(0).getCountry();
//        } else {
//            return Objects.requireNonNull(getContext()).getResources().getConfiguration().locale.getCountry();
//        }
//    }
//
//    private TextWatcher textWatcherForPhoneInput = new TextWatcher() {
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
//                binding.userPhoneInput.setText(countryCode);
//                Selection.setSelection(binding.userPhoneInput.getText(), binding.userPhoneInput.getText().length());
//            }
//        }
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
