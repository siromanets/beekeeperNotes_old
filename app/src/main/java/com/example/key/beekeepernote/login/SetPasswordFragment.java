package com.example.key.beekeepernote.login;

import android.support.v4.app.Fragment;

public class SetPasswordFragment extends Fragment {

//    private static final String TAG = "SetPasswordFragment";
//
//    private static final String VERIFICATION_DATA = "verification_data";
//
//    private static final int VALID_PASSWORD_CHARACTERS_NUMBER = 5;
//
//    private PasswordFragmentBinding binding;
//
//    private NavController navController;
//
//    private ResponseVerificationData data;
//
//    private SetPasswordFragmentVM viewModel;
//
//    private AlertDialog progressDialog;
//
//    public static Bundle createBundle(ResponseVerificationData data) {
//        Bundle args = new Bundle();
//        args.putParcelable(VERIFICATION_DATA, data);
//        return args;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        viewModel = ViewModelProviders.of(this).get(SetPasswordFragmentVM.class);
//        viewModel.getUserData().observe(this, this :: userDataLoaded);
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_password, container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        navController = Navigation.findNavController(binding.getRoot());
//        binding.setPasswordButton.setOnClickListener((v) -> onAcceptButtonClick());
//        binding.passwordInput.addTextChangedListener(this);
//        binding.passwordConfirmInput.addTextChangedListener(this);
//        initData();
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
//        getActivity().finish();
//    }
//
//    private void initData() {
//        final SetPasswordFragmentArgs arguments = SetPasswordFragmentArgs.fromBundle(getArguments());
//        if (arguments.getVerificationData() != null) {
//            data = arguments.getVerificationData();
//        } else {
//            throw new RuntimeException("Verification details are not provided");
//        }
//    }
//
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
//    public boolean isEmpty() {
//        return binding.passwordConfirmInput.getText().toString().isEmpty() || binding.passwordInput.getText().toString().isEmpty();
//    }
//
//    public boolean isValidPassword() {
//        if (binding.passwordInput.getText().toString().length() < VALID_PASSWORD_CHARACTERS_NUMBER) {
//            binding.passwordInputLayoutOne.setError(getString(R.string.unreliable_password_text));
//            return false;
//        }
//        return true;
//    }
//
//    public boolean isConfirmed() {
//        if (!binding.passwordInput.getText().toString().equals(
//                binding.passwordConfirmInput.getText().toString())) {
//            binding.passwordInputLayout.setError(getString(R.string.not_the_same));
//            return false;
//        }
//        return true;
//    }
//
//    private void onAcceptButtonClick() {
//        hideSystemKeyBoard();
//        if (ConnectionUtils.isConnected(Objects.requireNonNull(getContext()))) {
//            if (isValidPassword() && isConfirmed() && data != null) {
//             viewModel.setPassword(data, binding.passwordInput.getText().toString());
//            }
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
//    private void showNoConnectionToast() {
//        Snackbar.make(binding.getRoot(), R.string.no_internet_connection, Snackbar.LENGTH_LONG).show();
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
//            binding.setPasswordButton.setEnabled(true);
//        } else {
//            binding.setPasswordButton.setEnabled(false);
//        }
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
//    }
}
